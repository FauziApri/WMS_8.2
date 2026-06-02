/**
 * @author Kelompok 2 - R6P
 */

package SYNTAX;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class MENU {
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
