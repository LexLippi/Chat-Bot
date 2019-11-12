package chat_bot;

import chat_bot.game.GameFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class Telegram extends TelegramLongPollingBot {

    private HashMap<String, ChatBot> bots = new HashMap<>();
    private GameFactory gameFactory;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        var botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new Telegram());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public Telegram(){
        gameFactory = new GameFactory();
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
            System.out.println(id + " " + msg);
            switch (msg.getText()) {
                case "/start":
                    registerNewBot(id);
                    //sendKeyboard(update.getMessage().getChatId());
                    break;
                case "/stop":
                    deleteBot(id);
                    break;
                default:
                    if (!bots.containsKey(id)){
                        sendMsg(id, "для начала напишите /start");
                    }
                    else{
                        //answers.get(id).add(msg.getText());
                        bots.get(id).process(msg.getText());
                    }
            }
        }
        else if (update.hasCallbackQuery()){
            var a = update.getCallbackQuery();
            var id = a.getMessage().getChatId().toString();
            var data = a.getData();
            System.out.println(id + " " + data);
            bots.get(id).process(data);
            /*final AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(update.getCallbackQuery().getId());
            answer.setText("You've clicked at the button: " + update.getCallbackQuery().getData());
            answer.setShowAlert(true);
            try {
                execute(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }*/
        }
    }

    private void deleteBot(String id){
        if (!bots.containsKey(id)){
            return;
        }
        bots.get(id).process("пока");
        bots.remove(id);
    }

    public void sendInlineKeyBoardMessage(Long chatId, List<String> buttons, String message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        for (var button: buttons){
            keyboardFirstRow.add(button);
        }
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(message).setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        //return new SendMessage().setChatId(chatId).setText(message).setReplyMarkup(replyKeyboardMarkup);

        /*InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (var button: buttons){
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(button);
            inlineKeyboardButton.setCallbackData(button);
            keyboardButtonsRow.add(inlineKeyboardButton);
        }
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setText(message).setReplyMarkup(inlineKeyboardMarkup);*/
    }

    private void registerNewBot(String id){
        if (bots.containsKey(id)){
            bots.get(id).start();
        }
        else {
            var api = new TelegramApi(id, this);
            var bot = new ChatBot(api, gameFactory);
            bots.put(id, bot);
        }
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
