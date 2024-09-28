package com.eatda.login.controller;

import com.eatda.login.form.JoinRequest;
import com.eatda.login.form.LoginRequest;
import com.eatda.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt-login/")
public class JwtLoginApiController {

    private final UserService userService;


    /** ========== President join/login ========== */

    @PostMapping("join/president")
    public ResponseEntity<String> joinPresident(@RequestBody JoinRequest.PresidentJoinRequest joinRequest) {
        try {
            userService.joinPresident(joinRequest);
            return ResponseEntity.ok("President 가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("login/president")
    public ResponseEntity<String> loginPresident(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.loginPresident(loginRequest);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /** ========== Child join/login ========== */

    @PostMapping("join/child")
    public ResponseEntity<String> joinChild(@RequestBody JoinRequest.ChildJoinRequest joinRequest) {
        try {
            userService.joinChild(joinRequest);
            return ResponseEntity.ok("Child 가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Child Login
    @PostMapping("login/child")
    public ResponseEntity<String> loginChild(@RequestBody ChildLoginRequest loginRequest) {
        try {
            String token = userService.loginChild(loginRequest);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
