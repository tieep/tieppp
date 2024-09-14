/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.nhanVienBUS;
import DTO.nhanVienDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class addNhanVienGUI extends JFrame implements MouseListener {

    private int chieu_cao, chieu_rong;
    private addNhanVien addNV;
    private nhanVienGUI nvGUI;

    public addNhanVienGUI(nhanVienGUI nvGUI) {
        chieu_cao = 500;
        chieu_rong = 400;
        this.nvGUI = nvGUI;
        init();
    }

    private void init() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(chieu_rong, chieu_cao);
        setBackground(Color.WHITE);
        addNV = new addNhanVien(getWidth(), getHeight());

        add(addNV, BorderLayout.CENTER);
        addNV.btnHuy.addMouseListener(this);
        addNV.btnXacNhan.addMouseListener(this);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private class addNhanVien extends JPanel {

        private Font font_text = new Font("Tahoma", Font.BOLD, 15);
        private JTextField tfTen, tfSdt, tfDiaChi, tfEmail, tfChucVu;
        private JPanel btnXacNhan, btnHuy;

        public addNhanVien(int w, int h) {
            tfTen = new JTextField();
            tfTen.setFont(font_text);
            tfSdt = new JTextField();
            tfSdt.setFont(font_text);
            tfDiaChi = new JTextField();
            tfDiaChi.setFont(font_text);
            tfEmail = new JTextField();
            tfEmail.setFont(font_text);
            tfChucVu = new JTextField();
            tfChucVu.setFont(font_text);
            init(w, h);
        }

        public void init(int w, int h) {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(chieu_rong + 20, chieu_cao));

            JPanel titleGUI_wrap = new JPanel(new BorderLayout());
            titleGUI_wrap.setPreferredSize(new Dimension(chieu_rong, 40));
            JLabel titleGUI = new JLabel("Thêm Nhân viên", JLabel.CENTER);
            titleGUI.setFont(Cacthuoctinh_phuongthuc_chung.font_header);
            titleGUI_wrap.add(titleGUI, BorderLayout.CENTER);
            add(titleGUI_wrap, BorderLayout.NORTH);

            JPanel inputPn = new JPanel();
            inputPn.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;

            JLabel lbTen = new JLabel("Tên nhân viên:");
            lbTen.setFont(font_text);
            lbTen.setForeground(Cacthuoctinh_phuongthuc_chung.sky_blue);
            gbc.gridy = 0;
            inputPn.add(lbTen, gbc);

            tfTen.setPreferredSize(new Dimension(300, 30));
            gbc.gridy = 1;
            inputPn.add(tfTen, gbc);

            JLabel lbSdt = new JLabel("Số điện thoại:");
            lbSdt.setFont(font_text);
            lbSdt.setForeground(Cacthuoctinh_phuongthuc_chung.sky_blue);
            gbc.gridy = 2;
            inputPn.add(lbSdt, gbc);

            tfSdt.setPreferredSize(new Dimension(300, 30));
            gbc.gridy = 3;
            inputPn.add(tfSdt, gbc);

            JLabel lbDC = new JLabel("Địa chỉ:");
            lbDC.setFont(font_text);
            lbDC.setForeground(Cacthuoctinh_phuongthuc_chung.sky_blue);
            gbc.gridy = 4;
            inputPn.add(lbDC, gbc);

            tfDiaChi.setPreferredSize(new Dimension(300, 30));
            gbc.gridy = 5;
            inputPn.add(tfDiaChi, gbc);

            JLabel lbEmail = new JLabel("Email:");
            lbEmail.setFont(font_text);
            lbEmail.setForeground(Cacthuoctinh_phuongthuc_chung.sky_blue);
            gbc.gridy = 6;
            inputPn.add(lbEmail, gbc);

            tfEmail.setPreferredSize(new Dimension(300, 30));
            gbc.gridy = 7;
            inputPn.add(tfEmail, gbc);

            JLabel lbChucVu = new JLabel("Chức vụ:");
            lbChucVu.setFont(font_text);
            lbChucVu.setForeground(Cacthuoctinh_phuongthuc_chung.sky_blue);
            gbc.gridy = 8;
            inputPn.add(lbChucVu, gbc);

            tfChucVu.setPreferredSize(new Dimension(300, 30));
            gbc.gridy = 9;
            inputPn.add(tfChucVu, gbc);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
            buttonPanel.setPreferredSize(new Dimension(chieu_rong, 40));

            btnXacNhan = new JPanel();
            btnXacNhan.setPreferredSize(new Dimension(120, 30));
            cssBtn(btnXacNhan, "Xác nhận", "btnXacNhan");
            buttonPanel.add(btnXacNhan);

            btnHuy = new JPanel();
            btnHuy.setPreferredSize(new Dimension(120, 30));
            cssBtn(btnHuy, "Hủy", "btnHuy");
            buttonPanel.add(btnHuy);

            btnXacNhan.setName("btnXacNhan");
            btnHuy.setName("btnHuy");

            gbc.gridy = 10;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            inputPn.add(buttonPanel, gbc);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            add(inputPn, BorderLayout.CENTER);
        }

        private void cssBtn(JPanel b, String text, String name) {
            JLabel t = new JLabel(text, JLabel.CENTER);
            t.setForeground(Color.WHITE);
            b.setName(name);
            b.add(t);
            b.setBackground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
            b.setPreferredSize(new Dimension(100, (int) b.getPreferredSize().getHeight()));
            b.setOpaque(true);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            JPanel btn = (JPanel) e.getSource();
            switch (btn.getName()) {
                case "btnXacNhan": {
                    String tenNV = addNV.tfTen.getText();
                    String sdt = addNV.tfSdt.getText();
                    String email = addNV.tfEmail.getText();
                    String diaChi = addNV.tfDiaChi.getText();
                    String chucVu = addNV.tfChucVu.getText();
                    int trangThai = 1;

                    nhanVienBUS BUSNV = new nhanVienBUS();

                    nhanVienDTO nv = new nhanVienDTO(tenNV, chucVu, sdt, diaChi, email, trangThai);
                    boolean success = true;
                    for (nhanVienDTO x : BUSNV.getds_nhanVien()) {
                        if (x.getSDT().equals(nv.getSDT())) {
                            success = false;
                            break;
                        }
                    }
                    if (success) {
                        if (BUSNV.themNV(nv)) {
                            nvGUI.them_mot_nv(nv);
                            dispose();
                            JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công");
                        } else {
                            JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại nhập dữ liệu chưa đúng!");
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại!");
                    }

                    break;
                }
                case "btnHuy": {
                    Object[] options = {"Có", "Không"};
                    int r1 = JOptionPane.showOptionDialog(null, "Những thông tin sẽ không được lưu sau khi thoát!\nBạn có muốn tiếp tục thoát?", "Thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (r1 == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        try {
            JPanel btn = (JPanel) e.getSource();
            btn.setBackground(Cacthuoctinh_phuongthuc_chung.sky_blue);
            btn.setOpaque(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        try {
            JPanel btn = (JPanel) e.getSource();
            btn.setBackground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
            btn.setOpaque(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void cssBtn(JPanel b, String text, String name) {
        JLabel t = new JLabel(text, JLabel.CENTER);
        t.setForeground(Color.WHITE);
        b.setName(name);
        b.add(t);
        b.setBackground(Cacthuoctinh_phuongthuc_chung.darkness_blue);
        b.setPreferredSize(new Dimension(100, (int) b.getPreferredSize().getHeight()));
        b.setOpaque(true);
    }

}
