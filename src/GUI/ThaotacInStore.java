package GUI;

import BUS.BUS_qlkh;
import BUS.ChitietHD_BUS;
import BUS.Hoadon_BUS;
import BUS.KhoBUS;
import BUS.Nhanvien_BUS;
import BUS.SanPhamBUS;
import BUS.SizeBUS;
import BUS.chitietphieunhap_BUS;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Cursor;
import java.awt.Component;

import javax.net.ssl.SSLContext;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.ArrayList;
import BUS.chitietquyenBUS;
import BUS.chitietsanpham_BUS;
import BUS.khachHangBUS;
import BUS.loaiSPBUS;
import BUS.nhacungcapBUS;

import BUS.nhanVienBUS;

import BUS.phieunhap_BUS;

import BUS.quyenBUS;
import DAO.chitietquyenDAO;
import DTO.chitietquyenDTO;
import DTO.ChitietHD_DTO;
import DTO.Hoadon_DTO;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;
import DTO.chitietphieunhap_DTO;
import DTO.chitietsanpham_DTO;
import DTO.khachHangDTO;
import DTO.loaiSP;
import DTO.model_qlkh;

import DTO.nhacungcapDTO;
import DTO.nhanVienDTO;
import java.awt.Container;
import java.awt.event.MouseAdapter;

import DTO.phieunhap_DTO;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class ThaotacInStore extends JPanel implements MouseListener {

    private ArrayList<chitietquyenDTO> listHanhdong;
    private ArrayList<String> titleHanhdong;
    // private String[] titleThaotac = {"Thêm", "Sửa", "Xóa", "Import Excel", "Export Excel", "In PDF"};
    private Font font_title;
    private String MACHUCNANG;
    private String MAQUYEN;
    private Component pageContent;
    private StoreScreen s;

    //kích thước trang tai khoan
    int widthTK;
    int heightTK;

    public ThaotacInStore(String MACHUCNANG, String MAQUYEN, Component pageContent, StoreScreen s) {
        this.s = s;
        this.pageContent = pageContent;
        this.MACHUCNANG = MACHUCNANG;
        this.MAQUYEN = MAQUYEN;
        listHanhdong = new ArrayList<>();
        font_title = new Font("Tahoma", Font.PLAIN, 14);
        init();

    }

    public ThaotacInStore(String MACHUCNANG, String MAQUYEN, Component pageContent) {
        this.pageContent = pageContent;
        this.MACHUCNANG = MACHUCNANG;
        this.MAQUYEN = MAQUYEN;
        listHanhdong = new ArrayList<>();
        font_title = new Font("Tahoma", Font.PLAIN, 14);
        init();

    }

    private void init() {

        //setPreferredSize(new Dimension(chieurong, 150));
        listHanhdong = listHANHDONG(MACHUCNANG, MAQUYEN);
        if (!listHanhdong.isEmpty()) {
            JPanel jp_title = new JPanel();
            jp_title.setLayout(new BorderLayout());
            jp_title.setBackground(Cacthuoctinh_phuongthuc_chung.light_gray);
            jp_title.setOpaque(true);
            JLabel jl_title = new JLabel("<html><h1 style='font-family:Tahoma;font-weight:600;font-size:16;'>Thao tác</h1></html>");
            jp_title.add(jl_title, BorderLayout.WEST);

            JPanel x = new JPanel();

            x.setLayout(new FlowLayout(3, 5, 0));
            x.setBackground(Cacthuoctinh_phuongthuc_chung.light_gray);
            x.setOpaque(true);
//        x.setPreferredSize(new Dimension(chieurong, 80));
            for (int i = 0; i < listHanhdong.size(); i++) {
                String pathImangeIcon = "./src/images/";
                switch (listHanhdong.get(i).getHANHDONG()) {
                    case "Thêm":
                        pathImangeIcon += "add_icon.png";
                        break;
                    case "Sửa":
                        pathImangeIcon += "edit_icon.png";
                        break;
                    case "Xóa":
                        pathImangeIcon += "remove_icon.png";
                        break;
                    case "Import Excel":
                        pathImangeIcon += "import_icon.png";
                        break;
                    case "Export Excel":
                        pathImangeIcon += "export_icon.png";
                        break;
                    case "In PDF":
                        pathImangeIcon += "pdf_icon.png";
                        break;
//                    case "Xem biểu đồ":
//                        pathImangeIcon += "graph_icon.png";
//                        break;

                }
                hanhdongGUI thaotac = new hanhdongGUI(listHanhdong.get(i).getHANHDONG(), pathImangeIcon);
                thaotac.setName(listHanhdong.get(i).toString());
                thaotac.addMouseListener(this);
                x.add(thaotac);
            }
            setLayout(new FlowLayout(3, 0, 0));
            //setBackground(Cacthuoctinh_phuongthuc_chung.light_gray);
            jp_title.setPreferredSize(new Dimension((int) jl_title.getPreferredSize().getWidth() + 10, (int) jl_title.getPreferredSize().getHeight()));
            add(jp_title);
            x.setPreferredSize(new Dimension((int) x.getPreferredSize().getWidth(), 70));
            add(x);
            setBackground(Cacthuoctinh_phuongthuc_chung.light_gray);
            setOpaque(true);
        }
    }

    private ArrayList<chitietquyenDTO> listHANHDONG(String MACHUCNANG, String MAQUYEN) {
        ArrayList<chitietquyenDTO> hanhdong = new ArrayList<>();
        if (!MACHUCNANG.contains("NULL")) {
            System.out.println("MACHUCNANG" + MACHUCNANG);
            chitietquyenDAO ctqDAO = new chitietquyenDAO();
            String query = "SELECT * FROM chitietquyen WHERE MAQUYEN='" + MAQUYEN + "' AND MACHUCNANG='" + MACHUCNANG + "'";

            ArrayList<chitietquyenDTO> listChitietquyen = ctqDAO.searchReturnArray(query);
            for (chitietquyenDTO i : listChitietquyen) {
                if (!i.getHANHDONG().equals("Xem") && !(i.getMACHUCNANG().equals("HD") && i.getHANHDONG().equals("Thêm"))) {
                    hanhdong.add(i);
                }
            }
        }
        if (MACHUCNANG.equals("HD")) {
            hanhdong.add(new chitietquyenDTO(MAQUYEN, MACHUCNANG, "In PDF"));
        }
        if (MACHUCNANG.equals("NCC")) {
            hanhdong.add(new chitietquyenDTO(MAQUYEN, MACHUCNANG, "Import Excel"));
        }
//        if (MACHUCNANG.equals("NULLThK")) {
//            hanhdong.add(new chitietquyenDTO(MAQUYEN, MACHUCNANG, "Xem biểu đồ"));
//        }
        return hanhdong;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        hanhdongGUI itemClicked = (hanhdongGUI) e.getSource();
        String chitietquyen = itemClicked.getName();
        chitietquyenDTO ctqDTO = new chitietquyenDTO(chitietquyen);
        switch (ctqDTO.getMACHUCNANG()) {
            case "TK": {
                chucnangTaikhoan cntk_p = (chucnangTaikhoan) pageContent;
                widthTK = (int) cntk_p.JP_contentCuaNameChucnangCon.getPreferredSize().getWidth() - 10;
                heightTK = (int) cntk_p.JP_contentCuaNameChucnangCon.getPreferredSize().getHeight() - 10;
                switch (ctqDTO.getHANHDONG()) {
                    case "Thêm":
                        TaiKhoanGUI a = new TaiKhoanGUI(widthTK, heightTK);
                         {
                            try {
                                a.initPnThaoTacTK(450, 600);
                            } catch (SQLException ex) {
                                java.util.logging.Logger.getLogger(ThaotacInStore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }
                        }
                        a.initThem();
                        cntk_p.JP_contentCuaNameChucnangCon.removeAll();
                        cntk_p.JP_contentCuaNameChucnangCon.add(a, BorderLayout.CENTER);
                        cntk_p.JP_contentCuaNameChucnangCon.revalidate();
                        cntk_p.JP_contentCuaNameChucnangCon.repaint();
                        cntk_p.tkGUI = a;
                        break;

                    case "Sửa":
                        cntk_p.tkGUI.remove(cntk_p.tkGUI.pnThaoTacTK_main);
                        cntk_p.tkGUI.revalidate(); // Cập nhật lại container
                        cntk_p.tkGUI.repaint();
                        if (cntk_p.tkGUI.selectedTK.getMaNV() == null) {
                            JOptionPane.showMessageDialog(null,
                                    "Xin vui lòng chọn tài khoản cần sửa !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        } else {
                            TaiKhoanGUI b = new TaiKhoanGUI(widthTK, heightTK);
                            b.selectedTK = cntk_p.tkGUI.selectedTK;
                            try {
                                b.initPnThaoTacTK(450, 600);
                            } catch (SQLException ex) {
                                java.util.logging.Logger.getLogger(ThaotacInStore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }
                            b.initSua();
                            cntk_p.JP_contentCuaNameChucnangCon.removeAll();
                            cntk_p.JP_contentCuaNameChucnangCon.add(b, BorderLayout.CENTER);
                            cntk_p.JP_contentCuaNameChucnangCon.revalidate();
                            cntk_p.JP_contentCuaNameChucnangCon.repaint();
                            cntk_p.tkGUI = b;
                        }
                        break;
                    case "Xóa":
                        if (cntk_p.tkGUI.selectedTK.getMaNV() == null) {
                            JOptionPane.showMessageDialog(null,
                                    "Xin vui lòng chọn tài khoản cần xoá !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        } else {
                            Object[] options = {"Có", "Không"};
                            int result = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn muốn xoá tài khoản này ?", "Xác nhận xoá",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]
                            );

                            // Xử lý kết quả
                            if (result == JOptionPane.YES_OPTION) {
                                TaiKhoanGUI b = new TaiKhoanGUI(widthTK, heightTK);
                                b.selectedTK = cntk_p.tkGUI.selectedTK;
                                b.DeleteTK();
                                cntk_p.JP_contentCuaNameChucnangCon.removeAll();
                                cntk_p.JP_contentCuaNameChucnangCon.add(b, BorderLayout.CENTER);
                                cntk_p.JP_contentCuaNameChucnangCon.revalidate();
                                cntk_p.JP_contentCuaNameChucnangCon.repaint();
                                cntk_p.tkGUI = b;
                                JOptionPane.showMessageDialog(null,
                                        "Bạn đã xoá tài khoản thành công!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        break;
                }
                break;
            }
            case "PQ": {
                thaotacPQ(ctqDTO.getHANHDONG(), itemClicked);
                break;
            }
            case "NCC": {
                thaotacNCC(ctqDTO.getHANHDONG(), itemClicked);
                break;
            }
            case "LOAI": {
                thaotacLOAI(ctqDTO.getHANHDONG(), itemClicked);
                break;
            }
            case "KH": {
                thaotacKH(ctqDTO.getHANHDONG(), itemClicked);
                break;
            }
            case "SIZE": {
                thaotacSIZE(ctqDTO.getHANHDONG(), itemClicked);
                break;
            }
            case "NV": {
                thaotacNV(ctqDTO.getHANHDONG(), itemClicked);
                break;
            }
            case "PN": {
                try {
                    thaotacPN(ctqDTO.getHANHDONG(), itemClicked);
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(ThaotacInStore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                break;
            }
            case "SP": {
                thaotacSP(ctqDTO.getHANHDONG(), itemClicked);
                break;
            }
            case "HD": {
                chucnangHoadon hdGUI = (chucnangHoadon) pageContent;
                JPanel jp_content = hdGUI.JP_contentCuaNameChucnangCon;
                Component[] jp_con = jp_content.getComponents();
                TrangLichsuHD lshd = (TrangLichsuHD) jp_con[0];
                String mahd = lshd.MAHDSelect;
                BUS_qlkh khBUS = new BUS_qlkh();
                Hoadon_BUS hdBUS = null;
                ChitietHD_BUS cthdBUS = null;
                chitietsanpham_BUS ctspBUS = null;

                try {
                    hdBUS = new Hoadon_BUS();
                    cthdBUS = new ChitietHD_BUS(mahd);
                    ctspBUS = new chitietsanpham_BUS();
                } catch (SQLException ex) {
                }
                Hoadon_DTO hdSelect = hdBUS.searchHoadon_DTO(mahd);
                switch (ctqDTO.getHANHDONG()) {
                    case "Xóa":
                        if (lshd.currentSelectedPanel == null) {
                            JOptionPane.showMessageDialog(null, "Chọn hóa đơn cần xóa!");
                        } else {
                            if (!hdBUS.checkInDatetimeValid(hdSelect)) {
                                JOptionPane.showMessageDialog(null, "Đã vượt quá 24 tiếng kể lúc hóa đơn được in!\nKhông thể xóa!");
                                return;
                            }
                            Object[] options = {"Có", "Không"};
                            int r1 = JOptionPane.showOptionDialog(null, "Bạn chắc chắn muốn xóa?", "Xóa hóa đơn", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            if (r1 == JOptionPane.YES_OPTION) {
                                //1. hoan so luong ve kho

                                //2. render UI trangLSHD
                                //3. hoan diem tich luy
                                //4. xoa trong hoa don
                                //5. xoa trong chi tiet hoa don
                                try {

                                    hdBUS.delete(mahd);

                                    lshd.left.remove(lshd.currentSelectedPanel);
                                    lshd.right.removeAll();

//                                    lshd.init();
                                    lshd.revalidate();
                                    lshd.repaint();
                                    JOptionPane.showMessageDialog(null,
                                            "Xóa thành công!");
                                    if (hdSelect != null) {
                                        for (model_qlkh kh : khBUS.getlist()) {
                                            if (kh.getMakh() == hdSelect.getMaKH()) {
                                                if (hdSelect.getGiamgia() == 0) {
                                                    kh.setDiem(kh.getDiem() - hdSelect.getTongTien() / 10000);
                                                } else {
                                                    kh.setDiem(kh.getDiem() + hdSelect.getGiamgia() / 1000 - hdSelect.getTongTien() / 10000);
                                                }
                                                khBUS.set(kh);
                                                break;
                                            }
                                        }
                                    }

                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null,
                                            "Xóa thất bại!");
                                    java.util.logging.Logger.getLogger(ThaotacInStore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                }

                                for (ChitietHD_DTO ctDTO : cthdBUS.getList()) {
                                    for (chitietsanpham_DTO ctspDTO : ctspBUS.getlist()) {
                                        if (ctDTO.getMaSP().equals(ctspDTO.getMASP()) && ctDTO.getMaSize().equals(ctspDTO.getMASIZE())) {

                                            ctspDTO.setSoluong(ctspDTO.getSoluong() + ctDTO.getSl());

                                            try {
                                                ctspBUS.update(ctspDTO);
                                                cthdBUS.delete(mahd, ctDTO.getMaSP(), ctDTO.getMaSize());

                                            } catch (SQLException ex) {
                                                java.util.logging.Logger.getLogger(ThaotacInStore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                            }
                                            break;
                                        }

                                    }

                                }

                            }
                        }
                        break;

                    case "Sửa":
                        if (lshd.currentSelectedPanel == null) {
                            JOptionPane.showMessageDialog(null, "Chọn hóa đơn cần sửa!");
                        } else {
                            if (!hdBUS.checkInDatetimeValid(hdSelect)) {
                                JOptionPane.showMessageDialog(null, "Đã vượt quá 24 tiếng kể lúc hóa đơn được in!\nKhông thể sửa!");
                                return;
                            }
                            JPanel rightLSHD = lshd.right;
                            Component[] components = rightLSHD.getComponents();
                            ChitietHD_GUI cthd = (ChitietHD_GUI) components[0];

                            switch (itemClicked.title.getText()) {
                                case "Sửa":
                                    JOptionPane.showMessageDialog(null, "Bạn đang thực hiện chỉnh sửa hóa đơn!");
                                    JOptionPane.showMessageDialog(null, "Chỉ có thể thay đổi size và số lượng sản phẩm trong chi tiết hóa đơn\nẤn Lưu/Thoát khi đã hoàn tất chỉnh sửa!");
                                    cthd.table.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                                    itemClicked.title.setText("Lưu/Thoát");
                                    itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));
//                                  
                                    cthd.isEditingEnabled = true;
                                    cthd.doAfterThaotacUpdate(lshd.currentSelectedPanel);
                                    break;
                                case "Lưu/Thoát":
                                    Object[] options = {"Có", "Không"};
                                    int r1 = JOptionPane.showOptionDialog(null, "Bạn chắc chắn muốn lưu thay đổi?", "Sửa hóa đơn", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                    if (r1 == JOptionPane.YES_OPTION) {

                                        JOptionPane.showMessageDialog(null, "Lưu thay đổi hóa đơn thành công!");
                                        for (model_qlkh kh : khBUS.getlist()) {
                                            System.out.println("khach hang == " + (kh.getMakh() == cthd.maKH));
                                            if (kh.getMakh() == cthd.maKH) {
                                                System.out.println("Diem moi " + cthd.diemNew);
                                                kh.setDiem(cthd.diemNew);
                                                khBUS.set(kh);
                                                break;
                                            }
                                        }
                                        //System.out.println("bat dau luu hoa don");
                                        for (Hoadon_DTO hd : hdBUS.dshoadon) {
                                            if (hd.getMaHD().equals(mahd)) {
                                                hd.setGiamgia(cthd.giamgia);
                                                hd.setTongTien(cthd.tongtien);
                                                hdBUS.update(hd);
                                                break;
                                            }
                                        }
                                        //System.out.println("bat dau luu chi tiet hoa don");
                                        for (ChitietHD_DTO ct : cthd.listCTHDCurrent) {
                                            cthdBUS.update(ct);
                                        }
                                        ArrayList<chitietsanpham_DTO> listCTSP = ctspBUS.getlist();
                                        for (int i = 0; i < cthd.listCTSPBefore.size(); i++) {
                                            chitietsanpham_DTO s = cthd.listCTSPBefore.get(i);
                                            for (ChitietHD_DTO ct : cthd.listCTHDCurrent) {
                                                if (s.getMASP().equals(ct.getMaSP()) && s.getMASIZE().equals(ct.getMaSize())) {
                                                    s.setSoluong(s.getSoluong() - ct.getSl());
                                                }

                                                break;
                                            }
                                            if (listCTSP.get(i).getMASP().equals(s.getMASP()) && listCTSP.get(i).getMASIZE().equals(s.getMASIZE()) && listCTSP.get(i).getSoluong() != s.getSoluong()) {
                                                try {

                                                    ctspBUS.update(s);

                                                } catch (SQLException ex) {
                                                    java.util.logging.Logger.getLogger(ThaotacInStore.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                                }
                                            }

                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Hóa đơn không thay đổi!");
                                        Hoadon_DTO hd = hdBUS.searchHoadon_DTO(mahd);
                                        Component[] JL_Child = cthd.HoadonSelected.getComponents();
                                        ((JLabel) JL_Child[5]).setText(formatPrice(hd.getGiamgia() + ""));
                                        ((JLabel) JL_Child[6]).setText(formatPrice(hd.getTongTien() + ""));
                                        cthd.addDataInTable(cthdBUS.getList());
                                        cthd.valueInPayment[0].setText(formatPrice(hd.getGiamgia() + hd.getTongTien() + "") + " đ");
                                        cthd.valueInPayment[1].setText(formatPrice(hd.getGiamgia() + "") + " đ");
                                        cthd.valueInPayment[2].setText(formatPrice(hd.getTongTien() + "") + " đ");

                                    }
                                    cthd.isEditingEnabled = false;
                                    cthd.table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                                    itemClicked.title.setText("Sửa");
                                    itemClicked.icon = new JLabel(new ImageIcon("./src/image/edit_icon.png"));

                            }
                        }

                        break;

                    case "In PDF": {

                        if (lshd.currentSelectedPanel == null) {
                            JOptionPane.showMessageDialog(null, "Chọn vào hóa đơn cần in!\nSau đó ấn In PDF");
                        } else {
                            Object[] options = {"Có", "Không"};
                            int r1 = JOptionPane.showOptionDialog(null, "Bạn muốn in hóa đơn đang chọn?", "In hóa đơn", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            if (r1 == JOptionPane.YES_OPTION) {
                                try {
                                    inPDF in = new inPDF(lshd.MAHDSelect);
                                    JOptionPane.showMessageDialog(null,
                                            "In thành công!");
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null,
                                            "In thất bại!");
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null,
                                            "In thất bại!");
                                }

                            }

                        }
                        break;
                    }
                }
                break;
            }

            case "NULLThK": {
                chucnangThongke hdGUI = (chucnangThongke) pageContent;
                JPanel search = s.centerContent.search;
                Component[] JP_childSearch = search.getComponents();
                SearchInStore searchInS = (SearchInStore) JP_childSearch[0];
                ArrayList<Component> listComponentTimkiem = searchInS.listComponentTimkiem;
                ArrayList<String> data_filter = new ArrayList<>();
                for (Component c : listComponentTimkiem) {
                    if (c instanceof JTextField) {
                        JTextField text = (JTextField) c;
                        data_filter.add(text.getText());
                    } else if (c instanceof JComboBox<?>) {
                        JComboBox<String> comboBox = (JComboBox<String>) c;
                        String selectedItem = (String) comboBox.getSelectedItem();

                        data_filter.add(selectedItem);

                    } else {
                        JSpinner date = (JSpinner) c;
                        Date selectedDate = (Date) date.getValue();
                        // Định dạng ngày tháng năm thành chuỗi
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String dateString = dateFormat.format(selectedDate);
                        // In ra giá trị đã chọn dưới dạng chuỗi
                        data_filter.add(dateString);
                    }
                }

                switch (hdGUI.thongkeloai) {
                    case 0: {
                        ThongKeGUI tkGUI = new ThongKeGUI(800, 600);
                        tkGUI.ShowdoanhThu(data_filter);
                        break;
                    }

                    case 1:
                        ThongKeGUI tkGUI = new ThongKeGUI(800, 600);
                        tkGUI.ShowbanChay(data_filter);
                        break;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JPanel item = (JPanel) e.getSource();
        item.setBackground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Component[] components = item.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setForeground(Color.WHITE);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JPanel item = (JPanel) e.getSource();
        item.setBackground(Color.WHITE);
        Component[] components = item.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setForeground(Color.BLACK);
            }
        }
    }

    public void thaotacSP(String hanhdong, hanhdongGUI itemClicked) {
        SanPhamGUI spGUI = (SanPhamGUI) pageContent;
        SanPhamBUS spBUS = new SanPhamBUS();
        switch (hanhdong) {
            case "Thêm": {
                ChucNangSanPhamGUI sp = new ChucNangSanPhamGUI(spGUI, 500, 600);
                sp.initThem();
                break;
            }
            case "Sửa": {
                if (spGUI.selectedSP.getMaSP() == null) {
                    JOptionPane.showMessageDialog(null,
                            "Xin vui lòng chọn sản phẩm cần sửa !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    ChucNangSanPhamGUI sp = new ChucNangSanPhamGUI(spGUI, 500, 700);
                    sp.initSua();
                    sp.setTT();
                }
                break;
            }
            case "Xóa": {
                if (spGUI.selectedSP.getMaSP() == null) {
                    JOptionPane.showMessageDialog(null,
                            "Xin vui lòng chọn sản phẩm cần xoá !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    Object[] options = {"Có", "Không"};
                    int result = JOptionPane.showOptionDialog(
                            null,
                            "Bạn có chắc chắn muốn xoá sản phẩm này?", // Nội dung thông báo
                            "Xác nhận xoá", // Tiêu đề
                            JOptionPane.YES_NO_OPTION, // Tùy chọn Yes/No
                            JOptionPane.QUESTION_MESSAGE, // Biểu tượng dấu hỏi
                            null,
                            options,
                            options[0]
                    );

                    // Xử lý kết quả
                    if (result == JOptionPane.YES_OPTION) {
                        spGUI.DeleteSP();
                        JOptionPane.showMessageDialog(null,
                                "Bạn đã xoá sản phẩm thành công!", "Thông báo", JOptionPane.DEFAULT_OPTION);
                    } else {
                        spGUI.selectedSP = new SanPhamDTO();
                        spGUI.clear(-1);
                    }
                }
                break;
            }

        }
    }

    public void thaotacPN(String hanhdong, hanhdongGUI itemClicked) throws SQLException {
        phieunhap_GUI pnGUI = (phieunhap_GUI) pageContent;

        TaiKhoanDTO tk = s.tkUSER;

        phieunhap_BUS pnBUS = new phieunhap_BUS();
        switch (hanhdong) {
            case "Thêm": {
                pnGUI.frame_them_phieunhap = new frame_them_phieunhap(800, 500, pnGUI, tk);

                // Hiển thị frame_them_phieunhap mới
//                JFrame addFrame = new JFrame("Thêm Phiếu Nhập");
//                addFrame.setSize(800, 600);
//                addFrame.setLocationRelativeTo(null);
//                addFrame.add(pnGUI.frame_them_phieunhap.getContentPane()); // Thêm nội dung panel vào frame
//                addFrame.setVisible(true);
                break;
            }
            case "Sửa": {
            ArrayList<String> selectedListPN = pnGUI.getSelectedListPN();
            if (!selectedListPN.isEmpty()) {
                String selectedMAPN = selectedListPN.get(0); 
                if (selectedMAPN != null) {
                    
                    phieunhap_DTO selectedPhieuNhap = pnBUS.select_by_id1(selectedMAPN);
                    System.err.println("ngya"+selectedPhieuNhap);
                    chitietphieunhap_BUS ctpnBUS=new chitietphieunhap_BUS(selectedPhieuNhap);
                    ArrayList<chitietphieunhap_DTO> ctpn=ctpnBUS.select_by_id(selectedPhieuNhap);
                    frame_sua_pn fr=new frame_sua_pn(400,600,selectedPhieuNhap, pnGUI,ctpn);
                    
                }
 /*NOte: Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException: Cannot invoke "GUI.chitietphieunhap_GUI.set_tongtien()" because "this.chitietphieunhap_GUI" is null
         hình như ở đây do tui tạo 1 cái JFrame mới nên lúc ấn xác nhận cái gui truyền vô nó k đúng
                */       
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn vào phiếu nhập bạn muốn sửa.");
            }
            break;
        }

            case "Xóa": {
    switch (itemClicked.title.getText()) {
        case "Xóa":
            JOptionPane.showMessageDialog(null, "Để chọn nhiều ô cần xóa:\nKéo chuột\nGiữ Ctrl và click vào các ô cần xóa");
            itemClicked.title.setText("Lưu/Thoát");
            itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));
            break;
        case "Lưu/Thoát":
            ArrayList<String> listDelete = pnGUI.getSelectedListPN1();
            if (listDelete.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn phiếu nhập cần xóa.");
                return;
            }

            // Kiểm tra thời gian có còn hợp lệ (24 giờ)
            for (String mapn : listDelete) {
                phieunhap_DTO phieuNhap = pnBUS.select_by_id1(mapn);
                LocalDateTime ngayNhap = phieuNhap.getNgay().atStartOfDay();
                if (!isEditableWithin24Hours(ngayNhap)) {
                    JOptionPane.showMessageDialog(null, "Phiếu nhập " + mapn + " đã quá 24 giờ, không thể xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            KhoBUS khoBUS = new KhoBUS();
            // Kiểm tra sản phẩm trong kho
            for (String mapn : listDelete) {
                chitietphieunhap_BUS ctpnBUS = new chitietphieunhap_BUS(pnBUS.select_by_id1(mapn));
                ArrayList<chitietphieunhap_DTO> ctpnList = ctpnBUS.select_by_id(pnBUS.select_by_id1(mapn));

                for (chitietphieunhap_DTO ctpn : ctpnList) {
                    String masp = ctpn.getMasp();
                    String maSize=ctpn.getMasize();
                    int soluongNhap = ctpn.getSoluong();
                    
                    int soluongTonKho = khoBUS.getSoLuongTonKho(masp,maSize); // Lấy số lượng tồn kho hiện tại

                    if (soluongNhap > soluongTonKho) {
                        JOptionPane.showMessageDialog(null, "Phiếu nhập " + mapn + " chứa sản phẩm " + masp + " có số lượng nhiều hơn số lượng tồn kho, không thể xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
              SanPhamBUS spBUS=new SanPhamBUS();
            // Kiểm tra giá nhập và giá bán
            for (String mapn : listDelete) {
                chitietphieunhap_BUS ctpnBUS = new chitietphieunhap_BUS(pnBUS.select_by_id1(mapn));
                ArrayList<chitietphieunhap_DTO> ctpnList = ctpnBUS.select_by_id(pnBUS.select_by_id1(mapn));

                for (chitietphieunhap_DTO ctpn : ctpnList) {
                    double giaNhap = ctpn.getGianhap();
                    double giaBanHienTai = spBUS.getPrice(ctpn.getMasp()); // Lấy giá bán hiện tại
                    double tySuatTang = 1.2; // Ví dụ tỷ suất tăng giá là 20%
                      
                    
                        // Hiển thị thông báo yêu cầu nhập giá bán mới
                        JTextField giaBanMoiField = new JTextField(10);
                        giaBanMoiField.setEditable(false);
                        giaBanMoiField.setBorder(BorderFactory.createEmptyBorder());
                        JPanel panel = new JPanel();
                        
                        panel.add(new JLabel("Giá sản phẩm đang bán sẽ không thay đổi!"));
                        panel.add(giaBanMoiField);

                        int result = JOptionPane.showConfirmDialog(null, panel, "Lưu thay đổi?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            String giaBanMoiStr = giaBanMoiField.getText();
                            if (!giaBanMoiStr.isEmpty()) {
                                try {
                                    double giaBanMoi = Double.parseDouble(giaBanMoiStr);
                                    spBUS.updateGiaBan(ctpn.getMasp(), giaBanMoi); // Cập nhật giá bán mới
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null, "Giá bán mới không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                        }
                    
                }
            }

            Object[] options = {"Có", "Không"};
            int r2 = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn xóa?\nHành động này sẽ không thể hoàn tác", "Xóa phiếu nhập ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (r2 == JOptionPane.YES_OPTION) {
                for (String i : listDelete) {
                    pnBUS.delete(i);
                    pnBUS.deleteInSQL(i);
                }
                pnGUI.addDataInTable(pnBUS.dsPN());
                JOptionPane.showMessageDialog(null, "Xóa thành công");
            }
            itemClicked.title.setText("Xóa");
            itemClicked.icon = new JLabel(new ImageIcon("./src/images/remove_icon.png"));
            break;
    }
}

        }
    }

    public void thaotacPQ(String hanhdong, hanhdongGUI itemClicked) {
        phanquyen pq = (phanquyen) pageContent;
        quyenBUS qBUS = new quyenBUS();
        chitietquyenBUS ctqBUS = new chitietquyenBUS();
        switch (hanhdong) {
            case "Thêm":
                addQuyen addquyen = new addQuyen(pq);
                break;
            case "Sửa": {
                if (pq.currentQuyen.getMAQUYEN().equals("QQLHT")) {
                    JOptionPane.showMessageDialog(null, "Đây là quyền cao cấp.\nKhông thể sửa quyền này!");
                    return;
                }
                switch (itemClicked.title.getText()) {
                    case "Sửa":
                        //JOptionPane.showMessageDialog(null, "Double Click vào ô cần sửa thông tin\nKhông thể sửa đổi MANCC!\nTên nhà cung cấp không chứa chữ số và các kí tự đặc biệt\nSố điện thoại 10 số bắt đầu là số 0\nHoàn thành sửa đổi thì ấn nút Lưu");
                        JOptionPane.showMessageDialog(null, "Bạn đang thực hiện chỉnh sửa quyền\nẤn Lưu/Thoát khi đã hoàn tất chỉnh sửa!");
                        itemClicked.title.setText("Lưu/Thoát");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));

                        pq.updateTENQUYEN(pq.currentQuyen, 0);

                        pq.isEditingEnabled = true;
                        break;
                    case "Lưu/Thoát":
                        Object[] options = {"Có", "Không"};
                        int r2 = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn lưu chỉnh sửa?", "Sửa quyền ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (r2 == JOptionPane.YES_OPTION) {
                            itemClicked.title.setText("Sửa");
                            itemClicked.icon = new JLabel(new ImageIcon("./src/images/edit_icon.png"));

                            pq.updateTENQUYEN(pq.currentQuyen, 1);

                            qBUS.updateTENQUYEN(pq.currentQuyen);
                            ctqBUS.updateChitietquyen(pq.getListUpdateCtqTheoMAUQYEN(), pq.currentQuyen.getMAQUYEN());
                            pq.isEditingEnabled = false;

                            JOptionPane.showMessageDialog(null, "Lưu chỉnh sửa thành công!");

                            s.menu.removeAll();
                            s.menu.init();
                            s.menu.revalidate();
                            s.menu.repaint();

                        } else {
                            int r2_1 = JOptionPane.showOptionDialog(null, "Bạn có muốn tiếp tục chỉnh sửa?", "Sửa quyền ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            if (r2_1 == JOptionPane.NO_OPTION) {
                                int r2_2 = JOptionPane.showOptionDialog(null, "Những sửa đổi sẽ không được lưu sau khi bạn thoát!!\nBạn có chắc chắn thoát chỉnh sửa??", "Thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                if (r2_2 == JOptionPane.YES_OPTION) {

                                    itemClicked.title.setText("Sửa");
                                    itemClicked.icon = new JLabel(new ImageIcon("./src/images/edit_icon.png"));
                                    pq.updateTENQUYEN(pq.currentQuyen, 2);

                                    pq.isEditingEnabled = false;
                                }
                            }
                        }

                        break;
                }
                break;
            }
            case "Xóa": {
                if (pq.currentQuyen.getMAQUYEN().equals("QQLHT")) {
                    JOptionPane.showMessageDialog(null, "Đây là quyền cao cấp.\nKhông thể xóa quyền này!");
                    return;
                }
                Object[] options = {"Có", "Không"};
                int r2 = JOptionPane.showOptionDialog(null, "Chỉ có thể xóa quyền này khi không tồn tại tài khoản thuộc quyền này\nBạn có chắc chắn muốn xóa quyền đang chọn?", "Xóa quyền ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (r2 == JOptionPane.YES_OPTION) {
                    System.out.println(pq.currentQuyen);
                    System.out.println(qBUS.checkCanDelete(pq.currentQuyen));
                    if (qBUS.checkCanDelete(pq.currentQuyen)) {
                        qBUS.delete(pq.currentQuyen.getMAQUYEN());
                        pq.deleteJP_NameQuyen(pq.currentQuyen);

                        System.out.println("tencurrent quyen sau khi xoa tren thanh lau chon: " + pq.currentQuyen.toString());

                        JOptionPane.showMessageDialog(null, "Xóa quyền thành công");
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại do có tài khoản thuộc quyền này");
                    }
                }
                break;

            }
        }

    }

        public void thaotacNCC(String hanhdong, hanhdongGUI itemClicked) {
        nhacungcapGUI nccGUI = (nhacungcapGUI) pageContent;
        nhacungcapBUS nccBUS = new nhacungcapBUS();
        switch (hanhdong) {

            case "Thêm": {

                add_updateNhacungcapGUI addNCC = new add_updateNhacungcapGUI(nccGUI, "add");

                break;
            }
            case "Import Excel": {
                JOptionPane.showMessageDialog(null, "Chọn file excel .xlsx hoặc .xls cần import vào\nMỗi dòng trong file gồm 2 cột: TENNCC va SDT\nLưu ý: \nTên nhà cung cấp chỉ chứa chữ cái\nSố điện thoại theo định dạng 10 số");

                if (nccBUS.importExcelData(nccGUI)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại do dữ liệu không hợp lệ \nHoặc cấu trúc trong file khong đúng yêu cầu");
                }
            }
            case "Sửa": {
                MouseAdapter click = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (nccGUI.isEditingEnabled) {
                            // Xử lý sự kiện click chuột
                            int row = nccGUI.table.rowAtPoint(e.getPoint()); // Lấy chỉ số hàng của điểm click

                            // Kiểm tra xem sự kiện click có phải là click chuột trái
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                add_updateNhacungcapGUI n = new add_updateNhacungcapGUI(nccGUI, "update");

                            }
                        }

                    }
                };
                switch (itemClicked.title.getText()) {
                    case "Sửa":
                        JOptionPane.showMessageDialog(null, "Click vào dòng cần sửa thông tin\nKhông thể sửa đổi MANCC!\nTên nhà cung cấp không chứa chữ số và các kí tự đặc biệt\nSố điện thoại 10 số bắt đầu là số 0\nHoàn thành sửa đổi thì ấn nút Lưu");

                        itemClicked.title.setText("Lưu/Thoát");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));
                        nccGUI.isEditingEnabled = true;
                        nccGUI.listUpdate = new ArrayList<>();
                        nccGUI.table.addMouseListener(click);

                        break;
                    case "Lưu/Thoát":
                        Object[] options = {"Có", "Không"};
                        int r1 = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn lưu?", "Sửa nhà cung cấp ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (r1 == JOptionPane.YES_OPTION) {
                            if (nccGUI.listUpdate.size() != 0) {
                                for (nhacungcapDTO nccDTO : nccGUI.listUpdate) {
                                    nccBUS.updateInSQL(nccDTO);
                                }
                                JOptionPane.showMessageDialog(null, "Sửa thành công");
                            } else {
                                JOptionPane.showMessageDialog(null, "Không có dữ liệu nào mới để lưu thay đổi");
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Thông tin không thay đổi!");
                            nccGUI.addDataInTable(nccBUS.getList());
                        }
                        itemClicked.title.setText("Sửa");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/edit_icon.png"));
                        nccGUI.isEditingEnabled = false;
                        nccGUI.table.removeMouseListener(click);
                        nccGUI.listUpdate.clear();

                }

                break;
            }
            case "Xóa": {
                switch (itemClicked.title.getText()) {
                    case "Xóa":
                        JOptionPane.showMessageDialog(null, "Để chọn nhiều ô cần xóa:\nKéo chuột\nHoặc giữ Ctrl và click vào các ô cần xóa");
                        itemClicked.title.setText("Lưu/Thoát");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));
                        break;
                    case "Lưu/Thoát":
                        Object[] options = {"Có", "Không"};
                        int r2 = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn xóa?\nHành động này sẽ không thể hoàn tác", "Xóa nhà cung cấp ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (r2 == JOptionPane.YES_OPTION) {
                            ArrayList<nhacungcapDTO> listDelete = nccGUI.getSelectedListNCC();
                            if (listDelete.size() != 0) {
                                phieunhap_BUS pnBUS = new phieunhap_BUS();
                                for (nhacungcapDTO i : listDelete) {
                                    boolean flag = false;
                                    for (phieunhap_DTO pn : pnBUS.getList()) {
                                        if (pn.getMANCC().equals(i.getMANCC())) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    nccBUS.delete(i.getMANCC());
                                    if (!flag) {
                                        nccBUS.deleteInSQL(i.getMANCC());
                                    } else {
                                        i.setTRANGTHAI(0);
                                        nccBUS.updateInSQL(i);
                                    }

                                }
                                nccGUI.addDataInTable(nccBUS.getList());
                                JOptionPane.showMessageDialog(null, "Xóa thành công");
                            } else {
                                JOptionPane.showMessageDialog(null, "Không có dòng nào được chọn để xóa\nDữ liệu không thay đổi!");
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Dữ liệu không thay đổi");
                        }
                        itemClicked.title.setText("Xóa");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/remove_icon.png"));
                        break;
                }
            }

        }
    }

    public void thaotacLOAI(String hanhdong, hanhdongGUI itemClicked) {
        loaiSPGUI loaiGUI = (loaiSPGUI) pageContent;
        loaiSPBUS loaiBUS = new loaiSPBUS();
        switch (hanhdong) {
            case "Thêm": {

                add_updateLoaiSPGUI addNCC = new add_updateLoaiSPGUI(loaiGUI, "add");

                break;
            }
            case "Sửa": {
                MouseAdapter click = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (loaiGUI.isEditingEnabled) {
                            // Xử lý sự kiện click chuột
                            int row = loaiGUI.table.rowAtPoint(e.getPoint()); // Lấy chỉ số hàng của điểm click

                            // Kiểm tra xem sự kiện click có phải là click chuột trái
                            System.out.println("lam hien form");
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                add_updateLoaiSPGUI n = new add_updateLoaiSPGUI(loaiGUI, "update");

                            }
                        }

                    }
                };
                switch (itemClicked.title.getText()) {
                    case "Sửa":
                        JOptionPane.showMessageDialog(null, "Click vào dòng cần sửa thông tin\nKhông thể sửa đổi MALOAI!\nTên loại không chứa các kí tự đặc biệt");

                        itemClicked.title.setText("Lưu/Thoát");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));

                        loaiGUI.isEditingEnabled = true;
                        loaiGUI.listUpdate = new ArrayList<>();
                        loaiGUI.table.addMouseListener(click);
                        break;
                    case "Lưu/Thoát":
                        Object[] options = {"Có", "Không"};
                        int r1 = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn lưu?", "Sửa loại sản phẩm ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (r1 == JOptionPane.YES_OPTION) {

                            if (loaiGUI.listUpdate.size() != 0) {
                                for (loaiSP l : loaiGUI.listUpdate) {
                                    loaiBUS.updateInSQL(l);
                                }
                                JOptionPane.showMessageDialog(null, "Sửa thành công");
                            } else {
                                JOptionPane.showMessageDialog(null, "Không có dữ liệu nào mới để lưu thay đổi");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Dữ liệu không thay đổi");
                            loaiGUI.addDataInTable(loaiBUS.getList());
                        }
                        itemClicked.title.setText("Sửa");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/edit_icon.png"));
                        loaiGUI.isEditingEnabled = false;
                        loaiGUI.table.removeMouseListener(click);
                        loaiGUI.listUpdate.clear();

                }

                break;
            }
            case "Xóa": {
                switch (itemClicked.title.getText()) {
                    case "Xóa":
                        JOptionPane.showMessageDialog(null, "Để chọn nhiều ô cần xóa:\nKéo chuột\nHoặc giữ Ctrl và click vào các ô cần xóa");
                        itemClicked.title.setText("Lưu/Thoát");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));
                        break;
                    case "Lưu/Thoát":
                        Object[] options = {"Có", "Không"};
                        int r2 = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn xóa?\nHành động này sẽ không thể hoàn tác", "Xóa loại sản phẩm ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (r2 == JOptionPane.YES_OPTION) {
                            ArrayList<loaiSP> listDelete = loaiGUI.getSelectedListLoai();
                            if (listDelete.size() != 0) {
                                SanPhamBUS spBUS = new SanPhamBUS();
                                JOptionPane.showMessageDialog(null, "Xóa thành công");
                                for (loaiSP i : listDelete) {
                                    loaiBUS.delete(i.getMALOAI());

                                    boolean flag = false;
                                    for (SanPhamDTO s : spBUS.getDsSP()) {
                                        if (s.getMaLoai().equals(i.getMALOAI())) {
                                            flag = true;
                                            break;
                                        }

                                    }
                                    if (!flag) {
                                        loaiBUS.deleteInSQL(i.getMALOAI());
                                    } else {
                                        i.setTINHTRANG(2);
                                        loaiBUS.updateInSQL(i);
                                    }
                                }
                                loaiGUI.addDataInTable(loaiBUS.getList());

                            } else {
                                JOptionPane.showMessageDialog(null, "Không có dòng nào đưuọc chọn để xóa\nDữ liệu không thay đổi");
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Dữ liệu không thay đổi");
                        }
                        itemClicked.title.setText("Xóa");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/remove_icon.png"));
                        break;
                }
            }
        }
    }

//    public void thaotacNV(String hanhdong, hanhdongGUI itemClicked) throws SQLException {
//        Trangnhanvien_GUI nvGUI = (Trangnhanvien_GUI) pageContent;
//        Nhanvien_BUS loaiBUS = new Nhanvien_BUS();
//        switch (hanhdong) {
//            case "Thêm": {
//                addNhanvienGUI addnv = new addNhanvienGUI(nvGUI);
//                break;
//            }
//            case "Sửa": {
//                JOptionPane.showMessageDialog(null, "Click vào dòng cần sửa thông tin nhân viên\n và bấm \"Hoàn tất\" khi hoàn thành thao tác!");
//                nvGUI.reloadPagecontrol();
//                break;
//            }
//            case "Xóa": {
//                JOptionPane.showMessageDialog(null, "Click vào dòng cần xóa thông tin nhân viên\n và bấm \"Hoàn tất\" khi hoàn thành thao tác!");
//                nvGUI.reloadPagecontrol();
//                break;
//            }
//        }
//    }
    public void thaotacSIZE(String hanhdong, hanhdongGUI itemClicked) {

        SizeGUI sizeGUI = (SizeGUI) pageContent;
        SizeBUS sizeBUS = new SizeBUS();
        switch (hanhdong) {

            case "Thêm": {

                addSizeGUI addSIZE = new addSizeGUI(sizeGUI);

                break;
            }
            case "Sửa": {
                switch (itemClicked.title.getText()) {
                    case "Sửa":
                        JOptionPane.showMessageDialog(null, "Double Click vào ô cần sửa thông tin\nKhông thể sửa đổi MASIZE!\nTên size không chứa các kí tự đặc biệt\nHoàn thành sửa đổi thì ấn nút Lưu");

                        itemClicked.title.setText("Lưu/Thoát");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));

                        sizeGUI.isEditingEnabled = true;
                        break;
                    case "Lưu/Thoát":
                        TableCellEditor cellEditor = sizeGUI.table.getCellEditor();
                        if (cellEditor != null) {
                            // Loại bỏ trình chỉnh sửa, gián đoạn việc chỉnh sửa
                            cellEditor.cancelCellEditing();
                        }
                        Object[] options = {"Có", "Không"};
                        int r1 = JOptionPane.showOptionDialog(null, "Kiểm tra chắc chắn:\nTên size không chứa các kí tự đặc biệt\nNếu có dữ liệu để trống hoặc nhập sai kiểu dữ liệu, tất cả dữ liệu sẽ không được lưu thay đổi\nBạn có chắc chắn lưu?", "Sửa size ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (r1 == JOptionPane.YES_OPTION) {
                            if (sizeBUS.checkNewListSize(sizeGUI.getListSize())) {
                                sizeGUI.addDataInTable(sizeBUS.getList());
                                JOptionPane.showMessageDialog(null, "Sửa thành công");
                                sizeBUS.updateInSQL();
                            } else {
                                sizeGUI.addDataInTable(sizeBUS.getList());
                                JOptionPane.showMessageDialog(null, "Sửa thất bại do có ô không khớp kiểu dữ liệu\nHoặc tên size thay đổi trùng với tên size đã có");
                            }

                            itemClicked.title.setText("Sửa");
                            itemClicked.icon = new JLabel(new ImageIcon("./src/images/edit_icon.png"));
                            sizeGUI.isEditingEnabled = false;
                        } else {
                            int r11 = JOptionPane.showOptionDialog(null, "Bạn có muốn tiếp tục sửa?", "Sửa size", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            if (!(r11 == JOptionPane.YES_OPTION)) {
                                int r111 = JOptionPane.showOptionDialog(null, "Những dữ liệu sẽ không được lưu sau khi thoát\nBạn có chắc chắn thoát?", "Thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                if (r111 == JOptionPane.YES_OPTION) {
                                    sizeGUI.addDataInTable(sizeBUS.getList());
                                    itemClicked.title.setText("Sửa");
                                    itemClicked.icon = new JLabel(new ImageIcon("./src/images/edit_icon.png"));
                                    sizeGUI.isEditingEnabled = false;
                                }
                            }
                        }

                        break;

                }

                break;
            }
            case "Xóa": {
                switch (itemClicked.title.getText()) {
                    case "Xóa":
                        JOptionPane.showMessageDialog(null, "Để chọn nhiều ô cần xóa:\nKéo chuột\nGiữ Ctrl và click vào các ô cần xóa");
                        itemClicked.title.setText("Lưu/Thoát");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));
                        break;
                    case "Lưu/Thoát":
                        Object[] options = {"Có", "Không"};
                        int r2 = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn xóa?\nHành động này sẽ không thể hoàn tác", "Xóa size ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (r2 == JOptionPane.YES_OPTION) {
                            ArrayList<String> listDelete = sizeGUI.getSelectedListSize();
                            for (String i : listDelete) {
                                //chạy them chitietspBUS để kiểm tra MASIZE có được sử dujgn chưa
                                //nếu chưa dùng thì xóa, nếu dùng rồi thì bỏ qua
                                sizeBUS.delete(i);
                                sizeBUS.deleteInSQL(i);
                            }
                            sizeGUI.addDataInTable(sizeBUS.getList());
                            JOptionPane.showMessageDialog(null, "Xóa thành công");
                        }
                        itemClicked.title.setText("Xóa");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/remove_icon.png"));
                        break;
                }
            }

        }
    }

    public void thaotacKH(String hanhdong, hanhdongGUI itemClicked) {
        khachHangGUI KHGUI = (khachHangGUI) pageContent;
        khachHangBUS KHBUS = new khachHangBUS();
        switch (hanhdong) {
            case "Thêm": {
                System.out.println("vao them kh");
                addKhachHangGUI addNCC = new addKhachHangGUI(KHGUI);
                break;
            }
            case "Sửa": {
                int id = KHGUI.lay_id_table();
                khachHangDTO x = KHGUI.lay_mot_kh(id);
                new updateKhachHang(x, KHGUI);
                break;
            }
            case "Xóa": {
                switch (itemClicked.title.getText()) {
                    case "Xóa":
                        JOptionPane.showMessageDialog(null, "Để chọn nhiều ô cần xóa:\nKéo chuột\nGiữ Ctrl và click vào các ô cần xóa");
                        itemClicked.title.setText("Lưu/Thoát");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));
                        break;
                    case "Lưu/Thoát":
                        Object[] options = {"Có", "Không"};
                        int r2 = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn xóa?\nHành động này sẽ không thể hoàn tác", "Xóa Khách hàng ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (r2 == JOptionPane.YES_OPTION) {
                            ArrayList<Integer> listDelete = KHGUI.lay_sd_chon();
                            for (int i : listDelete) {
                                KHBUS.xoakhSql(i);
                                KHBUS.xoakh(i);
                            }
                            KHGUI.reloadData(KHBUS.getDs_khachHang());
                            JOptionPane.showMessageDialog(null, "Xóa thành công");
                        }
                        itemClicked.title.setText("Xóa");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/remove_icon.png"));
                    default:
                        throw new AssertionError("Trạng thái không hợp lệ: " + itemClicked.title.getText());
                }
                break;
            }
        }
    }

    public void thaotacNV(String hanhdong, hanhdongGUI itemClicked) {
        nhanVienGUI GUINV = (nhanVienGUI) pageContent;
        nhanVienBUS BUSNV = new nhanVienBUS();

        switch (hanhdong) {
            case "Thêm": {
                addNhanVienGUI addNV = new addNhanVienGUI(GUINV);
                break;
            }
            case "Sửa": {
                String id = GUINV.lay_id_table();
                nhanVienDTO x = GUINV.lay_mot_nv(id);
                new updateNhanVien(x, GUINV);
                break;
            }
            case "Xóa": {
                switch (itemClicked.title.getText()) {
                    case "Xóa":
                        JOptionPane.showMessageDialog(null, "Để chọn nhiều ô cần xóa:\nKéo chuột\nGiữ Ctrl và click vào các ô cần xóa");
                        itemClicked.title.setText("Lưu/Thoát");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/finish_icon.png"));
                        break;
                    case "Lưu/Thoát":
                        Object[] options = {"Có", "Không"};
                        int r2 = JOptionPane.showOptionDialog(null, "Bạn có chắc chắn xóa?\nHành động này sẽ không thể hoàn tác", "Xóa size ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (r2 == JOptionPane.YES_OPTION) {
                            GUINV.delRow();
                            JOptionPane.showMessageDialog(null, "Xóa thành công");
                        }
                        itemClicked.title.setText("Xóa");
                        itemClicked.icon = new JLabel(new ImageIcon("./src/images/remove_icon.png"));
                        break;
                }
                break;
            }
        }
    }

    private String formatPrice(String price) {// đổi từ giá 100000 -> 100,000 đ
        if (!price.equals("")) {
            DecimalFormat FormatInt = new DecimalFormat("#,###");
            return FormatInt.format(Integer.parseInt(price));
        }
        return price;

    }

    private String getPriceInFormatPrice(String formatprice) { // đổi từ 100.000 đ -> 100000(String)
        return formatprice.replaceAll("[^0-9]", "");
    }

    private boolean isEditableWithin24Hours(LocalDateTime ngayNhap) {
       LocalDateTime currentTime = LocalDateTime.now();
    
    // Tính toán khoảng thời gian giữa ngayNhap và currentTime
    Duration duration = Duration.between(ngayNhap, currentTime);
    
    // Kiểm tra nếu khoảng thời gian là 24 giờ hoặc ít hơn
    return duration.toHours() <= 24;
    }
}
