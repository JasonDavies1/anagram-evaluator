package com.amido.anagramevaluator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataLoaderServiceImpl implements DataLoaderService {
    @Override
    public Optional<List<String>> loadWordsFromFile(final String fileLocation) {
        try {
            final String fileContents = Files.readString(Path.of(fileLocation));
            final List<String> words = fileContents.lines().toList();
            return Optional.of(words);
        } catch (final IOException io) {
            log.error("File could not be found at location: {}", fileLocation);
        }
        return Optional.empty();
    }
}
