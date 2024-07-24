/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository;

import com.raven.config.DBConnect;
import com.raven.response.HoaDonResponse;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HoaDonRepository {

    public ArrayList<HoaDonResponse> getAll() {
        String sql = """
                   SELECT dbo.HoaDon.Id, 
                     dbo.HoaDon.MaHoaDon, 
                     dbo.HoaDon.NgayTao, 
                     dbo.HoaDon.NgayThanhToan, 
                     dbo.HoaDon.TongTien, 
                     dbo.HoaDon.TongTienKhiGiam, 
                     dbo.HoaDon.TrangThai, 
                     dbo.HinhThucThanhToan.TenHinhThuc, 
                    dbo.KhachHang.Id AS Expr1,
                     dbo.KhachHang.MaKH,
                     dbo.KhachHang.HoTen, 
                     dbo.KhachHang.DiaChi AS Expr3, 
                     dbo.LichSuHoaDon.Id AS Expr5, 
                     dbo.NhanVien.Id AS Expr4, 
                     dbo.NhanVien.MaNhanVien, 
                     dbo.PhieuGiamGia.Id AS Expr6, 
                    dbo.HinhThucThanhToan.Id AS Expr7,
                     dbo.KhachHang.Sdt AS Expr2, 
                     dbo.NhanVien.HoTen AS Expr8
                   FROM     dbo.HinhThucThanhToan INNER JOIN
                                     dbo.HoaDon ON dbo.HinhThucThanhToan.Id = dbo.HoaDon.IdHTTT INNER JOIN
                                     dbo.KhachHang ON dbo.HoaDon.IdKH = dbo.KhachHang.Id INNER JOIN
                                     dbo.LichSuHoaDon ON dbo.HoaDon.Id = dbo.LichSuHoaDon.IdHD INNER JOIN
                                     dbo.NhanVien ON dbo.HoaDon.IdNV = dbo.NhanVien.Id INNER JOIN
                                     dbo.PhieuGiamGia ON dbo.HoaDon.IdPGG = dbo.PhieuGiamGia.Id
                     """;
        ArrayList<HoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonResponse response = HoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .maHD(rs.getString(2))
                        .ngayTao(rs.getDate(3))
                        .NgayThanhToan(rs.getDate(4))
                        .tongTien(rs.getDouble(5))
                        .tongTienKhiGiam(rs.getDouble(6))
                        .trangThaiHD(rs.getInt(7))
                        .tenHTTT(rs.getInt(8))
                        .idKH(rs.getInt(9))
                        .maKH(rs.getString(10))
                        .tenKH(rs.getString(11))
                        .diaChiKH(rs.getString(12))
                        .idLSHD(rs.getInt(13))
                        .idNV(rs.getInt(14))
                        .maNV(rs.getString(15))
                        .idPGG(rs.getInt(16))
                        .idHTTT(rs.getInt(17))
                        .sdtKH(rs.getString(18))
                        .tenNV(rs.getString(19))
                        .build();
                lists.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }

//    public ArrayList<HoaDonResponse> getAllByStatus() {
//        String sql = """
//                                SELECT dbo.HoaDon.Id, 
//                                        dbo.HoaDon.MaHoaDon, 
//                                        dbo.HoaDon.NgayTao, 
//                                        dbo.HoaDon.NgayThanhToan, 
//                                        dbo.HoaDon.TongTien, 
//                                        dbo.HoaDon.TongTienKhiGiam, 
//                                        dbo.HoaDon.TrangThai, 
//                                        dbo.HinhThucThanhToan.TenHinhThuc, 
//                                       dbo.KhachHang.Id AS Expr1,
//                                        dbo.KhachHang.MaKH,
//                                        dbo.KhachHang.HoTen, 
//                                        dbo.KhachHang.DiaChi AS Expr3, 
//                                        dbo.LichSuHoaDon.Id AS Expr5, 
//                                        dbo.NhanVien.Id AS Expr4, 
//                                        dbo.NhanVien.MaNhanVien, 
//                                        dbo.PhieuGiamGia.Id AS Expr6, 
//                                       dbo.HinhThucThanhToan.Id AS Expr7,
//                                        dbo.KhachHang.Sdt AS Expr2, 
//                                        dbo.NhanVien.HoTen AS Expr8
//                                      FROM     dbo.HinhThucThanhToan INNER JOIN
//                                                        dbo.HoaDon ON dbo.HinhThucThanhToan.Id = dbo.HoaDon.IdHTTT INNER JOIN
//                                                        dbo.KhachHang ON dbo.HoaDon.IdKH = dbo.KhachHang.Id INNER JOIN
//                                                        dbo.LichSuHoaDon ON dbo.HoaDon.Id = dbo.LichSuHoaDon.IdHD INNER JOIN
//                                                        dbo.NhanVien ON dbo.HoaDon.IdNV = dbo.NhanVien.Id INNER JOIN
//                                                        dbo.PhieuGiamGia ON dbo.HoaDon.IdPGG = dbo.PhieuGiamGia.Id
//                     WHERE dbo.HoaDon.TrangThai = 0
//                     """;
//        ArrayList<HoaDonResponse> lists = new ArrayList<>();
//        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                HoaDonResponse response = HoaDonResponse.builder()
//                        .id(rs.getInt(1))
//                        .maHD(rs.getString(2))
//                        .ngayTao(rs.getDate(3))
//                        .NgayThanhToan(rs.getDate(4))
//                        .tongTien(rs.getDouble(5))
//                        .tongTienKhiGiam(rs.getDouble(6))
//                        .trangThaiHD(rs.getInt(7))
//                        .tenHTTT(rs.getInt(8))
//                        .idKH(rs.getInt(9))
//                        .maKH(rs.getString(10))
//                        .tenKH(rs.getString(11))
//                        .diaChiKH(rs.getString(12))
//                        .idLSHD(rs.getInt(13))
//                        .idNV(rs.getInt(14))
//                        .maNV(rs.getString(15))
//                        .idPGG(rs.getInt(16))
//                        .idHTTT(rs.getInt(17))
//                        .sdtKH(rs.getString(18))
//                        .tenNV(rs.getString(19))
//                        .build();
//                lists.add(response);
//            }
//        } catch (Exception e) {
//            e.printStackTrace(System.out); // nem loi khi xay ra 
//        }
//        return lists;
//    }
    // keyword : input => search theo nhieu truong (tim kiem theo moi truong tren table..)
    // keyword: ma hoa don, ma nv, ten nv, ten kh, ma kh,sdt......
    public ArrayList<HoaDonResponse> search(String keyword) {
        String sql = """
                   SELECT dbo.HoaDon.Id, 
                          dbo.HoaDon.MaHoaDon, 
                          dbo.HoaDon.NgayTao, 
                          dbo.HoaDon.NgayThanhToan, 
                           dbo.HoaDon.TongTien, 
                           dbo.HoaDon.TongTienKhiGiam, 
                           dbo.HoaDon.TrangThai, 
                           dbo.HinhThucThanhToan.TenHinhThuc, 
                           dbo.KhachHang.Id AS Expr1,
                           dbo.KhachHang.MaKH,
                           dbo.KhachHang.HoTen, 
                           dbo.KhachHang.DiaChi AS Expr3, 
                           dbo.LichSuHoaDon.Id AS Expr5, 
                           dbo.NhanVien.Id AS Expr4, 
                           dbo.NhanVien.MaNhanVien, 
                           dbo.PhieuGiamGia.Id AS Expr6, 
                           dbo.HinhThucThanhToan.Id AS Expr7,
                           dbo.KhachHang.Sdt AS Expr2, 
                           dbo.NhanVien.HoTen AS Expr8
                           FROM     dbo.HinhThucThanhToan INNER JOIN
                                    dbo.HoaDon ON dbo.HinhThucThanhToan.Id = dbo.HoaDon.IdHTTT INNER JOIN
                                    dbo.KhachHang ON dbo.HoaDon.IdKH = dbo.KhachHang.Id INNER JOIN
                                    dbo.LichSuHoaDon ON dbo.HoaDon.Id = dbo.LichSuHoaDon.IdHD INNER JOIN
                                    dbo.NhanVien ON dbo.HoaDon.IdNV = dbo.NhanVien.Id INNER JOIN
                                    dbo.PhieuGiamGia ON dbo.HoaDon.IdPGG = dbo.PhieuGiamGia.Id
                    """;
        // check neu keyword k nhap gi => k can them gi ca 
        // nhap => moi can cong them vao sql 
        if (keyword.length() > 0) { // isempty
            sql += """
                  AND 
                  	(dbo.HoaDon.MaHoaDon LIKE ?
                        OR 
                        dbo.NhanVien.MaNhanVien LIKE ?
                        OR 
                        dbo.KhachHang.HoTen LIKE ?
                        OR 
                        dbo.KhachHang.DiaChi LIKE ?
                        OR 
                        dbo.KhachHang.Sdt LIKE ? 
                  	)
                  """;
        }
//        -- ma hoa don, ma nv, ten nv, ma kh, ten kh, sdt
//        -- DK 1 DK2 DK 3 DK4 
//        -- AND => AND 4 DK
//        -- 09
        // thuoc tinh IS NULL 
        ArrayList<HoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int index = 1; // Vi tri cua dau hoi cham dau tien 
            if (keyword.length() > 0) {
                String value = "%" + keyword + "%";
                // search 1 o input nhieu truong
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonResponse response = HoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .maHD(rs.getString(2))
                        .ngayTao(rs.getDate(3))
                        .NgayThanhToan(rs.getDate(4))
                        .tongTien(rs.getDouble(5))
                        .tongTienKhiGiam(rs.getDouble(6))
                        .trangThaiHD(rs.getInt(7))
                        .tenHTTT(rs.getInt(8))
                        .idKH(rs.getInt(9))
                        .maKH(rs.getString(10))
                        .tenKH(rs.getString(11))
                        .diaChiKH(rs.getString(12))
                        .idLSHD(rs.getInt(13))
                        .idNV(rs.getInt(14))
                        .maNV(rs.getString(15))
                        .idPGG(rs.getInt(16))
                        .idHTTT(rs.getInt(17))
                        .sdtKH(rs.getString(18))
                        .tenNV(rs.getString(19))
                        .build();
                lists.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }

    public ArrayList<HoaDonResponse> loc(Integer trangThai, Integer hinhThuc, Double giaMin, Double giaMax) {
        String sql = """
                   SELECT dbo.HoaDon.Id, 
                          dbo.HoaDon.MaHoaDon, 
                          dbo.HoaDon.NgayTao, 
                          dbo.HoaDon.NgayThanhToan, 
                           dbo.HoaDon.TongTien,
                           dbo.HoaDon.TongTienKhiGiam, 
                           dbo.HoaDon.TrangThai, 
                           dbo.HinhThucThanhToan.TenHinhThuc, 
                           dbo.KhachHang.Id AS Expr1,
                           dbo.KhachHang.MaKH,
                           dbo.KhachHang.HoTen, 
                           dbo.KhachHang.DiaChi AS Expr3, 
                           dbo.LichSuHoaDon.Id AS Expr5, 
                           dbo.NhanVien.Id AS Expr4, 
                           dbo.NhanVien.MaNhanVien, 
                           dbo.PhieuGiamGia.Id AS Expr6, 
                           dbo.HinhThucThanhToan.Id AS Expr7,
                           dbo.KhachHang.Sdt AS Expr2, 
                           dbo.NhanVien.HoTen AS Expr8
                           FROM     dbo.HinhThucThanhToan INNER JOIN
                                    dbo.HoaDon ON dbo.HinhThucThanhToan.Id = dbo.HoaDon.IdHTTT INNER JOIN
                                    dbo.KhachHang ON dbo.HoaDon.IdKH = dbo.KhachHang.Id INNER JOIN
                                    dbo.LichSuHoaDon ON dbo.HoaDon.Id = dbo.LichSuHoaDon.IdHD INNER JOIN
                                    dbo.NhanVien ON dbo.HoaDon.IdNV = dbo.NhanVien.Id INNER JOIN
                                    dbo.PhieuGiamGia ON dbo.HoaDon.IdPGG = dbo.PhieuGiamGia.Id
                     where HoaDon.TrangThai = ?
                        
                     OR HinhThucThanhToan.TenHinhThuc = ?
                     OR (HoaDon.TongTien >= ? AND HoaDon.TongTien <= ? OR (HoaDon.TongTien >= 0 AND HoaDon.TongTien <= 1000000000))
                        
                    """;
//         ((? = 0.0 AND ? = 0.0) OR (hd.tong_tien >= ? AND hd.tong_tien <= ?))
        ArrayList<HoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

//            giaMin = 0.0;
//            giaMax = 0.0;
            int index = 1; // Vi tri cua dau hoi cham dau tien 
            ps.setObject(index++, trangThai);
            ps.setObject(index++, hinhThuc);
            ps.setObject(index++, giaMin);
            ps.setObject(index++, giaMax);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonResponse response = HoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .maHD(rs.getString(2))
                        .ngayTao(rs.getDate(3))
                        .NgayThanhToan(rs.getDate(4))
                        .tongTien(rs.getDouble(5))
                        .tongTienKhiGiam(rs.getDouble(6))
                        .trangThaiHD(rs.getInt(7))
                        .tenHTTT(rs.getInt(8))
                        .idKH(rs.getInt(9))
                        .maKH(rs.getString(10))
                        .tenKH(rs.getString(11))
                        .diaChiKH(rs.getString(12))
                        .idLSHD(rs.getInt(13))
                        .idNV(rs.getInt(14))
                        .maNV(rs.getString(15))
                        .idPGG(rs.getInt(16))
                        .idHTTT(rs.getInt(17))
                        .sdtKH(rs.getString(18))
                        .tenNV(rs.getString(19))
                        .build();
                lists.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 1

    public ArrayList<HoaDonResponse> getAllAndPaging(int page, int limit) {
        String sql = """
                   SELECT dbo.HoaDon.Id, 
                     dbo.HoaDon.MaHoaDon, 
                     dbo.HoaDon.NgayTao, 
                     dbo.HoaDon.NgayThanhToan, 
                     dbo.HoaDon.TongTien, 
                     dbo.HoaDon.TongTienKhiGiam, 
                     dbo.HoaDon.TrangThai, 
                     dbo.HinhThucThanhToan.TenHinhThuc, 
                    dbo.KhachHang.Id AS Expr1,
                     dbo.KhachHang.MaKH,
                     dbo.KhachHang.HoTen, 
                     dbo.KhachHang.DiaChi AS Expr3, 
                     dbo.LichSuHoaDon.Id AS Expr5, 
                     dbo.NhanVien.Id AS Expr4, 
                     dbo.NhanVien.MaNhanVien, 
                     dbo.PhieuGiamGia.Id AS Expr6, 
                    dbo.HinhThucThanhToan.Id AS Expr7,
                     dbo.KhachHang.Sdt AS Expr2, 
                     dbo.NhanVien.HoTen AS Expr8
                   FROM     dbo.HinhThucThanhToan INNER JOIN
                                     dbo.HoaDon ON dbo.HinhThucThanhToan.Id = dbo.HoaDon.IdHTTT INNER JOIN
                                     dbo.KhachHang ON dbo.HoaDon.IdKH = dbo.KhachHang.Id INNER JOIN
                                     dbo.LichSuHoaDon ON dbo.HoaDon.Id = dbo.LichSuHoaDon.IdHD INNER JOIN
                                     dbo.NhanVien ON dbo.HoaDon.IdNV = dbo.NhanVien.Id INNER JOIN
                                     dbo.PhieuGiamGia ON dbo.HoaDon.IdPGG = dbo.PhieuGiamGia.Id
                    ORDER BY HoaDon.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY 
                     """;
        ArrayList<HoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int index = 1; // Vi tri cua dau hoi cham dau tien 
            int offset = ((page - 1) * limit);
            ps.setObject(index++, offset);
            ps.setObject(index++, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonResponse response = HoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .maHD(rs.getString(2))
                        .ngayTao(rs.getDate(3))
                        .NgayThanhToan(rs.getDate(4))
                        .tongTien(rs.getDouble(5))
                        .tongTienKhiGiam(rs.getDouble(6))
                        .trangThaiHD(rs.getInt(7))
                        .tenHTTT(rs.getInt(8))
                        .idKH(rs.getInt(9))
                        .maKH(rs.getString(10))
                        .tenKH(rs.getString(11))
                        .diaChiKH(rs.getString(12))
                        .idLSHD(rs.getInt(13))
                        .idNV(rs.getInt(14))
                        .maNV(rs.getString(15))
                        .idPGG(rs.getInt(16))
                        .idHTTT(rs.getInt(17))
                        .sdtKH(rs.getString(18))
                        .tenNV(rs.getString(19))
                        .build();
                lists.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }

    public static int getTotalRowCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM HoaDon";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public ArrayList<HoaDonResponse> searchAndPaging(String keyword, int page, int limit) {
        String sql = """
                   SELECT dbo.HoaDon.Id, 
                          dbo.HoaDon.MaHoaDon, 
                          dbo.HoaDon.NgayTao, 
                          dbo.HoaDon.NgayThanhToan, 
                           dbo.HoaDon.TongTien, 
                           dbo.HoaDon.TongTienKhiGiam, 
                           dbo.HoaDon.TrangThai, 
                           dbo.HinhThucThanhToan.TenHinhThuc, 
                           dbo.KhachHang.Id AS Expr1,
                           dbo.KhachHang.MaKH,
                           dbo.KhachHang.HoTen, 
                           dbo.KhachHang.DiaChi AS Expr3, 
                           dbo.LichSuHoaDon.Id AS Expr5, 
                           dbo.NhanVien.Id AS Expr4, 
                           dbo.NhanVien.MaNhanVien, 
                           dbo.PhieuGiamGia.Id AS Expr6, 
                           dbo.HinhThucThanhToan.Id AS Expr7,
                           dbo.KhachHang.Sdt AS Expr2, 
                           dbo.NhanVien.HoTen AS Expr8
                           FROM     dbo.HinhThucThanhToan INNER JOIN
                                    dbo.HoaDon ON dbo.HinhThucThanhToan.Id = dbo.HoaDon.IdHTTT INNER JOIN
                                    dbo.KhachHang ON dbo.HoaDon.IdKH = dbo.KhachHang.Id INNER JOIN
                                    dbo.LichSuHoaDon ON dbo.HoaDon.Id = dbo.LichSuHoaDon.IdHD INNER JOIN
                                    dbo.NhanVien ON dbo.HoaDon.IdNV = dbo.NhanVien.Id INNER JOIN
                                    dbo.PhieuGiamGia ON dbo.HoaDon.IdPGG = dbo.PhieuGiamGia.Id
                    """;
        // check neu keyword k nhap gi => k can them gi ca 
        // nhap => moi can cong them vao sql 
        if (keyword.length() > 0) { // isempty
            sql += """
                  AND 
                  	(dbo.HoaDon.MaHoaDon LIKE ?
                        OR 
                        dbo.NhanVien.MaNhanVien LIKE ?
                        OR 
                        dbo.KhachHang.HoTen LIKE ?
                        OR 
                        dbo.KhachHang.DiaChi LIKE ?
                        OR 
                        dbo.KhachHang.Sdt LIKE ? 
                  	)
                  """;
        }

        sql += """
              ORDER BY HoaDon.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
              """;
//        -- ma hoa don, ma nv, ten nv, ma kh, ten kh, sdt
//        -- DK 1 DK2 DK 3 DK4 
//        -- AND => AND 4 DK
//        -- 09
        // thuoc tinh IS NULL 
        ArrayList<HoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int index = 1; // Vi tri cua dau hoi cham dau tien 
            int offset = ((page - 1) * limit);
            if (keyword.length() > 0) {
                String value = "%" + keyword + "%";
                // search 1 o input nhieu truong
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);
                ps.setObject(index++, value);

                ps.setObject(index++, offset);
                ps.setObject(index++, limit);
            } else {
                ps.setObject(index++, offset);
                ps.setObject(index++, limit);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonResponse response = HoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .maHD(rs.getString(2))
                        .ngayTao(rs.getDate(3))
                        .NgayThanhToan(rs.getDate(4))
                        .tongTien(rs.getDouble(5))
                        .tongTienKhiGiam(rs.getDouble(6))
                        .trangThaiHD(rs.getInt(7))
                        .tenHTTT(rs.getInt(8))
                        .idKH(rs.getInt(9))
                        .maKH(rs.getString(10))
                        .tenKH(rs.getString(11))
                        .diaChiKH(rs.getString(12))
                        .idLSHD(rs.getInt(13))
                        .idNV(rs.getInt(14))
                        .maNV(rs.getString(15))
                        .idPGG(rs.getInt(16))
                        .idHTTT(rs.getInt(17))
                        .sdtKH(rs.getString(18))
                        .tenNV(rs.getString(19))
                        .build();
                lists.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }

    public ArrayList<HoaDonResponse> locAndPaging(Integer trangThai, Integer hinhThuc, Double giaMin, Double giaMax, int page, int limit) {
        String sql = """
                   SELECT dbo.HoaDon.Id, 
                          dbo.HoaDon.MaHoaDon, 
                          dbo.HoaDon.NgayTao, 
                          dbo.HoaDon.NgayThanhToan, 
                           dbo.HoaDon.TongTien,
                           dbo.HoaDon.TongTienKhiGiam, 
                           dbo.HoaDon.TrangThai, 
                           dbo.HinhThucThanhToan.TenHinhThuc, 
                           dbo.KhachHang.Id AS Expr1,
                           dbo.KhachHang.MaKH,
                           dbo.KhachHang.HoTen, 
                           dbo.KhachHang.DiaChi AS Expr3, 
                           dbo.LichSuHoaDon.Id AS Expr5, 
                           dbo.NhanVien.Id AS Expr4, 
                           dbo.NhanVien.MaNhanVien, 
                           dbo.PhieuGiamGia.Id AS Expr6, 
                           dbo.HinhThucThanhToan.Id AS Expr7,
                           dbo.KhachHang.Sdt AS Expr2, 
                           dbo.NhanVien.HoTen AS Expr8
                           FROM     dbo.HinhThucThanhToan INNER JOIN
                                    dbo.HoaDon ON dbo.HinhThucThanhToan.Id = dbo.HoaDon.IdHTTT INNER JOIN
                                    dbo.KhachHang ON dbo.HoaDon.IdKH = dbo.KhachHang.Id INNER JOIN
                                    dbo.LichSuHoaDon ON dbo.HoaDon.Id = dbo.LichSuHoaDon.IdHD INNER JOIN
                                    dbo.NhanVien ON dbo.HoaDon.IdNV = dbo.NhanVien.Id INNER JOIN
                                    dbo.PhieuGiamGia ON dbo.HoaDon.IdPGG = dbo.PhieuGiamGia.Id
                     where HoaDon.TrangThai = ?
                        AND HinhThucThanhToan.TenHinhThuc = ?
                        AND HoaDon.TongTien >= ? AND HoaDon.TongTien <= ?
                     ORDER BY HoaDon.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                    """;
        ArrayList<HoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int index = 1; // Vi tri cua dau hoi cham dau tien 
            int offset = ((page - 1) * limit);
            ps.setObject(index++, trangThai);
            ps.setObject(index++, hinhThuc);
            ps.setObject(index++, giaMin);
            ps.setObject(index++, giaMax);
            ps.setObject(index++, offset);
            ps.setObject(index++, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonResponse response = HoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .maHD(rs.getString(2))
                        .ngayTao(rs.getDate(3))
                        .NgayThanhToan(rs.getDate(4))
                        .tongTien(rs.getDouble(5))
                        .tongTienKhiGiam(rs.getDouble(6))
                        .trangThaiHD(rs.getInt(7))
                        .tenHTTT(rs.getInt(8))
                        .idKH(rs.getInt(9))
                        .maKH(rs.getString(10))
                        .tenKH(rs.getString(11))
                        .diaChiKH(rs.getString(12))
                        .idLSHD(rs.getInt(13))
                        .idNV(rs.getInt(14))
                        .maNV(rs.getString(15))
                        .idPGG(rs.getInt(16))
                        .idHTTT(rs.getInt(17))
                        .sdtKH(rs.getString(18))
                        .tenNV(rs.getString(19))
                        .build();
                lists.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }

}
