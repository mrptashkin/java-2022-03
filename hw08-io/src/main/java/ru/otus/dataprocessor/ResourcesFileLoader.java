package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    final private String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        Type itemsListType = new TypeToken<List<Measurement>>() {
        }.getType();
        try (InputStream inputStream = getFileFromResourceAsStream(fileName)) {
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
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
            throw new FileProcessException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }


}
