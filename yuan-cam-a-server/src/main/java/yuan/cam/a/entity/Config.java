package yuan.cam.a.entity;

import lombok.Data;

@Data
public class Config {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String brand;

    /**
     * 底价
     */
    private Double floorPrice;

    /**
     * 商品名称
     */
    private String type;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private Double price;
}
