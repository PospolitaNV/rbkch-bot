package com.npospolita.rbkchbot.service;

import com.npospolita.rbkchbot.domain.TopicChat;
import com.npospolita.rbkchbot.domain.WorkingChat;
import com.npospolita.rbkchbot.repo.TopicChatRepository;
import com.npospolita.rbkchbot.repo.WorkingChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final WorkingChatRepository workingChatRepository;
    private final TopicChatRepository topicChatRepository;
    private Set<WorkingChat> workingChats = new HashSet<>();

    //todo add caching
    public boolean isInWorkingChat(Long chatId) {
        if (workingChats.isEmpty()) {
            workingChats.addAll(StreamUtils.createStreamFromIterator(workingChatRepository.findAll().iterator())
                    .collect(Collectors.toSet()));
        }

        return workingChats.contains(new WorkingChat(chatId, null));
    }

    public void addWorkingChat(Long chatId, String tag) {
        WorkingChat chat = new WorkingChat(chatId, tag);
        workingChats.add(workingChatRepository.save(chat));
    }

    public void removeWorkingChat(Long chatId) {
        workingChats.remove(new WorkingChat(chatId, null));
        workingChatRepository.deleteById(chatId);
    }

    public void addTopicChat(String description, String link) {
        topicChatRepository.save(new TopicChat(null, description, link));
    }

    public Collection<TopicChat> getTopicChatList() {
        return StreamUtils.createStreamFromIterator(topicChatRepository.findAll().iterator())
                .collect(Collectors.toList());
    }
}
