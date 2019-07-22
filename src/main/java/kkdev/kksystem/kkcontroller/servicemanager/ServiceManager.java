/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.servicemanager;

import static java.util.UUID.randomUUID;

/**
 *
 * @author sayma
 */
public final class ServiceManager {
    public String SessionUID;
    
    public ServiceManager()
    {
        SessionUID = randomUUID().toString();
    }

}