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
import models.sanpham_container.thuoctinh.DeGiay;

/**
 *
 * @author Ca1
 */
public class repo_degiay implements interface_repo_thuoctinh {
    Connection sConn = DBConnect.getConnection();

    @Override
    public void addThuocTinh(Object x) {
        DeGiay deGiay = ((DeGiay) x);
        String query = QuerySQL.getInsertQuery("DeGiay", "TenDeGiay");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, deGiay.getIdDeGiay());
            stm.setString(2, deGiay.getTenDeGiay());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void updateThuocTinh(Object x) {
        DeGiay deGiay = ((DeGiay) x);
        String query = QuerySQL.getUpdateQuery("DeGiay", deGiay.getIdDeGiay(), "TenDeGiay");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, deGiay.getTenDeGiay());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void removeThuocTinh(Object x) {
        DeGiay deGiay = ((DeGiay) x);
        String query = QuerySQL.getRemoveQuery("DeGiay", deGiay.getIdDeGiay());
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<Object> loadListThuocTinhFromDb() {
        ArrayList<Object> deGiays = new ArrayList<Object>();
        String query = "SELECT * FROM dbo.DeGiay";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                DeGiay ms = new DeGiay();

                ms.setIdDeGiay(rs.getString("Ma"));
                ms.setTenDeGiay(rs.getString("TenDeGiay"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                deGiays.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deGiays;
    }

}
