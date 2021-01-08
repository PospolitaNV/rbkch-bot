package com.npospolita.rbkchbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private Integer id;

    private String firstName;
    private String lastName;
    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> chatMembership = new HashSet<>();

    public Member addMembership(Long chatId) {
        chatMembership.add(chatId);
        return this;
    }

    public Member removeMembership(Long chatId) {
        chatMembership.remove(chatId);
        return this;
    }

    public void setUsername(String username) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
