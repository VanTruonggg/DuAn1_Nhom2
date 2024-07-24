/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.repository;

import com.raven.entity.PhieuGiamGia;
import com.raven.form.PhieuGiamGiaForm;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pham Thu
 */
public class UpdateSatus {

    PhieuGiamGiaRespository repo = new PhieuGiamGiaRespository();
    PhieuGiamGia pgg = new PhieuGiamGia();
//    private ScheduledExecutorService scheduler;
//    private PhieuGiamGiaForm form = new PhieuGiamGiaForm();
//
//    @Override
//    public void run() {
//        checkAndUpdateExpiry();
//    }
//
//    public void checkAndUpdateExpiry() {
//        try {
//            Date now = new Date();
//            if (now.after(pgg.getNgayKetThuc())) {
//                pgg.setTrangThai("Hết hạn");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        PhieuGiamGiaRespository repo = new PhieuGiamGiaRespository();
        PhieuGiamGiaForm form = new  PhieuGiamGiaForm();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Date currentDate = new Date();
                for (PhieuGiamGia pgg : repo.getAll()) {
                    if (currentDate.after(pgg.getNgayKetThuc())) {
                        pgg.setTrangThai("Hết hạn");
                        System.out.println("mã " + pgg.getMa() + " tranng thai " + pgg.getTrangThai());
                        
                    }
                }
            }
        }).start();
    }
//    private void startUIUpdateThread() {
//        ScheduledExecutorService uiUpdater = Executors.newScheduledThreadPool(1);
//        uiUpdater.scheduleAtFixedRate(() -> {
//            SwingUtilities.invokeLater(this::updateTable);
//        }, 0, 1, TimeUnit.MINUTES); // Cập nhật UI mỗi phút
//    }
//    private void updateTable(ArrayList<PhieuGiamGia> list) {
//        dtm.setRowCount(0);
//        AtomicInteger index = new AtomicInteger(1);
//        for (PhieuGiamGia pgg : list) {
//            dtm.addRow(new Object[]{
//                index.getAndIncrement(), pgg.getMa(), pgg.getTen(), pgg.getSoLuong(), pgg.getLoai(), pgg.getGiaTriToiThieu(),
//                pgg.getGiaTriToiDa(), pgg.getNgayBatDau(), pgg.getNgayKetThuc(), pgg.getTrangThai()
//            });
//        }
//    }
    
}

//    private void updateSatus() {
//        Date currentDate = new Date();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (PhieuGiamGia pgg : repo.getAll()) {
//                    if (currentDate.after(pgg.getNgayKetThuc())) {
//                        pgg.setTrangThai("Hết hạn");
//                        System.out.println("ma " + pgg.getMa() + "Trang thai" + pgg.getTrangThai());
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(PhieuGiamGiaForm.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//            }
//        }
//        ).start();
//    }

