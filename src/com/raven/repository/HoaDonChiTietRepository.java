/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository;

import com.raven.config.DBConnect;
import com.raven.response.HoaDonChiTietResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class HoaDonChiTietRepository {
    public ArrayList<HoaDonChiTietResponse> getAll(Integer hoaDonID) {
        String sql = """
                     SELECT dbo.HoaDon.MaHoaDon, dbo.SanPham.TenSanPham, dbo.ChiTietSanPham.SoLuong, dbo.ChiTietSanPham.DonGia, 
                                                 dbo.HoaDonChiTiet.SoLuong * dbo.HoaDonChiTiet.Gia, dbo.ChatLieu.Loai, dbo.KichThuoc.Size, dbo.MauSac.TenMau, dbo.DeGiay.TenDeGiay, dbo.NSX.TenNSX, 
                                       dbo.Hang.TenHang, dbo.CoGiay.TenCoGiay, dbo.KhoiLuong.TenKhoiLuong, dbo.XuatXu.NoiXuatXu, dbo.ChiTietSanPham.GhiChu, dbo.HoaDonChiTiet.Id, dbo.SanPham.MaSP, dbo.ChiTietSanPham.MaCTSP, 
                                       dbo.ChiTietSanPham.Id AS Expr1
                     FROM     dbo.ChatLieu INNER JOIN
                                       dbo.ChiTietSanPham ON dbo.ChatLieu.Id = dbo.ChiTietSanPham.IdChatLieu INNER JOIN
                                       dbo.CoGiay ON dbo.ChiTietSanPham.IdCoGiay = dbo.CoGiay.Id INNER JOIN
                                       dbo.DeGiay ON dbo.ChiTietSanPham.IdDeGiay = dbo.DeGiay.Id INNER JOIN
                                       dbo.Hang ON dbo.ChiTietSanPham.IdHang = dbo.Hang.Id INNER JOIN
                                       dbo.HoaDon ON dbo.ChatLieu.Id = dbo.HoaDon.Id INNER JOIN
                                       dbo.HoaDonChiTiet ON dbo.ChiTietSanPham.Id = dbo.HoaDonChiTiet.IdSPCT AND dbo.HoaDon.Id = dbo.HoaDonChiTiet.IdHD INNER JOIN
                                       dbo.KhoiLuong ON dbo.ChiTietSanPham.IdKhoiLuong = dbo.KhoiLuong.Id INNER JOIN
                                       dbo.KichThuoc ON dbo.ChiTietSanPham.IdKichThuoc = dbo.KichThuoc.Id INNER JOIN
                                       dbo.MauSac ON dbo.ChiTietSanPham.IdMauSac = dbo.MauSac.Id INNER JOIN
                                       dbo.NSX ON dbo.ChiTietSanPham.IdNSX = dbo.NSX.Id INNER JOIN
                                       dbo.SanPham ON dbo.ChiTietSanPham.IdSP = dbo.SanPham.Id INNER JOIN
                                       dbo.XuatXu ON dbo.ChiTietSanPham.IdXuatXu = dbo.XuatXu.Id
                     WHERE dbo.HoaDon.Id = ?
                     """;
        ArrayList<HoaDonChiTietResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hoaDonID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTietResponse response = HoaDonChiTietResponse.builder()
                        .maHd(rs.getString(1))
                        .tenSP(rs.getString(2))
                        .soLuong(rs.getInt(3))
                        .donGia(rs.getDouble(4))
                        .thanhTien(rs.getDouble(5))
                        .chatLieu(rs.getString(6))
                        .kichThuoc(rs.getInt(7))
                        .MauSac(rs.getString(8))
                        .deGiay(rs.getString(9))
                        .NSX(rs.getString(10))
                        .hang(rs.getString(11))
                        .coGiay(rs.getString(12))
                        .khoiLuong(rs.getString(13))
                        .xuatXu(rs.getString(14))
                        .ghiChu(rs.getString(15))
                        .id(rs.getInt(16))
                        .maSP(rs.getString(17))
                        .maCtsp(rs.getString(18))
                        .ctspID(rs.getInt(19))
                        .build();
                lists.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
}
