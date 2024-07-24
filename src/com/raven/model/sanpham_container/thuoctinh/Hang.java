/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.sanpham_container.thuoctinh;

import interfaces.interface_thuoctinh;

/**
 *
 * @author Ca1
 */
public class Hang implements interface_thuoctinh {
    private String idHang, tenHang;
    private int trangThai;

    public Hang(String idHang, String tenHang, int trangThai) {
        this.idHang = idHang;
        this.tenHang = tenHang;
        this.trangThai = trangThai;
    }

    public Hang() {
    }
    
    public void setIdThuocTinh(String idThuocTinh) {
        this.idHang = idThuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        this.tenHang = thuocTinh;
    }

    public String getIdHang() {
        return idHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setIdHang(String idHang) {
        this.idHang = idHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String getPrefix() {
        return "H";
    }

    @Override
    public String getIdThuocTinh() {
        return idHang;
    }

    @Override
    public String getThuocTinh() {
        return tenHang;
    }

}
