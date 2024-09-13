/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

public class nhanVienDTO {

    private String MANV, TENNV, CHUCVU, DIACHI, EMAIL, SDT;
    private int TRANGTHAi;

    public nhanVienDTO(String MANV, String TENNV, String CHUCVU, String SDT, String DIACHI, String EMAIL, int TRANGTHAi) {
        this.MANV = MANV;
        this.TENNV = TENNV;
        this.CHUCVU = CHUCVU;
        this.DIACHI = DIACHI;
        this.EMAIL = EMAIL;
        this.SDT = SDT;
        this.TRANGTHAi = TRANGTHAi;
    }

    public nhanVienDTO(String TENNV, String CHUCVU, String SDT, String DIACHI, String EMAIL, int TRANGTHAi) {
        this.TENNV = TENNV;
        this.CHUCVU = CHUCVU;
        this.SDT = SDT;
        this.DIACHI = DIACHI;
        this.EMAIL = EMAIL;
        this.TRANGTHAi = TRANGTHAi;
    }

    public String getMANV() {
        return MANV;
    }

    public String getTENNV() {
        return TENNV;
    }

    public String getCHUCVU() {
        return CHUCVU;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public int getTRANGTHAi() {
        return TRANGTHAi;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    }

    public void setTENNV(String TENNV) {
        this.TENNV = TENNV;
    }

    public void setCHUCVU(String CHUCVU) {
        this.CHUCVU = CHUCVU;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public void setTRANGTHAi(int TRANGTHAi) {
        this.TRANGTHAi = TRANGTHAi;
    }

}
