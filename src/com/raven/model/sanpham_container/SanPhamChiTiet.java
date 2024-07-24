package models.sanpham_container;

import org.w3c.dom.css.Counter;

public class SanPhamChiTiet {
    
    private String idSPCT, sanPham, hang, chatLieu, khoiLuong, mauSac, deGiay, xuatXu, nhaSanXuat, coGiay,
            tenCTSP, ghiChu, urlHinhAnh;
    private String soLuongTon, KichThuoc;
    private String donGia;
    private int trangThai, index;
    private boolean isSelected = false;

    public SanPhamChiTiet() {
    }

    public SanPhamChiTiet(String idSPCT, String sanPham, String hang, String chatLieu, String khoiLuong, String mauSac, String deGiay, String xuatXu, String nhaSanXuat, String coGiay, String tenCTSP, String ghiChu, String soLuongTon, String KichThuoc, String donGia, String urlHinhAnh, int trangThai) {
        this.idSPCT = idSPCT;
        this.sanPham = sanPham;
        this.hang = hang;
        this.chatLieu = chatLieu;
        this.khoiLuong = khoiLuong;
        this.mauSac = mauSac;
        this.deGiay = deGiay;
        this.xuatXu = xuatXu;
        this.nhaSanXuat = nhaSanXuat;
        this.coGiay = coGiay;
        this.tenCTSP = tenCTSP;
        this.ghiChu = ghiChu;
        this.soLuongTon = soLuongTon;
        this.KichThuoc = KichThuoc;
        this.donGia = donGia;
        this.trangThai = trangThai;
        this.urlHinhAnh = urlHinhAnh;
    }
  
    public String getIdSPCT() {
        return idSPCT;
    }

    public void setIdSPCT(String idSPCT) {
        this.idSPCT = idSPCT;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getKhoiLuong() {
        return khoiLuong;
    }

    public void setKhoiLuong(String khoiLuong) {
        this.khoiLuong = khoiLuong;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getDeGiay() {
        return deGiay;
    }

    public void setDeGiay(String deGiay) {
        this.deGiay = deGiay;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(String nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public String getCoGiay() {
        return coGiay;
    }

    public void setCoGiay(String coGiay) {
        this.coGiay = coGiay;
    }

    public String getTenCTSP() {
        return tenCTSP;
    }

    public void setTenCTSP(String tenCTSP) {
        this.tenCTSP = tenCTSP;
    }

    public String getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(String soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getKichThuoc() {
        return KichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        KichThuoc = kichThuoc;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getSanPham() {
        return sanPham;
    }

    public void setSanPham(String sanPham) {
        this.sanPham = sanPham;
    }

    public String getUrlHinhAnh() {
        return urlHinhAnh;
    }

    public void setUrlHinhAnh(String urlHinhAnh) {
        this.urlHinhAnh = urlHinhAnh;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}