package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.register.RegisterUserDTO;
import com.example.hogwartsPoints.dto.update.UpdateUserDTO;
import com.example.hogwartsPoints.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/data") //acessar os dados de qualquer user
    public ResponseEntity<?> getUserDataById(@RequestHeader Long userId, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDataById(getRequestToken(request), userId));
    }

    @GetMapping(value = "/info") // dados do user logado
    public ResponseEntity<?> getUserDataById(HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo(getRequestToken(request)));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO userData, HttpServletRequest request) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(getRequestToken(request), userData));
    }

    @PatchMapping(value = "/update/data", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserData(@RequestBody UpdateUserDTO newUserData, HttpServletRequest request) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateData(getRequestToken(request), newUserData));
    }

    @PatchMapping(value = "/update/password", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody MultiValueMap<String, String> body, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword(getRequestToken(request), body));
    }

    @DeleteMapping(value = "/disable", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> disableUser(@RequestBody MultiValueMap<String, String> body, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.disableUser(getRequestToken(request), Objects.requireNonNull(body.getFirst("password"))));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(getRequestToken(request), userId));
    }

}
