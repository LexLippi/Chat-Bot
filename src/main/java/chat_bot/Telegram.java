package chat_bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.logging.BotLogger;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Telegram extends TelegramLongPollingBot {

    private HashMap<String, Queue<String>> answers = new HashMap<>();
    private HashMap<String, ChatBot> bots = new HashMap<>();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        var botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new Telegram());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String chatId, String text){
        var sendMsg = new SendMessage();
        sendMsg.enableMarkdown(true);
        sendMsg.setChatId(chatId);
        sendMsg.setText(text);
        try{
            sendMessage(sendMsg);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        if (msg != null && msg.hasText()){
            var id = msg.getChatId().toString();
            sendMsg(id, msg.getText());
            switch (msg.getText()) {
                case "/start":
                    registerNewBot(id);
                    break;
                case "/stop":
                    deleteBot(id);
                    break;
                default:
                    if (!answers.containsKey(id)){
                        sendMsg(id, "для начала напишите /start");
                    }
                    else{
                        answers.get(id).add(msg.getText());
                    }
            }
        }
    }

    public String getAnswer(String id){
        String answer = null;
        while (answer == null){
            answer = answers.get(id).poll();
        }
        return answer;
    }

    private void deleteBot(String id){
        if (!bots.containsKey(id)){
            return;
        }
        bots.get(id).exit("пока");
        bots.remove(id);
        answers.remove(id);
    }

    private void registerNewBot(String id){
        if (answers.containsKey(id)){
            bots.get(id).start();
        }
        answers.put(id, new LinkedList<String>());
        var api = new TelegramApi(id, this);
        var bot = new ChatBot(api);
        bot.start();
        bots.put(id, bot);
    }

    @Override
    public String getBotUsername() {
        return "StudyCityBot";
    }

    @Override
    public String getBotToken() {
        return "974444714:AAHGOtQW3Zmh2P0AonOJVOIfc6DGUI8HGKA";
    }
}
