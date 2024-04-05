package com.ra.controller;

import com.ra.model.dto.request.UserLoginDTO;
import com.ra.model.dto.request.UserRequestDTO;
import com.ra.model.dto.response.UserLoginResponseDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.User;
import com.ra.service.UserService;
import com.ra.validate.ResponseValidate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO requestDTO){
        UserResponseDTO userResponseDTO= userService.register(requestDTO);
        return new ResponseEntity<>(userResponseDTO,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO,BindingResult result){
        if(result.hasErrors()){
            List<String> list = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
               list.add(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>
                    (new ResponseValidate<>(400,"Badrequest",list),HttpStatus.BAD_REQUEST);
        }
        UserLoginResponseDTO userLoginResponseDTO = userService.login(userLoginDTO);
        return new ResponseEntity<>(userLoginResponseDTO,HttpStatus.OK);
    }
}
