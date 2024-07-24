/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository.khachhang;

import com.raven.config.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.khachhang_container.KhachHang;

/**
 *
 * @author DELL-LAPTOP
 */
public class KhachHangRepo {
    List<KhachHang> list = new ArrayList<>();
    public List<KhachHang> getData(){
        String query = "select Id,MaKH,HoTen,Sdt,GioiTinh,DiaChi from KhachHang";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setId(rs.getInt(1));
                kh.setMaKH(rs.getString(2));
                kh.setHoTen(rs.getString(3));
                kh.setSDT(rs.getString(4));
                kh.setGioiTinh(rs.getBoolean(5));
                kh.setDiaChi(rs.getString(6));
                list.add(kh);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void Add(KhachHang kh) {
        String query = "insert into KhachHang(MaKH, HoTen, Sdt, GioiTinh, DiaChi) values (?,?,?,?,?)";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSDT());
            ps.setBoolean(4, kh.isGioiTinh());
            ps.setString(5, kh.getDiaChi());
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Update(KhachHang kh) {
        String query = "update KhachHang set HoTen=?,Sdt=?,GioiTinh=?,DiaChi=? where MaKH=?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            
            ps.setString(5, kh.getMaKH());
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSDT());
            ps.setBoolean(3, kh.isGioiTinh());
            ps.setString(4, kh.getDiaChi());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Delete(KhachHang kh) {
        String query = "delete from KhachHang where MaKH = ?\n";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, kh.getMaKH());
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<KhachHang> timKiem(String tenKH, String SDT) {
    ArrayList<KhachHang> list = new ArrayList<>();
    
    String sql = "select Id, MaKH, HoTen, Sdt, GioiTinh, DiaChi from KhachHang where HoTen like ? or Sdt like ?";
    
    try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, '%' + tenKH + '%');
        ps.setString(2, '%' + SDT + '%');
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            KhachHang rp = new KhachHang();
            rp.setId(rs.getInt(1));
            rp.setMaKH(rs.getString(2));
            rp.setHoTen(rs.getString(3));
            rp.setSDT(rs.getString(4));
            rp.setGioiTinh(rs.getBoolean(5));
            rp.setDiaChi(rs.getString(6));
            list.add(rp);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
