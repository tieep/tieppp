/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.nhacungcapBUS;
import DTO.nhacungcapDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author hp
 */
public class add_updateNhacungcapGUI extends JFrame implements MouseListener {

    private class add_updateNhacungcap extends JPanel {

        public JLabel[] error;
        public JTextField[] getData;
        private String[] title = {"Tên", "Số điện thoại"};
        private Font font_data = new Font("Tahoma", Font.PLAIN, 14);
        protected JPanel btn_exit;
        protected JPanel btn_submit;
        private String type;

        public add_updateNhacungcap(int chieurong, int chieucao, String type) {

            getData = new JTextField[title.length];
            error = new JLabel[title.length];
            this.type = type;
            init(chieurong, chieucao);
        }

        private void init(int chieurong, int chieucao) {

            setLayout(new FlowLayout(3, 0, 0));
            setPreferredSize(new Dimension(chieurong + 20, chieucao));
            JPanel titleGUI_wrap = new JPanel(new BorderLayout());
            titleGUI_wrap.setPreferredSize(new Dimension(chieurong, 40));
            JLabel titleGUI = null;
            switch (type) {
                case "update":
                    titleGUI = new JLabel("Sua nhà cung cáp".toUpperCase(), JLabel.CENTER);
                    break;
                case "add":
                    titleGUI = new JLabel("Thêm nhà cung cáp".toUpperCase(), JLabel.CENTER);
                    break;
            }
            titleGUI.setFont(Cacthuoctinh_phuongthuc_chung.font_header);
            titleGUI_wrap.add(titleGUI, BorderLayout.CENTER);
            add(titleGUI_wrap);

            for (int i = 0; i < title.length; i++) {
                JPanel item = new JPanel(new FlowLayout(3, 10, 0));
                item.setPreferredSize(new Dimension(chieurong, 100));
                JLabel lb_title = new JLabel(title[i]);
                lb_title.setPreferredSize(new Dimension(chieurong, 30));
                lb_title.setFont(font_data);
                lb_title.setForeground(Cacthuoctinh_phuongthuc_chung.sky_blue);
                item.add(lb_title);

                getData[i] = new JTextField();
                getData[i].setPreferredSize(new Dimension(chieurong - 10, 30));
                item.add(getData[i]);

                error[i] = new JLabel("");
                error[i].setFont(new Font("Tahoma", Font.ITALIC, 14) {
                });
                error[i].setPreferredSize(new Dimension(chieurong, 20));
                error[i].setForeground(Cacthuoctinh_phuongthuc_chung.error);
                item.add(error[i]);

                add(item);
            }

            JPanel btn_wrap = new JPanel(new FlowLayout(1));
            btn_submit = new JPanel();
            cssBtn(btn_submit, "Xác nhận", "btn_submit");

            btn_exit = new JPanel();
            cssBtn(btn_exit, "Hủy", "btn_exit");

            btn_wrap.setPreferredSize(new Dimension(chieurong, (int) btn_submit.getPreferredSize().getHeight() + 20));
            btn_wrap.add(btn_submit);
            btn_wrap.add(btn_exit);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            add(btn_wrap);
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
    private int chieurong, chieucao;
    private add_updateNhacungcap addNCC;
    private boolean flag_ten, flag_sdt;
    private nhacungcapGUI nccGUI;
    private String type;

    public add_updateNhacungcapGUI(nhacungcapGUI nccGUI, String type) {
        this.nccGUI = nccGUI;
        chieurong = chieucao = 300;
        flag_ten = flag_sdt = false;
        this.type = type;
        init();
    }

    private void init() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(chieurong, chieucao);
        setBackground(Color.WHITE);
        addNCC = new add_updateNhacungcap(getWidth(), getHeight(), type);
        addNCC.btn_exit.addMouseListener(this);
        addNCC.btn_submit.addMouseListener(this);
        add(addNCC, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        try {
            JPanel btn = (JPanel) e.getSource();
            switch (btn.getName()) {
                case "btn_exit":
                    Object[] options = {"Có", "Không"};
                    int r1 = JOptionPane.showOptionDialog(null, "Những thông tin sẽ không được lưu sau khi thoát!\nBạn có muốn tiếp tục thoát?", "Thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (r1 == JOptionPane.YES_OPTION) {
                        dispose();
                    } else {

                    }

                    break;
                case "btn_submit":
                    String ten = addNCC.getData[0].getText();
                    String sdt = addNCC.getData[1].getText();
                    nhacungcapBUS nccBUS = new nhacungcapBUS();
                    switch (type) {
                        case "update": {
                            if (!ten.equals("")) {
                                if (!nccBUS.checkTENNCC(ten)) {
                                    flag_ten = false;
                                    addNCC.error[0].setText("Tên chỉ chứa chữ cái");
                                } else {
                                    flag_ten = true;
                                    addNCC.error[0].setText("");
                                }
                            }else{
                                addNCC.error[0].setText("");
                                flag_ten = true;
                            }
                            System.out.println("ten "+flag_ten);
                            if (!sdt.equals("")) {
                                if (!nccBUS.checkSDT(sdt)) {
                                    flag_sdt = false;
                                    addNCC.error[1].setText("Chứa 10 kí tự số và bắt đầu là số 0");
                                } else {
                                    flag_sdt = true;
                                    addNCC.error[1].setText("");
                                }
                            }else{
                                addNCC.error[1].setText("");
                                flag_sdt = true;
                            }
                            System.out.println("ten "+flag_sdt);
                            break;
                        }
                        case "add": {
                            if (ten.equals("")) {
                                addNCC.error[0].setText("Không được để trống");
                            } else if (!nccBUS.checkTENNCC(ten)) {
                                addNCC.error[0].setText("Tên chỉ chứa chữ cái");
                            } else {
                                flag_ten = true;
                                addNCC.error[0].setText("");
                            }
                            if (sdt.equals("")) {
                                addNCC.error[1].setText("Không được để trống");
                            } else if (!nccBUS.checkSDT(sdt)) {
                                addNCC.error[1].setText("Chứa 10 kí tự số và bắt đầu là số 0");
                            } else {
                                flag_sdt = true;
                                addNCC.error[1].setText("");
                            }
                            break;
                        }
                    }
                    
                    if (flag_ten && flag_sdt) {
                        if(type.equals("update") && ten.equals("") && sdt.equals("")){
                            JOptionPane.showMessageDialog(null, "Không nhập thông tin mới!\nNhà cung cấp không thay đổi thông tin!");
                            dispose();
                            return;
                        }
                        for (nhacungcapDTO ncc : nccBUS.getList()) {
                            if (ncc.getTENNCC().equals(ten)) {
                                addNCC.error[0].setText("Tên đã tồn tại");
                                flag_ten = false;

                            }

                            if (("0" + String.valueOf(ncc.getSDT())).equals(sdt)) {
                                addNCC.error[1].setText("Số điện thoại đã tồn tại");
                                flag_sdt = false;

                            }

                            
                            if (!flag_ten || !flag_sdt) {
                                return;
                            }
                        }
                        
                            
                        Object[] options1 = {"Có", "Không"};
                        int r2 = JOptionPane.showOptionDialog(null, "Bạn đã chắc chắn với thông tin nhập vào?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1, options1[0]);
                        if (r2 == JOptionPane.YES_OPTION) {
                            
                            switch (type) {
                                case "update": {
                                    int row = nccGUI.table.getSelectedRow();
                                    String MANCC = (String) nccGUI.table.getValueAt(row, 0);
                                    String TENold = (String) nccGUI.table.getValueAt(row, 1);
                                    int SDTold = Integer.parseInt((String)nccGUI.table.getValueAt(row, 2));
                                    nhacungcapDTO nccDTO = new nhacungcapDTO(MANCC, TENold, SDTold);//
                                    if(!ten.equals("")){
                                         nccGUI.tableModel.setValueAt(ten, row, 1);
                                         nccDTO.setTENNCC(ten);
                                    }
                                       
                                    if(!sdt.equals("")){
                                        nccGUI.tableModel.setValueAt(sdt, row, 2);
                                        nccDTO.setSDT(Integer.parseInt(sdt));
                                    }
                                        
                                    nccGUI.tableModel.fireTableDataChanged();
                                    JOptionPane.showMessageDialog(null, "Sửa nhà cung cấp thành công!\nLưu ý: thay đổi này vẫn chưa được lưu, hãy bấm Lưu/thoát để lưu thay đổi");

                                    
                                    dispose();
                                    nccGUI.listUpdate.add(nccDTO);

                                    break;
                                }
                                case "add": {
                                    JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp mới thành công!");
                                    nhacungcapDTO nccDTO = new nhacungcapDTO(ten, sdt);
                                    nccBUS.add(nccDTO);
                                    nccGUI.addLineDataInTable(nccDTO);
                                    dispose();
                                    break;
                                }
                            }

                            

                        } else {
                            // Thực hiện hành động khi người dùng chọn No hoặc đóng cửa sổ
                        }

                    }

                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

//    public static void main(String[] args) {
//        addNhacungcapGUI k = new addNhacungcapGUI();
//    }
}
