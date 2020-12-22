package com.npospolita.rbkchbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Чат, в котором бот работает
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingChat {

    @Id
    Long id;

    @EqualsAndHashCode.Exclude
    String tag;
}
