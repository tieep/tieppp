/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.nhanVienDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class nhanVienDAO {

    private ConnectDataBase c;

    public nhanVienDAO() {
        try {
            c = new ConnectDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<nhanVienDTO> ds_nhanVien() {
        ArrayList<nhanVienDTO> ds = new ArrayList<>();

        try {
            c.connect();
            String sql = "SELECT * FROM NHANVIEN";
            ResultSet rs = c.executeQuery(sql);
            while (rs.next()) {
                nhanVienDTO nv = new nhanVienDTO(rs.getString("MANV"),
                        rs.getString("TENNV"),
                        rs.getString("CHUCVU"),
                        rs.getString("SDT"),
                        rs.getString("DIACHI"),
                        rs.getString("EMAIL"),
                        rs.getInt("TRANGTHAI"));
                ds.add(nv);
            }
            c.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean themNV(nhanVienDTO nv) {
        try {
            c.connect();
            String sql = "INSERT INTO NHANVIEN(MANV, TENNV, CHUCVU,SDT, DIACHI, EMAIL, TRANGTHAI) "
                    + "VALUES('" + nv.getMANV() + "','" + nv.getTENNV() + "','" + nv.getCHUCVU() + "','" + nv.getSDT() + "','" + nv.getDIACHI() + "','" + nv.getEMAIL() + "','" + nv.getTRANGTHAi() + "')";
            c.executeUpdate(sql);
            c.disconnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capnhatNV(nhanVienDTO nv) {
        try {
            c.connect();
            String sql = "UPDATE NHANVIEN SET TENNV='" + nv.getTENNV()
                    + "', CHUCVU='" + nv.getCHUCVU()
                    + "', SDT='" + nv.getSDT()
                    + "', DIACHI='" + nv.getDIACHI()
                    + "', EMAIL='" + nv.getEMAIL()
                    + "', TRANGTHAI='" + nv.getTRANGTHAi()
                    + "' WHERE MANV='" + nv.getMANV() + "'";

            c.executeUpdate(sql);
            c.disconnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public void xoaNV(String id) {
//        try {
//            c.connect();
//            String delSql = "DELETE FROM NHANVIEN WHERE MANV='" + id + "'";
//            c.executeUpdate(delSql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public int xoaNV(String id) {
        try {
            c.connect();

            String checkSql = "SELECT COUNT(*) FROM HOADON WHERE MANV = '" + id + "'";
            ResultSet rs = c.executeQuery(checkSql);

            if (rs.next() && rs.getInt(1) > 0) {
                String updateSql = "UPDATE NHANVIEN SET TRANGTHAI = 0 WHERE MANV = '" + id + "'";
                c.executeUpdate(updateSql);
                return 0; 
            } else {
                String delSql = "DELETE FROM NHANVIEN WHERE MANV = '" + id + "'";
                c.executeUpdate(delSql);
                return 1; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        } finally {
            c.disconnect();
        }
    }

    public void capNhatTrangThai(String id) {
        try {
            c.connect();
            String updateSql = "UPDATE NHANVIEN SET TRANGTHAI = 0 WHERE MANV='" + id + "'";
            c.executeUpdate(updateSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        nhanVienDAO dao = new nhanVienDAO();
        ArrayList<nhanVienDTO> ds = dao.ds_nhanVien();

//        dao.xoaNV("AD1");
        for (nhanVienDTO c : ds) {
            System.out.println(c.getTENNV() + " " + c.getCHUCVU() + "  " + c.getEMAIL() + "  " + c.getTRANGTHAi());
            if (c.getMANV().equals("QL4")) {
                c.setDIACHI("Quận 1 TPHCM");
                dao.capnhatNV(c);
            }
        }

    }
}
