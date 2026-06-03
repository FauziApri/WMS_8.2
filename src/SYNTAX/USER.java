/**
 * @author Kelompok 2 - R6P
 */

package SYNTAX;
import java.sql.*;
import javax.swing.JOptionPane;

public class USER {
    public static String currentUsn;
    public static String currentUser;
    public static String currentJabatan;
    
    // LOGIN
    public static boolean login(String username, String password) {
        try {
            Connection conn = KONEKSI.getConnection();
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();


            if (rs.next()) {     
                JOptionPane.showMessageDialog(null, "Selamat Datang " + rs.getString("full_name"));
                
                // untuk menampilkan informasi di PROFIL.java
                currentUsn = rs.getString("username");
                currentUser = rs.getString("full_name");
                currentJabatan = rs.getString("role");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "LOGIN GAGAL");
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // REGISTER
    public static boolean register(String username, String password, String jabatan, String nama_lengkap) {
        try {
            Connection conn = KONEKSI.getConnection();
            String sql = "INSERT INTO user (username, password, role, full_name) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, jabatan);
            pst.setString(4, nama_lengkap);
            
            int hasil = pst.executeUpdate();
            
            if (hasil > 0) {
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Button LOGOUT [ PROFIL.java ]
    public static void logout() {
        currentUsn = null;
        currentUser = null;      
        currentJabatan = null;        
    }
}
