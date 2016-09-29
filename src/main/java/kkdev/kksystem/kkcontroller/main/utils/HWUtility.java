/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils;

import java.util.List;
import kkdev.kksystem.base.classes.kkcontroller.RS232Device;
import kkdev.kksystem.base.interfaces.IHWDevicesUtils;
import kkdev.kksystem.kkcontroller.main.utils.RS232.RS232Scanner;
import kkdev.kksystem.kkcontroller.main.utils.display.DisplayPageStorage;

/**
 *
 * @author blinov_is
 */
public class HWUtility implements IHWDevicesUtils {

    private RS232Scanner RS232ScanUtility;

    public HWUtility() {
        RS232ScanUtility = new RS232Scanner();
    }

    @Override
    public List<RS232Device> getRS232Devices() {
        return RS232ScanUtility.RS232Ports;
    }

    public RS232Scanner getRS232Scanner() {
        return RS232ScanUtility;
    }

}
