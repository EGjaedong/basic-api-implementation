package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.Gender;
import com.thoughtworks.rslist.domain.Vote;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        for (int i = 0; i < 8; i++) {
            VoteEntity voteEntity = VoteEntity.builder().localDateTime(LocalDateTime.now()).rsEvent(rsEventEntity)
                    .user(userEntity).num(i + 1).build();
            voteEntityRepository.save(voteEntity);
        }
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

    @Test
    void should_get_vote_record_add_page() throws Exception {
        mockMvc.perform(get("/voteRecord")
                .param("userId", String.valueOf(userEntity.getId()))
                .param("rsEventId", String.valueOf(rsEventEntity.getId()))
                .param("pageIndex", "1"))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].userId", is(userEntity.getId())))
                .andExpect(jsonPath("$[0].rsEventId", is(rsEventEntity.getId())))
                .andExpect(jsonPath("$[0].voteNum", is(1)))
                .andExpect(jsonPath("$[1].voteNum", is(2)))
                .andExpect(jsonPath("$[2].voteNum", is(3)))
                .andExpect(jsonPath("$[3].voteNum", is(4)))
                .andExpect(jsonPath("$[4].voteNum", is(5)));

        mockMvc.perform(get("/voteRecord")
                .param("userId", String.valueOf(userEntity.getId()))
                .param("rsEventId", String.valueOf(rsEventEntity.getId()))
                .param("pageIndex", "2"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].userId", is(userEntity.getId())))
                .andExpect(jsonPath("$[0].rsEventId", is(rsEventEntity.getId())))
                .andExpect(jsonPath("$[0].voteNum", is(6)))
                .andExpect(jsonPath("$[1].voteNum", is(7)))
                .andExpect(jsonPath("$[2].voteNum", is(8)));
    }

    @Test
    void should_vote_rsEvent() throws Exception {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String timeString = df.format(time);
        Vote vote = Vote.builder().userId(userEntity.getId()).time(timeString).voteNum(5).rsEventId(rsEventEntity.getId()).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(vote);
        mockMvc.perform(post("/rs/vote").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("message", is("vote success")));
    }


}