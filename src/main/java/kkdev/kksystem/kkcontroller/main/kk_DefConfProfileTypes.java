/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Garikk
 */
public enum kk_DefConfProfileTypes {
    ODB_DATAVIEW,
    MEDIACENTER,
    BLUETOOTH_RPI,
    BLUETOOTH_ANDROID,
    ANDROID_EXT_CONNECTOR,
    ANDROID_HOST,
    UNKNOWN;

    public static List<kk_DefConfProfileTypes> GetProfiles(List<String> Profiles) {
        List<kk_DefConfProfileTypes> Ret = new ArrayList();
        Profiles.forEach((String i) -> {
            try {
                Ret.add(kk_DefConfProfileTypes.valueOf(i));
            } catch (IllegalArgumentException ex) {
                Ret.add(kk_DefConfProfileTypes.UNKNOWN);
            }
        });
        return Ret;
    }
}
