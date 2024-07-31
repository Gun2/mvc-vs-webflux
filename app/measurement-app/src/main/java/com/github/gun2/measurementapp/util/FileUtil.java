package com.github.gun2.measurementapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void saveObjectToFile(String filePath, T content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(objectMapper.writeValueAsString(content));
        }
    }
}
