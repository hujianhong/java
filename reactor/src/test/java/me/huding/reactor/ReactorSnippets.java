package me.huding.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class ReactorSnippets {
  private static List<String> words = Arrays.asList(
        "the",
        "quick",
        "brown",
        "fox",
        "jumped",
        "over",
        "the",
        "lazy",
        "dog"
        );

  @Test
  public void simpleCreation() {
     Flux<String> fewWords = Flux.just("Hello", "World");
     Flux<String> manyWords = Flux.fromIterable(words);

     fewWords.subscribe(System.out::println);
     System.out.println();
     manyWords.subscribe(System.out::println);
  }
}
