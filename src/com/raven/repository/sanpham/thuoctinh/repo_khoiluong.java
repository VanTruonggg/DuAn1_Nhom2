/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository.sanpham.thuoctinh;

import com.raven.config.DBConnect;
import com.raven.logic.QuerySQL;
import java.sql.*;
import interfaces.interface_repo_thuoctinh;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import models.sanpham_container.thuoctinh.KhoiLuong;

/**
 *
 * @author Ca1
 */
public class repo_khoiluong implements interface_repo_thuoctinh {
    Connection sConn = DBConnect.getConnection();

    @Override
    public void addThuocTinh(Object x) {
        KhoiLuong khoiLuong = ((KhoiLuong) x);
        String query = QuerySQL.getInsertQuery("KhoiLuong", "TenKhoiLuong");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, khoiLuong.getIdKhoiLuong());
            stm.setString(2, khoiLuong.getTenKhoiLuong());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void updateThuocTinh(Object x) {
        KhoiLuong khoiLuong = ((KhoiLuong) x);
        String query = QuerySQL.getUpdateQuery("KhoiLuong", khoiLuong.getIdKhoiLuong(), "TenKhoiLuong");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, khoiLuong.getTenKhoiLuong());
            stm.setInt(2, khoiLuong.getTrangThai());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void removeThuocTinh(Object x) {
        KhoiLuong khoiLuong = ((KhoiLuong) x);
        String query = QuerySQL.getRemoveQuery("KhoiLuong", khoiLuong.getIdKhoiLuong());
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<Object> loadListThuocTinhFromDb() {
        ArrayList<Object> khoiLuongs = new ArrayList<Object>();
        String query = "SELECT * FROM dbo.KhoiLuong";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                KhoiLuong ms = new KhoiLuong();

                ms.setIdKhoiLuong(rs.getString("Ma"));
                ms.setTenKhoiLuong(rs.getString("TenKhoiLuong"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                khoiLuongs.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return khoiLuongs;
    }

}
