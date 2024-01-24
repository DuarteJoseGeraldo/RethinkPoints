package com.example.RethinkPoints.controller;

import com.example.RethinkPoints.service.UserService;
import com.example.RethinkPoints.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtUtil jwtUtil;
    @Autowired
    private MockMvc mockMvc;
}
