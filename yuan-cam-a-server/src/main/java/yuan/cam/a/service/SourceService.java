package yuan.cam.a.service;

import yuan.cam.b.dto.ComputerConfigDTO;
import yuan.cam.b.vo.ConfigVO;

import java.util.List;
import java.util.Map;

//@FeignClient(value = Constants.SERVICE_BB, fallback = SourceServiceImpl.class)
public interface SourceService {

    String insertConfig(ComputerConfigDTO.InsertConfigDTO reqDTO);

    String deleteConfig(ComputerConfigDTO.DeleteConfigDTO reqDTO);

    String editConfig(ComputerConfigDTO.EditConfigDTO reqDTO);

    List<ConfigVO> queryConfig(Map<String, String> search, int page, int size);

//url请求的方式 请求b服务
//    @PostMapping(value = "/source/insert")
//    ResponseEntity<String> insertConfig(ComputerConfigDTO.InsertConfigDTO reqDTO);
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