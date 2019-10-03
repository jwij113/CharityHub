<%@ include file="/WEB-INF/views/include.jsp" %>

<body>

<%@ include file="/WEB-INF/views/navbar.jsp" %>

	<div class = "form-group form">
		<form action = "register" method = "POST">
		<div>
			<label>User email:</label>
			<input type ="text" id = "email" name = "email" class = "form-control"/>
		</div>
		<div>
			<label>Password: </label>
			<input type="password" id = "password" name = "password class = "form-control"/>
		</div>
		<button id = "loginBtn" class="form-control">register</button>
		</form>
	</div>
</body>