/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.TaiKhoanBUS;
import BUS.nhanVienBUS;
import DTO.TaiKhoanDTO;
import DTO.nhanVienDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class nhanVienGUI extends JPanel {

    private int chieu_cao, chieu_rong;
    public JTable table;
    private Font font_text = new Font("Tahoma", Font.PLAIN, 14);
    private DefaultTableModel dtm;
    private nhanVienDTO nvienDN;
    private String maNV;

    public nhanVienGUI(int h, int w, String maNV) {
        this.chieu_cao = h;
        this.chieu_rong = w;
        this.maNV = maNV;
        init();
    }

    public void init() {
        setLayout(new BorderLayout());
        String[] cot = {"Mã NV", "Tên nhân viên", "Tên quyền", "Số điện thoại", "Địa chỉ", "Email", "Trạng thái"};
        dtm = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(dtm);
        styleTable(table);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String trangThai = (String) table.getValueAt(selectedRow, 6);
                        if (trangThai.equals("Đã nghỉ làm")) {
                            JOptionPane.showMessageDialog(null, "Nhân viên này đã nghỉ làm. Bạn không thể chọn dòng này.");
                            table.clearSelection();
                        }
                    }
                }
            }
        });

        nhanVienBUS busNV = new nhanVienBUS();
        reloadData(busNV.getds_nhanVien());

        JScrollPane scrPane = new JScrollPane(table);
        scrPane.setPreferredSize(new Dimension(chieu_rong, chieu_cao));
        scrPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrPane, BorderLayout.CENTER);
    }

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(Cacthuoctinh_phuongthuc_chung.sky_blue);
        header.setForeground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        header.setFont(new Font("Tahoma", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        table.setRowHeight(35);
        table.setFont(font_text);
        int tableWidth = chieu_rong;
        table.getColumnModel().getColumn(0).setPreferredWidth((int) (tableWidth * 0.10));
        table.getColumnModel().getColumn(1).setPreferredWidth((int) (tableWidth * 0.20));
        table.getColumnModel().getColumn(2).setPreferredWidth((int) (tableWidth * 0.15));
        table.getColumnModel().getColumn(3).setPreferredWidth((int) (tableWidth * 0.15));
        table.getColumnModel().getColumn(4).setPreferredWidth((int) (tableWidth * 0.15));
        table.getColumnModel().getColumn(5).setPreferredWidth((int) (tableWidth * 0.15));
        table.getColumnModel().getColumn(6).setPreferredWidth((int) (tableWidth * 0.10));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }

    public void reloadData(ArrayList<nhanVienDTO> ds) {
        dtm.setRowCount(0);
        for (nhanVienDTO nv : ds) {
            if (!nv.getMANV().equals(maNV)) {
                String trangthai = nv.getTRANGTHAi() == 1 ? "Đang làm" : "Đã nghỉ làm";
                Object[] row = {nv.getMANV(), nv.getTENNV(), nv.getCHUCVU(), nv.getSDT(), nv.getDIACHI(), nv.getEMAIL(), trangthai};
                dtm.addRow(row);
            }
        }
    }

    public void them_mot_nv(nhanVienDTO nv) {
        String trangthai = nv.getTRANGTHAi() == 1 ? "Đang làm" : "Đã nghỉ làm";
        dtm.addRow(new Object[]{nv.getMANV(), nv.getTENNV(), nv.getCHUCVU(), nv.getSDT(), nv.getDIACHI(), nv.getEMAIL(), trangthai});
        dtm.fireTableDataChanged();
    }

    public void suaKH(nhanVienDTO nv) {
        int rowCount = dtm.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (dtm.getValueAt(i, 0).equals(nv.getMANV())) {
                dtm.setValueAt(nv.getTENNV(), i, 1);
                dtm.setValueAt(nv.getCHUCVU(), i, 2);
                dtm.setValueAt(nv.getSDT(), i, 3);
                dtm.setValueAt(nv.getDIACHI(), i, 4);
                dtm.setValueAt(nv.getEMAIL(), i, 5);
                break;
            }
        }
        dtm.fireTableDataChanged();
    }

    public nhanVienDTO lay_mot_nv(String id) {
        nhanVienBUS bus = new nhanVienBUS();
        ArrayList<nhanVienDTO> ds = bus.getds_nhanVien();
        for (nhanVienDTO nv : ds) {
            if (nv.getMANV().equals(id)) {
                return nv;
            }
        }
        return null;
    }

    public String lay_id_table() {
        int chon = table.getSelectedRow();
        String id = table.getValueAt(chon, 0) + "";
        return id;
    }

    public void delRow() {
        int[] selectedRows = table.getSelectedRows();
        nhanVienBUS busNV = new nhanVienBUS();
        TaiKhoanBUS busTK = new TaiKhoanBUS();
        ArrayList<TaiKhoanDTO> dstk = busTK.getDsTK();
        boolean anyAccountDeleted = false; 

        for (int i = selectedRows.length - 1; i >= 0; i--) {
            String id = (String) dtm.getValueAt(selectedRows[i], 0);
            int success = busNV.xoaInSQL(id);

            if (success == 1 || success == 0) {
                boolean accountDeleted = false; 
                for (TaiKhoanDTO tk : dstk) {
                    if (tk.getMaNV().equals(id)) {
                        Object[] options = {"Có", "Không"};
                        int tt = JOptionPane.showOptionDialog(null, "Bạn có muốn xóa tài khoản của Nhân viên không?", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (tt == JOptionPane.YES_OPTION) {
                            busTK.delete(id);
                            JOptionPane.showMessageDialog(null, "Đã xóa tài khoản thành công");
                            accountDeleted = true;
                            anyAccountDeleted = true;
                        }
                        break; 
                    }
                }

                if (success == 1) {
                    dtm.removeRow(selectedRows[i]);
                }

                if (accountDeleted) {
                    dstk = busTK.getDsTK();
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new nhanVienGUI(900, 600, "AD1"));
        f.pack();
        f.setVisible(true);
    }
}
