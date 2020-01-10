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
    PRF_ODB_DATAVIEW,
    PRF_MEDIACENTER,
    PRF_BLUETOOTH_RPI,
    PRF_BLUETOOTH_ANDROID,
    PRF_ANDROID_EXT_CONNECTOR,
    PRF_ANDROID_HOST,
    PRF_UNKNOWN;

    public static List<kk_DefConfProfileTypes> GetProfiles(List<String> Profiles) {
        List<kk_DefConfProfileTypes> Ret = new ArrayList();
        Profiles.forEach((String i) -> {
            try {
                Ret.add(kk_DefConfProfileTypes.valueOf(i));
            } catch (IllegalArgumentException ex) {
                Ret.add(kk_DefConfProfileTypes.PRF_UNKNOWN);
            }
        });
        return Ret;
    }
}
