/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate;

/**
 *
 * @author blinov_is
 */
public class UpdateModule {
   public enum ModuleType
   {
       Plugin,
       Controller
   }
    
    public String UUID;
   public String Version;
   public String URL;
   public ModuleType Type;
   
}
