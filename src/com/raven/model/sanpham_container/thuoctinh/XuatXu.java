package models.sanpham_container.thuoctinh;

import interfaces.interface_thuoctinh;

public class XuatXu implements interface_thuoctinh{
    private String idXuatXu, noiXuatXu;
    private int trangThai;

    public XuatXu(String idXuatXu, String noiXuatXu, int trangThai) {
        this.idXuatXu = idXuatXu;
        this.noiXuatXu = noiXuatXu;
        this.trangThai = trangThai;
    }

    public XuatXu() {
    }
    
    public void setIdThuocTinh(String idThuocTinh) {
        this.idXuatXu = idThuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        this.noiXuatXu = thuocTinh;
    }

    public String getIdXuatXu() {
        return idXuatXu;
    }

    public void setIdXuatXu(String idXuatXu) {
        this.idXuatXu = idXuatXu;
    }

    public String getNoiXuatXu() {
        return noiXuatXu;
    }

    public void setNoiXuatXu(String noiXuatXu) {
        this.noiXuatXu = noiXuatXu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String getPrefix() {
        return "XX";
    }

    @Override
    public String getIdThuocTinh() {
        return idXuatXu;
    }

    @Override
    public String getThuocTinh() {
        return noiXuatXu;
    }

}
