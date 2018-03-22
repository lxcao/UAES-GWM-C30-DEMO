package com.uaes.esw.gwmc30demo.domain.model.entity.driver;
//TODO: 区分User和Driver两种role
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class User {
    String cellPhone;
    String password;
}
