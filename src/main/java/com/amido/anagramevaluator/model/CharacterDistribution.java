package com.amido.anagramevaluator.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
public class CharacterDistribution {
    private Map<Character, Integer> characterDistribution;

    public static CharacterDistribution fromStringWord(final String word){
        final CharacterDistribution characterDistribution = new CharacterDistribution();
        final Map<Character, Integer> characterDistributionMap = new HashMap<>();
        for (final Character c : word.toCharArray()) {
            Optional.ofNullable(characterDistributionMap.get(c))
                    .ifPresentOrElse(
                            occurrences -> characterDistributionMap.put(c, occurrences + 1),
                            () -> characterDistributionMap.put(c, 1)
                    );
        }
        characterDistribution.setCharacterDistribution(characterDistributionMap);
        return characterDistribution;
    }
}
