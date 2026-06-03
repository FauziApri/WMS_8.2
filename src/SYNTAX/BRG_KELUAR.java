/**
 * @author Kelompok 2 - R6P
 */

package SYNTAX;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class BRG_KELUAR {
    
    // Button SAVE
    public static boolean save(String id, int jumlahKeluar) {
        try {
            Connection conn = KONEKSI.getConnection();

            // Ambil stok saat ini
            String cekSql = "SELECT jumlah FROM barang WHERE id_barang = ?";
            PreparedStatement cekPst = conn.prepareStatement(cekSql);
            cekPst.setString(1, id);

            ResultSet rs = cekPst.executeQuery();
            
            if (rs.next()) {
                // Cegah stok negatif
                if (jumlahKeluar > rs.getInt("jumlah")) {
                    JOptionPane.showMessageDialog(null,"Jumlah keluar melebihi stok yang tersedia!");
                    return false;
                }

                int stokBaru = rs.getInt("jumlah") - jumlahKeluar;
                if (stokBaru == 0) {
                    // Hapus barang jika stok habis
                    String deleteSql = "DELETE FROM barang WHERE id_barang = ?";
                    PreparedStatement deletePst = conn.prepareStatement(deleteSql);
                    deletePst.setString(1, id);

                    return deletePst.executeUpdate() > 0;                   
                } else {
                    // Update stok
                    String updateSql = "UPDATE barang SET jumlah = ? WHERE id_barang = ?";
                    PreparedStatement updatePst = conn.prepareStatement(updateSql);

                    updatePst.setInt(1, stokBaru);
                    updatePst.setString(2, id);

                    return updatePst.executeUpdate() > 0;
                }
            }

            return false;
        } catch (SQLException e) {
            System.out.println("Error Update: " + e.getMessage());
            return false;
        }
    }

    // Button EDIT
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
