package com.npospolita.rbkchbot.repo;

import com.npospolita.rbkchbot.domain.WorkingChat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkingChatRepository extends CrudRepository<WorkingChat, Long> {

    List<WorkingChat> findAllByTagIsIn(List<String> tag);

}
