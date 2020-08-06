package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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

    @Transactional
    public int deleteUserById(int id) {
        return userRepository.deleteUserEntityById(id);
    }
}
