<%@ include file="/WEB-INF/views/include.jsp" %>

<body>

<%@ include file="/WEB-INF/views/navbar.jsp" %>

	<div class = "form-group form">
		<form method = "POST">
		<div>
			<label>User email:</label>
			<input type ="text" id = "email" name = "email" class = "form-control"/>
		</div>
		<div>
			<label>Password: </label>
			<input type="password" id = "password" name = "password" class = "form-control"/>
		</div>
<!-- 		<div>
			<label>Date of birth: </label>
			<input type="" id = "password" name = "password class = "form-control"/>
		</div> -->
		
		<button id = "loginBtn" class="form-control">register</button>
		</form>
	</div>
</body>