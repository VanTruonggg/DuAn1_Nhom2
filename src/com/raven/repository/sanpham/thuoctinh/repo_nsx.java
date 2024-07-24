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
import models.sanpham_container.thuoctinh.NhaSanXuat;

/**
 *
 * @author Ca1
 */
public class repo_nsx implements interface_repo_thuoctinh {
    Connection sConn = DBConnect.getConnection();

    @Override
    public void addThuocTinh(Object x) {
        NhaSanXuat nhaSanXuat = ((NhaSanXuat) x);
        String query = QuerySQL.getInsertQuery("NSX", "TenNSX");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, nhaSanXuat.getMaNSX());
            stm.setString(2, nhaSanXuat.getTenNSX());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateThuocTinh(Object x) {
        NhaSanXuat nhaSanXuat = ((NhaSanXuat) x);
        String query = QuerySQL.getUpdateQuery("NSX", nhaSanXuat.getMaNSX(), "TenNSX");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, nhaSanXuat.getTenNSX());
            stm.setInt(2, nhaSanXuat.getTrangThai());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void removeThuocTinh(Object x) {
        NhaSanXuat nhaSanXuat = ((NhaSanXuat) x);
        String query = QuerySQL.getRemoveQuery("NSX", nhaSanXuat.getMaNSX());
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<Object> loadListThuocTinhFromDb() {
        ArrayList<Object> nhaSanXuats = new ArrayList<Object>();
        String query = "SELECT * FROM dbo.NSX";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                NhaSanXuat ms = new NhaSanXuat();

                ms.setMaNSX(rs.getString("Ma"));
                ms.setTenNSX(rs.getString("TenNSX"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                nhaSanXuats.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nhaSanXuats;
    }

}
