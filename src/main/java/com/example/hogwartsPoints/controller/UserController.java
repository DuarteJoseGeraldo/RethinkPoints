package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.register.RegisterUserDTO;
import com.example.hogwartsPoints.dto.update.UpdateUserDTO;
import com.example.hogwartsPoints.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;

@Controller
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/data") //acessar os dados de qualquer user
    public ResponseEntity<?> getUserDataById(@RequestHeader Long userId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.getUserDataById(getRequestToken(request), userId));
    }

    @GetMapping(value = "/info") // dados do user logado
    public ResponseEntity<?> getUserDataById(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.getUserInfo(getRequestToken(request)));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO userData) throws Exception {
        return ResponseEntity.created(URI.create("/users/register")).body(userService.registerUser(userData));
    }

    @PatchMapping(value = "/update/data", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserData(@RequestBody UpdateUserDTO newUserData, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.updateData(getRequestToken(request), newUserData));
    }

    @PostMapping(value = "/update/password", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody MultiValueMap<String, String> body, HttpServletRequest request) throws Exception {
        log.info("changePassword() - 'Body': {}", body);
        return ResponseEntity.ok(userService.changePassword(getRequestToken(request), body));
    }

    @DeleteMapping(value = "/disable", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> disableUser(@RequestBody MultiValueMap<String, String> body, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.disableUser(getRequestToken(request), Objects.requireNonNull(body.getFirst("password"))));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.deleteUser(getRequestToken(request), userId));
    }

}
