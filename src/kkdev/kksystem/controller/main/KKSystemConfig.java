/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.main;

import java.io.Serializable;
import kkdev.kksystem.base.classes.PluginInfo;
import kkdev.kksystem.base.classes.PluginPin;
import kkdev.kksystem.controller.pluginmanager.PinConnections;



/**
 *
 * @author blinov_is
 */
public class KKSystemConfig implements Serializable {
    public PluginInfo[] ConfPlugins;
    public PluginPin[] ConfPins;
    public PinConnections ConfConnections;
}
