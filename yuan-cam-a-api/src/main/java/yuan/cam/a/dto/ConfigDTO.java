package yuan.cam.a.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDTO {

    private Integer id;

    private String brand;

    private String type;

    private String name;

    private Double price;

    private double floorPrice;
}
