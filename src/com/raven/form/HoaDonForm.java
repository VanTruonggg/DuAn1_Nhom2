/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.raven.qrcode.QrCodeHoaDon;
import com.raven.qrcode.QrCodeListener;
import com.raven.repository.HoaDonChiTietRepository;
import com.raven.repository.HoaDonRepository;
import com.raven.repository.LichSuHoaDonRepository;
import com.raven.response.HoaDonChiTietResponse;
import com.raven.response.HoaDonResponse;
import com.raven.response.LichSuHoaDonResponse;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import raven.toast.Notifications;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;

/**
 *
 * @author ADMIN
 */
public class HoaDonForm extends javax.swing.JPanel implements QrCodeListener {

    /**
     * Creates new form HoaDon
     */
    private final HoaDonRepository hoaDonRepository;

    private final HoaDonChiTietRepository hoaDonChiTietRepository;

    private final LichSuHoaDonRepository lichSuHoaDonRepository;

    private final DefaultTableModel dtmHoaDon;

    private final DefaultTableModel dtmHoaDonChiTiet;

    private final DefaultTableModel dtmLichSuHoaDon;

    private ArrayList<HoaDonChiTietResponse> list = new ArrayList<>();

    private ArrayList<HoaDonResponse> lists = new ArrayList<>();

    private int page = 1;
    private int totalPage = 1;
    private final int limit = 3;

    public HoaDonForm() {
        initComponents();
        hoaDonRepository = new HoaDonRepository();
        hoaDonChiTietRepository = new HoaDonChiTietRepository();
        lichSuHoaDonRepository = new LichSuHoaDonRepository();
        dtmHoaDon = (DefaultTableModel) tbHoaDon.getModel();
        dtmHoaDonChiTiet = (DefaultTableModel) tbHoaDonChiTiet.getModel();
        dtmLichSuHoaDon = (DefaultTableModel) tbLichSuHoaDon.getModel();

        ((AbstractDocument) txtGiaTu.getDocument()).setDocumentFilter(new NumberOnlyFilter()); // không cho nhập kí tự
        ((AbstractDocument) txtGiaDen.getDocument()).setDocumentFilter(new NumberOnlyFilter()); // không cho nhập kí tự

        loadPage();
        showTableHoaDon(hoaDonRepository.getAllAndPaging(page, limit));

    }

//    private void showTableHoaDon(ArrayList<HoaDonResponse> lists) {
//        dtmHoaDon.setRowCount(0);
//        AtomicInteger index = new AtomicInteger(1);
//        lists.forEach(s -> dtmHoaDon.addRow(new Object[]{
//            index.getAndIncrement(), s.getMaHD(), s.getMaNV(),
//            s.getTenKH(), s.getDiaChiKH(), s.getSdtKH(), s.getNgayTao(), s.getTongTien(), s.getTrangThaiHD() == 1 ? "Chưa Thanh Toán" : (s.getTrangThaiHD() == 2 ? "Đã Thanh Toán" : "Đã Hủy")
//        }));
//    }
    public void showTableHoaDon(ArrayList<HoaDonResponse> lists) {
        dtmHoaDon.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        lists.forEach(s -> {
            System.out.println("Adding row: " + s.getMaHD()); // Debug: Kiểm tra dữ liệu được thêm vào bảng
            String formattedTongTien = currencyFormatter.format(s.getTongTien());
            String formattedNgayTao = dateFormatter.format(s.getNgayTao());
            dtmHoaDon.addRow(new Object[]{
                index.getAndIncrement(), s.getMaHD(), s.getMaNV(),
                s.getTenKH(), s.getDiaChiKH(), s.getSdtKH(), formattedNgayTao, formattedTongTien,
                s.getTrangThaiHD() == 1 ? "Chưa Thanh Toán" : (s.getTrangThaiHD() == 2 ? "Đã Thanh Toán" : "Đã Hủy")
            });
        });
    }

    public void showTableHoaDonChiTiet(ArrayList<HoaDonChiTietResponse> lists) {
        dtmHoaDonChiTiet.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        lists.forEach(s -> {
            String formattedDonGia = currencyFormatter.format(s.getDonGia());
            String formattedThanhTien = currencyFormatter.format(s.getThanhTien());
            dtmHoaDonChiTiet.addRow(new Object[]{
                index.getAndIncrement(), s.getMaHd(), s.getMaCtsp(), s.getTenSP(), s.getSoLuong(), formattedDonGia,
                formattedThanhTien, s.getChatLieu(), s.getKichThuoc(),
                s.getMauSac(), s.getDeGiay(), s.getNSX(), s.getHang(), s.getCoGiay(), s.getKhoiLuong(), s.getXuatXu()
            });
        });
    }

    public void showTableLichSuHoaDon(ArrayList<LichSuHoaDonResponse> lists) {
        dtmLichSuHoaDon.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        lists.forEach(s -> {
            String formattedNgayCapNhat = dateFormatter.format(s.getNgayCapNhat());
            dtmLichSuHoaDon.addRow(new Object[]{
                index.getAndIncrement(), s.getMaNV(), formattedNgayCapNhat, s.getHanhDongNguoiThaoTac()
            });
        });
    }

    private void loadPage() {
        dtmHoaDon.setRowCount(0);

        try {
            int rowCount = hoaDonRepository.getTotalRowCount();
            totalPage = (int) Math.ceil(rowCount / (double) limit);

            ArrayList<HoaDonResponse> lists = hoaDonRepository.getAllAndPaging(page, limit);
            showTableHoaDon(lists);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        lblPage.setText(page + " / " + totalPage);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btSearch = new javax.swing.JButton();
        btQuetQR = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        btClear = new javax.swing.JButton();
        btLoc = new javax.swing.JButton();
        btInHoaDon = new javax.swing.JButton();
        btXuatExel = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btLast = new javax.swing.JButton();
        btNext = new javax.swing.JButton();
        btLastMin = new javax.swing.JButton();
        btNextMax = new javax.swing.JButton();
        txtGiaTu = new textfield.TextField();
        cboHinhThucThanhToan = new combobox.Combobox();
        cboTinhTrangHoaDon = new combobox.Combobox();
        txtGiaDen = new textfield.TextField();
        txtSearch2 = new textfield.TextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbHoaDonChiTiet = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbLichSuHoaDon = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 204, 255));
        jLabel1.setText("Hóa Đơn");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn"));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Search");

        btSearch.setBackground(new java.awt.Color(52, 167, 160));
        btSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/9042816_page_search_icon.png"))); // NOI18N
        btSearch.setText("Search");
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });

        btQuetQR.setBackground(new java.awt.Color(52, 167, 160));
        btQuetQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/9104276_scan_barcode_qr_qr code_scanner_icon.png"))); // NOI18N
        btQuetQR.setText("Quét QR");
        btQuetQR.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btQuetQRActionPerformed(evt);
            }
        });

        jLabel5.setText("Tìm Theo Giá");

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Mã nhân viên", "Tên khách hàng", "Địa chỉ", "SDT", "Ngày tạo", "Tổng Tiền", "Trạng Thái"
            }
        ));
        tbHoaDon.setGridColor(new java.awt.Color(255, 255, 255));
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHoaDon);

        btClear.setBackground(new java.awt.Color(52, 167, 160));
        btClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/211882_refresh_icon.png"))); // NOI18N
        btClear.setText("Làm mới");
        btClear.setToolTipText("");
        btClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btClearActionPerformed(evt);
            }
        });

        btLoc.setBackground(new java.awt.Color(102, 204, 255));
        btLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/9044379_filter_edit_icon.png"))); // NOI18N
        btLoc.setText("Lọc");
        btLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLocActionPerformed(evt);
            }
        });

        btInHoaDon.setBackground(new java.awt.Color(52, 167, 160));
        btInHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/bill.png"))); // NOI18N
        btInHoaDon.setText("In Hóa Đơn");
        btInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInHoaDonActionPerformed(evt);
            }
        });

        btXuatExel.setBackground(new java.awt.Color(102, 204, 255));
        btXuatExel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/img/excel.png"))); // NOI18N
        btXuatExel.setText("Xuất Exel");
        btXuatExel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXuatExelActionPerformed(evt);
            }
        });

        lblPage.setText("0");

        btLast.setText("<");
        btLast.setEnabled(false);
        btLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLastActionPerformed(evt);
            }
        });

        btNext.setText(">");
        btNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNextActionPerformed(evt);
            }
        });

        btLastMin.setText("<<");
        btLastMin.setEnabled(false);
        btLastMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLastMinActionPerformed(evt);
            }
        });

        btNextMax.setText(">>");
        btNextMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNextMaxActionPerformed(evt);
            }
        });

        txtGiaTu.setText("textField1");

        txtGiaDen.setText("textField1");

        txtSearch2.setText("textField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(17, 17, 17))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btLastMin, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btLast, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btNextMax, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(405, 405, 405)
                .addComponent(btInHoaDon)
                .addGap(56, 56, 56)
                .addComponent(btXuatExel)
                .addGap(60, 60, 60))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(398, 398, 398)
                        .addComponent(btSearch)
                        .addGap(134, 134, 134)
                        .addComponent(btQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(153, 153, 153)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaTu, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(txtGiaDen, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(btClear, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(btLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(64, 64, 64)
                    .addComponent(cboTinhTrangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1186, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(141, 141, 141)
                    .addComponent(txtSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1025, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btSearch)
                    .addComponent(btQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btClear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(btLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGiaTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btNext)
                                .addComponent(lblPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btLast)
                                .addComponent(btLastMin)
                                .addComponent(btNextMax))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btInHoaDon)
                                .addComponent(btXuatExel)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(76, 76, 76)
                    .addComponent(cboTinhTrangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(285, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(23, 23, 23)
                    .addComponent(txtSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(334, Short.MAX_VALUE)))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn Chi Tiết"));

        tbHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HD", "Mã SPCT", "Tên sản phẩm", "Số lượng", "Đơn Giá", "Tổng tiền", "Chất Liệu", "Kích thước", "Màu sắc", "Đế Giày", "NSX", "Hãng", "Cổ Giầy", "Khối Lượng", "Xuất Xứ"
            }
        ));
        jScrollPane2.setViewportView(tbHoaDonChiTiet);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lịch Sử Hóa Đơn"));

        tbLichSuHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã NV", "Ngày", "Hành động"
            }
        ));
        jScrollPane3.setViewportView(tbLichSuHoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(651, 651, 651)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hóa Đơn", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1414, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btClearActionPerformed

        clearForm();
        showTableHoaDon(hoaDonRepository.getAllAndPaging(page, limit));
    }//GEN-LAST:event_btClearActionPerformed

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked

        int id = hoaDonRepository.getAll().get(tbHoaDon.getSelectedRow()).getId();
        showTableHoaDonChiTiet(hoaDonChiTietRepository.getAll(id));
        showTableLichSuHoaDon(lichSuHoaDonRepository.getAll(id));
    }//GEN-LAST:event_tbHoaDonMouseClicked

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed

        if (checkSearch()) {
//            showTableHoaDon(hoaDonRepository.search(txtSearch.getText().trim()));
            showTableHoaDon(hoaDonRepository.searchAndPaging(txtGiaTu.getText().trim(), page, limit));

        }
//        loadPage();
    }//GEN-LAST:event_btSearchActionPerformed

    private void btLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLocActionPerformed
        // TODO add your handling code here:
        if (txtGiaTu.getText().trim().equalsIgnoreCase("") || txtGiaDen.getText().trim().equalsIgnoreCase("")) {
            txtGiaTu.setText("0");
            txtGiaDen.setText("10000000000");

            txtGiaTu.setForeground(new Color(255, 255, 255));
            txtGiaDen.setForeground(new Color(255, 255, 255));
        }

        if (checkLoc()) {
//            showTableHoaDon(hoaDonRepository.loc(cboTinhTrangHoaDon.getSelectedIndex(), cboHinhThucThanhToan.getSelectedIndex(), Double.valueOf(txtGiaTu.getText().trim()), Double.valueOf(txtGiaDen.getText().trim())));
            showTableHoaDon(hoaDonRepository.locAndPaging(cboTinhTrangHoaDon.getSelectedIndex(), cboHinhThucThanhToan.getSelectedIndex(), Double.valueOf(txtGiaTu.getText().trim()), Double.valueOf(txtGiaDen.getText().trim()), page, limit));

        }

    }//GEN-LAST:event_btLocActionPerformed

    private void btNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNextActionPerformed

        if (page < totalPage) {
            page++;
            loadPage();
        }
        checkButtonPage();
    }//GEN-LAST:event_btNextActionPerformed

    private void btLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLastActionPerformed

        if (page > 1) {
            page--;
            loadPage();
        }
        checkButtonPage();
    }//GEN-LAST:event_btLastActionPerformed

    private void btLastMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLastMinActionPerformed

        page = 1;
        loadPage();
        checkButtonPage();
    }//GEN-LAST:event_btLastMinActionPerformed

    private void btNextMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNextMaxActionPerformed

        page = totalPage;
        loadPage();
        checkButtonPage();
    }//GEN-LAST:event_btNextMaxActionPerformed

    private void cboTinhTrangHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTinhTrangHoaDonActionPerformed

        if (cboTinhTrangHoaDon.getSelectedIndex() == 0) {
            showTableHoaDon(hoaDonRepository.getAllAndPaging(page, limit));
        }
    }//GEN-LAST:event_cboTinhTrangHoaDonActionPerformed

    private void btXuatExelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXuatExelActionPerformed

        int check = JOptionPane.showConfirmDialog(this, "Bạn chắn chắn muốn thêm ?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            Workbook workbook = new XSSFWorkbook();
            try {
                Sheet sheet1 = workbook.createSheet("HoaDon");
                exportTableToSheet(tbHoaDon, sheet1);

                Sheet sheet2 = workbook.createSheet("HoaDonChiTiet");
                exportTableToSheet(tbHoaDonChiTiet, sheet2);

                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    workbook.write(outputStream);
                }
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Export thành công");
                // JOptionPane.showMessageDialog(this, "Export thành công");
            } catch (Exception e) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Export thất bại");
                // JOptionPane.showMessageDialog(this, "Export thất bại");
                e.printStackTrace();
            } finally {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btXuatExelActionPerformed

    private void txtGiaTuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiaTuFocusGained
        // TODO add your handling code here:
        txtGiaTu.setText("");
        txtGiaTu.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_txtGiaTuFocusGained

    private void txtGiaDenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiaDenFocusGained
        // TODO add your handling code here:
        txtGiaDen.setText("");
        txtGiaDen.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_txtGiaDenFocusGained

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        // TODO add your handling code here:
        txtGiaTu.setText("");
    }//GEN-LAST:event_txtSearchFocusGained

    private void btQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btQuetQRActionPerformed
        // TODO add your handling code here:
        QrCodeHoaDon qrCodeHoaDon = new QrCodeHoaDon();
        qrCodeHoaDon.setQrCodeListener(this);
        qrCodeHoaDon.setVisible(true);
    }//GEN-LAST:event_btQuetQRActionPerformed

    private void btInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInHoaDonActionPerformed
//        int check = JOptionPane.showConfirmDialog(this, "Bạn chắn chắn muốn in hóa đơn ?");
//        if (check != JOptionPane.YES_OPTION) {
//            return;
//        }
//
//        String directoryPath = "";
//        JFileChooser j = new JFileChooser();
//        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//
//        int x = j.showSaveDialog(this);
//        if (x == JFileChooser.APPROVE_OPTION) {
//            directoryPath = j.getSelectedFile().getPath();
//        } else {
//            return; // Người dùng hủy bỏ
//        }
//
//        String fileName = JOptionPane.showInputDialog(this, "Nhập tên file (không cần .pdf):");
//        if (fileName == null || fileName.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Tên file không hợp lệ!");
//            return;
//        }
//
//        String filePath = directoryPath + "/" + fileName + ".pdf";
//
//        Document doc = new Document();
//        try {
//            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
//
//            doc.open();
//
//            // Thêm tiêu đề
//            Font titleFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
//
//            Paragraph title = new Paragraph("BILL OF SALE", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            doc.add(title);
//
//            doc.add(new Paragraph(" ")); // Thêm khoảng trống
//
//            // Thêm thông tin cửa hàng
//            Paragraph storeInfo = new Paragraph("GROUP2.com\nNo 00000 Address Line One\nAddress Line 02 SRI LANKA\nwww.facebook.com/SD19310\n+947000000000");
//            storeInfo.setAlignment(Element.ALIGN_CENTER);
//            doc.add(storeInfo);
//
//            doc.add(new Paragraph(" ")); // Thêm khoảng trống
//
//            // Thêm bảng thông tin hóa đơn
//            PdfPTable table = new PdfPTable(8);
//            table.setWidthPercentage(100);
//            table.setSpacingBefore(10f);
//            table.setSpacingAfter(10f);
//
//            float[] columnWidths = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
//            table.setWidths(columnWidths);
//
//            PdfPCell cell1 = new PdfPCell(new Paragraph("STT"));
//            PdfPCell cell2 = new PdfPCell(new Paragraph("Code Bill"));
//            PdfPCell cell3 = new PdfPCell(new Paragraph("Employee Code"));
//            PdfPCell cell4 = new PdfPCell(new Paragraph("Customer name"));
//            PdfPCell cell5 = new PdfPCell(new Paragraph("Address"));
//            PdfPCell cell6 = new PdfPCell(new Paragraph("Phone Number"));
//            PdfPCell cell7 = new PdfPCell(new Paragraph("Date Created"));
//            PdfPCell cell8 = new PdfPCell(new Paragraph("Total Amount"));
//
//            table.addCell(cell1);
//            table.addCell(cell2);
//            table.addCell(cell3);
//            table.addCell(cell4);
//            table.addCell(cell5);
//            table.addCell(cell6);
//            table.addCell(cell7);
//            table.addCell(cell8);
//
//            for (int i = 0; i < tbHoaDon.getRowCount(); i++) {
//                String stt = tbHoaDon.getValueAt(i, 0).toString();
//                String codeBill = tbHoaDon.getValueAt(i, 1).toString();
//                String employeeCode = tbHoaDon.getValueAt(i, 2).toString();
//                String customerName = tbHoaDon.getValueAt(i, 3).toString();
//                String address = tbHoaDon.getValueAt(i, 4).toString();
//                String phoneNumber = tbHoaDon.getValueAt(i, 5).toString();
//                String dateCreated = tbHoaDon.getValueAt(i, 6).toString();
//                String totalAmount = tbHoaDon.getValueAt(i, 7).toString();
//
//                table.addCell(stt);
//                table.addCell(codeBill);
//                table.addCell(employeeCode);
//                table.addCell(customerName);
//                table.addCell(address);
//                table.addCell(phoneNumber);
//                table.addCell(dateCreated);
//                table.addCell(totalAmount);
//            }
//
//            doc.add(table);
//
//            // Thêm QR code  \src\com\raven\icons\img
//            Image qrCode = Image.getInstance("src/com/raven/icons/img/qrcode1.jpg");
//            qrCode.setAlignment(Element.ALIGN_CENTER);
//            doc.add(qrCode);
//
//            // Thêm lời cảm ơn
//            Paragraph thankYou = new Paragraph("THANK YOU COME AGAIN\n"
//                    + "★★★★**★★★★★★★★＊★★★★★\n"
//                    + "SOFTWARE BY: GROUP 2\n"
//                    + "CONTACT: group2@SD19310.com");
//            thankYou.setAlignment(Element.ALIGN_CENTER);
//            doc.add(thankYou);
//
//        } catch (FileNotFoundException | DocumentException ex) {
//            Logger.getLogger(HoaDonForm.class.getName()).log(Level.SEVERE, null, ex);
////        } catch (BadElementException ex) {
////            Logger.getLogger(HoaDonForm.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(HoaDonForm.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            doc.close();
//        }
//
//        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Export thành công");

        int check = JOptionPane.showConfirmDialog(this, "Bạn chắn chắn muốn in hóa đơn ?");
        if (check != JOptionPane.YES_OPTION) {
            return;
        }

        String directoryPath = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int x = j.showSaveDialog(this);
        if (x == JFileChooser.APPROVE_OPTION) {
            directoryPath = j.getSelectedFile().getPath();
        } else {
            return; // Người dùng hủy bỏ
        }

        String fileName = JOptionPane.showInputDialog(this, "Nhập tên file (không cần .pdf):");
        if (fileName == null || fileName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên file không hợp lệ!");
            return;
        }

        String filePath = directoryPath + "/" + fileName + ".pdf";

        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));

            doc.open();
//            Font font = FontFactory.getFont(FontFactory.HELVETICA, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // Thêm tiêu đề
            Font titleFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);

            Paragraph title = new Paragraph("BILL OF SALE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);

            doc.add(new Paragraph(" ")); // Thêm khoảng trống

            // Thêm thông tin cửa hàng
            Paragraph storeInfo = new Paragraph("GROUP2.com\nNo 00000 Address Line One\nAddress Line 02 SRI LANKA\nwww.facebook.com/SD19310\n+947000000000");
            storeInfo.setAlignment(Element.ALIGN_CENTER);
            doc.add(storeInfo);

            doc.add(new Paragraph(" ")); // Thêm khoảng trống

            // Thêm bảng thông tin hóa đơn
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
            table.setWidths(columnWidths);

            BaseFont baseFont = BaseFont.createFont("src/com/raven/style/font-times-new-roman/times-new-roman-14.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12);
            
            PdfPCell cell1 = new PdfPCell(new Paragraph("STT", font));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Code Bill", font));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Employee Code", font));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Customer name", font));
            PdfPCell cell5 = new PdfPCell(new Paragraph("Address", font));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Phone Number", font));
            PdfPCell cell7 = new PdfPCell(new Paragraph("Date Created", font));
            PdfPCell cell8 = new PdfPCell(new Paragraph("Total Amount", font));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);

            for (int i = 0; i < tbHoaDon.getRowCount(); i++) {
                String stt = tbHoaDon.getValueAt(i, 0).toString();
                String codeBill = tbHoaDon.getValueAt(i, 1).toString();
                String employeeCode = tbHoaDon.getValueAt(i, 2).toString();
                String customerName = tbHoaDon.getValueAt(i, 3).toString();
                String address = tbHoaDon.getValueAt(i, 4).toString();
                String phoneNumber = tbHoaDon.getValueAt(i, 5).toString();
                String dateCreated = tbHoaDon.getValueAt(i, 6).toString();
                String totalAmount = tbHoaDon.getValueAt(i, 7).toString();

                table.addCell(new PdfPCell(new Paragraph(stt, font)));
                table.addCell(new PdfPCell(new Paragraph(codeBill, font)));
                table.addCell(new PdfPCell(new Paragraph(employeeCode, font)));
                table.addCell(new PdfPCell(new Paragraph(customerName, font)));
                table.addCell(new PdfPCell(new Paragraph(address, font)));
                table.addCell(new PdfPCell(new Paragraph(phoneNumber, font)));
                table.addCell(new PdfPCell(new Paragraph(dateCreated, font)));
                table.addCell(new PdfPCell(new Paragraph(totalAmount, font)));
            }

            doc.add(table);

            // Thêm QR code  \src\com\raven\icons\img
            Image qrCode = Image.getInstance("src/com/raven/icon/img/qrcode1.jpg");
            qrCode.setAlignment(Element.ALIGN_CENTER);
            doc.add(qrCode);

            // Thêm lời cảm ơn
            Paragraph thankYou = new Paragraph("THANK YOU COME AGAIN\n"
                    + "★★★★**★★★★★★★★＊★★★★★\n"
                    + "SOFTWARE BY: GROUP 2\n"
                    + "CONTACT: group2@SD19310.com");
            thankYou.setAlignment(Element.ALIGN_CENTER);
            doc.add(thankYou);

        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(HoaDonForm.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (BadElementException ex) {
//            Logger.getLogger(HoaDonForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HoaDonForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            doc.close();
        }

        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Export thành công");

    }//GEN-LAST:event_btInHoaDonActionPerformed

    private boolean checkSearch() {

        if (txtGiaTu.getText().trim().equals("")) {
//            JOptionPane.showMessageDialog(this, "tìm kiếm đang trống");
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "tìm kiếm đang trống");
            return false;
        }

        if (txtGiaTu.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "tìm kiếm đang trống");
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Nhập đúng định dạng");
            return false;
        }

        return true;
    }

    private boolean checkLoc() {

        Double giaMin = Double.valueOf(txtGiaTu.getText().trim());
        Double giaMax = Double.valueOf(txtGiaDen.getText().trim());

        if (giaMin > giaMax) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "giá min không thể nhỏ hơn giá max");
//            JOptionPane.showMessageDialog(this, "giá min không thể nhỏ hơn giá max");
            return false;
        }

        return true;
    }

    private void checkButtonPage() {
        if (page == 1) {
            btLast.setEnabled(false);
        } else {
            btLast.setEnabled(true);
        }

        if (page >= totalPage) {
            btNext.setEnabled(false);
        } else {
            btNext.setEnabled(true);
        }

        if (page == 1) {
            btLastMin.setEnabled(false);
        } else {
            btLastMin.setEnabled(true);
        }

        if (page >= totalPage) {
            btNextMax.setEnabled(false);
        } else {
            btNextMax.setEnabled(true);
        }
    }

    private void clearForm() {
        cboHinhThucThanhToan.setSelectedIndex(-1);
        cboTinhTrangHoaDon.setSelectedIndex(-1);

        txtGiaTu.setText("");
        txtGiaDen.setText("");
        txtGiaTu.setText("");
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Clear thành công");
    }

    private void exportTableToSheet(JTable table, Sheet sheet) {
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
        }

        // Tạo các dòng dữ liệu
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < columnCount; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(table.getValueAt(i, j).toString());
            }
        }
    }

    @Override
    public void onQrCodeRead(String qrCode) {
        txtGiaTu.setText(qrCode);
        System.out.println("QR Code read: " + qrCode); // Debug: Kiểm tra mã QR được quét
        updateTable(qrCode);
    }

    private void updateTable(String qrCode) {
      showTableHoaDon(hoaDonRepository.searchAndPaging(txtGiaTu.getText().trim(), page, limit));
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "QR Code thành công");
    }

    // class set kí tự extends tới DocumentFilter/  chỉ cho nhập số
    class NumberOnlyFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d*")) { // Allow empty string and digits
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d*")) { // Allow empty string and digits
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClear;
    private javax.swing.JButton btInHoaDon;
    private javax.swing.JButton btLast;
    private javax.swing.JButton btLastMin;
    private javax.swing.JButton btLoc;
    private javax.swing.JButton btNext;
    private javax.swing.JButton btNextMax;
    private javax.swing.JButton btQuetQR;
    private javax.swing.JButton btSearch;
    private javax.swing.JButton btXuatExel;
    private combobox.Combobox cboHinhThucThanhToan;
    private combobox.Combobox cboTinhTrangHoaDon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblPage;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbHoaDonChiTiet;
    private javax.swing.JTable tbLichSuHoaDon;
    private textfield.TextField txtGiaDen;
    private textfield.TextField txtGiaTu;
    private textfield.TextField txtSearch2;
    // End of variables declaration//GEN-END:variables
}
