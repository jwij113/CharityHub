<%@ include file="/WEB-INF/views/include.jsp" %>

<body>

<%@ include file="/WEB-INF/views/navbar.jsp" %>

<br/>
<br/>

<script>
$(document).ready(function(){
	
	$("#theForm").submit(function(e) {

	    e.preventDefault(); // avoid to execute the actual submit of the form.

	    var form = $(this);
	    var url = form.attr('action');

	    $.ajax({
	           type: "POST",
	           url: url,
	           data: new FormData(this),
	           processData: false,
	           contentType: false,
	           success: function(data)
	           {
	        	  if (data != "Register success")
	              	$("#response").hide().html("<div class='alert alert-danger'>" + data + "</div>").fadeIn('slow');
	        	  else if (data == "Register success"){
	        		$("#response").hide().html("<div class='alert alert-success'>" + data + "</div>").fadeIn('slow');		            	
	        	  	window.location = "../login?w=1";
	        	  }
	           }
	         });


	});
});
</script>



<div class="container">
  <div class="row">
    <div class="col-sm">
    </div>
    <div class="col-sm">
      
		<form id="theForm">
		  <div class="form-group">
		    <label for="exampleInputEmail1">Email address</label>
		    <input type="email" name="email" class="form-control" aria-describedby="emailHelp" placeholder="Enter email">
		  </div>
		  <div class="form-group">
		    <label for="exampleInputPassword1">Password</label>
		    <input name="password" type="password" class="form-control" placeholder="Password">
		  </div>
		  
		  <div class="form-group">
		    <label for="org_name">Organisation name</label>
		    <input name="org_name" type="text" class="form-control"  placeholder="Organisation name">
		  </div>
		  
		  <div class="form-group">
		    <label for="desc">Description</label>
		    <input name="description" type="text" class="form-control"  placeholder="Description">
		  </div>
		  
		  <div class="form-group">
		    <label for="desc">Profile picture</label>
		    <input name="profile_pic" type="file" class="form-control-file" id="exampleFormControlFile1">
		  </div>
		  
		  <div id="response"></div>
		 

		  <button type="submit" class="btn btn-primary">Submit</button>
		</form>

    </div>
    <div class="col-sm">
    </div>
  </div>
</div>


</body>
</html>