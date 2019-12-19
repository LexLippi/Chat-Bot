package chat_bot.game.city_game.levels;

import chat_bot.game.city_game.Data;
import chat_bot.game.city_game.CityAnswerType;
import chat_bot.game.return_types.GameExitType;
import chat_bot.game.return_types.GameReturnedValue;
import java.util.ArrayList;
import java.util.Random;

abstract public class GameLevel implements DifficultLevel {
    protected Data data;
    protected Character waitingLetter = null;
    protected Integer step_counter;
    private Random rnd = new Random();
    protected Double minPercent;
    protected Double maxPercent;

    abstract public String computeCity(Character lastLetter);

    abstract public String getStrName();

    public GameReturnedValue processingUserCourse(String inputString) throws IllegalStateException{
        if (inputString.compareTo("стоп") == 0) {
            return new GameReturnedValue(GameExitType.GAME_INTERRUPTED, "Приходи еще!");
        }
        else if (inputString.compareTo("сдаюсь") == 0) {
            return new GameReturnedValue(GameExitType.PLAYER_LOOSE,
                    "Ничего, в другой раз повезет!");
        }
        var answer = checkAnswer(inputString);
        switch(answer)
        {
            case INCORRECT_INPUT:
                return new GameReturnedValue(null, "Врёшь, не уйдешь!");
            case INCORRECT_CITY:
                return new GameReturnedValue(null,
                        "Я не знаю такого города, попробуйте снова");
            case CORRECT_INPUT:
                return getBotCourse(inputString);
            case USED_CITY:
                return new GameReturnedValue(null, "Решил обмануть меня, бродяга?! Этот город уже был!");
            default:
                throw new IllegalStateException("incorrect CityAnswerType");
        }
    }

    public GameReturnedValue getBotCourse() {
        var cities = getCorrectCities();
        if (cities.isEmpty()) {
            return new GameReturnedValue(GameExitType.PLAYER_WIN, "Я проиграл :(");
        }
        var city = getRandomListElement(cities);
        var firstLetter = getFirstLetter(city);
        var resultCity = data.getCities().get(firstLetter).get(city).name;
        updateGameFields(city);
        return new GameReturnedValue(null, resultCity);
    }

    private ArrayList<String> getCorrectCities() {
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
        return cities;
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

    protected Integer getLetterCount() {
        return 33 - data.getStopLetters().size();
    }

    private GameReturnedValue getBotCourse(String userCity)
    {
        if (isStepCounterEmpty()) {
            return new GameReturnedValue(GameExitType.PLAYER_WIN, "Я проиграл :(");
        }
        var userLastLetter = getCityLastLetter(userCity);
        var resultCity = computeCity(userLastLetter);
        if (resultCity == null) {
            return new GameReturnedValue(GameExitType.PLAYER_WIN, "Я проиграл :(");
        }
        var firstLetter = getFirstLetter(resultCity);
        var city = data.getCities().get(firstLetter).get(resultCity).name;
        updateGameFields(resultCity);
        return new GameReturnedValue(null, city);
    }

    private void updateGameFields(String resultCity) {
        var lastLetter = getCityLastLetter(resultCity);
        incStepCounter();
        waitingLetter = lastLetter;
        data.updateStatistics(lastLetter, resultCity);;
    }

    private CityAnswerType checkAnswer(String city) {
        var firstLetter = getFirstLetter(city);
        if (isCityStartsOnIncorrectLetter(firstLetter)) {
            return CityAnswerType.INCORRECT_INPUT;
        }
        if (isCityCorrect(city))
        {
            data.updateStatistics(firstLetter, city);
            return CityAnswerType.CORRECT_INPUT;
        }
        if (isCityUsed(city)) {
            return CityAnswerType.USED_CITY;
        }
        return CityAnswerType.INCORRECT_CITY;
    }

    private Boolean isCityUsed(String city) {
        return data.getUsedCities().contains(city);
    }

    private void incStepCounter() {
        if (step_counter > 0) {
            step_counter--;
        }
    }

    private Character getFirstLetter(String city) {
        return city.toUpperCase().charAt(0);
    }

    private Boolean isCityStartsOnIncorrectLetter(Character firstLetter) {
        return waitingLetter != null && waitingLetter.compareTo(firstLetter) != 0;
    }

    private Boolean isCityCorrect(String city) {
        var firstLetter = getFirstLetter(city);
        return data.getCities().containsKey(firstLetter) && data.getCities().get(firstLetter).containsKey(city);
    }
}
