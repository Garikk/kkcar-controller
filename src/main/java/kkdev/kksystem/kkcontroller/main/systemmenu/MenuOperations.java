/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;


import kkdev.kksystem.base.classes.base.PinDataFtrCtx;
import kkdev.kksystem.base.classes.base.PinDataSystemOperations;
import static kkdev.kksystem.base.classes.base.PinDataSystemOperations.SystemOperationsCommand.SYSTEM_CHANGE_MANAGEDPARAMETER;
import kkdev.kksystem.base.classes.base.PluginMessageData;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PIN_SYSTEMOPERATION;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_UUID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_BROADCAST_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import static kkdev.kksystem.kkcontroller.main.systemmenu.SystemMenu.*;
import kkdev.kksystem.base.interfaces.IBaseConnection;


/**
 *
 * @author sayma_000
 */
public final class MenuOperations {

    public static void ExecSysMenuOperation(IBaseConnection BCE,String[] Command) {
        switch (Command[0]) {
            case MNU_CMD_SYSMENU_PFX_INFO:
                switch (Command[1]) {
                    case MNU_CMD_BRD_INFO_PLUGINS:
                        break;
                    case MNU_CMD_BRD_INFO_VERSION:
                        break;

                }
                break;
            case MNU_CMD_SYSMENU_PFX_BRDTOOLS:
                switch (Command[1]) {
                    case MNU_CMD_BRD_TOOLS_BOARDINFO:
                        break;
                    case MNU_CMD_BRD_TOOLS_POWEROFF:
                        break;
                    case MNU_CMD_BRD_TOOLS_REBOOT:
                        break;
                    case MNU_CMD_BRD_TOOLS_CHANGE_MANAGEDPARAMETER:
                        changeManagedParameter(BCE,Command);
                        break;
                }
                break;
            
        }
    }

    private static void CreateToolsInfoPages()
    { 
       
    
    }
    private static void ExecSysMenuOperationPluginList(String[] Command) {

    }
    
    private static void ExecSysMenuOperationKKTools(String[] Command)
    {
    }
    
    private static void changeManagedParameter(IBaseConnection BCE,String[] Command)
    {
        String PluginUUID=Command[2];
        String ParameterName=Command[3];
        String ParameterValue=Command[4];
        PinDataSystemOperations PData = new PinDataSystemOperations();
        PData.CommandType=SYSTEM_CHANGE_MANAGEDPARAMETER;
        PData.ParameterName=ParameterName;
        PData.ParameterValue=ParameterValue;

        PluginMessageData Msg = new PluginMessageData(PData);
        Msg.pinName = KK_PLUGIN_BASE_PIN_SYSTEMOPERATION;
        Msg.FeatureID.add(KK_BASE_FEATURES_SYSTEM_UID);
        Msg.SenderUID=KK_PLUGIN_BASE_PLUGIN_UUID;
        
        BCE._executePinCommandDirect(PluginUUID, Msg);

    
    }
}
