package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.Word;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AnagramServiceImplTest {

    private static final String FILE_PATH = "/path/to/file.txt";
    private final DataLoaderService dataLoaderService = mock(DataLoaderService.class);
    private final AnagramServiceImpl anagramService = new AnagramServiceImpl(dataLoaderService);

    @Test
    public void givenNoWordsAreLoadedFromFile_WhenEvaluatingAnagrams_ThenResultingListShouldContainErrorMessage() {
        given(dataLoaderService.loadWordsFromFile(FILE_PATH))
                .willReturn(new ArrayList<>());

        final List<String> result = anagramService.findAnagramsInWordlist(FILE_PATH);

        assertThat(result)
                .singleElement()
                .satisfies(element -> assertThat(element).isEqualTo("No words loaded from supplied file - is the file empty?"));
    }

    @Test
    public void givenSingularWordIsLoadedFromFile_WhenEvaluatingAnagrams_ThenResultingListShouldBeContainNoValues(){
        given(dataLoaderService.loadWordsFromFile(FILE_PATH))
                .willReturn(List.of(
                        new Word(
                                "abc",
                                Map.of(
                                        'a', 1,
                                        'b', 1,
                                        'c', 1
                                )
                        )
                ));

        final List<String> result = anagramService.findAnagramsInWordlist(FILE_PATH);

        assertThat(result).isEmpty();
    }

    @Test
    public void givenTwoWordsAreLoadedThatAreNotAnagramsFromFile_WhenEvaluatingAnagrams_ThenResultingListShouldContainNoValues(){
        given(dataLoaderService.loadWordsFromFile(FILE_PATH))
                .willReturn(List.of(
                        new Word(
                                "abc",
                                Map.of(
                                        'a', 1,
                                        'b', 1,
                                        'c', 1
                                )
                        ),
                        new Word(
                                "def",
                                Map.of(
                                        'd', 1,
                                        'e', 1,
                                        'f', 1
                                )
                        )
                ));

        final List<String> result = anagramService.findAnagramsInWordlist(FILE_PATH);

        assertThat(result).isEmpty();
    }

    @Test
    public void givenTwoWordsAreLoadedThatAreAnagramsFromFile_WhenEvaluatingAnagrams_ThenResultingListShouldContainAStringWithBothWords(){
        given(dataLoaderService.loadWordsFromFile(FILE_PATH))
                .willReturn(List.of(
                        new Word(
                                "abc",
                                Map.of(
                                        'a', 1,
                                        'b', 1,
                                        'c', 1
                                )
                        ),
                        new Word(
                                "cba",
                                Map.of(
                                        'c', 1,
                                        'b', 1,
                                        'a', 1
                                )
                        )
                ));

        final List<String> result = anagramService.findAnagramsInWordlist(FILE_PATH);

        assertThat(result)
                .singleElement()
                .satisfies(value -> assertThat(value).contains("abc", "cba", ","));
    }
}