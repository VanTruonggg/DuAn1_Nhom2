package com.raven.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class Menu extends JComponent {

    private MenuEvent event;

    public MenuEvent getEvent() {
        return event;
    }

    public void setEvent(MenuEvent event) {
        this.event = event;
    }
    
    private MigLayout layout;
    
    private String[][] menuItems = new String[][]{
        {"BÁN HÀNG"},
        {"SẢN PHẨM"},
        {"NHÂN VIÊN"},
        {"HÓA ĐƠN"},
        {"KHÁCH HÀNG"},
        {"VOUCHER"},
        {"THỐNG KÊ"},
        {""},
        {""},
        {"ĐĂNG XUẤT"}
    };

    public Menu() {
        init();
    }

    private void init() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);
        for (int i = 0; i < menuItems.length; i++) {
            addMenu(menuItems[i][0], i);
        }
    }

    private Icon getIcon(int index) {
        URL url = getClass().getResource("/com/raven/icon/" + index + ".png");
        if (url != null) {
            return new ImageIcon(url);
        } else {
            return null;
        }

    }

    private void addMenu(String menuName, int index) {
        int length = menuItems[index].length;
        MenuItems item = new MenuItems(menuName, index, length > 1);
        Icon icon = getIcon(index);
        if (icon != null) {
            item.setIcon(icon);
        }
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (length > 1) {
                    if (!item.isSelected()) {
                        item.setSelected(true);
                        addSubMenu(item, index, length, getComponentZOrder(item));

                    } else {

                        hideMenu(item, index);
                        item.setSelected(false);
                    }
                } else {
                    if (event!= null) {
                        event.selected(index, 0);
                    }
                }
            }
        });
        add(item);
        revalidate();
        repaint();
    }

    private void addSubMenu(MenuItems item, int index, int length, int indexZorder) {
        JPanel panel = new JPanel(new MigLayout("wrap 1, fillx, inset 0 ,gapy 0", "fill"));
        panel.setName(index + "");
        panel.setOpaque(false);
        for (int i = 1; i < length; i++) {
            MenuItems subItem = new MenuItems(menuItems[index][i], i, false);
            subItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (event!= null) {
                        event.selected(index, subItem.getIndex());
                    }
                }
            });
            subItem.initSubMenu(i, length);
            panel.add(subItem);
        }

        add(panel, "h 0!", indexZorder + 1);
        revalidate();
        repaint();
        Menuanimation.showMenu(panel, item, layout, true);
    }

    private void hideMenu(MenuItems item, int index) {
        for (Component com : getComponents()) {
            if (com instanceof JPanel && com.getName() != null && com.getName().equals(index + "")) {
                com.setName(null);
                Menuanimation.showMenu(com, item, layout, false);
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(89,168,104));
//        GradientPaint gd = new GradientPaint(0, 0, Color.decode("#F37335"), 0, getHeight(), Color.decode("#F37335"));
//        g2.setPaint(gd);
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(g);
    }

}
