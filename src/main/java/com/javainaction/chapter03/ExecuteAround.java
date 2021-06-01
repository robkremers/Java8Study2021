package com.javainaction.chapter03;

import com.javainaction.interfaces.BufferredReaderProcessor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Execute around pattern:
 * A recurrent pattern in resource processing (for example, dealing with files or databases)
 * is to open a resource, do some  * processing on it, and then close the resource.
 * The setup and cleanup phases are always similar and surround the important code doing the processing.
 */
@Slf4j
public class ExecuteAround {

    private static String dataFile = "javainaction/chapter03/data.txt";

    public static void main(String... args) throws IOException {
        log.info("Chapter 3: Lambda expressions; execute around pattern.");

        String result = processFile();
        log.info("result = {}", result);

        result = processFile((BufferedReader br) -> br.readLine() + "\n" + br.readLine() + "\n" + br.readLine());
        log.info("result processFile= \n{}", result);

        // Sort of https://www.baeldung.com/convert-input-stream-to-string; method 4.1.
        result = processFile2(
                (BufferedReader br) ->
                        br.lines().collect(Collectors.joining("\n"))
        );
        log.info("result processFile2 = \n{}", result);

        List<String> lines = processFile3(dataFile);
        lines.forEach( (String line) -> log.info(line));

        lines = processFile4(dataFile);
        lines.forEach((String line) -> log.info(line));
        // This would fail: here an absolute path is required.
        // See: http://tutorials.jenkov.com/java-nio/path.html
        long uniqueWords = Files.lines(Paths.get("javainaction/src/main/resources/javainaction/chapter05/data.txt"), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .distinct()
                .count();

        System.out.println("There are " + uniqueWords + " unique words in data.txt");
    }

    public static String processFile() throws IOException {
        InputStream inputStream = ExecuteAround.class.getClassLoader().getResourceAsStream(dataFile);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.readLine();
        }
    }

    public static String processFile(BufferredReaderProcessor bufferredReaderProcessor) throws IOException {
        InputStream inputStream = ExecuteAround.class.getClassLoader().getResourceAsStream(dataFile);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferredReaderProcessor.process(bufferedReader);
        }
    }

    public static String processFile2(Function<BufferedReader, String> processBufferedReader) throws IOException {
        InputStream inputStream = ExecuteAround.class.getClassLoader().getResourceAsStream(dataFile);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return processBufferedReader.apply(bufferedReader);
        }
    }

    public static List<String> processFile3(String dataFile) throws IOException {
        InputStream inputStream = ExecuteAround.class.getClassLoader().getResourceAsStream(dataFile);
        List<String> lines = new ArrayList<>();
        try( BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream))) {
            lines.addAll(bufferedReader.lines().collect(Collectors.toList()));
        }
        return lines;
    }

    // Doesn't work: Paths.get() requires an absolute path.
    public static List<String> processFile4(String dataFile) throws IOException {
        return Files.lines(Paths.get("data.txt"), Charset.defaultCharset()).collect(Collectors.toList());
    }
}
