/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.logic;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import swing.Notification;

/**
 *
 * @author Ca1
 */
public class ShowMessageCustom {

    public static void showMessageWarning(JPanel panel, String message) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        Notification notii = new Notification(parentFrame, Notification.Type.WARNING, Notification.Location.TOP_RIGHT, message);
        notii.showNotification();
    }
    
    public static void showMessageSuccess(JPanel panel, String message) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        Notification notii = new Notification(parentFrame, Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, message);
        notii.showNotification();
    }
}
