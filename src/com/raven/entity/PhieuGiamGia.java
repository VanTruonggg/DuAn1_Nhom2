/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Pham Thu
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PhieuGiamGia {

    private int id;

    private String ma;

    private String ten;

    private int soLuong;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private double dieuKien;

    private String loai;

    private double giaTriToiThieu;

    private double giaTriToiDa;

    private String trangThai;

    public PhieuGiamGia(String ten, int soLuong, Date ngayBatDau, Date ngayKetThuc, double dieuKien, String loai, double giaTriToiThieu, double giaTriToiDa, String trangThai) {
        this.ten = ten;
        this.soLuong = soLuong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.dieuKien = dieuKien;
        this.loai = loai;
        this.giaTriToiThieu = giaTriToiThieu;
        this.giaTriToiDa = giaTriToiDa;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getDieuKien() {
        return dieuKien;
    }

    public void setDieuKien(double dieuKien) {
        this.dieuKien = dieuKien;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public double getGiaTriToiThieu() {
        return giaTriToiThieu;
    }

    public void setGiaTriToiThieu(double giaTriToiThieu) {
        this.giaTriToiThieu = giaTriToiThieu;
    }

    public double getGiaTriToiDa() {
        return giaTriToiDa;
    }

    public void setGiaTriToiDa(double giaTriToiDa) {
        this.giaTriToiDa = giaTriToiDa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
