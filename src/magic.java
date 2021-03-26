import java.sql.*;

public class magic {
    public static Connection c = null;

    public static void connectDB() {
        c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/"+"ICPC",
                            "postgres", "1234");

            c.setAutoCommit(false);
            //stmt.close();
            //c.close();
        } catch (Exception e) {
            System.out.println("Couldn't open database");
            e.printStackTrace();
        }
    }

    public static ResultSet query(String queryLine, boolean updates) {
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( queryLine );
            if(updates) c.commit();
            //stmt.close();
            //c.close();
            return rs;
        } catch (Exception e) {
            System.out.println("HERE? WTF!");
            System.out.println("For QUERY: " + queryLine);
            return null;
        }
    }

    public static void update(String updLine) {
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();

            stmt.executeUpdate( updLine);
            c.commit();
            //stmt.close();
            //c.close();
        } catch (Exception e) {
            System.out.println("UPDATE HERE? WTF!");
            System.out.println("For Update: " + updLine);
        }
    }
}