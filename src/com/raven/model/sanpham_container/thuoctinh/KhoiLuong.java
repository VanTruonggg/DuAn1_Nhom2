package models.sanpham_container.thuoctinh;

import interfaces.interface_thuoctinh;

public class KhoiLuong implements interface_thuoctinh{
    private String idKhoiLuong;
    private String khoiLuong;
    private int trangThai;

    public KhoiLuong(String idKhoiLuong, String khoiLuong, int trangThai) {
        this.idKhoiLuong = idKhoiLuong;
        this.khoiLuong = khoiLuong;
        this.trangThai = trangThai;
    }

    public KhoiLuong() {
    }
    
    public void setIdThuocTinh(String idThuocTinh) {
        this.idKhoiLuong = idThuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        this.khoiLuong = thuocTinh;
    }

    public String getIdKhoiLuong() {
        return idKhoiLuong;
    }

    public void setIdKhoiLuong(String idKhoiLuong) {
        this.idKhoiLuong = idKhoiLuong;
    }

    public String getTenKhoiLuong() {
        return khoiLuong;
    }

    public void setTenKhoiLuong(String khoiLuong) {
        this.khoiLuong = khoiLuong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String getPrefix() {
        return "KL";
    }

    @Override
    public String getIdThuocTinh() {
        return idKhoiLuong;
    }

    @Override
    public String getThuocTinh() {
        return khoiLuong;
    }

}
