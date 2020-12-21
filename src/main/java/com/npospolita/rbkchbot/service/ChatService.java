package com.npospolita.rbkchbot.service;

import com.npospolita.rbkchbot.domain.WorkingChat;
import com.npospolita.rbkchbot.repo.WorkingChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final WorkingChatRepository workingChatRepository;
    private Set<Long> workingChats = new HashSet<>();

    public boolean isInWorkingChat(Long chatId) {
        if (workingChats.isEmpty()) {
            workingChats.addAll(StreamUtils.createStreamFromIterator(workingChatRepository.findAll().iterator())
                    .map(WorkingChat::getId)
                    .collect(Collectors.toSet()));
        }

        return workingChats.contains(chatId);
    }

//    @Cacheable(CacheNames.WORKING_CHATS)
    public void addWorkingChat(Long chatId) {
        workingChats.add(workingChatRepository.save(new WorkingChat(chatId)).getId());
    }

//    @CacheEvict(CacheNames.WORKING_CHATS)
    public void removeWorkingChat(Long chatId) {
        workingChats.remove(chatId);
        workingChatRepository.deleteById(chatId);
    }
}
