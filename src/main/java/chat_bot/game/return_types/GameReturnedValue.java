package chat_bot.game.return_types;

public final class GameReturnedValue {
    private GameExitType type;
    private String[] messages;

    public GameReturnedValue(GameExitType type, String ... message) {
        this.type = type;
        this.messages = message;
    }

    public GameExitType getType() {
        return type;
    }

    public String[] getMessages() {
        return messages;
    }
}
