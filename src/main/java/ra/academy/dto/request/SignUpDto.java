package ra.academy.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import ra.academy.entity.Role;
import ra.academy.validator.PasswordMatching;
import ra.academy.validator.UserNameUnique;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatching(password = "password",
        confirmPassword = "confirmPassword")
public class SignUpDto {
    @NotBlank(message = "Không được để trống")
    @Size(min = 6, max = 50, message = "Độ dài tối thiểu 6 , tối đa 50 kí tự")
    @UserNameUnique
    private String username;
    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Mật khẩu ít nhất 8 kí tự, ít nhất 1 kí tự Hoa, 1 kí tự thường, 1 kí tự đặc biệt và không chứa khoảng trắng")
    private String password;
    private String confirmPassword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;
    @NotBlank(message = "Không được để trống")

    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "email không đúng định dạng!")
    private String email;
    @NotBlank(message = "Không được để trống")
    @Pattern(regexp = "^(0|\\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5]|9[0-4|6-9])(\\d{7})$", message = "Số điện thoại không đúng định dạng")
    private String phone;
    private MultipartFile file;
    private Set<String> listRoles;
}
