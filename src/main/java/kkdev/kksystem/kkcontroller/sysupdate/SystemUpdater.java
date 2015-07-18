/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import kkdev.kksystem.base.classes.plugins.ControllerConfiguration;
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

    final static String ___TEST_KKCAR_UUID_="c44bcdfc-d72b-4db7-ab99-37916cc55bd1";
    final static String WEBMASTER_URL = "http://supergarikk.dlinkddns.com/";
    final static String WEBMASTER_URL_SERVICE = "kkcontroller/request";

    final static String WEBMASTER_REQUEST_ACT = "action";
    final static String WEBMASTER_REQUEST_MYUUID = "myid";

    final static String WEBMASTER_REQUEST_GET_MYCONF_INFO = "1";           //get ctrlr configuration, ID, stamp
    final static String WEBMASTER_REQUEST_GET_MYCONF_DATA = "2";           //get ctrlr configuration
    final static String WEBMASTER_REQUEST_GET_PLUGINS_INFO = "5";          //get plugins id, version and config stamp
    final static String WEBMASTER_REQUEST_GET_PLUGINS_DATA = "6";          //get extended plugins info (with file names)
    final static String WEBMASTER_REQUEST_CTRLR_DATA_KKPIN = "10";               //KKSystem PIN

    public static ControllerConfiguration GetConfigFromWeb() {
        ControllerConfiguration Ret = null;
        KKMasterAnswer Ans;
        Gson gson = new Gson();
        
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(WEBMASTER_URL + WEBMASTER_URL_SERVICE);
            List<NameValuePair> nameValuePairs = new ArrayList<>(1);
            nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_ACT,
                    WEBMASTER_REQUEST_GET_MYCONF_INFO));
            nameValuePairs.add(new BasicNameValuePair(WEBMASTER_REQUEST_MYUUID,
                    ___TEST_KKCAR_UUID_));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            Ans = gson.fromJson(rd, KKMasterAnswer.class);
         //   String inputLine;
           // while ((inputLine = rd.readLine()) != null) {
	//		System.out.println(inputLine);
	//	}
           // return null;
            if (Ans.AnswerState==KKMasterAnswer.KKM_ANS_States.ANS_CONF_OK)
            {
                Ret=(ControllerConfiguration)gson.fromJson(Ans.JsonData,ControllerConfiguration.class);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Ret;
    }

}
