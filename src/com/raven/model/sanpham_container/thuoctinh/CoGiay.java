package models.sanpham_container.thuoctinh;

import interfaces.interface_thuoctinh;

public class CoGiay implements interface_thuoctinh{
    private String idCoGiay, tenCoGiay;
    private int trangThai;

    public CoGiay(String idCoGiay, String tenCoGiay, int trangThai) {
        this.idCoGiay = idCoGiay;
        this.tenCoGiay = tenCoGiay;
        this.trangThai = trangThai;
    }

    public CoGiay() {
    }
    
    public void setIdThuocTinh(String idThuocTinh) {
        this.idCoGiay = idThuocTinh;
    }

    public void setThuocTinh(String thuoctinh) {
        this.tenCoGiay = thuoctinh;
    }

    public String getIdCoGiay() {
        return idCoGiay;
    }

    public void setIdCoGiay(String idCoGiay) {
        this.idCoGiay = idCoGiay;
    }

    public String getTenCoGiay() {
        return tenCoGiay;
    }

    public void setTenCoGiay(String tenCoGiay) {
        this.tenCoGiay = tenCoGiay;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String getPrefix() {
        return "CG";
    }

    @Override
    public String getIdThuocTinh() {
        return idCoGiay;
    }

    @Override
    public String getThuocTinh() {
        return tenCoGiay;
    }

}
