package chat_bot;

import com.fasterxml.jackson.databind.JsonSerializer;
import jdk.dynalink.NamedOperation;

import java.util.ArrayList;

public class TelegramApi implements Api {

    private String id;
    private Telegram telegram;
    private IButtonsProvider provider;

    public TelegramApi(String chatID, Telegram telegram) {
        id = chatID;
        this.telegram = telegram;
    }

    public String getId(){
        return id;
    }

    @Override
    public void out(String massage) {
        ArrayList<String> buttons = null;
        if (provider != null) {
            buttons = provider.getButtons();
        }
        if (buttons == null) {
            telegram.sendMsg(id, massage);
        }
        else{
            outkeyboard(massage, buttons);
        }
    }

    @Override
    public boolean invite(String name) {
        return telegram.invite(name, this);
    }

    @Override
    public void cancelInvision(boolean broadcast) {
        telegram.cancelInvision(this, broadcast);
    }

    @Override
    public void setButtonsProvider(IButtonsProvider provider) {
        this.provider = provider;
    }

    @Override
    public void outkeyboard(String message, ArrayList... buttons ) {
        telegram.sendInlineKeyBoardMessage(Long.parseLong(id), message, buttons);
    }
}
