package com.smoorsy.model.service;

import com.smoorsy.model.dao.users_schema.UserDao;
import com.smoorsy.model.dao.users_schema.Users_and_RolesDao;
import com.smoorsy.model.dto.user.LoginUserDto;
import com.smoorsy.model.dto.user.RegistrationUserDto;
import com.smoorsy.model.dto.user.UserDto;
import com.smoorsy.model.entity.users_schema.User;
import com.smoorsy.model.entity.users_schema.Users_and_Roles;
import com.smoorsy.model.service.mapper.user.CheckRolesOfUserMapper;
import com.smoorsy.model.service.mapper.user.LoginUserMapper;
import com.smoorsy.model.service.mapper.user.RegistrationUserMapper;
import com.smoorsy.model.service.mapper.user.UserMapper;
import com.smoorsy.model.service.validator.ValidationResult;
import com.smoorsy.model.service.validator.exception.ValidationException;
import com.smoorsy.model.service.validator.user.LoginUserValidator;
import com.smoorsy.model.service.validator.user.RegistrationUserValidator;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    private final RegistrationUserValidator registrationUserValidator = RegistrationUserValidator.getInstance();
    private final RegistrationUserMapper registrationUserMapper = RegistrationUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final LoginUserValidator loginUserValidator = LoginUserValidator.getInstance();
    private final LoginUserMapper loginUserMapper = LoginUserMapper.getInstance();
    private final CheckRolesOfUserMapper checkRolesOfUserMapper = CheckRolesOfUserMapper.getInstance();
    private final Users_and_RolesDao usersAndRolesDao = Users_and_RolesDao.getInstance();

    public Optional<UserDto> registration(RegistrationUserDto registrationUserDto) {
        // step 1: validation

        ValidationResult validationResult = registrationUserValidator.isValid(registrationUserDto);
        // Если список ошибок не пустой, тогда пробрасываем ошибку, которую обработаем на уровне сервлета
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2: map

        User userEntity = registrationUserMapper.fromObject(registrationUserDto);

        // step 3: insert

        Optional<User> resultInsertUser = userDao.insert(userEntity);
        userEntity.setId(resultInsertUser.get().getId());

        // step 4: return

        return Optional.ofNullable(userMapper.fromObject(userEntity));
    }

    public Optional<UserDto> login(LoginUserDto loginUserDto) {
        // step 1: validation

        ValidationResult validationResult = loginUserValidator.isValid(loginUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2: map

        User userEntity = loginUserMapper.fromObject(loginUserDto);

        // step 3: find

        Optional<User> resultFindUser = userDao.findByEmailAndPassword(loginUserDto.getEmail(), loginUserDto.getPassword());

        if (resultFindUser.equals(Optional.empty())) {
            return Optional.empty();
        }

        userEntity.setId(resultFindUser.get().getId());
        userEntity.setSurname(resultFindUser.get().getSurname());
        userEntity.setName(resultFindUser.get().getName());
        userEntity.setPatronymic(resultFindUser.get().getPatronymic());
        userEntity.setBirthday(resultFindUser.get().getBirthday());

        // step 4: return

        return Optional.ofNullable(userMapper.fromObject(userEntity));
    }

    public List<Users_and_Roles> checkRoles(UserDto userDto) {
        User user = checkRolesOfUserMapper.fromObject(userDto);

        return usersAndRolesDao.findAllByUser(user);
    }
}
