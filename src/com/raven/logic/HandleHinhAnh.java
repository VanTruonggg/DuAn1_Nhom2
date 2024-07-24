/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.raven.logic;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Ca1
 */
public class HandleHinhAnh {

    public static void setImg(JLabel jLabel, String relativePath) {
        ImageIcon imageIcon = new ImageIcon(relativePath);
        Image image = imageIcon.getImage();

        // Thay đổi kích thước của ảnh
        Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Chỉnh lại kích thước ảnh

        // Tạo lại ImageIcon từ ảnh đã thay đổi kích thước
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        jLabel.setIcon(scaledIcon);
    }

    public static String chooseImage(JLabel jLabel, Component parent) {
        JFileChooser fileChooser = new JFileChooser("./src/img");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getPath();
            String keyword = "src";

            // Find the index of the keyword
            int startIndex = filePath.indexOf(keyword);

            if (startIndex != -1) {
                // Extract the substring from the keyword to the end
                String extractedPath = filePath.substring(startIndex);
                setImg(jLabel, extractedPath);
                return extractedPath;
            } else {
                System.out.println("Keyword not found in the given path.");
            }
        }
        return null;
    }
}
