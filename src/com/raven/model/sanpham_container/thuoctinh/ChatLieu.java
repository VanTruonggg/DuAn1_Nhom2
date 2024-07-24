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
public class ChatLieu implements interface_thuoctinh{
    private String idChatLieu, loai;
    private int trangThai;

    public ChatLieu(String idChatLieu, String loai, int trangThai) {
        this.idChatLieu = idChatLieu;
        this.loai = loai;
        this.trangThai = trangThai;
    }

    public ChatLieu() {
    }
    
    public void setIdThuocTinh(String idThuocTinh) {
        this.idChatLieu = idThuocTinh;
    }

    public void setThuocTinh(String thuoctinh) {
        this.loai = thuoctinh;
    }

    public String getIdChatLieu() {
        return idChatLieu;
    }

    public void setIdChatLieu(String idChatLieu) {
        this.idChatLieu = idChatLieu;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String getPrefix() {
        return "CL";
    }

    @Override
    public String getIdThuocTinh() {
        return this.idChatLieu;
    }

    @Override
    public String getThuocTinh() {
        return loai;
    }

}
