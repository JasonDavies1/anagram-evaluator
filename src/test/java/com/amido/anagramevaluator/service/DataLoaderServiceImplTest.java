package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.Word;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class DataLoaderServiceImplTest {

    private final WordConverterService wordConverterService = mock(WordConverterService.class);
    private final DataLoaderServiceImpl dataLoaderService = new DataLoaderServiceImpl(wordConverterService);

    @Test
    public void givenFilePathDoesNotLeadToFile_WhenLoadingWords_ThenNoWordsAreReturned() {
        final String filePath = "nonsense";

        final List<Word> result = dataLoaderService.loadWordsFromFile(filePath);

        assertThat(result.isEmpty())
                .isTrue();
    }

    @Test
    public void givenFilePathLeadsToFile_WhenLoadingWords_ThenAllWordsWithinTheFileAreReturned() {
        final String filePath = "src/test/resources/example_words.txt";

        final List<Word> result = dataLoaderService.loadWordsFromFile(filePath);

        assertThat(result.size())
                .isEqualTo(7);
    }
    @Test
    public void givenFilePathLeadsToFileWithSevenWords_WhenLoadingWords_ThenStringWordsWillBeConvertedSevenTimes(){
        final String filePath = "src/test/resources/example_words.txt";

        dataLoaderService.loadWordsFromFile(filePath);

        then(wordConverterService)
                .should(times(7))
                .convertStringToWord(anyString());
    }

}