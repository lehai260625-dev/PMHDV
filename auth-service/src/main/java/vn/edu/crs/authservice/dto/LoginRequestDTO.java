package vn.edu.crs.authservice.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class LoginRequestDTO {
    @NotBlank(message = "Username khong duoc de trong")
    private String username;
    @NotBlank(message = "Password khong duoc de trong")
    private String password;
}

// path: auth-service/src/main/java/vn/edu/crs/authservice/dto/LoginResponseDTO.java

// purpose: DTO tra ve token va thong tin co ban sau khi dang nhap thanh cong
