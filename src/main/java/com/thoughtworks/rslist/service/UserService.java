package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repositories.UserListRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserListRepository userListRepository;

    @Autowired
    UserRepository userRepository;

    public int addOne(User user) {
        return userListRepository.addUser(user);
    }

    public List<User> getAllUsers() {
        List<User> userList = userListRepository.getUserList();
        return userList;
    }

    public int register(User user) {
        UserEntity entity = new UserEntity().builder()
                .userName(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .email(user.getEmail())
                .phone(user.getPhone())
                .voteNum(user.getVoteNum())
                .build();
        UserEntity saved = userRepository.save(entity);
        return saved.getId();
    }

    public UserEntity findUserById(int id) {
        return userRepository.findUserEntityById(id);
    }
}
