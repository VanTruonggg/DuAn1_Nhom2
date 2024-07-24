/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.logic;

/**
 *
 * @author Ca1
 */
public class QuerySQL {

    public static String getInsertQuery(String tableName, String tenThuocTinh) {
        return "INSERT INTO dbo." + tableName + " \n"
                + "(\n"
                + "Ma,\n"
                + "" + tenThuocTinh + ",\n"
                + "TrangThai\n"
                + ")\n"
                + "VALUES(?, ?, 1)";
    }

    public static String getUpdateQuery(String tableName, String maThuocTinh, String tenThuocTinh) {
        return "UPDATE dbo." + tableName + "\n"
                + "SET\n"
                + "    " + tenThuocTinh + " = ?\n"
                + "WHERE Ma = " + maThuocTinh + " ";
    }

    public static String getRemoveQuery(String tableName, String maThuocTinh) {
        return "UPDATE dbo." + tableName + "\n"
                + "SET\n"
                + "    TrangThai = ?\n"
                + "WHERE Ma = " + maThuocTinh+ " ";
    }
}
