package com.amido.anagramevaluator;

import com.amido.anagramevaluator.service.AnagramService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class AnagramEvaluatorApplicationTest {

    private final AnagramService anagramService = mock(AnagramService.class);
    private final AnagramEvaluatorApplication anagramEvaluatorApplication = new AnagramEvaluatorApplication(anagramService);

    @Test
    public void givenZeroArgumentsPassedToApplication_WhenApplicationStarts_ThenNoAttemptToFindAnagramsShouldBeMade() {
        final String[] arguments = new String[0];

        assertThatCode(() -> anagramEvaluatorApplication.run(arguments))
                .isInstanceOf(Exception.class);

        thenAnagramServiceShouldNotBeInvoked();
    }

    @Test
    public void givenMultipleArgumentsPassedToApplication_WhenApplicationStarts_ThenNoAttemptToFindAnagramsShouldBeMade() {
        final String[] arguments = new String[]{"/my/file/path", "extra-argument"};

        assertThatCode(() -> anagramEvaluatorApplication.run(arguments))
                .isInstanceOf(Exception.class);

        thenAnagramServiceShouldNotBeInvoked();
    }

    @Test
    public void givenOnlyOneArgumentPassedToApplication_WhenApplicationStarts_ThenAnagramsShouldBeFoundFromWordlist() throws Exception {
        final String filePath = "/path/to/file.txt";
        final String[] arguments = new String[]{filePath};

        anagramEvaluatorApplication.run(arguments);

        then(anagramService)
                .should()
                .findAnagramsInWordlist(filePath);
    }

    private void thenAnagramServiceShouldNotBeInvoked() {
        then(anagramService)
                .should(never())
                .findAnagramsInWordlist(anyString());
    }

}