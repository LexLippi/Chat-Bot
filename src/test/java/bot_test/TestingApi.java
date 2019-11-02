package bot_test;

import chat_bot.Api;

import java.util.ArrayList;

public class TestingApi implements Api {

    public ArrayList<String> answers = new ArrayList<>();

    @Override
    public void out(String massage) {
        answers.add(massage);
    }
}
