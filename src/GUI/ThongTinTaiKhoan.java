package GUI;

import BUS.Nhanvien_BUS;
import BUS.TaiKhoanBUS;
import DTO.Nhanvien_DTO;
import DTO.TaiKhoanDTO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.Border;

public class ThongTinTaiKhoan extends JPanel implements MouseListener {

    private JPanel pnHeader, pnContentLeft, pnContentRight, pnThaoTac;
    private JPanel[] pnPassword = new JPanel[3];
    private JTextField txtMaNV, txtUsername;
    private JPasswordField[] txtPassword = new JPasswordField[3];
    private JLabel lblMK, lblUser, lblLuu, lblHuy;
    private int choiceTT = 0;
    private Nhanvien_DTO thongTinNV = new Nhanvien_DTO();
    private TaiKhoanDTO thongTinTK = new TaiKhoanDTO();
    private TaiKhoanBUS tkBUS = new TaiKhoanBUS();;

    private Color normal = Color.decode("#0A3D62");
    Font font = new Font("Segoe UI", Font.BOLD, 16);
    Font fontHeader = new Font("Segoe UI", Font.BOLD, 20);
    private int chieurong, chieucao;//Oanh them
    private Color backGroundColor;
    String username, password;

    int width, height;
    int height_row = 30;

    public ThongTinTaiKhoan(StoreScreen s, int width, int height, Color backG_thisJPanel) throws SQLException {
        this.width = width;
        this.height = height;
        this.backGroundColor = backG_thisJPanel;
        username = s.tkUSER.getUsername();
        password = s.tkUSER.getPassword();
        setDuLieu();
        init();
    }

    public ThongTinTaiKhoan(int width, int height) {
        this.width = width;
        this.height = height;
        init();
    }

    //Lấy dữ liệu từ tài khoản đăng nhập
    public void setDuLieu() throws SQLException {
        for (int i = 0; i < tkBUS.getDsTK().size(); i++) {
            if (tkBUS.getDsTK().get(i).getUsername().equals(username)) {
                thongTinTK = tkBUS.getDsTK().get(i);
                break;
            }
        }
        Nhanvien_BUS nvBUS = new Nhanvien_BUS();
        for (int i = 0; i < nvBUS.listnv.size(); i++) {
            if (nvBUS.listnv.get(i).getManv().equals(thongTinTK.getMaNV())) {
                thongTinNV = nvBUS.listnv.get(i);
                break;
            }
        }
    }
    
    public void init() {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BorderLayout(100, 0));
        this.setBackground(backGroundColor);
        this.setOpaque(true);

        //pnHeader
        pnHeader = new JPanel(new FlowLayout());//them cai nay de chinh headerTitle center
        pnHeader.setPreferredSize(new Dimension(width, 50));
        pnHeader.setMaximumSize(new Dimension(width, 50));
        pnHeader.setBackground(normal);
        pnHeader.setOpaque(true);
        JLabel headerTitle = new JLabel("Thông tin tài khoản", JLabel.CENTER);
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setFont(fontHeader);
        pnHeader.add(headerTitle);

        //pnContentLeft
        pnContentLeft = new JPanel();
        pnContentLeft.setLayout(new BoxLayout(pnContentLeft, BoxLayout.Y_AXIS));
        pnContentLeft.setPreferredSize(new Dimension((width / 2) - 20, height));
        pnContentLeft.setMaximumSize(new Dimension(width / 2, height));
        pnContentLeft.add(Box.createRigidArea(new Dimension(0, 50)));

        String thuocTinh[] = {"Mã nhân viên:", "Tên nhân viên:", "Chức vụ:", "SĐT:",
            "Email:", "Địa chỉ:", "Ngày tạo:", "Tên đăng nhập:", "Mật khẩu cũ:", "Mật khẩu mới:", "Mật Khẩu:"};

        String duLieuNV[] = {thongTinNV.getManv(), thongTinNV.getTennv(), thongTinNV.getChucvu(),
            thongTinNV.getSdt() + "", thongTinNV.getEmail(), thongTinNV.getDiachi(),
            (String) thongTinTK.getNgayTao(), thongTinTK.getUsername(), "", "", thongTinTK.getPassword()};

        for (int i = 0; i <= 6; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1, 1, 10, 10));
            panel.setPreferredSize(new Dimension(400, height_row));
            panel.setMaximumSize(new Dimension(400, height_row));
            JLabel lbl = new JLabel(thuocTinh[i], JLabel.LEFT);
            lbl.setFont(font);
            lbl.setPreferredSize(new Dimension(100, height_row)); // Kích thước cố định

            JTextField txt = new JTextField();
            txt.setText(duLieuNV[i]);
            txt.setMinimumSize(new Dimension(350, height_row)); // Kích thước cố định
            txt.setPreferredSize(new Dimension(350, height_row)); // Kích thước cố định
            txt.setMaximumSize(new Dimension(350, height_row)); // Kích thước cố định
            txt.setEditable(false);
            Border padding = BorderFactory.createEmptyBorder(0, 5, 0, 5);  // 10px padding ở mọi cạnh
            txt.setBorder(BorderFactory.createCompoundBorder(txt.getBorder(), padding));
            panel.add(lbl);
            panel.add(txt);
            pnContentLeft.add(panel);
            pnContentLeft.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        //pnContentRight
        pnContentRight = new JPanel();
        pnContentRight.setLayout(new BoxLayout(pnContentRight, BoxLayout.Y_AXIS));
        pnContentRight.setPreferredSize(new Dimension((width / 2) - 20, height));
        pnContentRight.setMaximumSize(new Dimension(width / 2, height));
        pnContentRight.add(Box.createRigidArea(new Dimension(0, 50)));
//        pnContentRight.setBackground(backGroundColor);

        //-------- Username-----
        JPanel pnUsername = new JPanel();
        pnUsername.setLayout(new GridLayout(1, 1, 10, 10));
        pnUsername.setPreferredSize(new Dimension(400, height_row));
        pnUsername.setMaximumSize(new Dimension(400, height_row));
        JLabel lblUsername = new JLabel(thuocTinh[7], JLabel.LEFT);
        lblUsername.setFont(font);
        lblUsername.setPreferredSize(new Dimension(100, height_row)); // Kích thước cố định

        txtUsername = new JTextField();
        txtUsername.setText(duLieuNV[7]);
        txtUsername.setMinimumSize(new Dimension(300, height_row)); // Kích thước cố định
        txtUsername.setPreferredSize(new Dimension(300, height_row)); // Kích thước cố định
        txtUsername.setMaximumSize(new Dimension(300, height_row)); // Kích thước cố định
        txtUsername.setEnabled(false);
        pnUsername.add(lblUsername);
        pnUsername.add(txtUsername);

        //------ Password--------
        int n = 8;
        for (int i = 0; i < 3; i++) {
            pnPassword[i] = new JPanel();
            pnPassword[i].setLayout(new GridLayout(1, 1, 10, 10));
            pnPassword[i].setPreferredSize(new Dimension(400, height_row));
            pnPassword[i].setMaximumSize(new Dimension(400, height_row));
            JLabel lblOldPassword = new JLabel(thuocTinh[n], JLabel.LEFT);
            lblOldPassword.setFont(font);
            lblOldPassword.setPreferredSize(new Dimension(100, height_row)); // Kích thước cố định
            txtPassword[i] = new JPasswordField("", 15);
            txtPassword[i].setMinimumSize(new Dimension(300, height_row)); // Kích thước cố định
            txtPassword[i].setPreferredSize(new Dimension(300, height_row)); // Kích thước cố định
            txtPassword[i].setMaximumSize(new Dimension(300, height_row)); // Kích thước cố định
            pnPassword[i].add(lblOldPassword);
            pnPassword[i].add(txtPassword[i]);
            pnPassword[i].setVisible(false);
            n++;
        }
        txtPassword[2].setText(duLieuNV[10]);
        txtPassword[2].setEnabled(false);
        pnPassword[2].setVisible(true);

        //---------- Nút thay đổi mật khẩu, username
        pnThaoTac = new JPanel();
        pnThaoTac.setLayout(new GridLayout(1, 1, 20, 10));
        pnThaoTac.setPreferredSize(new Dimension(80, height_row + 5));
        pnThaoTac.setMaximumSize(new Dimension(200, height_row + 5));

        pnContentRight.add(pnUsername);
        for (int i = 0; i < 2; i++) {
            pnContentRight.add(Box.createRigidArea(new Dimension(0, 20)));
            pnContentRight.add(pnPassword[i]);
        }

        pnContentRight.add(Box.createRigidArea(new Dimension(0, 20)));
        pnContentRight.add(pnPassword[2]);
        pnContentRight.add(Box.createRigidArea(new Dimension(0, 50)));
        pnContentRight.add(pnThaoTac);

        JPanel pnCententMain = new JPanel();
        pnCententMain.setLayout(new FlowLayout());
        pnCententMain.add(pnContentLeft);
        pnCententMain.add(pnContentRight);
        pnCententMain.setBackground(Color.white);
        this.add(pnHeader, BorderLayout.NORTH);
        this.add(pnCententMain, BorderLayout.CENTER);
    }

    public void initThaoTac_macdinh() {
        lblMK = new JLabel("Đổi mật khẩu", JLabel.CENTER);
        lblMK.setPreferredSize(new Dimension(80, 35));
        lblMK.setForeground(Color.white);
        lblMK.setFont(font);
        lblMK.setBackground(normal);
        lblMK.setOpaque(true);
        lblMK.addMouseListener(this);
        lblMK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

//        lblUser = new JLabel("Sửa tên đăng nhập", JLabel.CENTER);
//        lblUser.setPreferredSize(new Dimension(150, 35));
//        lblUser.setForeground(Color.white);
//        lblUser.setFont(font);
//        lblUser.setBackground(normal);
//        lblUser.setOpaque(true);
//        lblUser.addMouseListener(this);
//        lblUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnThaoTac.add(lblMK);
//        pnThaoTac.add(lblUser);
    }

    public void initSua() {
        lblLuu = new JLabel("Lưu", JLabel.CENTER);
        lblLuu.setPreferredSize(new Dimension(80, 35));
        lblLuu.setForeground(Color.white);
        lblLuu.setFont(font);
        lblLuu.setBackground(normal);
        lblLuu.setOpaque(true);
        lblLuu.addMouseListener(this);
        lblLuu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblHuy = new JLabel("Huỷ", JLabel.CENTER);
        lblHuy.setPreferredSize(new Dimension(80, 35));
        lblHuy.setForeground(Color.white);
        lblHuy.setFont(font);
        lblHuy.setBackground(normal);
        lblHuy.setOpaque(true);
        lblHuy.addMouseListener(this);
        lblHuy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pnThaoTac.add(lblLuu);
        pnThaoTac.add(lblHuy);

    }

    public boolean check_OldPassword(String pass) {
        if (pass.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!thongTinTK.getPassword().equals(pass)) {
            JOptionPane.showMessageDialog(null,
                    "Sai mật khẩu cũ, vui lòng nhập lại !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtPassword[0].setText("");
            return false;
        }
        return true;
    }

    public boolean check_NewPassword(String pass) {
        if (!tkBUS.checkPASSWORD(pass)) {
            JOptionPane.showMessageDialog(null,
                    "<html>Chỉ được chứa kí tự số, chữ hoa<br>Chữ thường, kí tự đặc biệt: !, @</html>", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean check_Usename(String username) {
        if (username.equals(thongTinTK.getUsername())) {
            return true;
        }
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Username không được để trống, xin vui lòng nhập username !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        for (int i = 0; i < tkBUS.getDsTK().size(); i++) {
            if (tkBUS.getDsTK().get(i).getUsername().equals(username)) {
                JOptionPane.showMessageDialog(null,
                        "Username đã tồn tại", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ThongTinTaiKhoan t = new ThongTinTaiKhoan(800, 500);
        t.initThaoTac_macdinh();
        JFrame f = new JFrame();
        f.add(t);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel lbl = (JLabel) e.getSource();
        if (lbl == lblHuy) {
            if (choiceTT == 1) {
                pnPassword[0].setVisible(false);
                pnPassword[1].setVisible(false);
                pnPassword[2].setVisible(true);
            } else {
                txtUsername.setText(thongTinTK.getUsername());
                txtUsername.setEnabled(false);
            }
            pnThaoTac.removeAll();
            initThaoTac_macdinh();
            this.revalidate();
            this.repaint();
        }
        if (lbl == lblMK) {
            choiceTT = 1;
            txtPassword[0].setText("");
            txtPassword[1].setText("");
            pnPassword[0].setVisible(true);
            pnPassword[1].setVisible(true);
            pnPassword[2].setVisible(false);
            pnThaoTac.removeAll();
            initSua();
            this.revalidate();
            this.repaint();
        }
//        if (lbl == lblUser) {
//            choiceTT = 2;
//            pnThaoTac.removeAll();
//            initSua();
//            txtUsername.setEnabled(true);
//            this.revalidate();
//            this.repaint();
//        }
        if (lbl == lblLuu) {
            if (choiceTT == 1) {
                String oldPassword = new String(txtPassword[0].getPassword());// Lấy dữ liệu đã nhập từ JPasswordField
                String newPassword = new String(txtPassword[1].getPassword());
                if (check_OldPassword(oldPassword)) {
                    if (check_NewPassword(newPassword)) {
                        thongTinTK.setPassword(newPassword);
                        tkBUS.set(thongTinTK);

                        JOptionPane.showMessageDialog(null,
                                "Bạn đã thay đổi mật khẩu thành công !", "Thông báo", JOptionPane.DEFAULT_OPTION);
                        pnPassword[0].setVisible(false);
                        pnPassword[1].setVisible(false);
                        pnPassword[2].setVisible(true);
                        pnThaoTac.removeAll();
                        initThaoTac_macdinh();
                        this.revalidate();
                        this.repaint();
                    }
                }
            }
//            if (choiceTT == 2) {
//                if (check_Usename(txtUsername.getText())) {
//                    thongTinTK.setUsername(txtUsername.getText());
//                    tkBUS.set(thongTinTK);
//                    JOptionPane.showMessageDialog(null,
//                            "Bạn đã thay đổi tên đăng nhập thành công !", "Thông báo", JOptionPane.DEFAULT_OPTION);
//                    txtUsername.setEnabled(false);
//                    pnThaoTac.removeAll();
//                    initThaoTac_macdinh();
//                    this.revalidate();
//                    this.repaint();
//                }
//            }
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
