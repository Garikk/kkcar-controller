/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main.utils;

import java.util.HashMap;
import kkdev.kksystem.base.classes.kkcontroller.UIContextInfo;

/**
 *
 * @author blinov_is
 */
public class UIContextStorage {
      HashMap<String,UIContextInfo> UIContexts;
    
    public UIContextStorage()
    {
        UIContexts=new HashMap<>();
    }
    public UIContextInfo GetContext(String PageID)
    {
        return UIContexts.get(PageID);
    }
    public void AddContext(UIContextInfo UIContext)
    {
        UIContexts.put(UIContext.UIContextID, UIContext);
    }
    

}
