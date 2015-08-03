/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate;

import kkdev.kksystem.kkcontroller.sysupdate.webmasterconnection.KKMasterAnswer;
import kkdev.kksystem.kkcontroller.sysupdate.webmasterconnection.WM_Answer_Configuration_Info;
import com.google.gson.Gson;
import com.sun.glass.ui.Application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kkdev.kksystem.base.classes.plugins.ControllerConfiguration;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UPDATE_WDJOB_FILE;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_VERSION;
import kkdev.kksystem.kkcontroller.main.ControllerSettingsManager;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
import static kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader.GetRequiredPlugins;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author blinov_is
 */
public abstract class SystemUpdater {

    final static String ___TEST_KKCAR_UUID_ = "2e2efd7b-ab83-42fa-9c00-2e45bb4b3ba1";
    final static String WEBMASTER_URL = "http://localhost/";
    final static String WEBMASTER_URL_SERVICE = "kkcontroller/request";

    final static String WEBMASTER_REQUEST_ACT = "action";
    final static String WEBMASTER_REQUEST_MYUUID = "myid";

    final static String WEBMASTER_REQUEST_GET_MYCONF_INFO = "1";           //get ctrlr configuration, ID, stamp
    final static String WEBMASTER_REQUEST_GET_MYCONF_DATA = "2";           //get ctrlr configuration
    final static String WEBMASTER_REQUEST_GET_PLUGINS_INFO = "5";          //get plugins id, version and config stamp
    final static String WEBMASTER_REQUEST_GET_PLUGINS_DATA = "6";          //get extended plugins info (with file names)
    final static String WEBMASTER_REQUEST_CTRLR_DATA_KKPIN = "10";               //KKSystem PIN

    public static boolean CheckUpdate(String KKControllerVersion) {

        boolean NeedReload=false;
        ControllerConfiguration UpdatedConfig;
        WatchdogJob UpdateJob=new WatchdogJob();
        
        //Check configuration
        WM_Answer_Configuration_Info ConfInfo = GetConfigInfoFromWeb();
        //
        if (!ControllerSettingsManager.MainConfiguration.ConfigurationUID.equals(ConfInfo.confuuid) | !ControllerSettingsManager.MainConfiguration.ConfigurationStamp.equals(ConfInfo.confstamp))
        {
            System.out.println("Loading new Config");
            NeedReload=true;
            UpdatedConfig = GetUpdatedConfigurations();
        }
        else
        {
            UpdatedConfig=ControllerSettingsManager.MainConfiguration;
        }
        
        //
        //Check plugins
        //
        //Pre init
        //
        PluginLoader.PreInitAllPlugins();
        //
        //Get Available plugins list
        //
        Set<String> AvailPlugins = PluginLoader.GetPluginUIDs();
        //
        // Get Required plugins
        //
        List<String> ReqPlugins=GetRequiredPlugins(UpdatedConfig.Features);
        //
        AvailPlugins.stream().filter((RP) -> (ReqPlugins.contains(RP))).forEach((RP) -> {
            ReqPlugins.remove(RP);
        });
        //
        if (ReqPlugins.size()>0)
        {
            ReqPlugins.stream().forEach((AP) -> {
                UpdateJob.AddModule(AP, false);
            });
        }
        /////
        // Check update kkcontroller jar
        ////
        if (!KKControllerVersion.equals(ConfInfo.kkcontroller_version))
        {
            UpdateJob.AddModule("kkcontroller", true);
        }
          /////
        // Check update base jar
        ////
        if (!KK_BASE_VERSION.equals(ConfInfo.base_version))
        {
            UpdateJob.AddModule("base", true);
        }
        //
        if (UpdateJob.GetJobsCount()>0)
        {
            UpdateJob.SaveWatchdogJob(KK_BASE_UPDATE_WDJOB_FILE);
        }
        
        
        return (UpdateJob.GetJobsCount()>0);
        
    }

    private static List<NameValuePair> GetConfigurationInfoRequest() {
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_ACT,
                WEBMASTER_REQUEST_GET_MYCONF_INFO));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_MYUUID,
                ___TEST_KKCAR_UUID_));

        return nameValuePairs;
    }

    private static List<NameValuePair> GetConfigurationDataRequest() {
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_ACT,
                WEBMASTER_REQUEST_GET_MYCONF_DATA));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_MYUUID,
                ___TEST_KKCAR_UUID_));

        return nameValuePairs;
    }

    public static WM_Answer_Configuration_Info GetConfigInfoFromWeb() {
        ControllerConfiguration Ret = null;
        KKMasterAnswer Ans;
        Gson gson = new Gson();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(WEBMASTER_URL + WEBMASTER_URL_SERVICE);

            post.setEntity(new UrlEncodedFormEntity(GetConfigurationInfoRequest()));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Ans = gson.fromJson(rd, KKMasterAnswer.class);

            if (Ans.AnswerState == 0) {
                return gson.fromJson(Ans.JsonData, WM_Answer_Configuration_Info.class);
            } else {
                return null;
            }

        } catch (IOException e) {
            return null;
        }
    }

    public static ControllerConfiguration GetUpdatedConfigurations() {
        ControllerConfiguration Ret = null;
        KKMasterAnswer Ans;
        Gson gson = new Gson();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(WEBMASTER_URL + WEBMASTER_URL_SERVICE);

            post.setEntity(new UrlEncodedFormEntity(GetConfigurationDataRequest()));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Ans = gson.fromJson(rd, KKMasterAnswer.class);

            if (Ans.AnswerState == 0) {
                return gson.fromJson(Ans.JsonData, ControllerConfiguration.class);
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static void GetPluginsFromWeb(List<String> RequiredPlugins)
    {
    
    }

}
