package com.amido.anagramevaluator;

import com.amido.anagramevaluator.service.AnagramService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class AnagramEvaluatorApplication implements CommandLineRunner {

    private final AnagramService anagramService;

    public static void main(String[] args) {
        SpringApplication.run(AnagramEvaluatorApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Please specify only the path to your desired wordlist");
        }
        anagramService.findAnagramsInWordlist(args[0])
                .forEach(System.out::println);
    }
}
