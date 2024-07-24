/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository.sanpham;

import com.raven.config.DBConnect;
import java.util.ArrayList;
import models.sanpham_container.SanPham;
import java.sql.*;

/**
 *
 * @author Ca1
 */
public class repo_sanpham {

    Connection sConn = DBConnect.getConnection();

    public void themSanPham(SanPham x) {
        String query = "INSERT INTO dbo.SanPham\n"
                + "(\n"
                + "    MaSP,\n"
                + "    TenSanPham,\n"
                + "    SoLuong,\n"
                + "    TrangThai\n"
                + ")\n"
                + "VALUES\n"
                + "(   ?,     -- MaSP - varchar(10)\n"
                + "    ?,    -- TenSanPham - nvarchar(30)\n"
                + "    0,      -- SoLuong - int\n"
                + "    ? -- TrangThai - bit\n"
                + "    )";
        try {
            PreparedStatement stm = sConn.prepareStatement(query);

            stm.setString(1, x.getMaSp());
            stm.setString(2, x.getTenSp());
            stm.setInt(3, x.getTrangThai());

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void suaSanPham(SanPham x) {
        String query = "UPDATE dbo.SanPham\n"
                + "SET TenSanPham = ?\n"
                + "WHERE MaSP = ?";
        try {
            PreparedStatement stm = sConn.prepareStatement(query);

            stm.setString(1, x.getTenSp());
            stm.setString(2, x.getMaSp());

            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }
    
    public void xoaSanPham(String maSP) {
        String query = "UPDATE dbo.SanPham\n"
                + "SET TrangThai = 0\n"
                + "WHERE MaSP = ?";
        try {
            PreparedStatement stm = sConn.prepareStatement(query);

            stm.setString(1, maSP);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SanPham> loadListSanPhamFromDb() {

        ArrayList<SanPham> sanPhams = new ArrayList<SanPham>();
        String query = "SELECT * FROM dbo.SanPham";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                SanPham ms = new SanPham();

                ms.setMaSp(rs.getString("MaSP"));
                ms.setTenSp(rs.getString("TenSanPham"));
                ms.setSoLuong(rs.getInt("SoLuong"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                sanPhams.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sanPhams;
    }
}
