package com.npospolita.rbkchbot.service;

import com.npospolita.rbkchbot.domain.Member;
import com.npospolita.rbkchbot.repo.MemberRepository;
import com.pengrad.telegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис трекинга пользователей чата. Трекаем пользователей, кто состоит в активных чата бота.
 */
@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MemberRepository memberRepository;
    private final ChatService chatService;

    //todo add caching
    public boolean isChatMember(User user) {
        Optional<Member> member = memberRepository.findById(user.id());
        return member.isPresent()
                && !member.get().getChatMembership().isEmpty();
    }

    //todo add caching
    public void addMembership(User user, Long chatId) {
        if (!chatService.isInWorkingChat(chatId))
            return;

        Member dbMember = memberRepository.findById(user.id())
                .orElse(new Member(user.id(), user.firstName(), user.lastName(), user.username(), new HashSet<>()));
        dbMember.addMembership(chatId);
        memberRepository.save(dbMember);
    }

    public void removeMembership(User user, Long chatId) {
        if (!chatService.isInWorkingChat(chatId))
            return;

        Member dbMember = memberRepository.findById(user.id())
                .orElse(new Member(user.id(), user.firstName(), user.lastName(), user.username(), new HashSet<>()));
        dbMember.removeMembership(chatId);
        memberRepository.save(dbMember);
    }

    public void removeAllMembership(Long chatId) {
        List<Member> removedMemberships = StreamUtils.createStreamFromIterator(memberRepository.findAll().iterator())
                .map(member -> member.removeMembership(chatId))
                .collect(Collectors.toList());

        memberRepository.saveAll(removedMemberships);
    }
}
