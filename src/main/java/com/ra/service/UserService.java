package com.ra.service;

import com.ra.model.dto.request.UserLoginDTO;
import com.ra.model.dto.request.UserRequestDTO;
import com.ra.model.dto.response.UserLoginResponseDTO;
import com.ra.model.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO register(UserRequestDTO requestDTO);

    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);

}
