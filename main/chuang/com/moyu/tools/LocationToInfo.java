package com.moyu.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

/**
 * 做热点模式时需要将典型点的位置转换成该点的详细信息 做拥堵模式时需要将路段起始点，
 * 终点位置转换成该倆点的街道信息
 */
public class LocationToInfo {
	private static String get(double x, double y) {
		System.setProperty("http.proxyHost", "112.80.248.46");
		String path = String.format(
				"http://api.map.baidu.com/geocoder/v2/?ak=YLa8sZyeFYuvoauXNg0T4hRA"
						+ "&location=%f,%f&output=json", y, x);
		String lines = null;
		try {
			URL url = new URL(path);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(),"utf-8"));
			lines = reader.readLine();
			reader.close();
			urlConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return lines;
		}
	}

	/**
	 * 热点
	 * @param x 百度经度 
	 * @param y 百度纬度
	 * @return 详细信息
	 */
	public static String toDetail(double x, double y) {
		String lines = get(x, y);
		if (lines != null) {
			JSONObject obj = JSONObject.fromObject(JSONObject.fromObject(lines)
					.getString("result"));
			return obj.getString("formatted_address");
		} else
			return null;
	}

	/**
	 * 
	 * @param x	 百度经度
	 * @param y	 百度纬度
	 * @return 街道名
	 * @throws UnsupportedEncodingException 
	 */
	public static String toStreet(double x, double y){
		String lines = get(x, y);
		if (lines != null) {
			JSONObject obj = JSONObject.fromObject(JSONObject.fromObject(
					JSONObject.fromObject(lines).getString("result"))
					.getString("addressComponent"));
			return obj.getString("street");
		}
		else
			return null;
	}
	public static void main(String[] args) {
		double x = 116.424374;
		double y = 39.914668;
		//116.380967,39.913285
		//116.424374,39.914668
		//!!!!!注意该坐标为百度坐标
		System.out.println(toStreet(x,y));
		System.out.println(toDetail(x,y));
		x=116.380967;y=39.913285;
		System.out.println(toStreet(x,y));
		System.out.println(toDetail(x,y));
	}
}
