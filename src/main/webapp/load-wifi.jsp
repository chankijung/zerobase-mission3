<%@ page import="zerobase.wifi.model.WifiInfoModel" %>
<%@ page import="zerobase.wifi.service.WifiInfoService" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>와이파이 정보 구하기</title>
	<link href="/res/css/main.css" rel="stylesheet"/>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>

	<%
		WifiInfoService wifiInfoService = new WifiInfoService();
		wifiInfoService.reset();
		int total= wifiInfoService.getTotal();
		out.write(String.valueOf(total));
		wifiInfoService.insertData();
	%>
	개의 WIFI정보를 정상적으로 저장하였습니다.
	<a href="index.jsp">홈으로 가기</a>

</body>
</html>