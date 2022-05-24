package com.amido.anagramevaluator;

import com.amido.anagramevaluator.service.AnagramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class AnagramEvaluatorApplication implements CommandLineRunner {

    private final AnagramService anagramService;

    public static void main(String[] args) {
        SpringApplication.run(AnagramEvaluatorApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        final long applicationStartTime = System.currentTimeMillis();
        if (args.length != 1) {
            throw new Exception("Please specify only the path to your desired wordlist");
        }
        anagramService.findAnagramsInWordlist(args[0])
                .forEach(System.out::println);
        final long applicationEndTime = System.currentTimeMillis();
        log.info("Total time to run application: {}ms", applicationEndTime - applicationStartTime);
    }
}
