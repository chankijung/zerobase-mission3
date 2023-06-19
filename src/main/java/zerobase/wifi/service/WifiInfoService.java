package zerobase.wifi.service;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.json.simple.parser.JSONParser;
import zerobase.wifi.dto.WifiInfoDto;
import zerobase.wifi.model.WifiInfoModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class WifiInfoService {

    /*
    데이터 총 갯수 구하기
     */

    public int getTotal() throws IOException{
        StringBuilder urlBuilder=new StringBuilder("http://openapi.seoul.go.kr:8088/7073676c4a7361743131375672575259/json/TbPublicWifiInfo");
        urlBuilder.append("/"+ URLEncoder.encode("1","UTF-8"));
        urlBuilder.append("/"+ URLEncoder.encode("1","UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type","application/json");
        System.out.println("Response code:"+conn.getResponseCode());
    //서비스 코드가 정상이면 200~300 사이 숫자.
        BufferedReader bf;
        if(conn.getResponseCode()>=200 && conn.getResponseCode()<=300){
            bf=new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            bf = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb= new StringBuilder();
        String line;
        while((line=bf.readLine())!=null){
            sb.append(line);
        }
        bf.close();
        conn.disconnect();

        String total = sb.substring(40, 45);
        return Integer.parseInt(total);
    }

    /*
    공공데이터 db에 저장

     */

    public void insertData(){
        try{
            int intTotal = getTotal();
            BufferedReader bf;
            StringBuilder sb;
            String line;

            int idx=1;
            for(int i=0;i<intTotal/1000+1;i++){
                StringBuilder urlBuilder= new StringBuilder("http://openapi.seoul.go.kr:8088/7073676c4a7361743131375672575259/json/TbPublicWifiInfo");
                urlBuilder.append("/"+URLEncoder.encode(String.valueOf(idx),"UTF-8"));
                urlBuilder.append("/" + URLEncoder.encode(String.valueOf((idx+999)),"UTF-8"));

                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                if(conn.getResponseCode()>=200&&conn.getResponseCode()<=300){
                    bf=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                }else{
                    bf=new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                sb= new StringBuilder();
                while((line=bf.readLine())!=null){
                    sb.append(line);
                }

                JSONObject result= (JSONObject)new JSONParser().parse(sb.toString());
                JSONObject data = (JSONObject) result.get("TbPublicWifiInfo");
                JSONArray array = (JSONArray) data.get("row");

                JSONObject tmp;

                for(int j =0;j<array.size();j++){
                    WifiInfoDto wifiInfoDto = new WifiInfoDto();
                    tmp =(JSONObject) array.get(j);
                    wifiInfoDto.setMgrNo((Integer) tmp.get("X_SWIFI_MGR_NO"));
                    wifiInfoDto.setRegion(String.valueOf(tmp.get("X_SWIFI_WRDOFC")));
                    wifiInfoDto.setMainNm(String.valueOf(tmp.get("X_SWIFI_MAIN_NM")));
                    wifiInfoDto.setAddress(String.valueOf(tmp.get("X_SWIFI_ADRES1")));
                    wifiInfoDto.setAddressDetail(String.valueOf(tmp.get("X_SWIFI_ADRES2")));
                    wifiInfoDto.setInstallFloor(String.valueOf(tmp.get("X_SWIFI_INSTL_FLOOR")));
                    wifiInfoDto.setInstallTy(String.valueOf(tmp.get("X_SWIFI_INSTL_TY")));
                    wifiInfoDto.setInstallMby(String.valueOf(tmp.get("X_SWIFI_INSTL_MBY")));
                    wifiInfoDto.setServiceSe(String.valueOf(tmp.get("X_SWIFI_SVC_SE")));
                    wifiInfoDto.setNetworkTy(String.valueOf(tmp.get("X_SWIFI_CMCWR")));
                    wifiInfoDto.setInstallYear((Integer) tmp.get("X_SWIFI_CNSTC_YEAR"));
                    wifiInfoDto.setIsOutdoor((String) tmp.get("X_SWIFI_INOUT_DOOR"));
                    wifiInfoDto.setConnectEnv((String) tmp.get("X_SWIFI_REMARS3"));
                    wifiInfoDto.setLongitude((Double) tmp.get("LNT"));
                    wifiInfoDto.setLatitude((Double) tmp.get("LAT"));
                    wifiInfoDto.setWorkDate((Date) tmp.get("WORK_DTTM"));

                    WifiInfoModel wifiInfoModel = new WifiInfoModel();
                    wifiInfoModel.insert(wifiInfoDto);

                }
                bf.close();
                conn.disconnect();
                idx+=1000;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * return public wifi list
     */
    public List<WifiInfoDto> list(String lnt,String lat){
        WifiInfoModel wifiInfoModel=new WifiInfoModel();
        return wifiInfoModel.selectList(lnt,lat);

    }
    public void reset(){
        WifiInfoModel wifiInfoModel=new WifiInfoModel();
        wifiInfoModel.deleteAll();
    }
}
