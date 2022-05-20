package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.Word;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WordConverterServiceImpl implements WordConverterService {
    @Override
    public Word convertStringToWord(final String word) {
        return new Word(word, getCharacterOccurrenceDistribution(word));
    }

    private Map<Character, Integer> getCharacterOccurrenceDistribution(final String word) {
        final Map<Character, Integer> characterOccurrences = new HashMap<>();
        for (final Character c : word.toCharArray()) {
            Optional.ofNullable(characterOccurrences.get(c))
                    .ifPresentOrElse(
                            occurrences -> characterOccurrences.put(c, occurrences + 1),
                            () -> characterOccurrences.put(c, 1)
                    );
        }
        return characterOccurrences;
    }
}
