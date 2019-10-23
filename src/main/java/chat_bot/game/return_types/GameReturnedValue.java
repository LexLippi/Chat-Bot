package chat_bot.game.return_types;

public final class GameReturnedValue {
    private GameExitType type;
    private String message;

    public GameReturnedValue(GameExitType type, String message) {
        this.type = type;
        this.message = message;
    }

    public GameExitType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
