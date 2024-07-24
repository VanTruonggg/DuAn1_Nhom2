package repositories.sanpham.thuoctinh;

import com.raven.config.DBConnect;
import com.raven.logic.QuerySQL;
import java.util.ArrayList;
import models.sanpham_container.thuoctinh.MauSac;
import java.sql.*;
import interfaces.interface_repo_thuoctinh;

public class repo_mausac implements interface_repo_thuoctinh {

    Connection sConn = DBConnect.getConnection();

    @Override
    public void addThuocTinh(Object x) {
        MauSac mauSac = ((MauSac) x);
        String query = QuerySQL.getInsertQuery("MauSac", "TenMau");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, mauSac.getIdMauSac());
            stm.setString(2, mauSac.getTenMauSac());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateThuocTinh(Object x) {
        MauSac mauSac = ((MauSac) x);
        String query = QuerySQL.getUpdateQuery("MauSac", mauSac.getIdMauSac(), "TenMau");
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.setString(1, mauSac.getTenMauSac());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeThuocTinh(Object x) {
        MauSac mauSac = ((MauSac) x);
        String query = QuerySQL.getRemoveQuery("MauSac", mauSac.getIdMauSac());
        try {
            PreparedStatement stm = sConn.prepareStatement(query);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Object> loadListThuocTinhFromDb() {

        ArrayList<Object> mauSacs = new ArrayList<Object>();
        String query = "SELECT * FROM dbo.MauSac";
        try {
            Statement stm = sConn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                MauSac ms = new MauSac();

                ms.setIdMauSac(rs.getString("Ma"));
                ms.setTenMauSac(rs.getString("TenMau"));
                ms.setTrangThai(rs.getInt("TrangThai"));

                mauSacs.add(ms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mauSacs;
    }
}
