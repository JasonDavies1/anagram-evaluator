package com.amido.anagramevaluator.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class DataLoaderServiceImplTest {

    private DataLoaderServiceImpl dataLoaderService = new DataLoaderServiceImpl();

    @Test
    public void givenFilePathDoesNotLeadToFile_WhenLoadingWords_ThenNoWordsAreReturned() {
        final String filePath = "nonsense";

        final Optional<List<String>> result = dataLoaderService.loadWordsFromFile(filePath);

        assertThat(result).isEmpty();
    }

    @Test
    public void givenFilePathLeadsToFile_WhenLoadingWords_ThenAllWordsWithinTheFileAreReturned() {
        final String filePath = "src/test/resources/example_words.txt";

        final Optional<List<String>> result = dataLoaderService.loadWordsFromFile(filePath);

        assertThat(result).isNotEmpty();
        final List<String> words = result.get();
        assertThat(words.size())
                .isEqualTo(7);
    }

}