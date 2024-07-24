package models.sanpham_container.thuoctinh;

import interfaces.interface_thuoctinh;

public class DeGiay implements interface_thuoctinh{
    private String idDeGiay, tenDeGiay;
    private int trangThai;

    public DeGiay(String idDeGiay, String tenDeGiay, int trangThai) {
        this.idDeGiay = idDeGiay;
        this.tenDeGiay = tenDeGiay;
        this.trangThai = trangThai;
    }

    public DeGiay() {
    }
    
    public void setIdThuocTinh(String idThuocTinh) {
        this.idDeGiay = idThuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        this.tenDeGiay = thuocTinh;
    }

    public String getIdDeGiay() {
        return idDeGiay;
    }

    public void setIdDeGiay(String idDeGiay) {
        this.idDeGiay = idDeGiay;
    }

    public String getTenDeGiay() {
        return tenDeGiay;
    }

    public void setTenDeGiay(String tenDeGiay) {
        this.tenDeGiay = tenDeGiay;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String getPrefix() {
        return "DG";
    }

    @Override
    public String getIdThuocTinh() {
        return idDeGiay;
    }

    @Override
    public String getThuocTinh() {
        return tenDeGiay;
    }

}
