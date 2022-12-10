package yuan.cam.a.service;

import yuan.cam.b.dto.ComputerConfigDTO;
import yuan.cam.b.dto.GoodsInfoDTO;
import yuan.cam.b.vo.ComputerConfigVO;

import java.util.List;
import java.util.Map;

public interface SourceService {

    String insertConfig(GoodsInfoDTO reqDTO);

    String deleteConfig(ComputerConfigDTO.DeleteConfigDTO reqDTO);

    List<ComputerConfigVO> queryConfig(Map<String, String> search, int page, int size);

//url请求的方式 请求b服务
//    @PostMapping(value = "/source/insert/goods")
//    String insertConfig(GoodsInfoDTO reqDTO);
//
//    @PostMapping(value = "/source/delete")
//    ResponseEntity<String> deleteConfig(ComputerConfigDTO.DeleteConfigDTO reqDTO);
//
//    @PostMapping(value = "/source/edit")
//    ResponseEntity<String> editConfig(ComputerConfigDTO.EditConfigDTO reqDTO);
//
//    @PostMapping(value = "/source/query")
//    ResponseEntity<List<ConfigVO>> queryConfig( ComputerConfigDTO.QueryConfigDTO reqDTO);
}