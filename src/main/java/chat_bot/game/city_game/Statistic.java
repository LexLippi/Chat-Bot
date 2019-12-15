package chat_bot.game.city_game;

import chat_bot.game.city_game.levels.GameLevel;

import java.util.HashMap;

public class Statistic {
    private Integer totalGames = 0;
    private Integer wins = 0;
    private Integer defeats = 0;
    private Integer interruptions = 0;

    public String getStatistic() {
        return "всего: " + totalGames
                + ", побед: " + wins
                + ", поражений: " + defeats
                + ", прерываний: " + interruptions;
    }

    public Integer getDefeats() {
        return defeats;
    }

    public Integer getTotalGames() {
        return totalGames;
    }

    public Integer getWins() {
        return wins;
    }

    public Integer getInterruptions() {
        return interruptions;
    }

    public void increaseDefeats() {
        this.defeats += 1;
    }

    public void increaseTotalGames() {
        this.totalGames += 1;
    }

    public void increaseWins() {
        this.wins += 1;
    }

    public void increaseInterruptions() {
        this.interruptions += 1;
    }
}
