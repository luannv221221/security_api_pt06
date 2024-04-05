package com.ra.validate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseValidate<T>{

    private  Integer httpStatus;
    private String httpStatusName;
    private T content;
}
