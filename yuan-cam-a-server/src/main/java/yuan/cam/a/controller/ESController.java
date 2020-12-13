package yuan.cam.a.controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuan.cam.a.dto.ESDTO;
import yuan.cam.a.export.ESApi;
import yuan.cam.a.service.ESService;

import javax.annotation.Resource;
import java.util.UUID;

@RequestMapping("/source")
@RestController
public class ESController implements ESApi {
    @Resource
    private ESService esService;

    @Override
    public String insertConfig(@RequestBody @Validated ESDTO.ESInsertConfigDTO reqDTO) {
        String qid = UUID.randomUUID().toString();
        return esService.insertConfig(reqDTO.getConfigDTO(), qid);
    }

    @Override
    public String deleteConfig(@RequestBody @Validated ESDTO.ESDeleteConfigDTO reqDTO) {
        String qid = UUID.randomUUID().toString();
        return esService.deleteConfig(reqDTO.getIdList(), qid);
    }

    @Override
    public JSONArray queryConfig(@RequestBody @Validated ESDTO.ESQueryConfigDTO reqDTO) {
        String qid = UUID.randomUUID().toString();
        return esService.queryConfig(reqDTO.getJsonObject(), reqDTO.getPage(), reqDTO.getSize(), qid);
    }
}
