package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Gender;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserEntityRepository;
import com.thoughtworks.rslist.repository.VoteEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    RsEventRepository rsEventRepository;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    VoteEntityRepository voteEntityRepository;

    UserEntity userEntity = null;

    RsEventEntity rsEventEntity = null;

    VoteEntity voteEntity = null;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .age(20)
                .email("a@a.com")
                .gender(Gender.MALE)
                .phone("11234567890")
                .userName("user 0")
                .voteNum(10).build();
        userEntity = userEntityRepository.save(userEntity);
        rsEventEntity = RsEventEntity.builder()
                .eventName("第一条事件")
                .keyword("经济")
                .userEntity(userEntity)
                .build();
        rsEventEntity = rsEventRepository.save(rsEventEntity);
        voteEntity = VoteEntity.builder().localDateTime(LocalDateTime.now()).rsEvent(rsEventEntity)
                .user(userEntity).num(5).build();
        voteEntity = voteEntityRepository.save(voteEntity);
    }

    @AfterEach
    void tearDown() {
        voteEntityRepository.deleteAll();
        rsEventRepository.deleteAll();
        userEntityRepository.deleteAll();
    }

    @Test
    void should_get_vote_record() throws Exception {
        mockMvc.perform(get("/voteRecord")
                .param("userId", String.valueOf(userEntity.getId()))
                .param("rsEventId", String.valueOf(rsEventEntity.getId())))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(userEntity.getId())))
                .andExpect(jsonPath("$[0].rsEventId", is(rsEventEntity.getId())))
                .andExpect(jsonPath("$[0].voteNum", is(5)));

    }
}