package com.amido.anagramevaluator.service;

import java.util.List;

public interface AnagramService {

    List<String> findAnagramsInWordlist(String filePath);

}
