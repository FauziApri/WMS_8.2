/**
 * @author Kelompok 2 - R6P
 */

package SYNTAX;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class BRG_MASUK {
    
    // Button SAVE  [Input ke Database]
    public static boolean save(String id, String nama, String kategori, int jumlah) {
        try {
            Connection conn = KONEKSI.getConnection();

            // Cek apakah barang sudah ada
            String cekSql = "SELECT jumlah FROM barang WHERE nama_barang = ? AND kategori = ?";
            PreparedStatement cekPst = conn.prepareStatement(cekSql);
            cekPst.setString(1, nama);
            cekPst.setString(2, kategori);

            ResultSet rs = cekPst.executeQuery();

            if (rs.next()) {
                // Barang sudah ada -> update jumlah
                int jumlahLama = rs.getInt("jumlah");
                int jumlahBaru = jumlahLama + jumlah;

                String updateSql = "UPDATE barang SET jumlah = ? WHERE nama_barang = ? AND kategori = ?";
                PreparedStatement updatePst = conn.prepareStatement(updateSql);

                updatePst.setInt(1, jumlahBaru);
                updatePst.setString(2, nama);
                updatePst.setString(3, kategori);

                return updatePst.executeUpdate() > 0;

            } else {
                // Barang belum ada -> insert baru
                String insertSql = "INSERT INTO barang (id_barang, nama_barang, kategori, jumlah) VALUES (?, ?, ?, ?)";
                PreparedStatement insertPst = conn.prepareStatement(insertSql);

                insertPst.setString(1, id);
                insertPst.setString(2, nama);
                insertPst.setString(3, kategori);
                insertPst.setInt(4, jumlah);

                return insertPst.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    // Button EDIT  [Ubah data yang ada  di Database]
    public static boolean edit(String id, String namaBaru, String kategori, int jumlah){
        try {
            Connection conn = KONEKSI.getConnection();
            String sql = "UPDATE barang SET nama_barang=?, kategori=?, jumlah=? WHERE id_barang=?";         
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, namaBaru);
            pst.setString(2, kategori);
            pst.setInt(3, jumlah);
            pst.setString(4, id); 

            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error Update: " + e.getMessage());
            return false;
        }
    }
    
    // Button DELETE  [Hapus data dari Database]
    public static boolean delete(String id){
        try {            
            Connection conn = KONEKSI.getConnection();
            String sql = "DELETE FROM barang WHERE id_barang = ?";            
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, id);
            
            int rowAffected = pst.executeUpdate();
            return rowAffected > 0;
            
        } catch (SQLException e){
            System.out.println("Error Hapus: " + e.getMessage());
            return false;
        }
    }
    
    // Model Tabel  [Untuk menampilkan data ke tabel GUI]
    public DefaultTableModel getModelBarang() {
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori");
        model.addColumn("Jumlah");

        try {
            Connection conn = KONEKSI.getConnection();
            String sql = "SELECT * FROM barang";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    rs.getInt("jumlah")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
}
