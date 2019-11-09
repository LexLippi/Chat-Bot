package chat_bot.game.board_game;

public class BoardCell {
    private Character symbol = null;
    private boolean canBeHorizontal = true;
    private boolean canBeVertical = true;

    public char getCharacter(){
        return symbol;
    }

    public boolean CanPlace(Character symbol, boolean isHorizotal){
        var symbolFit = true;
        if (this.symbol != null && !this.symbol.equals(symbol)) {
            symbolFit = false;
        }
        boolean directionFit;
        if (isHorizotal){
            directionFit = canBeHorizontal;
        }
        else {
            directionFit = canBeVertical;
        }
        return symbolFit && directionFit;
    }

    public void Set(Character symbol, boolean isHorizontal){
        if (symbol == null)
            throw new IllegalArgumentException("can't set null as symbol");
        if (!CanPlace(symbol, isHorizontal))
            throw new IllegalArgumentException("can't place this symbol");
        this.symbol = symbol;
        if (isHorizontal){
            canBeHorizontal = false;
        }
        else {
            canBeVertical = false;
        }
    }
}
