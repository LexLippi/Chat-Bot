package chat_bot.game.board_game.board_levels;

import chat_bot.game.board_game.BoardData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Hard extends BoardLevel {
    public Hard() {
        super();
    }

    @Override
    public HashMap<String, Double> getSortedData() {
        var pairs = new BoardData().getWords().values();
        var dict = new HashMap<String, Double>();
        for (var a: pairs)
            dict.putAll(a);
        var list = new ArrayList<>(dict.entrySet());
        list.sort(Comparator.comparingDouble(Map.Entry::getValue));
        var count = Integer.divideUnsigned(dict.size(), 3);
        var resList = list.subList(0, count);
        var res = new HashMap<String, Double>();
        for (var el : resList) {
            res.put(el.getKey(), el.getValue());
        }
        return res;
    }
}
