package gdg.week2.controller;

import gdg.week2.UserRole;
import gdg.week2.domain.UserEntity;
import gdg.week2.dto.auth.JoinRequestDto;
import gdg.week2.dto.auth.LoginRequestDto;
import gdg.week2.dto.auth.UserDto;
import gdg.week2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session-login")
public class SessionLoginController {

    private final UserService userService;

    @Autowired
    public SessionLoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login-status")
    public ResponseEntity<String> loginCheck(@SessionAttribute(name = "userId", required = false) Long userId) {
        if (userId != null) {
            return ResponseEntity.ok("로그인 상태입니다. userId: " + userId);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되어 있지 않은 상태입니다.");
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinRequestDto joinRequestDto) {

        if (userService.checkLoginIdDuplicate(joinRequestDto.getLoginId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("로그인 아이디가 중복됩니다.");
        }
        if (!joinRequestDto.getPassword().equals(joinRequestDto.getPasswordCheck())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("1차, 2차 비밀번호가 일치하지 않습니다.");
        }

        userService.join(joinRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공하셨습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto,
                                        HttpServletRequest httpServletRequest) {

        UserEntity user = userService.login(loginRequestDto);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패 : 아이디 또는 비밀번호가 틀렸습니다.");
        }

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("userId", user.getId());
        session.setMaxInactiveInterval(30 * 60);
        return ResponseEntity.ok("로그인 성공하셨습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) session.invalidate();

        return ResponseEntity.ok("로그아웃 성공하셨습니다.");
    }

    // 유저 정보 확인 api
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@SessionAttribute(name = "userId", required = false) Long userId) {

        UserDto userDto = userService.getUserById(userId);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(userDto);
    }

    // 관리자 접근 api - UserRole 사용하여 일반유저인지, 관리자인지 확인
    @GetMapping("/admin")
    public ResponseEntity<String> checkAdmin(@SessionAttribute(name = "userId", required = false) Long userId) {

        UserDto userDto = userService.getUserById(userId);
        if (userDto == null || !userDto.getUserRole().equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("당신은 관리자 권한이 없습니다.");
        }
        return ResponseEntity.ok("관리자님 환영합니다.");
    }
}

/* postman 요청 모음

로그인 상태 확인
GET - http://localhost:8080/api/session-login/login-status

회원가입
POST - http://localhost:8080/api/session-login/join
Body (JSON)
{
  "loginId": "testuser",
  "password": "password123",
  "passwordCheck": "password123",
  "nickname": "Test User",
  "userRole": "USER"
}

로그인
POST - http://localhost:8080/api/session-login/login
Body (JSON)
{
  "loginId": "testuser",
  "password": "password123"
}

로그아웃
POST - http://localhost:8080/api/session-login/logout

유저 정보 확인
GET - http://localhost:8080/api/session-login/info

관리자 접근
GET - http://localhost:8080/api/session-login/admin

*/