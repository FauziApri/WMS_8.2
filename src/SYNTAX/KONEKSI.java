/**
 * @author Kelompok 2 - R6P
 */

package SYNTAX;
import java.sql.*;

public class KONEKSI {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/wms";
                String user = "root";
                String password = "";

                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi berhasil!");
            } catch (SQLException e) {
                System.out.println("Koneksi gagal!");
                e.printStackTrace();
            }
        }
        return conn;
    } 
}
