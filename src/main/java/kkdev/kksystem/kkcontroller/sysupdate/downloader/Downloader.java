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
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_TEMP;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_TEMP_BASE;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_TEMP_EXTCONF;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_TEMP_PLUGINS;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_WDJOB_FILE;
import kkdev.kksystem.kkcontroller.sysupdate.SystemUpdater;
import kkdev.kksystem.kkcontroller.sysupdate.webmasterconnection.WM_File_Data;
import kkdev.kksystem.kkcontroller.sysupdate.webmasterconnection.WM_Files_Info;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author blinov_is
 */
public abstract class Downloader {

    public static void DownloadFiles(String ConfigUID, Set<String> ReqPlugins) {
        System.out.println("KK System Watchdog");
        //
        // Create updater folders
        //
        File TempPath = new java.io.File(KK_BASE_UPDATE_TEMP);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
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
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_EXTCONF);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        //
        WM_Files_Info BinFilesToDownload = SystemUpdater.GetPluginFilesInfo(ReqPlugins);
        WM_Files_Info ConfFilesToDownload = SystemUpdater.GetExternalConfigurationsInfo(ConfigUID);

        for (WM_File_Data F : BinFilesToDownload.Files) {
            DownloadFile(KK_BASE_UPDATE_TEMP_PLUGINS, F.fileurl, F.filename);
        }
        for (WM_File_Data F : ConfFilesToDownload.Files) {
            DownloadFile(KK_BASE_UPDATE_TEMP_PLUGINS, F.fileurl, F.filename);
        }

    }

    private static void DownloadFile(String Dir, String URL, String TargetName) {

        HttpClient client = HttpClientBuilder.create().build();

        BufferedOutputStream bos = null;
        try {
            HttpGet httpget = new HttpGet(URL);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();

            BufferedInputStream bis = new BufferedInputStream(entity.getContent());
            String filePath = Dir+"/"+TargetName;
            bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            int inByte;
            while ((inByte = bis.read()) != -1) {
                bos.write(inByte);
            }
            bis.close();
            bos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
