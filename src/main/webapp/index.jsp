<!DOCTYPE html>
<%@page import="mango.condor.servlet.test.Test.NewItem"%>
<%@page import="mango.condor.servlet.test.Test.ImgItem"%>
<%@page import="mango.condor.servlet.test.Test.ImgModel"%>
<%@page import="mango.condor.servlet.test.Test.NewsModel"%>
<%@page import="mango.condor.servlet.test.Test"%>
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
<meta http-equiv="content-type" content="text/html;charset=utf-8">

<title>WinXinPingTai</title>

<style type="text/css">
	#menuTable{
		width: 50%
	}
	#menuTable .td1{
		width: 10%
	}
	#menuTable input{
		width: 100%
	}
</style>

</head>
<body>
	<a href="jsp/jqueryMobile.jsp">jqueryMobile</a>
	<h1>微信自定义菜单</h1>
	<br>
	<br>
	<br>
	<form action="PublishMenu" method="post" enctype="multipart/form-data">
		<table id = "menuTable">
			<tr>
				<th>内容类型</th>
				<th>资源编号</th>
			</tr>
			<tr>
				<th>
				<select>
					<option>文本</option>
					<option>连接</option>
					<option>图片</option>
					<option>文图</option>
				</select>
				</th>
				<th>
				<select>
					<%
						ImgModel imgModel = Test.flushImgModel();
						NewsModel newsModel = Test.flushNewsModel();
						for(ImgItem s : imgModel.item){
					%>
							<option><%=s.media_id %> - <%=s.name %></option>					
					<%
						}
					%>
					
					<%
						for(NewItem s : newsModel.item){
					%>
							<option><%=s.media_id %></option>	
					<%
						}
					%>
					
				</select>
				</th>
			</tr>
			<tr>
				<th><iframe src=""></iframe></th>
			</tr>
		</table>
		<br>
		<br>
		<input type="submit" value="发布" />
	</form>
</body>
</html>