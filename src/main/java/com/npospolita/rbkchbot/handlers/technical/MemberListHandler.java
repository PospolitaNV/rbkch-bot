package com.npospolita.rbkchbot.handlers.technical;

import com.npospolita.rbkchbot.handlers.CommonMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.npospolita.rbkchbot.service.MembershipService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class MemberListHandler extends CommonMessageHandler {

    private final MembershipService membershipService;

    @Override
    public Result handle(Update update) {
        if (update.message().leftChatMember() != null) {
            membershipService.removeMembership(update.message().leftChatMember(), update.message().chat().id());
        }
        if (update.message().newChatMembers() != null) {
            for (User user : update.message().newChatMembers()) {
                membershipService.addMembership(user, update.message().chat().id());
            }
        }
        if (update.message().from() != null) {
            membershipService.addMembership(update.message().from(), update.message().chat().id());
        }
        return Result.CONTINUE;
    }
}
