import java.sql.*;
import java.util.Scanner;

public class BloggingBackend {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/BloggingDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "your_password"; 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Connected to the database!");

            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Add User");
                System.out.println("2. Add Post");
                System.out.println("3. Add Comment");
                System.out.println("4. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addUser(connection, scanner);
                        break;
                    case 2:
                        addPost(connection, scanner);
                        break;
                    case 3:
                        addComment(connection, scanner);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void addUser(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        String sql = "INSERT INTO Users (username, email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("User added successfully!");
        }
    }

    private static void addPost(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter post title: ");
        String title = scanner.nextLine();
        System.out.print("Enter post content: ");
        String content = scanner.nextLine();

        String sql = "INSERT INTO Posts (user_id, title, content) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.executeUpdate();
            System.out.println("Post added successfully!");
        }
    }

    private static void addComment(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter post ID: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();

        String sql = "INSERT INTO Comments (post_id, comment) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setString(2, comment);
            stmt.executeUpdate();
            System.out.println("Comment added successfully!");
        }
    }
}
