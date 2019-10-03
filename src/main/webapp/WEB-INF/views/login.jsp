<%@ include file="/WEB-INF/views/include.jsp" %>



<body>

<%@ include file="/WEB-INF/views/navbar.jsp" %>

<br/>
<br/>



<% 	String w = request.getParameter("w"); 
	String e = request.getParameter("e");
%>

<div class="container">
  <div class="row">
    <div class="col-sm">
    </div>
    <div id="mid_login" class="col-sm">
    
    	<% 
    		if (w!=null && w.equals("1")){
    			out.print("<div class='alert alert-success'>Register success, please login below</div>");   
		 	}else if (w!=null && w.equals("2")){
    			out.print("<div class='alert alert-success'>Logout successful</div>");
		 	}else if (e!=null && e.equals("1")){
				out.print("<div class='alert alert-danger'>Email can't be empty</div>");   
		 	}else if (e!=null && e.equals("2")){
				out.print("<div class='alert alert-danger'>Password can't be empty</div>");   
		 	}else if (e!=null && (e.equals("3") || e.equals("4"))){
				out.print("<div class='alert alert-danger'>Login failed: Email and password don't match</div>");   
		 	}else if (e!=null && e.equals("5") ){
				out.print("<div class='alert alert-danger'>Login session expired</div>");   
		 	}
		%>
    
		<form id="theForm" method="post">
		  <div class="form-group">
		    <label for="exampleInputEmail1">Email address</label>
		    <input type="email" name="email" class="form-control" aria-describedby="emailHelp" placeholder="Enter email">
		  </div>
		  <div class="form-group">
		    <label for="exampleInputPassword1">Password</label>
		    <input name="password" type="password" class="form-control" placeholder="Password">
		  </div>
		   <br/>
		   <button type="submit" class="btn btn-primary">Log In</button>
		</form>
  	</div>
  		  <div class="col-sm">
    	  </div>
</div>
</div>
		  
</body>
</html>