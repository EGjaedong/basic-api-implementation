package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteEntityRepository extends CrudRepository<VoteEntity, Integer> {
    List<VoteEntity> findAllByUserIdAndRsEventId(int userId, int rsEventId);
}
