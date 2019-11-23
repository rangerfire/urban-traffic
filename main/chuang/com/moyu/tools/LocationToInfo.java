package com.moyu.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

/**
 * ���ȵ�ģʽʱ��Ҫ�����͵��λ��ת���ɸõ����ϸ��Ϣ ��ӵ��ģʽʱ��Ҫ��·����ʼ�㣬
 * �յ�λ��ת���ɸÂz��Ľֵ���Ϣ
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
	 * �ȵ�
	 * @param x �ٶȾ��� 
	 * @param y �ٶ�γ��
	 * @return ��ϸ��Ϣ
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
	 * @param x	 �ٶȾ���
	 * @param y	 �ٶ�γ��
	 * @return �ֵ���
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
		//!!!!!ע�������Ϊ�ٶ�����
		System.out.println(toStreet(x,y));
		System.out.println(toDetail(x,y));
		x=116.380967;y=39.913285;
		System.out.println(toStreet(x,y));
		System.out.println(toDetail(x,y));
	}
}
