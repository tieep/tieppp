package DAO;

import DTO.KhoDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

public class KhoDAO {

    private ConnectDataBase mySQL;

    public KhoDAO() {
        try {
            mySQL = new ConnectDataBase();
        } catch (SQLException e) {
            System.out.println("That bai");
        }
    }

    public ArrayList<KhoDTO> list(ArrayList<String> data_filters) {
        String tuNgay = data_filters.get(0);
        String denNgay = data_filters.get(1);
        ArrayList<KhoDTO> ds = new ArrayList<>();
        try {
            mySQL.connect();
            String sql = "SELECT \n"
                    + "    sp.MASP, \n"
                    + "    loai.TENLOAI, \n"
                    + "    sp.TENSP, \n"
                    + "    size.TENSIZE, \n"
                    + "    -- Số lượng tồn hiện tại\n"
                    + "    COALESCE(ctsp.SOLUONG, 0) AS SLTon,  \n"
                    + "    -- Số lượng nhập trong khoảng thời gian\n"
                    + "    COALESCE(SUM(ctpn.SOLUONG), 0) AS SLNhap,\n"
                    + "    -- Số lượng bán trong khoảng thời gian\n"
                    + "    COALESCE(SUM(cthd.SOLUONG), 0) AS SLXuat\n"
                    + "FROM \n"
                    + "    sanpham sp\n"
                    + "LEFT JOIN \n"
                    + "    loai ON sp.MALOAI = loai.MALOAI\n"
                    + "LEFT JOIN \n"
                    + "    chitietsanpham ctsp ON sp.MASP = ctsp.MASP\n"
                    + "LEFT JOIN \n"
                    + "    size ON ctsp.MASIZE = size.MASIZE\n"
                    + "LEFT JOIN \n"
                    + "    chitietphieunhap ctpn ON sp.MASP = ctpn.MASP AND ctsp.MASIZE = ctpn.MASIZE\n"
                    + "LEFT JOIN \n"
                    + "    phieunhap pn ON ctpn.MAPN = pn.MAPN AND pn.NGAYNHAP BETWEEN '" + tuNgay + "' AND '" + denNgay + "'  -- Chỉ tính số lượng nhập trong khoảng thời gian\n"
                    + "LEFT JOIN \n"
                    + "    chitiethoadon cthd ON sp.MASP = cthd.MASP AND ctsp.MASIZE = cthd.MASIZE\n"
                    + "LEFT JOIN \n"
                    + "    hoadon hd ON cthd.SOHD = hd.SOHD AND hd.NGAYHD BETWEEN '2024-09-05' AND '2024-09-05'  -- Chỉ tính số lượng bán trong khoảng thời gian\n"
                    + "GROUP BY \n"
                    + "    sp.MASP, \n"
                    + "    loai.TENLOAI, \n"
                    + "    sp.TENSP, \n"
                    + "    size.TENSIZE, \n"
                    + "    ctsp.SOLUONG;\n";
            ResultSet rs = mySQL.executeQuery(sql);
            while (rs.next()) {
                String maSP = rs.getNString("MASP");
                String tenLoai = rs.getNString("TENLOAI");
                String tenSP = rs.getNString("TENSP");
                String size = rs.getNString("TENSIZE");
                int slTon = rs.getInt("SLTon");
                int slNhap = rs.getInt("SLNhap");
                int slXuat = rs.getInt("SLXuat");
                KhoDTO kho = new KhoDTO(maSP, tenLoai, tenSP, size, slTon, slNhap, slXuat);
                ds.add(kho);
            }
            rs.close();
            mySQL.disconnect();

        } catch (SQLException ex) {

        }
        return ds;
    }

    public static void main(String[] args) {
        KhoDAO k = new KhoDAO();
        ArrayList<String> currentday = new ArrayList<>();
        currentday.add("2024/09/05");
        currentday.add("2024/09/05");
        System.out.println(k.list(currentday).size());
//        System.out.println(k.list().get(0).getTonDK());
    }
}
