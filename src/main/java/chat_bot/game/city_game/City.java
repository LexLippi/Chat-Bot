package chat_bot.game.city_game;

public class City {
    public final String name;
    public final String nameInLowerCase;
    public final Integer population;

    public City(String name, Integer population)
    {
        this.name = name;
        this.nameInLowerCase = name.toLowerCase();
        this.population = population;
    }
}
