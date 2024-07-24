/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Ca1
 */
public class CustomHeaderRenderer extends DefaultTableCellRenderer {

    public CustomHeaderRenderer() {
    }

    ;
    
    public CustomHeaderRenderer(JTable table) {
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Times New Roman", Font.BOLD, 14)); // Đặt font cho header
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBackground(new Color(15, 187, 245)); // Đặt màu nền cho header
        c.setForeground(Color.WHITE); // Đặt màu chữ cho header
        return c;
    }
}
