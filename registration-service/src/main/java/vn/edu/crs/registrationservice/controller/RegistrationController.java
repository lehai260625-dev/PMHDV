package vn.edu.crs.registrationservice.controller;

import vn.edu.crs.registrationservice.dto.RegistrationRequestDTO;
import vn.edu.crs.registrationservice.entity.Registration;
import vn.edu.crs.registrationservice.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {
   private final RegistrationService registrationService;

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public Registration register(@Valid @RequestBody RegistrationRequestDTO dto) {
       return registrationService.register(dto);
   }

   @DeleteMapping("/{id}")
   public void cancel(@PathVariable Long id) {
       registrationService.cancel(id);
   }

   @GetMapping("/my")
   public List<Registration> getMyRegistrations(Authentication authentication) {
       if (authentication == null || authentication.getCredentials() == null) {
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token không hợp lệ");
       }

       Long studentId = Long.valueOf(authentication.getCredentials().toString());
       return registrationService.getMyRegistrations(studentId);
   }
}