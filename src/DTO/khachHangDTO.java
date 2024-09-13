/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

public class khachHangDTO {

    private int maKH;
    private String tenKH;
    private String soDienThoai;
    private int diem;
    private int trangThai;

    public khachHangDTO(int maKH, String tenKH, String soDienThoai, int diem, int trangThai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.diem = diem;
        this.trangThai = trangThai;
    }

    public khachHangDTO(String tenKH, String soDienThoai) {
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.diem = 0;
        this.trangThai = 1;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaKH() {
        return maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public int getDiem() {
        return diem;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    @Override
    public String toString() {
        return maKH + " " + tenKH + " " + soDienThoai + " " + diem;
    }
}
