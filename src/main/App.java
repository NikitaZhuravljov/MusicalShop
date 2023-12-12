import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/zewShop";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Choose an option:");
            System.out.println("1. Add data");
            System.out.println("2. Display existing data");
            System.out.println("3. Delete data");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    // Taking user inputs to add data
                    System.out.println("Enter value for id (integer): ");
                    int idValue = scanner.nextInt();

                    scanner.nextLine(); // Consume newline

                    System.out.println("Enter value for name (string): ");
                    String nameValue = scanner.nextLine();

                    System.out.println("Enter value for age (integer): ");
                    int ageValue = scanner.nextInt();

                    // Inserting data into the 'zew' table using user inputs
                    String insertQuery = "INSERT INTO student (id, name, age) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, idValue);
                    preparedStatement.setString(2, nameValue);
                    preparedStatement.setInt(3, ageValue);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Data inserted successfully.");
                    } else {
                        System.out.println("Failed to insert data.");
                    }

                    preparedStatement.close();
                    break;

                case 2:
                    // Displaying existing data from the 'zew' table
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM student");

                    while (resultSet.next()) {
                        System.out.println(resultSet.getInt(1) +
                                " " + resultSet.getString(2) + " " + resultSet.getInt(3));
                    }
                    resultSet.close();
                    break;
                case 3:
                    // Deleting all data from the 'zew' table
                    String deleteQuery = "DELETE FROM student";
                    int rowsDeleted = statement.executeUpdate(deleteQuery);

                    if (rowsDeleted > 0) {
                        System.out.println("All data deleted successfully.");
                    } else {
                        System.out.println("Failed to delete data.");
                    }
                    break;

                default:
                    System.out.println("Invalid option");
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
