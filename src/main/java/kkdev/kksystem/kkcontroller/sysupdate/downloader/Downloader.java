/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate.downloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.out;
import java.util.Set;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import static kkdev.kksystem.base.constants.SystemConsts.*;
import kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater;
import static kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater.GetExternalConfigurationsInfo;
import static kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater.GetPluginFilesInfo;
import kkdev.kksystem.kkcontroller.sysupdate.webmasterconnection.WM_Configuration_Data;
import kkdev.kksystem.kkcontroller.sysupdate.webmasterconnection.WM_File_Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import static org.apache.http.impl.client.HttpClientBuilder.create;

/**
 *
 * @author blinov_is
 */
public abstract class Downloader {

    public static void DownloadFiles(String ConfigUID, Set<String> ReqPlugins) {
        out.println("Download updates");
        //
        // Create updater folders
        //
        // Base Update folder
        
        File TempPath = new java.io.File(KK_BASE_UPDATE_TEMP);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
         //
        // Main backup folder
        //
        TempPath = new java.io.File(KK_BASE_BACKUP);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        // Main backup folder
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_REPAIRBACKUP);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        // Second backup folder
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_REPAIRBACKUP_2);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        // Second backup folder
        //
        TempPath = new java.io.File(KK_BASE_EMERGENCYREPAIR);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
  
        //
        //
        // Emergency backup folder
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_PLUGINS);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_BASE);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
         TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_CONF);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_EXTCONF);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        //
        WM_File_Data[] BinFilesToDownload = GetPluginFilesInfo(ReqPlugins);
        WM_File_Data[] ConfFilesToDownload = GetExternalConfigurationsInfo(ConfigUID);

        if (BinFilesToDownload != null) {
            for (WM_File_Data F : BinFilesToDownload) {
                DownloadFile(KK_BASE_UPDATE_TEMP_PLUGINS, F.url, F.name);
            }
        }

        if (ConfFilesToDownload != null) {
            for (WM_File_Data F : ConfFilesToDownload) {
                DownloadFile(KK_BASE_UPDATE_TEMP_EXTCONF, F.url, F.name);
            }
        }
    }

    private static void DownloadFile(String Dir, String URL, String TargetName) {

        HttpClient client = create().build();

        BufferedOutputStream bos = null;
        try {
            HttpGet httpget = new HttpGet(URL);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();

            try (BufferedInputStream bis = new BufferedInputStream(entity.getContent())) {
                String filePath = Dir+"/"+TargetName;
                bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                int inByte;
                while ((inByte = bis.read()) != -1) {
                    bos.write(inByte);
                }
            }
            bos.close();
        } catch (FileNotFoundException ex) {
            getLogger(Downloader.class.getName()).log(SEVERE, null, ex);
        } catch (IOException ex) {
            getLogger(Downloader.class.getName()).log(SEVERE, null, ex);
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                getLogger(Downloader.class.getName()).log(SEVERE, null, ex);
            }
        }
    }

    public static void SaveConfigFiles(String GlobalConfUID,WM_Configuration_Data ConfFile) {
        FileWriter fw;
        String Prefix="";
        if (ConfFile.configurationtype==1)
            Prefix="kksys_";
        
        try {
            fw = new FileWriter(KK_BASE_UPDATE_TEMP_CONF + "/" +Prefix+ GlobalConfUID + "_"+ ConfFile.uid + ".json");
            fw.write(ConfFile.data);
            fw.flush();
            fw.close();
        } catch (IOException ex) {

        }
    }
}
