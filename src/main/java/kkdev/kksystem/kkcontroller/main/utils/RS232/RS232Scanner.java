/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils.RS232;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import jssc.SerialPort;
import jssc.SerialPortList;

/**
 *
 * @author sayma_000
 */
public class RS232Scanner {
   
    public static List<RS232Device> RS232Ports;
    
    
   public class RS232Device
   {
        public String PortName;
        public RS232DevType PortType;
        public RS232Device(String PName)
        {
            PortName=PName;
        }
    }
  public  enum RS232DevType
    {
        Dev3GModem,
        DevELM327,
        Other
    }

    
  
    
    
   public static void MakeRS232DevList() {
       RS232Ports=new ArrayList<>();
       
       
        //Method getPortNames() returns an array of strings. Elements of the array is already sorted.
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            RS232Ports.add(new RS232DevType(i));
        }
    }
}
