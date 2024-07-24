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
import models.sanpham_container.thuoctinh.XuatXu;

/**
 *
 * @author Ca1
 */
public class repo_xuatxu implements interface_repo_thuoctinh {
    Connection sConn = DBConnect.getConnection();

    @Override
    public void addThuocTinh(Object x) {
        XuatXu xuatXu = ((XuatXu) x);
        String query = QuerySQL.getInsertQuery("XuatXu", "NoiXuatXu");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, xuatXu.getIdXuatXu());
            stm.setString(2, xuatXu.getNoiXuatXu());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void updateThuocTinh(Object x) {
        XuatXu xuatXu = ((XuatXu) x);
        String query = QuerySQL.getUpdateQuery("XuatXu", xuatXu.getIdXuatXu(), "NoiXuatXu");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, xuatXu.getNoiXuatXu());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void removeThuocTinh(Object x) {
        XuatXu xuatXu = ((XuatXu) x);
        String query = QuerySQL.getRemoveQuery("XuatXu", xuatXu.getIdXuatXu());
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<Object> loadListThuocTinhFromDb() {
        ArrayList<Object> xuatXus = new ArrayList<Object>();
        String query = "SELECT * FROM dbo.XuatXu";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                XuatXu ms = new XuatXu();

                ms.setIdXuatXu(rs.getString("Ma"));
                ms.setNoiXuatXu(rs.getString("NoiXuatXu"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                xuatXus.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xuatXus;
    }

}
