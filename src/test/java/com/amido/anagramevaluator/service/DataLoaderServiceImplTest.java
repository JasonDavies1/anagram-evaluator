package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.exception.EmptyWordlistException;
import com.amido.anagramevaluator.exception.WordlistNotFoundException;
import com.amido.anagramevaluator.model.Word;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class DataLoaderServiceImplTest {

    private final WordConverterService wordConverterService = mock(WordConverterService.class);
    private final DataLoaderServiceImpl dataLoaderService = new DataLoaderServiceImpl(wordConverterService);

    @Test
    public void givenFilePathDoesNotLeadToFile_WhenLoadingWords_ThenExceptionIsThrown() {
        final String filePath = "nonsense";

        assertThatCode(() -> dataLoaderService.loadWordsFromFile(filePath))
                .isInstanceOf(WordlistNotFoundException.class)
                .hasMessage("Wordlist not found at location: nonsense");
    }

    @Test
    public void givenFilePathLeadsToFile_WhenLoadingWords_ThenAllWordsWithinTheFileAreReturned() {
        final String filePath = "src/test/resources/example_words.txt";

        final List<Word> result = dataLoaderService.loadWordsFromFile(filePath);

        assertThat(result.size())
                .isEqualTo(7);
    }

    @Test
    public void givenFilePathLeadsToFileWithSevenWords_WhenLoadingWords_ThenStringWordsWillBeConvertedSevenTimes() {
        final String filePath = "src/test/resources/example_words.txt";

        dataLoaderService.loadWordsFromFile(filePath);

        then(wordConverterService)
                .should(times(7))
                .convertStringToWord(anyString());
    }

    @Test
    public void givenFilePathLeadsToFileWithNoContent_WhenLoadingWords_ThenExceptionIsThrown() {
        final String filePath = "src/test/resources/empty_wordlist.txt";

        assertThatCode(() -> dataLoaderService.loadWordsFromFile(filePath))
                .isInstanceOf(EmptyWordlistException.class)
                .hasMessage("The wordlist found at location " + filePath + " contained no content");
    }

    @Test
    public void givenFilePathLeadsToFileWithOnlyBlankLines_WhenLoadingWords_ThenExceptionIsThrown() {
        final String filePath = "src/test/resources/wordlist_with_only_blank_lines.txt";

        assertThatCode(() -> dataLoaderService.loadWordsFromFile(filePath))
                .isInstanceOf(EmptyWordlistException.class)
                .hasMessage("The wordlist found at location " + filePath + " contained no content");
    }

}