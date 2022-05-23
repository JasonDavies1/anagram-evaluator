package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnagramServiceImpl implements AnagramService {

    private final DataLoaderService dataLoaderService;

    @Override
    public List<String> findAnagramsInWordlist(final String filePath) {
        final List<Word> words = dataLoaderService.loadWordsFromFile(filePath);

        if (words.isEmpty()) {
            return Collections.singletonList("No words loaded from supplied file - is the file empty?");
        }

        final Map<Map<Character, Integer>, List<String>> anagrams = new HashMap<>();
        words.forEach(w -> Optional.ofNullable(anagrams.get(w.getCharacterOccurrences()))
                .ifPresentOrElse(
                        anagramList -> anagramList.add(w.getWord()),
                        () -> {
                            final List<String> initialAnagramList = new ArrayList<>();
                            initialAnagramList.add(w.getWord());
                            anagrams.put(w.getCharacterOccurrences(), initialAnagramList);
                        }
                ));

        return anagrams.values().stream()
                .filter(strings -> strings.size() > 1)
                .map(strings -> String.join(", ", strings))
                .collect(Collectors.toList());
    }
}
