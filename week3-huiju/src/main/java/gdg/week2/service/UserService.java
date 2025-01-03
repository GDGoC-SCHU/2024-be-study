package gdg.week2.service;

import gdg.week2.domain.UserEntity;
import gdg.week2.dto.auth.JoinRequestDto;
import gdg.week2.dto.auth.LoginRequestDto;
import gdg.week2.dto.auth.UserDto;
import gdg.week2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 정보 저장
    public void join(JoinRequestDto joinRequestDto) {
        UserEntity user = new UserEntity();
        user.setLoginId(joinRequestDto.getLoginId());
        user.setPassword(joinRequestDto.getPassword());
        user.setNickname(joinRequestDto.getNickname());
        user.setUserRole(joinRequestDto.getUserRole());

        userRepository.save(user);
    }

    // 아이디 중복 확인
    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public UserEntity login(LoginRequestDto loginRequestDto) {
        Optional<UserEntity> user = userRepository.findByLoginId(loginRequestDto.getLoginId());

        if (user.isPresent() && user.get().getPassword().equals(loginRequestDto.getPassword())) {
            return user.get();
        }
        return null;
    }

    // 로그인된 사용자 세션정보를 통한 정보 조회
    // optional로 구현해도 됨!
    public UserDto getUserById(Long userId) {

        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getLoginId(),
                user.getPassword(),
                user.getNickname(),
                user.getUserRole()
        );
    }
}
