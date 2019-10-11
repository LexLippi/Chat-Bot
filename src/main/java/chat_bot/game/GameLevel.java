package chat_bot.game;

import chat_bot.Data;

abstract class GameLevel implements DifficultLevel {
    protected Data data;
    protected Character waitingLetter = null;

    abstract public String computeCity(Character lastLetter);
}
