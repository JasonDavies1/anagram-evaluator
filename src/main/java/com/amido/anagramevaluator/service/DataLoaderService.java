package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.Word;

import java.util.List;
import java.util.Optional;

public interface DataLoaderService {

    Optional<List<Word>> loadWordsFromFile(String fileLocation);

}
