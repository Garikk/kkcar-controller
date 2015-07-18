/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate;

/**
 *
 * @author sayma_000
 */
public class KKMasterAnswer {
    public static enum KKM_ANS_States
    {
        ANS_CONF_OK,
        ANS_ERR
    }
    
    public KKM_ANS_States AnswerState;
    public int Version;
    public String JsonData;
    
}
