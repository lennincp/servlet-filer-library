<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Servlet Filter Library</title>
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet"></link>
</head>
<body>
	<div class="container">
		<div class="panel panel-default" style="max-width: 330px; margin: 0px auto; margin-top: 10%; padding: 3%">
			<form action="LoginServlet" method="post">
				<input type="text" name="username" class="form-control" placeholder="Username">
				<input type="password" name="password" class="form-control" placeholder="Password">
				<input class="form-control btn btn-info" type="submit" value="Sign in" style="margin-top: 5%; margin-bottom: 5%">
			</form>
			<div class="alert alert-danger" style="padding: 1%; padding-left: 2%; margin: 0px auto">
				<span class="glyphicon glyphicon-exclamation-sign"></span>
				Incorrect username or password
			</div>
		</div>
	</div>
</body>
</html>