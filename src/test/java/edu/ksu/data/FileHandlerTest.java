package edu.ksu.data;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ksu.pizzanow.data.FileHandler;

public class FileHandlerTest {
    FileHandler fileHandler;


    private String resourcePath(String relative) {
        try {
            var url = getClass().getClassLoader().getResource("data/" + relative);
            assertNotNull(url, "Resource not found: data/" + relative);
            return java.nio.file.Paths.get(url.toURI()).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve resource path for: " + relative, e);
        }
    }


    @BeforeEach
    public void setUp(){
        fileHandler = new FileHandler();
    }

    @Test
    void readCustomerCSV() throws IOException{
        List<String[]> rows = fileHandler.readCSV(resourcePath("test-customers.csv"), true);
        
        assertEquals(3, rows.size(), "Expected 3 customer rows");

        String[] row0 = rows.get(0);
        assertEquals(3, row0.length, "Row 0 should have 3 columns");
        assertEquals("4445556666", row0[0]);
        assertEquals("Alex Rivera", row0[1]);
        assertEquals("CREDIT", row0[2]);

        String[] row1 = rows.get(1);
        assertEquals(3, row1.length, "Row 1 should have 3 columns");
        assertEquals("7778889999", row1[0]);
        assertEquals("Jordan Lee", row1[1]);
        assertEquals("CASH", row1[2]);

        String[] row2 = rows.get(2);
        assertEquals(3, row2.length, "Row 2 should have 3 columns");
        assertEquals("2223334444", row2[0]);
        assertEquals("Sam Patel", row2[1]);
        assertEquals("DEBIT", row2[2]);
    }

    @Test
    void readOrderCSV() throws IOException {
        List<String[]> rows = fileHandler.readCSV(resourcePath("test-orders.csv"), true);

        assertEquals(4, rows.size(), "Expected 4 order rows");

        String[] row0 = rows.get(0);
        assertEquals(9, row0.length, "Row 0 should have 9 columns");
        assertEquals("1234567890", row0[0]);
        assertEquals("4445556666", row0[1]);
        assertEquals("DELIVERY", row0[2]);
        assertEquals("CREDIT", row0[3]);
        assertEquals("18.98", row0[4]);
        assertEquals("1.14", row0[5]);
        assertEquals("20.12", row0[6]);
        assertEquals("2025-11-17T18:30:00", row0[7]);
        assertEquals("COMPLETE", row0[8]);

        String[] row1 = rows.get(1);
        assertEquals(9, row1.length, "Row 1 should have 9 columns");
        assertEquals("8573926145", row1[0]);
        assertEquals("7778889999", row1[1]);
        assertEquals("PICKUP", row1[2]);
        assertEquals("CASH", row1[3]);
        assertEquals("9.99", row1[4]);
        assertEquals("0.60", row1[5]);
        assertEquals("10.59", row1[6]);
        assertEquals("2025-11-17T19:05:30", row1[7]);
        assertEquals("COMPLETE", row1[8]);

        String[] row2 = rows.get(2);
        assertEquals(9, row2.length, "Row 2 should have 9 columns");
        assertEquals("9021457733", row2[0]);
        assertEquals("4445556666", row2[1]);
        assertEquals("DELIVERY", row2[2]);
        assertEquals("DEBIT", row2[3]);
        assertEquals("25.50", row2[4]);
        assertEquals("1.53", row2[5]);
        assertEquals("27.03", row2[6]);
        assertEquals("2025-11-17T20:10:00", row2[7]);
        assertEquals("COMPLETE", row2[8]);

        String[] row3 = rows.get(3);
        assertEquals(9, row3.length, "Row 3 should have 9 columns");
        assertEquals("6641285901", row3[0]);
        assertEquals("2223334444", row3[1]);
        assertEquals("PICKUP", row3[2]);
        assertEquals("DEBIT", row3[3]);
        assertEquals("12.49", row3[4]);
        assertEquals("0.75", row3[5]);
        assertEquals("13.24", row3[6]);
        assertEquals("2025-11-18T11:15:45", row3[7]);
        assertEquals("COMPLETE", row3[8]);

    }
}
