<%@ include file="/WEB-INF/views/include.jsp" %>

<body>

<%@ include file="/WEB-INF/views/navbar.jsp" %>

<br/>
<br/>

<% 	 
	String e = request.getParameter("e");
%>


<div class="container">
  <div class="row">
    <div class="col-sm">
    </div>
    <div class="col-sm">
    
    	<% 
		    if (e!=null && e.equals("1")){
				out.print("<div class='alert alert-danger'>Unable to register</div>"); 
		    }
    	
		%>

		<div class = "form-group form">
			<form method = "POST" enctype="multipart/form-data">
			<div class="form-group">
				<label>First Name</label>
				<input type ="text" name = "first" class = "form-control" placeHolder="Enter first name"/>
			</div>
			<div class="form-group">
				<label>Last Name </label>
				<input type="text"  name = "last" class = "form-control" placeHolder="Enter last name"/>
			</div>
			
			<div class="form-group">
				<label>Email</label>
				<input type ="text" id = "email" name = "email" class = "form-control" placeHolder="Enter email"/>
			</div>
			<div class="form-group">
				<label>Password </label>
				<input type="password" id = "password" name = "password" class = "form-control" placeHolder="Password"/>
			</div>
			
			<div class="form-group">
		    <label for="desc">Profile picture</label>
		    <input name="profile_pic" type="file" class="form-control-file" id="exampleFormControlFile1">
		  	</div>
	<!-- 		<div>
				<label>Date of birth: </label>
				<input type="" id = "password" name = "password class = "form-control"/>
			</div> -->
			
			<button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</div>
	</div>
	<div class="col-sm">
    </div>
  </div>
</div>
</body>