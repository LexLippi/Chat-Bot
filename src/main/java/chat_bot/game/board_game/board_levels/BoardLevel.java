package chat_bot.game.board_game.board_levels;

import chat_bot.game.board_game.BoardData;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BoardLevel {
    protected BoardData data;

    public abstract HashMap<String, Double> sortData();

    public String[] getWords(int count) {
        if (count == 0) {
            return null;
        }
        var keySet = data.getWords().keySet();
        var mainWord = getMainWord(keySet);
        var resultWords = getBestWords(mainWord, keySet);
        if (count > resultWords.size() + 1) {
            throw new IllegalArgumentException("Count of words is too big");
        }
        var result = resultWords.stream().limit(count - 1).collect(Collectors.toList());
        result.add(mainWord);
        return result.toArray(String[]::new);
    }

    private ArrayList<String> getBestWords(String mainWord, Set<Character> keySet) {
        var distances = new HashMap<String, Long>();
        for (var key : keySet) {
            for (var word : data.getWords().get(key).keySet()) {
                if (word.compareTo(mainWord) != 0) {
                    distances.put(word, getDistanceBetweenWords(mainWord, word));
                }
            }
        }
        return sortDistances(distances);
    }

    private ArrayList<String> sortDistances(HashMap<String, Long> distances) {
        var entries = new ArrayList<Map.Entry<String, Long>>(distances.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        var sortStrings = new ArrayList<String>();
        for (var entry : entries) {
            sortStrings.add(entry.getKey());
        }
        return sortStrings;
    }

    private String getMainWord(Set<Character> keySet) {
        var mainKey = getRandomElementFromCollection(keySet);
        return getRandomElementFromCollection(data.getWords().get(mainKey).keySet());
    }

    private long getDistanceBetweenWords(String firstWord, String secondWord) {
        var firstSize = firstWord.length();
        var secondSize = secondWord.length();
        var countOfCommonSymbols = getCountOfCommonSymbols(firstWord, secondWord);
        return getJaccardIndex(firstSize, secondSize, countOfCommonSymbols);
    }

    private long getCountOfCommonSymbols(String firstWord, String secondWord) {
        var uniqueSize = firstWord.concat(secondWord).chars().distinct().count();
        return firstWord.length() + secondWord.length() - uniqueSize;
    }

    private long getJaccardIndex(long firstStringSize, long secondStringSize, long countOfCommonSymbols) {
        return countOfCommonSymbols / (firstStringSize + secondStringSize - countOfCommonSymbols);
    }

    private <T> T getRandomElementFromCollection(Collection<T> collection) {
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
}
