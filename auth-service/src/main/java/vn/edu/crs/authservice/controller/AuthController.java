package vn.edu.crs.authservice.controller;

import vn.edu.crs.authservice.dto.LoginRequestDTO;
import vn.edu.crs.authservice.dto.LoginResponseDTO;
import vn.edu.crs.authservice.dto.StudentDTO;
import vn.edu.crs.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }

    @GetMapping("/public/students")
    public ResponseEntity<List<StudentDTO>> getStudents() {
        List<StudentDTO> students = authService.getAllStudents();
        return ResponseEntity.ok(students);
    }
}