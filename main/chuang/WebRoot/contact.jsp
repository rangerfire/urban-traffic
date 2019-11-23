<%@page import="com.moyu.entity.Comment"%>
<%@page import="com.moyu.dao.CommentsDAO"%>
<%@page import="com.moyu.tools.SendMail"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>联系我们</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	
	-->
<link rel="stylesheet" type="text/css" href="union/css/formstyle.css">
<script type="text/javascript">
	function init() {
		form.name.style.background = "#444444";
		form.email.style.background = "#444444";
		form.phone.style.background = "#444444";
		form.mess.style.background = "#444444";
	}
	function mysubmit() {
		if (!myFunction())
			return false;
		else {
			return true;
		}
	}
	function myFunction() {
		var isError = false;
		var warclr = "#880000";
		if (!validate(form.name)) {
			form.name.style.background = warclr;
			isError = true;
		}
		if (!validate(form.email)) {
			form.email.style.background = warclr;
			isError = true;
		} else if (!validateEmail()) {
			form.email.style.background = warclr;
			isError = true;
		}
		if (!validate(form.phone)) {
			form.phone.style.background = warclr;
			isError = true;
		} else if (!validatePhone()) {
			form.phone.style.background = warclr;
			isError = true;
		}
		if (!validate(form.mess)) {
			form.mess.style.background = warclr;
			isError = true;
		}
		return !isError;
	};
	function validatePhone() {
		var re = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/
		if (!re.test(form.phone.value)) {
			form.phone.value = "请输入正确的手机号码";
			return false;
		}
		return true;
	}
	function validateEmail() {
		var re = /\w@\w*\.\w/
		if (!re.test(form.email.value.trim())) {
			form.email.value = "请输入正确的电子邮箱";
			return false;
		}
		return true;
	}

	function validate(valider) {
		if (valider.value.trim() == "" || valider.value == valider.defaultValue) {
			valider.value = valider.defaultValue;
			return false;
		}
		return true;
	}
</script>
</head>
<body style="background:url(union/image/gray.jpg);">
	<jsp:include page="union/h.jsp"></jsp:include>
	<br><br><br><br>
	
	<h1 align="center"
		style="font-family:华文行楷 ; font-size:47px;color: black; ">
		<strong>联系我们</strong>
	</h1>
	<div class="login-01">
		<form id="form" action="" method="post" onsubmit="return mysubmit()">
			<ul>
				<li class="first"><a href="javascript:;" class=" icon user"></a><input
					tabIndex="1" name="name" style="font-family:微软雅黑 ;" type="text"
					class="text" value="请输入姓名"
					onFocus="init();if(this.value==this.defaultValue){this.value=''}"
					onBlur="if (this.value == '') {this.value = '请输入姓名';}">
					<div class="clear"></div></li>
				<li class="first"><a href="javascript:;" class=" icon email"></a><input
					tabIndex="2" name="email" style="font-family:微软雅黑 ;" type="text"
					class="text" value="请输入电子邮箱"
					onFocus="init();if(this.value ==this.defaultValue||this.value=='请输入正确的电子邮箱'){this.value=''}"
					onBlur="if (this.value == '') {this.value = '请输入电子邮箱';}">
					<div class="clear"></div></li>
				<li class="first"><a href="javascript:;" class=" icon phone"></a><input
					tabIndex="3" name="phone" style="font-family:微软雅黑 ;" type="text"
					class="text" value="请输入手机"
					onFocus="init(); if(this.value == this.defaultValue||this.value=='请输入正确的手机号码'){this.value=''}"
					onBlur="if (this.value == '') {this.value = '请输入手机';}">
					<div class="clear"></div></li>
				<li class="second"><a href="javascript:;" class=" icon msg"></a>
					<textarea tabIndex="4" name="mess" style="font-family:微软雅黑 ;"
						onFocus="init(); if(this.value == this.defaultValue||this.value=='请输入留言'){this.value=''}"
						onBlur="if (this.value == '') {this.value = '请输入留言';}">请输入留言</textarea>
					<div class="clear"></div></li>
			</ul>
			<input tabIndex="5" name="submit" style="font-family:幼圆;"
				type="submit" value="提交留言">
		</form>
	</div>
	<jsp:include page="union/footer.jsp"></jsp:include>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		//为什么调用封装后的方法会有错？
		if (request.getParameter("name") != null) 
		{
			final String name = new String(request.getParameter("name").getBytes(
					"ISO-8859-1"), "utf-8");
			final String email = new String(request.getParameter("email")
					.getBytes("ISO-8859-1"), "utf-8");
			final String phone = new String(request.getParameter("phone")
					.getBytes("ISO-8859-1"), "utf-8");
			final String mess = new String(request.getParameter("mess").getBytes(
					"ISO-8859-1"), "utf-8");
			boolean v=new CommentsDAO(this.getServletContext().
						getRealPath("/WEB-INF/config/jdbc.properties")).
					addComments(new Comment(name,email,phone,mess));
			if (v == false)
				out.write("<script>alert('每人每日只能留言一次');</script>");
			else 
			{
				out.write("<script>alert('留言成功');</script>");
				Thread th=new Thread(){
					public void run(){
						SendMail.Send(name, email, mess, getServletContext().
						getRealPath("/WEB-INF/config/mail.properties"));
					}
				};
				th.start();
			}
		/*	//直接访问数据库，插入数据
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/chuang", "root", "110114");
			PreparedStatement ps = conn
					.prepareStatement("insert into comments values(?,?,?,?,curdate())");
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, phone);
			ps.setString(4, mess);
			int v = 0;
			try {
				v = ps.executeUpdate();
			} catch (SQLException e) {
			}
			if (v == 0)
				out.write("<script>alert('每人每日只能留言一次');</script>");
			else {
				out.write("<script>alert('留言成功');</script>");
				//调用自动发送邮件的方法,不能发送邮件？？
				//SendMail.Send(name, email, mess);
				Thread newth=new Thread(){
					public void run()
					{
						String smtphost = null; // 发送邮件服务器
						String user = null; // 邮件服务器登录用户名
						String password = null; // 邮件服务器登录密码
						String from = null; // 发送人邮件地址
						String to = email; // 接受人邮件地址
						String subject = null; // 邮件标题
						String body = null; // 邮件内容
						try {
							smtphost = "smtp.qq.com";
							user = "492899414";
							password = "pigyc6708";
							from = "492899414@qq.com";
							subject = "感谢您的留言(南师大——大创)";
							body = "感谢您的留言,>.<";
						} catch (Exception e) {
						}
						try {
							Properties props = new Properties();
							props.put("mail.smtp.host", smtphost);
							props.put("mail.smtp.auth", "true");
							Session sess = Session.getDefaultInstance(props, null);
							sess.setDebug(true);
							MimeMessage message = new MimeMessage(sess);
							InternetAddress fromAddress = new InternetAddress(from);
							message.setFrom(fromAddress);
							InternetAddress toAddress = new InternetAddress(to);
							message.addRecipient(Message.RecipientType.TO,
									toAddress);
							message.setSubject(subject);
							message.setText(
											"尊敬的"
											+ "<span style='color:red'>"+name+"</span>"
											+ ","
											+ body
											+ "<br>您的留言内容为:&nbsp;&nbsp;<span style='color:red'>"
											+ mess + "</span>", "utf-8", "html");
							Transport transport = sess.getTransport("smtp");
							transport.connect(smtphost, user, password);
							transport.sendMessage(message,
									message.getAllRecipients());
							transport.close();
						} catch (Exception e) {}
					}
				};
				newth.start();	
			}*/
		}
			//直接调用封装好的方法后会出错？？
			/*		boolean v=CommentsDAO.addComments(new Comment(name,email,phone,mess));
					if (v == false)
						out.write("<script>alert('每人每日只能留言一次');</script>");
					else 
					{
						out.write("<script>alert('留言成功');</script>");
					}*/
	%>
</body>
</html>
