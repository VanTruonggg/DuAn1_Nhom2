/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Lenovo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LichSuHoaDonResponse {
    private Integer id;
    private Integer idHoaDon;
    private Integer idNV;
    
    private String ma;
    private String maNV;
    
    private String hanhDongNguoiThaoTac;
    private String lyDoHuy;
    private Date ngayCapNhat;
    private String ghiChu;
    private Integer trangThai;
    
}
