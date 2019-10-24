package chat_bot.game.levels;

import chat_bot.Data;
import chat_bot.game.return_types.CityAnswerType;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;

import java.util.ArrayList;
import java.util.Random;

abstract public class GameLevel implements DifficultLevel {
    protected Data data;
    protected Character waitingLetter = null;
    protected Integer step_counter;
    private Random rnd = new Random();

    abstract public String computeCity(Character lastLetter);

    public GameReturnedValue processingUserCourse(String inputString) throws IllegalStateException{
        if (inputString.compareTo("стоп") == 0) {
            return new GameReturnedValue(GameExitType.GAME_INTERRUPTED, "Гена говорит: приходи еще!");
        }
        else if (inputString.compareTo("сдаюсь") == 0) {
            return new GameReturnedValue(GameExitType.PLAYER_LOOSE,
                    "Гена говорит: ничего, в другой раз повезет!");
        }
        var answer = checkAnswer(inputString);
        switch(answer)
        {
            case INCORRECT_INPUT:
                return new GameReturnedValue(null, "Гена говорит: врешь, не уйдешь!");
            case INCORRECT_CITY:
                return new GameReturnedValue(null,
                        "Гена говорит: я не знаю такого города, попробуйте снова");
            case CORRECT_INPUT:
                return getBotCourse(inputString);
            default:
                throw new IllegalStateException("incorrect CityAnswerType");
        }
    }

    public GameReturnedValue getBotCourse() {
        var cities = new ArrayList<String>();
        for (var i = 'А'; i <= 'Я'; ++i) {
            if (!data.getStopLetters().contains(i)) {
                var result = computeCity(i);
                if (result != null) {
                    cities.add(result);
                }
            }
        }
        if (!data.getStopLetters().contains('Ё')) {
            var result = computeCity('Ё');
            if (result != null) {
                cities.add(result);
            }
        }
        if (cities.isEmpty()) {
            return new GameReturnedValue(GameExitType.PLAYER_WIN, "Гена говорит: я проиграл :(");
        }
        var city = getRandomListElement(cities);
        updateGameFields(city);
        return new GameReturnedValue(null, "Гена говорит: " + city);
    }

    public Character getCityLastLetter(String city) {
        var i = 1;
        var currentLastLetter = city.toUpperCase().charAt(city.length() - i);
        while (data.getStopLetters().contains(currentLastLetter))
        {
            ++i;
            currentLastLetter = city.toUpperCase().charAt(city.length() - i);
        }
        return currentLastLetter;
    }

    public Boolean isStepCounterEmpty() {
        return step_counter == 0;
    }

    protected String getRandomListElement(ArrayList<String> list) {
        var index = rnd.nextInt(list.size());
        return list.get(index);
    }

    private GameReturnedValue getBotCourse(String userCity)
    {
        if (isStepCounterEmpty()) {
            return new GameReturnedValue(GameExitType.PLAYER_WIN, "Гена говорит: я проиграл :(");
        }
        var userLastLetter = getCityLastLetter(userCity);
        var resultCity = computeCity(userLastLetter);
        if (resultCity == null) {
            return new GameReturnedValue(GameExitType.PLAYER_WIN, "Гена говорит: я проиграл :(");
        }
        updateGameFields(resultCity);
        return new GameReturnedValue(null, "Гена говорит: " + resultCity);
    }

    private void updateGameFields(String resultCity) {
        var lastLetter = getCityLastLetter(resultCity);
        incStepCounter();
        waitingLetter = lastLetter;
        data.updateStatistics(lastLetter);
        data.getCities().get(lastLetter).keySet().remove(resultCity);
    }

    private CityAnswerType checkAnswer(String city) {
        var firstLetter = city.toUpperCase().charAt(0);
        var yourCity = firstLetter + city.toLowerCase().substring(1);
        if (waitingLetter != null && waitingLetter != firstLetter)
            return CityAnswerType.INCORRECT_INPUT;
        if (data.getCities().containsKey(firstLetter) && data.getCities().get(firstLetter).containsKey(yourCity))
        {
            data.getCities().get(firstLetter).remove(yourCity);
            data.updateStatistics(firstLetter);
            return CityAnswerType.CORRECT_INPUT;
        }
        return CityAnswerType.INCORRECT_CITY;
    }

    private void incStepCounter() {
        if (step_counter > 0) {
            step_counter--;
        }
    }
}
