package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("SELECT * FROM %s", entityClassMetaData.getName().toLowerCase());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("SELECT * FROM %s WHERE %s = ?"
                , entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getIdField().getName().toLowerCase());
    }


    @Override
    public String getInsertSql() {
        StringBuilder values = new StringBuilder();
        StringBuilder insertingValues = new StringBuilder();
        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            values.append(field.getName()).append(", ");
            insertingValues.append("?, ");
        }
        values.delete(values.length() - 2, values.length());
        insertingValues.delete(insertingValues.length() - 2, insertingValues.length());
        return String.format("INSERT INTO %s (%s) VALUES (%s)",
                entityClassMetaData.getName().toLowerCase(),
                values,
                insertingValues);
    }

    @Override
    public String getUpdateSql() {
        StringBuilder updatedFields = new StringBuilder();
        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            updatedFields.append(field.getName()).append(" = ?, ");
        }
        updatedFields.delete(updatedFields.length() - 2, updatedFields.length());
        return String.format("UPDATE %s SET %s WHERE %s = ?",
                entityClassMetaData.getName().toLowerCase(),
                updatedFields,
                entityClassMetaData.getIdField().getName().toLowerCase());
    }


}
