package com.example.crawlerr.service;

import com.example.crawlerr.model.ColumnInfo;
import com.example.crawlerr.model.TableInfo;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ModelGeneratorService {

    private static final String PACKAGE_NAME = "com.example.crawlerr.generatedmodels";

    public void generateModels(List<TableInfo> tableInfos) {
        for (TableInfo table : tableInfos) {
            try {
                generateModelClass(table);
            } catch (IOException e) {
                System.err.println("❌ Error generating model for table: " + table.getTableName());
                e.printStackTrace();
            }
        }
    }

    private void generateModelClass(TableInfo table) throws IOException {
        String className = toPascalCase(table.getTableName());
        List<ColumnInfo> columns = table.getColumns();

        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(PACKAGE_NAME).append(";\n\n");
        builder.append("public class ").append(className).append(" {\n\n");

        // Fields
        for (ColumnInfo column : columns) {
            String type = mapSqlTypeToJava(column.getType());
            String name = column.getName();
            builder.append("    private ").append(type).append(" ").append(name).append(";\n");
        }

        builder.append("\n");

        // Getters and Setters
        for (ColumnInfo column : columns) {
            String type = mapSqlTypeToJava(column.getType());
            String name = column.getName();
            String pascalName = toPascalCase(name);

            // Getter
            builder.append("    public ").append(type).append(" get").append(pascalName).append("() {\n");
            builder.append("        return ").append(name).append(";\n");
            builder.append("    }\n\n");

            // Setter
            builder.append("    public void set").append(pascalName).append("(").append(type).append(" ").append(name).append(") {\n");
            builder.append("        this.").append(name).append(" = ").append(name).append(";\n");
            builder.append("    }\n\n");
        }

        builder.append("}\n");

        // Write to file
        String dir = "crawlerr/src/main/java/com/example/crawlerr/generatedmodels/";
        String filePath = dir + className + ".java";
        FileWriter writer = new FileWriter(filePath);
        writer.write(builder.toString());
        writer.close();

        System.out.println("✅ Generated model: " + className);
    }

    private String mapSqlTypeToJava(String sqlType) {
        sqlType = sqlType.toUpperCase();
        return switch (sqlType) {
            case "INT", "INTEGER", "SMALLINT" -> "int";
            case "BIGINT" -> "long";
            case "FLOAT", "REAL" -> "float";
            case "DOUBLE", "DECIMAL", "NUMERIC" -> "double";
            case "BOOLEAN", "BIT" -> "boolean";
            case "DATE", "TIME", "DATETIME", "TIMESTAMP" -> "java.util.Date";
            default -> "String";
        };
    }

    private String toPascalCase(String str) {
        String[] parts = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
        }
        return sb.toString();
    }
}
