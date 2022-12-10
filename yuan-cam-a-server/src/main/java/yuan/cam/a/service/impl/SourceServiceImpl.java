package yuan.cam.a.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuan.cam.a.service.SourceService;
import yuan.cam.b.dto.ComputerConfigDTO;
import yuan.cam.b.dto.GoodsInfoDTO;
import yuan.cam.b.export.SourceApi;
import yuan.cam.b.vo.ComputerConfigVO;
import yuan.cam.b.vo.ResultVO;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SourceServiceImpl implements SourceService {

    @Autowired
    private SourceApi sourceApi;

    @Override
    public String insertConfig(GoodsInfoDTO reqDTO) {
        return sourceApi.insertGoods(reqDTO).getResult();
    }

    @Override
    public String deleteConfig(ComputerConfigDTO.DeleteConfigDTO reqDTO) {
        return sourceApi.deleteGoods(reqDTO).getResult();
    }

    @Override
    public List<ComputerConfigVO> queryConfig(Map<String, String> search, int page, int size) {
        ComputerConfigDTO.QueryDetailDTO queryConfigDTO = new ComputerConfigDTO.QueryDetailDTO();
        queryConfigDTO.setSearch(search);
        queryConfigDTO.setPage(page);
        queryConfigDTO.setSize(size);
        return sourceApi.queryDetail(queryConfigDTO).getResult();
    }

//url请求的方式 请求b服务
//    @Override
//    public ResponseEntity<String> insertConfig(ComputerConfigDTO.InsertConfigDTO reqDTO) {
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    @Override
//    public ResponseEntity<String> deleteConfig(ComputerConfigDTO.DeleteConfigDTO reqDTO) {
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    @Override
//    public ResponseEntity<String> editConfig(ComputerConfigDTO.EditConfigDTO reqDTO) {
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    @Override
//    public ResponseEntity<List<ConfigVO>> queryConfig(ComputerConfigDTO.QueryConfigDTO reqDTO) {
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
}
