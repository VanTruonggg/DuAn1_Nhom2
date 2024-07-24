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
public class MauSac implements interface_thuoctinh{
    private String idMauSac, tenMauSac;
    private int trangThai;

    public MauSac(String idMauSac, String tenMauSac, int trangThai) {
        this.idMauSac = idMauSac;
        this.tenMauSac = tenMauSac;
        this.trangThai = trangThai;
    }

    public MauSac() {
    }
    
    public void setIdThuocTinh(String idThuocTinh) {
        this.idMauSac = idThuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        this.tenMauSac = thuocTinh;
    }

    public String getIdMauSac() {
        return idMauSac;
    }

    public void setIdMauSac(String idMauSac) {
        this.idMauSac = idMauSac;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String getPrefix() {
        return "MS";
    }

    @Override
    public String getIdThuocTinh() {
        return idMauSac;
    }

    @Override
    public String getThuocTinh() {
        return tenMauSac;
    }

}
