package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.chitietphieunhap_DTO;
import DTO.phieunhap_DTO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO_chitietphieunhap {

    private ConnectDataBase c;

    public DAO_chitietphieunhap() {
        try {
            c = new ConnectDataBase();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
          public int getOldQuantity(String masp, String masize, String mapn) throws SQLException {
    int oldQuantity = 0;
    c.connect();
    
    String query = "SELECT SOLUONG FROM chitietphieunhap WHERE MASP = '" + masp + "' AND MASIZE = '" + masize + "' AND MAPN = '" + mapn + "'";
    
    try {
        ResultSet rs = c.executeQuery(query);
        if (rs.next()) {
            oldQuantity = rs.getInt("SOLUONG");
        }
        rs.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        throw ex;
    } finally {
        c.disconnect();
    }

    return oldQuantity;
}
public void setAfterTT(chitietphieunhap_DTO d, int oldQuantity, int newQuantity) throws SQLException {
    c.connect();
    int currentQuantity = 0;

    // Lấy số lượng hiện tại từ chitietsanpham
    String selectSql = "SELECT SOLUONG FROM chitietsanpham WHERE MASP = '" + d.getMasp() + "' AND MASIZE = '" + d.getMasize() + "'";
    try {
        ResultSet rs = c.executeQuery(selectSql);
        if (rs.next()) {
            currentQuantity = rs.getInt("SOLUONG");
        }
        rs.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        c.disconnect();
        throw ex; // Ném lại ngoại lệ để xử lý
    }

    // Tính toán số lượng cập nhật: hiện tại - số lượng cũ + số lượng mới
    int updatedQuantity = currentQuantity - oldQuantity + newQuantity;

    System.err.println("Số lượng hiện tại: " + currentQuantity);
    System.err.println("Số lượng cũ: " + oldQuantity);
    System.err.println("Số lượng mới: " + newQuantity);
    System.err.println("Số lượng cập nhật: " + updatedQuantity);

    // Cập nhật số lượng mới vào chitietsanpham
    String updateSql = "UPDATE chitietsanpham SET SOLUONG = " + updatedQuantity +
                       " WHERE MASP = '" + d.getMasp() + "' AND MASIZE = '" + d.getMasize() + "'";
    c.executeUpdate(updateSql); // Ném lại ngoại lệ để xử lý
    System.err.println("Cập nhật thành công số lượng mới");
    System.out.println(updateSql);
    c.disconnect();
}
    public ArrayList<chitietphieunhap_DTO> list() {
        ArrayList<chitietphieunhap_DTO> ds = new ArrayList<chitietphieunhap_DTO>();
        try {
            c.connect();
            String sql = "SELECT * FROM chitietphieunhap";
            ResultSet rs = c.executeQuery(sql);
            while (rs.next()) {
                String mapn = rs.getString("MAPN");
                String masp = rs.getString("MASP");
                int soluong = rs.getInt("SOLUONG");
                double gianhap = rs.getDouble("GIANHAP");
                double thanhtien = rs.getDouble("THANHTIEN");
                String masize = rs.getString("MASIZE");
                
                chitietphieunhap_DTO d = new chitietphieunhap_DTO(mapn, masp, soluong, gianhap, thanhtien, masize);
                ds.add(d);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_chitietphieunhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    public ArrayList<chitietphieunhap_DTO> selectby_id(phieunhap_DTO h) {
        ArrayList<chitietphieunhap_DTO> ds = new ArrayList<chitietphieunhap_DTO>();
        try {

            c.connect();
            String sql = "select * from chitietphieunhap where MAPN = '" + h.getMAPN() + "'";

            ResultSet rs = c.executeQuery(sql);
            while (rs.next()) {
                String mapn = rs.getString("MAPN");
                String masp = rs.getString("MASP");
                int soluong = rs.getInt("SOLUONG");
                double gianhap = rs.getDouble("GIANHAP");
                double thanhtien = rs.getDouble("THANHTIEN");
                String masize = rs.getString("MASIZE");

                chitietphieunhap_DTO d = new chitietphieunhap_DTO(mapn, masp, soluong, gianhap, thanhtien, masize);
                ds.add(d);

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ds;

    }

    public void add(chitietphieunhap_DTO h) {
        try {
            c.connect();

            String sql = "INSERT INTO chitietphieunhap (MAPN,MASP,SOLUONG,GIANHAP,THANHTIEN,MASIZE) "
                    + "values ('" + h.getMapn() + "','" + h.getMasp() + "'," + h.getSoluong() + "," + h.getGianhap()
                    + "," + h.getThanhtien() + ",'" + h.getMasize() + "')";

            c.executeUpdate(sql);

            System.out.println("lenh da thuc hien " + sql);

            c.disconnect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void set(chitietphieunhap_DTO d) {
        try {
            c.connect();

            String sql = "UPDATE chitietphieunhap \r\n"
                    + "SET SOLUONG  = " + d.getSoluong() + "  ,GIANHAP =" + d.getGianhap() + ",THANHTIEN  = SOLUONG * GIANHAP "
                    + " WHERE MAPN = '" + d.getMapn() + "'  AND  MASP = '" + d.getMasp() + "'  AND  MASIZE = '" + d.getMasize() + "'";

            c.executeUpdate(sql);

            c.disconnect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void delete(chitietphieunhap_DTO h) {
        try {
            c.connect();

            String sql = "DELETE FROM chitietphieunhap WHERE MAPN = '" + h.getMapn() + "' and MASP = '" + h.getMasp() + "' and MASIZE = '" + h.getMasize() + "'";

            c.executeUpdate(sql);

            c.disconnect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        phieunhap_DTO m = new phieunhap_DTO("PN006", "", 0, "");
        DAO_chitietphieunhap c = new DAO_chitietphieunhap();
        for (chitietphieunhap_DTO h : c.selectby_id(m)) {
            System.out.println(h.toString());
        }

    }
}
