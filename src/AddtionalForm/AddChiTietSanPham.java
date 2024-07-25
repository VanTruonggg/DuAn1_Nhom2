/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package AddtionalForm;

import AddtionalForm.models.ThuocTinh;
import com.raven.form.SanPhamForm;
import com.raven.logic.HandleHinhAnh;
import com.raven.logic.RandomStringGenerator;
import com.raven.logic.ShowMessageCustom;
import com.raven.logic.Validate;
import interfaces.interface_repo_thuoctinh;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.raven.repository.sanpham.thuoctinh.repo_chatlieu;
import com.raven.repository.sanpham.thuoctinh.repo_cogiay;
import com.raven.repository.sanpham.thuoctinh.repo_degiay;
import com.raven.repository.sanpham.thuoctinh.repo_hang;
import com.raven.repository.sanpham.thuoctinh.repo_khoiluong;
import com.raven.repository.sanpham.thuoctinh.repo_kichthuoc;
import com.raven.repository.sanpham.thuoctinh.repo_nsx;
import com.raven.repository.sanpham.thuoctinh.repo_xuatxu;

import java.lang.reflect.Method;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.sanpham_container.SanPham;
import models.sanpham_container.SanPhamChiTiet;
import models.sanpham_container.thuoctinh.*;
import com.raven.repository.sanpham.repo_chitietsanpham;
import com.raven.repository.sanpham.repo_sanpham;
import interfaces.interface_thuoctinh;
import repositories.sanpham.thuoctinh.repo_mausac;
import swing.Notification;

/**
 *
 * @author Ca1
 */
public final class AddChiTietSanPham extends javax.swing.JPanel {

    interface_repo_thuoctinh interface_repo_thuoctinh = null;
    repo_sanpham repo_sanpham = new repo_sanpham();
    repo_chitietsanpham repo_chitietsanpham = new repo_chitietsanpham();

    ArrayList<String> listThuocTinh = new ArrayList<>();
    ArrayList<SanPham> listSanPham = new ArrayList<>();

    SanPhamForm spf = null;

    String idKichThuoc = null;
    String idMauSac = null;
    String idDeGiay = null;
    String idKhoiLuong = null;
    String idCoGiay = null;
    String idXuatXu = null;
    String idChatLieu = null;
    String idHang = null;
    String idNhaSanXuat = null;
    String idHinhAnh = null;
    String tenSanPham = null;
    String urlHinhAnh = null;

    /**
     * Creates new form AddChiTietSanPham
     */
    public AddChiTietSanPham(SanPhamForm spf) {
        initComponents();

        this.spf = spf;

        loadListThuocTinh(cboxHang, Hang.class, new repo_hang());
        loadListThuocTinh(cboxChatLieu, ChatLieu.class, new repo_chatlieu());
        loadListThuocTinh(cboxDeGiay, DeGiay.class, new repo_degiay());
        loadListThuocTinh(cboxKhoiLuong, KhoiLuong.class, new repo_khoiluong());
        loadListThuocTinh(cboxKichThuoc, KichThuoc.class, new repo_kichthuoc());
        loadListThuocTinh(cboxNhaSanXuat, NhaSanXuat.class, new repo_nsx());
        loadListThuocTinh(cboxXuatXu, XuatXu.class, new repo_xuatxu());
        loadListThuocTinh(cboxCoGiay, CoGiay.class, new repo_cogiay());
        loadListThuocTinh(cboxMauSac, MauSac.class, new repo_mausac());
        loadListTenSanPham(cboxTenSanPham);

        idMauSac = getIdAt(0, MauSac.class, new repo_mausac());
        idChatLieu = getIdAt(0, ChatLieu.class, new repo_chatlieu());
        idCoGiay = getIdAt(0, CoGiay.class, new repo_cogiay());
        idDeGiay = getIdAt(0, DeGiay.class, new repo_degiay());
        idHang = getIdAt(0, Hang.class, new repo_hang());
        idKichThuoc = getIdAt(0, KichThuoc.class, new repo_kichthuoc());
        idNhaSanXuat = getIdAt(0, NhaSanXuat.class, new repo_nsx());
        idKhoiLuong = getIdAt(0, KhoiLuong.class, new repo_khoiluong());
        idXuatXu = getIdAt(0, XuatXu.class, new repo_xuatxu());
    }

    public <T> int loadListThuocTinh(JComboBox<String> comboBox, Class<T> clazz, interface_repo_thuoctinh repo) {
        resetComboBox(comboBox);
        ArrayList<Object> listThuocTinh = repo.loadListThuocTinhFromDb();
        for (Object i : listThuocTinh) {
            try {
                T castedT = clazz.cast(i);

                Method getMaThuocTinh = clazz.getMethod("getIdThuocTinh");
                String maThuocTinh = (String) getMaThuocTinh.invoke(castedT);

                Method getTenThuocTinh = clazz.getMethod("getThuocTinh");
                String tenThuocTinh = (String) getTenThuocTinh.invoke(castedT);

                ThuocTinh thuocTinh = new ThuocTinh(maThuocTinh, tenThuocTinh);

                comboBox.addItem(thuocTinh.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listThuocTinh.size();
    }

    private void resetComboBox(JComboBox<String> comboBox) {
        comboBox.setModel(new DefaultComboBoxModel<String>());
    }

    public int loadListTenSanPham(JComboBox<String> comboBox) {
        listSanPham = repo_sanpham.loadListSanPhamFromDb();
        for (SanPham i : listSanPham) {
            ThuocTinh thuocTinh = new ThuocTinh();

            comboBox.addItem(i.getTenSp());
        }
        return listSanPham.size();
    }

    public <T> String setSelectedItemAndGetId(JComboBox<String> comboBox, int index, Class<T> clazz,
            interface_repo_thuoctinh repo) {
        ArrayList<Object> listThuocTinh = repo.loadListThuocTinhFromDb();

        Object obj = listThuocTinh.get(index);

        try {
            T selectedThuocTinh = clazz.cast(obj); // Ép kiểu đối tượng thành kiểu clazz

            Method getIdThuocTinh = clazz.getMethod("getIdThuocTinh");
            String idThuocTinh = (String) getIdThuocTinh.invoke(selectedThuocTinh);

            comboBox.setSelectedIndex(index);

            return idThuocTinh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> String getIdAt(int index, Class<T> clazz,
            interface_repo_thuoctinh repo) {
        ArrayList<Object> listThuocTinh = repo.loadListThuocTinhFromDb();

        Object obj = listThuocTinh.get(index);

        try {
            T selectedThuocTinh = clazz.cast(obj); // Ép kiểu đối tượng thành kiểu clazz

            Method getIdThuocTinh = clazz.getMethod("getIdThuocTinh");
            String idThuocTinh = (String) getIdThuocTinh.invoke(selectedThuocTinh);

            return idThuocTinh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAddKhoiLuong = new javax.swing.JButton();
        cboxKhoiLuong = new swing.Combobox();
        jPanel2 = new javax.swing.JPanel();
        cboxMauSac = new swing.Combobox();
        btnAddMauSac = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cboxXuatXu = new swing.Combobox();
        btnAddXuatXu = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnAddNhaSanXuat = new javax.swing.JButton();
        cboxNhaSanXuat = new swing.Combobox();
        jPanel5 = new javax.swing.JPanel();
        btnAddChatLieu = new javax.swing.JButton();
        cboxChatLieu = new swing.Combobox();
        jPanel4 = new javax.swing.JPanel();
        btnAddCoGiay = new javax.swing.JButton();
        cboxCoGiay = new swing.Combobox();
        jPanel7 = new javax.swing.JPanel();
        btnAddHang = new javax.swing.JButton();
        cboxHang = new swing.Combobox();
        jPanel8 = new javax.swing.JPanel();
        btnAddKichThuoc = new javax.swing.JButton();
        cboxKichThuoc = new swing.Combobox();
        jPanel9 = new javax.swing.JPanel();
        btnAddDeGiay = new javax.swing.JButton();
        cboxDeGiay = new swing.Combobox();
        jPanel11 = new javax.swing.JPanel();
        cboxTenSanPham = new swing.Combobox();
        btnAddTenSanPham = new javax.swing.JButton();
        btnThemCTSP = new javax.swing.JButton();
        lblHinhAnh = new javax.swing.JLabel();
        btnChonAnh = new javax.swing.JButton();
        txtGiaSanPhamChiTiet = new swing.TextField();
        txtSoLuongSanPhamChiTiet = new swing.TextField();
        textAreaScroll1 = new swing.TextAreaScroll();
        txtCTSPGhiChu = new swing.TextArea();
        btnQuayLại = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnAddKhoiLuong.setText("+");
        btnAddKhoiLuong.setBackground(new java.awt.Color(0, 153, 0));

        btnAddKhoiLuong.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N

        btnAddKhoiLuong.setForeground(new java.awt.Color(255, 255, 255));
        btnAddKhoiLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKhoiLuongActionPerformed(evt);
            }
        });

        cboxKhoiLuong.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Item1", "Item2", "Item3", "Item1", "Item2", "Item3", "Item1", "Item2", "Item3" }));
        cboxKhoiLuong.setSelectedIndex(-1);
        cboxKhoiLuong.setLabeText("Khối lượng");
        cboxKhoiLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxKhoiLuongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cboxKhoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddKhoiLuong)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 12, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cboxKhoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddKhoiLuong))));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        cboxMauSac.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Item1", "Item2", "Item3", "Item1", "Item2", "Item3", "Item1", "Item2", "Item3" }));
        cboxMauSac.setSelectedIndex(-1);
        cboxMauSac.setLabeText("Màu sắc");
        cboxMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxMauSacActionPerformed(evt);
            }
        });

        btnAddMauSac.setText("+");
        btnAddMauSac.setBackground(new java.awt.Color(0, 153, 0));

        btnAddMauSac.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N

        btnAddMauSac.setForeground(new java.awt.Color(255, 255, 255));
        btnAddMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMauSacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cboxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAddMauSac)
                                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 35, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cboxMauSac, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddMauSac, javax.swing.GroupLayout.Alignment.TRAILING))));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        cboxXuatXu.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Item1", "Item2", "Item3", "Item1", "Item2", "Item3", "Item1", "Item2", "Item3" }));
        cboxXuatXu.setSelectedIndex(-1);
        cboxXuatXu.setLabeText("Xuất xứ");
        cboxXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxXuatXuActionPerformed(evt);
            }
        });

        btnAddXuatXu.setText("+");
        btnAddXuatXu.setBackground(new java.awt.Color(0, 153, 0));

        btnAddXuatXu.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N

        btnAddXuatXu.setForeground(new java.awt.Color(255, 255, 255));
        btnAddXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddXuatXuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cboxXuatXu, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddXuatXu)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnAddXuatXu)
                                        .addComponent(cboxXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        btnAddNhaSanXuat.setText("+");
        btnAddNhaSanXuat.setBackground(new java.awt.Color(0, 153, 0));

        btnAddNhaSanXuat.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N

        btnAddNhaSanXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNhaSanXuatActionPerformed(evt);
            }
        });

        cboxNhaSanXuat.setLabeText("Nhà sản xuất");
        cboxNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxNhaSanXuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cboxNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddNhaSanXuat)
                                .addGap(0, 0, Short.MAX_VALUE)));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddNhaSanXuat)
                                        .addComponent(cboxNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(8, Short.MAX_VALUE)));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        btnAddChatLieu.setText("+");
        btnAddChatLieu.setBackground(new java.awt.Color(0, 153, 0));

        btnAddChatLieu.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N

        btnAddChatLieu.setForeground(new java.awt.Color(255, 255, 255));
        btnAddChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddChatLieuActionPerformed(evt);
            }
        });

        cboxChatLieu.setLabeText("Chất liệu");
        cboxChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxChatLieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cboxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddChatLieu)
                                .addGap(0, 18, Short.MAX_VALUE)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddChatLieu)
                                        .addComponent(cboxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(8, Short.MAX_VALUE)));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnAddCoGiay.setText("+");
        btnAddCoGiay.setBackground(new java.awt.Color(0, 153, 0));

        btnAddCoGiay.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N

        btnAddCoGiay.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCoGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCoGiayActionPerformed(evt);
            }
        });

        cboxCoGiay.setLabeText("Cổ giày");
        cboxCoGiay.setLightWeightPopupEnabled(false);
        cboxCoGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxCoGiayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cboxCoGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddCoGiay)));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddCoGiay)
                                        .addComponent(cboxCoGiay, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(8, Short.MAX_VALUE)));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnAddHang.setText("+");
        btnAddHang.setBackground(new java.awt.Color(0, 153, 0));

        btnAddHang.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N

        btnAddHang.setForeground(new java.awt.Color(255, 255, 255));
        btnAddHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddHangActionPerformed(evt);
            }
        });

        cboxHang.setLabeText("Hãng");
        cboxHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cboxHang, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddHang)
                                .addGap(0, 12, Short.MAX_VALUE)));
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddHang)
                                        .addComponent(cboxHang, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnAddKichThuoc.setText("+");
        btnAddKichThuoc.setBackground(new java.awt.Color(0, 153, 0));

        btnAddKichThuoc.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N

        btnAddKichThuoc.setForeground(new java.awt.Color(255, 255, 255));
        btnAddKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKichThuocActionPerformed(evt);
            }
        });

        cboxKichThuoc.setLabeText("Kích thước");
        cboxKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxKichThuocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cboxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddKichThuoc)
                                .addGap(0, 12, Short.MAX_VALUE)));
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddKichThuoc)
                                        .addComponent(cboxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(8, Short.MAX_VALUE)));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        btnAddDeGiay.setText("+");
        btnAddDeGiay.setBackground(new java.awt.Color(0, 153, 0));

        btnAddDeGiay.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N

        btnAddDeGiay.setForeground(new java.awt.Color(255, 255, 255));
        btnAddDeGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDeGiayActionPerformed(evt);
            }
        });

        cboxDeGiay.setLabeText("Đế giày");
        cboxDeGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxDeGiayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cboxDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddDeGiay)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cboxDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddDeGiay))));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        cboxTenSanPham.setLabeText("Tên sản phẩm");

        btnAddTenSanPham.setBackground(new java.awt.Color(0, 153, 0));
        btnAddTenSanPham.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        btnAddTenSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnAddTenSanPham.setText("+");
        btnAddTenSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTenSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cboxTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddTenSanPham)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cboxTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddTenSanPham))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        btnThemCTSP.setText("OK");
        btnThemCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCTSPActionPerformed(evt);
            }
        });

        lblHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnChonAnh.setText("chọn ảnh");
        btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhActionPerformed(evt);
            }
        });

        txtGiaSanPhamChiTiet.setLabelText("Giá ");

        txtSoLuongSanPhamChiTiet.setLabelText("Số lượng");

        textAreaScroll1.setLabelText("Ghi chú");

        txtCTSPGhiChu.setBackground(new java.awt.Color(232, 232, 232));
        txtCTSPGhiChu.setColumns(20);
        txtCTSPGhiChu.setRows(5);
        textAreaScroll1.setViewportView(txtCTSPGhiChu);

        btnQuayLại.setText("Quay lại");
        btnQuayLại.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLạiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(32, 32, 32)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jPanel9,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(73, 73, 73)
                                                                .addComponent(jPanel1,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        false)
                                                                        .addComponent(textAreaScroll1,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(jPanel8,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(jPanel11,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(68, 68, 68)
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jPanel5,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(77, 77, 77)
                                                                                .addComponent(lblHinhAnh,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        195,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(btnChonAnh,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        91,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addComponent(btnQuayLại, javax.swing.GroupLayout.PREFERRED_SIZE, 84,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnThemCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(24, 24, 24))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jPanel3,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(68, 68, 68)
                                                                .addComponent(jPanel6,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                        .addComponent(jPanel4,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(txtSoLuongSanPhamChiTiet,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE))
                                                                .addGap(78, 78, 78)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                        .addComponent(jPanel7,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(txtGiaSanPhamChiTiet,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE))))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addGap(57, 57, 57)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(jPanel6,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jPanel3,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(jPanel11,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(27, 27, 27))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(23, 23, 23)
                                                                .addComponent(jPanel2,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)))
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jPanel5,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jPanel7,
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(txtSoLuongSanPhamChiTiet,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(txtGiaSanPhamChiTiet,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblHinhAnh,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 198,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(textAreaScroll1,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 220,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(btnChonAnh))
                                .addGap(18, 23, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnThemCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 38,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnQuayLại, javax.swing.GroupLayout.PREFERRED_SIZE, 38,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)));
    }// </editor-fold>//GEN-END:initComponents

    private boolean isTrungItem(JComboBox comboBox, String item) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (item.equalsIgnoreCase(comboBox.getItemAt(i).toString())) {
                return true;
            }
        }
        return false;
    }

    private void btnQuayLạiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnQuayLạiActionPerformed
        spf.quit();
    }// GEN-LAST:event_btnQuayLạiActionPerformed

    private void btnChonAnhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChonAnhActionPerformed
        urlHinhAnh = HandleHinhAnh.chooseImage(lblHinhAnh, this);
    }// GEN-LAST:event_btnChonAnhActionPerformed

    private void cboxKhoiLuongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxKhoiLuongActionPerformed
        int indexSelected = cboxKhoiLuong.getSelectedIndex();
        idKhoiLuong = setSelectedItemAndGetId(cboxKhoiLuong, indexSelected, KhoiLuong.class, new repo_khoiluong());
    }// GEN-LAST:event_cboxKhoiLuongActionPerformed

    private void cboxMauSacActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxMauSacActionPerformed
        int indexSelected = cboxMauSac.getSelectedIndex();
        idMauSac = setSelectedItemAndGetId(cboxMauSac, indexSelected, MauSac.class, new repo_mausac());
    }// GEN-LAST:event_cboxMauSacActionPerformed

    private void cboxXuatXuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxXuatXuActionPerformed
        int indexSelected = cboxXuatXu.getSelectedIndex();
        idXuatXu = setSelectedItemAndGetId(cboxXuatXu, indexSelected, XuatXu.class, new repo_xuatxu());
    }// GEN-LAST:event_cboxXuatXuActionPerformed

    private void cboxDeGiayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxDeGiayActionPerformed
        int indexSelected = cboxDeGiay.getSelectedIndex();
        idDeGiay = setSelectedItemAndGetId(cboxDeGiay, indexSelected, DeGiay.class, new repo_degiay());
    }// GEN-LAST:event_cboxDeGiayActionPerformed

    private void cboxChatLieuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxChatLieuActionPerformed
        int indexSelected = cboxChatLieu.getSelectedIndex();
        idChatLieu = setSelectedItemAndGetId(cboxChatLieu, indexSelected, ChatLieu.class, new repo_chatlieu());
    }// GEN-LAST:event_cboxChatLieuActionPerformed

    private void cboxKichThuocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxKichThuocActionPerformed
        int indexSelected = cboxKichThuoc.getSelectedIndex();
        idKichThuoc = setSelectedItemAndGetId(cboxKichThuoc, indexSelected, KichThuoc.class, new repo_kichthuoc());
    }// GEN-LAST:event_cboxKichThuocActionPerformed

    private void cboxHangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxHangActionPerformed
        int indexSelected = cboxHang.getSelectedIndex();
        idHang = setSelectedItemAndGetId(cboxHang, indexSelected, Hang.class, new repo_hang());
    }// GEN-LAST:event_cboxHangActionPerformed

    private void cboxNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxNhaSanXuatActionPerformed
        int indexSelected = cboxNhaSanXuat.getSelectedIndex();
        idNhaSanXuat = setSelectedItemAndGetId(cboxNhaSanXuat, indexSelected, NhaSanXuat.class, new repo_nsx());
    }// GEN-LAST:event_cboxNhaSanXuatActionPerformed

    private void cboxCoGiayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxCoGiayActionPerformed
        int indexSelected = cboxCoGiay.getSelectedIndex();
        idCoGiay = setSelectedItemAndGetId(cboxCoGiay, indexSelected, CoGiay.class, new repo_cogiay());
    }// GEN-LAST:event_cboxCoGiayActionPerformed

    private void btnAddMauSacActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddMauSacActionPerformed
        while (true) {
            repo_mausac repo_mausac = new repo_mausac();
            String inputThuocTinh = JOptionPane.showInputDialog(this,
                    "Nhập thông số thuộc tính (ví dụ: Da, Vải, Nhựa)");
            if (inputThuocTinh != null) {
                if (inputThuocTinh.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxMauSac, inputThuocTinh)) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputThuocTinh);
                } else {
                    String idThuocTinh = RandomStringGenerator.generateRandomString("MS");
                    repo_mausac.addThuocTinh(new MauSac(idThuocTinh, inputThuocTinh, 1));
                    loadListThuocTinh(cboxMauSac, MauSac.class, repo_mausac);
                    idMauSac = setSelectedItemAndGetId(cboxMauSac, cboxMauSac.getItemCount() - 1,
                            MauSac.class, repo_mausac);
                    return;
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddMauSacActionPerformed

    private void btnAddXuatXuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddXuatXuActionPerformed
        while (true) {
            repo_xuatxu repo_xuatxu = new repo_xuatxu();
            String inputThuocTinh = JOptionPane.showInputDialog(this,
                    "Nhập thông số thuộc tính (ví dụ: Việt Nam, Mỹ, Nhật Bản)");
            if (inputThuocTinh != null) {
                if (inputThuocTinh.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxXuatXu, inputThuocTinh)) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputThuocTinh);
                } else {
                    String idThuocTinh = RandomStringGenerator.generateRandomString("XX");
                    repo_xuatxu.addThuocTinh(new XuatXu(idThuocTinh, inputThuocTinh, 1));
                    loadListThuocTinh(cboxXuatXu, XuatXu.class, repo_xuatxu);
                    idXuatXu = setSelectedItemAndGetId(cboxXuatXu, cboxXuatXu.getItemCount() - 1,
                            XuatXu.class, repo_xuatxu);
                    return;
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddXuatXuActionPerformed

    private void btnAddChatLieuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddChatLieuActionPerformed
        while (true) {
            repo_chatlieu repo_chatlieu = new repo_chatlieu();
            String inputThuocTinh = JOptionPane.showInputDialog(this,
                    "Nhập thông số thuộc tính (ví dụ: Da, Vải, Nhựa)");
            if (inputThuocTinh != null) {
                if (inputThuocTinh.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxChatLieu, inputThuocTinh)) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputThuocTinh);
                } else {
                    String idThuocTinh = RandomStringGenerator.generateRandomString("CL");
                    repo_chatlieu.addThuocTinh(new ChatLieu(idThuocTinh, inputThuocTinh, 1));
                    loadListThuocTinh(cboxChatLieu, ChatLieu.class, repo_chatlieu);
                    cboxChatLieu.setSelectedIndex(cboxChatLieu.getItemCount() - 1);
                    idChatLieu = setSelectedItemAndGetId(cboxChatLieu, cboxChatLieu.getItemCount() - 1, ChatLieu.class,
                            repo_chatlieu);
                    return;
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddChatLieuActionPerformed

    private void btnAddCoGiayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddCoGiayActionPerformed
        while (true) {
            repo_cogiay repo_cogiay = new repo_cogiay();
            String inputThuocTinh = JOptionPane.showInputDialog(this,
                    "Nhập thông số thuộc tính (ví dụ: Thấp, Trung, Cao)");

            if (inputThuocTinh != null) {
                if (inputThuocTinh.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxCoGiay, inputThuocTinh)) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputThuocTinh);
                } else {
                    String idThuocTinh = RandomStringGenerator.generateRandomString("CG");
                    repo_cogiay.addThuocTinh(new CoGiay(idThuocTinh, inputThuocTinh, 1));
                    loadListThuocTinh(cboxCoGiay, CoGiay.class, repo_cogiay);
                    idCoGiay = setSelectedItemAndGetId(cboxCoGiay, cboxCoGiay.getItemCount() - 1, CoGiay.class,
                            repo_cogiay);
                    return;
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddCoGiayActionPerformed

    private void btnAddDeGiayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddDeGiayActionPerformed
        while (true) {
            repo_degiay repo_degiay = new repo_degiay();
            String inputThuocTinh = JOptionPane.showInputDialog(this,
                    "Nhập thông số thuộc tính (ví dụ: Cao su, Da, Nhựa)");

            if (inputThuocTinh != null) {
                if (inputThuocTinh.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxDeGiay, inputThuocTinh)) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputThuocTinh);
                } else {
                    String idThuocTinhDeGiay = RandomStringGenerator.generateRandomString("DG");
                    repo_degiay.addThuocTinh(new DeGiay(idThuocTinhDeGiay, inputThuocTinh, 1));
                    loadListThuocTinh(cboxDeGiay, DeGiay.class, repo_degiay);
                    idDeGiay = setSelectedItemAndGetId(cboxDeGiay, cboxDeGiay.getItemCount() - 1, DeGiay.class,
                            repo_degiay);
                    return;
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddDeGiayActionPerformed

    private void btnAddHangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddHangActionPerformed
        while (true) {
            repo_hang repo_hang = new repo_hang();
            String inputThuocTinh = JOptionPane.showInputDialog(this,
                    "Nhập thông số thuộc tính (ví dụ: Nike, Adidas, Puma)");

            if (inputThuocTinh != null) {
                if (inputThuocTinh.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxHang, inputThuocTinh)) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputThuocTinh);
                } else {
                    String idThuocTinhHang = RandomStringGenerator.generateRandomString("H");
                    repo_hang.addThuocTinh(new Hang(idThuocTinhHang, inputThuocTinh, 1));
                    loadListThuocTinh(cboxHang, Hang.class, repo_hang);
                    idHang = setSelectedItemAndGetId(cboxHang, cboxHang.getItemCount() - 1, Hang.class, repo_hang);
                    return;
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddHangActionPerformed

    private void btnAddKichThuocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddKichThuocActionPerformed
        while (true) {
            repo_kichthuoc repo_kichthuoc = new repo_kichthuoc();
            String inputThuocTinh = JOptionPane.showInputDialog(this,
                    "Nhập thông số thuộc tính (ví dụ: 38, 39, 40)");

            if (inputThuocTinh != null) {
                if (inputThuocTinh.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxKichThuoc, inputThuocTinh)) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputThuocTinh);
                } else {
                    Validate validate = new Validate();
                    validate.checkNumber("Kích thước", inputThuocTinh);
                    if (validate.isChuoiHopLe()) {
                        String idThuocTinhKichThuoc = RandomStringGenerator.generateRandomString("KT");
                        repo_kichthuoc
                                .addThuocTinh(new KichThuoc(idThuocTinhKichThuoc, Integer.parseInt(inputThuocTinh), 1));
                        loadListThuocTinh(cboxKichThuoc, KichThuoc.class, repo_kichthuoc);
                        idKichThuoc = setSelectedItemAndGetId(cboxKichThuoc, cboxKichThuoc.getItemCount() - 1,
                                KichThuoc.class,
                                repo_kichthuoc);
                        return;
                    } else {
                        validate.showWarning(this);
                    }
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddKichThuocActionPerformed

    private void btnAddNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddNhaSanXuatActionPerformed
        while (true) {
            repo_nsx repo_nsx = new repo_nsx();
            String inputThuocTinh = JOptionPane.showInputDialog(this,
                    "Nhập thông số thuộc tính (ví dụ: Công ty ABC, Công ty XYZ)");
            if (inputThuocTinh != null) {
                if (inputThuocTinh.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxNhaSanXuat, inputThuocTinh)) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputThuocTinh);
                } else {
                    String idThuocTinhNSX = RandomStringGenerator.generateRandomString("NSX");
                    repo_nsx.addThuocTinh(new NhaSanXuat(idThuocTinhNSX, inputThuocTinh, 1));
                    loadListThuocTinh(cboxNhaSanXuat, NhaSanXuat.class, repo_nsx);
                    idNhaSanXuat = setSelectedItemAndGetId(cboxNhaSanXuat, cboxNhaSanXuat.getItemCount() - 1,
                            NhaSanXuat.class,
                            repo_nsx);
                    return;
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddNhaSanXuatActionPerformed

    private void btnThemCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemCTSPActionPerformed
        int indexHang = cboxHang.getSelectedIndex() + 1;
        int indexChatLieu = cboxChatLieu.getSelectedIndex() + 1;
        int indexKhoiLuong = cboxKhoiLuong.getSelectedIndex() + 1;
        int indexMauSac = cboxMauSac.getSelectedIndex() + 1;
        int indexDeGiay = cboxDeGiay.getSelectedIndex() + 1;
        int indexXuatXu = cboxXuatXu.getSelectedIndex() + 1;
        int indexNhaSanXuat = cboxNhaSanXuat.getSelectedIndex() + 1;
        int indexCoGiay = cboxCoGiay.getSelectedIndex() + 1;
        int indexKichThuoc = cboxKichThuoc.getSelectedIndex() + 1;

        // Lấy các mã từ các JComboBox tương ứng
        String idHang = indexHang + "";
        String idChatLieu = indexChatLieu + "";
        String idKhoiLuong = indexKhoiLuong + "";
        String idMauSac = indexMauSac + "";
        String idDeGiay = indexDeGiay + "";
        String idXuatXu = indexXuatXu + "";
        String idNhaSanXuat = indexNhaSanXuat + "";
        String idCoGiay = indexCoGiay + "";
        String idKichThuoc = indexKichThuoc + "";

        String idSPCT = RandomStringGenerator.generateRandomString("SPCT");
        String tenSanPham = cboxTenSanPham.getSelectedItem().toString();
        String ghiChu = txtCTSPGhiChu.getText();
        String soLuong = txtSoLuongSanPhamChiTiet.getText();
        String donGia = txtGiaSanPhamChiTiet.getText();

        Validate validate = new Validate();
        validate.checkNull("Số lượng", soLuong);
        validate.checkNumber("Số lượng", soLuong);
        validate.checkNull("Giá", donGia);
        validate.checkNumber("Giá", donGia);

        if (validate.isChuoiHopLe()) {
            SanPhamChiTiet spct = new SanPhamChiTiet(idSPCT, tenSanPham, idHang, idChatLieu, idKhoiLuong, idMauSac,
                    idDeGiay, idXuatXu, idNhaSanXuat, idCoGiay, null, ghiChu, soLuong, idKichThuoc, donGia, urlHinhAnh,
                    1);

            repo_chitietsanpham.addCTSPToDB(spct);
            spf.loadListChiTietSanPhamToTable();
            spf.quit();
            ShowMessageCustom.showMessageSuccess(spf, "Thêm chi tiết sản phẩm thành công");
        } else {
            validate.showWarning(this);
        }
    }// GEN-LAST:event_btnThemCTSPActionPerformed

    private void btnAddTenSanPhamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddTenSanPhamActionPerformed
        while (true) {
            String inputTenSanPham = JOptionPane.showInputDialog(this, "Nhập tên sản phẩm");
            if (inputTenSanPham != null) {
                if (inputTenSanPham.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxTenSanPham, inputTenSanPham)) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputTenSanPham);
                } else {
                    cboxTenSanPham.addItem(inputTenSanPham);
                    cboxTenSanPham.setSelectedIndex(cboxTenSanPham.getItemCount() - 1);
                    return;
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddTenSanPhamActionPerformed

    private void btnAddKhoiLuongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddKhoiLuongActionPerformed
        while (true) {
            repo_khoiluong repo_khoiluong = new repo_khoiluong();
            String inputThuocTinh = JOptionPane.showInputDialog(this, "Nhập thông số thuộc tính ( đơn vị: gram )");

            if (inputThuocTinh != null) {
                if (inputThuocTinh.isEmpty()) {
                    ShowMessageCustom.showMessageWarning(this, "Vui lòng nhập giá trị");
                } else if (isTrungItem(cboxKhoiLuong, inputThuocTinh + " Gram")) {
                    ShowMessageCustom.showMessageWarning(this, "Đã tồn thuộc tính " + inputThuocTinh + " Gram");
                } else {
                    Validate validate = new Validate();
                    validate.checkNumber("Khối lượng", inputThuocTinh);
                    if (validate.isChuoiHopLe()) {
                        repo_khoiluong.addThuocTinh(
                                new KhoiLuong(RandomStringGenerator.generateRandomString("KL"), inputThuocTinh, 1));
                        loadListThuocTinh(cboxKhoiLuong, KhoiLuong.class, repo_khoiluong);
                        idKhoiLuong = setSelectedItemAndGetId(cboxKhoiLuong, cboxKhoiLuong.getItemCount() - 1,
                                KhoiLuong.class,
                                repo_khoiluong);
                        return;
                    } else {
                        validate.showWarning(this);
                    }
                }
            } else {
                return;
            }
        }
    }// GEN-LAST:event_btnAddKhoiLuongActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddChiTietSanPham.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddChiTietSanPham.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddChiTietSanPham.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddChiTietSanPham.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        // java.awt.EventQueue.invokeLater(new Runnable() {
        // public void run() {
        // new AddChiTietSanPham().setVisible(true);
        // }
        // });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddChatLieu;
    private javax.swing.JButton btnAddCoGiay;
    private javax.swing.JButton btnAddDeGiay;
    private javax.swing.JButton btnAddHang;
    private javax.swing.JButton btnAddKhoiLuong;
    private javax.swing.JButton btnAddKichThuoc;
    private javax.swing.JButton btnAddMauSac;
    private javax.swing.JButton btnAddNhaSanXuat;
    private javax.swing.JButton btnAddTenSanPham;
    private javax.swing.JButton btnAddXuatXu;
    private javax.swing.JButton btnChonAnh;
    private javax.swing.JButton btnQuayLại;
    private javax.swing.JButton btnThemCTSP;
    private swing.Combobox cboxChatLieu;
    private swing.Combobox cboxCoGiay;
    private swing.Combobox cboxDeGiay;
    private swing.Combobox cboxHang;
    private swing.Combobox cboxKhoiLuong;
    private swing.Combobox cboxKichThuoc;
    private swing.Combobox cboxMauSac;
    private swing.Combobox cboxNhaSanXuat;
    private swing.Combobox cboxTenSanPham;
    private swing.Combobox cboxXuatXu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblHinhAnh;
    private swing.TextAreaScroll textAreaScroll1;
    private swing.TextArea txtCTSPGhiChu;
    private swing.TextField txtGiaSanPhamChiTiet;
    private swing.TextField txtSoLuongSanPhamChiTiet;
    // End of variables declaration//GEN-END:variables
}
