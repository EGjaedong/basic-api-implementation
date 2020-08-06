package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue()
    private Integer id;
    @Column(name = "name")
    private String userName;
    private Gender gender;
    private Integer age;
    private String email;
    private String phone;
    private Integer voteNum;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userEntity")
    private List<RsEventEntity> events;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "rsEvent")
    private List<VoteEntity> votes;
}
