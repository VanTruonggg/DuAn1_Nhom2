package com.raven.repository.khachhang;

import com.raven.config.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.khachhang_container.LichSuMuaHang;

/**
 *
 * @author DELL-LAPTOP
 */
public class LichSuMuaHangRepo {
    List<LichSuMuaHang> list = new ArrayList<>();

    public List<LichSuMuaHang> getData(){
        String query ="SELECT HoaDon.MaHoaDon,KhachHang.HoTen,KhachHang.Sdt,HoaDon.NgayTao, HoaDon.NgayThanhToan, HoaDon.TongTien FROM KhachHang JOIN HoaDon ON KhachHang.Id = HoaDon.IdKH";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LichSuMuaHang kh = new LichSuMuaHang();
         
                kh.setMaHD(rs.getString(1));
                kh.setTenKH(rs.getString(2));
                kh.setSDT(rs.getString(3));
                kh.setNgayMH(rs.getDate(4));
                kh.setNgayTT(rs.getDate(5));
                kh.setTongTien(rs.getDouble(6));
                list.add(kh);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LichSuMuaHang> getDataByCustomer(String tenKH, String sdt) {
        List<LichSuMuaHang> result = new ArrayList<>();
        String query = "SELECT HoaDon.MaHoaDon, KhachHang.HoTen, KhachHang.Sdt, HoaDon.NgayTao, HoaDon.NgayThanhToan, HoaDon.TongTien " +
                       "FROM KhachHang " +
                       "JOIN HoaDon ON KhachHang.Id = HoaDon.IdKH " +
                       "WHERE KhachHang.HoTen = ? AND KhachHang.Sdt = ?";
        try (Connection conn = DBConnect.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, tenKH);
            ps.setString(2, sdt);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LichSuMuaHang kh = new LichSuMuaHang();
                kh.setMaHD(rs.getString(1));
                kh.setTenKH(rs.getString(2));
                kh.setSDT(rs.getString(3));
                kh.setNgayMH(rs.getDate(4));
                kh.setNgayTT(rs.getDate(5));
                kh.setTongTien(rs.getDouble(6));
                result.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
