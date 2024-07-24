/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.khachhang_container;

/**
 *
 * @author DELL-LAPTOP
 */
public class KhachHang {
    private int id;
    private String maKH;
    private String hoTen;
    private boolean gioiTinh;
    private String SDT;
    private String diaChi;

    public KhachHang() {
    }

    public KhachHang(int id, String maKH, String hoTen, boolean gioiTinh, String SDT, String diaChi) {
        this.id = id;
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.SDT = SDT;
        this.diaChi = diaChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    
    public Object[] toRowTable(){
        return new Object[]{
            id, maKH, hoTen, SDT, gioiTinh ? "Nữ" : "Nam", diaChi
        };
    }
    
    
    
}
