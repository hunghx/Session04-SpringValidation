package ra.academy.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ra.academy.dto.request.SignInDto;
import ra.academy.dto.request.SignUpDto;
import ra.academy.dto.response.UserResponse;
import ra.academy.entity.Role;
import ra.academy.entity.RoleName;
import ra.academy.entity.User;
import ra.academy.exception.UserNameAndPasswordException;
import ra.academy.repository.IRoleRepository;
import ra.academy.repository.IUserRepository;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final IRoleRepository repository;
    private final UploadService uploadService;

    @Override
    public UserResponse login(SignInDto signInDto) throws UserNameAndPasswordException {
        User userLogin = userRepository
                .findByUsernameAndPassword(signInDto.getUsername(), signInDto.getPassword())
                .orElseThrow(() -> new UserNameAndPasswordException("Tài khoản hoặc mật khẩu không tồn tại"));
        return modelMapper.map(userLogin, UserResponse.class);
    }

    @Override
    public void register(SignUpDto signUpDto) {
        // upload filr nếu có
        String avatar = "https://iphonecugiare.com/wp-content/uploads/2020/03/84156601_1148106832202066_479016465572298752_o.jpg";
        if (!signUpDto.getFile().isEmpty()) {
            // upload file
            avatar = uploadService.uploadFileToServer(signUpDto.getFile());
        }
        // danh sách quyền
        Set<Role> roleSet = new HashSet<>();
        if (signUpDto.getListRoles() == null || signUpDto.getListRoles().isEmpty()) {
            roleSet.add(repository.findByRoleName(RoleName.USER).orElseThrow(() -> new NoSuchElementException("Không tìm thấy tài nguyên")));
        } else {
            signUpDto.getListRoles().forEach(s -> {
                switch (s) {
                    case "admin":
                        roleSet.add(repository.findByRoleName(RoleName.ADMIN).orElseThrow(() -> new NoSuchElementException("Không tìm thấy tài nguyên")));
                    case "pm":
                        roleSet.add(repository.findByRoleName(RoleName.PM).orElseThrow(() -> new NoSuchElementException("Không tìm thấy tài nguyên")));
                    case "leader":
                        roleSet.add(repository.findByRoleName(RoleName.LEADER).orElseThrow(() -> new NoSuchElementException("Không tìm thấy tài nguyên")));
                    case "user":
                    default:
                        roleSet.add(repository.findByRoleName(RoleName.USER).orElseThrow(() -> new NoSuchElementException("Không tìm thấy tài nguyên")));

                }
            });
        }
        User user = modelMapper.map(signUpDto, User.class);
        user.setAvatar(avatar);
        user.setRoles(roleSet);
        userRepository.save(user);
    }

    @Override
    public boolean existByUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}
