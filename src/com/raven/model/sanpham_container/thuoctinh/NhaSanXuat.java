package models.sanpham_container.thuoctinh;

import interfaces.interface_thuoctinh;

public class NhaSanXuat implements interface_thuoctinh{
    private String maNSX, tenNSX;
    private int trangThai;

    public NhaSanXuat(String maNSX, String tenNSX, int trangThai) {
        this.maNSX = maNSX;
        this.tenNSX = tenNSX;
        this.trangThai = trangThai;
    }

    public NhaSanXuat() {
    }
    
    public void setIdThuocTinh(String idThuocTinh) {
        this.maNSX = idThuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        this.tenNSX = thuocTinh;
    }

    public String getMaNSX() {
        return maNSX;
    }

    public void setMaNSX(String maNSX) {
        this.maNSX = maNSX;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String getPrefix() {
        return "NSX";
    }

    @Override
    public String getIdThuocTinh() {
        return maNSX;
    }

    @Override
    public String getThuocTinh() {
        return tenNSX;
    }

}
