package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteEntityRepository extends PagingAndSortingRepository<VoteEntity, Integer> {
    List<VoteEntity> findAllByUserIdAndRsEventId(int userId, int rsEventId, Pageable pageable);
    @Query(value = "from VoteEntity where localDateTime between ?1 and ?2")
    List<VoteEntity> findAllBetweenTime(LocalDateTime startTime, LocalDateTime endTime);
}
