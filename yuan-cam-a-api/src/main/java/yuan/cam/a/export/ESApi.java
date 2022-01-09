package yuan.cam.a.export;


import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yuan.cam.a.common.Constants;
import yuan.cam.a.dto.ESDTO;
import yuan.cam.a.vo.ConfigVO;


@Api(value = "ES变更")
@FeignClient(value = Constants.SERVICE_NAME)
public interface ESApi {

    @ApiOperation(value = "新增商品信息", response = ConfigVO.class)
    @PostMapping(value = "/es/insert", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    String insertConfig(@RequestBody @Validated ESDTO.ESInsertConfigDTO reqDTO);

    @ApiOperation(value = "删除商品信息", response = ConfigVO.class)
    @PostMapping(value = "/es/delete", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    String deleteConfig(@RequestBody @Validated ESDTO.ESDeleteConfigDTO reqDTO);

    @ApiOperation(value = "查询商品信息", response = ConfigVO.class)
    @PostMapping(value = "/es/query", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    JSONArray queryConfig(@RequestBody @Validated ESDTO.ESQueryConfigDTO reqDTO);

}
