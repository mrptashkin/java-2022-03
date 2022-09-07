package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;
    private String selectAllFormed;
    private String selectByIdFormed;
    private String insertFormed;
    private String updateFormed;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        if (selectAllFormed == null) {
            selectAllFormed = String.format("SELECT * FROM %s", entityClassMetaData.getName().toLowerCase());
        }
        return selectAllFormed;
    }

    @Override
    public String getSelectByIdSql() {
        if (selectByIdFormed == null) {
            selectByIdFormed = String.format("SELECT * FROM %s WHERE %s = ?"
                    , entityClassMetaData.getName().toLowerCase(),
                    entityClassMetaData.getIdField().getName().toLowerCase());
        }
        return selectByIdFormed;
    }


    @Override
    public String getInsertSql() {
        if (insertFormed == null) {
            List<String> fieldsNames = entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).toList();
            String values = String.join(",", fieldsNames);
            String insertingValues = String.join(",", fieldsNames.stream().map(s -> "?").toList());
            insertFormed = String.format("INSERT INTO %s (%s) VALUES (%s)",
                    entityClassMetaData.getName().toLowerCase(),
                    values,
                    insertingValues);
        }
        return insertFormed;
    }

    @Override
    public String getUpdateSql() {
        if (updateFormed == null) {
            String updatedFields = String.join(" = ?, ", entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).toList());
            updateFormed = String.format("UPDATE %s SET %s WHERE %s = ?",
                    entityClassMetaData.getName().toLowerCase(),
                    updatedFields,
                    entityClassMetaData.getIdField().getName().toLowerCase());
        }
        return updateFormed;
    }
}
