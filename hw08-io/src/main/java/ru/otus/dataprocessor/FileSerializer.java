package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class FileSerializer implements Serializer {
    final private String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(data);
            printWriter.write(jsonString);
        } catch (FileProcessException e) {
            e.printStackTrace();
        }
    }
}
