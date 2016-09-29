/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;

import kkdev.kksystem.base.classes.display.pages.DisplayPage;
import static kkdev.kksystem.base.classes.display.pages.PageConsts.*;
import kkdev.kksystem.base.classes.display.pages.UIFrameData;
import kkdev.kksystem.base.classes.display.pages.UIFramePack;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import kkdev.kksystem.kkcontroller.main.utils.UtilsManager;

/**
 *
 * @author blinov_is
 */
public class kk_defaultUI {

    public static void AddDefaultSystemUIPages() {
        DisplayPage DP;
        UIFramePack[] FramePack;
        //
        FramePack=GetSystemFramePack();
        //
        DP = new DisplayPage();
        DP.dynamicElements = false;
        DP.contexts=new String[1];
        DP.contexts[0]= SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        DP.features = new String[1];
        DP.features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.pageName = KK_DISPLAY_PAGES_SIMPLEMENU_TXT_C1RX_PREFIX;
        DP.isDefaultPage = false;
        DP.isMultifeaturePage = true;
        DP.framesPack = FramePack[0];
        //
        UtilsManager.getInstance().DisplayFunctions().AddUIDisplayPage(DP);
        //
        DP = new DisplayPage();
        DP.dynamicElements = false;
        DP.features = new String[1];
        DP.features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.contexts=new String[1];
        DP.contexts[0]= SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        DP.pageName = KK_DISPLAY_PAGES_SIMPLECLOCK_TXT_PREFIX;
        DP.isDefaultPage = false;
        DP.isMultifeaturePage = true;
        DP.framesPack = GetSystemFramePack()[1];
        //
        UtilsManager.getInstance().DisplayFunctions().AddUIDisplayPage(DP);
        //
   
    }

    private static UIFramePack[] GetSystemFramePack() {
        UIFramePack[] Ret = new UIFramePack[7];
        Ret[0] = new UIFramePack();
        Ret[0].name = "KKController System menu";
        Ret[0].packID = "";
        Ret[0].data = new UIFrameData[2];
        Ret[0].data[0] = new UIFrameData();
        Ret[0].data[1] = new UIFrameData();
   
        //
        Ret[0] = new UIFramePack();
        Ret[0].name = "SYSTEM MENU 1 Col X Row";
        Ret[0].packID = "";
        Ret[0].data = new UIFrameData[1];
        Ret[0].data[0] = new UIFrameData();
        Ret[0].data[0].frameData = "[SEL_[$COUNT]][SYSMENU_[$COUNT]][SEL_[$COUNT]]\r\n";
        Ret[0].data[0].fontSize = 2;
      
        Ret[1] = new UIFramePack();
        Ret[1].name = "Default Clock";
        Ret[1].packID = "";
        Ret[1].data = new UIFrameData[1];
        Ret[1].data[0] = new UIFrameData();
        Ret[1].data[0].frameData = "           \r\n           \r\n [KK_PL_TIME] \r\n           \r\n           ";
        Ret[1].data[0].fontSize = 3;
        return Ret;
    }

}

