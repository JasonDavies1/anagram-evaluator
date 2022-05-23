package com.amido.anagramevaluator.service;

import com.amido.anagramevaluator.model.Word;

import java.util.List;

public interface DataLoaderService {

    List<Word> loadWordsFromFile(String fileLocation);

}
