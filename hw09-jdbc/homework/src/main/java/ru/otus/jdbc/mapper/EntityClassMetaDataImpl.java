package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private String name;
    private Constructor<T> constructor;
    private boolean idFieldPresent;
    private Field field;
    private List<Field> fieldList;
    private List<Field> fieldWithoutIdList;


    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        if (name == null) {
            name = clazz.getSimpleName();
        }
        return name;
    }

    @Override
    public Constructor<T> getConstructor() throws NoSuchMethodException {
        if (constructor == null) {
            constructor = clazz.getConstructor();
        }
        return constructor;
    }

    @Override
    public Field getIdField() {
        if (idFieldPresent) {
            return field;
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idFieldPresent = true;
                return this.field = field;
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        if (fieldList == null) {
            fieldList = List.of(clazz.getDeclaredFields());
        }
        return fieldList;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldWithoutIdList != null) {
            return fieldWithoutIdList;
        }

        fieldWithoutIdList = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Id.class)) {
                fieldWithoutIdList.add(field);
            }
        }
        return fieldWithoutIdList;
    }

}
