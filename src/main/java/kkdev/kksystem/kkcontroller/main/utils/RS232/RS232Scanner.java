/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils.RS232;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import kkdev.kksystem.base.classes.kkcontroller.RS232Device.RS232DevType;
import kkdev.kksystem.base.classes.kkcontroller.RS232Device;

/**
 *
 * @author sayma_000
 */
public class RS232Scanner {

    public List<RS232Device> RS232Ports;
    SerialPort serialPort;

    public void MakeRS232DevList() {
        RS232Ports = new ArrayList<>();

        String[] portNames;
        portNames = SerialPortList.getPortNames();
        for (String PN : portNames) {
            RS232Ports.add(new RS232Device(PN));
        }
        CheckRS232Devices();
    }

    private void CheckRS232Devices() {
        for (int i = 0; i < RS232Ports.size(); i++) {
            RS232Ports.get(i).PortType = GetPortDevice(RS232Ports.get(i).PortName);
        }
    }

    private RS232DevType GetPortDevice(String DevName) {
        RS232DevType Ret = RS232DevType.Other;
        serialPort = new SerialPort(DevName);
        try {
            out.println("Check " + DevName);
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.writeString("ATI\r\n");

            int i = 0;
            boolean Ok = false;
            while (i < 5 && Ok == false) {
                String buffer = serialPort.readString();
                System.out.println("[UTIL] ANS " + buffer);
                if (buffer != null) {
                    if (IsModem(buffer)) {
                        Ret = RS232DevType.Dev3GModem;
                        Ok = true;
                    }

                    if (IsELM(buffer)) {
                        Ret = RS232DevType.DevELM327;
                        Ok = true;
                    }
                    if (IsSmarthead(buffer)) {
                        Ret = RS232DevType.DevSmarthead;
                        Ok = true;
                    }
                }
                if (Ok != true) {
                    i++;
                    Thread.sleep(200);
                }
            }
            //Closing the port
            serialPort.closePort();
        } catch (SerialPortException ex) {
            Ret = RS232DevType.Error;
        } catch (InterruptedException ex) {
            Logger.getLogger(RS232Scanner.class.getName()).log(Level.SEVERE, null, ex);
        }

        out.println("Result " + Ret);
        return Ret;
    }

    public boolean IsModem(String CheckString) {
        if (CheckString.contains("IMEI")) {
            return true;
        } else if (CheckString.contains("CGSM")) {
            return true;
        }

        return false;
    }

    public boolean IsELM(String CheckString) {
        return CheckString.contains("ELM");
    }

    public boolean IsSmarthead(String CheckString) {
        return CheckString.contains("KKSmarthead");
    }
}
