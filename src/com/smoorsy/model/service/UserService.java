package com.smoorsy.model.service;

import com.smoorsy.model.dao.users_schema.UserDao;
import com.smoorsy.model.dto.RegistrationUserDto;
import com.smoorsy.model.entity.users_schema.User;
import com.smoorsy.model.service.mapper.RegistrationUserMapper;
import com.smoorsy.model.service.validator.RegistrationUserValidator;
import com.smoorsy.model.service.validator.ValidationResult;
import com.smoorsy.model.service.validator.exception.ValidationException;

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

    public Long registration(RegistrationUserDto registrationUserDto) {
        // step 1: validation

        ValidationResult validationResult = registrationUserValidator.isValid(registrationUserDto);
        // Если список ошибок не пустой, тогда пробрасываем ошибку, которую обработаем на уровне сервлета
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2: map

        User userEntity = registrationUserMapper.fromObject(registrationUserDto);

        // step 3: save

        Optional<User> resultInsertUser = userDao.insert(userEntity);
        userEntity.setId(resultInsertUser.get().getId());

        // step 4: return

        return userEntity.getId();
    }
}
