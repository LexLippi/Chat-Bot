package chat_bot.dataProviders;

import chat_bot.game.city_game.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataProviderFile implements IDataProvider {
    @Override
    public void getData(Data data) {
        try {
            var reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\main\\resources\\new_input.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                var firstLetter = line.charAt(0);
                var nameAndPopulation = line.split(" ");
                var name = nameAndPopulation[0];
                var population = 0;
                if (nameAndPopulation.length > 2) {
                    for (var i = 1; i < nameAndPopulation.length - 1; i++)
                        name += " " + nameAndPopulation[i];
                    population = Integer.parseInt(nameAndPopulation[nameAndPopulation.length - 1]);
                }
                else if (nameAndPopulation.length == 2) {
                    population = Integer.parseInt(nameAndPopulation[1]);
                }
                data.addCity(firstLetter, name, population);
                System.out.println(name + " " + population);
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
