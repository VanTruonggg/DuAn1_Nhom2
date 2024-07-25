/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import AddtionalForm.*;
import AddtionalForm.models.ThuocTinh;
import com.raven.repository.sanpham.repo_chitietsanpham;
import com.raven.repository.sanpham.repo_sanpham;
import interfaces.interface_repo_thuoctinh;
import interfaces.interface_thuoctinh;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import models.sanpham_container.thuoctinh.ChatLieu;
import models.sanpham_container.thuoctinh.CoGiay;
import models.sanpham_container.thuoctinh.DeGiay;
import models.sanpham_container.thuoctinh.Hang;
import models.sanpham_container.thuoctinh.KhoiLuong;
import models.sanpham_container.thuoctinh.KichThuoc;
import models.sanpham_container.thuoctinh.MauSac;
import models.sanpham_container.thuoctinh.NhaSanXuat;
import models.sanpham_container.thuoctinh.XuatXu;
import com.raven.repository.sanpham.thuoctinh.repo_chatlieu;
import com.raven.repository.sanpham.thuoctinh.repo_cogiay;
import com.raven.repository.sanpham.thuoctinh.repo_degiay;
import com.raven.repository.sanpham.thuoctinh.repo_hang;
import com.raven.repository.sanpham.thuoctinh.repo_khoiluong;
import com.raven.repository.sanpham.thuoctinh.repo_kichthuoc;
import repositories.sanpham.thuoctinh.repo_mausac;
import com.raven.repository.sanpham.thuoctinh.repo_nsx;
import com.raven.repository.sanpham.thuoctinh.repo_xuatxu;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import com.raven.logic.HandleHinhAnh;
import com.raven.logic.RandomStringGenerator;
import com.raven.logic.ShowMessageCustom;
import com.raven.logic.Validate;
import java.awt.Component;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import models.sanpham_container.SanPham;
import models.sanpham_container.SanPhamChiTiet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.raven.logic.*;
import com.raven.qrcode.*;

import swing.*;

/**
 *
 * @author ADMIN
 */
public class SanPhamForm extends javax.swing.JPanel implements QrCodeListener {

    String maSPJoinCTSP = null;

    interface_repo_thuoctinh interface_repo_thuoctinh = null;
    interface_thuoctinh x = null;
    repo_sanpham repo_sanpham = new repo_sanpham();
    repo_chitietsanpham repo_chitietsanpham = new repo_chitietsanpham();

    ArrayList<Object> lists = null;
    ArrayList<SanPham> listSanPham;
    ArrayList<SanPhamChiTiet> listSPCT;

    Map<String, Boolean> listSPCTIsSelected = new LinkedHashMap<>();

    DefaultTableModel modelTblThuocTinh;
    DefaultTableModel modelTblSanPham;
    DefaultTableModel modelTblCTSP;
    TableRowSorter<TableModel> sorter, sorterSP, sorterTT;

    List<JComboBox<String>> comboBoxes = null;

    /**
     * Creates new form SanPham
     */
    public SanPhamForm() {
        initComponents();

        modelTblThuocTinh = (DefaultTableModel) tblThuocTinh.getModel();
        modelTblSanPham = (DefaultTableModel) tblSanPham.getModel();
        modelTblCTSP = (DefaultTableModel) tblCTSP.getModel();

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                suaSPCT(row);
            }

            @Override
            public void onDelete(int row) {
                removeSPCT(row);
            }

            @Override
            public void onView(int row) {

            }
        };

        tblCTSP.getColumnModel().getColumn(16).setCellRenderer(new TableActionCellRender());
        tblCTSP.getColumnModel().getColumn(16).setCellEditor(new TableActionCellEditor(event));

        JTableHeader headerCTSP = tblCTSP.getTableHeader();
        headerCTSP.setDefaultRenderer(new CustomHeaderRenderer(tblCTSP));
        JTableHeader headerSP = tblSanPham.getTableHeader();
        headerSP.setDefaultRenderer(new CustomHeaderRenderer(tblSanPham));
        JTableHeader headerTT = tblThuocTinh.getTableHeader();
        headerTT.setDefaultRenderer(new CustomHeaderRenderer(tblThuocTinh));

        rdConHangCTSP.setSelected(true);
        rdConHangSanPham.setSelected(true);

        loadListSanPhamToTable();
        loadListChiTietSanPhamToTable();

        for (SanPhamChiTiet x : listSPCT) {
            listSPCTIsSelected.put(x.getIdSPCT(), false);
            x.setUrlImgQrCode(RenderQRCode.renderQRCode(x.getIdSPCT()));
        }

        loadListThuocTinh(cboxHang, Hang.class, new repo_hang());
        loadListThuocTinh(cboxChatLieu, ChatLieu.class, new repo_chatlieu());
        loadListThuocTinh(cboxDeGiay, DeGiay.class, new repo_degiay());
        loadListThuocTinh(cboxKhoiLuong, KhoiLuong.class, new repo_khoiluong());
        loadListThuocTinh(cboxKichThuoc, KichThuoc.class, new repo_kichthuoc());
        loadListThuocTinh(cboxNhaSanXuat, NhaSanXuat.class, new repo_nsx());
        loadListThuocTinh(cboxXuatXu, XuatXu.class, new repo_xuatxu());
        loadListThuocTinh(cboxCoGiay, CoGiay.class, new repo_cogiay());
        loadListThuocTinh(cboxMauSac, MauSac.class, new repo_mausac());

        sorter = new TableRowSorter<>(modelTblCTSP);
        tblCTSP.setRowSorter(sorter);

        sorterSP = new TableRowSorter<>(modelTblSanPham);
        tblSanPham.setRowSorter(sorterSP);

        sorterTT = new TableRowSorter<>(modelTblThuocTinh);
        tblThuocTinh.setRowSorter(sorterTT);

        comboBoxes = Arrays.asList(cboxHang, cboxChatLieu, cboxDeGiay, cboxKhoiLuong,
                cboxKichThuoc, cboxNhaSanXuat, cboxXuatXu, cboxCoGiay, cboxMauSac);
        for (JComboBox<String> comboBox : comboBoxes) {
            comboBox.addActionListener(e -> applyFilters());
        }

        for (String i : getColumnNameSPCT()) {
            cboxSearchCTSP.addItem(i);
        }

        rdMauSac.setSelected(true);
        interface_repo_thuoctinh = new repo_mausac();
        x = new MauSac();
        loadListThuocTinhToTable();

        txtSearchSP.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(txtSearchSP.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(txtSearchSP.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(txtSearchSP.getText());
            }

            private void search(String text) {
                sorterSP.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2));
            }
        });

        tblCTSP.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 17) {
                Boolean checked = (Boolean) tblCTSP.getValueAt(row, column);
                String maSPCT = tblCTSP.getValueAt(row, 1).toString();
                listSPCTIsSelected.put(maSPCT, checked);

                checkBoxAll.setSelected(isAllListSPCTChecked());
            }
        });
    }

    public boolean isAllListSPCTChecked() {
        for (Map.Entry<String, Boolean> entry : listSPCTIsSelected.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            if (entry.getValue() == false) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getColumnNameSPCT() {
        TableColumnModel columnModel = tblCTSP.getColumnModel();
        ArrayList<String> columnNames = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String columnName = columnModel.getColumn(i).getHeaderValue().toString();
            columnNames.add(columnName);
        }
        return columnNames;
    }

    public void loadListThuocTinhToTable() {
        lists = interface_repo_thuoctinh.loadListThuocTinhFromDb();

        modelTblThuocTinh.setRowCount(0);
        int count = 1;
        for (Object i : lists) {
            Class<?> classOfI = i.getClass();
            Field[] fields = classOfI.getDeclaredFields();

            Object[] rowData = new Object[fields.length + 1];

            // Gán giá trị số thứ tự
            rowData[0] = count++;

            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    if (j + 1 == fields.length) {
                        if (fields[j].get(i).toString().equals("1")) {
                            rowData[j + 1] = "Có sẵn";
                        } else {
                            rowData[j + 1] = "Đã hết";
                        }
                        continue;
                    }
                    rowData[j + 1] = fields[j].get(i);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            modelTblThuocTinh.addRow(rowData);
        }
    }

    public void loadListSanPhamToTable() {
        int count = 0;
        listSanPham = repo_sanpham.loadListSanPhamFromDb();
        modelTblSanPham.setRowCount(0);
        for (SanPham i : listSanPham) {
            if (rdConHangSanPham.isSelected() && i.getTrangThai() == 1) {
                addSPRowToTable(i, ++count);
            } else if (rdHetHangSanPham.isSelected() && i.getTrangThai() == 0) {
                addSPRowToTable(i, ++count);
            } else if (rdAllSanPham.isSelected()) {
                addSPRowToTable(i, ++count);
            }
        }
    }

    public void addSPRowToTable(SanPham i, int count) {
        String trangThai;
        switch (i.getTrangThai()) {
            case 0:
                trangThai = "Hết hàng";
                break;
            case 1:
                trangThai = "Còn hàng";
                break;
            default:
                throw new AssertionError();
        }
        modelTblSanPham.addRow(new Object[]{
            count, i.getMaSp(), i.getTenSp(), i.getSoLuong(), trangThai
        });
    }

    public void loadListChiTietSanPhamToTable() {
        if (maSPJoinCTSP != null) {
            listSPCT = repo_chitietsanpham.getListSPCTJoinSP(maSPJoinCTSP);
        } else {
            listSPCT = repo_chitietsanpham.loadListSanPhamChiTietFromDb();
        }
        modelTblCTSP.setRowCount(0);
        int count = 0;
        for (SanPhamChiTiet i : listSPCT) {
            if (rdConHangCTSP.isSelected() && i.getTrangThai() == 1) {
                addCTSPRowToTable(i, ++count);
            } else if (rdHetHangCTSP.isSelected() && i.getTrangThai() == 0) {
                addCTSPRowToTable(i, ++count);
            }
        }
    }

    public void addCTSPRowToTable(SanPhamChiTiet i, int count) {
        String trangThai;
        switch (i.getTrangThai()) {
            case 0:
                trangThai = "Hết hàng";
                break;
            case 1:
                trangThai = "Còn hàng";
                break;
            default:
                throw new AssertionError();
        }
        modelTblCTSP.addRow(new Object[]{
            count, i.getIdSPCT(), i.getSanPham(), i.getHang(), i.getChatLieu(), i.getKichThuoc(),
            i.getMauSac(), i.getDeGiay(), i.getKhoiLuong(), i.getXuatXu(), i.getNhaSanXuat(), i.getCoGiay(),
            i.getDonGia(), i.getSoLuongTon(), i.getGhiChu(), trangThai});
        tblCTSP.setValueAt(listSPCTIsSelected.get(i.getIdSPCT()), count - 1, 17);
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

    public <T> int loadListThuocTinh(JComboBox<String> comboBox, Class<T> clazz, interface_repo_thuoctinh repo) {
        resetComboBox(comboBox);
        comboBox.addItem("---Tất cả---");
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

    private void applyFilters() {
        RowFilter<TableModel, Object> hangFilter = createRowFilter(cboxHang, 3);
        RowFilter<TableModel, Object> chatLieuFilter = createRowFilter(cboxChatLieu, 4);
        RowFilter<TableModel, Object> kichThuocFilter = createRowFilter(cboxKichThuoc, 5);
        RowFilter<TableModel, Object> mauSacFilter = createRowFilter(cboxMauSac, 6);
        RowFilter<TableModel, Object> deGiayFilter = createRowFilter(cboxDeGiay, 7);
        RowFilter<TableModel, Object> khoiLuongFilter = createRowFilter(cboxKhoiLuong, 8);
        RowFilter<TableModel, Object> xuatXuFilter = createRowFilter(cboxXuatXu, 9);
        RowFilter<TableModel, Object> nhaSanXuatFilter = createRowFilter(cboxNhaSanXuat, 10);
        RowFilter<TableModel, Object> coGiayFilter = createRowFilter(cboxCoGiay, 11);

        // Combine filters into a single filter
        RowFilter<TableModel, Object> compoundFilter = RowFilter.andFilter(
                java.util.Arrays.asList(hangFilter, chatLieuFilter, kichThuocFilter, mauSacFilter, deGiayFilter,
                        khoiLuongFilter, xuatXuFilter, nhaSanXuatFilter, coGiayFilter));

        sorter.setRowFilter(compoundFilter);
    }

    private RowFilter<TableModel, Object> createRowFilter(JComboBox<String> comboBox, int columnIndex) {
        String selectedFilter = (String) comboBox.getSelectedItem();
        txtSearchCTSP.setText("");
        if (selectedFilter.equals("---Tất cả---")) {
            return RowFilter.regexFilter("");
        } else {
            return RowFilter.regexFilter("(?i)" + selectedFilter, columnIndex);
        }
    }

    private void clearFilters() {
        sorter.setRowFilter(null);
        for (JComboBox i : comboBoxes) {
            i.setSelectedItem("---Tất cả---");
        }
    }

    // public void loadList
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        buttonGroupThuocTinh = new javax.swing.ButtonGroup();
        buttonGroupConHangHetHangCTSP = new javax.swing.ButtonGroup();
        buttonGroupSP = new javax.swing.ButtonGroup();
        menuBar = new MaterialTabbed();
        menuActivity2 = new javax.swing.JPanel();
        jScrollPane2 = new ScrollPaneWin11();
        tblCTSP = new javax.swing.JTable();
        lblHinhAnh = new javax.swing.JLabel();
        rdConHangCTSP = new javax.swing.JRadioButton();
        rdHetHangCTSP = new javax.swing.JRadioButton();
        btnThemSPCT = new swing.ButtonCustom();
        btnSuaCTSP = new swing.ButtonCustom();
        btnXoaCTSP = new swing.ButtonCustom();
        btnLamMoi = new swing.ButtonCustom();
        cboxSearchCTSP = new swing.Combobox();
        txtSearchCTSP = new swing.TextField();
        btnXuatExcel = new ButtonCustom();
        checkBoxAll = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        cboxKhoiLuong = new swing.Combobox();
        cboxDeGiay = new swing.Combobox();
        cboxMauSac = new swing.Combobox();
        cboxXuatXu = new swing.Combobox();
        cboxCoGiay = new swing.Combobox();
        cboxChatLieu = new swing.Combobox();
        cboxKichThuoc = new swing.Combobox();
        cboxNhaSanXuat = new swing.Combobox();
        cboxHang = new swing.Combobox();
        jLabel1 = new javax.swing.JLabel();
        btnHuyLoc = new swing.ButtonCustom();
        btnQuetQR = new swing.ButtonCustom();
        menuActivity3 = new javax.swing.JPanel();
        btnThemThuocTinh = new ButtonCustom();
        btnSuaThuocTinh = new ButtonCustom();
        btnXoaThuocTinh = new ButtonCustom();
        btnLamMoiThuocTinh = new ButtonCustom();
        rdChatLieu = new javax.swing.JRadioButton();
        rdMauSac = new javax.swing.JRadioButton();
        rdXuatXu = new javax.swing.JRadioButton();
        rdKichThuoc = new javax.swing.JRadioButton();
        rdDeGiay = new javax.swing.JRadioButton();
        rdNSX = new javax.swing.JRadioButton();
        rdCoGiay = new javax.swing.JRadioButton();
        rdKhoiLuong = new javax.swing.JRadioButton();
        rdHang = new javax.swing.JRadioButton();
        jScrollPane1 = new ScrollPaneWin11();
        tblThuocTinh = new javax.swing.JTable();
        txtThuocTinh = new swing.TextField();
        menuActivity1 = new javax.swing.JPanel();
        jScrollPane3 = new ScrollPaneWin11();
        tblSanPham = new javax.swing.JTable();
        btnThemSanPham = new ButtonCustom();
        btnSuaSanPham = new ButtonCustom();
        btnXoaSanPham = new ButtonCustom();
        btnLamMoiTxtSanPham = new ButtonCustom();
        rdConHangSanPham = new javax.swing.JRadioButton();
        rdHetHangSanPham = new javax.swing.JRadioButton();
        txtTenSP = new swing.TextField();
        rdAllSanPham = new javax.swing.JRadioButton();
        txtSearchSP = new swing.TextField();
        jLabel2 = new javax.swing.JLabel();

        jMenuBar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuBar2MouseClicked(evt);
            }
        });

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        menuBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBarMouseClicked(evt);
            }
        });

        menuActivity2.setBackground(new java.awt.Color(255, 255, 255));

        tblCTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã CTSP", "Tên SP", "Hãng", "Chất liệu", "Size", "Màu sắc", "Đế giày", "Khối lượng", "Xuất xứ", "Nhà sản xuất", "Cổ giày", "Giá", "SL", "Ghi chú", "Trạng thái", "Hành động", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblCTSP.setRowHeight(40);
        tblCTSP.setSelectionBackground(new java.awt.Color(37, 168, 246));
        tblCTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCTSP);
        if (tblCTSP.getColumnModel().getColumnCount() > 0) {
            tblCTSP.getColumnModel().getColumn(0).setResizable(false);
            tblCTSP.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblCTSP.getColumnModel().getColumn(1).setMaxWidth(70);
            tblCTSP.getColumnModel().getColumn(3).setMaxWidth(60);
            tblCTSP.getColumnModel().getColumn(4).setMaxWidth(70);
            tblCTSP.getColumnModel().getColumn(5).setMaxWidth(50);
            tblCTSP.getColumnModel().getColumn(6).setMaxWidth(60);
            tblCTSP.getColumnModel().getColumn(7).setMaxWidth(80);
            tblCTSP.getColumnModel().getColumn(8).setMaxWidth(80);
            tblCTSP.getColumnModel().getColumn(9).setMaxWidth(70);
            tblCTSP.getColumnModel().getColumn(10).setMaxWidth(100);
            tblCTSP.getColumnModel().getColumn(11).setMaxWidth(80);
            tblCTSP.getColumnModel().getColumn(12).setMaxWidth(80);
            tblCTSP.getColumnModel().getColumn(13).setMaxWidth(45);
            tblCTSP.getColumnModel().getColumn(17).setMaxWidth(70);
        }

        lblHinhAnh.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroupConHangHetHangCTSP.add(rdConHangCTSP);
        rdConHangCTSP.setText("Còn hàng");
        rdConHangCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdConHangCTSPActionPerformed(evt);
            }
        });

        buttonGroupConHangHetHangCTSP.add(rdHetHangCTSP);
        rdHetHangCTSP.setText("Hết hàng");
        rdHetHangCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdHetHangCTSPActionPerformed(evt);
            }
        });

        btnThemSPCT.setText("Thêm");
        btnThemSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPCTActionPerformed(evt);
            }
        });

        btnSuaCTSP.setText("Sửa");
        btnSuaCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaCTSPActionPerformed(evt);
            }
        });

        btnXoaCTSP.setText("Xóa");
        btnXoaCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCTSPActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        cboxSearchCTSP.setLabeText("Cột tìm kiếm");
        cboxSearchCTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboxSearchCTSPMouseClicked(evt);
            }
        });
        cboxSearchCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxSearchCTSPActionPerformed(evt);
            }
        });

        txtSearchCTSP.setLabelText("Tìm kiếm theo " + cboxSearchCTSP.getSelectedItem());
        txtSearchCTSP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchCTSPFocusGained(evt);
            }
        });
        txtSearchCTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchCTSPMouseClicked(evt);
            }
        });
        txtSearchCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchCTSPActionPerformed(evt);
            }
        });

        btnXuatExcel.setText("Xuất excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });

        checkBoxAll.setText("All");
        checkBoxAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxAllActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Lọc thông tin sản phảm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        cboxKhoiLuong.setLabeText("Khối lượng");

        cboxDeGiay.setLabeText("Đế giày");

        cboxMauSac.setLabeText("Màu sắc");

        cboxXuatXu.setLabeText("Xuất xứ");

        cboxCoGiay.setLabeText("Cổ giày");

        cboxChatLieu.setLabeText("Chất liệu");

        cboxKichThuoc.setLabeText("Size");

        cboxNhaSanXuat.setLabeText("Nhà sản xuất");

        cboxHang.setLabeText("Hãng");

        jLabel1.setFont(new java.awt.Font("JetBrains Mono", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Lọc");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnHuyLoc.setText("Hủy lọc");
        btnHuyLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cboxNhaSanXuat, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(cboxHang, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(cboxKhoiLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHuyLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboxChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(cboxCoGiay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboxDeGiay, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboxMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(cboxXuatXu, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(cboxKichThuoc, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxCoGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxKhoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHuyLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        btnQuetQR.setText("Quét QR");
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuActivity2Layout = new javax.swing.GroupLayout(menuActivity2);
        menuActivity2.setLayout(menuActivity2Layout);
        menuActivity2Layout.setHorizontalGroup(
            menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuActivity2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuActivity2Layout.createSequentialGroup()
                        .addComponent(cboxSearchCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearchCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                        .addComponent(btnXuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdConHangCTSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdHetHangCTSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkBoxAll)
                        .addGap(16, 16, 16))
                    .addGroup(menuActivity2Layout.createSequentialGroup()
                        .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98))))
            .addGroup(menuActivity2Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        menuActivity2Layout.setVerticalGroup(
            menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuActivity2Layout.createSequentialGroup()
                .addGap(0, 80, Short.MAX_VALUE)
                .addGroup(menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuActivity2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuActivity2Layout.createSequentialGroup()
                        .addGroup(menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuActivity2Layout.createSequentialGroup()
                                .addComponent(btnThemSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSuaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXoaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnQuetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)))
                .addGroup(menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cboxSearchCTSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchCTSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuActivity2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkBoxAll, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdHetHangCTSP)
                            .addComponent(rdConHangCTSP))
                        .addComponent(btnXuatExcel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuBar.addTab("Chi tiết sản phẩm", menuActivity2);

        menuActivity3.setBackground(new java.awt.Color(255, 255, 255));
        menuActivity3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuActivity3MouseClicked(evt);
            }
        });

        btnThemThuocTinh.setText("Thêm");
        btnThemThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemThuocTinhMouseClicked(evt);
            }
        });
        btnThemThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThuocTinhActionPerformed(evt);
            }
        });

        btnSuaThuocTinh.setText("Sửa");
        btnSuaThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaThuocTinhMouseClicked(evt);
            }
        });
        btnSuaThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaThuocTinhActionPerformed(evt);
            }
        });

        btnXoaThuocTinh.setText("Xóa");
        btnXoaThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaThuocTinhMouseClicked(evt);
            }
        });
        btnXoaThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaThuocTinhActionPerformed(evt);
            }
        });

        btnLamMoiThuocTinh.setText("Làm mới");
        btnLamMoiThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiThuocTinhActionPerformed(evt);
            }
        });

        buttonGroupThuocTinh.add(rdChatLieu);
        rdChatLieu.setText("Chất liệu");
        rdChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdChatLieuActionPerformed(evt);
            }
        });

        buttonGroupThuocTinh.add(rdMauSac);
        rdMauSac.setText("Màu sắc");
        rdMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdMauSacActionPerformed(evt);
            }
        });

        buttonGroupThuocTinh.add(rdXuatXu);
        rdXuatXu.setText("Xuất xứ");
        rdXuatXu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdXuatXuActionPerformed(evt);
            }
        });

        buttonGroupThuocTinh.add(rdKichThuoc);
        rdKichThuoc.setText("Kích thước");
        rdKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdKichThuocActionPerformed(evt);
            }
        });

        buttonGroupThuocTinh.add(rdDeGiay);
        rdDeGiay.setText("Đế giày");
        rdDeGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdDeGiayActionPerformed(evt);
            }
        });

        buttonGroupThuocTinh.add(rdNSX);
        rdNSX.setText("Nhà sản xuất");
        rdNSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNSXActionPerformed(evt);
            }
        });

        buttonGroupThuocTinh.add(rdCoGiay);
        rdCoGiay.setText("Cổ giày");
        rdCoGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdCoGiayActionPerformed(evt);
            }
        });

        buttonGroupThuocTinh.add(rdKhoiLuong);
        rdKhoiLuong.setText("Khối lượng");
        rdKhoiLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdKhoiLuongActionPerformed(evt);
            }
        });

        buttonGroupThuocTinh.add(rdHang);
        rdHang.setText("Hãng");
        rdHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdHangActionPerformed(evt);
            }
        });

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã thuộc tính", "Thông số thuộc tính", "Trạng thái"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThuocTinh);

        txtThuocTinh.setLabelText("Tên thuộc tính");
        txtThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThuocTinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuActivity3Layout = new javax.swing.GroupLayout(menuActivity3);
        menuActivity3.setLayout(menuActivity3Layout);
        menuActivity3Layout.setHorizontalGroup(
            menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuActivity3Layout.createSequentialGroup()
                .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuActivity3Layout.createSequentialGroup()
                        .addGap(466, 466, 466)
                        .addComponent(btnThemThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnSuaThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnXoaThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnLamMoiThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuActivity3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
                            .addGroup(menuActivity3Layout.createSequentialGroup()
                                .addComponent(txtThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdChatLieu)
                                    .addComponent(rdXuatXu)
                                    .addComponent(rdMauSac))
                                .addGap(23, 23, 23)
                                .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdKichThuoc)
                                    .addComponent(rdDeGiay)
                                    .addComponent(rdNSX))
                                .addGap(38, 38, 38)
                                .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdHang)
                                    .addComponent(rdCoGiay)
                                    .addComponent(rdKhoiLuong))))))
                .addContainerGap())
        );
        menuActivity3Layout.setVerticalGroup(
            menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuActivity3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdMauSac)
                    .addComponent(rdKichThuoc)
                    .addComponent(rdCoGiay))
                .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuActivity3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdChatLieu)
                            .addComponent(rdDeGiay)
                            .addComponent(rdKhoiLuong)))
                    .addGroup(menuActivity3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(txtThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdXuatXu)
                    .addComponent(rdNSX)
                    .addComponent(rdHang))
                .addGap(82, 82, 82)
                .addGroup(menuActivity3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoiThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuBar.addTab("Thuộc tính sản phẩm", menuActivity3);

        menuActivity1.setBackground(new java.awt.Color(255, 255, 255));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên Sản Phẩm", "Số lượng", "Trạng thái"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setResizable(false);
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblSanPham.getColumnModel().getColumn(3).setResizable(false);
            tblSanPham.getColumnModel().getColumn(3).setPreferredWidth(10);
            tblSanPham.getColumnModel().getColumn(4).setResizable(false);
            tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        btnThemSanPham.setText("Thêm");
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        btnSuaSanPham.setText("Sửa");
        btnSuaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSanPhamActionPerformed(evt);
            }
        });

        btnXoaSanPham.setText("Xóa");
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        btnLamMoiTxtSanPham.setText("Làm mới");
        btnLamMoiTxtSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiTxtSanPhamActionPerformed(evt);
            }
        });

        buttonGroupSP.add(rdConHangSanPham);
        rdConHangSanPham.setText("Còn hàng");
        rdConHangSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdConHangSanPhamActionPerformed(evt);
            }
        });

        buttonGroupSP.add(rdHetHangSanPham);
        rdHetHangSanPham.setText("Hết hàng");
        rdHetHangSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdHetHangSanPhamActionPerformed(evt);
            }
        });

        txtTenSP.setLabelText("Tên sản phẩm");

        buttonGroupSP.add(rdAllSanPham);
        rdAllSanPham.setText("Tất cả");
        rdAllSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdAllSanPhamActionPerformed(evt);
            }
        });

        txtSearchSP.setLabelText("Tìm kiếm tên sản phẩm");
        txtSearchSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchSPActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 153, 51));
        jLabel2.setText("Trạng thái");

        javax.swing.GroupLayout menuActivity1Layout = new javax.swing.GroupLayout(menuActivity1);
        menuActivity1.setLayout(menuActivity1Layout);
        menuActivity1Layout.setHorizontalGroup(
            menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuActivity1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addComponent(txtSearchSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(rdAllSanPham)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuActivity1Layout.createSequentialGroup()
                        .addComponent(rdConHangSanPham)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdHetHangSanPham))
                    .addGroup(menuActivity1Layout.createSequentialGroup()
                        .addGroup(menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnThemSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSuaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoiTxtSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(242, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        menuActivity1Layout.setVerticalGroup(
            menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuActivity1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                .addGroup(menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoiTxtSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(menuActivity1Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(10, 10, 10))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuActivity1Layout.createSequentialGroup()
                            .addGroup(menuActivity1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rdConHangSanPham)
                                .addComponent(rdHetHangSanPham)
                                .addComponent(rdAllSanPham))
                            .addGap(11, 11, 11)))
                    .addComponent(txtSearchSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menuBar.addTab("Sản phẩm", menuActivity1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuBar, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        QrCodeSanPhamChiTiet qrCodeSanPhamChiTiet = new QrCodeSanPhamChiTiet();
        qrCodeSanPhamChiTiet.setQrCodeListener(this);
        qrCodeSanPhamChiTiet.setVisible(true);
    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void checkBoxAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_checkBoxAllActionPerformed
        if (checkBoxAll.isSelected()) {
            for (Map.Entry<String, Boolean> entry : listSPCTIsSelected.entrySet()) {
                String maSPCT = entry.getKey();
                listSPCTIsSelected.put(maSPCT, true);
                loadListChiTietSanPhamToTable();
            }
        } else {
            for (Map.Entry<String, Boolean> entry : listSPCTIsSelected.entrySet()) {
                String maSPCT = entry.getKey();
                listSPCTIsSelected.put(maSPCT, false);
                loadListChiTietSanPhamToTable();
            }
        }
    }// GEN-LAST:event_checkBoxAllActionPerformed

    public void quit() {
        SanPhamForm x = new SanPhamForm();
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(x);
        this.revalidate();
        this.repaint();
        loadListChiTietSanPhamToTable();
    }

    public void setTrang(int index) {
        menuBar.setSelectedIndex(index);
    }

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLamMoiActionPerformed
        maSPJoinCTSP = null;
        loadListChiTietSanPhamToTable();
    }// GEN-LAST:event_btnLamMoiActionPerformed

    private void cboxSearchCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxSearchCTSPActionPerformed
        txtSearchCTSP.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(txtSearchCTSP.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(txtSearchCTSP.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(txtSearchCTSP.getText());
            }

            private void search(String text) {
                int columnIndex = cboxSearchCTSP.getSelectedIndex();
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, columnIndex));
            }
        });
        txtSearchCTSP.setLabelText("Tìm kiếm theo " + cboxSearchCTSP.getSelectedItem());
    }// GEN-LAST:event_cboxSearchCTSPActionPerformed

    private void cboxSearchCTSPMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_cboxSearchCTSPMouseClicked

    }// GEN-LAST:event_cboxSearchCTSPMouseClicked

    private void txtSearchCTSPMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_txtSearchCTSPMouseClicked

    }// GEN-LAST:event_txtSearchCTSPMouseClicked

    private void txtSearchSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSearchSPActionPerformed

    }// GEN-LAST:event_txtSearchSPActionPerformed

    private void txtSearchCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSearchCTSPActionPerformed

    }// GEN-LAST:event_txtSearchCTSPActionPerformed

    private void txtSearchCTSPFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtSearchCTSPFocusGained
        clearFilters();
    }// GEN-LAST:event_txtSearchCTSPFocusGained

    private void tblCTSPMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblCTSPMouseClicked
        int index = tblCTSP.getSelectedRow();
        SanPhamChiTiet spct = getSPCT(tblCTSP.getValueAt(index, 1).toString());
        HandleHinhAnh.setImg(lblHinhAnh, spct.getUrlHinhAnh(), HandleHinhAnh.WIDTH = 400, HandleHinhAnh.HEIGHT = 400);

        SanPhamChiTiet x = getSPCT(tblCTSP.getValueAt(index, 1).toString());

        InfoChiTietSanPham infoChiTietSanPham = new InfoChiTietSanPham(this, x);

        menuActivity2.removeAll();
        menuActivity2.setLayout(new BorderLayout());
        menuActivity2.add(infoChiTietSanPham);
        menuActivity2.revalidate();
        menuActivity2.repaint();
    }// GEN-LAST:event_tblCTSPMouseClicked

    private void txtThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtThuocTinhActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtThuocTinhActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách SPCT");

            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(3);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã SPCT");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Tên sản phẩm");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Hãng");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Chất liệu");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Size");

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Màu sắc");

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Đế giày");

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("Khối lượng");

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Xuất xứ");

            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue("Nhà sản xuất");

            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue("Cổ giày");

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue("Giá");

            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue("Số lượng");

            int count = 0;
            ArrayList<SanPhamChiTiet> listSPCT = repo_chitietsanpham.loadListSanPhamChiTietFromDb();
            for (int i = 0; i < listSPCT.size(); i++) {
                SanPhamChiTiet spct = listSPCT.get(i);
                String maSpct = spct.getIdSPCT();
                if (listSPCTIsSelected.get(maSpct)) {
                    row = sheet.createRow(4 + count);

                    cell = row.createCell(0, CellType.NUMERIC);
                    cell.setCellValue(count + 1);

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getIdSPCT());

                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getSanPham());

                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getHang());

                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getChatLieu());

                    cell = row.createCell(5, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getKichThuoc());

                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getMauSac());

                    cell = row.createCell(7, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getDeGiay());

                    cell = row.createCell(8, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getKhoiLuong());

                    cell = row.createCell(9, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getXuatXu());

                    cell = row.createCell(10, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getNhaSanXuat());

                    cell = row.createCell(11, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getCoGiay());

                    cell = row.createCell(12, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getSanPham());

                    cell = row.createCell(13, CellType.STRING);
                    cell.setCellValue(listSPCT.get(i).getSoLuongTon());

                    count++;
                }
            }
            File file = new File("D://danhsach.xlsx");
            try {
                FileOutputStream fis = new FileOutputStream(file);
                workbook.write(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// GEN-LAST:event_btnXuatExcelActionPerformed

    private void rdAllSanPhamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdAllSanPhamActionPerformed
        loadListSanPhamToTable();
    }// GEN-LAST:event_rdAllSanPhamActionPerformed

    private void btnThemSPCTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemSPCTActionPerformed
        SanPhamChiTiet x = new SanPhamChiTiet();
        AddChiTietSanPham addChiTietSanPham = new AddChiTietSanPham(this);

        menuActivity2.removeAll();
        menuActivity2.setLayout(new BorderLayout());
        menuActivity2.add(addChiTietSanPham);
        menuActivity2.revalidate();
        menuActivity2.repaint();
    }// GEN-LAST:event_btnThemSPCTActionPerformed

    private void btnSuaCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSuaCTSPActionPerformed
        int index = tblCTSP.getSelectedRow();
        if (index == -1) {
            ShowMessageCustom.showMessageWarning(this, "Vui lòng chọn dòng muốn Update");
        } else {
            suaSPCT(index);
        }
    }// GEN-LAST:event_btnSuaCTSPActionPerformed

    private void btnHuyLocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyLocActionPerformed
        clearFilters();
    }// GEN-LAST:event_btnHuyLocActionPerformed

    private void btnXoaCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaCTSPActionPerformed
        int index = tblCTSP.getSelectedRow();
        if (index == -1) {
            ShowMessageCustom.showMessageWarning(this, "Vui lòng chọn dòng bạn muốn xóa");
        } else {
            removeSPCT(index);
        }
    }// GEN-LAST:event_btnXoaCTSPActionPerformed

    private void removeSPCT(int index) {
        String maCTSP = tblCTSP.getValueAt(index, 1).toString();
        repo_chitietsanpham.removeCTSPFromDb(maCTSP);
        loadListChiTietSanPhamToTable();
        ShowMessageCustom.showMessageSuccess(this, "Xóa thành công");
    }

    private void suaSPCT(int index) {
        SanPhamChiTiet x = getSPCT(tblCTSP.getValueAt(index, 1).toString());

        UpdateChiTietSanPham updateChiTietSanPham = new UpdateChiTietSanPham(this, x);

        menuActivity2.removeAll();
        menuActivity2.setLayout(new BorderLayout());
        menuActivity2.add(updateChiTietSanPham);
        menuActivity2.revalidate();
        menuActivity2.repaint();
    }

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblSanPhamMouseClicked
        int index = tblSanPham.getSelectedRow();
        String maSP = tblSanPham.getValueAt(index, 1).toString();
        String tenSP = tblSanPham.getValueAt(index, 2).toString();

        txtTenSP.setText(tenSP);

        setTrang(0);
        maSPJoinCTSP = maSP;
        loadListChiTietSanPhamToTable();
    }// GEN-LAST:event_tblSanPhamMouseClicked

    private void rdHetHangCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdHetHangCTSPActionPerformed
        loadListChiTietSanPhamToTable();
    }// GEN-LAST:event_rdHetHangCTSPActionPerformed

    private void rdConHangCTSPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdConHangCTSPActionPerformed
        loadListChiTietSanPhamToTable();
    }// GEN-LAST:event_rdConHangCTSPActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblThuocTinhMouseClicked
        int index = tblThuocTinh.getSelectedRow();
        txtThuocTinh.setText(tblThuocTinh.getValueAt(index, 2).toString());
    }// GEN-LAST:event_tblThuocTinhMouseClicked

    private void rdConHangSanPhamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdConHangSanPhamActionPerformed
        loadListSanPhamToTable();
    }// GEN-LAST:event_rdConHangSanPhamActionPerformed

    private void rdHetHangSanPhamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdHetHangSanPhamActionPerformed
        loadListSanPhamToTable();
    }// GEN-LAST:event_rdHetHangSanPhamActionPerformed

    private void btnLamMoiThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLamMoiThuocTinhActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnLamMoiThuocTinhActionPerformed

    private void menuBarMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_menuBarMouseClicked

    }// GEN-LAST:event_menuBarMouseClicked

    private void menuActivity3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_menuActivity3MouseClicked
    }// GEN-LAST:event_menuActivity3MouseClicked

    private void jMenuBar2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jMenuBar2MouseClicked

    }// GEN-LAST:event_jMenuBar2MouseClicked

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemSanPhamActionPerformed
        String maSP = RandomStringGenerator.generateRandomString("SP");
        String tenSP = txtTenSP.getText();

        SanPham sp = new SanPham();
        sp.setMaSp(maSP);
        sp.setTenSp(tenSP);

        repo_sanpham.themSanPham(sp);

        loadListSanPhamToTable();
    }// GEN-LAST:event_btnThemSanPhamActionPerformed

    public SanPham getSanPham(String maSP) {
        for (SanPham i : listSanPham) {
            if (i.getMaSp().equals(maSP)) {
                return i;
            }
        }
        return null;
    }

    public SanPhamChiTiet getSPCT(String maSPCT) {
        for (SanPhamChiTiet i : listSPCT) {
            if (i.getIdSPCT().equals(maSPCT)) {
                return i;
            }
        }
        return null;
    }

    ;

    private void btnSuaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSuaSanPhamActionPerformed
        int index = tblSanPham.getSelectedRow();
        String maSP = tblSanPham.getValueAt(index, 1).toString();
        SanPham x = getSanPham(maSP);

        x.setTenSp(txtTenSP.getText());

        repo_sanpham.suaSanPham(x);
        loadListSanPhamToTable();
    }// GEN-LAST:event_btnSuaSanPhamActionPerformed

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaSanPhamActionPerformed
        int index = tblSanPham.getSelectedRow();
        if (index == -1) {
            ShowMessageCustom.showMessageWarning(this, "Vui lòng chọn sản phẩm muốn xóa");
        } else {
            String maSP = tblSanPham.getValueAt(index, 1).toString();
            repo_sanpham.xoaSanPham(maSP);
            loadListSanPhamToTable();
        }

    }// GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void btnLamMoiTxtSanPhamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLamMoiTxtSanPhamActionPerformed

    }// GEN-LAST:event_btnLamMoiTxtSanPhamActionPerformed

    private void btnThemThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnThemThuocTinhMouseClicked

    }// GEN-LAST:event_btnThemThuocTinhMouseClicked

    private void btnSuaThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnSuaThuocTinhMouseClicked

    }// GEN-LAST:event_btnSuaThuocTinhMouseClicked

    private void btnXoaThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnXoaThuocTinhMouseClicked
        interface_repo_thuoctinh.updateThuocTinh(x);
    }// GEN-LAST:event_btnXoaThuocTinhMouseClicked

    private void btnSuaThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnUpdateActionPerformed
        int index = tblThuocTinh.getSelectedRow();
        if (index == -1) {
            ShowMessageCustom.showMessageWarning(this, "Vui lòng chọn thuộc tính muốn sửa");
        } else {
            Validate validate = new Validate();
            validate.checkNull("Thuộc tính", txtThuocTinh.getText());
            if (rdKhoiLuong.isSelected() || rdKichThuoc.isSelected()) {
                validate.checkNumber("Thuộc tính", txtThuocTinh.getText());
            }
            if (validate.isChuoiHopLe()) {
                String maTT = tblThuocTinh.getValueAt(index, 1).toString();
                x.setIdThuocTinh(maTT);
                x.setThuocTinh(txtThuocTinh.getText());

                interface_repo_thuoctinh.updateThuocTinh(x);
                loadListThuocTinhToTable();
                ShowMessageCustom.showMessageSuccess(this, "Đã sửa thành công thuộc tính");
            }
        }
        loadListThuocTinhToTable();
    }// GEN-LAST:event_btnUpdateActionPerformed

    private void rdMauSacActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdMauSacActionPerformed
        interface_repo_thuoctinh = new repo_mausac();
        x = new MauSac();
        loadListThuocTinhToTable();
    }// GEN-LAST:event_rdMauSacActionPerformed

    private void rdKichThuocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdKichThuocActionPerformed
        interface_repo_thuoctinh = new repo_kichthuoc();
        x = new KichThuoc();
        loadListThuocTinhToTable();
    }// GEN-LAST:event_rdKichThuocActionPerformed

    private void rdDeGiayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdDeGiayActionPerformed
        interface_repo_thuoctinh = new repo_degiay();
        x = new DeGiay();
        loadListThuocTinhToTable();
    }// GEN-LAST:event_rdDeGiayActionPerformed

    private void rdNSXActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdNSXActionPerformed
        interface_repo_thuoctinh = new repo_nsx();
        x = new NhaSanXuat();
        loadListThuocTinhToTable();
    }// GEN-LAST:event_rdNSXActionPerformed

    private void rdCoGiayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdCoGiayActionPerformed
        interface_repo_thuoctinh = new repo_cogiay();
        x = new CoGiay();
        loadListThuocTinhToTable();
    }// GEN-LAST:event_rdCoGiayActionPerformed

    private void rdKhoiLuongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdKhoiLuongActionPerformed
        interface_repo_thuoctinh = new repo_khoiluong();
        x = new KhoiLuong();
        loadListThuocTinhToTable();
    }// GEN-LAST:event_rdKhoiLuongActionPerformed

    private void rdHangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdHangActionPerformed
        interface_repo_thuoctinh = new repo_hang();
        x = new Hang();
        loadListThuocTinhToTable();
    }// GEN-LAST:event_rdHangActionPerformed

    private void btnThemThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddActionPerformed
        Validate validate = new Validate();
        validate.checkNull("Thuộc tính", txtThuocTinh.getText());
        if (rdKhoiLuong.isSelected() || rdKichThuoc.isSelected()) {
            validate.checkNumber("Thuộc tính", txtThuocTinh.getText());
        }
        if (validate.isChuoiHopLe()) {
            String id = RandomStringGenerator.generateRandomString(x.getPrefix());
            String thuocTinh = txtThuocTinh.getText();

            x.setIdThuocTinh(id);
            x.setThuocTinh(thuocTinh);
            x.setTrangThai(1);

            interface_repo_thuoctinh.addThuocTinh(x);
            loadListThuocTinhToTable();
            ShowMessageCustom.showMessageSuccess(this, "Thêm thuộc tính thành công");
        } else {
            validate.showWarning(this);
        }
    }// GEN-LAST:event_btnAddActionPerformed

    private void btnXoaThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRemoveActionPerformed

    }// GEN-LAST:event_btnRemoveActionPerformed

    private void rdXuatXuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdXuatXuActionPerformed
        interface_repo_thuoctinh = new repo_xuatxu();
        x = new XuatXu();
        loadListThuocTinhToTable();
    }// GEN-LAST:event_rdXuatXuActionPerformed

    private void rdChatLieuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdChatLieuActionPerformed
        interface_repo_thuoctinh = new repo_chatlieu();
        x = new ChatLieu();
        loadListThuocTinhToTable();
    }// GEN-LAST:event_rdChatLieuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ButtonCustom btnHuyLoc;
    private swing.ButtonCustom btnLamMoi;
    private javax.swing.JButton btnLamMoiThuocTinh;
    private javax.swing.JButton btnLamMoiTxtSanPham;
    private swing.ButtonCustom btnQuetQR;
    private swing.ButtonCustom btnSuaCTSP;
    private javax.swing.JButton btnSuaSanPham;
    private javax.swing.JButton btnSuaThuocTinh;
    private swing.ButtonCustom btnThemSPCT;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnThemThuocTinh;
    private swing.ButtonCustom btnXoaCTSP;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JButton btnXoaThuocTinh;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.ButtonGroup buttonGroupConHangHetHangCTSP;
    private javax.swing.ButtonGroup buttonGroupSP;
    private javax.swing.ButtonGroup buttonGroupThuocTinh;
    private swing.Combobox cboxChatLieu;
    private swing.Combobox cboxCoGiay;
    private swing.Combobox cboxDeGiay;
    private swing.Combobox cboxHang;
    private swing.Combobox cboxKhoiLuong;
    private swing.Combobox cboxKichThuoc;
    private swing.Combobox cboxMauSac;
    private swing.Combobox cboxNhaSanXuat;
    private swing.Combobox cboxSearchCTSP;
    private swing.Combobox cboxXuatXu;
    private javax.swing.JCheckBox checkBoxAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JPanel menuActivity1;
    private javax.swing.JPanel menuActivity2;
    private javax.swing.JPanel menuActivity3;
    private javax.swing.JTabbedPane menuBar;
    private javax.swing.JRadioButton rdAllSanPham;
    private javax.swing.JRadioButton rdChatLieu;
    private javax.swing.JRadioButton rdCoGiay;
    private javax.swing.JRadioButton rdConHangCTSP;
    private javax.swing.JRadioButton rdConHangSanPham;
    private javax.swing.JRadioButton rdDeGiay;
    private javax.swing.JRadioButton rdHang;
    private javax.swing.JRadioButton rdHetHangCTSP;
    private javax.swing.JRadioButton rdHetHangSanPham;
    private javax.swing.JRadioButton rdKhoiLuong;
    private javax.swing.JRadioButton rdKichThuoc;
    private javax.swing.JRadioButton rdMauSac;
    private javax.swing.JRadioButton rdNSX;
    private javax.swing.JRadioButton rdXuatXu;
    private javax.swing.JTable tblCTSP;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblThuocTinh;
    private swing.TextField txtSearchCTSP;
    private swing.TextField txtSearchSP;
    private swing.TextField txtTenSP;
    private swing.TextField txtThuocTinh;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onQrCodeRead(String qrCode) {
        InfoChiTietSanPham infoChiTietSanPham = new InfoChiTietSanPham(this, getSPCT(qrCode));
        
        menuActivity2.removeAll();
        menuActivity2.setLayout(new BorderLayout());
        menuActivity2.add(infoChiTietSanPham);
        menuActivity2.revalidate();
        menuActivity2.repaint();
    }
}
