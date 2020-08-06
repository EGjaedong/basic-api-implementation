package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    List<UserEntity> findAll();
    UserEntity findUserEntityById(Integer id);
    @Transactional
    int deleteUserEntityById(Integer id);
    @Override
    void deleteAll();
}
