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
import models.sanpham_container.thuoctinh.Hang;

/**
 *
 * @author Ca1
 */
public class repo_hang implements interface_repo_thuoctinh {
    Connection sConn = DBConnect.getConnection();

    @Override
    public void addThuocTinh(Object x) {
        Hang hang = ((Hang) x);
        String query = QuerySQL.getInsertQuery("Hang", "TenHang");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, hang.getIdHang());
            stm.setString(2, hang.getTenHang());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void updateThuocTinh(Object x) {
        Hang hang = ((Hang) x);
        String query = QuerySQL.getUpdateQuery("Hang", hang.getIdHang(), "TenHang");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, hang.getTenHang());
            stm.setInt(2, hang.getTrangThai());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void removeThuocTinh(Object x) {
        Hang hang = ((Hang) x);
        String query = QuerySQL.getRemoveQuery("Hang", hang.getIdHang());
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<Object> loadListThuocTinhFromDb() {
        ArrayList<Object> hangs = new ArrayList<Object>();
        String query = "SELECT * FROM dbo.Hang";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                Hang ms = new Hang();

                ms.setIdHang(rs.getString("Ma"));
                ms.setTenHang(rs.getString("TenHang"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                hangs.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hangs;
    }

}
