package chat_bot.game.levels;

import chat_bot.Api;
import chat_bot.Data;
import chat_bot.game.levels.DifficultLevel;
import chat_bot.game.return_types.CityAnswerType;
import chat_bot.game.return_types.GameExitType;

import java.util.ArrayList;
import java.util.Random;

abstract public class GameLevel implements DifficultLevel {
    protected Data data;
    protected Character waitingLetter = null;
    protected Integer step_counter;
    private Random rnd = new Random();
    protected Api api;

    abstract public String computeCity(Character lastLetter);

    public GameExitType processingUserCourse() throws IllegalStateException{
        api.out("Твой ход: ");
        try {
            var inputString = api.in().toLowerCase();
            if (inputString.compareTo("стоп") == 0) {
                api.out("Гена говорит: приходи еще!");
                return GameExitType.GAME_INTERRUPTED;
            }
            else if (inputString.compareTo("сдаюсь") == 0) {
                api.out("Гена говорит: ничего, в другой раз повезет!");
                return GameExitType.PLAYER_LOOSE;
            }
            var answer = checkAnswer(inputString);
            switch(answer)
            {
                case INCORRECT_INPUT:
                    api.out("Гена говорит: врешь, не уйдешь!");
                    break;
                case INCORRECT_CITY:
                    api.out("Гена говорит: я не знаю такого города, попробуйте снова");
                    break;
                case CORRECT_INPUT:
                    var result = getBotCourse(inputString);
                    if (result != null) {
                        return result;
                    }
                    break;
                default:
                    throw new IllegalStateException("incorrect CityAnswerType");
            }
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }

    public GameExitType getBotFirstCourse() {
        var cities = new ArrayList<String>();
        // think about letter Ё
        for (var i = 'А'; i <= 'Я'; ++i) {
            if (!data.stopLetters.contains(i)) {
                var result = computeCity(i);
                if (result != null) {
                    cities.add(result);
                }
            }
        }
        if (cities.isEmpty()) {
            api.out("Гена говорит: я проиграл :(");
            return GameExitType.PLAYER_WIN;
        }
        var city = getRandomListElement(cities);
        System.out.println(city);
        incStepCounter();
        api.out("Гена говорит: " + city);
        return null;
    }

    public Character getCityLastLetter(String city) {
        var i = 1;
        var currentLastLetter = city.toUpperCase().charAt(city.length() - i);
        while (data.stopLetters.contains(currentLastLetter))
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

    private GameExitType getBotCourse(String userCity)
    {
        if (isStepCounterEmpty()) {
            api.out("Гена говорит: я проиграл :(");
            return GameExitType.PLAYER_WIN;
        }
        var lastLetter = getCityLastLetter(userCity);
        var resultCity = computeCity(lastLetter);
        incStepCounter();
        if (resultCity == null) {
            api.out("Гена говорит: я проиграл :(");
            return GameExitType.PLAYER_WIN;
        }
        api.out("Гена говорит: " + resultCity);
        return null;
    }

    private CityAnswerType checkAnswer(String city) {
        var firstLetter = city.toUpperCase().charAt(0);
        var yourCity = firstLetter + city.toLowerCase().substring(1);
        if (waitingLetter != null && waitingLetter != firstLetter)
            return CityAnswerType.INCORRECT_INPUT;
        if (data.cities.containsKey(firstLetter) && data.cities.get(firstLetter).contains(yourCity))
        {
            data.cities.get(firstLetter).remove(yourCity);
            data.countCities.put(firstLetter, data.countCities.get(firstLetter) - 1);
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
