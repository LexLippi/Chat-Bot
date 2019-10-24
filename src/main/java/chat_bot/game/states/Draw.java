package chat_bot.game.states;

import chat_bot.game.levels.GameLevel;
import chat_bot.game.return_types.GameReturnedValue;
import java.util.Random;

public class Draw extends State {
    public Draw() {
        this.nextState = new BotCourse();
    }

    @Override
    public GameReturnedValue processCommand(String inputString, GameLevel level) {
        var answer = "Ты победил, не ожидал от тебя такой прыти. Ходи!";
        if (!getDraw(inputString)) {
            answer = "Ха-ха. Ходить буду я!";
            var gameResult = level.getBotCourse();
            return new GameReturnedValue(gameResult.getType(), gameResult.getMessages()[0], answer);
        }
        return new GameReturnedValue(null, answer);
    }

    @Override
    public GameReturnedValue getIllegalGameLevel() {
        var answer = "Кажется, ты выбрал не орла или решку, а что-то посерьезнее... Попробуй повторить!";
        return new GameReturnedValue(null, answer);
    }

    private boolean getDraw(String inputString) {
        if (inputString.compareTo("орёл") == 0 || inputString.compareTo("решка") == 0) {
            return getDrawResult(inputString);
        }
        throw new IllegalArgumentException();
    }

    private boolean getDrawResult(String side) {
        var rnd = new Random();
        var result = rnd.nextInt(2);
        var resultSide = result == 0 ? "орёл" : "решка";
        return side.compareTo(resultSide) == 0;
    }
}
