package chat_bot.game.board_game;

import chat_bot.game.board_game.board_levels.BoardLevel;

import java.io.IOException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BoardData {
    private HashMap<Character, HashMap<String, Double>> words = new HashMap<>();
    private final Pattern patternAllWord = Pattern.compile("&nbsp;&nbsp;</font></td><td>(.+?)</tr>", Pattern.DOTALL);

    public BoardData() {
        getData();
    }

    public BoardData(HashMap<String, Double> wordsWithFrequency) {
        for (var wordWithFrequency : wordsWithFrequency.entrySet()) {
            var word = wordWithFrequency.getKey();
            var firstLetter = getFirstLetter(word);
            var frequency = wordWithFrequency.getValue();
            if (!this.words.containsKey(firstLetter)) {
                this.words.put(firstLetter, new HashMap<>());
            }
            this.words.get(firstLetter).put(word, frequency);
        }
    }

    public Boolean isDataContainsWord(String word) {
        if (words.containsKey(getFirstLetter(word))) {
            return words.get(getFirstLetter(word)).containsKey(word);
        }
        return false;
    }

    public HashMap<Character, HashMap<String, Double>> getWords() {
        return words;
    }

    private void getData() {
        var words = getWordsAndWordsFrequency();
        assert words != null;
        for (var word : words.keySet()) {
            var firstLetter = getFirstLetter(word);
            if (!this.words.containsKey(firstLetter)) {
                this.words.put(firstLetter, new HashMap<>());
            }
            this.words.get(firstLetter).put(word, words.get(word));
        }
    }

    private HashMap<String, Double> getWordsAndWordsFrequency() {
        try {
            var url = new URL("http://dict.ruslang.ru/freq.php?act=show&dic=freq_freq&title=%D7%E0%F1%F2%EE%F2%ED%FB%E9%20%F1%EF%E8%F1%EE%EA%20%EB%E5%EC%EC");
            var br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "cp1251"));
            String s;
            var str = new StringBuilder();
            while ((s = br.readLine()) != null)
                str.append(s);
            br.close();
            Matcher matcher = patternAllWord.matcher(str.toString());
            var res = new HashMap<String, Double>();
            while (matcher.find()) {
                var wordListWithFrequency = Arrays.stream(matcher.group(1).split("</td><td>"))
                        .distinct()
                        .collect(Collectors.toList());
                var format = NumberFormat.getInstance(Locale.UK);
                res.put(wordListWithFrequency.get(0), format.parse(wordListWithFrequency.get(2)).doubleValue());
            }
            return res;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Character getFirstLetter(String text) {
        if (text.length() > 0) {
            return text.charAt(0);
        }
        throw new IllegalArgumentException("Text was empty");
    }
}
