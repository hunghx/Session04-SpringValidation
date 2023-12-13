package ra.academy.service;

import ra.academy.dto.request.SignInDto;
import ra.academy.dto.request.SignUpDto;
import ra.academy.dto.response.UserResponse;
import ra.academy.entity.User;
import ra.academy.exception.UserNameAndPasswordException;

public interface IUserService {
     UserResponse login(SignInDto signInDto) throws UserNameAndPasswordException;

     void register(SignUpDto signUpDto);
     boolean existByUserName(String username);
     boolean existByEmail(String email);
     boolean existByPhone(String phone);

}
