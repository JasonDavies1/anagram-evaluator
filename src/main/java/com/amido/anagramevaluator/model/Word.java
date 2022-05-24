package com.amido.anagramevaluator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Word {
    private final String word;
    private final CharacterDistribution characterDistribution;
//    private final Map<Character, Integer> characterOccurrences;
}
