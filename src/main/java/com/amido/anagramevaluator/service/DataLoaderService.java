package com.amido.anagramevaluator.service;

import java.util.List;
import java.util.Optional;

public interface DataLoaderService {

    Optional<List<String>> loadWordsFromFile(String fileLocation);

}