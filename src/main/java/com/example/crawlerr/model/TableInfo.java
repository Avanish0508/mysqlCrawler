package com.example.crawlerr.model;

import java.util.List;

public class TableInfo {
    private String tableName;
    private List<ColumnInfo> columns;
    private List<String> primaryKeys;
    private List<ForeignKeyInfo> foreignKeys;
    private boolean isJoinTable; // add this

    // Constructors, Getters, Setters
    public TableInfo() {}

    public TableInfo(String tableName, List<ColumnInfo> columns, List<String> primaryKeys, List<ForeignKeyInfo> foreignKeys) {
        this.tableName = tableName;
        this.columns = columns;
        this.primaryKeys = primaryKeys;
        this.foreignKeys = foreignKeys;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<ForeignKeyInfo> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKeyInfo> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public boolean isJoinTable() {
        return isJoinTable;
    }

    public void setJoinTable(boolean joinTable) {
        isJoinTable = joinTable;
    }
}
