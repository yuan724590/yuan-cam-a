package yuan.cam.a.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("ComputerConfigDTO.ConfigDTO")
public class ConfigVO {
    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @ApiModelProperty(value = "商品品牌", required = true)
    private String brand;

    @ApiModelProperty(value = "商品类型", required = true)
    private String type;

    @ApiModelProperty(value = "商品名", required = true)
    private String name;

    @ApiModelProperty(value = "商品价格", required = true)
    private Double price;

    @ApiModelProperty(value = "商品底价", required = true)
    private double floorPrice;
}
