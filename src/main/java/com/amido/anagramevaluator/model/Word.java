package com.amido.anagramevaluator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Word {
    private final String word;
    private final Map<Character, Integer> characterOccurrences;
}
