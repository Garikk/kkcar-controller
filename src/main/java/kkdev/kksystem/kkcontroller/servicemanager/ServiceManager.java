/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.servicemanager;

/**
 *
 * @author sayma
 */
public final class ServiceManager {
    public String SessionUID;
    
    public ServiceManager()
    {
        SessionUID = java.util.UUID.randomUUID().toString();
    }

