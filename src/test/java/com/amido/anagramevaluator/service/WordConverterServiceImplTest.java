package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.Word;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WordConverterServiceImplTest {

    private final WordConverterServiceImpl wordConverterService = new WordConverterServiceImpl();

    @Test
    public void givenLowercaseStringIsToBeConverted_WhenConvertingToWordModel_ThenOriginalWordIsPresentInModel() {
        final String wordToConvert = "banana";

        final Word result = wordConverterService.convertStringToWord(wordToConvert);

        assertThat(result.getWord())
                .isEqualTo(wordToConvert);
    }

    @Test
    public void givenLowercaseStringIsToBeConverted_WhenConvertingToWordModel_ThenCharacterOccurrencesAreMappedCorrectly() {
        final String wordToConvert = "banana";

        final Word result = wordConverterService.convertStringToWord(wordToConvert);

        assertThat(result.getCharacterOccurrences())
                .satisfies(map -> {
                    assertThat(map.get('b')).isEqualTo(1);
                    assertThat(map.get('a')).isEqualTo(3);
                    assertThat(map.get('n')).isEqualTo(2);
                });
    }

    @Test
    public void givenAlphanumericStringIsToBeConverted_WhenConvertingToWordModel_ThenResultingWordModelRespectsCasing() {
        final String alphanumericWordToConvert = "B4nana";

        final Word result = wordConverterService.convertStringToWord(alphanumericWordToConvert);

        assertThat(result.getWord())
                .isEqualTo(alphanumericWordToConvert);
        assertThat(result.getCharacterOccurrences())
                .satisfies(map -> {
                    assertThat(map.get('B')).isEqualTo(1);
                    assertThat(map.get('4')).isEqualTo(1);
                    assertThat(map.get('n')).isEqualTo(2);
                    assertThat(map.get('a')).isEqualTo(2);
                });
    }

}