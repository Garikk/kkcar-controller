/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate;

import kkdev.kksystem.kkcontroller.sysupdate.webmasterconnection.wm_answer_configuration_info;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kkdev.kksystem.base.classes.plugins.ControllerConfiguration;
import kkdev.kksystem.base.interfaces.IPluginKKConnector;
import kkdev.kksystem.kkcontroller.main.ControllerSettingsManager;
import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
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
    final static String WEBMASTER_URL = "http://supergarikk.dlinkddns.com/";
    final static String WEBMASTER_URL_SERVICE = "kkcontroller/request";

    final static String WEBMASTER_REQUEST_ACT = "action";
    final static String WEBMASTER_REQUEST_MYUUID = "myid";

    final static String WEBMASTER_REQUEST_GET_MYCONF_INFO = "1";           //get ctrlr configuration, ID, stamp
    final static String WEBMASTER_REQUEST_GET_MYCONF_DATA = "2";           //get ctrlr configuration
    final static String WEBMASTER_REQUEST_GET_PLUGINS_INFO = "5";          //get plugins id, version and config stamp
    final static String WEBMASTER_REQUEST_GET_PLUGINS_DATA = "6";          //get extended plugins info (with file names)
    final static String WEBMASTER_REQUEST_CTRLR_DATA_KKPIN = "10";               //KKSystem PIN

    public static void CheckUpdate() {

        boolean ForcePluginUpdate=false;
        ControllerConfiguration UpdatedConfig;
        //Check configuration
        wm_answer_configuration_info ConfInfo = GetConfigInfoFromWeb();
        
        if (!ControllerSettingsManager.MainConfiguration.ConfigurationUID.equals(ConfInfo.confuuid) | !ControllerSettingsManager.MainConfiguration.ConfigurationStamp.equals(ConfInfo.confstamp))
        {
            ForcePluginUpdate=true;
            UpdatedConfig = GetConfigDataFromWeb();
        }
        ///
            

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

    public static wm_answer_configuration_info GetConfigInfoFromWeb() {
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
                return gson.fromJson(Ans.JsonData, wm_answer_configuration_info.class);
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ControllerConfiguration GetConfigDataFromWeb() {
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
}
