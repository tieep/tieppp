package BUS;

import DTO.Hoadon_DTO;
import DAO.Hoadon_DAO;
import DTO.chitietsanpham_DTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Hoadon_BUS {

    public static int getNumberOfRow() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static boolean addHoaDon(Hoadon_DTO hoaDon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public ArrayList<Hoadon_DTO> dshoadon;

    public Hoadon_BUS() throws SQLException {
        dshoadon = new ArrayList<>();
        list();
    }

    public void list() throws SQLException {
        Hoadon_DAO dshd = new Hoadon_DAO();
        dshoadon = dshd.listchucnang();
    }

    public void delete(String mahd) throws SQLException {
        Hoadon_DAO hdDAO = new Hoadon_DAO();
        hdDAO.delete(mahd);
    }

    public Hoadon_DTO searchHoadon_DTO(String mahd) {
        Hoadon_DTO hdDTO = null;
        try {
            Hoadon_DAO hdDAO = new Hoadon_DAO();
            hdDTO = hdDAO.searchHoadon_DTO(mahd);
        } catch (SQLException ex) {
            Logger.getLogger(Hoadon_BUS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hdDTO;
    }

    public ArrayList<Hoadon_DTO> search_for_ID(String id) throws SQLException {
        Hoadon_DAO hdDAO = new Hoadon_DAO();
        ArrayList<Hoadon_DTO> dshd = hdDAO.search_for_ID(id);
        return dshd;
    }

    public ArrayList<Hoadon_DTO> search_for_Date(String begin, String end) throws SQLException, ParseException {
        Hoadon_DAO hdDAO = new Hoadon_DAO();
        ArrayList<Hoadon_DTO> dshd = hdDAO.search_for_Date(begin, end);
        return dshd;
    }

    public ArrayList<Hoadon_DTO> search_for_IDDate(String id, String begin, String end) throws SQLException, ParseException {
        Hoadon_DAO hdDAO = new Hoadon_DAO();
        ArrayList<Hoadon_DTO> dshd = hdDAO.search_for_IDDate(id, begin, end);
        return dshd;
    }
    private boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
               (date.isEqual(endDate) || date.isBefore(endDate));
    }
    public ArrayList<Hoadon_DTO> search(ArrayList<String> data_filter) {
        
        String value1 = data_filter.get(0);
        String start = data_filter.get(1).replace("/", "-");
        String end = data_filter.get(2).replace("/", "-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(start, formatter);
        LocalDate date2 = LocalDate.parse(end, formatter);
        ArrayList<Hoadon_DTO> re = new ArrayList<>();
        System.out.println("bang nhau? "+start.equals(end));
        System.out.println("so luong "+dshoadon.size());
        for(Hoadon_DTO hd : dshoadon){
            System.out.println("value1 = null "+(value1 == ""));
            if(value1.equals("")){
                System.out.println("dang duyet ? "+hd.getNgayHD());
                System.out.println("search ? "+start);
                if(start.equals(end)){
                        if(hd.getNgayHD().equals(start)) re.add(hd);
                    }else{
                        LocalDate date3 = LocalDate.parse(hd.getNgayHD(), formatter);
                        if (isDateInRange(date3,date1,date2)) re.add(hd);
                    }  
            }else{
                if(hd.getMaHD().equalsIgnoreCase(value1) || hd.getMaNV().equalsIgnoreCase(value1) || String.valueOf(hd.getMaKH()).equals(value1))
                {
                    if(start.equals(end)){
                        if(hd.getNgayHD().equals(start)) re.add(hd);
                    }else{
                        LocalDate date3 = LocalDate.parse(hd.getNgayHD(), formatter);
                        if (isDateInRange(date3,date1,date2)) re.add(hd);
                    }    
                }   
            }
        }
        return re;
    }

    public ArrayList<Hoadon_DTO> searchFillData(ArrayList<String> data_filter) {
        Hoadon_DAO hdDAO;
        try {
            hdDAO = new Hoadon_DAO();
            return hdDAO.searchFillData(data_filter);
        } catch (SQLException ex) {
            Logger.getLogger(Hoadon_BUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public boolean checkInDatetimeValid(Hoadon_DTO hdSelected){
        boolean flag = true;
        String ngay = hdSelected.getNgayHD();
        String time = hdSelected.getThoigian();
        
        // Chuỗi ngày và giờ cần kiểm tra
        String ngayCurrent = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String thoigianCurrent = new SimpleDateFormat("HH:mm:ss").format(new Date());
        
        // Định dạng cho ngày và giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Chuyển đổi các chuỗi thành LocalDateTime
        LocalDateTime dateTime1 = LocalDateTime.parse(ngay + " " + time, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(ngayCurrent + " " + thoigianCurrent, formatter);

        // Tính khoảng cách giữa 2 thời điểm
        Duration duration = Duration.between(dateTime1, dateTime2);

        // Kiểm tra nếu khoảng cách <= 24 giờ (24*60*60 giây)
        if (Math.abs(duration.toSeconds()) > 24 * 60 * 60) 
            flag = false;
        return flag;
    
    }

    public String createMAHD() {
        int max = 0;
        for (int i = 0; i < dshoadon.size(); i++) {
            String MAHDlast = dshoadon.get(i).getMaHD();
            String so = MAHDlast.replaceAll("[^0-9]", "");
            int stt = Integer.parseInt(so) + 1;
            if (stt > max) {
                max = stt;
            }
        }
        return "HD" + max;

    }
    public void update(Hoadon_DTO item) {
        try {
            Hoadon_DAO hdDAO = new Hoadon_DAO();
            hdDAO.updatehd(item);

        } catch (SQLException ex) {
            Logger.getLogger(Hoadon_BUS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean addInSQL(Hoadon_DTO item) {
        try {
            Hoadon_DAO hdDAO = new Hoadon_DAO();
            return hdDAO.add(item);

        } catch (SQLException ex) {
            Logger.getLogger(Hoadon_BUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

//     public static void main(String[] args) throws SQLException {
//        Hoadon_BUS cthd = new Hoadon_BUS();
//        Hoadon_DTO hd = new Hoadon_DTO("HD001","2023-08-13",3,"NV003",1260000,100000);
//        cthd.delete(hd);
}
