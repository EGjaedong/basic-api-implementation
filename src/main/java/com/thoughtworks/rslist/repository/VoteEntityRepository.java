package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VoteEntityRepository extends PagingAndSortingRepository<VoteEntity, Integer> {
    List<VoteEntity> findAllByUserIdAndRsEventId(int userId, int rsEventId, Pageable pageable);
}
