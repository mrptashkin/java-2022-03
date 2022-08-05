package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

public class ResourcesFileLoader implements Loader {

    final private String fileName;

    public ResourcesFileLoader() {
        this.fileName = "inputData.json";
    }

    @Override
    public List<Measurement> load() throws IOException {
        Type itemsListType = new TypeToken<List<Measurement>>() {
        }.getType();
        try (InputStream inputStream = getFileFromResourceAsStream(fileName)) {
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            return new Gson().fromJson(result, itemsListType);
        } catch (FileProcessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }


}
