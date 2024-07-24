package com.raven.form;

import com.raven.cell.TableActionCellEditor;
import com.raven.cell.TableActionCellRender;
import com.raven.cell.TableActionEvent;
import com.raven.entity.NhanVien;
import com.raven.repository.NhanVienRepository;
import com.raven.respose.NhanVienResponse;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import jdk.jfr.consumer.EventStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhanVienForm extends javax.swing.JPanel {

    NhanVienRepository repo = new NhanVienRepository();

    private DefaultTableModel dtm;

    private ArrayList<NhanVienResponse> list = new ArrayList<>();

    private DefaultTableModel dtm1;

    public NhanVienForm() {
        initComponents();

        dtm = (DefaultTableModel) tbNhanVien.getModel();
        dtm1 = (DefaultTableModel) tbNhanVienNghiViec.getModel();
        list = repo.getAll();
        showTableNhanVien(repo.getAll());
        showTableNhanVienStatus(repo.getAllStatus());
        txtMaNv.setEnabled(false);

        edit();
        editNghi();
        
        
    }

    public void showTableNhanVien(ArrayList<NhanVienResponse> lists) {
        dtm.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        lists.forEach(s -> dtm.addRow(new Object[]{
            index.getAndIncrement(),
            s.getMaNv(),
            s.getHoTenNv(),
            s.getEmail(),
            s.getSdt(),
            s.isGioiTinh() ? "Nam" : "Nữ",
            s.getNgaySinh(),
            s.getDiachi(),
            s.isChucVu() ? "Trưởng phòng" : "Nhân Viên",
            s.getLuong(),
            s.getTrangThai() == 1 ? "Làm việc" : "Nghỉ việc"
        }
        ));
    }

    public void showTableNhanVienStatus(ArrayList<NhanVienResponse> lists) {
        dtm1.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        lists.forEach(s -> dtm1.addRow(new Object[]{
            index.getAndIncrement(),
            s.getMaNv(),
            s.getHoTenNv(),
            s.getEmail(),
            s.getSdt(),
            s.isGioiTinh() ? "Nam" : "Nữ",
            s.getNgaySinh(),
            s.getDiachi(),
            s.isChucVu() ? "Trưởng phòng" : "Nhân Viên",
            s.getLuong(),
            s.getTrangThai() == 1 ? "Làm việc" : "Nghỉ việc"
        }
        ));
    }

    private void edit() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int index) {
//                System.out.println("Row " + index);
//                DefaultTableModel dtm = (DefaultTableModel) tbNhanVien.getModel();
                NhanVienResponse pgg = repo.getAll().get(index);
                repo.delete(pgg.getId());
                showTableNhanVien(repo.getAll());
                showTableNhanVienStatus(repo.getAllStatus());
            }
        };

        tbNhanVien.getColumnModel().getColumn(11).setCellRenderer(new TableActionCellRender());
        tbNhanVien.getColumnModel().getColumn(11).setCellEditor(new TableActionCellEditor(event));
    }

    private void editNghi() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int index) {
//                System.out.println("Row " + index);
//                DefaultTableModel dtm = (DefaultTableModel) tbNhanVien.getModel();
                NhanVienResponse pgg = repo.getAllStatus().get(index);
                repo.deleteNghi(pgg.getId());
                showTableNhanVienStatus(repo.getAllStatus());
                showTableNhanVien(repo.getAll());
            }
        };

        tbNhanVienNghiViec.getColumnModel().getColumn(11).setCellRenderer(new TableActionCellRender());
        tbNhanVienNghiViec.getColumnModel().getColumn(11).setCellEditor(new TableActionCellEditor(event));
    }

    public void show1(int index) {
        NhanVienResponse nv = repo.getAll().get(index);
        txtMaNv.setText(nv.getMaNv().trim().replaceAll("\\s+", ""));
        txtTenNhanVien.setText(nv.getHoTenNv().trim().replaceAll("\\s+", ""));
        if (nv.isGioiTinh()) {
            rdNam.setSelected(true);
            rdNu.setSelected(false);
        } else {
            rdNam.setSelected(false);
            rdNu.setSelected(true);
        }

        if (nv.isChucVu()) {
            rdQuanLi.setSelected(true);
            rdNhanVien.setSelected(false);
        } else {
            rdQuanLi.setSelected(false);
            rdNhanVien.setSelected(true);
        }

        txtNgaySinh.setDate(nv.getNgaySinh());
        txtDiaChi.setText(nv.getDiachi().trim().replaceAll("\\s+", ""));
        txtSdt.setText(nv.getSdt().trim().replaceAll("\\s+", ""));
        txtEmail.setText(nv.getEmail().trim().replaceAll("\\s+", ""));
        txtLuong.setText(nv.getLuong().toString());
        txtMatKhau.setText(nv.getMatKhau().trim().replaceAll("\\s+", ""));
    }

    public void reset() {
        txtMaNv.setText("");
        txtTenNhanVien.setText("");
        rdNam.setSelected(true);
        rdNu.setSelected(false);
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtLuong.setText("");
        txtNgaySinh.setDate(null);
        txtSdt.setEnabled(true);
        rdQuanLi.setSelected(true);
        rdNhanVien.setSelected(false);
        txtSdt.setText("");
        txtMatKhau.setText("");
    }

    public NhanVienResponse getFormUpdate() {
        String maNv = txtMaNv.getText().trim().replaceAll("\\s+", "");
        String tenNv = txtTenNhanVien.getText().trim().replaceAll("\\s+", "");
        String diaChi = txtDiaChi.getText().trim().replaceAll("\\s+", "");
        String email = txtEmail.getText().trim().replaceAll("\\s+", "");
        Double luong = Double.valueOf(txtLuong.getText());
        Date ngaySinh = txtNgaySinh.getDate();
        String sdt = txtSdt.getText().trim().replaceAll("\\s+", "");
        boolean gioiTinh = rdNam.isSelected();
        if (gioiTinh) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        boolean chucVu = rdQuanLi.isSelected();
        if (chucVu) {
            chucVu = true;
        } else {
            chucVu = false;
        }

        String mk = txtMatKhau.getText();

        NhanVienResponse rp = new NhanVienResponse(WIDTH, maNv, tenNv, gioiTinh, ngaySinh, email, sdt, WIDTH, luong, chucVu, diaChi, mk);

        return rp;
    }

    public boolean test() {
        if (txtMaNv.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã không được để trống");
            txtMaNv.requestFocus();
            return false;
        }

        if (txtTenNhanVien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống");
            txtTenNhanVien.requestFocus();
            return false;
        }

        if (txtTenNhanVien.getText().length() <= 5) {
            JOptionPane.showMessageDialog(this, "Họ tên phải nhiều hơn 5 kí tự");
            txtTenNhanVien.requestFocus();
            return false;
        }
        if (txtTenNhanVien.getText().length() >= 50) {
            JOptionPane.showMessageDialog(this, "Họ tên không được nhiều hơn 50 kí tự");
            txtTenNhanVien.requestFocus();

            return false;
        }
        if (txtDiaChi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống");
            return false;
        }

        if (txtLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lương không được để trống");
            return false;
        }

        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống");
            return false;
        }

        if (txtEmail.getText().length() <= 10) {
            JOptionPane.showMessageDialog(this, "Email phải nhiều hơn 10 kí tự");
            return false;
        }
        if (txtEmail.getText().length() >= 50) {
            JOptionPane.showMessageDialog(this, "Email không được nhiều hơn 50 kí tự");
            return false;
        }

        if (txtSdt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "SDT không được để trống");
            return false;
        }

        if (txtSdt.getText().length() <= 5) {
            JOptionPane.showMessageDialog(this, "SDT phải nhiều hơn 5 kí tự");
            return false;
        }
        if (txtSdt.getText().length() >= 11) {
            JOptionPane.showMessageDialog(this, "SDT không được nhiều hơn 10 kí tự");
            return false;
        }

        if (txtMatKhau.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống");
            return false;
        }

        if (txtMatKhau.getText().length() < 5 || txtMatKhau.getText().length() > 50) {
            JOptionPane.showMessageDialog(this, "Mật khẩu cần dài hơn 5 kí tự (tối đa 50 kí tự)");
            return false;
        }
        String rex = "^[0-9a-zA-Z]+$";

        if (!txtMatKhau.getText().matches(rex)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được chứa kí tự đặc biệt");
            return false;
        }

        if (txtNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không được để trống");
            txtNgaySinh.requestFocus();
            return false;
        }

        Date ngaySinhDate = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String datePar = format.format(txtNgaySinh.getDate());
            ngaySinhDate = format.parse(datePar);
            Date dateNow = new Date();
            if (ngaySinhDate.after(dateNow)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không được lớn hơn ngày hiện tại");
                txtNgaySinh.requestFocus();
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh sai định dạng");
            System.err.println(format);
            System.err.println(txtNgaySinh.getDate());
            System.err.println(ngaySinhDate);
            txtNgaySinh.requestFocus();
            return false;
        }

        return true;
    }

    public void updateTable() {
        DefaultTableModel dtm = (DefaultTableModel) tbNhanVien.getModel();
        dtm.setRowCount(0); // Xóa tất cả các dòng hiện tại
        showTableNhanVien(repo.getAll()); // Hiển thị lại dữ liệu từ repo.getAll()
    }

    public void openDetail(String maNv,
            String hoTenNv,
            boolean gioiTinh,
            boolean chucVu,
            Date ngaySinh,
            String diachi,
            String sdt,
            String email,
            Double luong) {
        DetailNhanVien detail = new DetailNhanVien(this);

        detail.setDetail(maNv, hoTenNv, gioiTinh, chucVu, ngaySinh, diachi, sdt, email, luong);
        detail.setVisible(true);
    }
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtMaNv = new javax.swing.JTextField();
        txtTenNhanVien = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtLuong = new javax.swing.JTextField();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        rdQuanLi = new javax.swing.JRadioButton();
        rdNhanVien = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnXuat = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        cboTrangThai = new javax.swing.JComboBox<>();
        rdNamSearch = new javax.swing.JRadioButton();
        rdNuSearch = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        textField1 = new com.raven.util.TextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbNhanVien = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbNhanVienNghiViec = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 204, 153)));
        jPanel1.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thông tin nhân viên");

        jLabel2.setText("Mã nhân viên");

        jLabel3.setText("Tên nhân viên");

        jLabel4.setText("Giới tính");

        jLabel5.setText("Ngày sinh");

        jLabel6.setText("Email");

        jLabel8.setText("SDT");

        jLabel10.setText("Địa chỉ");

        jLabel11.setText("Chức vụ");

        jLabel13.setText("Lương");

        buttonGroup1.add(rdNam);
        rdNam.setSelected(true);
        rdNam.setText("Nam");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        buttonGroup2.add(rdQuanLi);
        rdQuanLi.setSelected(true);
        rdQuanLi.setText("Trưởng phòng");

        buttonGroup2.add(rdNhanVien);
        rdNhanVien.setText("Nhân viên");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        jPanel2.setBackground(new java.awt.Color(240, 246, 246));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 204, 153)));

        btnThem.setBackground(new java.awt.Color(255, 153, 51));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Add.png"))); // NOI18N
        btnThem.setBorder(null);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 153, 51));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/updated (1).png"))); // NOI18N
        btnUpdate.setBorder(null);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 153, 51));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/reset.png"))); // NOI18N
        btnReset.setBorder(null);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnXuat.setBackground(new java.awt.Color(255, 153, 51));
        btnXuat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/logo.png"))); // NOI18N
        btnXuat.setBorder(null);
        btnXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setText("Chức năng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(163, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(162, 162, 162))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(21, 21, 21)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setText("Mật khẩu");

        txtNgaySinh.setDateFormatString("dd-MM-yyyy");

        jPanel5.setBackground(new java.awt.Color(240, 246, 246));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 204, 153)));

        jButton1.setBackground(new java.awt.Color(255, 153, 51));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/search.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nghỉ việc", "Làm việc" }));
        cboTrangThai.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        buttonGroup4.add(rdNamSearch);
        rdNamSearch.setSelected(true);
        rdNamSearch.setText("Nam");

        buttonGroup4.add(rdNuSearch);
        rdNuSearch.setText("Nữ");
        rdNuSearch.setToolTipText("");

        jLabel7.setText("TÌm kiếm");

        jLabel12.setText("Trạng thái");

        jLabel15.setText("Giới tính");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSearch)
                        .addComponent(cboTrangThai, 0, 233, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(rdNamSearch)
                        .addGap(68, 68, 68)
                        .addComponent(rdNuSearch)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdNamSearch)
                    .addComponent(rdNuSearch)
                    .addComponent(jLabel15))
                .addGap(44, 44, 44)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        textField1.setText("textField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel13)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNv)
                            .addComponent(txtTenNhanVien)
                            .addComponent(txtSdt)
                            .addComponent(txtEmail)
                            .addComponent(txtLuong)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdQuanLi)
                                        .addGap(62, 62, 62))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdNam)
                                        .addGap(74, 74, 74)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdNhanVien)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdNu)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                            .addComponent(txtMatKhau)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(rdNam)
                            .addComponent(rdNu))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(rdQuanLi)
                            .addComponent(rdNhanVien))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addGap(82, 82, 82))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 204, 153)));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        tbNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "Email", "SDT", "GioiTinh", "NgaySinh", "DiaChi", "ChucVu", "Lương", "Trạng thái", "Hành động"
            }
        ));
        tbNhanVien.setRowHeight(35);
        tbNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNhanVienMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbNhanVienMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbNhanVien);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Đang Hoạt Động", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        tbNhanVienNghiViec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "Email", "SDT", "GioiTinh", "NgaySinh", "DiaChi", "ChucVu", "Lương", "Trạng thái", "Hành động"
            }
        ));
        tbNhanVienNghiViec.setRowHeight(35);
        tbNhanVienNghiViec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNhanVienNghiViecMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbNhanVienNghiViec);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Nghỉ việc", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNhanVienMouseClicked

    }//GEN-LAST:event_tbNhanVienMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

//        Add add = new Add(this); // Truyền tham chiếu của NhanVienForm vào Add
//        add.setVisible(true);
        ThemNhanVien te = new ThemNhanVien(this);
        setLayout(new BorderLayout());
        this.removeAll(); // Clear existing content
        this.add(te);
        revalidate();
        repaint();

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int index = tbNhanVien.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn một dòng");
            return;
        }

        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa không ?", "add", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }

        if (test()) {
            int id = repo.getAll().get(index).getId();
            if (repo.update(id, getFormUpdate())) {
                showTableNhanVien(repo.getAll());
                this.reset();
                JOptionPane.showMessageDialog(this, "Bạn đã update thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Update thất bại");
            }
        }


    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
        showTableNhanVien(repo.getAll());
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatActionPerformed

        try {
            String path = "D:\\DA1\\DuAn1 (1)\\excel";
            JFileChooser jFileChooser = new JFileChooser(path);
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = (Sheet) wb.createSheet("Account");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tbNhanVien.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tbNhanVien.getColumnName(i));
                }
                for (int j = 0; j < tbNhanVien.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tbNhanVien.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tbNhanVien.getValueAt(j, k) != null) {
                            cell.setCellValue(tbNhanVien.getValueAt(j, k).toString());
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
    }//GEN-LAST:event_btnXuatActionPerformed

    private void tbNhanVienNghiViecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNhanVienNghiViecMouseClicked

    }//GEN-LAST:event_tbNhanVienNghiViecMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        boolean gioiTinh = rdNamSearch.isSelected();
        System.out.println(gioiTinh);

        showTableNhanVien(repo.search(txtSearch.getText(), cboTrangThai.getSelectedIndex(), rdNamSearch.isSelected()));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNhanVienMousePressed
        // TODO add your handling code here:
        int index = tbNhanVien.getSelectedRow();
        int col = tbNhanVien.getSelectedColumn();
        show1(index);
//        txtMaNv.setEnabled(false);

        int clickk = evt.getClickCount();
        

        if (index != -1 && clickk == 2) {
            String maNv = tbNhanVien.getValueAt(index, 1).toString();
            String hoTenNv = tbNhanVien.getValueAt(index, 2).toString();

            String gioiTinhStr = tbNhanVien.getValueAt(index, 5).toString();
            boolean gioiTinh = Boolean.parseBoolean(gioiTinhStr);

            String chucVuu = tbNhanVien.getValueAt(index, 8).toString();
            boolean chucVu = Boolean.parseBoolean(chucVuu);

            Date ngaySinh = null;
            try {
                String ngaySinh0 = tbNhanVien.getValueAt(index, 6).toString();
                SimpleDateFormat dateF = new SimpleDateFormat("dd-MM-yyyy");
                ngaySinh = dateF.parse(ngaySinh0);

            } catch (Exception e) {
                e.printStackTrace();
            }

            String diachi = tbNhanVien.getValueAt(index, 7).toString();
            String sdt = tbNhanVien.getValueAt(index, 4).toString();
            String email = tbNhanVien.getValueAt(index, 3).toString();
            Double luong = Double.valueOf(tbNhanVien.getValueAt(index, 9).toString());

            openDetail(maNv, hoTenNv, gioiTinh, chucVu, ngaySinh, diachi, sdt, email, luong);
        }
    }//GEN-LAST:event_tbNhanVienMousePressed
//
//    public static void main(String[] args) {
//            TableModel model = new AbstractTableModel() {
//            @Override
//            public int getRowCount() {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//
//            @Override
//            public int getColumnCount() {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//
//            @Override
//            public Object getValueAt(int rowIndex, int columnIndex) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//
//            @Override
//            public boolean isCellEditable(int index, int col) {
//                return false; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
//            }
//            
//            
//            
//            };
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXuat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNamSearch;
    private javax.swing.JRadioButton rdNhanVien;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JRadioButton rdNuSearch;
    private javax.swing.JRadioButton rdQuanLi;
    private javax.swing.JTable tbNhanVien;
    private javax.swing.JTable tbNhanVienNghiViec;
    private com.raven.util.TextField textField1;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaNv;
    private javax.swing.JTextField txtMatKhau;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenNhanVien;
    // End of variables declaration//GEN-END:variables
}
