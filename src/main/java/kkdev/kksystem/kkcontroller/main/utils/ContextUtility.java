/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils;

import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.kkcontroller.UIContextInfo;
import kkdev.kksystem.base.interfaces.IContextUtils;
import kkdev.kksystem.kkcontroller.main.systemoperations.SystemOperations;

/**
 *
 * @author blinov_is
 */
public class ContextUtility implements IContextUtils {

    private static UIContextStorage UICtxStor;

    public ContextUtility() {
        UICtxStor = new UIContextStorage();
    }

    @Override
    public void AddUIContext(String UIContextID) {
        UIContextInfo UIC = new UIContextInfo(UIContextID);
        UICtxStor.AddContext(UIC);
    }

    @Override
    public UIContextInfo GetUIContextInfo(String ContextID) {
        //Fill additional indo
        UIContextInfo Ret;
        Ret = UICtxStor.GetContext(ContextID);

        Ret.ActiveFeature = SystemOperations.getActiveFeatureID(ContextID);
        Ret.ActivePage = SystemOperations.ActivePage;

        return Ret;
    }

    @Override
    public void UpdateDisplayInUIContext(String ContextID, DisplayInfo DI) {
        UICtxStor.GetContext(ContextID).UIDisplay = DI;
    }

}
