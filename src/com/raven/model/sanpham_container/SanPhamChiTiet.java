package models.sanpham_container;
import org.w3c.dom.css.Counter;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter

public class SanPhamChiTiet {
    
    private String idSPCT, sanPham, hang, chatLieu, khoiLuong, mauSac, deGiay, xuatXu, nhaSanXuat, coGiay,
            tenCTSP, ghiChu, urlHinhAnh;
    private String soLuongTon, KichThuoc;
    private String donGia;
    private int trangThai, index;
    private boolean isSelected = false;
    private String urlImgQrCode;
    

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
}