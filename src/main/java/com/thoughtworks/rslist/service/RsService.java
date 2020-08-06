package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RsService {
    @Autowired
    private RsEventRepository rsRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    public int addEvent(RsEvent rsEvent) {
        UserEntity userEntity = userEntityRepository.findUserEntityById(rsEvent.getUserId());
        if (userEntity == null)
            return 0;
        RsEventEntity rsEventEntity = RsEventEntity.builder().eventName(rsEvent.getEventName())
                .keyword(rsEvent.getKeyWord()).userEntity(userEntity).build();

        return rsRepository.save(rsEventEntity).getId();
    }

    public List<RsEventEntity> findAll() {
        return rsRepository.findAll();
    }

    public RsEventEntity findById(int id) {
        return rsRepository.findRsEventEntityById(id);
    }

    public RsEventEntity updateById(int id, RsEvent rsEvent) {
        UserEntity userEntity = userEntityRepository.findUserEntityById(rsEvent.getUserId());
        RsEventEntity oldEvent = rsRepository.findRsEventEntityById(id);
        if (!(userEntity == null)) {
            RsEventEntity rsEventEntity = RsEventEntity.builder()
                    .eventName((rsEvent.getEventName() == null ? oldEvent.getEventName() : rsEvent.getEventName()))
                    .keyword((rsEvent.getKeyWord() == null ? oldEvent.getKeyword() : rsEvent.getKeyWord()))
                    .id(id)
                    .userEntity(userEntity).build();
            return rsRepository.save(rsEventEntity);
        }
        return null;
    }
}
