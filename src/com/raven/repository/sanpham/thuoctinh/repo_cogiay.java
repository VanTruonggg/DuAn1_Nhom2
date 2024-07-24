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
import models.sanpham_container.thuoctinh.CoGiay;

/**
 *
 * @author Ca1
 */
public class repo_cogiay implements interface_repo_thuoctinh {
    Connection sConn = DBConnect.getConnection();

    @Override
    public void addThuocTinh(Object x) {
        CoGiay coGiay = ((CoGiay) x);
        String query = QuerySQL.getInsertQuery("CoGiay", "TenCoGiay");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, coGiay.getIdCoGiay());
            stm.setString(2, coGiay.getTenCoGiay());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void updateThuocTinh(Object x) {
        CoGiay coGiay = ((CoGiay) x);
        String query = QuerySQL.getUpdateQuery("CoGiay", coGiay.getIdCoGiay(), "TenCoGiay");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, coGiay.getTenCoGiay());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void removeThuocTinh(Object x) {
        CoGiay coGiay = ((CoGiay) x);
        String query = QuerySQL.getRemoveQuery("CoGiay", coGiay.getIdCoGiay());
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<Object> loadListThuocTinhFromDb() {
        ArrayList<Object> coGiays = new ArrayList<Object>();
        String query = "SELECT * FROM dbo.CoGiay";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                CoGiay ms = new CoGiay();

                ms.setIdCoGiay(rs.getString("Ma"));
                ms.setTenCoGiay(rs.getString("TenCoGiay"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                coGiays.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return coGiays;
    }
}
