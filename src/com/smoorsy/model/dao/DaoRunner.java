package com.smoorsy.model.dao;

import com.smoorsy.model.dao.users_schema.Users_and_RolesDao;
import com.smoorsy.model.entity.users_schema.Role;
import com.smoorsy.model.entity.users_schema.User;
import com.smoorsy.model.entity.users_schema.Users_and_Roles;

public class DaoRunner {
    public static void main(String[] args) {
        Users_and_RolesDao usersAndRolesDao = Users_and_RolesDao.getInstance();
//        System.out.println(usersAndRolesDao.findAllByRole(Role.TEACHER));
//        System.out.println(usersAndRolesDao.insert(new Users_and_Roles(User.builder().id(1L).build(), Role.CLASSROOM_TEACHER)));
//        System.out.println(usersAndRolesDao.findAllByUser(User.builder().id(1L).build()));
//        System.out.println(usersAndRolesDao.deleteUserByRole(User.builder().id(1L).build(), Role.CLASSROOM_TEACHER));

    }
}
