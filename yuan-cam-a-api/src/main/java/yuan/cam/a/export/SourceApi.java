package yuan.cam.a.export;


import io.swagger.annotations.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yuan.cam.a.ContentConst;
import yuan.cam.b.dto.ComputerConfigDTO;
import yuan.cam.b.vo.ConfigVO;

import java.util.List;


@Api(value = "数据层变更")
@FeignClient(value = ContentConst.SERVICE_NAME)
public interface SourceApi {

    @ApiOperation(value = "新增商品信息", response = ConfigVO.class)
    @PostMapping(value = "/insert", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    String insertConfig(@RequestBody @Validated ComputerConfigDTO.InsertConfigDTO reqDTO);

    @ApiOperation(value = "删除商品信息", response = ConfigVO.class)
    @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    String deleteConfig(@RequestBody @Validated ComputerConfigDTO.DeleteConfigDTO reqDTO);

    @ApiOperation(value = "编辑商品信息", response = ConfigVO.class)
    @PostMapping(value = "/edit", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    String editConfig(@RequestBody @Validated ComputerConfigDTO.EditConfigDTO reqDTO);

    @ApiOperation(value = "查询商品信息", response = ConfigVO.class)
    @PostMapping(value = "/query", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    List<ConfigVO> queryConfig(@RequestBody @Validated ComputerConfigDTO.QueryConfigDTO reqDTO);

}
