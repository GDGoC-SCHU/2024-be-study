package gdg.week2.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String loginId;
    private String password;
}
