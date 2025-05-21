import java.sql.*;

public class SimpleSQLInjection {
    public static void main(String[] args) {
        String userInput = args[0] + "' OR '1'='1"; // Attacker input
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "user", "password");
            stmt = conn.createStatement();

            // VULNERABLE: SQL Injection via string concatenation
            String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("User: " + rs.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}