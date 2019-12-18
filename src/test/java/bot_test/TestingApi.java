package bot_test;

import chat_bot.Api;

import java.util.ArrayList;

public class TestingApi implements Api {

    public ArrayList<String> answers = new ArrayList<>();

    @Override
    public void out(String massage) {
        answers.add(massage);
    }

    @Override
    public boolean invite(String name) {
        return false;
    }

    @Override
    public void cancelInvision(boolean broadcast) {
        return;
    }

    @Override
    public void outkeyboard(ArrayList buttons, String message) {
        //ignore
    }
}
