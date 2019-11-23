package com.moyu.tools;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;
/*
 * ��GPS����ת���ɰٶ�����,ʹ��ǰҪ��֤������json��
 */
public class GpsToBaidu {
	public static double[] getBaiduLoc(double x,double y){
		System.setProperty("http.proxyHost", "115.28.227.0");
		String path = String.format("http://api.zdoz.net/transgpsbd.aspx?lng=%f&lat=%f"
        		,x,y);
		String lines=null;
        try { 
            URL url = new URL(path);  
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
    		lines = reader.readLine();    		
    	    reader.close(); 
    	    urlConnection.disconnect();
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	JSONObject obj=JSONObject.fromObject(lines);
        	double[] res=new double[]{obj.getDouble("Lng"),obj.getDouble("Lat")};
        	return res;
        }
	}
	
    public static void main(String[] args) {  
    	
        // ת��ǰ��GPS����  
        double x = 120;  
        double y = 30;
        //res[0]:����		res[1]:ά��
        double[] res=getBaiduLoc(x, y);
        for (double d : res) {
        	System.out.println(d);
		}
    }  
}  