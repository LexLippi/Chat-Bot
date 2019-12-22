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

import java.util.*;
import java.util.List;

public class Telegram extends TelegramLongPollingBot {

    private HashMap<String, ChatBot> bots = new HashMap<>();
    private HashMap<String, String> namesToIds = new HashMap<>();
    private HashMap<String, ArrayList<TelegramApi>> invites = new HashMap<>();
    private HashMap<String, TelegramApi> idToApi = new HashMap<>();
    private HashMap<TelegramApi, String> ApiToInvatedUsername = new HashMap<>();
    private GameFactory gameFactory;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        var botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new Telegram());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public Telegram(){
        gameFactory = new GameFactory();
    }

    public void sendMsg(String chatId, String text) {
        var sendMsg = new SendMessage();
        sendMsg.enableMarkdown(true);
        sendMsg.setChatId(chatId);
        sendMsg.setParseMode("Markdown");
        sendMsg.setText(text);
        try{
            sendMessage(sendMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean checkName(String name){
        return name.matches("@[A-Za-z0-9_]{5,100}");
    }

    public boolean invite(String name, TelegramApi api){
        if (!checkName(name))
            return false;
        if (namesToIds.containsKey(name)){
            var id = namesToIds.get(name);
            var bot = bots.get(id);
            System.out.println();
            ApiToInvatedUsername.put(api, name);
            bot.addWaitingBot(bots.get(api.getId()));

        }
        else{
            if (!invites.containsKey(name)){
                invites.put(name, new ArrayList<>());
            }
            invites.get(name).add(api);
            ApiToInvatedUsername.put(api, name);
        }
        return true;
    }

    public void cancelInvision(TelegramApi api, boolean tellIt){
        if (!ApiToInvatedUsername.containsKey(api)) {
            return;
        }
        var invitedName = ApiToInvatedUsername.get(api);
        ApiToInvatedUsername.remove(api);
        if (invites.containsKey(invitedName)) {
            invites.get(invitedName).remove(api);
        }
        var sender = bots.get(api.getId());
        if (namesToIds.containsKey(invitedName)){
            var invited = bots.get(namesToIds.get(invitedName));
            invited.cancelWaiting(sender, tellIt);
        }
    }

    @Override
    public synchronized void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        if (msg != null && msg.hasText()) {
            var id = msg.getChatId().toString();
            var username ="@"+msg.getFrom().getUserName();
            switch (msg.getText()) {
                case "/start":
                    registerNewBot(id, username);
                    break;
                case "/stop":
                    deleteBot(id, username);
                    break;
                default:
                    if (!bots.containsKey(id)) {
                        ArrayList buttons = new ArrayList() {
                            {
                                add("/start");
                            }};
                        sendInlineKeyBoardMessage(Long.parseLong(id), "для начала напишите /start", buttons);
                    }
                    else {
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
        }
    }

    private void deleteBot(String id, String userName) {
        if (!bots.containsKey(id)) {
            return;
        }
        bots.get(id).finish();
        bots.remove(id);
        namesToIds.remove(userName);
        var api = idToApi.get(id);
        idToApi.remove(id);
        cancelInvision(api, true);
    }

    public void sendInlineKeyBoardMessage(Long chatId, String message, List<String> ... lines) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        for (var buttons: lines) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (var button: buttons){
                keyboardRow.add(button);
            }
            keyboard.add(keyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
        SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(message).setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void registerNewBot(String id, String userName){
        System.out.println(userName);
        if (bots.containsKey(id)){
            bots.get(id).start();
        }
        else {
            var api = new TelegramApi(id, this);
            var bot = new ChatBot(api, gameFactory);
            if (invites.containsKey(userName)){
                for (var a : invites.get(userName)){
                    var newId = a.getId();
                    var newBot = bots.get(newId);
                    bot.addWaitingBot(newBot);
                }
            }
            bots.put(id, bot);
            namesToIds.put(userName, id);
            idToApi.put(id, api);
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
