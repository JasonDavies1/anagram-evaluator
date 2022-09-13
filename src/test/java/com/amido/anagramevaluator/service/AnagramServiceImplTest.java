package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.CharacterDistribution;
import com.amido.anagramevaluator.model.Word;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AnagramServiceImplTest {

    private static final String FILE_PATH = "/path/to/file.txt";
    private final DataLoaderService dataLoaderService = mock(DataLoaderService.class);
    private final AnagramServiceImpl anagramService = new AnagramServiceImpl(dataLoaderService);

    @Test
    public void givenSingularWordIsLoadedFromFile_WhenEvaluatingAnagrams_ThenResultingListShouldBeContainSingleElementForWord(){
        given(dataLoaderService.loadWordsFromFile(FILE_PATH))
                .willReturn(List.of(
                        new Word(
                                "abc",
                                CharacterDistribution.fromStringWord("abc")
                        )
                ));

        final List<String> result = anagramService.findAnagramsInWordlist(FILE_PATH);

        assertThat(result)
                .singleElement()
                .satisfies(element -> assertThat(element).isEqualTo("abc"));
    }

    @Test
    public void givenTwoWordsAreLoadedThatAreNotAnagramsFromFile_WhenEvaluatingAnagrams_ThenResultingListShouldTwoSeparateElements(){
        given(dataLoaderService.loadWordsFromFile(FILE_PATH))
                .willReturn(List.of(
                        new Word(
                                "abc",
                                CharacterDistribution.fromStringWord("abc")
                        ),
                        new Word(
                                "def",
                                CharacterDistribution.fromStringWord("def")
                        )
                ));

        final List<String> result = anagramService.findAnagramsInWordlist(FILE_PATH);

        assertThat(result)
                .containsExactlyInAnyOrder("abc", "def");
    }

    @Test
    public void givenTwoWordsAreLoadedThatAreAnagramsFromFile_WhenEvaluatingAnagrams_ThenResultingListShouldContainAStringWithBothWords(){
        given(dataLoaderService.loadWordsFromFile(FILE_PATH))
                .willReturn(List.of(
                        new Word(
                                "abc",
                                CharacterDistribution.fromStringWord("abc")
                        ),
                        new Word(
                                "cba",
                                CharacterDistribution.fromStringWord("cba")
                        )
                ));

        final List<String> result = anagramService.findAnagramsInWordlist(FILE_PATH);

        assertThat(result)
                .singleElement()
                .satisfies(value -> assertThat(value).contains("abc", "cba", ","));
    }
}