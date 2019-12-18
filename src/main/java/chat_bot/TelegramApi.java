package chat_bot;

import com.fasterxml.jackson.databind.JsonSerializer;
import jdk.dynalink.NamedOperation;

import java.util.ArrayList;

public class TelegramApi implements Api {

    private String id;
    private Telegram telegram;

    public TelegramApi(String chatID, Telegram telegram) {
        id = chatID;
        this.telegram = telegram;
    }

    public String getId(){
        return id;
    }

    @Override
    public void out(String massage) {
        telegram.sendMsg(id, massage);
    }

    @Override
    public boolean invite(String name) {
        return telegram.invite(name, this);
    }

    @Override
    public void cancelInvision() {
        telegram.cancelInvision(this);
    }

    @Override
    public void outkeyboard(ArrayList buttons, String message) {
        telegram.sendInlineKeyBoardMessage(Long.parseLong(id), buttons, message);
    }
}
