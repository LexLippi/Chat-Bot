package chat_bot;

public class TelegramApi implements Api {

    private String id;
    private Telegram telegram;

    public TelegramApi(String chatID, Telegram telegram){
        id = chatID;
        this.telegram = telegram;
    }

    @Override
    public String in(){
        return telegram.getAnswer(id);
    }

    @Override
    public void out(String massage) {
        telegram.sendMsg(id, massage);
    }
}
