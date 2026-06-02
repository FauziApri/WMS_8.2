/**
 * @author Kelompok 2 - R6P
 */

package SYNTAX;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class SPL {
    
    // Button ADD
    public boolean input(String id, String nama, String barang, String alamat) {
        try {
            Connection conn = KONEKSI.getConnection();
            String sql = "INSERT INTO supplier (id_supplier, nama_supplier, nama_barang, alamat_supplier) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, id);
            ps.setString(2, nama);
            ps.setString(3, barang);
            ps.setString(4, alamat);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }
    
    // Model buat nampilin tabel barang
    public DefaultTableModel getModelBarang() {
        DefaultTableModel model_barang = new DefaultTableModel();
        
        model_barang.addColumn("ID Barang");
        model_barang.addColumn("Nama Barang");
        
        try {
            Connection conn = KONEKSI.getConnection();
            String sql = "SELECT id_barang, nama_barang FROM barang";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model_barang.addRow(new Object[]{
                    rs.getString("id_barang"),
                    rs.getString("nama_barang"),
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return model_barang;
    }
    
    // Model buat nampilin tabel supplier
    public DefaultTableModel getModelSupplier() {
        DefaultTableModel model_supplier = new DefaultTableModel();
        
        model_supplier.addColumn("ID Supplier");
        model_supplier.addColumn("Nama Supplier");
        model_supplier.addColumn("Nama Barang");
        model_supplier.addColumn("Alamat");

        try {
            Connection conn = KONEKSI.getConnection();
            String sql = " SELECT supplier.id_supplier, supplier.nama_supplier, barang.nama_barang, supplier.alamat_supplier "
                    + "FROM supplier JOIN barang "
                    + "ON supplier.nama_barang = barang.nama_barang;";
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model_supplier.addRow(new Object[]{
                    rs.getString("id_supplier"),
                    rs.getString("nama_supplier"),
                    rs.getString("nama_barang"),
                    rs.getString("alamat_supplier")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return model_supplier;
    }
}
