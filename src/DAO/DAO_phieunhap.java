package DAO;

import DTO.chitietphieunhap_DTO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import DTO.phieunhap_DTO;

public class DAO_phieunhap {
	private ConnectDataBase c;
	public DAO_phieunhap() {
		try {
			c = new ConnectDataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<phieunhap_DTO> select_all(){
		ArrayList<phieunhap_DTO> ds = new ArrayList<phieunhap_DTO>();
		try {
			c.connect();
			String sql = "select * from phieunhap";
			ResultSet rs = c.executeQuery(sql);
			while (rs.next()) {
				String MAPN = rs.getString("MAPN");
				String MANV = rs.getString("MANV");
				LocalDate ngay = rs.getDate("NGAYNHAP").toLocalDate();
				double tontien = rs.getDouble("TONGTIEN");
				String MANCC = rs.getString("MANCC");
				phieunhap_DTO h = new phieunhap_DTO(MAPN, MANV, ngay, tontien, MANCC);
				ds.add(h);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ds;
	}
	public void add(phieunhap_DTO h) {
		try {
			c = new ConnectDataBase();
			c.connect();
			String sql = "insert into phieunhap (MAPN,MANV,NGAYNHAP,TONGTIEN,MANCC) " + 
					"values ('"+h.getMAPN()+"','" +h.getMANV()+"','"+h.getNgay()+"'," +h.getTongtien() + 
					",'"+h.getMANCC()+"')";
			 c.executeUpdate(sql);
			System.out.println("lenh da thuc thi " + sql);
			c.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delete(phieunhap_DTO h) {
		try {
			c = new ConnectDataBase();
			c.connect();
			
			String sql = "delete from phieunhap where MAPN = '" + h.getMAPN()+"'";
			
			c.executeUpdate(sql);
			
			c.disconnect();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
        public void delete(String m) {
    try {
        c = new ConnectDataBase();
        c.connect();

        // Lấy danh sách chi tiết phiếu nhập trước khi xóa
        String querySelectCTPN = "SELECT MASP, MASIZE, SOLUONG FROM chitietphieunhap WHERE MAPN = '" + m + "'";
        ResultSet rs = c.executeQuery(querySelectCTPN);
        
        while (rs.next()) {
            String masp = rs.getString("MASP");
            String maSize = rs.getString("MASIZE");
            int soluong = rs.getInt("SOLUONG");

            // Cập nhật số lượng sản phẩm trong bảng chitietsanpham
            updateSoLuongSanPham(masp, maSize, soluong);
        }

        // Sau khi cập nhật chitietsanpham, tiến hành xóa phiếu nhập và chi tiết phiếu nhập
        String queryDeletePN = "DELETE FROM phieunhap WHERE MAPN = '" + m + "'";
        String queryDeleteCTPN = "DELETE FROM chitietphieunhap WHERE MAPN = '" + m + "'";
        c.executeUpdate(queryDeletePN);
        c.executeUpdate(queryDeleteCTPN);

        c.disconnect();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
private void updateSoLuongSanPham(String masp, String maSize, int soluongThayDoi) {
    try {
        ConnectDataBase c = new ConnectDataBase();
        c.connect();

        // Lấy số lượng hiện tại từ bảng chitietsanpham
        String querySelect = "SELECT SOLUONG FROM chitietsanpham WHERE MASP = '" + masp + "' AND MASIZE = '" + maSize + "'";
        ResultSet rs = c.executeQuery(querySelect);

        if (rs.next()) {
            int soluongHienTai = rs.getInt("SOLUONG");
            int soluongMoi = soluongHienTai - soluongThayDoi; // Trừ số lượng chi tiết sản phẩm

            // Cập nhật lại số lượng sản phẩm mới
            String queryUpdate = "UPDATE chitietsanpham SET SOLUONG = " + soluongMoi + " WHERE MASP = '" + masp + "' AND MASIZE = '" + maSize + "'";
            c.executeUpdate(queryUpdate);
        }

        c.disconnect();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

	public void update (phieunhap_DTO h ) {
		try {
			c = new ConnectDataBase();
			c.connect();
			String sql = "update phieunhap set MANV = '" + h.getMANV() + "',NGAYNHAP = '" + h.getNgay() + "', TONGTIEN = "  
					+ h.getTongtien() +",MANCC = '" + h.getMANCC() + "'" +
					"where MAPN = '" + h.getMAPN() + "'" ;
			c.executeUpdate(sql);
			c.disconnect();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	public int select_max() {
		int i = 0;
		try {
			c.connect();
			String sql = "SELECT MAX(DEM) AS maxx FROM phieunhap";
			
			ResultSet rs = c.executeQuery(sql);
			
			 if (rs.next()) {
				 i = rs.getInt("maxx");
			 }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	
	 	public phieunhap_DTO select_byid(String t) {
	 		phieunhap_DTO h = null;
	 		try {
	 			
				c.connect();
				String sql = "select * from phieunhap where MAPN ='" + t +"'";
				
				ResultSet rs = c.executeQuery(sql);
				while(rs.next()) {
					String MAPN = rs.getString("MAPN");
					String MANV = rs.getString("MANV");
					LocalDate ngay = rs.getDate("NGAYNHAP").toLocalDate();
					double tongtien = rs.getDouble("TONGTIEN");
					String MANCC = rs.getString("MANCC");
					h = new phieunhap_DTO(MAPN, MANV, tongtien, MANCC);
					
				}
				
				c.disconnect();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		return h;
	 	}
                public phieunhap_DTO select_byid1(String t) {
	 		phieunhap_DTO h = null;
	 		try {
	 			
				c.connect();
				String sql = "select * from phieunhap where MAPN ='" + t +"'";
				
				ResultSet rs = c.executeQuery(sql);
				while(rs.next()) {
					String MAPN = rs.getString("MAPN");
					String MANV = rs.getString("MANV");
					LocalDate ngay = rs.getDate("NGAYNHAP").toLocalDate();
					double tongtien = rs.getDouble("TONGTIEN");
					String MANCC = rs.getString("MANCC");
					h = new phieunhap_DTO(MAPN, MANV,ngay, tongtien, MANCC);
					
				}
				
				c.disconnect();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		return h;
	 	}
                public int getOldQuantity(String masp, String masize, String mapn) throws SQLException {
    int oldQuantity = 0;
    c.connect();
    
    String query = "SELECT SOLUONG FROM chitietphieunhap WHERE MASP = '" + masp + "' AND MASIZE = '" + masize + "' AND MAPN = '" + mapn + "'";
    
    try {
        ResultSet rs = c.executeQuery(query);
        if (rs.next()) {
            oldQuantity = rs.getInt("SOLUONG");
        }
        rs.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        throw ex;
    } finally {
        c.disconnect();
    }

    return oldQuantity;
}
public void setAfterTT(chitietphieunhap_DTO d, int oldQuantity, int newQuantity) throws SQLException {
    c.connect();
    int currentQuantity = 0;

    // Lấy số lượng hiện tại từ chitietsanpham
    String selectSql = "SELECT SOLUONG FROM chitietsanpham WHERE MASP = '" + d.getMasp() + "' AND MASIZE = '" + d.getMasize() + "'";
    try {
        ResultSet rs = c.executeQuery(selectSql);
        if (rs.next()) {
            currentQuantity = rs.getInt("SOLUONG");
        }
        rs.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        c.disconnect();
        throw ex; // Ném lại ngoại lệ để xử lý
    }

    // Tính toán số lượng cập nhật: hiện tại - số lượng cũ + số lượng mới
    int updatedQuantity = currentQuantity - oldQuantity + newQuantity;

    System.err.println("Số lượng hiện tại: " + currentQuantity);
    System.err.println("Số lượng cũ: " + oldQuantity);
    System.err.println("Số lượng mới: " + newQuantity);
    System.err.println("Số lượng cập nhật: " + updatedQuantity);

    // Cập nhật số lượng mới vào chitietsanpham
    String updateSql = "UPDATE chitietsanpham SET SOLUONG = " + updatedQuantity +
                       " WHERE MASP = '" + d.getMasp() + "' AND MASIZE = '" + d.getMasize() + "'";
    c.executeUpdate(updateSql); // Ném lại ngoại lệ để xử lý
    System.err.println("Cập nhật thành công số lượng mới");
    System.out.println(updateSql);
    c.disconnect();
}

	 	public ArrayList<phieunhap_DTO> search(String MAPN , String MANV ,String ngaytruoc,String ngaysau,double giabe, double gialon,String MANCC) {     
	 		ArrayList<phieunhap_DTO> ds = new ArrayList<phieunhap_DTO>();
	 		
	 		
	 		
	 		
	 		
	 		try {
				c.connect();
				
				String sql = "	SELECT * FROM phieunhap"
						+ "	 WHERE MAPN LIKE '%"+ MAPN +"%'"
						+ "	 AND MANV LIKE '%" +  MANV +"%'"
						+ "	 AND NGAYNHAP BETWEEN '"+ ngaytruoc +"' AND '"+ngaysau +  "' "
						+ "	 AND TONGTIEN BETWEEN "+ giabe  +" AND " + gialon  +" AND "
						+ "	 MANCC LIKE '%"+ MANCC +"%'"
						
						;
				System.out.println(sql);
				ResultSet rs = c.executeQuery(sql);
		
				while (rs.next()) {
					String mapn = rs.getString("MAPN");
					String manv = rs.getString("MANV");
					LocalDate ngay = rs.getDate("NGAYNHAP").toLocalDate();
					double tongtien = rs.getDouble("TONGTIEN");
					String mancc = rs.getString("MANCC");
					phieunhap_DTO m = new phieunhap_DTO(mapn, manv, ngay, tongtien, mancc);
					ds.add(m);
					
				}
				c.disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		return ds;
	 	}
                
                public ArrayList<String> select_hanhdong_phieunhap(String MAQUYEN) {
			ArrayList<String> ds = new ArrayList<String>();
			
			try {
				c.connect();
				
				String sql = "SELECT HANHDONG FROM chitietquyen WHERE MACHUCNANG = 'PN' AND  MAQUYEN ='" + MAQUYEN +"'";
				
			 ResultSet rs = c.executeQuery(sql);
			 
			 while(rs.next()) {	
				 String t = rs.getString("HANHDONG");
				 ds.add(t);
			 }
			 c.disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return ds;
		}
	
	public static void main(String[] args) {
		DAO_phieunhap h = new DAO_phieunhap();
		phieunhap_DTO m = new phieunhap_DTO("pn008", "nc0010", 14,"");
		ArrayList<phieunhap_DTO> ds = h.search("a-z","a-z", 	"2024-05-05", "2024-05-07", 500, 700,"" );
		for (phieunhap_DTO j : ds) {
			System.out.println(j.toString());
		}
		
	}
	
}
