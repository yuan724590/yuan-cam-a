package yuan.cam.a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuan.cam.a.export.SourceApi;
import yuan.cam.a.service.SourceService;
import yuan.cam.b.dto.ComputerConfigDTO;
import yuan.cam.b.dto.GoodsInfoDTO;
import yuan.cam.b.vo.ComputerConfigVO;

import java.util.List;

@RequestMapping
@RestController
public class SourceController implements SourceApi {
    @Autowired
    private SourceService sourceService;

    @Override
    public String insertConfig(@RequestBody @Validated GoodsInfoDTO reqDTO) {
        return sourceService.insertConfig(reqDTO);
    }

    @Override
    public String deleteConfig(@RequestBody @Validated ComputerConfigDTO.DeleteConfigDTO reqDTO) {
        return sourceService.deleteConfig(reqDTO);
    }

    @Override
    public List<ComputerConfigVO> queryConfig(@RequestBody @Validated ComputerConfigDTO.QueryDetailDTO reqDTO) {
        return sourceService.queryConfig(reqDTO.getSearch(), reqDTO.getPage(), reqDTO.getSize());
    }
}
