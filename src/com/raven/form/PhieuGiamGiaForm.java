/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.raven.cell.TableActionCellEditor;
import com.raven.cell.TableActionCellRender;
import com.raven.cell.TableActionEvent;
import com.raven.entity.PhieuGiamGia;
import com.raven.repository.PhieuGiamGiaRespository;
import com.raven.util.Helper;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jdk.jfr.consumer.EventStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
public class PhieuGiamGiaForm extends javax.swing.JPanel {

    private DefaultTableModel dtm = new DefaultTableModel();
    private PhieuGiamGiaRespository repo;

    /**
     * Creates new form PhieuGiamGiaForm
     */
    public PhieuGiamGiaForm() {
        initComponents();
        edit();
        repo = new PhieuGiamGiaRespository();
        tbPhieuGiamGia.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender());
        dtm = (DefaultTableModel) tbPhieuGiamGia.getModel();
//        startUIUpdateThread();
        showDataTable(repo.getAll());
        runThreadCheckNgayKetThuc();

    }

    private void edit() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int index) {
//                System.out.println("Row " + index);
                DefaultTableModel dtm = (DefaultTableModel) tbPhieuGiamGia.getModel();
                PhieuGiamGia pgg = repo.getAll().get(index);
                repo.updateSatus(pgg.getId());
                showDataTable(repo.getAll());
            }
        };

        tbPhieuGiamGia.getColumnModel().getColumn(10).setCellRenderer(new TableActionCellRender());
        tbPhieuGiamGia.getColumnModel().getColumn(10).setCellEditor(new TableActionCellEditor(event));
    }

    private void showDataTable(ArrayList<PhieuGiamGia> list) {
        dtm.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        for (PhieuGiamGia pgg : list) {
            dtm.addRow(new Object[]{
                index.getAndIncrement(), pgg.getMa(), pgg.getTen(), pgg.getSoLuong(), pgg.getLoai(), pgg.getGiaTriToiThieu(),
                pgg.getGiaTriToiDa(), pgg.getNgayBatDau(), pgg.getNgayKetThuc(), pgg.getTrangThai()
            });
        }
    }

    public void deatailPhieuGiamGia(int index) {
        PhieuGiamGia pgg = repo.getAll().get(index);
        txtMaVoucher.setText(pgg.getMa());
        txtTenVoucher.setText(pgg.getTen());
        txtDieuKien.setText(String.valueOf(pgg.getDieuKien()));
        txtGiaTriToiDa.setText(String.valueOf(pgg.getGiaTriToiDa()));
        txtGiaTriToiThieu.setText(String.valueOf(pgg.getGiaTriToiThieu()));
        txtSoLuong.setText(String.valueOf(pgg.getSoLuong()));
        dateNgayBatDau.setDate(pgg.getNgayBatDau());
        dateNgayKetThuc.setDate(pgg.getNgayKetThuc());
        cbbLoai.setSelectedItem(pgg.getLoai());
    }

    private void openDetail(String ma, String ten, String loai, double gtrTT, double gtrTD,
            Date ngayBD, Date ngayKT, int soLuong, double dieuKien, String trangThai) {

        PhieuGiamGiaJFrame frame = new PhieuGiamGiaJFrame(this);

        frame.setDeatail(ma, ten, loai, gtrTT, gtrTD, ngayBD, ngayKT, soLuong, dieuKien, trangThai);
        frame.setVisible(true);
    }

    private PhieuGiamGia getForm() {
        String ma = txtMaVoucher.getText().trim();
        String ten = txtTenVoucher.getText().trim();
        if (ten == null || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống");
            return null;
        }
        if (!Helper.checkDoDaiCuaChuoi(ten)) {
            JOptionPane.showMessageDialog(this, "Độ dài của tên không hợp lệ. Từ 3-20 kí tự !");
            return null;
        }
        String loai = (String) cbbLoai.getSelectedItem();

        if (txtGiaTriToiThieu.getText() == null || txtGiaTriToiThieu.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Gía trị không được để trống");
            return null;
        }
        double giaTriToiThieu = 0;
        try {
            giaTriToiThieu = Double.parseDouble(txtGiaTriToiThieu.getText().trim());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gía trị không hợp lệ !");
            return null;
        }
        if (giaTriToiThieu < 0) {
            JOptionPane.showInternalMessageDialog(this, "Giá trị phải lớn hơn 0");
            return null;
        }

        if (txtGiaTriToiDa.getText() == null || txtGiaTriToiDa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Gía trị không được để trống");
            return null;
        }

        double giaTriToiDa = 0;
        try {
            giaTriToiDa = Double.parseDouble(txtGiaTriToiDa.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gía trị không hợp lệ !");
            return null;
        }
        if (giaTriToiDa < 0) {
            JOptionPane.showMessageDialog(this, "Giá trị phải lớn hơn 0");
            return null;
        }

        if (giaTriToiThieu > giaTriToiDa) {
            JOptionPane.showInternalMessageDialog(this, "Giá trị tối thiểu phải nhỏ hơn GT tối đa");
            return null;
        }
        Date starDate = dateNgayBatDau.getDate();

        if (starDate == null) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được để trống!");
            return null;
        }
        Date endDate = dateNgayKetThuc.getDate();

        if (endDate == null) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được để trống!");
            return null;
        }

        if (endDate.getTime() < starDate.getTime()) {
            JOptionPane.showMessageDialog(this,
                    "Ngày kết thúc phải sau ngày bắt đầu!");
            return null;
        }

        if (txtSoLuong.getText() == null || txtSoLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống");
            return null;
        }
        int soLuong = 0;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gía trị không hợp lệ !");
            return null;
        }
        if (txtDieuKien.getText() == null || txtDieuKien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điều kiện không được để trống");
            return null;
        }
        double dieuKien = 0;
        try {
            dieuKien = Double.parseDouble(txtDieuKien.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gía trị không hợp lệ !");
            return null;
        }

        Date currentDate = new Date();
        String trangThai;
        if (currentDate.before(starDate)) {
            trangThai = "Sắp diễn ra";
        } else if (currentDate.after(endDate)) {
            trangThai = "Hết hạn";
        } else {
            trangThai = "Đang diễn ra";
        }
        return new PhieuGiamGia(ten, soLuong, starDate, endDate,
                dieuKien, loai, giaTriToiThieu, giaTriToiDa, trangThai);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbbLoai = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        dateNgayBatDau = new com.toedter.calendar.JDateChooser();
        dateNgayKetThuc = new com.toedter.calendar.JDateChooser();
        txtTenVoucher = new com.raven.util.TextField();
        txtGiaTriToiDa = new com.raven.util.TextField();
        txtGiaTriToiThieu = new com.raven.util.TextField();
        txtSoLuong = new com.raven.util.TextField();
        txtDieuKien = new com.raven.util.TextField();
        txtMaVoucher = new com.raven.util.TextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbbLoaiSearch = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnLoc = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPhieuGiamGia = new javax.swing.JTable();
        dateLocNgayBatDau = new com.toedter.calendar.JDateChooser();
        dateLocNgayKetThuc = new com.toedter.calendar.JDateChooser();
        btnTimKiem = new javax.swing.JButton();
        txtSearch = new com.raven.util.TextField();
        btnXuatExcel = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phiếu giảm giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel4.setText("Loại giảm giá : ");

        cbbLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phần trăm", "Số Tiền" }));

        jLabel6.setText("Ngày bắt đầu : ");

        jLabel7.setText("Ngày kết thúc : ");

        btnThem.setBackground(new java.awt.Color(255, 153, 51));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Add.png"))); // NOI18N
        btnThem.setText("Thêm mới");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 153, 51));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Upload.png"))); // NOI18N
        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 153, 51));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Refresh.png"))); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        dateNgayBatDau.setDateFormatString("dd-MM-yyyy");

        dateNgayKetThuc.setDateFormatString("dd-MM-yyyy");

        txtTenVoucher.setLabelText("Tên voucher");

        txtGiaTriToiDa.setLabelText("Giá trị tối đa");

        txtGiaTriToiThieu.setLabelText("Giá trị tối thiểu");

        txtSoLuong.setLabelText("Số lượng");

        txtDieuKien.setLabelText("Điều kiện");

        txtMaVoucher.setEditable(false);
        txtMaVoucher.setEnabled(false);
        txtMaVoucher.setLabelText("Mã voucher");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDieuKien, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGiaTriToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtGiaTriToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(35, 35, 35)
                        .addComponent(dateNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel4)
                        .addGap(22, 22, 22)
                        .addComponent(cbbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(btnThem)
                        .addGap(65, 65, 65)
                        .addComponent(btnUpdate)
                        .addGap(67, 67, 67)
                        .addComponent(btnReset))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(35, 35, 35)
                        .addComponent(dateNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaTriToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTenVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDieuKien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaTriToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(dateNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cbbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(dateNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnUpdate)
                    .addComponent(btnReset))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("QUẢN LÝ PHIẾU GIẢM GIÁ");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phiếu giảm giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel10.setText("Loại Giảm");

        cbbLoaiSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Phần trăm", "Số Tiền" }));
        cbbLoaiSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLoaiSearchActionPerformed(evt);
            }
        });

        jLabel11.setText("Trạng thái");

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đang diễn ra", "Sắp diễn ra", "Hết hạn" }));
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        jLabel12.setText("Ngày bắt đầu : ");

        jLabel13.setText("Ngày kết thúc : ");

        btnLoc.setBackground(new java.awt.Color(255, 153, 51));
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        tbPhieuGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã voucher", "Tên voucher", "Số lượng", "Loại", "Giá trị tối thiểu", "Giá trị tối đa", "Ngày BĐ", "Ngày KT", "Trạng thái", "Hành động"
            }
        ));
        tbPhieuGiamGia.setRowHeight(35);
        tbPhieuGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhieuGiamGiaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbPhieuGiamGiaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbPhieuGiamGia);
        if (tbPhieuGiamGia.getColumnModel().getColumnCount() > 0) {
            tbPhieuGiamGia.getColumnModel().getColumn(10).setPreferredWidth(35);
        }

        dateLocNgayBatDau.setDateFormatString("dd-MM-yyyy");

        dateLocNgayKetThuc.setDateFormatString("dd-MM-yyyy");

        btnTimKiem.setBackground(new java.awt.Color(255, 153, 51));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Search_1.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        txtSearch.setLabelText("Tìm kiếm theo mã, tên voucher");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnXuatExcel.setBackground(new java.awt.Color(255, 153, 51));
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateLocNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbbLoaiSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(dateLocNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnXuatExcel)
                                .addGap(24, 24, 24))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cbbLoaiSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimKiem)))
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLoc)
                        .addComponent(jLabel12)
                        .addComponent(jLabel13))
                    .addComponent(dateLocNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnXuatExcel)
                        .addComponent(dateLocNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(389, 389, 389))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn chắn chắn muốn thêm ?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        if (repo.add(getForm())) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            showDataTable(repo.getAll());
            this.reSet();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại ");
        }
//        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, "Thêm thành công");
    }//GEN-LAST:event_btnThemActionPerformed


    private void tbPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhieuGiamGiaMouseClicked
        // TODO add your handling code here:
        int index = tbPhieuGiamGia.getSelectedRow();
        deatailPhieuGiamGia(index);
        PhieuGiamGia pgg = repo.getAll().get(index);
        if (evt.getClickCount() == 2) {
            String ma = tbPhieuGiamGia.getValueAt(index, 1).toString();
            String ten = tbPhieuGiamGia.getValueAt(index, 2).toString();
            int soLuong = Integer.valueOf(tbPhieuGiamGia.getValueAt(index, 3).toString());
            String loai = tbPhieuGiamGia.getValueAt(index, 4).toString();
            double gtriTT = Double.valueOf(tbPhieuGiamGia.getValueAt(index, 5).toString());
            double gtriTD = Double.valueOf(tbPhieuGiamGia.getValueAt(index, 6).toString());
            Date ngayBD = null;
            Date ngayKT = null;
            try {
                String ngayBD0 = tbPhieuGiamGia.getValueAt(index, 7).toString();
                String ngayKT0 = tbPhieuGiamGia.getValueAt(index, 8).toString();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                ngayBD = format.parse(ngayBD0);
                ngayKT = format.parse(ngayKT0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String trangThai = tbPhieuGiamGia.getValueAt(index, 9).toString();
            double dieuKien = pgg.getDieuKien();

            openDetail(ma, ten, loai, gtriTT, gtriTD, ngayBD, ngayKT, soLuong, dieuKien, trangThai);
        }
    }//GEN-LAST:event_tbPhieuGiamGiaMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa không");

        if (check != JOptionPane.YES_OPTION) {
            return;
        }
        int index = tbPhieuGiamGia.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "bạn chưa chọn PGG để sửa");
        } else {
            PhieuGiamGia pgg = repo.getAll().get(tbPhieuGiamGia.getSelectedRow());
            if (repo.update(getForm(), pgg.getId())) {
                JOptionPane.showMessageDialog(this, "Sửa thành công");
//                Notification.show()
                showDataTable(repo.getAll());
                this.reSet();
            } else {
                JOptionPane.showInternalMessageDialog(this, "Sửa thất bại");
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void reSet() {
        txtMaVoucher.setText("");
        txtTenVoucher.setText("");
        txtGiaTriToiThieu.setText("");
        txtGiaTriToiDa.setText("");
        txtSoLuong.setText("");
        txtDieuKien.setText("");
        cbbLoai.setSelectedItem(null);
        txtSoLuong.setText("");
        dateNgayBatDau.setDate(null);
        dateNgayKetThuc.setDate(null);
        dateLocNgayBatDau.setDate(null);
        dateLocNgayKetThuc.setDate(null);
        showDataTable(repo.getAll());
    }

    private void runThreadCheckNgayKetThuc() {
        Thread checkNgayKetThuc = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("hello");
                    Date currentDate = new Date();
                    for (PhieuGiamGia pgg : repo.getAll()) {
                        System.out.println(pgg.getMa());
                        if (currentDate.after(pgg.getNgayKetThuc())) {
                            pgg.setTrangThai("Hết hạn");
                            System.out.println(pgg.getMa());
                            repo.updateNgayHetHan(pgg.getId());
                            showDataTable(repo.getAll());
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        checkNgayKetThuc.start();
    }
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reSet();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed

        String keyword = txtSearch.getText();
        showDataTable(repo.search(keyword,
                cbbLoaiSearch.getSelectedItem().toString(), cbbTrangThai.getSelectedItem().toString()));
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
        Date sartDate = dateLocNgayBatDau.getDate();
        Date endDate = dateLocNgayKetThuc.getDate();
        showDataTable(repo.locTheoKhoangThoiGian(sartDate, endDate));
    }//GEN-LAST:event_btnLocActionPerformed

    private void cbbLoaiSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLoaiSearchActionPerformed
        // TODO add your handling code here:
        if (cbbLoaiSearch.getSelectedIndex() == 0) {
            showDataTable(repo.getAll());
        }
    }//GEN-LAST:event_cbbLoaiSearchActionPerformed

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed
        // TODO add your handling code here:
        if (cbbTrangThai.getSelectedIndex() == 0) {
            showDataTable(repo.getAll());
        }
    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String keyword = txtSearch.getText();
        showDataTable(repo.search(keyword,
                cbbLoaiSearch.getSelectedItem().toString(), cbbTrangThai.getSelectedItem().toString()));
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tbPhieuGiamGiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhieuGiamGiaMousePressed
        // TODO add your handling code here:
          int index = tbPhieuGiamGia.getSelectedRow();
        deatailPhieuGiamGia(index);
        PhieuGiamGia pgg = repo.getAll().get(index);
        if (evt.getClickCount() == 2) {
            String ma = tbPhieuGiamGia.getValueAt(index, 1).toString();
            String ten = tbPhieuGiamGia.getValueAt(index, 2).toString();
            int soLuong = Integer.valueOf(tbPhieuGiamGia.getValueAt(index, 3).toString());
            String loai = tbPhieuGiamGia.getValueAt(index, 4).toString();
            double gtriTT = Double.valueOf(tbPhieuGiamGia.getValueAt(index, 5).toString());
            double gtriTD = Double.valueOf(tbPhieuGiamGia.getValueAt(index, 6).toString());
            Date ngayBD = null;
            Date ngayKT = null;
            try {
                String ngayBD0 = tbPhieuGiamGia.getValueAt(index, 7).toString();
                String ngayKT0 = tbPhieuGiamGia.getValueAt(index, 8).toString();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                ngayBD = format.parse(ngayBD0);
                ngayKT = format.parse(ngayKT0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String trangThai = tbPhieuGiamGia.getValueAt(index, 9).toString();
            double dieuKien = pgg.getDieuKien();

            openDetail(ma, ten, loai, gtriTT, gtriTD, ngayBD, ngayKT, soLuong, dieuKien, trangThai);
        }
    }//GEN-LAST:event_tbPhieuGiamGiaMousePressed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        try {
            String path = "C:\\Users\\ThuPC\\Documents\\DA1";
            JFileChooser jFileChooser = new JFileChooser(path);
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = (Sheet) wb.createSheet("Account");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tbPhieuGiamGia.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tbPhieuGiamGia.getColumnName(i));
                }
                for (int j = 0; j < tbPhieuGiamGia.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tbPhieuGiamGia.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tbPhieuGiamGia.getValueAt(j, k) != null) {
                            cell.setCellValue(tbPhieuGiamGia.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                EventStream.openFile(saveFile.toPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXuatExcelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JComboBox<String> cbbLoai;
    private javax.swing.JComboBox<String> cbbLoaiSearch;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private com.toedter.calendar.JDateChooser dateLocNgayBatDau;
    private com.toedter.calendar.JDateChooser dateLocNgayKetThuc;
    private com.toedter.calendar.JDateChooser dateNgayBatDau;
    private com.toedter.calendar.JDateChooser dateNgayKetThuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbPhieuGiamGia;
    private com.raven.util.TextField txtDieuKien;
    private com.raven.util.TextField txtGiaTriToiDa;
    private com.raven.util.TextField txtGiaTriToiThieu;
    private com.raven.util.TextField txtMaVoucher;
    private com.raven.util.TextField txtSearch;
    private com.raven.util.TextField txtSoLuong;
    private com.raven.util.TextField txtTenVoucher;
    // End of variables declaration//GEN-END:variables

}
