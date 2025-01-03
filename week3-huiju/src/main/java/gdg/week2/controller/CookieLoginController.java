package gdg.week2.controller;

import gdg.week2.domain.UserEntity;
import gdg.week2.dto.auth.JoinRequestDto;
import gdg.week2.dto.auth.LoginRequestDto;
import gdg.week2.dto.auth.UserDto;
import gdg.week2.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cookie-login")
public class CookieLoginController {

    private final UserService userService;

    // 생성자 하나일 때는 @Autowired 붙이지 않아도 알아서 의존성 주입됨.
    @Autowired
    public CookieLoginController(UserService userService) {
        this.userService = userService;
    }

    // 로그인된 사용자 확인 api
    @GetMapping("/login-status")
    public ResponseEntity<String> loginCheck(@CookieValue(value = "userId", required = false) Long userId) {
        if (userId != null) {
            return ResponseEntity.ok("로그인 상태입니다. userId: "+ userId);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되어 있지 않은 상태입니다.");
    }

    // 회원가입 api
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

    // 로그인 api
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto,
                                        // 쿠키 넘길 때 사용
                                        HttpServletResponse httpServletResponse) {

        UserEntity user = userService.login(loginRequestDto);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패 : 아이디 또는 비밀번호가 틀렸습니다.");
        }

        Cookie cookie = new Cookie("userId", String.valueOf(user.getId()));
        // 보안 위해서 사용, 연습할 때 없어도 됨
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60); // 60 * 60second == 1hours
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok("로그인 성공하셨습니다.");
    }

    // 로그아웃 api
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse httpServletResponse) {

        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 성공하셨습니다.");
    }

    // 유저 정보 확인 api
    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(@CookieValue(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되지 않았습니다.");
        }

        UserDto user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저 정보를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(user);
    }

    // 관리자 페이지 접근 api - UserRole 사용하여 일반유저인지, 관리자인지 확인
    @GetMapping("/admin")
    public ResponseEntity<?> accessAdminPage(@CookieValue(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되지 않았습니다.");
        }

        UserDto user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저 정보를 찾을 수 없습니다.");
        }

        if (!"ADMIN".equals(user.getUserRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
        }
        return ResponseEntity.ok("관리자 페이지에 접근 가능합니다.");
    }
}

/* postman 요청 모음

로그인 상태 확인
GET - http://localhost:8080/api/cookie-login/login-status

회원가입
POST - http://localhost:8080/api/cookie-login/join
Body (JSON)
{
  "loginId": "testuser",
  "password": "password123",
  "passwordCheck": "password123",
  "nickname": "Test User",
  "userRole": "USER"
}

로그인
POST - http://localhost:8080/api/cookie-login/login
Body (JSON)
{
  "loginId": "testuser",
  "password": "password123"
}

로그아웃
POST - http://localhost:8080/api/cookie-login/logout

 */