package edu.ksu.pizzanow.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class FileHandler {
    // constants
    private static final String DELIMITER = ",";
    private static final String DATA_DIR = "data/";


    // public API
    public List<String[]> readCSV(String fileName, boolean hasHeader) throws IOException{
        Path path = Paths.get(DATA_DIR, fileName);
        List<String[]> rows = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(path)){
            String line;
            boolean isFirstLine = true;
            
            while((line = reader.readLine()) != null){
                if(isFirstLine && hasHeader){
                    isFirstLine = false;
                    continue;
                }
                if(line.isBlank()){
                    continue;
                }

                rows.add(parseLine(line));
                isFirstLine = false;
            }
        }
        return rows;
    }

    public void writeCSV(String fileName, List<String[]> rows, String[] header) throws IOException{
        Path path = Paths.get(DATA_DIR, fileName);

        try(var writer = Files.newBufferedWriter(path)){
            if(header != null){
                writer.write(String.join(DELIMITER, header));
                writer.newLine();
            }

            for(String[] row : rows){
                writer.write(String.join(DELIMITER, row));
                writer.newLine();
            }
        }
    }

    public void appendRow(String fileName, String[] row) throws IOException{
        Path path = Paths.get(DATA_DIR, fileName);
        Files.createDirectories(path.getParent());

        try(BufferedWriter writer = Files.newBufferedWriter(
            path,
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND)) {

            writer.write(String.join(DELIMITER, row));
            writer.newLine();
        }
    }

    public <T> List<T> csvToObject(String fileName, boolean hasHeader, Function<String[], T> mapper) throws IOException {
        List<String[]> rows = readCSV(fileName, hasHeader);
        List<T> objectList = new ArrayList<>(rows.size());
        for(String[] row : rows){
            objectList.add(mapper.apply(row));
        }

        return objectList;
    }

    // private API
    private String[] parseLine(String line){
        return line.split(DELIMITER);
    }
}
