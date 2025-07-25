package com.example.crawlerr.service;

import com.example.crawlerr.config.DBConfig;
import com.example.crawlerr.model.ColumnInfo;
import com.example.crawlerr.model.ForeignKeyInfo;
import com.example.crawlerr.model.TableInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseCrawlerService {

    public List<TableInfo> crawlDatabase() {
        List<TableInfo> tableInfoList = new ArrayList<>();

        try {
            // ✅ Load DB config from JSON file
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = getClass().getClassLoader().getResourceAsStream("dbconfig.json");

            if (input == null) {
                throw new RuntimeException("⚠️ Could not find dbconfig.json in resources folder.");
            }

            DBConfig config = mapper.readValue(input, DBConfig.class);

            String url = config.getUrl();
            String username = config.getUsername();
            String password = config.getPassword();

            // ✅ Connect to database using loaded config
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");

                    // Columns
                    List<ColumnInfo> columns = new ArrayList<>();
                    ResultSet columnRS = metaData.getColumns(null, null, tableName, "%");
                    while (columnRS.next()) {
                        String name = columnRS.getString("COLUMN_NAME");
                        String type = columnRS.getString("TYPE_NAME");
                        int size = columnRS.getInt("COLUMN_SIZE");
                        boolean nullable = "YES".equals(columnRS.getString("IS_NULLABLE"));
                        columns.add(new ColumnInfo(name, type, size, nullable));
                    }

                    // Primary Keys
                    List<String> primaryKeys = new ArrayList<>();
                    ResultSet pkRS = metaData.getPrimaryKeys(null, null, tableName);
                    while (pkRS.next()) {
                        primaryKeys.add(pkRS.getString("COLUMN_NAME"));
                    }

                    // Foreign Keys
                    List<ForeignKeyInfo> foreignKeys = new ArrayList<>();
                    ResultSet fkRS = metaData.getImportedKeys(null, null, tableName);
                    while (fkRS.next()) {
                        String fkColumn = fkRS.getString("FKCOLUMN_NAME");
                        String pkTable = fkRS.getString("PKTABLE_NAME");
                        String pkColumn = fkRS.getString("PKCOLUMN_NAME");
                        foreignKeys.add(new ForeignKeyInfo(fkColumn, pkTable, pkColumn));
                    }
                    boolean isJoinTable = false;

// ✅ Detect join table: 2 FKs, both part of composite primary key, no extra columns
                    if (foreignKeys.size() == 2 && columns.size() <= 3 && primaryKeys.size() == 2) {
                        List<String> fkColumnNames = new ArrayList<>();
                        for (ForeignKeyInfo fk : foreignKeys) {
                            fkColumnNames.add(fk.getColumnName());
                        }

                        if (primaryKeys.containsAll(fkColumnNames)) {
                            isJoinTable = true;
                        }
                    }

                     // ✅ Set in TableInfo
                    TableInfo table = new TableInfo(tableName, columns, primaryKeys, foreignKeys);
                    table.setJoinTable(isJoinTable);
                    tableInfoList.add(table);
                }

            } catch (SQLException e) {
                System.err.println("❌ Error connecting to the database:");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("❌ Error loading dbconfig.json:");
            e.printStackTrace();
        }

        return tableInfoList;
    }
}
