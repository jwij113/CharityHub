<%@ include file="/WEB-INF/views/include.jsp" %>

<body>

<%@ include file="/WEB-INF/views/navbarCharity.jsp" %>

<br/>
<br/>

<div class="container">
  <div class="row">
    <div class="col-3" style="text-align:center">
    
    <img height="100px" width="100px" src="data:image/png;base64,<c:out value="${model.encoded}"/>">
    <br/>
    <c:out value="${model.charity.orgName}" />
    <br/>
    0 follower
    <br/>
    </div>
    
    <div class="col-6">
    
    <h3>Create new post</h3> <br/>
    
    <form method="post" enctype="multipart/form-data">
	  <div class="form-group">
	    <label for="desc">Description </label>
	    <textarea name="description" class="form-control"> </textarea>
	   </div>
	  <div class="form-group">
	    <label for="file">File</label>
	    <input name="file" type="file" class="form-control-file" id="formControlFile">
	    </div>
	  <button type="submit" class="btn btn-primary">Submit</button>
	</form>
    
    </div>
    
    <div class="col-3" style="text-align:center">
    Followed by: <br/> none
    </div>
  </div>
</div>

</body>

