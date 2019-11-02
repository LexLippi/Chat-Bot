package chat_bot.game;

public interface IGameFactory {
    public IGame getGame(GameType type);
}
