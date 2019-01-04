/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.servicemanager;

import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.interfaces.IBaseConnection;
import kkdev.kksystem.base.interfaces.IControllerUtils;

/**
 *
 * @author sayma
 */
public class ServiceManager implements IBaseConnection {

    @Override
    public void executePinCommand(PluginMessage PP) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void _executePinCommandDirect(String PluginUUID, PluginMessage PP) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IControllerUtils systemUtilities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
