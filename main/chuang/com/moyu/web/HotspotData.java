package com.moyu.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;






import java.util.Set;

import net.sf.json.JSONArray;


import com.moyu.dao.CarInfoDAO;
import com.moyu.entity.CarInfo;
import com.moyu.entity.HourData;
import com.moyu.entity.PosHours;
import com.moyu.tools.GpsToBaidu;
import com.moyu.tools.LocationToInfo;

public class HotspotData {
	private final String PATH;
	public HotspotData(String path){
		PATH=path;
	}
	public List<CarInfo> CarsbySpeedTime(String maxSp,String bgtime,String edtime) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		return new CarInfoDAO(PATH).ListCarsbySpeedTime(maxSp,bgtime,edtime);
		//List<List<HotspotofHour>>
	}
	public List<CarInfo> AllCarInfos() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		return new CarInfoDAO(PATH).ListAllCarInfo();
	}
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException {
		Set<PosHours> set=new HashSet<PosHours>();
		Set<HourData> data=new HashSet<HourData>();
		data.add(new HourData(0, 0, 10));
		data.add(new HourData(0, 1, 19));
		double[] loc=GpsToBaidu.getBaiduLoc(116,40);
		String info=LocationToInfo.toDetail(loc[0], loc[1]);
		set.add(new PosHours(loc[0], loc[1],info, data));
		//Ð´ÈëÎÄ¼þ
		System.out.println(JSONArray.fromObject(set));
	}
	
}
