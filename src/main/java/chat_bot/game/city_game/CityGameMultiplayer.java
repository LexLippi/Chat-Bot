package chat_bot.game.city_game;

import chat_bot.game.city_game.states.MultiplayerStepResult;

public class CityGameMultiplayer {
    private Data data;

    public CityGameMultiplayer() {
        data = new Data();
    }

    public MultiplayerStepResult isStepCorrect(String previousCity, String currentStep) {
        var firstLetter = getFirstLetter(currentStep);
        var lastLetter = getLastLetter(previousCity);
        if (data.getCities().get(firstLetter).containsKey(currentStep) && firstLetter.compareTo(lastLetter) == 0) {
            data.updateStatistics(firstLetter, currentStep);
            return MultiplayerStepResult.CORRECT_STEP;
        }
        else if (data.getCities().get(firstLetter).containsKey(currentStep)) {
            return MultiplayerStepResult.INCORRECT_LETTER;
        }
        else if (data.getUsedCities().contains(currentStep)) {
            return MultiplayerStepResult.USED_CITY;
        }
        else {
            return MultiplayerStepResult.INCORRECT_CITY;
        }
    }

    private Character getFirstLetter(String text) {
        if (text.length() > 0) {
            return text.toUpperCase().charAt(0);
        }
        throw new IllegalArgumentException("Text was empty");
    }

    private Character getLastLetter(String city) {
        var i = 1;
        var currentLastLetter = city.toUpperCase().charAt(city.length() - i);
        while (data.getStopLetters().contains(currentLastLetter))
        {
            ++i;
            currentLastLetter = city.toUpperCase().charAt(city.length() - i);
        }
        return currentLastLetter;
    }
}
