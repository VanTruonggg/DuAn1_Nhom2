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

/**
 * KichThuoc
 */
public class KichThuoc implements interface_thuoctinh{
    private String idKichThuoc;
    private int size;
    private int trangThai;

    public KichThuoc() {
    }

    public KichThuoc(String idKichThuoc, int size, int trangThai) {
        this.idKichThuoc = idKichThuoc;
        this.size = size;
        this.trangThai = trangThai;
    }
    
    public void setIdThuocTinh(String idThuocTinh) {
        this.idKichThuoc = idThuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        this.size = Integer.parseInt(thuocTinh);
    }

    public String getIdKichThuoc() {
        return idKichThuoc;
    }

    public void setIdKichThuoc(String idKichThuoc) {
        this.idKichThuoc = idKichThuoc;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String getPrefix() {
        return "KT";
    }

    @Override
    public String getIdThuocTinh() {
        return idKichThuoc;
    }

    @Override
    public String getThuocTinh() {
        return size + "";
    }

}