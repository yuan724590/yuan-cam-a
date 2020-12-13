package yuan.cam.a.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import yuan.cam.a.ContentConst;
import yuan.cam.a.dto.ConfigDTO;
import yuan.cam.a.service.ESService;
import yuan.cam.a.util.EsUtil;
import yuan.cam.a.util.LogUtil;
import yuan.cam.a.util.RedisUtil;

import java.util.List;


@Slf4j
@Service
public class ESServiceImpl implements ESService {

//    private Mapper dozer =new DozerBeanMapper();

    @Override
    public String insertConfig(ConfigDTO configDTO, String qid) {
        if(configDTO == null){
            return "false,入参为空";
        }
        LogUtil.info("开始进行ES新增", qid);
        try{
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(configDTO));
            jsonObject.put("createTime", (int) (System.currentTimeMillis() / 1000));
            jsonObject.put("updateTime", (int) (System.currentTimeMillis() / 1000));
            IndexRequest indexRequest = new IndexRequest(ContentConst.ES_INDEX);
            indexRequest.source(jsonObject, XContentType.JSON);
            EsUtil.getESClient().index(indexRequest, RequestOptions.DEFAULT);
        }catch (Exception e){
            LogUtil.error("请求es异常:" + Throwables.getStackTraceAsString(e), qid);
            return "false,请求es异常";
        }
        return "true";
    }

    @Override
    public String deleteConfig(List<Integer> idList, String qid) {
        try{
            LogUtil.debug("开始进行ES删除", qid);
            DeleteByQueryRequest request = new DeleteByQueryRequest(ContentConst.ES_INDEX);
            request.setConflicts("proceed");
            request.setQuery(new BoolQueryBuilder().filter(new TermsQueryBuilder("id", Lists.newArrayList(idList))));
            request.setTimeout(TimeValue.timeValueMinutes(2));
            request.setScroll(TimeValue.timeValueMinutes(10));
            request.setRefresh(true);
            BulkByScrollResponse bulkResponse = EsUtil.getESClient().deleteByQuery(request, RequestOptions.DEFAULT);
            if(bulkResponse.getStatus().getTotal() != idList.size()){
                return "false,es删除数据异常";
            }
        }catch (Exception e){
            LogUtil.error("请求es异常:" + Throwables.getStackTraceAsString(e), qid);
            return "false,请求es删除数据异常";
        }
        return "true";
    }

    @Override
    public JSONArray queryConfig(JSONObject jsonObject, Integer page, Integer size, String qid) {
        JSONArray jsonArray = new JSONArray();
        try{
            LogUtil.debug("开始进行ES查询", qid);
            int from = (page - 1) * size;
            Jedis jedis = RedisUtil.getJedis(qid);
            String key = jsonObject.toString() + page + size;
            if(jedis.get(key) == null){
                MultiSearchRequest request = new MultiSearchRequest();
                SearchRequest searchRequest = new SearchRequest(ContentConst.ES_INDEX);
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                if(jsonObject.size() > 0){
                    searchSourceBuilder.query(QueryBuilders.matchQuery(jsonObject.keySet().iterator().next(), jsonObject.get(jsonObject.keySet().iterator().next())));
                }
                searchSourceBuilder.from(from);
                searchSourceBuilder.size(size);
                searchRequest.source(searchSourceBuilder);
                request.add(searchRequest);
                MultiSearchResponse searchResponse = EsUtil.getESClient().msearch(request, RequestOptions.DEFAULT);
                MultiSearchResponse.Item item = searchResponse.getResponses()[0];
                for(SearchHit hit : item.getResponse().getHits()) {
                    JSONObject json = new JSONObject();
                    json.put("id", hit.getSourceAsMap().get("id"));
                    json.put("brand", hit.getSourceAsMap().get("brand"));
                    json.put("name", hit.getSourceAsMap().get("name"));
                    json.put("type", hit.getSourceAsMap().get("type"));
                    json.put("price", hit.getSourceAsMap().get("price"));
                    json.put("floorPrice", hit.getSourceAsMap().get("floorPrice"));
                    json.put("createTime", hit.getSourceAsMap().get("createTime"));
                    json.put("updateTime", hit.getSourceAsMap().get("updateTime"));
                    jsonArray.add(json);
                }
                jedis.set(key, jsonArray.toJSONString());
                return jsonArray;
            }
            jsonArray = (JSONArray) JSON.parse(jedis.get(key));
        }catch (Exception e){
            LogUtil.error("请求es异常:" + Throwables.getStackTraceAsString(e), qid);
            return null;
        }
        return jsonArray;
    }

    @Override
    public Integer queryCount(JSONObject jsonObject, String qid) {
        int count = 0;
        try{
            CountRequest countRequest = new CountRequest();
            countRequest.indices(ContentConst.ES_INDEX);
            if(jsonObject.size() > 0) {
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                searchSourceBuilder.query(QueryBuilders.matchQuery(jsonObject.keySet().iterator().next(), jsonObject.get(jsonObject.keySet().iterator().next())));
                countRequest.source(searchSourceBuilder);
            }
            CountResponse countResponse = EsUtil.getESClient().count(countRequest, RequestOptions.DEFAULT);
            count = (int)countResponse.getCount();
        }catch (Exception e){
            LogUtil.error("查询总数请求es异常:" + Throwables.getStackTraceAsString(e), qid);
            return 0;
        }
        return count;
    }
}
