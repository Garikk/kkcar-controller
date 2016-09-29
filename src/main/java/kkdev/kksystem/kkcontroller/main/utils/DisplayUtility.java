/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils;

import java.util.List;
import kkdev.kksystem.base.classes.display.pages.DisplayPage;
import kkdev.kksystem.base.interfaces.IDisplayUtils;
import kkdev.kksystem.kkcontroller.main.utils.display.DisplayPageStorage;

/**
 *
 * @author blinov_is
 */
public class DisplayUtility implements IDisplayUtils {

    private static DisplayPageStorage DPStor;

    public DisplayUtility() {
        DPStor = new DisplayPageStorage();
    }

    @Override
    public List<DisplayPage> GetUIDisplayPages() {
        return DPStor.GetPages();
    }

    @Override
    public void AddUIDisplayPage(DisplayPage Page) {

        DPStor.AddPage(Page);
    }

    @Override
    public DisplayPage GetUIDisplayPage(String Page) {
        return DPStor.GetPage(Page);
    }

}
