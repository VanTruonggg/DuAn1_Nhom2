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
import models.sanpham_container.thuoctinh.ChatLieu;

/**
 *
 * @author Ca1
 */
public class repo_chatlieu implements interface_repo_thuoctinh {
    Connection sConn = DBConnect.getConnection();

    @Override
    public void addThuocTinh(Object x) {
        ChatLieu chatLieu = ((ChatLieu) x);
        String query = QuerySQL.getInsertQuery("ChatLieu", "Loai");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, chatLieu.getIdChatLieu());
            stm.setString(2, chatLieu.getLoai());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void updateThuocTinh(Object x) {
        ChatLieu chatLieu = ((ChatLieu) x);
        String query = QuerySQL.getUpdateQuery("ChatLieu", chatLieu.getIdChatLieu(), "Loai");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, chatLieu.getLoai());
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void removeThuocTinh(Object x) {
        ChatLieu chatLieu = ((ChatLieu) x);
        String query = QuerySQL.getRemoveQuery("ChatLieu", chatLieu.getIdChatLieu());
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public ArrayList<Object> loadListThuocTinhFromDb() {
        ArrayList<Object> chatLieus = new ArrayList<Object>();
        String query = "SELECT * FROM dbo.ChatLieu";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                ChatLieu ms = new ChatLieu();

                ms.setIdChatLieu(rs.getString("Ma"));
                ms.setLoai(rs.getString("Loai"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                chatLieus.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chatLieus;
    }

}
