
package com.raven.menu;

import java.awt.Component;
import java.awt.MenuItem;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Menuanimation {
    public static void showMenu(Component component, MenuItems item, MigLayout layout, boolean show) {
        int height = component.getPreferredSize().height;
        Animator animator = new Animator(300, new TimingTargetAdapter(){
            @Override
            public void timingEvent(float  fraction){
                float f = show?fraction:1f-fraction;
                layout.setComponentConstraints(component, "h "+height * f + "!");
                item.setAnimate(f);
                component.revalidate();
            }
        });
        
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        animator.start();
    }
}
