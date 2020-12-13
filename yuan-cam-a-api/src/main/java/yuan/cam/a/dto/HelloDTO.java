package yuan.cam.a.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class HelloDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("HelloDTO.HelloRedisDTO")
    public static class HelloRedisDTO{
        @ApiModelProperty(value = "名字", required = true)
        @NotNull
        private String name;
    }
}
