package com.npospolita.rbkchbot.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Список чатов по интересам
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicChat {

    @Id @GeneratedValue
    Long id;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String link;

}
