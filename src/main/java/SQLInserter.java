import com.fazecast.jSerialComm.SerialPort;
import controllers.DatabaseConnection; // Import your DatabaseConnection class

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SQLInserter {
    public static void main(String[] args) {
        // Step 1: Open Serial Port
        SerialPort comPort = SerialPort.getCommPort("COM4"); // Replace with your port name
        comPort.setBaudRate(115200);

        if (comPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.err.println("Failed to open port.");
            return;
        }

        // Add a shutdown hook to ensure the port is closed when the program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (comPort.isOpen()) {
                comPort.closePort();
                System.out.println("Port closed.");
            }
        }));

        // Step 2: Establish Database Connection
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();
        if (connection != null) {
            System.out.println("Connected to database.");
        } else {
            System.err.println("Failed to connect to the database.");
            return;
        }
        StringBuilder buffer = new StringBuilder();
        // Step 3: Read from Serial Port and Insert into Database
        while (true) {
            if (comPort.bytesAvailable() > 14) {
                byte[] readBuffer = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                String receivedData = new String(readBuffer).trim(); // Convert byte array to string
                // Append the new data to the buffer
                buffer.append(new String(readBuffer, 0, numRead));

                // Check if the buffer contains a full line (delimited by \n)
                int newlineIndex;
                while ((newlineIndex = buffer.indexOf("\n")) != -1) {
                    // Extract the complete line
                    String line = buffer.substring(0, newlineIndex).trim();
                    buffer.delete(0, newlineIndex + 1); // Remove processed data

                    String[] lines = receivedData.split("\n");
                    receivedData = lines[lines.length -1];
                    String[] parts = receivedData.split(", ");
                    float foundTDS = Float.parseFloat(parts[0]);
                    if(parts[1].equals("-")) parts[1] += "0";
                    float foundTroebel = Float.parseFloat(parts[1]);
                    LocalDateTime dateTime = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = dateTime.format(myFormatObj);
                    System.out.println(formattedDate);
                    boolean quality = foundTDS < 4000 && foundTroebel < 4000;
//                    System.out.println("Byte amount: " + numRead + "\nCurrent date and time: " + formattedDate);
//                    System.out.println("Found values: " + foundTDS + ", " + foundTDS);
//                    System.out.println("Is the water safe? " + quality);
                    // Insert received data into the database
                   String sql = "INSERT INTO SensorData VALUES ('" + formattedDate + "', " + foundTDS + ", " + foundTroebel + ", " + quality + ");";
//                   System.out.println(sql);
                    try {
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(sql);
                        System.out.println("Data inserted into database: " + sql);
                    } catch (SQLException e) {
                        System.err.println("Failed to insert data: " + e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}