/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import swing.Notification;

/**
 *
 * @author Ca1
 */
public class Validate {

    private boolean chuoiHopLe = true;
    Map<String, String> listWarning = new LinkedHashMap<>();

    public Validate() {
    }

    public void checkNull(String label, String giaTri) {
        if (giaTri.trim().isEmpty()) {
            listWarning.put(label, label + " không được để trống");
            chuoiHopLe = false;
        }
    }

    ;
    
    public void checkNumber(String label, String number) {
        try {
            Integer.parseInt(number);
        } catch (Exception e) {
            if (!number.isEmpty()) {
                listWarning.put(label, label + " chỉ được chứa số");
            }
            chuoiHopLe = false;
        }
    }

    public boolean isChuoiHopLe() {
        return chuoiHopLe;
    }

    public void setChuoiHopLe(boolean chuoiHopLe) {
        this.chuoiHopLe = chuoiHopLe;
    }

    public void showWarning(JPanel panel) {
        int time = 0;
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);

        for (Map.Entry<String, String> entry : listWarning.entrySet()) {
            Timer timer = new Timer(time, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Notification notii = new Notification(parentFrame, Notification.Type.WARNING, Notification.Location.TOP_RIGHT, entry.getValue());
                    notii.showNotification();
                }
            });
            timer.setRepeats(false);
            timer.start();
            time += 3000;
        }
    }
}
