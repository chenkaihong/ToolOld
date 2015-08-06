<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
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
	<h1>微信自定义菜单</h1>
	<dl>
		<dt>步骤1: </dt>
		<dd>在微信管理平台 -> 开发者中心 中找到AppID(应用ID) 和 AppSecret(应用密钥), 并填入页面对应的空格中(注: 如果填写错误,则会直接返回错误页面)</dd>
		<dt>步骤2: </dt>
		<dd>在自己本地的Menu.txt(微信自定义菜单模版)进行修改并保存,然后点击menuFile字样后面的文件传输按钮,将刚才保存完毕的文件进行上传</dd>
		<dt>步骤3: </dt>
		<dd>与步骤2一致,修改并保存EvenProperties.properties文件后点击EvenPropertiesFile字样后面的文件传输按钮进行上传</dd>
		<dt>注:</dt>
		<dd>menuFile : 描述微信菜单结构的文件</dd>
		<dd>EvenPropertiesFile : 描述微信菜单具体功能的文件(使用方法查看EvenProperties.properties文件)</dd>
	</dl>
	<hr>
	<br>
	<br>
	<form action="PublishMenu" method="post" enctype="multipart/form-data">
		<table id = "menuTable">
			<tr>
				<td colspan="2">步骤1: </td>
			</tr>
			<tr>
				<td class="td1">AppID: </td>
				<td><input type="text" name="appID"/></td>
			</tr>
			<tr>
				<td class="td1">AppSecret: </td>
				<td><input type="text" name="secret"/></td>
			</tr>
			<tr><td><hr></td></tr>
			<tr>
				<td colspan="2">步骤2: </td>
			</tr>
			<tr>
				<td class="td1">menuFile: </td>
				<td><input type="file" size="10" accept="text/plain" name="menuFile"/></td>
			</tr>
			<tr><td><hr></td></tr>
			<tr>
				<td colspan="2">步骤3: </td>
			</tr>
			<tr>
				<td class="td1">EvenPropertiesFile: </td>
				<td><input type="file" size="10" name="EvenPropertiesFile"/></td>
			</tr>
		</table>
		<br>
		<br>
		<input type="submit" value="发布" />
	</form>
</body>
</html>