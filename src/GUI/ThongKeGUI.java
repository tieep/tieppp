package GUI;

import BUS.loaiSPBUS;
import DAO.ConnectDataBase;
import DTO.ThongKeDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class ThongKeGUI extends JPanel {

    private ConnectDataBase mySQL;

    JPanel pnHeader;
    JPanel pnDoanhThu;
    double doanhThu;
    JTable table;
    String[] header = {"Mã sản phẩm", "Mã loại", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành Tiền"};
    ArrayList<ThongKeDTO> ds;
    DefaultTableModel df;
    Font fontHeader = new Font("Tahoma", Font.BOLD, 20);
    Font font = new Font("Tahoma", Font.BOLD, 13);
    int width, height;
    JLabel lblHeader;

    // Định dạng sử dụng dấu phân cách hàng nghìn
    DecimalFormat FormatInt = new DecimalFormat("#,###");

    // Định dạng với dấu phân cách hàng nghìn và 2 số thập phân
    DecimalFormat FormatDouble = new DecimalFormat("#,###.##");

    public ThongKeGUI(int width, int height) {
        this.width = width;
        this.height = height;

        try {
            mySQL = new ConnectDataBase();
        } catch (SQLException e) {
            System.out.println("That bai");
        }

        LocalDate ngayHienTai = LocalDate.now();

        // Định dạng ngày theo định dạng dd/MM/yyyy
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String ngayDinhDang = ngayHienTai.format(dinhDang);

        init(width, height);

    }

    public void tinhDoanhThu() {
        int sum = 0;
        for (int i = 0; i < ds.size(); i++) {
            sum += ds.get(i).getThanhTien();
        }
        this.doanhThu = sum;
    }

    public void ShowdoanhThu(ArrayList<String> data_filters) {
        lblHeader.setText("THỐNG KÊ DOANH THU");
        String tuNgay = data_filters.get(0);
        String denNgay = data_filters.get(1);
        String loai = data_filters.get(2);
        loaiSPBUS loaiBUS = new loaiSPBUS();
        for (int i = 0; i < loaiBUS.getListFull().size(); i++) {
            if (loaiBUS.getListFull().get(i).getTENLOAI().equals(loai)) {
                loai = loaiBUS.getListFull().get(i).getMALOAI();
            }
        }
        df.setRowCount(0);
        ds = new ArrayList<>();
        String query = "SELECT * FROM"
                + "("
                + "    SELECT sp.MASP, sp.MALOAI,sp.TENSP,SUM(cthd.SOLUONG) AS SL,"
                + "           sp.PRICE, sp.PRICE * SUM(cthd.SOLUONG) AS TT"
                + "    FROM hoadon hd"
                + "    INNER JOIN chitiethoadon cthd ON hd.SOHD = cthd.SOHD"
                + "    INNER JOIN sanpham sp ON sp.MASP = cthd.MASP"
                + "    WHERE hd.NGAYHD BETWEEN '" + tuNgay + "' AND '" + denNgay + "'"
                + "    GROUP BY sp.MASP, sp.MALOAI, sp.TENSP, sp.PRICE"
                + ")"
                + "AS subquery ";
        if (!loai.equalsIgnoreCase("Tất cả")) {
            query += "where subquery.maloai ='" + loai + "'";
        }
        System.out.println(query);
        try {
            mySQL.connect();
            ResultSet rs = mySQL.executeQuery(query);
            while (rs.next()) {
                String maSP = rs.getString("MASP");
                String maLoai = rs.getString("MALOAI");
                String tenSP = rs.getString("TENSP");
                int soLuong = rs.getInt("SL");
                double donGia = rs.getDouble("PRICE");
                double thanhTien = rs.getDouble("TT");
                ThongKeDTO thongKe = new ThongKeDTO(maSP, maLoai, tenSP, soLuong, donGia, thanhTien);
                ds.add(thongKe);
            }
            rs.close();
            mySQL.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ThongKeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("danh sach thong ke " + ds.size());
        for (ThongKeDTO row : ds) {
            //gọi lại hàm format cho cách hiện thị cho các số có giá trị quá lớn hoặc nhở
            String donGia = FormatDouble.format(row.getDonGia());
            String soLuong = FormatInt.format(row.getSoLuong());
            String thanhTien = FormatDouble.format(row.getThanhTien());
            Object[] data = {row.getMaSP(), row.getMaLoai(), row.getTenSP(), soLuong, donGia, thanhTien};
            df.addRow(data);
        }
        table.setModel(df);
        df.fireTableDataChanged();
        tinhDoanhThu();
    }

    public void ShowbanChay(ArrayList<String> data_filters) {
        lblHeader.setText("SẢN PHẨM BÁN CHẠY");
        String tuNgay = data_filters.get(0);
        String denNgay = data_filters.get(1);
        int top = Integer.parseInt(data_filters.get(2));
        df.setRowCount(0);
        ds = new ArrayList<>();
        String query = "SELECT * FROM"
                + "("
                + "    SELECT sp.MASP, sp.MALOAI, sp.TENSP, SUM(cthd.SOLUONG) AS SL,"
                + "           sp.PRICE, sp.PRICE * SUM(cthd.SOLUONG) AS TT"
                + "    FROM hoadon hd"
                + "    INNER JOIN chitiethoadon cthd ON hd.SOHD = cthd.SOHD"
                + "    INNER JOIN sanpham sp ON sp.MASP = cthd.MASP"
                + "    WHERE hd.NGAYHD BETWEEN '" + tuNgay + "' AND '" + denNgay + "'"
                + "    GROUP BY sp.MASP, sp.MALOAI, sp.TENSP, sp.PRICE"
                + ")"
                + " AS subquery ";

        // Sắp xếp theo số lượng sản phẩm bán ra (SL) và giới hạn top 10
        query += "ORDER BY subquery.SL DESC LIMIT " + top;

        System.out.println(query);
        try {
            mySQL.connect();
            ResultSet rs = mySQL.executeQuery(query);
            while (rs.next()) {
                String maSP = rs.getString("MASP");
                String maLoai = rs.getString("MALOAI");
                String tenSP = rs.getString("TENSP");
                int soLuong = rs.getInt("SL");
                double donGia = rs.getDouble("PRICE");
                double thanhTien = rs.getDouble("TT");
                ThongKeDTO thongKe = new ThongKeDTO(maSP, maLoai, tenSP, soLuong, donGia, thanhTien);
                ds.add(thongKe);
            }
            rs.close();
            mySQL.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ThongKeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (ThongKeDTO row : ds) {
            //gọi lại hàm format cho cách hiện thị cho các số có giá trị quá lớn hoặc nhở
            String donGia = FormatDouble.format(row.getDonGia());
            String soLuong = FormatInt.format(row.getSoLuong());
            String thanhTien = FormatDouble.format(row.getThanhTien());
            Object[] data = {row.getMaSP(), row.getMaLoai(), row.getTenSP(),
                soLuong, donGia, thanhTien};
            df.addRow(data);
        }
        table.setModel(df);
        df.fireTableDataChanged();
    }

    public void initPnDoanhThu() {
        // Tính toán doanh thu trước
        tinhDoanhThu(); // Đảm bảo gọi tinhDoanhThu trước khi cập nhật nhãn
        if (pnDoanhThu == null) {
            pnDoanhThu = new JPanel(new FlowLayout());
            pnDoanhThu.setPreferredSize(new Dimension(width, 100));
        }

        String dt = FormatDouble.format(doanhThu); // Định dạng doanh thu
        JLabel lblDoanhThu = new JLabel("Tổng doanh thu: " + dt + " VNĐ");
        lblDoanhThu.setFont(font);

        pnDoanhThu.removeAll(); // Xóa các thành phần cũ để đảm bảo không bị trùng
        pnDoanhThu.add(lblDoanhThu); // Thêm nhãn mới
        this.add(pnDoanhThu, BorderLayout.SOUTH); // Đảm bảo thêm vào đúng vị trí

        this.revalidate(); // Cập nhật bố trí
        this.repaint(); // Vẽ lại giao diện
    }

    public void init(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());

        //Header
        pnHeader = new JPanel();
        pnHeader.setLayout(new FlowLayout());
        pnHeader.setBorder(new EmptyBorder(20, 0, 20, 0));
        lblHeader = new JLabel("THỐNG KÊ DOANH THU", JLabel.CENTER);
        lblHeader.setFont(fontHeader);
        pnHeader.add(lblHeader);
        this.add(pnHeader, BorderLayout.NORTH);

        //tạo bảng thống kê
        table = new JTable();
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(30); //thiết lập chiều cao các cột

        // Thiết lập dữ liệu cho JTable
        df = new DefaultTableModel(header, 0);

        table.setModel(df);

        // Canh giữa nội dung trong mỗi ô trong cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < header.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Đặt kích thước cho các cột
        TableColumnModel columnModel = table.getColumnModel();

        // Cột "Mã sản phẩm"
        columnModel.getColumn(0).setPreferredWidth(80);

        //Cột "Mã loại"
        columnModel.getColumn(1).setPreferredWidth(50);

        //Cột "Tên sản phẩm"
        columnModel.getColumn(2).setPreferredWidth(300);

        //cột "Số lượng"
        columnModel.getColumn(3).setPreferredWidth(60);

        //cột "Đơn giá"
        columnModel.getColumn(4).setPreferredWidth(90);

        //cột "Thành tiền"
        columnModel.getColumn(5).setPreferredWidth(150);

        // Bỏ kẻ dọc
        table.setShowVerticalLines(false);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 30));
        tableHeader.setBackground(Color.LIGHT_GRAY); // Đặt màu nền cho tiêu đề là màu xám nhạt
        tableHeader.setFont(font);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane jp = new JScrollPane(table);
        this.add(jp, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ThongKeGUI thongKeGUI = new ThongKeGUI(800, 600);
        ArrayList<String> currentday = new ArrayList<>();
        currentday.add("2023/01/01");
        currentday.add("2024/01/01");
        currentday.add("Tất cả");
        thongKeGUI.ShowdoanhThu(currentday);
        thongKeGUI.initPnDoanhThu();

//              ShowbanChay("2023/01/01", "2024/05/04", 1);
//        thongKeGUI.ShowbanChay(currentday);
        JFrame frame = new JFrame("Thống Kê Sản Phẩm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.add(thongKeGUI); // Thêm GUI vào JFrame
        frame.setVisible(true); // Hiển thị cửa sổ
    }
}
