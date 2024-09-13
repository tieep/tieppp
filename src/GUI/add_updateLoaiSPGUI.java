/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.loaiSPBUS;
import DTO.loaiSP;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

/**
 *
 * @author hp
 */
public class add_updateLoaiSPGUI extends JFrame implements MouseListener{

    private class add_updateLoaiSP extends JPanel {

        public JLabel error;
        public JTextField getData;
        private String title = "Tên";
        private Font font_data = new Font("Tahoma", Font.PLAIN, 14);
        protected JPanel btn_exit;
        protected JPanel btn_submit;
        protected JComboBox<String> comboBox ;
        private String type ;
        
        public add_updateLoaiSP(int chieurong, int chieucao,String type) {
            
            getData = new JTextField();
            error = new JLabel();
            this.type = type;
            init(chieurong, chieucao);
        }

        private void init(int chieurong, int chieucao) {

            setLayout(new FlowLayout(3, 0, 0));
            setPreferredSize(new Dimension(chieurong+20, chieucao));
            JPanel titleGUI_wrap = new JPanel(new BorderLayout());
            titleGUI_wrap.setPreferredSize(new Dimension(chieurong, 40));
            JLabel titleGUI = null;
            switch(type){
                case "update":
                    titleGUI = new JLabel("Sửa loại sản phẩm".toUpperCase(), JLabel.CENTER);
                    break;
                case "add":
                    titleGUI = new JLabel("Thêm loại sản phẩm".toUpperCase(), JLabel.CENTER);
                    break;
            }
            
            titleGUI.setFont(Cacthuoctinh_phuongthuc_chung.font_header);
            titleGUI_wrap.add(titleGUI, BorderLayout.CENTER);
            add(titleGUI_wrap);

            
                JPanel item = new JPanel(new FlowLayout(3, 10, 0));
                item.setPreferredSize(new Dimension(chieurong, 100));
                JLabel lb_title = new JLabel(title);
                lb_title.setPreferredSize(new Dimension(chieurong, 30));
                lb_title.setFont(font_data);
                lb_title.setForeground(Cacthuoctinh_phuongthuc_chung.sky_blue);
                item.add(lb_title);

                getData = new JTextField();
                getData.setPreferredSize(new Dimension(chieurong -10, 30));
                item.add(getData);

                error = new JLabel("");
                error.setFont(new Font("Tahoma", Font.ITALIC, 14) {
                });
                error.setPreferredSize(new Dimension(chieurong, 20));
                error.setForeground(Cacthuoctinh_phuongthuc_chung.error);
                item.add(error);

                
             if(type.equals("update")){
                int row = loaiGUI.table.getSelectedRow();
                String status = (String) loaiGUI.table.getValueAt(row, 2);
                loaiSPBUS lBUS = new loaiSPBUS();
        ArrayList<loaiSP> list = lBUS.getList();
        
            comboBox = new JComboBox<>();
            comboBox.addItem("Đang bán");
            comboBox.addItem("Ngừng bán");
            comboBox.setSelectedItem(status);
                item.add(comboBox);
                item.setPreferredSize(new Dimension(chieurong, 120));
             }
            add(item);

            JPanel btn_wrap = new JPanel(new FlowLayout(1));

            btn_exit = new JPanel();
            cssBtn(btn_exit, "Hủy", "btn_exit");

            btn_submit = new JPanel();
            cssBtn(btn_submit, "Xác nhận", "btn_submit");
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
    private add_updateLoaiSP addLoai;
    private boolean flag_ten;
    private loaiSPGUI loaiGUI;
    private String type;
    public add_updateLoaiSPGUI(loaiSPGUI loaiGUI, String type) {
        this.loaiGUI=loaiGUI;
        chieurong = 300;
        chieucao = 250;
        flag_ten  = false;
        this.type = type;
        init();
    }

    private void init() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(chieurong, chieucao);
        setBackground(Color.WHITE);
        addLoai = new add_updateLoaiSP(getWidth(), getHeight(),type);
        addLoai.btn_exit.addMouseListener(this);
        addLoai.btn_submit.addMouseListener(this);
        add(addLoai, BorderLayout.CENTER);
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
                    int r1 = JOptionPane.showOptionDialog(null, "Những thông tin sẽ không được lưu sau khi thoát!\nBạn có muốn tiếp tục thoát?", "Thoát", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                    if (r1 == JOptionPane.YES_OPTION) {
                        dispose();
                    } else {
                       
                    }

                    break;
                case "btn_submit":
                    String ten = addLoai.getData.getText();
                    
                    loaiSPBUS loaiBUS = new loaiSPBUS();
                    
                    switch (type) {
                        case "update": {
                            if (!ten.equals("")) {
                                if (!loaiBUS.checkTENLOAI(ten)) {
                                    flag_ten = false;
                                    addLoai.error.setText("Tên không chứa kí tự đặc biệt");
                                } else {
                                    flag_ten = true;
                                    addLoai.error.setText("");
                                }
                            }else{
                                addLoai.error.setText("");
                                flag_ten = true;
                            }
                            
                            break;
                        }
                        case "add": {
                            if (ten.equals("")) {
                        addLoai.error.setText("Không được để trống");
                    } else if (!loaiBUS.checkTENLOAI(ten)) {
                        addLoai.error.setText("Tên không chứa kí tự đặc biệt");
                    } else {
                        flag_ten = true;
                        addLoai.error.setText("");
                    }
                            break;
                        }
                    }
                    
                    
                   

                    if (flag_ten) {
                        
                        for(loaiSP s: loaiBUS.getList()){
                                if(s.getTENLOAI().equals(ten)){
                                    addLoai.error.setText("Tên đã tồn tại");
                                    flag_ten = false;
                                    return;
                                }
                            }
                        
                        Object[] options1 = {"Có", "Không"};
                        int r2 = JOptionPane.showOptionDialog(null, "Bạn đã chắc chắn với thông tin nhập vào?", "Xác nhận", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options1,options1[0]);
                        if (r2 == JOptionPane.YES_OPTION) {
                            switch (type) {
                                case "update": {
                                    String status = (String)addLoai.comboBox.getSelectedItem();
                                    int row = loaiGUI.table.getSelectedRow();
                                    String MAold = (String) loaiGUI.table.getValueAt(row, 0);
                                    String TENold = (String) loaiGUI.table.getValueAt(row, 1);
                                    String STATUSold = (String) loaiGUI.table.getValueAt(row, 2);
                                    loaiSP loaiDTO = new loaiSP(MAold,TENold,(STATUSold.equals("Đang bán"))?1:0);
                                    
                                    System.out.println(ten.equals("") && status.equals(STATUSold));
                                    if(ten.equals("") && status.equals(STATUSold)){
                                        JOptionPane.showMessageDialog(null, "Không có dữ liệu nhập mới!\nThông tin không thay đổi");
                                        dispose();
                                        return;
                                    }
                                    
                                    if(!ten.equals("")){
                                         loaiGUI.tableModel.setValueAt(ten, row, 1);
                                         loaiDTO.setTENLOAI(ten);
                                    }
                                    if(!status.equals(STATUSold)){
                                        loaiGUI.tableModel.setValueAt(status, row, 2);
                                         loaiDTO.setTINHTRANG((status.equals("Đang bán"))?1:0);
                                    }
                                    
                                    loaiGUI.tableModel.fireTableDataChanged();
                                    JOptionPane.showMessageDialog(null, "Sửa loại thành công!\nLưu ý: thay đổi này vẫn chưa được lưu, hãy bấm Lưu/thoát để lưu thay đổi");

                                    
                                    dispose();
                                    loaiGUI.listUpdate.add(loaiDTO);
                                     System.out.println("ma "+loaiDTO.getMALOAI()+" ten "+loaiDTO.getTENLOAI()+" TRANG THAI "+loaiDTO.getTINHTRANG());
                                    break;
                                }
                                case "add": {
                                    JOptionPane.showMessageDialog(null, "Thêm loại sản phẩm mới thành công!");
                                    loaiSP loaiDTO = new loaiSP(ten);
                                    loaiBUS.add(loaiDTO);
                                    loaiGUI.addLineDataInTable(loaiDTO); 
                                    dispose();
                                    break;
                                }
                            }
                            
                               
                          
                           
                            
                           

                        } else {
                             
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
      //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
//
}
