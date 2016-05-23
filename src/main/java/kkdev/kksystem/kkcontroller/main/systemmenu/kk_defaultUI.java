/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.systemmenu;

import static java.lang.System.out;
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
        DP.DynamicElements = false;
        DP.UIContexts=new String[1];
        DP.UIContexts[0]= SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        DP.Features = new String[1];
        DP.Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.PageName = KK_DISPLAY_PAGES_SIMPLEMENU_TXT_C1RX_PREFIX;
        DP.UIContexts = new String[2];

        DP.IsDefaultPage = false;
        DP.IsMultifeaturePage = true;
        DP.UIFramesPack = FramePack[0];
        //
        UtilsManager.getInstance().DISPLAY_AddUIDisplayPage(DP);
        //
        DP = new DisplayPage();
        DP.DynamicElements = false;
        DP.Features = new String[1];
        DP.Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP.UIContexts=new String[1];
        DP.UIContexts[0]= SystemConsts.KK_BASE_UICONTEXT_DEFAULT;
        DP.PageName = KK_DISPLAY_PAGES_SIMPLECLOCK_TXT_PREFIX;
        DP.IsDefaultPage = false;
        DP.IsMultifeaturePage = true;
        DP.UIFramesPack = GetSystemFramePack()[1];
        //
        UtilsManager.getInstance().DISPLAY_AddUIDisplayPage(DP);
        //
   
    }

    private static UIFramePack[] GetSystemFramePack() {
        UIFramePack[] Ret = new UIFramePack[7];
        Ret[0] = new UIFramePack();
        Ret[0].Name = "KKController System menu";
        Ret[0].PackID = "";
        Ret[0].Data = new UIFrameData[2];
        Ret[0].Data[0] = new UIFrameData();
        Ret[0].Data[1] = new UIFrameData();
   
        //
        Ret[0] = new UIFramePack();
        Ret[0].Name = "SYSTEM MENU 1 Col X Row";
        Ret[0].PackID = "";
        Ret[0].Data = new UIFrameData[1];
        Ret[0].Data[0] = new UIFrameData();
        Ret[0].Data[0].FrameData = "[SEL_[$COUNT]][SYSMENU_[$COUNT]][SEL_[$COUNT]]\r\n[SEL_[$COUNT]][SYSMENU_[$COUNT]][SEL_[$COUNT]]";
        Ret[0].Data[0].FontSize = 2;
      
        Ret[1] = new UIFramePack();
        Ret[1].Name = "Default Clock";
        Ret[1].PackID = "";
        Ret[1].Data = new UIFrameData[1];
        Ret[1].Data[0] = new UIFrameData();
        Ret[1].Data[0].FrameData = "           \r\n           \r\n [KK_PL_TIME] \r\n           \r\n           ";
        Ret[1].Data[0].FontSize = 3;
        return Ret;
    }

}

