package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.ChangePasswordDTO;
import com.example.hogwartsPoints.dto.RegisterUserDTO;
import com.example.hogwartsPoints.dto.UpdateUserDTO;
import com.example.hogwartsPoints.service.UserService;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/data") //acessar os dados de qualquer user
    public ResponseEntity<?> getUserDataById(@RequestHeader Long userId, HttpServletRequest request) throws Exception {
        jwtUtil.adminValidator((String) request.getAttribute("userType"));
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDataById(userId));
    }

    @GetMapping("/info") // dados do user logado
    public ResponseEntity<?> getUserDataById(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDataById((Long) request.getAttribute("userId")));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO userData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userData));
    }

    @PatchMapping("/update/data")
    public ResponseEntity<?> updateUserData(@RequestBody UpdateUserDTO newUserData, HttpServletRequest request){
        newUserData.setId((Long)request.getAttribute("userId"));
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateData(newUserData));
    }
    @PatchMapping("/update/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordData, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword((Long) request.getAttribute("userId"), changePasswordData));
    }
    @DeleteMapping("/disable")
    public ResponseEntity<?> disableUser(@RequestHeader String password, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.disableUser((Long) request.getAttribute("userId"), password));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId, HttpServletRequest request) throws Exception {
        jwtUtil.adminValidator((String)request.getAttribute("userType"));
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
    }

}
