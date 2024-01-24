package com.example.RethinkPoints.controller;

import com.example.RethinkPoints.dto.MessagesDTO;
import com.example.RethinkPoints.dto.register.RegisterUserDTO;
import com.example.RethinkPoints.dto.update.UpdateUserDTO;
import com.example.RethinkPoints.entity.ExtractEntity;
import com.example.RethinkPoints.entity.UserEntity;
import com.example.RethinkPoints.service.ExtractService;
import com.example.RethinkPoints.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static com.example.RethinkPoints.utils.AppUtils.getRequestToken;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final ExtractService extractService;

    @GetMapping(value = "/data") //acessar os dados de qualquer user
    public ResponseEntity<UserEntity> getUserDataById(@RequestParam Long userId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.getUserDataById(getRequestToken(request), userId));
    }

    @GetMapping(value = "/info") // dados do user logado
    public ResponseEntity<UserEntity> getUserDataById(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.getUserInfo(getRequestToken(request)));
    }

    @GetMapping(value = "/extract")
    public ResponseEntity<List<ExtractEntity>> getUserExtract(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(extractService.getUserExtract(getRequestToken(request)));
    }
    @GetMapping(value = "/extracts")//acessar os extratos de qualquer user
    public ResponseEntity<List<ExtractEntity>> getUserExtractsById(@RequestParam Long userId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(extractService.getUserExtractById(getRequestToken(request), userId));
    }
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> registerUser(@RequestBody RegisterUserDTO userData) throws Exception {
        return ResponseEntity.created(URI.create("/users/register")).body(userService.registerUser(userData));
    }
    @PatchMapping(value = "/update/data", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> updateUserData(@RequestBody UpdateUserDTO newUserData, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.updateData(getRequestToken(request), newUserData));
    }
    @PostMapping(value = "/update/password", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<MessagesDTO> changePassword(@RequestBody MultiValueMap<String, String> body, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.changePassword(getRequestToken(request), body));
    }

    @PostMapping(value = "/disable", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<MessagesDTO> disableUser(@RequestBody MultiValueMap<String, String> body, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.disableUser(getRequestToken(request), Objects.requireNonNull(body.getFirst("password"))));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<MessagesDTO> deleteUser(@RequestParam Long userId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userService.deleteUser(getRequestToken(request), userId));
    }

}
