package chat_bot.game.city_game;

import chat_bot.Api;
import chat_bot.game.IGame;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;

import java.util.Random;

public class CityMultiplayerGame implements IGame {
    private CityGameMultiplayerAnswerChecker answerChecker;
    private Api currentPlayer;
    private Api invitedApi;
    private Api otherPlayer;
    private boolean gameWasStarted = false;
    private boolean invisionWasSent = false;
    private String preivousCity = null;

    public CityMultiplayerGame(Data data){
        answerChecker = new CityGameMultiplayerAnswerChecker(data);
    }

    @Override
    public GameReturnedValue process(String answer, Api api) {
        var lowerAnswer = answer.toLowerCase();
        if (!gameWasStarted && !invisionWasSent){
            invisionWasSent = api.invite(answer);
            if (invisionWasSent) {
                return new GameReturnedValue(null, "приглашение было отправлено");
            }
            return new GameReturnedValue(null, "что-то пошло не так");
        }
        if (!gameWasStarted){
            if (lowerAnswer.equals("сдаюсь") || lowerAnswer.equals("стоп")){
                invitedApi.cancelInvision();
                return new GameReturnedValue(GameExitType.GAME_INTERRUPTED);
            }
            return new GameReturnedValue(null, "второй игрок еще не подключился, подождите немного");
        }
        if (lowerAnswer.equals("сдаюсь")) {
            Api winner;
            if (api == currentPlayer) {
                winner = otherPlayer;
                currentPlayer = null;
                switchPlayers();
            }
            else{
                winner = currentPlayer;
                otherPlayer = null;
            }
            winner.out("твой противник сдался");
            winner.out("ты победил");
            winner.out("введи что-нибудь, чтобы закончить игру");
            invitedApi.cancelInvision();
            return new GameReturnedValue(GameExitType.PLAYER_LOOSE, "ничего, в другой раз повезет");
        }
        if (api == otherPlayer)
            return new GameReturnedValue(null, "похоже, сейчас ход твоего противника");
        if (otherPlayer == null)
            return new GameReturnedValue(GameExitType.PLAYER_WIN );
        var answerType = answerChecker.isStepCorrect(preivousCity, lowerAnswer);
        switch (answerType) {
            case CORRECT_STEP:
                otherPlayer.out("твой противник говорит:");
                otherPlayer.out(answer);
                otherPlayer.out("твой ход");
                preivousCity = lowerAnswer;
                switchPlayers();
                return new GameReturnedValue(null, "принято!");
            case INCORRECT_LETTER:
                return new GameReturnedValue(null, "первая буква вообще не та!");
            case USED_CITY:
                return new GameReturnedValue(null, "этот город уже был");
            case INCORRECT_CITY:
                return new GameReturnedValue(null, "я не знаю такого города :(");
            default:
                throw new IllegalArgumentException("incorrect MultiplayerStepResult value");
        }
    }

    private void switchPlayers(){
        var other = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = other;
    }

    @Override
    public GameReturnedValue startGame(Api api) {
        if (currentPlayer == null) {
            currentPlayer = api;
            invitedApi = api;
            return new GameReturnedValue(null, "вы успешно подключились к игре, " +
                    "чтобы пригласить своего друга введите его username");
        }
        if (otherPlayer == null){
            otherPlayer = api;
            var random = new Random();
            var value = random.nextInt(2);
            if (value == 0)
                switchPlayers();
            gameWasStarted = true;
            if (api == currentPlayer) {
                otherPlayer.out("второй игрок подключился, он ходит первым");
                return new GameReturnedValue(null, "ты успешно подключился к игре", "твой ход");
            }
            else{
                currentPlayer.out("второй игрок подключился, ты ходишь первым");
                return new GameReturnedValue(null, "ты успешно подключился к игре", "твой противник ходит первым");
            }
        }
        return new GameReturnedValue(GameExitType.GAME_INTERRUPTED, "похоже, в этой игре уже слишком много игроков");
    }
}
