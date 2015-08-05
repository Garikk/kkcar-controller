/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate.downloader;

import java.io.File;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_TEMP;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_TEMP_BASE;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_TEMP_PLUGINS;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_WDJOB_FILE;
import kkdev.kksystem.kkcontroller.sysupdate.DownloaderJob;

/**
 *
 * @author blinov_is
 */
public abstract class Downloader {
    public static void DownloadBinFiles(DownloaderJob Job)
    {
      System.out.println("KK System Watchdog");
        //
        //
        File WDJob = new java.io.File(KK_BASE_UPDATE_WDJOB_FILE);   
        //
        if (!WDJob.exists())
            return;
        //
        File TempPath = new java.io.File(KK_BASE_UPDATE_TEMP);   
        //
        if (!TempPath.exists())
            TempPath.mkdir();
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_PLUGINS);   
        //
        if (!TempPath.exists())
            TempPath.mkdir();

        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_BASE);   
        //
        if (!TempPath.exists())
            TempPath.mkdir();
        //
    }
}
