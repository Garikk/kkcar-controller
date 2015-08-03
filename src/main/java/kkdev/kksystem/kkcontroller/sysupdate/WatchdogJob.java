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
import kkdev.kksystem.base.constants.SystemConsts;

/**
 *
 * @author blinov_is
 */
public class WatchdogJob {

    public Set<UpdateModule> RequiredModules;

    public WatchdogJob() {
        RequiredModules = new HashSet<>();
    }

    public void AddModule(String UUID, boolean IsPlugin) {
        UpdateModule UM = new UpdateModule();
        UM.UUID = UUID;
        if (!IsPlugin) {
            UM.Type = UpdateModule.ModuleType.Plugin;
        } else {
            UM.Type = UpdateModule.ModuleType.Controller;
        }
        //
        RequiredModules.add(UM);
        //
    }

    public int GetJobsCount() {
        return RequiredModules.size();
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
     public static WatchdogJob LoadWatchdogJob(String Jobfile) {
        try {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(
                    new FileReader(Jobfile));
            //
            WatchdogJob Ret = gson.fromJson(br, WatchdogJob.class);
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
