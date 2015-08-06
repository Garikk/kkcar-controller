/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import kkdev.kksystem.kkcontroller.sysupdate.UpdateModule.ModuleType;

/**
 *
 * @author blinov_is
 */
public class DownloaderJob {

    public Set<UpdateModule> RequiredModules;
    public Set<UpdateModule> RequiredExtConf;

    public DownloaderJob() {
        RequiredModules = new HashSet<>();
        RequiredExtConf = new HashSet<>();
    }

    public void AddModule(String UUID, UpdateModule.ModuleType MType) {
        UpdateModule UM = new UpdateModule();
        UM.UUID = UUID;
        UM.Type = MType;
        //
        if (MType!=ModuleType.PluginConf)
            RequiredModules.add(UM);
        else 
            RequiredExtConf.add(UM);
        //
    }

    public int GetJobsCount() {
        return RequiredModules.size()+RequiredExtConf.size();
    }

    public void FillFilesURLs()
    {
       // SystemUpdater.
    
    }
    
    public void SaveWatchdogJob(String Jobfile) {
        Gson gson = new Gson();

        String Res = gson.toJson(this);

        try {
            FileWriter fw;
            fw = new FileWriter("./" + Jobfile);
            fw.write(Res);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
        }
    }
     public static DownloaderJob LoadWatchdogJob(String Jobfile) {
        try {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(
                    new FileReader(Jobfile));
            //
            DownloaderJob Ret = gson.fromJson(br, DownloaderJob.class);
            //
            br.close();
            //
            return Ret;
        } catch (FileNotFoundException  ex ) {
            return null;
        }
          catch (IOException  ex ) {
              return null;
        }

    }
}
