package gdg.week2.dto.auth;

import gdg.week2.UserRole;
import lombok.Data;

@Data
public class JoinRequestDto {

    private String loginId;
    private String nickname;
    private String password;
    private String passwordCheck;
    private UserRole userRole;
}
