package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.exception.EmptyWordlistException;
import com.amido.anagramevaluator.exception.WordlistNotFoundException;
import com.amido.anagramevaluator.model.Word;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoaderServiceImpl implements DataLoaderService {

    private final WordConverterService wordConverterService;

    @Override
    public List<Word> loadWordsFromFile(final String fileLocation) {
        try {
            final String fileContents = Files.readString(Path.of(fileLocation));
            if (fileContents.isBlank()) {
                throw new EmptyWordlistException("The wordlist found at location " + fileLocation + " contained no content");
            }
            return fileContents.lines().map(wordConverterService::convertStringToWord).toList();
        } catch (final IOException io) {
            throw new WordlistNotFoundException("Wordlist not found at location: " + fileLocation);
        }
    }
}
