package com.raven.menu;

import com.raven.effect.RippleEffect;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MenuItems extends JButton {

    private int index;

    private boolean subMenuAble;

    private int subMenuIdex;

    private int length;
    
    private float animate;
    
    private RippleEffect rippleEffect;
    

    public float getAnimate() {
        return animate;
    }

    public void setAnimate(float animate) {
        this.animate = animate;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isSubMenuAble() {
        return subMenuAble;
    }

    public void setSubMenuAble(boolean subMenuAble) {
        this.subMenuAble = subMenuAble;
    }

    public int getSubMenuIdex() {
        return subMenuIdex;
    }

    public void setSubMenuIdex(int subMenuIdex) {
        this.subMenuIdex = subMenuIdex;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public MenuItems(String name, int index, boolean subMenuAble) {
        super(name);
        this.index = index;
        this.subMenuAble = subMenuAble;
        setContentAreaFilled(false);
        setForeground(new Color(230, 230, 230));
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(new EmptyBorder(9, 10, 9, 10));
        setIconTextGap(10);
        rippleEffect = new RippleEffect(this);
        rippleEffect.setRippleColor(new Color(220,220,220));
    }

    public void initSubMenu(int subMenuIdex, int length) {
        this.subMenuIdex = subMenuIdex;
        this.length = length;
        setBorder(new EmptyBorder(9, 33, 9, 10));
        setBackground(new Color(18, 99, 63));
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (length != 0) {
            g2.setColor(new Color(43, 141, 98));
            if (subMenuIdex == 1) {
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);

            } else if (subMenuIdex == length - 1) {
                g2.drawLine(18, 0, 18, getHeight() / 2);
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);

            } else {
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);

            }
        } else if (subMenuAble) {
            g2.setColor(getForeground());
            int arroWidth = 8;
            int arroHeight = 4;
            Path2D p = new Path2D.Double();
            p.moveTo(0, arroHeight*animate);
            p.lineTo(arroWidth / 2, (1f-animate)*arroHeight);
            p.lineTo(arroWidth, arroHeight*animate);
            g2.translate(getWidth() - arroWidth - 15, (getHeight() - arroHeight*5));
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.draw(p);
        }
        g2.dispose();
        rippleEffect.reder(g, new Rectangle2D.Double(0, 0, getWidth(), getHeight()) );
    }

}
