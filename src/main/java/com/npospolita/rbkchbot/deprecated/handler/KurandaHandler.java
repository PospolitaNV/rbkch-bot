package com.npospolita.rbkchbot.deprecated.handler;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class KurandaHandler implements Handler {

    private final TelegramApi api;

    @Value("${kuranda.id}")
    Integer kurandaId;

    @Override
    public boolean canHandle(Update update) {
        Message message = update.message();
        return message.from().id().equals(kurandaId) && message.text() != null
                && isPidorCommand(message.text().toLowerCase());
    }

    @Override
    public void handle(Update update) {
        api.sendMessage(update, "Сам пидор, держи частушку:\n\n" + getDitty(), ParseMode.HTML);
    }

    private boolean isPidorCommand(String text) {
        return text.startsWith("/") && notDefaultCommand(text)
        || text.length() < 30 && text.contains("пидор") && (text.contains("спартак") || text.contains("робот") || text.contains("ты"))
        || text.length() < 30 && text.contains("pidor") && (text.contains("spartak") || text.contains("robot") || text.contains("ti"));
    }

    private boolean notDefaultCommand(String text) {
        List<BotCommand> commands = api.getCommands();
        log.info("text: {}, commands: {}", text, commands);
        return commands.stream()
                .map(BotCommand::command)
                .noneMatch(command -> text.startsWith("/" + command));
    }

    private String getDitty() {
        return ditties.get(ThreadLocalRandom.current().nextInt(ditties.size()));
    }

    private static final List<String> ditties = List.of("Пидарасы, **баны,\nО**евшие мудланы.\nСуки, бля*и, говнюки,\nРас****яи, мудаки.\nЛетом, осенью, весной\nСверлят ночью за стеной.",
            "Все леса у нас дремучи,\nА болота тописты.\nДевки все у нас еб*чи,\nСисясты и жописты.",
            "На дворе туман стоит-\nНулевая видимость,\nПод горой мужик лежит-\nРусская недвижимость.",
            "Как у нашего забора два подкидыша лежат,\nОдному лет сорок восемь, а другому пятьдесят.",
            "Как на ветке на макушке, \nСоловей задул кукушке, \nТолько слышно на суку, \nЧирик, пи*дык, х*як, ку-ку",
            "Как на солнечной просЕке\nТанцевали гомосеки,\nОпушки.Опушки,\nВолосаты жОпушки",
            "Я е**ась с интеллигентом\nНочью на завалинке.\nПенис, девки, это х*й,\nтолько очень маленький.",
            "По деревне ходят утки,\nСеренькие крякают.\nМою милую е*у,\nТолько серьги брякают!",
            "Мне сегодня между ног\nКак-то очень весело.\nЭто милка мне на х**\nБубенцы навесила!",
            "Пароход плывет по Волге,\nНебо голубеется.\nДевки едут без билета -\nНа пи**у надеются.",
            "Я ли с милым не полажу?\nЛяжу с милым, полежу,\nНежно х*й ему поглажу,\nНежно в глазки погляжу\nЯ влюбилась в Михаила,\nОн большущий смех**ла.",
            "Люли-люли, трали-вали,\nЛяжки толстые у Вали,\nЛюли-люли, трали-вали,\nДо хера херов у Вали.",
            "Ну и мать твою етит,\nМаша в Боинге летит,\nЕдет Маша в Катманду,\nВезет туда свою ма*ду.",
            "Я приехала в колхоз\nИмени Мичурина.\nЗнала, в жопу от***ут...\nСловно сердце чуяло.",
            "Пришел к теще натощак\nТеща плавает во щах\nКак Венера, жопой вниз\nна пиз**е лавровый лист",
            "Не ходите девки замуж -\nничего хорошего:\nутром встанешь - груди набок,\nвся п*зда взъерошена.",
            "Часовые пояса\nсдвинули на глобусе -\nраньше х*й вставал в постели,\nа теперь - в автобусе.",
            "над селом х*йня летала\nсеребристого металла\nмного стало в наши дни\nнеопознанной ху*ни",
            "У моей милашки жопа\nЛучше не отыщется\nСтоит вечером похлопать\nДо утра колышется!",
            "Ах снег - снежок,\nБелая метелица.\nнахватался мандавошек,\nаж штаны шевелятся",
            "На базар пошла сноха\nИ купила петуха\nПосадила между ног\nОн понюхал и издох",
            "Вот надену пеньюар,\nЛягу на перину.\nКак меня не отъ**ать,\nТакую балерину!",
            "Я к марксизму приобщусь,\nЯ на Фурцевой женюсь.\nБуду тискать сиски я\nСамые марксиськия.",
            "Через реку перекинуто\nсукатое бревно\nменя миленький не любит -\nсука, падла, бл*дь, говно",
            "Уронил в п**ду часы я,\nТикают проклятые.\nЯ их х*ем завожу\nВ половину пятого.",
            "Моя милка вброд ходила\nЧерез речку Енисей.\nПолная пи*** набилась\nОкуней и карасей.",
            "Моя милка дорогая,\nЯ тебе советую:\nНикому ты не давай,\nЗалепи газетою.",
            "А мой милый - мильцанер,\nНе боится драки,\nПотому что у него\nПистолет на сраке.",
            "Мы под деревом сидели,\nЯ нюхала акацию,\nА милок изображал\nПод юбкою прострацию.",
            "Эх, миленок мой, Алеша!\nПосмотри на потолок!\nНе твои ли черны яйца\nТам котенок поволок?",
            "Горько плачут мандавошки\nВзрослые, малышки -\nПотеряли дом родной\nПри лобковой стрижке",
            "Как на нашем огороде\nВыросла вдруг *опа.\nЖирная мясистая,\nМестами волосистая.",
            "Я поеду в Гваделупу\nПоказать свою *алупу.\nПусть все ****и в Гваделупе\nЗнают о моей *алупе.",
            "С неба звездочка упала\nПрямо милому в штаны\nПусть бы все там разорвала,\nЛишь бы не было войны",
            "Я вчера навеселе\nДрал всех телок на селе.\nЗвали их Тамара, Даша,\nЗина, Дмитрий и Аркаша.",
            "Я на днях пошел в театр\nИ пихнул себе вибратор.\n**уеть, какие чувства\nДоставляет мне искусство.");
}
