/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository;

import com.raven.config.DBConnect;
import com.raven.response.LichSuHoaDonResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class LichSuHoaDonRepository {
    public ArrayList<LichSuHoaDonResponse> getAll(Integer hoaDonID) {
        String sql = """
                     SELECT dbo.LichSuHoaDon.Id, dbo.HoaDon.Id AS Expr1, dbo.NhanVien.Id AS Expr2, dbo.LichSuHoaDon.Ma, dbo.NhanVien.MaNhanVien, dbo.LichSuHoaDon.HanhDongNguoiThaoTac, dbo.LichSuHoaDon.LyDoHuy, dbo.LichSuHoaDon.NgayCapNhat, 
                                       dbo.LichSuHoaDon.GhiChu, dbo.LichSuHoaDon.TrangThai
                     FROM     dbo.HoaDon INNER JOIN
                                       dbo.LichSuHoaDon ON dbo.HoaDon.Id = dbo.LichSuHoaDon.IdHD INNER JOIN
                                       dbo.NhanVien ON dbo.HoaDon.IdNV = dbo.NhanVien.Id
                     WHERE dbo.HoaDon.Id = ?
                     """;
        ArrayList<LichSuHoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hoaDonID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LichSuHoaDonResponse response = LichSuHoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .idHoaDon(rs.getInt(2))
                        .idNV(rs.getInt(3))
                        .ma(rs.getString(4))
                        .maNV(rs.getString(5))
                        .hanhDongNguoiThaoTac(rs.getString(6))
                        .lyDoHuy(rs.getString(7))
                        .ngayCapNhat(rs.getDate(8))
                        .ghiChu(rs.getString(9))
                        .trangThai(rs.getInt(10))
                        .build();
                lists.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out); // nem loi khi xay ra 
        }
        return lists;
    }
}
