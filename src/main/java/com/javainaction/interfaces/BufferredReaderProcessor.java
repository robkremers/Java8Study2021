package com.javainaction.interfaces;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Note that this is a customization of the functional interface Function:
 * Interface Function<T,R>
 */
@FunctionalInterface
public interface BufferredReaderProcessor {
    String process(BufferedReader bufferedReader) throws IOException;
}
