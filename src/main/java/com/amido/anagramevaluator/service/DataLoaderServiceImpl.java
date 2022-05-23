package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.Word;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoaderServiceImpl implements DataLoaderService {

    private final WordConverterService wordConverterService;
    @Override
    public Optional<List<Word>> loadWordsFromFile(final String fileLocation) {
        try {
            final String fileContents = Files.readString(Path.of(fileLocation));
            final List<Word> words = fileContents.lines().map(wordConverterService::convertStringToWord).toList();
            return Optional.of(words);
        } catch (final IOException io) {
            log.error("File could not be found at location: {}", fileLocation);
        }
        return Optional.empty();
    }
}
