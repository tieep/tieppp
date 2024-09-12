package BUS;

import java.util.ArrayList;

import DAO.ConnectDataBase;
import DAO.DAO_phieunhap;
import DTO.phieunhap_DTO;

public class phieunhap_BUS {
	private ArrayList<phieunhap_DTO> dspn;
	public phieunhap_BUS() {
		newlist();
	}
	
	public void newlist() {
		DAO_phieunhap c = new DAO_phieunhap();
		this.dspn = c.select_all();
	}
	
	
	public void add(phieunhap_DTO h) {
		DAO_phieunhap c = new DAO_phieunhap();
		c.add(h);
	}
	
	
	public void delete(phieunhap_DTO h) {
		DAO_phieunhap c = new DAO_phieunhap();
		c.delete(h);
	}
	
	public void updateInSQL(){
        DAO_phieunhap pnDAO = new  DAO_phieunhap();
        for(phieunhap_DTO ncc : dspn){
            pnDAO.update(ncc);
        }
    }
	public void update(phieunhap_DTO h) {
		DAO_phieunhap c = new DAO_phieunhap();
		c.update(h);
	}
	public void delete(String MAPN){
        for(int i=0;i<dspn.size();i++){
            if(dspn.get(i).getMAPN().equals(MAPN))
                dspn.remove(i);
        }
        
    }
	public void deleteInSQL(String maDelete){
        DAO_phieunhap pnDAO = new  DAO_phieunhap();
        pnDAO.delete(maDelete);
    }
	public int stt_max() {
		DAO_phieunhap c = new DAO_phieunhap();
		return c.select_max();
	}
	
	
	public phieunhap_DTO select_by_id(String t ) {
		DAO_phieunhap c = new DAO_phieunhap();
		return c.select_byid(t);
		
	}
	public ArrayList<phieunhap_DTO> dsPN(){
		return this.dspn;
	}
        
        public ArrayList<String> hanhdong_phieunhap(String MAQUYEN){
           
            DAO_phieunhap c = new DAO_phieunhap();
           return c.select_hanhdong_phieunhap(MAQUYEN);
        }
	public ArrayList<phieunhap_DTO> search(String MAPN,String MANV,String ngaytruoc, String ngaysau, double giabe, double gialon , String mancc){
		DAO_phieunhap c = new DAO_phieunhap();
		return c.search(MAPN, MANV, ngaytruoc, ngaysau, giabe, gialon, mancc);
	}
	public ArrayList<phieunhap_DTO> search(ArrayList<String> data_filter){
        ArrayList<phieunhap_DTO> re = new ArrayList<>();
        for(String i : data_filter){
            for(phieunhap_DTO j : dspn){
                boolean cond = true;
                if(!data_filter.get(0).equals(""))
                    cond = j.getMANCC().toLowerCase().contains(i.toLowerCase()) || j.getMANV().toLowerCase().contains(i.toLowerCase()) || j.getMAPN().toLowerCase().contains(i.toLowerCase());
//                if (data_filter.size() > 1) {
//            if (data_filter.get(1).equals("Theo ngày nhập")) {
//                cond=j.getNgay().contains(i);
//            }
//        }
                if(cond)
                    re.add(j);
                       
                   
                     
            }
            
        
            
    }
        return re;
    }
}
