package com.amido.anagramevaluator;

import com.amido.anagramevaluator.service.DataLoaderService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class AnagramEvaluatorApplicationTest {

    private final DataLoaderService dataLoaderService = mock(DataLoaderService.class);
    private final AnagramEvaluatorApplication anagramEvaluatorApplication = new AnagramEvaluatorApplication(dataLoaderService);

    @Test
    public void givenZeroArgumentsPassedToApplication_WhenApplicationStarts_ThenWordsAreNotLoadedByDataLoader() {
        final String[] arguments = new String[0];

        assertThatCode(() -> anagramEvaluatorApplication.run(arguments))
                .isInstanceOf(Exception.class);

        thenDataLoaderShouldNotBeInvoked();
    }

    @Test
    public void givenMultipleArgumentsPassedToApplication_WhenApplicationStarts_ThenWordsAreNotLoadedByDataLoader() {
        final String[] arguments = new String[]{"/my/file/path", "extra-argument"};

        assertThatCode(() -> anagramEvaluatorApplication.run(arguments))
                .isInstanceOf(Exception.class);

        thenDataLoaderShouldNotBeInvoked();
    }

    @Test
    public void givenOnlyOneArgumentPassedToApplication_WhenApplicationStarts_ThenWordsShouldBeLoadedByDataLoader() throws Exception {
        final String filePath = "/path/to/file.txt";
        final String[] arguments = new String[]{filePath};

        anagramEvaluatorApplication.run(arguments);

        then(dataLoaderService)
                .should()
                .loadWordsFromFile(filePath);
    }

    private void thenDataLoaderShouldNotBeInvoked() {
        then(dataLoaderService)
                .should(never())
                .loadWordsFromFile(anyString());
    }

}