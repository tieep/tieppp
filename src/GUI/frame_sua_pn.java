
package GUI;

import BUS.Nhanvien_BUS;
import BUS.chitietphieunhap_BUS;
import BUS.nhacungcapBUS;
import BUS.phieunhap_BUS;
import DTO.Nhanvien_DTO;
import DTO.chitietphieunhap_DTO;
import DTO.nhacungcapDTO;
import DTO.phieunhap_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class frame_sua_pn extends JFrame {
    private JTextField tfMaPN, tfNgay, tfMaNV, tfMaNCC, tfTongTien;
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel btnLuu, btnHuy;
    private int crong, ccao;
    private Font font_text = new Font("Tahoma", Font.BOLD, 15);
    private Font font_data = new Font("Tahoma", Font.PLAIN, 14);
    private ArrayList<chitietphieunhap_DTO> ctpnDTO;
    private phieunhap_DTO pnDTO;
    private phieunhap_GUI pnGUI;
    private JComboBox<String> cbMaNV, cbMaNCC;

    public frame_sua_pn(int crong, int ccao, phieunhap_DTO pn, phieunhap_GUI pnGUI, ArrayList<chitietphieunhap_DTO> ctpn) throws SQLException {
        tfMaPN = new JTextField(pn.getMAPN());
        this.crong = crong;
        this.ccao = ccao;
        tfMaNV = new JTextField(pn.getMANV());
        tfNgay = new JTextField(pn.getNgay().toString());
        tfMaNCC = new JTextField(pn.getMANCC());
        this.pnDTO = pn;
        this.pnGUI = pnGUI;
        this.ctpnDTO = ctpn;
        
    cbMaNV = new JComboBox<>();
    cbMaNCC = new JComboBox<>();
        Nhanvien_BUS nhanvienBUS = new Nhanvien_BUS();
    for (Nhanvien_DTO nv : nhanvienBUS.getlist()) {
        cbMaNV.addItem(nv.getManv());
    }
    cbMaNV.setSelectedItem(pn.getMANV()); // Set the current value

    // Populate cbMaNCC with supplier IDs
    nhacungcapBUS nhacungcapBUS = new nhacungcapBUS();
    for (nhacungcapDTO ncc : nhacungcapBUS.getList()) {
        cbMaNCC.addItem(ncc.getMANCC());
    }
    cbMaNCC.setSelectedItem(pn.getMANCC());
    
    init();
    }

    private void init() {
        setTitle("SỬA CHI TIẾT PHIẾU NHẬP");
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("SỬA CHI TIẾT PHIẾU NHẬP");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setForeground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setPreferredSize(new Dimension(800, 50));

        tfMaPN.setFont(font_text);
        tfMaPN.setEditable(false);
        tfMaPN.setBorder(BorderFactory.createEmptyBorder());
        tfMaPN.setHorizontalAlignment(JTextField.RIGHT);
        tfNgay.setFont(font_text);
        tfNgay.setBorder(BorderFactory.createEmptyBorder());
        tfNgay.setHorizontalAlignment(JTextField.RIGHT);
        tfMaNV.setFont(font_text);
        tfMaNCC.setFont(font_text);

        infoPanel.add(new JLabel("MAPN:"));
        infoPanel.add(tfMaPN);
        infoPanel.add(new JLabel("-- Ngày:"));
        infoPanel.add(tfNgay);
        infoPanel.add(new JLabel("-- MANV:"));
        infoPanel.add(cbMaNV);
        infoPanel.add(new JLabel("-- MANCC:"));
        infoPanel.add(cbMaNCC);

        infoPanel.setForeground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        centerPanel.add(infoPanel);
        centerPanel.setForeground(Cacthuoctinh_phuongthuc_chung.darkness_blue);

        String[] columnNames = {"MASP", "Số lượng", "Mã size", "Giá nhập", "Thành tiền"};
        Object[][] data = new Object[ctpnDTO.size()][5];

        for (int i = 0; i < ctpnDTO.size(); i++) {
            chitietphieunhap_DTO item = ctpnDTO.get(i);
            data[i][0] = item.getMasp();
            data[i][1] = item.getSoluong();
            data[i][2] = item.getMasize();
            data[i][3] = item.getGianhap();
            data[i][4] = item.getThanhtien();
        }

        tableModel = new DefaultTableModel(data, columnNames);
        tableModel = new DefaultTableModel(data, columnNames) {
    @Override
    public boolean isCellEditable(int row, int column) {
        // Only allow editing of "Số lượng" (1) and "Giá nhập" (3) columns
        return column == 1 || column == 3;
    }
};
        tableModel.addTableModelListener(new TableModelListener() {
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        DecimalFormat df = new DecimalFormat("#,###.00");

        // Only trigger calculation if "Số lượng" or "Giá nhập" are changed
        if (column == 1 || column == 3) {
            try {
                int soluong = Integer.parseInt(tableModel.getValueAt(row, 1).toString());
                double gianhap = Double.parseDouble(tableModel.getValueAt(row, 3).toString());

                // Calculate "Thành tiền"
                    double thanhtien = soluong * gianhap;
                    tableModel.setValueAt(df.format(thanhtien), row, 4);

    // Tính tổng tiền
                double tongTien = 0;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    tongTien += Double.parseDouble(tableModel.getValueAt(i, 4).toString().replace(",", ""));
                }
                tfTongTien.setText(df.format(tongTien) + " Đ");


                // Set the total in the "Tổng tiền" text field
               
                tfTongTien.setText(df.format(tongTien) + " Đ");
            } catch (NumberFormatException ex) {
                // Handle the case where input is invalid (not a number)
                JOptionPane.showMessageDialog(frame_sua_pn.this, "Vui lòng nhập giá trị hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
});
tableModel = new DefaultTableModel(data, columnNames) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 1 || column == 3;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        super.setValueAt(value, row, column);
        if (column == 1 || column == 3) {
            try {
                int soluong = Integer.parseInt(tableModel.getValueAt(row, 1).toString());
                double gianhap = Double.parseDouble(tableModel.getValueAt(row, 3).toString());

                // Calculate "Thành tiền"
                double thanhtien = soluong * gianhap;
                DecimalFormat df = new DecimalFormat("#,###.00");
                tableModel.setValueAt(df.format(thanhtien), row, 4);

                // Calculate "Tổng tiền"
                double tongTien = 0;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    tongTien += Double.parseDouble(tableModel.getValueAt(i, 4).toString().replace(",", ""));
                }
                tfTongTien.setText(df.format(tongTien) + " Đ");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame_sua_pn.this, "Vui lòng nhập giá trị hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
};

        table = new JTable(tableModel);
        cssDataTable();
        JTableHeader header = table.getTableHeader();
        header.setForeground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        header.setFont(font_text);
        table.setRowHeight(30);
        JScrollPane tableScrollPane = new JScrollPane(table);
        centerPanel.add(tableScrollPane);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        double tongTien = ctpnDTO.stream().mapToDouble(chitietphieunhap_DTO::getThanhtien).sum();
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        DecimalFormat df = new DecimalFormat("#,###.00");
        tfTongTien = new JTextField(df.format(tongTien) + " Đ", 15);
        tfTongTien.setFont(font_text);
        tfTongTien.setEditable(false);
        tfTongTien.setBorder(BorderFactory.createEmptyBorder());
          // Căn phải văn bản

        totalPanel.add(new JLabel("Tổng tiền:"));
        totalPanel.add(tfTongTien);
        bottomPanel.add(totalPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JPanel();
        cssBtn(btnLuu, "Lưu", "btnLuu");
        buttonPanel.add(btnLuu);

        btnHuy = new JPanel();
        cssBtn(btnHuy, "Hủy", "btnHuy");
        buttonPanel.add(btnHuy);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        btnLuu.addMouseListener(new MouseListenerAdapter("Lưu"));
        btnHuy.addMouseListener(new MouseListenerAdapter("Hủy"));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void cssBtn(JPanel b, String text, String name) {
        JLabel t = new JLabel(text, JLabel.CENTER);
        t.setForeground(Color.WHITE);
        b.setName(name);
        b.add(t);
        b.setBackground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        b.setPreferredSize(new Dimension(100, 30));
        b.setOpaque(true);
    }

    private void cssDataTable() {
        table.setRowHeight(35);
        table.setFont(font_data);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(160);
        table.getColumnModel().getColumn(4).setPreferredWidth(crong - 320); // Adjust as needed
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }
private ArrayList<chitietphieunhap_DTO> getUpdatedChitietPhieunhapFromTable() {
    ArrayList<chitietphieunhap_DTO> updatedList = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#,###.00");

    for (int i = 0; i < tableModel.getRowCount(); i++) {
        String masp = tableModel.getValueAt(i, 0).toString();
        int soluong = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
        String masize = tableModel.getValueAt(i, 2).toString();
        double gianhap = Double.parseDouble(tableModel.getValueAt(i, 3).toString());
        double thanhtien = Double.parseDouble(tableModel.getValueAt(i, 4).toString().replace(",", ""));
        chitietphieunhap_DTO ctpn = new chitietphieunhap_DTO(tfMaPN.getText(), masp, soluong, gianhap, thanhtien, masize);
        updatedList.add(ctpn);
    }

    return updatedList;
}



    class MouseListenerAdapter extends MouseAdapter {
        private String btnType;

        MouseListenerAdapter(String btnType) {
            this.btnType = btnType;
        }

        @Override
public void mouseClicked(MouseEvent e) {
    try {
        JPanel btn = (JPanel) e.getSource();
        switch (btn.getName()) {
            case "btnHuy":
                int r1 = JOptionPane.showConfirmDialog(frame_sua_pn.this, "Những thông tin sẽ không được lưu sau khi thoát!\nBạn có muốn tiếp tục thoát?", "Thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (r1 == JOptionPane.YES_OPTION) {
                    dispose();
                }
                break;
            case "btnLuu":
                try {
                    LocalDate date = LocalDate.parse(tfNgay.getText());
                    String maNV = cbMaNV.getSelectedItem().toString();
                    String maNcc = cbMaNCC.getSelectedItem().toString();

                    // Kiểm tra giá trị tổng tiền có hợp lệ không
                    String tongTienText = tfTongTien.getText().replace(" Đ", "").trim();
                    double tongTien;
                    try {
                        tongTien = Double.parseDouble(tongTienText.replace(",", ""));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame_sua_pn.this, "Vui lòng nhập tổng tiền hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return; // Thoát nếu tổng tiền không hợp lệ
                    }

                    phieunhap_BUS pnBUS = new phieunhap_BUS();
                    phieunhap_DTO updatedPnDTO = new phieunhap_DTO(tfMaPN.getText(), maNV, date, tongTien, maNcc);
                    pnBUS.update(updatedPnDTO);

                    // Lấy danh sách các chi tiết phiếu nhập từ bảng
                    ArrayList<chitietphieunhap_DTO> updatedCtpnList = getUpdatedChitietPhieunhapFromTable();

                    chitietphieunhap_BUS ctpnBUS = new chitietphieunhap_BUS(pnDTO);

                    // Cập nhật từng chi tiết phiếu nhập trong danh sách
                    for (chitietphieunhap_DTO updatedCtpnDTO : updatedCtpnList) {
                        ctpnBUS.set(updatedCtpnDTO); // Cập nhật từng chi tiết vào DB
                    }

                    JOptionPane.showMessageDialog(frame_sua_pn.this, "Cập nhật thành công!");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame_sua_pn.this, "Có lỗi xảy ra: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame_sua_pn.this, "Có lỗi xảy ra: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

        @Override
        public void mouseEntered(MouseEvent e) {
            JPanel btn = (JPanel) e.getSource();
            btn.setBackground(Color.DARK_GRAY);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel btn = (JPanel) e.getSource();
            btn.setBackground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        }
    }
}
