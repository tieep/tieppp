
package BUS;

import DAO.ChitietHD_DAO;
import DAO.DAO_chitietsanpham;
import DAO.Hoadon_DAO;
import DTO.ChitietHD_DTO;
import DTO.chitietsanpham_DTO;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChitietHD_BUS {
    public ArrayList<ChitietHD_DTO> list;

    public ChitietHD_BUS(String maHD) throws SQLException {
        list(maHD); 
    }
  public ChitietHD_BUS(){
        try {
            ChitietHD_DAO cthd = new ChitietHD_DAO();
            list = new ArrayList<>();
            list = cthd.list();
        } catch (SQLException ex) {
            Logger.getLogger(ChitietHD_BUS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void list(String maHD) throws SQLException {
        ChitietHD_DAO listsphd = new ChitietHD_DAO();
        list = listsphd.list(maHD);
    }
    public ArrayList<ChitietHD_DTO> getList(){
        return list;
    }
    public void delete(String sohd, String masp, String masize) throws SQLException {
        ChitietHD_DAO cdDAO = new ChitietHD_DAO();
        cdDAO.delete(sohd, masp, masize);
    }
    public ArrayList<chitietsanpham_DTO> listtorestore(String maHD) throws SQLException {
        ChitietHD_DAO listsphd = new ChitietHD_DAO();
        return listsphd.listtorestore(maHD);
    }
    public void addInSQL(ChitietHD_DTO item){
        System.out.println("MAHD la "+item.getMaHD());
        try {
            ChitietHD_DAO cthdDAO= new ChitietHD_DAO();
           
                 cthdDAO.add(item);
        
           
        } catch (SQLException ex) {
            Logger.getLogger(ChitietHD_BUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String[] get_AllSize(String id) throws SQLException
    {
        DAO_chitietsanpham cpDAO = new DAO_chitietsanpham();
        String[] allSize = cpDAO.get_AllSIZE(id);
        return allSize;
    }
    
    public int get_slsp (String id, String s) throws SQLException{
        DAO_chitietsanpham cpDAO = new DAO_chitietsanpham();
        int sl = cpDAO.get_sl(id, s);
        return sl;
    }
    public void update (ChitietHD_DTO ct) {
        System.out.println("bat dau luu chi tiet hoa don xuomg DAO");
        ChitietHD_DAO cdDAO;
        try {
            cdDAO = new ChitietHD_DAO();
            cdDAO.update(ct.getMaHD(), ct.getMaSP(), ct.getMaSize(), ct.getSl());
        } catch (SQLException ex) {
            Logger.getLogger(ChitietHD_BUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void restore(String id, int sl, String s) throws SQLException{
        DAO_chitietsanpham cpDAO = new DAO_chitietsanpham();
        String ids = cpDAO.Get_Masize(s);
        cpDAO.Restore_pro(sl, id, ids);
    }
    
       
       public String Get_Masize(String tensize) throws SQLException{
            DAO_chitietsanpham cpDAO = new DAO_chitietsanpham();
            String ids = cpDAO.Get_Masize(tensize);
            return ids;
       }
         public static void main (String[] args) throws SQLException{
        ChitietHD_BUS hd = new ChitietHD_BUS("HD002");
        hd.listtorestore("HD002");
    }

         
         
}
