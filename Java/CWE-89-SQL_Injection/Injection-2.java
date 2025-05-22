import java.sql.*;

public class MediumComplexitySQLInjection {

    public static void main(String[] args) {
        String userInput = getUserInput(); // Suppose this comes from a form or API

        Database db = new Database();
        db.findUserByName(userInput);
    }

    // Simulated user input function
    private static String getUserInput() {
        return "admin' --";
    }

    static class Database {
        private Connection getConnection() throws SQLException {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "user", "password");
        }

        public void findUserByName(String username) {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {

                // VULNERABLE: Still using string concatenation with input passed via multiple layers
                String sql = buildQuery(username);
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    System.out.println("User found: " + rs.getString("username"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private String buildQuery(String name) {
            return "SELECT * FROM users WHERE username = '" + name + "'";
        }
    }
}