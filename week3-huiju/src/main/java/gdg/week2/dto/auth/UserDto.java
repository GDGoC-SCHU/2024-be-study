package gdg.week2.dto.auth;

import gdg.week2.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String loginId;
    private String password;
    private String nickname;
    private UserRole userRole;
}
