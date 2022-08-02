package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    final private String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = "./src/test/resources/" + fileName;
    }

    @Override
    public List<Measurement> load() {
        Type itemsListType = new TypeToken<List<Measurement>>() {
        }.getType();
        try (FileReader fileReader = new FileReader(fileName)) {
            return new Gson().fromJson(fileReader, itemsListType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
