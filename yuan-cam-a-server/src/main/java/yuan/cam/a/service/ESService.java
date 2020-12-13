package yuan.cam.a.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import yuan.cam.a.dto.ConfigDTO;

import java.util.List;

public interface ESService {

    String insertConfig(ConfigDTO configDTO, String qid);

    String deleteConfig(List<Integer> idList, String qid);

    JSONArray queryConfig(JSONObject jsonObject, Integer page, Integer size, String qid);

    Integer queryCount(JSONObject jsonObject, String qid);
}
