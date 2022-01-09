package yuan.cam.a.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class ComputerConfigDTO {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ComputerConfigDTO.InsertConfigDTO")
    public static class InsertConfigDTO {
        @ApiModelProperty(value = "商品品牌", required = true)
        @NotNull
        private String brand;

        @ApiModelProperty(value = "商品底价", required = true)
        @NotNull
        private double floorPrice;

        @ApiModelProperty(value = "商品类型", required = true)
        @NotNull
        private String type;

        @ApiModelProperty(value = "商品名", required = true)
        @NotNull
        private String name;

        @ApiModelProperty(value = "商品价格", required = true)
        @NotNull
        private Double price;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ComputerConfigDTO.DeleteConfigDTO")
    public static class DeleteConfigDTO {
        @ApiModelProperty(value = "要删除的id列表", required = true)
        @NotEmpty
        List<Integer> idList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ComputerConfigDTO.EditConfigDTO")
    public static class EditConfigDTO {
        @ApiModelProperty(value = "id", required = true)
        private Integer id;

        @ApiModelProperty(value = "商品品牌", required = true)
        @NotNull
        private String brand;

        @ApiModelProperty(value = "商品底价", required = true)
        @NotNull
        private double floorPrice;

        @ApiModelProperty(value = "商品类型", required = true)
        @NotNull
        private String type;

        @ApiModelProperty(value = "商品名", required = true)
        @NotNull
        private String name;

        @ApiModelProperty(value = "商品价格", required = true)
        @NotNull
        private Double price;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ComputerConfigDTO.QueryConfigDTO")
    public static class QueryConfigDTO {

        @ApiModelProperty(value = "查询条件", required = true)
        private Map<String, String> search;

        @NotNull
        @Min(1)
        @ApiModelProperty(value = "页码", required = true)
        private Integer page;

        @NotNull
        @Min(1)
        @ApiModelProperty(value = "页面大小", required = true)
        private Integer size;
    }
}
