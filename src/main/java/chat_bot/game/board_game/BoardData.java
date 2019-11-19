package chat_bot.game.board_game;

import java.io.IOException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardData {
    private HashMap<Character, ArrayList<String>> words = new HashMap<>();
    private final Pattern patternAllWord = Pattern.compile("");
    private final Pattern patternWord = Pattern.compile("");
    private final String[] letters = new String[]{"a", "b", "v", "g", "d", "je", "zh", "z",
            "i", "j", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u",
            "f", "x", "c", "ch", "sh", "sch", "je", "ju", "ja", "jer"};

    public BoardData() {
        initialize();
        getData();
    }

    public BoardData(String[] words) {
        for (var word: words) {
            var firstLetter = getFirstLetter(word);
            if (!this.words.containsKey(firstLetter)) {
                this.words.put(getFirstLetter(word), new ArrayList<>());
            }
            this.words.get(firstLetter).add(word);
        }
    }

    public String[] getWords(int count) {
        var keySet = this.words.keySet();
        var mainWord = getMainWord(keySet);
        var result = getBestWords(mainWord, keySet).stream().limit(count - 1).collect(Collectors.toList());
        result.add(mainWord);
        return (String[]) result.toArray();
    }

    private void initialize() {
        for (var i = 'А'; i <= 'Я'; ++i) {
            this.words.put(i, new ArrayList<>());
        }
        this.words.put('Ё', new ArrayList<>());
    }

    private void getData() {
        var threadPool = Executors.newFixedThreadPool(letters.length);
        List<Future<Integer>> futures = new ArrayList<>();
        for (var letter: letters) {
            final String l = letter;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> {
                                var words = getWords("http://www.slovorod.ru/dic-dal/dal-" + l + ".htm");
                                assert words != null;
                                for (var word : words) {
                                    this.words.get(getFirstLetter(word)).add(word);
                                    System.out.println(word);
                                }
                                return null;
                            },
                            threadPool
                    ));
        }
        for (Future<Integer> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }

    private String[] getWords(String site) {
        try{
            var url = new URL(site);
            var br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String s;
            var str = new StringBuilder();
            while ((s = br.readLine()) != null)
                str.append(s);
            br.close();
            Matcher matcher = patternAllWord.matcher(str.toString());
            var res = matcher.group(1).replace("</tr>", ",");
            return patternWord.matcher(res).replaceAll("").split(",");
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<String> getBestWords(String mainWord, Set<Character> keySet) {
        var distances = new HashMap<String, Long>();
        for (var key: keySet) {
            for (var word: this.words.get(key)) {
                if (word.compareTo(mainWord) != 0) {
                    distances.put(word, getDistanceBetweenWords(mainWord, word));
                }
            }
        }
        return sortDistances(distances);
    }

    private ArrayList<String> sortDistances(HashMap<String, Long> distances) {
        var entries = new ArrayList<>(distances.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        var sortStrings = new ArrayList<String>();
        for (var entry: entries) {
            sortStrings.add(entry.getKey());
        }
        return sortStrings;
    }

    private String getMainWord(Set<Character> keySet) {
        var mainKey = (char) getRandomElementFromCollection(keySet);
        return (String) getRandomElementFromCollection(this.words.get(mainKey));
    }

    private long getDistanceBetweenWords(String firstWord, String secondWord) {
        var firstSize = firstWord.length();
        var secondSize = secondWord.length();
        var countOfCommonSymbols = getCountOfCommonSymbols(firstWord, secondWord);
        return getJaccardIndex(firstSize, secondSize, countOfCommonSymbols);
    }

    private long getCountOfCommonSymbols(String firstWord, String secondWord) {
        var firstSymbols = firstWord.chars().distinct();
        var secondSymbols = secondWord.chars().distinct();
        var firstSize = firstSymbols.count();
        var secondSize = secondSymbols.count();
        var commonSize = IntStream.concat(firstSymbols, secondSymbols).count();
        return firstSize + secondSize - commonSize;
    }

    private long getJaccardIndex(long firstStringSize, long secondStringSize, long countOfCommonSymbols) {
        return countOfCommonSymbols / (firstStringSize + secondStringSize - countOfCommonSymbols);
    }

    private Object getRandomElementFromCollection(Collection collection) {
        var size = collection.size();
        var searchIndex = new Random().nextInt(size);
        var i = 0;
        for (var obj : collection) {
            if (i == searchIndex) {
                return obj;
            }
            ++i;
        }
        throw new IllegalArgumentException("Collection doesn't have random element");
    }

    private Character getFirstLetter(String text) {
        if (text.length() > 0) {
            return text.charAt(0);
        }
        throw new IllegalArgumentException("Text was empty");
    }
}
