/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils.display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kkdev.kksystem.base.classes.display.pages.DisplayPage;

/**
 *
 * @author blinov_is
 */
public class DisplayPageStorage {
    HashMap<String,DisplayPage> DisplayPages;
    
    public DisplayPageStorage()
    {
        DisplayPages=new HashMap<>();
    }
    public DisplayPage GetPage(String PageID)
    {
        return DisplayPages.get(PageID).GetInstance();
    }
    public void AddPage(DisplayPage Page)
    {
        DisplayPages.put(Page.PageName, Page);
    }
    public List<DisplayPage> GetPages()
    {
        List<DisplayPage> Ret = new ArrayList<>();
        Ret.addAll(DisplayPages.values());
        return Ret;
    }


}
