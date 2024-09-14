/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.nhanVienDAO;
import DTO.Hoadon_DTO;
import DTO.nhanVienDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class nhanVienBUS {

    private ArrayList<nhanVienDTO> ds_nhanVien;
    private nhanVienDAO dao = new nhanVienDAO();

    public nhanVienBUS() {
        ds_nhanVien = new ArrayList<>();
        ds_nhanVien = dao.ds_nhanVien();
    }

    public ArrayList<nhanVienDTO> getds_nhanVien() {
        return ds_nhanVien;
    }

    public boolean checkTENNV(String t) {
        //tên nhân viên không chứa số và các kí tự đặc biệt
        String regex = "^[\\p{L} ]+$";
        return t.matches(regex);
    }

    public boolean checkDCHI(String address) {
        return address != null && !address.trim().isEmpty() && address.length() <= 250;
    }

    public boolean checkEMAIL(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean checkSDT(String sdt) {
        String regex = "^0\\d{9}$";
        return sdt.matches(regex);
    }

    private int Maxid(String prefix) {
        int maxNumber = 0;
        for (int i = 0; i < ds_nhanVien.size(); i++) {
            String employeeId = ds_nhanVien.get(i).getMANV();
            if (employeeId.startsWith(prefix)) {
                String numberPart = employeeId.substring(2);
                int number = Integer.parseInt(numberPart);
                if (number > maxNumber) {
                    maxNumber = number;
                    System.out.println(maxNumber);
                }
            }
        }
        return maxNumber;

    }

    private String createidNV() {
        int stt = Maxid("NV") + 1;
        return "NV" + stt;
    }

    public boolean themNV(nhanVienDTO nv) {
        if (checkTENNV(nv.getTENNV()) && checkEMAIL(nv.getEMAIL()) && checkDCHI(nv.getDIACHI()) && checkSDT(nv.getSDT())) {
            nv.setMANV(createidNV());
            return dao.themNV(nv);
        } else if (checkTENNV(nv.getTENNV()) == false) {
            Object[] options = {"Đồng ý"};
            JOptionPane.showOptionDialog(null,
                    "Tên khách hàng không được chứa ký tự đặc biệt",
                    "Thông báo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        } else if (checkEMAIL(nv.getEMAIL()) == false) {
            Object[] options = {"Đồng ý"};
            JOptionPane.showOptionDialog(null,
                    "Email không hợp lệ",
                    "Thông báo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        } else if (checkDCHI(nv.getDIACHI()) == false) {
            Object[] options = {"Đồng ý"};
            JOptionPane.showOptionDialog(null,
                    "Địa chỉ không hợp lệ",
                    "Thông báo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        } else if (checkSDT(nv.getSDT()) == false) {
            Object[] options = {"Đồng ý"};
            JOptionPane.showOptionDialog(null,
                    "Bắt đầu bằng số 0 và chỉ chứa 10 số",
                    "Thông báo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        }
        return false;
    }

    public boolean capnhatNV(nhanVienDTO nv) {
        if (checkTENNV(nv.getTENNV()) && checkEMAIL(nv.getEMAIL()) && checkDCHI(nv.getDIACHI())) {
            Object[] options = {"Đồng ý"};
            JOptionPane.showOptionDialog(null,
                    "Cập nhật Nhân viên thành công " + nv.getTENNV(),
                    "Thông báo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);
            return dao.capnhatNV(nv);
        } else if (checkTENNV(nv.getTENNV()) == false) {
            Object[] options = {"Đồng ý"};
            JOptionPane.showOptionDialog(null,
                    "Tên khách hàng không được chứa ký tự đặc biệt",
                    "Thông báo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        } else if (checkEMAIL(nv.getEMAIL()) == false) {
            Object[] options = {"Đồng ý"};
            JOptionPane.showOptionDialog(null,
                    "Email không hợp lệ",
                    "Thông báo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        } else if (checkDCHI(nv.getDIACHI()) == false) {
            Object[] options = {"Đồng ý"};
            JOptionPane.showOptionDialog(null,
                    "Vui lòng không để trống địa chỉ",
                    "Thông báo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);
            return false;
        }
        return false;
    }

    public int xoaInSQL(String id) {
        nhanVienDAO dao = new nhanVienDAO();
        return dao.xoaNV(id);
    }

    public ArrayList<nhanVienDTO> search(ArrayList<String> data_filter) {
        ArrayList<nhanVienDTO> re = new ArrayList<>();
        for (nhanVienDTO nv : ds_nhanVien) {
            boolean maNV = nv.getMANV().contains(data_filter.get(0));
            boolean tenNV = nv.getTENNV().contains(data_filter.get(0));
            boolean sdt = nv.getSDT().equals(data_filter.get(0));
            boolean isCheck = false;

            if (data_filter.get(0).equals("") && data_filter.get(1).equals("Tất cả")) {
                isCheck = true;
            } else if (data_filter.get(1).equals("Đang làm việc")) {
                if (nv.getTRANGTHAi() == 1) {
                    re.add(nv);
                }
            } else if (data_filter.get(1).equals("Đã nghỉ việc")) {
                if (nv.getTRANGTHAi() == 0) {
                    re.add(nv);
                }
            } else {
                isCheck = maNV || tenNV || sdt;
            }

            if (isCheck) {
                re.add(nv);
            }

        }
        return re;
    }

    public static void main(String[] args) throws SQLException {
        nhanVienBUS bus = new nhanVienBUS();
//        ArrayList<String> data_filter1 = new ArrayList<>();
//        data_filter1.add("t"); // Empty search query
//        data_filter1.add("Tất cả");
//        ArrayList<nhanVienDTO> ds = bus.search(data_filter1);
//        for (nhanVienDTO c : ds) {
//            System.out.println(c.getTENNV() + " " + c.getCHUCVU() + "  " + c.getEMAIL());
//        }
        bus.xoaInSQL("AD1");
    }
}
