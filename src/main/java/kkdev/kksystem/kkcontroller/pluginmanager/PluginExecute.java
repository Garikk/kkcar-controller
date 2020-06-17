/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.pluginmanager;

import java.util.ArrayList;
import java.util.HashMap;
import kkdev.kksystem.base.classes.plugins.PluginConnection;
import kkdev.kksystem.base.classes.plugins.FeatureConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.kkcontroller.main.systemoperations.SystemOperations;
import kkdev.kksystem.kkcontroller.main.utils.UtilsManager;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.mainConfiguration;
import kkdev.kksystem.base.interfaces.IPluginConnection;
import kkdev.kksystem.base.interfaces.IControllerUtils;
import kkdev.kksystem.base.interfaces.IBaseConnection;
import org.slf4j.LoggerFactory;

/**
 *
 * @author blinov_is
 */
public final class PluginExecute implements IBaseConnection {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(PluginExecute.class);
    //Pin path: FeatureID,SenderID,PIN,array of connectors
    HashMap<String, HashMap<String, HashMap<String, ArrayList<IPluginConnection>>>> Pin;
    HashMap<String, IPluginConnection> ActivePlugins;

    public PluginExecute(HashMap<String, IPluginConnection> Plugins) {
        ActivePlugins = Plugins;

        Pin = new HashMap();

        for (FeatureConfiguration Feature : mainConfiguration.features) {
            if (Feature.Connections != null) {
                for (PluginConnection PC : Feature.Connections) {
                    for (String PIN : PC.PinName) {
                        if (PIN != null) //Skip wrong config
                        {
                            RegisterPINTarget(Feature.FeatureUUID, PC.SourcePluginUID, PC.TargetPluginUID, PIN);
                            //Register multicast feature pin
                            RegisterPINTarget(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID, PC.SourcePluginUID, PC.TargetPluginUID, PIN);
                            //KKController may send any pin to any plugin in system feature
                            RegisterPINTarget(KK_BASE_FEATURES_SYSTEM_UID, PluginConsts.KK_PLUGIN_BASE_PLUGIN_UUID, PC.TargetPluginUID, PIN);
                        }
                    }
                }
            }
        }

    }

    private void RegisterPINTarget(String FeatureID, String SenderPluginUUID, String TargetPluginUID, String PIN) {

        if (!Pin.containsKey(FeatureID)) {
            Pin.put(FeatureID, new HashMap());
        }

        if (!Pin.get(FeatureID).containsKey(SenderPluginUUID)) {
            Pin.get(FeatureID).put(SenderPluginUUID, new HashMap());
        }

        if (!Pin.get(FeatureID).get(SenderPluginUUID).containsKey(PIN)) {
            Pin.get(FeatureID).get(SenderPluginUUID).put(PIN, new ArrayList<>());
        }

        //System.out.println("[PKK] Reg PIN " +PIN + " " +TargetPluginUID +" " +  ActivePlugins.get(TargetPluginUID));
        if (!Pin.get(FeatureID).get(SenderPluginUUID).get(PIN).contains(ActivePlugins.get(TargetPluginUID))) {
            Pin.get(FeatureID).get(SenderPluginUUID).get(PIN).add(ActivePlugins.get(TargetPluginUID));
        }

    }

    public void InitPlugins() {
        ActivePlugins.values().stream().forEach((CONN) -> {
            logger.info("init to: " + CONN.getPluginInfo().PluginUUID);
            CONN.pluginInit(this, mainConfiguration.configurationUID);
            logger.info("ok to: " + CONN.getPluginInfo().PluginName);
        });

    }

    public void StartPlugins() {
        ActivePlugins.values().stream().forEach((CONN) -> {
            CONN.pluginStart();
        });

    }

    public void StopPlugins() {
        ActivePlugins.values().stream().forEach((CONN) -> {
            CONN.pluginStop();
        });
    }

    @Override
    public void executePinCommand(PluginMessage PP) {
        internalExecutePin(PP);
    }

    private void internalExecutePin(PluginMessage PP) {
        if (PP.FeatureID == null) {
            logger.warn("[ERR] Wrong PluginMessage! Not found FeatureID Plugin: " + PP.SenderUID + " Pin: " + PP.pinName);
            return;
        }

        SystemBasePINReceiver(PP.cloneMessage());

        if (PP.FeatureID.contains(KK_BASE_FEATURES_SYSTEM_UID) & !PP.SenderUID.contains(PluginConsts.KK_PLUGIN_BASE_PLUGIN_UUID)) {
            return;
        }

        if (!Pin.keySet().containsAll(PP.FeatureID)) {
            logger.warn("Wrong PIN received (not found feature) FTR " + PP.FeatureID + " PIN " + PP.pinName);
            return;
        }
        for (String Ftr : PP.FeatureID) {
            if (!Pin.get(Ftr).containsKey(PP.SenderUID)) {
                logger.warn("Wrong PIN received (not found sender) FTR " + PP.FeatureID + " PIN " + PP.pinName);
                return;
            }

            if (!Pin.get(Ftr).get(PP.SenderUID).containsKey(PP.pinName)) {
                logger.warn("Wrong PIN received (Not found Pin) FTR " + PP.FeatureID + " PIN " + PP.pinName);
                return;
            }
        }

        ArrayList<IPluginConnection> Exec;

        if (PP.FeatureID.contains(SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID) || PP.FeatureID.contains(SystemConsts.KK_BASE_FEATURES_SYSTEM_BROADCAST_UID)) {
            Exec = Pin.get(SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID).get(PP.SenderUID).get(PP.pinName);
            InternalExecutePin_Exec(Exec, PP);
        } else {
            for (String Ftr : PP.FeatureID) {
                Exec = Pin.get(Ftr).get(PP.SenderUID).get(PP.pinName);
                InternalExecutePin_Exec(Exec, PP);
            }
        }

    }

    private void InternalExecutePin_Exec(ArrayList<IPluginConnection> Exec, PluginMessage PP) {
        Exec.stream().forEach((PKK) -> {
            PKK.executePin(PP.cloneMessage());
        });
    }

    @Override
    public void _executePinCommandDirect(String PluginUUID, PluginMessage PP) {
        ExecuteDirectCommand(PluginUUID, PP);
    }

    public void ExecuteDirectCommand(String TargetUUID, PluginMessage PP) {
        if (TargetUUID.equals("")) {
            TargetUUID = KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
        }

        SystemBasePINReceiver(PP);

        if (TargetUUID.equals(KK_BASE_FEATURES_SYSTEM_BROADCAST_UID) | TargetUUID.equals(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID)) {
            for (IPluginConnection PKK : ActivePlugins.values()) {
                PKK.executePin(PP.cloneMessage());
            }
            return;
        } else {
            ActivePlugins.get(TargetUUID).executePin(PP.cloneMessage());
        }
    }

    private void SystemBasePINReceiver(PluginMessage PP) {
        //Standart PINs
        if (PP.FeatureID.contains(KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID) || PP.FeatureID.contains(KK_BASE_FEATURES_SYSTEM_UID)) {
            SystemOperations.processSystemPIN(PP);
        }

        //Special PINs
        switch (PP.pinName) {
            case (PluginConsts.KK_PLUGIN_BASE_LED_COMMAND):
                SystemOperations.processSpecialPIN(PP);
                return;
        }

    }

    @Override
    public IControllerUtils systemUtilities() {
        return UtilsManager.getInstance();
    }

}
