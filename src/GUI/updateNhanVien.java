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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class updateNhanVien extends JFrame implements MouseListener {

    private Font font_text = new Font("Tahoma", Font.BOLD, 15);
    private JTextField tfTen, tfSdt, tfDiaChi, tfEmail, tfChucVu;
    private JPanel btnXacNhan, btnHuy;
    private int chieu_rong, chieu_cao, id;
    private nhanVienGUI nvGUI;
    private nhanVienDTO nvDTO;

    public updateNhanVien(nhanVienDTO nvDTO, nhanVienGUI nvGUI) {
        tfTen = new JTextField(nvDTO.getTENNV());
        tfTen.setFont(font_text);
        tfSdt = new JTextField(nvDTO.getSDT());
        tfSdt.setFont(font_text);
        tfDiaChi = new JTextField(nvDTO.getDIACHI());
        tfDiaChi.setFont(font_text);
        tfEmail = new JTextField(nvDTO.getEMAIL());
        tfEmail.setFont(font_text);
        tfChucVu = new JTextField(nvDTO.getCHUCVU());
        tfChucVu.setFont(font_text);
        this.nvDTO = nvDTO;
        this.nvGUI = nvGUI;
        init();
    }

    public void init() {
        setLayout(new BorderLayout());
        setSize(380, 500);
        setPreferredSize(new Dimension(380, 500));
        setUndecorated(true);

        JPanel titleGUI_wrap = new JPanel(new BorderLayout());
        titleGUI_wrap.setPreferredSize(new Dimension(chieu_rong, 40));
        JLabel titleGUI = new JLabel("Sửa Nhân Viên", JLabel.CENTER);
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

        JLabel lbTen = new JLabel("Tên khách hàng:");
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

        add(inputPn, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
        btnXacNhan.addMouseListener(this);
        btnHuy.addMouseListener(this);

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

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            JPanel btn = (JPanel) e.getSource();
            switch (btn.getName()) {
                case "btnHuy":
                    Object[] options = {"Có", "Không"};
                    int r1 = JOptionPane.showOptionDialog(null, "Những thông tin sẽ không được lưu sau khi thoát!\nBạn có muốn tiếp tục thoát?", "Thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (r1 == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                    break;
                case "btnXacNhan":
                    String tenNV = tfTen.getText();
                    String sdt = tfSdt.getText();
                    String email = tfEmail.getText();
                    String diaChi = tfDiaChi.getText();
                    String chucVu = tfChucVu.getText();
                    
                    nhanVienBUS busNV = new nhanVienBUS();
                    nvDTO.setTENNV(tenNV);
                    nvDTO.setCHUCVU(chucVu);
                    nvDTO.setSDT(sdt);
                    nvDTO.setEMAIL(email);
                    nvDTO.setDIACHI(diaChi);
                    
                    busNV.capnhatNV(nvDTO);
                    nvGUI.suaKH(nvDTO);
                    dispose();
                    break;
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

    public static void main(String[] args) {
    }
}
