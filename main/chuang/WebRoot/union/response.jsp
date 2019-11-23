<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.List"%>
<%@page import="com.moyu.web.HotspotData"%>
<%@page import="com.moyu.entity.CarInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String id=(String)request.getParameter("id");//hot:1   jam:2   sum:3
	
	if(id.equals("1"))
	{
		List<CarInfo> cars=new HotspotData(getServletContext().
					getRealPath("/WEB-INF/config/jdbc.properties")).AllCarInfos();
		out.write(JSONArray.fromObject(cars).toString());
	}
	else if(id.equals("2"))//jam
	{
		String maxSp=request.getParameter("maxSpeed");
		String bgtime=request.getParameter("bgtime");
		String edtime=request.getParameter("edtime");
		List<CarInfo> cars=new HotspotData(getServletContext().
					getRealPath("/WEB-INF/config/jdbc.properties")).
						CarsbySpeedTime(maxSp,bgtime,edtime);
		out.write(JSONArray.fromObject(cars).toString());
	}
%>