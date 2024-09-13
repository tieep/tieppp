package GUI;

import BUS.BUS_qlkh;
import BUS.ChitietHD_BUS;
import BUS.Nhanvien_BUS;
import BUS.SanPhamBUS;
import BUS.SizeBUS;
import BUS.chitietsanpham_BUS;
import DAO.ConnectDataBase;
import DTO.ChitietHD_DTO;
import DTO.Nhanvien_DTO;
import DTO.SanPhamDTO;
import DTO.chitietsanpham_DTO;
import DTO.model_qlkh;
import DTO.nhacungcapDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public final class ChitietHD_GUI extends JPanel {

    private JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9;
    private int chieurong, chieucao;
    private Font f = new Font("Tahoma", Font.BOLD, 14);
    private ConnectDataBase mySQL = new ConnectDataBase();
    private ChitietHD_BUS dscthd;
    private String[] titleOrder = {"Mã hóa đơn", "Thời gian", "Tên khách hàng", "Tên nhân viên"};
    private String[] titlePayment = {"Tổng cộng", "Giảm giá", "Thành tiền"};
    private JLabel[] JL_valuePay;
    private DefaultTableModel tableModel;
    public boolean isEditingEnabled = false;
    public JTable table;
    public String MAHD;
    public int giamgia, tongtien;
    public int maKH;
    public int diemBefore = 0;
    public int diemNew = 0;
    public ArrayList<ChitietHD_DTO> listCTHDCurrent ;
    public ArrayList<chitietsanpham_DTO> listCTSPBefore ;
    public JPanel pPayment;
    public JLabel[] valueInPayment = new JLabel[3];
    public JPanel HoadonSelected;
    public ChitietHD_GUI(int chieurong, int chieucao, String maHD, String ngayHD, String time, String maKH, String maNV, String giamgia, String tongtien) throws SQLException {
        this.chieurong = chieurong;
        this.chieucao = chieucao;
        this.MAHD = maHD;
        this.giamgia = Integer.parseInt(giamgia);
        this.tongtien = Integer.parseInt(tongtien);
        this.maKH = Integer.parseInt(maKH);
        init(maHD, ngayHD, time, maKH, maNV);
    }

    public void init(String maHD, String ngayHD, String time, String maKH, String maNV) throws SQLException {
        this.setPreferredSize(new Dimension(chieurong, chieucao));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setVisible(true);
        //--------------------------------SHOW_Title-------------------------------
        p1 = new JPanel(new BorderLayout());
        p1.setBackground(Color.white);

        JLabel title = new JLabel("CHI TIẾT HÓA ĐƠN", JLabel.CENTER);

        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 28));
        title.setForeground(Color.decode("#145369"));
        p1.add(title, BorderLayout.CENTER);
        p1.setPreferredSize(new Dimension(chieurong, (int) title.getPreferredSize().getHeight() + 20));
        add(p1);

        //--------------------------------SHOW_IDHD-------------------------------
        JPanel pInforOrder = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < titleOrder.length; i++) {

            JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 7));

            p.setBackground(Color.white);
            JLabel t = new JLabel(titleOrder[i]);
            t.setFont(f);
            t.setForeground(Color.decode("#2596be"));
            t.setPreferredSize(new Dimension(chieurong / 4, (int) t.getPreferredSize().getHeight()));
            p.add(t);
            p.setPreferredSize(new Dimension(chieurong, (int) p.getPreferredSize().getHeight()));
            JLabel value = null;

            switch (i) {
                case 0: {

                    value = new JLabel(maHD);
                    break;
                }
                case 1: {
                    value = new JLabel(time + ",  " + ngayHD);
                    break;
                }
                case 2: {

                    BUS_qlkh khBUS = new BUS_qlkh();
                    for (model_qlkh k : khBUS.getlist()) {

                        if (String.valueOf(k.getMakh()).equals(maKH)) {
                            value = new JLabel(k.getTen());

                        }
                    }
                    break;
                }
                case 3: {
                    Nhanvien_BUS nvBUS = null;
                    try {
                        nvBUS = new Nhanvien_BUS();
                    } catch (SQLException ex) {

                    }

                    for (Nhanvien_DTO n : nvBUS.getlist()) {
                        if (n.getManv().equals(maNV)) {
                            value = new JLabel(n.getTennv());

                        }
                    }
                    break;
                }

            }
            value.setFont(f);
            value.setForeground(Color.decode("#2596be"));

            p.add(value);
            pInforOrder.add(p);
        }
        add(pInforOrder);
        //--------------------------------SHOW_SANPHAM_CTHD-------------------------------
        JPanel pDetails = new JPanel();
        pDetails.setBackground(Color.white);

        String[] columnNames = {"MASP", "Tên SP", "Size", "SL", "Đơn giá", "Tổng"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);

        // Tạo đối tượng JTable và gán mô hình dữ liệu
        table = new JTable(tableModel) {

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 4 || column == 5) {
                    return false;
                }
                return isEditingEnabled;
            }

        };
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isEditingEnabled) {
                    // Xử lý sự kiện click chuột
                    int row = table.rowAtPoint(e.getPoint()); // Lấy chỉ số hàng của điểm click

                    // Kiểm tra xem sự kiện click có phải là click chuột trái
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        String MASP = (String) table.getValueAt(row, 0); // Lấy giá trị của ô được click
                        String TENSIZE = (String) table.getValueAt(row, 2);
                        int SL = (int) table.getValueAt(row, 3);

                        frameUpdate(MASP, TENSIZE, SL);

                    }
                }

            }
        });
        dscthd = new ChitietHD_BUS(maHD);
        addDataInTable(dscthd.getList());

        cssHeaderTable(table.getTableHeader());
        //css cho rows cua table
        cssDataTable();

        // Tạo đối tượng JScrollPane và gán JTable vào để cuộn nếu cần
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // Tạo JFrame và thêm JScrollPane vào giao diện
        pDetails.add(scrollPane);

        pPayment = new JPanel(new GridLayout(3, 1));
        
        for (int i = 0; i < titlePayment.length; i++) {
            JPanel jp = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            jp.setBackground(Color.white);
            JLabel t = new JLabel(titlePayment[i]);
            t.setFont(new Font("Tahoma", Font.PLAIN, 17));
            t.setForeground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
            t.setPreferredSize(new Dimension(chieurong / 5, (int) t.getPreferredSize().getHeight()));
            jp.add(t);
            jp.setPreferredSize(new Dimension(chieurong, (int) jp.getPreferredSize().getHeight()));
          
            switch (i) {
                case 0: {
                    valueInPayment[i] = new JLabel(giamgia + tongtien + "", JLabel.CENTER);
                    break;
                }
                case 1: {
                    valueInPayment[i] = new JLabel(giamgia + "", JLabel.CENTER);
                    break;
                }
                case 2: {
                    valueInPayment[i] = new JLabel(tongtien + "", JLabel.CENTER);
                    break;
                }
            }
            valueInPayment[i].setFont(new Font("Tahoma", Font.PLAIN, 17));
            valueInPayment[i].setForeground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
            jp.add(valueInPayment[i]);
            pPayment.add(jp);
        }
        pDetails.setPreferredSize(new Dimension(chieurong, chieucao - (int) p1.getPreferredSize().getHeight() - (int) pInforOrder.getPreferredSize().getHeight() - (int) pPayment.getPreferredSize().getHeight()));
        scrollPane.setPreferredSize(new Dimension(chieurong, (int) pDetails.getPreferredSize().getHeight()));
        this.add(scrollPane);
        add(pPayment);

    }
    
    public void addDataInTable(ArrayList<ChitietHD_DTO> list) {
        Vector data;
        tableModel.setRowCount(0);
        SanPhamBUS spBUS = new SanPhamBUS();
        SizeBUS sizeBUS = new SizeBUS();
        for (ChitietHD_DTO n : list) {
            data = new Vector();
            data.add(n.getMaSP());
            data.add(spBUS.select_by_id(n.getMaSP()).getTenSP());
            data.add((sizeBUS.getSizeDTO(n.getMaSize()).getTENSIZE()));
            data.add(n.getSl());
            data.add((int) n.getGia());
            data.add(n.getSl() * (int) n.getGia());
            tableModel.addRow(data);
        }
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();

    }

    public void frameUpdate(String MASP, String TENSIZE, int SL) {
        SizeBUS sizeBUS = new SizeBUS();
        SanPhamBUS spBUS = new SanPhamBUS();
        chitietsanpham_BUS ctspBUS = null;
        try {
            ctspBUS = new chitietsanpham_BUS();
        } catch (SQLException ex) {
            Logger.getLogger(ChitietHD_GUI.class.getName()).log(Level.SEVERE, null, ex);
            return; // Stop the method if initialization fails
        }
        int quantityInStore = 0;
        JComboBox<String> comboBoxSize = new JComboBox<>();
        JComboBox<Integer> comboBoxQuantity = new JComboBox<>();
        System.out.println(SL);
        int cr = 450;
        int cc = 280;
        JFrame f = new JFrame("Thay đổi " + MASP + " trong hóa đơn");
        f.setUndecorated(true);

        f.setSize(cr, cc);
        f.setLayout(new BorderLayout(0, 0));

        Font f_titleChild = new Font("Tahoma", Font.PLAIN, 17);
        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        wrap.setOpaque(true);
        wrap.setBackground(Cacthuoctinh_phuongthuc_chung.light_gray);
        JPanel wrapSize = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        wrapSize.setBackground(Cacthuoctinh_phuongthuc_chung.light_gray);
        JPanel wrapQuantity = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        wrapQuantity.setBackground(Cacthuoctinh_phuongthuc_chung.light_gray);
        JPanel wrapBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        wrapBtn.setBackground(Cacthuoctinh_phuongthuc_chung.light_gray);
        JButton btn_save = new JButton("Lưu");
        btn_save.setFont(f_titleChild);
        btn_save.setBackground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        btn_save.setForeground(Color.white);
        btn_save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object[] options = {"Có", "Không"};
                int r1 = JOptionPane.showOptionDialog(null, "Bạn chắc chắn muốn lưu thay đổi?", "Sửa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (r1 == JOptionPane.YES_OPTION) {
                                        f.dispose();
                                        int row = table.getSelectedRow();
                                        String sizeNew = (String)comboBoxSize.getSelectedItem();
                                        String sizeOld = (String)table.getValueAt(row, 2);
                                        int quantityNew = (int)comboBoxQuantity.getSelectedItem();
                                        
                                        int dongia = (int)table.getValueAt(row, 4);
                                        String MASP = (String)table.getValueAt(row, 0);
                                        tableModel.setValueAt(sizeNew, row, 2);
                                        tableModel.setValueAt(quantityNew, row, 3);
                                        tableModel.setValueAt(quantityNew * dongia, row, 5);
                                        tableModel.fireTableDataChanged();
                                        //lưu thay đổi xuống mảng ảo chi tiết hóa đơn và tính tổng chi tiết hóa đơn
                                        SizeBUS sizeBUS = new SizeBUS();
                                        String masizeNew = (sizeBUS.getSizeDTO(sizeNew)).getMASIZE();
                                        String masizeOld = (sizeBUS.getSizeDTO(sizeOld)).getMASIZE();
                                        int subTotal = 0 ;
                                        for(ChitietHD_DTO c : listCTHDCurrent){
                                            if(c.getMaSP().equals(MASP) && c.getMaSize().equals(masizeOld)){
                                             
                                                c.setMaSize(masizeNew);
                                                c.setSl(quantityNew);
                                            }
                                            subTotal += (c.getSl() * (int)c.getGia());
                                            System.out.println("subtotal "+subTotal);
                                        }
                                        int giamgiaNew = 0;
                                        if(giamgia != 0){//co dung giam gia
                                            if(diemBefore*1000 >= subTotal){
                                                giamgiaNew = subTotal ;
                                                diemNew = diemBefore - giamgiaNew/1000 + (subTotal - giamgiaNew)/10000;
                                            }      
                                            else{
                                                giamgiaNew = diemBefore*1000;
                                                diemNew = (subTotal - giamgiaNew)/10000;
                                            }
                                            
                                        }else diemNew = diemBefore + (int)subTotal/10000;
                                          
                                        
                                        valueInPayment[0].setText(subTotal+"");
                                        valueInPayment[1].setText(giamgiaNew+"");
                                        valueInPayment[2].setText(subTotal-giamgiaNew+"");
                                        Component[] JL_Child = HoadonSelected.getComponents();
                                        ((JLabel)JL_Child[5]).setText(giamgiaNew+"");
                                        ((JLabel)JL_Child[6]).setText(subTotal-giamgiaNew+"");
                                        
                                        tongtien = subTotal-giamgiaNew;
                                        giamgia = giamgiaNew;
                }
            }
        });
        
        
        JButton btn_cancel = new JButton("Thoát");
        btn_cancel.setFont(f_titleChild);
        btn_cancel.setBackground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        btn_cancel.setForeground(Color.white);
        btn_cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.dispose();
            }
        });
        wrapBtn.add(btn_save);
        wrapBtn.add(btn_cancel);
        wrapBtn.setPreferredSize(new Dimension(cr, (int) wrapBtn.getPreferredSize().getHeight()));

        JLabel titleSize = new JLabel("Size", JLabel.CENTER);
        titleSize.setFont(f_titleChild);
        JLabel titleQuantity = new JLabel("Số lượng", JLabel.CENTER);
        titleQuantity.setFont(f_titleChild);

        wrapSize.add(titleSize);
        wrapQuantity.add(titleQuantity);

        ArrayList<chitietsanpham_DTO> list = ctspBUS.getlistByFilter(MASP);
        String TENSP = spBUS.select_by_id(MASP).getTenSP();

        for (chitietsanpham_DTO c : list) {
            String sizeName = sizeBUS.getSizeDTO(c.getMASIZE()).getTENSIZE();
            comboBoxSize.addItem(sizeName);
            if (sizeName.equals(TENSIZE)) {
                quantityInStore = c.getSoluong()+SL;
            }
        }
        comboBoxSize.setSelectedItem(TENSIZE);
        comboBoxSize.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Kiểm tra trạng thái của sự kiện (SELECTED hoặc DESELECTED)
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Lấy mục được chọn
                    String selectedOption = (String) comboBoxSize.getSelectedItem();
                    // Hiển thị lựa chọn

                    comboBoxQuantity.removeAllItems();
                    updateQuantityGUI(list, selectedOption, TENSIZE, SL, comboBoxQuantity);
                }
            }
        });

        for (int i = 1; i <= quantityInStore; i++) {
            comboBoxQuantity.addItem(i);
        }

        comboBoxQuantity.setSelectedItem(SL);
        JLabel JL_name = new JLabel(TENSP, JLabel.CENTER);
        if ((int) JL_name.getPreferredSize().getWidth() > f.getWidth()) {
            f.setSize((int) JL_name.getPreferredSize().getWidth() + 100, f.getHeight());
            JL_name.setPreferredSize(new Dimension((int) JL_name.getPreferredSize().getWidth(), (int) JL_name.getPreferredSize().getHeight() + 50));
        } else {
            JL_name.setPreferredSize(new Dimension(cr, (int) JL_name.getPreferredSize().getHeight() + 50));
        }

        JL_name.setFont(Cacthuoctinh_phuongthuc_chung.font_header);
        wrap.add(JL_name);
        wrapSize.add(comboBoxSize);
        wrapQuantity.add(comboBoxQuantity);
        wrapSize.setPreferredSize(new Dimension(cr, (int) wrapSize.getPreferredSize().getHeight()));
        wrapQuantity.setPreferredSize(new Dimension(cr, (int) wrapQuantity.getPreferredSize().getHeight()));
        wrap.add(wrapSize);
        wrap.add(wrapQuantity);
        wrap.add(wrapBtn);
        f.add(wrap, BorderLayout.CENTER);

        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);

    }

    private void updateQuantityGUI(ArrayList<chitietsanpham_DTO> list, String selectedSize, String startedSize, int startedSL, JComboBox<Integer> comboBoxQuantity) {
        SizeBUS sizeBUS = new SizeBUS();
        int sl = 0;
        for (chitietsanpham_DTO c : list) {
            String sizeName = sizeBUS.getSizeDTO(c.getMASIZE()).getTENSIZE();
            if (sizeName.equals(selectedSize)) {
                sl = c.getSoluong();
                break;
            }

        }
        for (int i = 1; i <= sl; i++) {
            comboBoxQuantity.addItem(i);
        }

        if (selectedSize.equals(startedSize)) {
            comboBoxQuantity.setSelectedItem(startedSL);
        } else {
            comboBoxQuantity.setSelectedIndex(0);
        }
    }
    public void doAfterThaotacUpdate(JPanel HoadonSelected){
        this.HoadonSelected = HoadonSelected;
        //1.tìm đểm khách hàng trước khi bán hóa đơn, => dạng đối tượng MAKH,điểm diemBefore
        //2.chi tiết sản phẩm trong kho trước khi bán hóa đơn => dạng đối tướng MASP, MASIZE, số lượng listCTSPBefore
        //3. mảng ảo chi tiết hóa đơn listCTHDCurrent
                    BUS_qlkh khBUS = new BUS_qlkh();
                    int diemCurrent = khBUS.selecby_id(maKH).getDiem();
                   
                    if (giamgia == 0) {
                        diemBefore = diemCurrent - tongtien / 10000;
                    } else {
                        diemBefore = diemCurrent + giamgia / 1000 - tongtien / 10000;
                    }
                    chitietsanpham_BUS ctspBUS = null;
                    ChitietHD_BUS cthdBUS = null;
                    try {
                        ctspBUS = new chitietsanpham_BUS();
                        cthdBUS = new ChitietHD_BUS(MAHD);
                    } catch (SQLException ex) {
                        Logger.getLogger(ChitietHD_GUI.class.getName()).log(Level.SEVERE, null, ex);
                        return; // Stop the method if initialization fails
                    }
                    
                        listCTHDCurrent = cthdBUS.getList();
                  
                    
                        listCTSPBefore = new ArrayList<>();
                    for (chitietsanpham_DTO sp : ctspBUS.getlist()) {
                        for (ChitietHD_DTO c : listCTHDCurrent) {
                            if (c.getMaSP().equals(sp.getMASP()) && c.getMaSize().equals(sp.getMASIZE())) {
                                sp.setSoluong(sp.getSoluong() + c.getSl());
                                break;
                            }
                        }
                        listCTSPBefore.add(sp);
                        }
                    
                        
                            
                                
                                
    }
                                


    private void cssHeaderTable(JTableHeader header) {
        header.setBackground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        header.setForeground(Cacthuoctinh_phuongthuc_chung.sky_blue);
        header.setFont(new Font("Tahoma", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 30));
    }

    private void cssDataTable() {
        // {"Tên SP", "Size", "Số lượng", "Đơn giá", "Tổng"};
        table.setRowHeight(35);//chieo cao cua hang
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));//font cho dât trong table
        table.getColumnModel().getColumn(5).setPreferredWidth((int) chieurong / 6);
        table.getColumnModel().getColumn(1).setPreferredWidth((int) chieurong * 2 / 5);
        table.getColumnModel().getColumn(2).setPreferredWidth((int) chieurong / 10);
        table.getColumnModel().getColumn(3).setPreferredWidth((int) chieurong / 10);
        table.getColumnModel().getColumn(4).setPreferredWidth((int) chieurong / 7);
        table.getColumnModel().getColumn(0).setPreferredWidth(chieurong - (int) chieurong * 2 / 5 - (int) chieurong * 2 / 10 - (int) chieurong / 7 - (int) chieurong / 6);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // Căn giữa dữ liệu trong các ô
        table.setDefaultRenderer(Object.class, centerRenderer);
    }

}
