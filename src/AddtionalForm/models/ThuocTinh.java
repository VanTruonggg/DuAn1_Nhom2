/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AddtionalForm.models;

/**
 *
 * @author Ca1
 */
public class ThuocTinh {
    private String ma;
    private String ten;

    public ThuocTinh(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    public ThuocTinh() {
    }

    public String getMa() {
        return ma;
    }

    public String getTen() {
        return ten;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return ten; // Hiển thị tên trong JComboBox
    }
}
