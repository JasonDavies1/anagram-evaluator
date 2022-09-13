package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.CharacterDistribution;
import com.amido.anagramevaluator.model.Word;
import org.springframework.stereotype.Service;

@Service
public class WordConverterServiceImpl implements WordConverterService {
    @Override
    public Word convertStringToWord(final String word) {
        return new Word(word, CharacterDistribution.fromStringWord(word));
    }
}
