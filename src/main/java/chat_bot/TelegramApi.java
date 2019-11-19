package chat_bot;

import java.util.ArrayList;

public class TelegramApi implements Api {

    private String id;
    private Telegram telegram;

    public TelegramApi(String chatID, Telegram telegram) {
        id = chatID;
        this.telegram = telegram;
    }

    @Override
    public void out(String massage) {
        telegram.sendMsg(id, massage);
    }

    @Override
    public void outkeyboard(ArrayList buttons, String message) {
        telegram.sendInlineKeyBoardMessage(Long.parseLong(id), buttons, message);
    }
}
