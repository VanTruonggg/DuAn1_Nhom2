/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import com.raven.form.Jframe.QrCode;
import com.raven.logic.RandomStringGenerator;
import com.raven.repository.NhanVienRepository;
import com.raven.respose.NhanVienResponse;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.net.PasswordAuthentication;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author ADMIN
 */
public class ThemNhanVien extends javax.swing.JPanel {

    private NhanVienRepository repo = new NhanVienRepository();

    private NhanVienForm nv;

    private DefaultTableModel dtm;

    private ArrayList<NhanVienResponse> list = new ArrayList<>();

    String ma = RandomStringGenerator.generateRandomString("NV");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public ThemNhanVien() {
        initComponents();
    }

    public void setTenNhanVien(String tenNhanVien) {
        txtTenNhanVien.setText(tenNhanVien);
    }

    public void setNgaySinh(Date ngaySinh) throws ParseException {
        String dt = dateFormat.format(txtNgaySinh.getDate());
        ngaySinh = dateFormat.parse(dt);
    }

    public void setDiaChi(String diaChi) {
        tpDiaChi.setText(diaChi);
    }

    public ThemNhanVien(NhanVienForm nvForm) {
        initComponents();
        this.nv = nvForm; // Lưu tham chiếu từ bên ngoài
        list = repo.getAll();
        setVisible(true);
    }

    public void quit() {
        NhanVienForm te = new NhanVienForm();
        setLayout(new BorderLayout());
        this.removeAll(); // Clear existing content
        this.add(te);
        revalidate();
        repaint();
    }

    public NhanVienResponse getForm() {

        String tenNv = txtTenNhanVien.getText().trim().replaceAll("\\s+", "");
        String diaChi = tpDiaChi.getText().trim().replaceAll("\\s+", "");
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
        boolean chucVu = rdTruongPhong.isSelected();
        if (chucVu) {
            chucVu = true;
        } else {
            chucVu = false;
        }

        String pass = txtPassWord.getText();

        NhanVienResponse rp = new NhanVienResponse(WIDTH, ma, tenNv, gioiTinh, ngaySinh, email, sdt, WIDTH, luong, chucVu, diaChi, pass);

        return rp;
    }

    public boolean testData() {
        if (txtTenNhanVien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống");
            return false;
        }

        if (txtTenNhanVien.getText().length() <= 5) {
            JOptionPane.showMessageDialog(this, "Họ tên phải nhiều hơn 5 kí tự");
            return false;
        }
        if (txtTenNhanVien.getText().length() >= 50) {
            JOptionPane.showMessageDialog(this, "Họ tên không được nhiều hơn 50 kí tự");
            return false;
        }

        if (txtNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống");
            return false;
        }

        SimpleDateFormat simp = new SimpleDateFormat("dd-MM-yyyy");
        Date ngaySinh;

        try {

            ngaySinh = simp.parse(simp.format(txtNgaySinh.getDate()));
            Date date = new Date();
            if (ngaySinh.after(date)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không thể lớn hơn ngày hiện tại");
                txtNgaySinh.requestFocus();
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng yyyy-MM-dd !");
            txtNgaySinh.requestFocus();
            return false;

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Lỗi trong việc đặt định dạng ngày!");
            txtNgaySinh.requestFocus();
            return false;
        }

        if (tpDiaChi.getText().isEmpty()) {
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

        if (txtPassWord.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống");
            return false;
        }

        if (txtPassWord.getText().length() < 5 || txtPassWord.getText().length() > 50) {
            JOptionPane.showMessageDialog(this, "Mật khẩu cần dài hơn 5 kí tự (tối đa 50 kí tự)");
            return false;
        }
        String rex = "^[0-9a-zA-Z]+$";

        if (!txtPassWord.getText().matches(rex)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được chứa kí tự đặc biệt");
            return false;
        }
        return true;
    }

    public boolean trung() {
        for (NhanVienResponse nhanVienResponse : list) {
            if (nhanVienResponse.getEmail().equals(txtEmail.getText())) {
                JOptionPane.showMessageDialog(this, "Email bị trùng");
                return false;
            } else if (nhanVienResponse.getSdt().equals(txtSdt.getText())) {
                JOptionPane.showMessageDialog(this, "SDT bị trùng");
                return false;
            }
        }
        return true;
    }

    public void mail() {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);

            String acountName = txtUser.getText();
            String acountPassWork = txtPassApp.getText();

            Session s = Session.getInstance(p, new Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(acountName, acountPassWork);
                }
            });

            String from = txtUser.getText();
            String to = txtTo.getText();
            String sub = txtSubject.getText();
            String body = txtPassWord.getText();

            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            msg.setSubject(sub);
            msg.setText(body);

            Transport.send(msg);
            JOptionPane.showMessageDialog(null, "Kiểm tra Email để nhập mật khẩu", "Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi gửi email", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rdNu = new javax.swing.JRadioButton();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpDiaChi = new javax.swing.JTextPane();
        txtLuong = new javax.swing.JTextField();
        SDT = new javax.swing.JLabel();
        Emial = new javax.swing.JLabel();
        rdTruongPhong = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        rdNhanVien = new javax.swing.JRadioButton();
        txtTenNhanVien = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnExitAdd = new javax.swing.JButton();
        btnThemAdd1 = new javax.swing.JButton();
        txtSdt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        rdNam = new javax.swing.JRadioButton();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        btnCccd = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtPassWord = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPassApp = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        txtTo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSubject = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Chức vụ");

        jLabel5.setText("Ngày sinh");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel6.setText("DiaChi");

        jScrollPane1.setViewportView(tpDiaChi);

        SDT.setText("SDT");

        Emial.setText("Email");

        buttonGroup2.add(rdTruongPhong);
        rdTruongPhong.setSelected(true);
        rdTruongPhong.setText("Trưởng phòng");
        rdTruongPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTruongPhongActionPerformed(evt);
            }
        });

        jLabel10.setText("Luong");

        buttonGroup2.add(rdNhanVien);
        rdNhanVien.setText("Nhân  Viên");

        jLabel2.setText("Tên Nhân Viên");

        btnExitAdd.setBackground(new java.awt.Color(255, 153, 51));
        btnExitAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExitAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnExitAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/button.png"))); // NOI18N
        btnExitAdd.setBorder(null);
        btnExitAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitAddActionPerformed(evt);
            }
        });

        btnThemAdd1.setBackground(new java.awt.Color(255, 153, 51));
        btnThemAdd1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemAdd1.setForeground(new java.awt.Color(255, 255, 255));
        btnThemAdd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Add.png"))); // NOI18N
        btnThemAdd1.setBorder(null);
        btnThemAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemAdd1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Giới tính");

        buttonGroup1.add(rdNam);
        rdNam.setSelected(true);
        rdNam.setText("Nam");

        txtNgaySinh.setDateFormatString("ddMMyyyy");

        btnCccd.setBackground(new java.awt.Color(255, 153, 51));
        btnCccd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/card.png"))); // NOI18N
        btnCccd.setBorder(null);
        btnCccd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCccdActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Send", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel9.setText("Message");

        jLabel1.setText("UserName");

        txtUser.setText("vantruong22082005@gmail.com");

        jLabel7.setText("PassWord");

        txtPassApp.setText("vveslhucvqbsfpzq");

        jLabel8.setText("To");

        jLabel11.setText("Subject");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPassWord)
                    .addComponent(txtUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(txtPassApp, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSubject))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPassApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(2, 2, 2)
                .addComponent(txtPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(btnThemAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118)
                        .addComponent(btnCccd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165)
                        .addComponent(btnExitAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5))
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(SDT)
                                    .addComponent(Emial)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel10))
                                .addGap(66, 66, 66)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSdt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(rdTruongPhong)
                                .addGap(55, 55, 55)
                                .addComponent(rdNhanVien))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(rdNam)
                                .addGap(83, 83, 83)
                                .addComponent(rdNu))
                            .addComponent(txtTenNhanVien, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(124, 124, 124)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(221, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(rdNam)
                            .addComponent(rdNu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SDT))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Emial)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdTruongPhong)
                            .addComponent(rdNhanVien)
                            .addComponent(jLabel4))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExitAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCccd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitAddActionPerformed
        quit();
    }//GEN-LAST:event_btnExitAddActionPerformed

    private void btnThemAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemAdd1ActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm nhân viên mới không?");
        if (testData() && trung()) {
            if (a == 0) {
                if (repo.add(getForm())) {
                    JOptionPane.showMessageDialog(this, "Bạn đã thêm thành công");
                    mail();
                    nv.updateTable();
                    quit();
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn đã thêm thất bại");
                }

            }
        }

    }//GEN-LAST:event_btnThemAdd1ActionPerformed

    private void btnCccdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCccdActionPerformed
        // TODO add your handling code here:
        // txtNgaySinh,txtTenNhanVien,tpDiaChi
        QrCode qr = new QrCode(null, true);
        qr.setVisible(true);
        qr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("window closing");
                tpDiaChi.setText(QrCode.diaChi);
                txtTenNhanVien.setText(QrCode.tenNv);

                if (QrCode.ngaySinh != null && !QrCode.ngaySinh.isEmpty()) {
                    SimpleDateFormat sdfInput = new SimpleDateFormat("ddMMyyyy");
                    SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date date = sdfInput.parse(QrCode.ngaySinh);
                        String formattedDate = sdfOutput.format(date);
                        txtNgaySinh.setDate(sdfOutput.parse(formattedDate));
                        System.out.println("Formatted Date: " + formattedDate);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    // Handle the case where nvbirth is null or empty
                    System.out.println("Date of birth is null or empty.");
                    // Optionally set a default value or handle accordingly
                    txtNgaySinh.setDate(null); // Clear or set a default value
                }

                qr.dispose();
            }
        });
    }//GEN-LAST:event_btnCccdActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void rdTruongPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTruongPhongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdTruongPhongActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Emial;
    private javax.swing.JLabel SDT;
    private javax.swing.JButton btnCccd;
    private javax.swing.JButton btnExitAdd;
    private javax.swing.JButton btnThemAdd1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNhanVien;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JRadioButton rdTruongPhong;
    private javax.swing.JTextPane tpDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLuong;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JPasswordField txtPassApp;
    private javax.swing.JTextField txtPassWord;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSubject;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTo;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
