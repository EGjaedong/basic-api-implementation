package com.thoughtworks.rslist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rs_event")
public class RsEventEntity {
    @Id
    @GeneratedValue()
    private Integer id;
    private String eventName;
    private String keyword;
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @JsonBackReference
    public UserEntity getUserEntity() {
        return userEntity;
    }

    @JsonBackReference
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
