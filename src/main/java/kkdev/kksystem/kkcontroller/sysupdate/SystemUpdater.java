/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate;

import kkdev.kksystem.base.classes.plugins.weblink.WM_Answer;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kkdev.kksystem.base.classes.plugins.ControllerConfiguration;
import static kkdev.kksystem.base.classes.plugins.weblink.WM_KKMasterConsts.*;
import kkdev.kksystem.kkcontroller.main.ControllerSettingsManager;
import static kkdev.kksystem.kkcontroller.main.ControllerSettingsManager.SaveLastConfUID;

import kkdev.kksystem.kkcontroller.pluginmanager.PluginLoader;
import kkdev.kksystem.kkcontroller.sysupdate.downloader.WebFileDownloader;
import kkdev.kksystem.base.classes.plugins.weblink.WM_Answer_Configuration_Data;
import kkdev.kksystem.base.classes.plugins.weblink.WM_Answer_Configuration_Info_Pack;
import kkdev.kksystem.base.classes.plugins.weblink.WM_Answer_Data;
import kkdev.kksystem.base.classes.plugins.weblink.WM_Configuration_Data;
import kkdev.kksystem.base.classes.plugins.weblink.WM_File_Data_Pack;
import kkdev.kksystem.base.constants.SystemConsts;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import static org.apache.http.impl.client.HttpClientBuilder.create;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;

/**
 *
 * @author blinov_is
 */
public final class SystemUpdater {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(SystemUpdater.class);
    final static String ___TEST_kkiot_UUID_ = "2e2efd7b-ab83-42fa-9c00-2e45bb4b3ba1";
    final static String WEBMASTER_URL = "http://www.dingo-cloud.tk/";
    final static String WEBMASTER_URL_SERVICE = "weblink";
    final static int WEBMASTER_CLIENT_VERSION = 1;

    private static SystemUpdater instance;
    private String proxyHost;
    private int proxyPort;

    public static SystemUpdater getInstance() {
        if (instance == null) {
            instance = new SystemUpdater();
        }

        return instance;
    }

    public void setProxyHost(String Proxy) {
        proxyHost = Proxy;
    }

    public void setProxyPort(int Port) {
        proxyPort = Port;
    }

    public boolean checkSystemUpdateOnStart(String KKControllerVersion) {
        //Skip by now
        // if (true) {
        //     return false;
        //}

        boolean NeedReload = false;
        logger.info("Check WebLink State");
        logger.info("Available System versions:");
        logger.info("Base: " + SystemConsts.KK_BASE_VERSION + " Controller: " + KKControllerVersion);

        //Init check
        ControllerConfiguration UpdatedConfig = null;
        WM_Answer_Configuration_Data NewConfigurations = null;
        WebFileDownloader ConfigFileDownloader = new WebFileDownloader();

        //Check configuration
        logger.info("Check configuration changes");
        WM_Answer_Configuration_Info_Pack ConfInfo = (WM_Answer_Configuration_Info_Pack) doRequest(WEBMASTER_REQUEST_GET_MYCONF_INFO, getConfigurationInfoRequest(), WM_Answer_Configuration_Info_Pack.class);

        if (ConfInfo.Pack[0] != null && (!ControllerSettingsManager.mainConfiguration.configurationUID.equals(ConfInfo.Pack[0].confuuid) | !ControllerSettingsManager.mainConfiguration.configurationStamp.equals(ConfInfo.Pack[0].confstamp))) {
            logger.info("Loading updated configurations");
            NeedReload = true;
            NewConfigurations = (WM_Answer_Configuration_Data) doRequest(WEBMASTER_REQUEST_GET_MYCONF_DATA, getConfigurationDataRequest(), WM_Answer_Configuration_Data.class);// getUpdatedConfigurations();
        } else {
            UpdatedConfig = ControllerSettingsManager.mainConfiguration;
        }

        if (NewConfigurations != null) {
            String MainConfUID = "";
            for (WM_Configuration_Data DT : NewConfigurations.configurations) {
                if (DT.configurationtype == 1) {
                    Gson gson = new Gson();
                    UpdatedConfig = gson.fromJson(DT.data, ControllerConfiguration.class);
                    MainConfUID = UpdatedConfig.configurationUID;
                }
            }
            for (WM_Configuration_Data DT : NewConfigurations.configurations) {
                logger.info("Store new configuration for WD: T:[" + DT.configurationtype + "] U:[" + DT.uid + "]");
                ConfigFileDownloader.saveConfigurationFiles(MainConfUID, DT);
            }

            SaveLastConfUID(MainConfUID);

        }

        //Check plugins
        //Pre init
        logger.info("Check plugin changes");
        PluginLoader.preInitAllPlugins();
        //InitAllPlugins
        //Get Available plugins list
        
        Set<String> AvailPlugins = PluginLoader.getActivePluginUIDs();
        
        // Get Required plugins
        //
        Set<String> ReqPlugins = PluginLoader.getRequiredPlugins(UpdatedConfig.features);

        //
        // Remove existed plugins from requierments list
        //
        AvailPlugins.stream().filter((RP) -> (ReqPlugins.contains(RP))).forEach((RP) -> {
            ReqPlugins.remove(RP);
        });

        if (ReqPlugins.size() > 0) {
            WM_File_Data_Pack BinFilesToDownload = (WM_File_Data_Pack) doRequest(WEBMASTER_REQUEST_GET_FILES_INFO_BIN, getFilesInfoRequestBin(ReqPlugins), WM_File_Data_Pack.class);
            WM_File_Data_Pack ConfFilesToDownload = (WM_File_Data_Pack) doRequest(WEBMASTER_REQUEST_GET_FILES_INFO_EXTCONF, getFilesInfoRequestExtConf(UpdatedConfig.configurationUID), WM_File_Data_Pack.class);

            ConfigFileDownloader.downloadPluginFiles(UpdatedConfig.configurationUID, BinFilesToDownload, ConfFilesToDownload);
        }

        return NeedReload;

    }

    private List<NameValuePair> getCheckServerInfo() {
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_ACT,
                WEBMASTER_REQUEST_CHECK));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_MYUUID,
                ___TEST_kkiot_UUID_));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_CLIENTINFO,
                String.valueOf(WEBMASTER_CLIENT_VERSION)));

        return nameValuePairs;
    }

    private List<NameValuePair> getConfigurationInfoRequest() {
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_ACT,
                String.valueOf(WEBMASTER_REQUEST_GET_MYCONF_INFO)));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_MYUUID,
                ___TEST_kkiot_UUID_));

        return nameValuePairs;
    }

    private List<NameValuePair> getConfigurationDataRequest() {
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_ACT,
                String.valueOf(WEBMASTER_REQUEST_GET_MYCONF_DATA)));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_MYUUID,
                ___TEST_kkiot_UUID_));
        return nameValuePairs;
    }

    private List<NameValuePair> getFilesInfoRequestBin(Set<String> ReqFiles) {
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_ACT,
                String.valueOf(WEBMASTER_REQUEST_GET_FILES_INFO_BIN)));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_MYUUID,
                ___TEST_kkiot_UUID_));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_REQFILESBIN,
                ReqFiles.spliterator().toString()));

        return nameValuePairs;
    }

    private List<NameValuePair> getFilesInfoRequestExtConf(String MainConf) {
        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_ACT,
                String.valueOf(WEBMASTER_REQUEST_GET_FILES_INFO_EXTCONF)));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_MYUUID,
                ___TEST_kkiot_UUID_));
        nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_CONFUUID,
                MainConf));

        return nameValuePairs;
    }

    private WM_Answer_Data doRequest(String ServiceLink, List<NameValuePair> Params, Class TargetType) {
        ControllerConfiguration Ret = null;
        WM_Answer Ans;
        Gson gson = new Gson();

        try {
            HttpClient client;
            if (proxyHost != null) {
                HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http"); //only http by now, TODO use ssl
                client = HttpClients.custom().setProxy(proxy).build();//create().build();
            } else {
                client = create().build();
            }

            HttpPost post = new HttpPost(WEBMASTER_URL + WEBMASTER_URL_SERVICE + "/" + ServiceLink);

            post.setEntity(new UrlEncodedFormEntity(Params));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            Ans = gson.fromJson(rd, WM_Answer.class);
            //  if (Ans!=null || Ans[0].answerState == 0) {
            logger.info(Ans.jsonData);

            return (WM_Answer_Data) gson.fromJson(Ans.jsonData, TargetType);

            //   } else {
            //        return null
            //   }
        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
            return null;
        }
    }

}

/*
      public WM_Answer_SystemState[] getSystemStateInfo() {
        ControllerConfiguration Ret = null;
        WM_Answer Ans;
        Gson gson = new Gson();

        try {
            HttpClient client = create().build();
            HttpPost post = new HttpPost(WEBMASTER_URL + WEBMASTER_URL_SERVICE);

            post.setEntity(new UrlEncodedFormEntity(getConfigurationInfoRequest()));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Ans = gson.fromJson(rd, WM_Answer.class);

            // if (Ans!=null || Ans[0].answerState == 0) {
            return gson.fromJson(Ans.jsonData, WM_Answer_Configuration_Info[].class
            );
            //  } else {
            //       return null;
            //   }

        } catch (IOException e) {
            return null;
        }
    }

    public WM_Answer_Configuration_Info[] getConfigInfoFromWeb() {
        ControllerConfiguration Ret = null;
        WM_Answer Ans;
        Gson gson = new Gson();

        try {
            HttpClient client = create().build();
            HttpPost post = new HttpPost(WEBMASTER_URL + WEBMASTER_URL_SERVICE);

            post.setEntity(new UrlEncodedFormEntity(getConfigurationInfoRequest()));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Ans = gson.fromJson(rd, WM_Answer.class);

            // if (Ans!=null || Ans[0].answerState == 0) {
            return gson.fromJson(Ans.jsonData, WM_Answer_Configuration_Info[].class
            );
            //  } else {
            //       return null;
            //   }

        } catch (IOException e) {
            return null;
        }
    }

    public WM_Answer_Configuration_Data getUpdatedConfigurations() {
        ControllerConfiguration Ret = null;
        WM_Answer Ans;
        Gson gson = new Gson();

        try {
            HttpClient client = create().build();
            HttpPost post = new HttpPost(WEBMASTER_URL + WEBMASTER_URL_SERVICE);

            post.setEntity(new UrlEncodedFormEntity(getConfigurationDataRequest()));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Ans
                    = gson.fromJson(rd, WM_Answer.class
                    );

            if (Ans.answerState
                    == 0) {
                WM_Answer_Configuration_Data RC = gson.fromJson(Ans.jsonData, WM_Answer_Configuration_Data.class);
                return RC;
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WM_File_Data[] getPluginFilesInfo(Set<String> Plugins) {
        if (Plugins.isEmpty()) {
            return null;
        }

        ControllerConfiguration Ret = null;
        WM_Answer Ans;
        Gson gson = new Gson();

        try {
            HttpClient client = create().build();
            HttpPost post = new HttpPost(WEBMASTER_URL + WEBMASTER_URL_SERVICE);

            post.setEntity(new UrlEncodedFormEntity(getFilesInfoRequestBin(join(",", Plugins))));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Ans
                    = gson.fromJson(rd, WM_Answer.class
                    );

            if (Ans.answerState
                    == 0) {
                return gson.fromJson(Ans.jsonData, WM_File_Data[].class);
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WM_File_Data[] getExternalConfigurationsInfo(String MainConf) {
        ControllerConfiguration Ret = null;
        WM_Answer Ans;
        Gson gson = new Gson();

        try {
            HttpClient client = create().build();
            HttpPost post = new HttpPost(WEBMASTER_URL + WEBMASTER_URL_SERVICE);

            post.setEntity(new UrlEncodedFormEntity(getFilesInfoRequestExtConf(MainConf)));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Ans = gson.fromJson(rd, WM_Answer.class);

            if (Ans.answerState == 0) {
                return gson.fromJson(Ans.jsonData, WM_File_Data[].class);
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
 */
