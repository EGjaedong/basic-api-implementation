package com.thoughtworks.rslist.entity;

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
    @ManyToOne()
    private UserEntity userEntity;
}
