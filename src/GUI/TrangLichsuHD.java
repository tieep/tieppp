package GUI;

import GUI.ChitietHD_GUI;
import DTO.Hoadon_DTO;
import BUS.Hoadon_BUS;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static org.apache.poi.hssf.usermodel.HeaderFooter.date;

//import java.sql.*;
public final class TrangLichsuHD extends JPanel implements MouseListener{

    public boolean inHD = false;
    public String MAHDSelect;
    public JPanel left, right;
    private JPanel p;
    private Hoadon_BUS lshd;
    private int chieurong, chieucao;
    private Color backGroundColor;
    private Font f = new Font("Tahoma", Font.BOLD, 16);
    public  JPanel currentSelectedPanel = null;
    private String[] columnNames = {"MAHD","Ngày" , "Thời gian", "MAKH", "MANV", "Giảm (đ)", "Thanh toán"};
    public TrangLichsuHD(int chieurong, int chieucao) throws SQLException {
        this.chieurong = chieurong;
        this.chieucao = chieucao;
        init();

    }

    public void init() throws SQLException {
        MAHDSelect = null;
        currentSelectedPanel = null;
        this.setPreferredSize(new Dimension(chieurong, chieucao));
        this.setLayout(new BorderLayout(5,0));
        
        lshd = new Hoadon_BUS();
        
        
        right = new JPanel(new BorderLayout(0,0));
        
        right.setBackground(Color.white);
        
        

        p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Chưa lựa chọn Hóa đơn để hiển thị chi tiết", JLabel.CENTER), BorderLayout.CENTER);
       // p.setPreferredSize(new Dimension(chieurong * 3 / 5, 0));
        p.setBackground(Color.WHITE);
        right.add(p);
        left = new JPanel(new FlowLayout(1,0,0));
        left.setPreferredSize(new Dimension((int)chieurong /2  , 35 * lshd.dshoadon.size()));
        left.setBackground(Color.white);
        LocalDate today = LocalDate.now();
        
        // Định dạng ngày theo định dạng yyyy/MM/dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        ArrayList<String> data_filter = new ArrayList<>();
        data_filter.add("");
        data_filter.add(today.format(formatter));
        data_filter.add(today.format(formatter));
        renderLeft(lshd.search(data_filter));
        
         JScrollPane scrollPane = new JScrollPane(left); // Wrap 'left' panel with JScrollPane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       scrollPane.setPreferredSize(new Dimension((int)left.getPreferredSize().getWidth() + 20, (int)left.getPreferredSize().getHeight())); // Set preferred size
       scrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
       this.add(scrollPane,BorderLayout.CENTER);
       right.setPreferredSize(new Dimension(chieurong - (int)scrollPane.getPreferredSize().getWidth(), chieucao));
//    this.add(left);
        this.add(right,BorderLayout.EAST);
    }
    
    public void addHD_gui(TrangLichsuHD hdGUI, Hoadon_DTO hd) {
        JPanel pa = new JPanel(new GridLayout(1, columnNames.length,0,0));
        pa.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#0A3D62")));
        pa.setBackground(Color.white);
        
        

        JLabel lab1 = new JLabel(hd.getNgayHD(), JLabel.CENTER);
        JLabel lab2 = new JLabel(Integer.toString(hd.getMaKH()), JLabel.CENTER);
        JLabel lab3 = new JLabel((String) hd.getMaNV(), JLabel.CENTER);
        JLabel lab4 = new JLabel((String) hd.getMaHD(), JLabel.CENTER);
        JLabel lab5 = new JLabel(formatPrice(String.valueOf((int)hd.getTongTien())), JLabel.CENTER);
        JLabel lab6 = new JLabel(formatPrice(String.valueOf((int)hd.getGiamgia())), JLabel.CENTER);
        JLabel lab7 = new JLabel((String) hd.getThoigian(), JLabel.CENTER);
        pa.add(lab4);
        pa.add(lab1);
        pa.add(lab7);
        pa.add(lab2);
        pa.add(lab3);
        pa.add(lab6);
        pa.add(lab5);
        pa.setPreferredSize(new Dimension((int)left.getPreferredSize().getWidth(),30));
        left.add(pa);
        pa.addMouseListener(this);
        pa.setName(hd.getMaHD());
    }
    public void renderLeft(ArrayList<Hoadon_DTO> list){
        left.removeAll();
        
        
        JPanel titlePanel = new JPanel(new GridLayout(1, columnNames.length,0,0));
        
        titlePanel.setBackground(Color.decode("#60A3BC"));
        for (String col : columnNames) {
            JLabel l = null;
            if(col.equals(columnNames[columnNames.length - 1]))
                l = new JLabel("<html><p style='text-align: center;padding-bottom:2px;'>"+col+"</p></html>", JLabel.CENTER);
            else
                l = new JLabel(col, JLabel.CENTER);
            
            l.setBorder(BorderFactory.createEmptyBorder(10, 0, 8, 0));
            l.setFont(new Font("Tahoma", Font.BOLD, 13));
            l.setForeground(Color.white);
            titlePanel.add(l);
        }
        titlePanel.setPreferredSize(new Dimension((int)left.getPreferredSize().getWidth() ,(int)titlePanel.getPreferredSize().getHeight()));
        left.add(titlePanel);

        
        for (Hoadon_DTO hd : list) {
            addHD_gui(this, hd);
        }
        
       
       left.validate();
       left.repaint();
    }
    private ArrayList<String> extractLabelContents(JPanel pa) {
                ArrayList<String> contents = new ArrayList<>();
                Component[] components = pa.getComponents();
                for (Component component : components) {
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        contents.add(label.getText());
                    }
                }
                return contents;
            }
    
    
    public void search(ArrayList<Hoadon_DTO> list) throws SQLException {
        this.setPreferredSize(new Dimension(chieurong, chieucao));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        right = new JPanel();
        right.setPreferredSize(new Dimension(chieurong * 3 / 5, 0));
        right.setBackground(Color.white);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Chưa lựa chọn Hóa đơn để hiển thị chi tiết", JLabel.CENTER), BorderLayout.CENTER);
        p.setPreferredSize(new Dimension(chieurong * 3 / 5, 0));
        p.setBackground(Color.WHITE);
        right.add(p);
        left = new JPanel();
        left.setPreferredSize(new Dimension(chieurong * 2 / 5, 0));
        left.setBackground(Color.white);
        left.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.decode("#60A3BC")));
//        this.add(left);
//        this.add(right);
        // create title
        String[] columnNames = {"Ngay", "Thoigian", "KH", "Nhanvien", "Hoadon", "Giamgia", "Thanhtien"};
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(1, 0, 0));
        titlePanel.setBackground(Color.decode("#60A3BC"));
        for (String col : columnNames) {

            JLabel l = new JLabel(col, JLabel.CENTER);
            l.setPreferredSize(new Dimension(((chieurong) * 2 / 5) / 7, 30));
            l.setFont(new Font(l.getFont().getName(), Font.CENTER_BASELINE, 12));
            l.setForeground(Color.white);
            titlePanel.add(l);
        }
        left.add(titlePanel);
        for (Hoadon_DTO hd : list) {
            addHD_gui(this, hd);
        }
        left.setPreferredSize(new Dimension((chieurong * 2 / 5), chieucao + chieucao/2));
        JScrollPane scrollPane = new JScrollPane(left); // Wrap 'left' panel with JScrollPane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension((chieurong * 2 / 5), chieucao - (int) left.getPreferredSize().getHeight())); // Set preferred size
        this.add(scrollPane); // Add JScrollPane instead of 'left' directly
//    this.add(left);
        this.add(right);
    }
    public void renderJPanelHoadonAfterUpdate(){
        
    }
    public void removePanel(JPanel itemHD, JPanel control) {
        this.remove(itemHD); // Xóa panel itemNV khỏi container
        this.remove(control); // Xóa panel control khỏi container
        this.revalidate(); // Cập nhật container
        this.repaint(); // Vẽ lại container để hiển thị sự thay đổi
    }

    public void changeColorJPanelChild(JPanel p, Color bg, Color fg) {
        p.setBackground(bg);
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Component[] components = p.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setForeground(fg);

            }
        }
        MouseListener[] mouseListeners = p.getMouseListeners();
        for (MouseListener listener : mouseListeners) {
            p.removeMouseListener(listener);
        }

    }

// reload trang nhân viên
    public void reloadPage() throws SQLException {
        // Xóa toàn bộ nội dung của frame
        this.removeAll();

        // Khởi tạo lại giao diện mới
        this.init();

        // Vẽ lại frame
        revalidate();
        repaint();
    }
    
    private String formatPrice(String price){// đổi từ giá 100000 -> 100,000 đ
        if(!price.equals("")){
            DecimalFormat FormatInt = new DecimalFormat("#,###");
            return FormatInt.format(Integer.parseInt(price));
        }
        return price;
        
    }
    private String getPriceInFormatPrice(String formatprice){ // đổi từ 100.000 đ -> 100000(String)
        return formatprice.replaceAll("[^0-9]", "");
    }
    

    public void reloadSearch(ArrayList<Hoadon_DTO> listhd) throws SQLException {
        this.removeAll();
        this.search(listhd);
        revalidate();
        repaint();
    }

    public void SearchHD(ArrayList<String> data_filter) throws SQLException, ParseException {
   if(lshd.searchFillData(data_filter)!= null)
    this.reloadSearch(lshd.searchFillData(data_filter));
}


    public static void main(String[] args) throws SQLException {
        JFrame f = new JFrame();
        f.setSize(1000, 800);
        TrangLichsuHD p = new TrangLichsuHD(1000, 800);
        f.add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
                JPanel HDItem = (JPanel) e.getSource();
                HDItem.setBackground(Cacthuoctinh_phuongthuc_chung.second_gray);
                if(currentSelectedPanel != null && HDItem != currentSelectedPanel){
                    currentSelectedPanel.setBackground(Color.white);
                    currentSelectedPanel.addMouseListener(this);
                }
                currentSelectedPanel = HDItem;
                MAHDSelect = HDItem.getName();
                
                MouseListener[] mouseListeners = HDItem.getMouseListeners();
                for (MouseListener listener : mouseListeners) {
                    HDItem.removeMouseListener(listener);
                } 
                ArrayList<String> LCont = extractLabelContents(HDItem);
                inHD = true;
                
                try {//{"MAHD","Ngày" , "Thời gian", "MAKH", "MANV", "Giảm", "Thanh toán"};
                    //String maHD, String ngayHD,String time, String maKH, String maNV,String giamgia, String tongtien
                    
                    ChitietHD_GUI s = new ChitietHD_GUI((int)right.getPreferredSize().getWidth(), chieucao, LCont.get(0), LCont.get(1), LCont.get(2), LCont.get(3), LCont.get(4), getPriceInFormatPrice(LCont.get(5)), getPriceInFormatPrice(LCont.get(6)));
                    right.removeAll();
                    right.add(s);
                    right.revalidate();
                    right.repaint();
                
                } catch (SQLException ex) {
                    Logger.getLogger(TrangLichsuHD.class.getName()).log(Level.SEVERE, null, ex);
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
                JPanel HDItem = (JPanel) e.getSource();

                HDItem.setBackground(Cacthuoctinh_phuongthuc_chung.light_gray);
                HDItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JPanel HDItem = (JPanel) e.getSource();

                HDItem.setBackground(Color.white);
            }
}
