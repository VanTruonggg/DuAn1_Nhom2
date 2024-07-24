/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.khachhang_container;

import java.util.Date;

/**
 *
 * @author DELL-LAPTOP
 */
public class LichSuMuaHang {
    private int id;
    private String tenKH;
    private String maHD;
    private String SDT;
    private Date NgayMH;
    private Date ngayTT;
    private double tongTien;
    

    public LichSuMuaHang() {
    }

    public LichSuMuaHang(int id, String tenKH, String maHD, String SDT, Date NgayMH, Date ngayTT, double tongTien) {
        this.id = id;
        this.tenKH = tenKH;
        this.maHD = maHD;
        this.SDT = SDT;
        this.NgayMH = NgayMH;
        this.ngayTT = ngayTT;
        this.tongTien = tongTien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public Date getNgayMH() {
        return NgayMH;
    }

    public void setNgayMH(Date NgayMH) {
        this.NgayMH = NgayMH;
    }

    public Date getNgayTT() {
        return ngayTT;
    }

    public void setNgayTT(Date ngayTT) {
        this.ngayTT = ngayTT;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
    
    
    public Object[] toRowTable2(){
        return new Object[]{
              maHD, NgayMH, ngayTT, tongTien
        };
    }
}
