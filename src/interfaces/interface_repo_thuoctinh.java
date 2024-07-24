/*
 * Click nbfs://nbhost/SystemFileSystem/Objectemplates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Objectemplates/Classes/Class.java to edit this template
 */
package interfaces;

import java.util.ArrayList;

/**
 *
 * @author Ca1
 * @param <Object>
 */
public interface interface_repo_thuoctinh {
    public void addThuocTinh(Object x);

    public void updateThuocTinh(Object x);

    public void removeThuocTinh(Object x);

    public ArrayList<Object> loadListThuocTinhFromDb();
}
