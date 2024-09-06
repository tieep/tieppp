
package GUI;
import DTO.SanPhamDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CartGUI extends JPanel {
    private int width, height;
    private JPanel wrapHeaderCart;
    private JPanel wrapCenterCart;
    private JPanel wrapBottomCart;
    private JScrollPane scrollPane;
    public CartGUI(int w, int h) {
        width = w;
        height = h;
        init();
    }

    private void init() {
        headerCart();
        centerCart();
        bottomCart();
        setLayout(new BorderLayout(0,10));
        add(wrapHeaderCart,BorderLayout.NORTH);
        
        add(scrollPane,BorderLayout.CENTER);
        add(wrapBottomCart,BorderLayout.SOUTH);
        setBorder(BorderFactory.createLineBorder(Color.decode("#60A3BC"), 1));
    }
    
    private void headerCart(){
        wrapHeaderCart = new JPanel(new BorderLayout(0,0));
        int hHeader = height/11;
        wrapHeaderCart.setPreferredSize(new Dimension(width, hHeader));
        
        
        JPanel wrapSearchMAKH = new JPanel(new FlowLayout(3,10,0));
        
        
        JPanel searchMAKH = new JPanel(new GridLayout(2, 1));
        
        searchMAKH.setPreferredSize(new Dimension(width/6, hHeader));
        JLabel MAKH = new JLabel("Tìm theo MAKH",JLabel.CENTER);
        JTextField inputMAKH = new JTextField();
        searchMAKH.add(MAKH);
        searchMAKH.add(inputMAKH);
        
        JLabel iconSearch = new JLabel(new ImageIcon("./src/images/search_icon.png"),JLabel.CENTER);
        wrapSearchMAKH.add(searchMAKH);
        wrapSearchMAKH.add(iconSearch);
        
        
        JPanel wrapInforKH = new JPanel(new GridLayout(2, 1));
        
        String[] columnNames = {"Tên khách hàng", "Điểm"};
        String[] inforCus = {"Nguyen Van A asdas hdjashdj adhaj","230"};
        for(int i = 0; i<2 ; i++){
            JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
            String[] use = null;
            switch(i){
                case 0:
                    use=columnNames;
                    break;
                case 1:
                    use=inforCus;
                    break;
            }
            JLabel col1 = new JLabel(use[0],JLabel.CENTER);
            col1.setPreferredSize(new Dimension(width/3,(int)col1.getPreferredSize().getHeight()));
            line.add(col1);
            line.add(new JLabel(use[1],JLabel.CENTER));
            wrapInforKH.add(line);
        }
//        for(String i: columnNames){
//            JLabel l = new JLabel(i,JLabel.CENTER);
//            
//            wrapInforKH.add(l);
//        }
//        JLabel TENKH = new JLabel("",JLabel.CENTER);
//        JLabel POINT = new JLabel("230",JLabel.CENTER);
        
        wrapInforKH.setPreferredSize(new Dimension(width/2,hHeader));
        
//        wrapInforKH.add(TENKH);
//        wrapInforKH.add(POINT);
        
        JCheckBox checkBox1 = new JCheckBox("Dùng điểm");
        checkBox1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        
        
        setPreferredSize(new Dimension(width, height));
        
        wrapHeaderCart.add(wrapSearchMAKH,BorderLayout.WEST);
        wrapHeaderCart.add(wrapInforKH,BorderLayout.CENTER);
        wrapHeaderCart.add(checkBox1,BorderLayout.EAST);
        
    }

    private void centerCart(){
        wrapCenterCart = new JPanel(new FlowLayout(1,0,10));
        
        JPanel pro = product();
        wrapCenterCart.setPreferredSize(new Dimension(width - 10,(int)pro.getPreferredSize().getHeight()*13 + 10));
        
        wrapCenterCart.add(product());
        wrapCenterCart.add(product());
        wrapCenterCart.add(product());
        wrapCenterCart.add(product());
        wrapCenterCart.add(product());
        wrapCenterCart.add(product());
        wrapCenterCart.add(product());
        wrapCenterCart.add(product());wrapCenterCart.add(product());
        wrapCenterCart.add(product());wrapCenterCart.add(product());
        wrapCenterCart.add(product());
        wrapCenterCart.add(product());
        
        
        scrollPane = new JScrollPane(wrapCenterCart);
        
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("#60A3BC")));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
           
    }
    
    private JPanel product(){
        JPanel product =  new JPanel(new BorderLayout(10,0));
        
        
        JPanel wrapCenter = new JPanel(new GridLayout(3,1,0,5));
        
        
        JLabel price = new JLabel(89000+"", JLabel.CENTER);
        JLabel titleSize = new JLabel("Size", JLabel.CENTER);
        String[] options = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> size = new JComboBox<>(options);
        
        JLabel remove = new JLabel(new ImageIcon("./src/images/remove_icon.png"));
        
        
        JPanel wrapName = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        wrapName.setBackground(Color.white);
        wrapName.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        JLabel name = new JLabel("BCCBCB SBD ", JLabel.CENTER);
        name.setOpaque(true);
        name.setBackground(Color.white);
        name.setForeground(Color.decode("#0A3D62"));
        
        name.setFont(new Font("Tahoma",Font.PLAIN,17));
        wrapName.add(name);
        
        JPanel wrapSize = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
        wrapSize.add(titleSize);
        wrapSize.setBackground(Color.white);
        titleSize.setFont(new Font("Tahoma",Font.PLAIN,15));
        wrapSize.add(size);
       
        JPanel wrapPrice_Quantity = new JPanel(new BorderLayout());
        wrapPrice_Quantity.setBackground(Color.white);
        wrapPrice_Quantity.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        JPanel wrapQuantity = new JPanel(new GridLayout(1,3,2,0));
        wrapQuantity.setPreferredSize(new Dimension(width/9,25));
        JLabel []updateQuantity = new JLabel[3];
        String []btnUpdate = {"-","1","+"};
        for(int i = 0 ; i<btnUpdate.length ; i++){
            updateQuantity[i] = new JLabel(btnUpdate[i],JLabel.CENTER);
            updateQuantity[i].setFont(new Font("Tahoma",Font.BOLD,14));
            updateQuantity[i].setOpaque(true);
            if( i == 1 )
                updateQuantity[i].setBackground(Color.white);
            else{
                updateQuantity[i].setBackground(Color.decode("#60A3BC"));
                updateQuantity[i].setForeground(Color.white);
            }
                
            wrapQuantity.add(updateQuantity[i]);
        }
        price.setFont(new Font("Tahoma",Font.PLAIN,16));
        price.setOpaque(true);
        price.setBackground(Color.white);
        price.setForeground(Color.decode("#60A3BC"));
        wrapPrice_Quantity.add(price,BorderLayout.WEST);
        wrapPrice_Quantity.add(wrapQuantity,BorderLayout.EAST);
        
        
        wrapCenter.add(wrapName);
        wrapCenter.add(wrapSize);
        wrapCenter.add(wrapPrice_Quantity);
//        wrapSouth.add();
        remove.setPreferredSize(new Dimension(width/12,(int)product.getPreferredSize().getHeight()));
        remove.setOpaque(true);
        remove.setBackground(Color.decode("#D9D9D9"));
        wrapCenter.setBackground(Color.white);
        product.setBackground(Color.white);
        product.add(wrapCenter,BorderLayout.CENTER);
        product.add(remove,BorderLayout.EAST);
        product.setPreferredSize(new Dimension(width*2/3 ,(int)product.getPreferredSize().getHeight()));
        
        return product;
    }
    
    private JPanel product(SanPhamDTO sp){
        JPanel product =  new JPanel(new BorderLayout(0,0));
        
        
        JPanel wrapCenter = new JPanel(new GridLayout(3,1,0,5));
        JLabel name = new JLabel(sp.getTenSP(), JLabel.CENTER);
        JLabel price = new JLabel(sp.getPrice()+"", JLabel.CENTER);
        JLabel titleSize = new JLabel("Size", JLabel.CENTER);
        String[] options = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> size = new JComboBox<>(options);
        JLabel remove = new JLabel(new ImageIcon("./src/images/remove_icon.png"));
        
        
        JPanel wrapName = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        wrapName.add(name);
        
        JPanel wrapSize = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        wrapSize.add(titleSize);
        wrapSize.add(size);
        
        JPanel wrapPrice_Quantity = new JPanel(new BorderLayout());
        JPanel wrapQuantity = new JPanel(new GridLayout(1,3,2,0));
        wrapQuantity.setPreferredSize(new Dimension(width/9,25));
        JLabel []updateQuantity = new JLabel[3];
        String []btnUpdate = {"-","1","+"};
        for(int i = 0 ; i<btnUpdate.length ; i++){
            updateQuantity[i] = new JLabel(btnUpdate[i],JLabel.CENTER);
            updateQuantity[i].setFont(new Font("Tahoma",Font.BOLD,14));
            updateQuantity[i].setOpaque(true);
            if( i == 1 )
                updateQuantity[i].setBackground(Color.white);
            else{
                updateQuantity[i].setBackground(Color.decode("#60A3BC"));
                updateQuantity[i].setForeground(Color.white);
            }
                
            wrapQuantity.add(updateQuantity[i]);
        }
        wrapPrice_Quantity.add(price,BorderLayout.WEST);
        wrapPrice_Quantity.add(wrapQuantity,BorderLayout.EAST);
        
        
        wrapCenter.add(wrapName);
        wrapCenter.add(wrapSize);
        wrapCenter.add(wrapPrice_Quantity);
//        wrapSouth.add();
        product.add(wrapCenter,BorderLayout.CENTER);
        product.setPreferredSize(new Dimension(width,(int)product.getPreferredSize().getHeight()));
        
        return product;
    }
    private void bottomCart(){
        wrapBottomCart = new JPanel(new BorderLayout(0,0));
        
        JPanel centerBottom = new JPanel(new GridLayout(3,1,0,0));
        
        JButton pay = new JButton("Thanh toán");
        pay.setBackground(Color.decode("#0A3D62"));
        pay.setForeground(Color.decode("#60A3BC"));
        pay.setFont(new Font("Tahoma", Font.PLAIN, 18));
        
        JLabel []JL_title = new JLabel[3];
        JLabel []JL_value = new JLabel[3];
        JPanel []JP_line = new JPanel[3];
        String []title = {"Tổng cộng","Giảm giá","Thành tiền"};
        String[] values = {"10000000", "20000000", "30000000"};
        
        for( int i = 0; i<JP_line.length ; i++){
            JP_line[i] = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
            
        JP_line[i].setBackground(Color.white);
            JL_title[i] = new JLabel(title[i]);
            JL_value[i] = new JLabel(values[i]);
            if(i != JP_line.length - 1 ){
                JL_title[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
                JL_value[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
            }else{
                JL_title[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
                JL_value[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
            }
               
            JL_title[i].setPreferredSize(new Dimension(width/8,(int)JL_title[i].getPreferredSize().getHeight()));
            JP_line[i].add(JL_title[i]);
            JP_line[i].add(JL_value[i]);
                    
            centerBottom.add(JP_line[i]);
        }
        wrapBottomCart.setBackground(Color.blue);
        wrapBottomCart.add(centerBottom,BorderLayout.CENTER);
        wrapBottomCart.add(pay,BorderLayout.EAST);
    }
    public static void main(String[] args) {
        JFrame f = new JFrame("Cart GUI");
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new CartGUI(800, 600));
        f.pack(); // Adjust size to fit components
        f.setVisible(true);
    }
}
