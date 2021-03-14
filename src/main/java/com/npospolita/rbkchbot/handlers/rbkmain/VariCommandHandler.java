package com.npospolita.rbkchbot.handlers.rbkmain;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.constant.UserCommand;
import com.npospolita.rbkchbot.handlers.CommonTextMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class VariCommandHandler extends CommonTextMessageHandler {

    private static final String RANDOM_IMAGE_URL = "https://source.unsplash.com/random";
    private static final UserCommand command = UserCommand.VARI;
    private static final RestTemplate nonRedirectingRestTemplate =
            new RestTemplate( new SimpleClientHttpRequestFactory(){
        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod ) {
            connection.setInstanceFollowRedirects(false);
        }
    } );

    private final TelegramApi telegramApi;

    @Override
    public Result handle(Update update) {
        ResponseEntity<String> responseEntity = nonRedirectingRestTemplate.getForEntity(RANDOM_IMAGE_URL, String.class);
        URI location = responseEntity.getHeaders().getLocation();
        if (location != null) {
            telegramApi.sendImage(update, location.toString());
        }
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update) && update.message().text().startsWith(command.getCommand());
    }
}
