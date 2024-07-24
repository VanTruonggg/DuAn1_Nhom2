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
import models.sanpham_container.thuoctinh.KichThuoc;

/**
 *
 * @author Ca1
 */
public class repo_kichthuoc implements interface_repo_thuoctinh {
    Connection sConn = DBConnect.getConnection();

    @Override
    public void addThuocTinh(Object x) {
        KichThuoc kichThuoc = ((KichThuoc) x);
        String query = QuerySQL.getInsertQuery("KichThuoc", "Size");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, kichThuoc.getIdKichThuoc());
            stm.setInt(2, kichThuoc.getSize());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void updateThuocTinh(Object x) {
        KichThuoc kichThuoc = ((KichThuoc) x);
        String query = QuerySQL.getUpdateQuery("KichThuoc", kichThuoc.getIdKichThuoc(), "Size");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setInt(1, kichThuoc.getSize());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void removeThuocTinh(Object x) {
        KichThuoc kichThuoc = ((KichThuoc) x);
        String query = QuerySQL.getRemoveQuery("KichThuoc", kichThuoc.getIdKichThuoc());
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<Object> loadListThuocTinhFromDb() {
        ArrayList<Object> kichThuocs = new ArrayList<Object>();
        String query = "SELECT * FROM dbo.KichThuoc";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                KichThuoc ms = new KichThuoc();

                ms.setIdKichThuoc(rs.getString("Ma"));
                ms.setSize(rs.getInt("Size"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                kichThuocs.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kichThuocs;
    }

}
