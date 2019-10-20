<%@ include file="/WEB-INF/views/include.jsp" %>

<body>

<%@ include file="/WEB-INF/views/navbarCharity.jsp" %>

<br/>
<br/>

<%@ page import="au.charityhub.app.domain.Charity" %>
<%@ page import="au.charityhub.app.domain.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="au.charityhub.app.domain.Post" %>
<%@ page import="au.charityhub.app.domain.Liked" %>
<%@ page import="au.charityhub.app.domain.Comment" %>
<%@ page import="java.util.Base64" %>

<% 
Charity c = (Charity) ((Map<String, Object>) request.getAttribute("model")).get("charity");
Map<Long, Boolean> postsLiked = (Map<Long, Boolean>)  ((Map<String, Object>) request.getAttribute("model")).get("postsLiked");
List<Post> posts = c.getPosts();
Set<User> su = (Set<User>) ((Map<String, Object>) request.getAttribute("model")).get("users");

%>
<script>
	$(document).ready(function(){
		
		$(".post_like").click(function(){
			var this1 = $(this);
			$.ajax({
			  method: "POST",
			  url: "post/like",
			  data: { id: this1.attr('id') }
			})
			  .done(function( msg ) {
			     if (msg == "Liked"){
			    	 this1.text("Liked");
			    	 this1.parent().css("background-color","lightblue");
			     }else if (msg == "Unliked") {
			    	 this1.text("Like");
			    	 this1.parent().css("background-color","white");
			     }else {
			    	 
			     }
			  });
		});
		
		$(".comment_button").click (function(){
			var selected = ".comment_place#comment_"+$(this).attr("id");
			$(selected).focus();
		});
		
		$(".comment_place").keyup(function(event) {
		    if (event.keyCode === 13) {
		  
				var this1 = $(this);
				$.ajax({
				  method: "POST",
				  url: "post/comment",
				  data: { id: this1.attr('pid')
					      ,comment: this1.val() }
				})
				  .done(function( msg ) {
					  if (msg=="true"){
						  $("#table_comment_row_"+this1.attr('pid')).show();
						  $("#table_comment_"+this1.attr('pid')).append("<tr><td><%out.print(c.getOrgName()+" : ");%>"+this1.val()+"</td></tr>");
					  }
						  
				  });
		    }
		    
		});
		
		$("#showModal").click(function(){
			$('#exampleModal').modal('show');
		});
	});
</script>

<div class="container">
  <div class="row">
    <div class="col-3" style="text-align:center">
    
    <img height="100px" width="100px" src="data:image/png;base64,<c:out value="${model.encoded}"/>">
    <br/>
    <c:out value="${model.charity.orgName}" />
    <br/>
    <% out.println(su.size());%> follower
    <br/>
    </div>
    
    <div class="col-6" style="text-align:center">
    	<% 
    	
    		
    		if (posts == null || posts.isEmpty())
    			out.println("No post at the moment");
    		else{ 
    			for (Post p: posts){
    						
    				out.println("<table class='table table-bordered' >");
    				out.println("<tbody>");
    				out.println("<tr>");
    				out.println("<td style='text-align:left;' colspan='2'>");
    				out.println(c.getOrgName() + " wrote:" + "<br/> <br/>");
    				
    				if (p.getFile().length != 0){
    					byte[] encoded = Base64.getEncoder().encode(p.getFile());
    					String encodeds = new String(encoded);
    					out.println("<img height='200px' width='300px' src='data:image/png;base64,"+encodeds+"'/> <br/>");
    				}
    				
    				out.println(p.getDescription());
    				
    				out.println("</td>");
    				out.println("</tr>");
 					if (p.getLikes().size() > 0) {
	    				out.println("<tr>");
	    				out.println("<td style='text-align:left;' colspan='2'>");
	    				out.println(p.getLikes().size() + " likes");
	    				out.println("</td>");
	    				out.println("</tr>");
 					}
 					
 					
 					 
	    				out.println("<tr id='table_comment_row_"+p.getId()+ "' ");
	    				if (p.getComments().size() == 0) 
	    					out.println("style='display:none'");
	    				out.println(">");
	    				out.println("<td style='text-align:left;' colspan='2'>");
	    				out.println("<table id='table_comment_"+p.getId()+"' class='table_comment table'>");
	    				for (Comment co : p.getComments()){
		    				out.println("<tr>");
		    				out.println("<td>");
			    				if (co.getCharity() != null)
			    					out.println(co.getCharity().getOrgName() + " : " + co.getComment());
			    				else
			    					out.println(co.getUser().getFirstName() + " : " + co.getComment());	
		    				out.println("</td>");
		    				out.println("</tr>");
	    				}
	    				out.println("</table>");
	    				out.println("</td>");
	    				out.println("</tr>");
 					
    				
    				out.println("<tr>");
    				if (postsLiked.get(p.getId())){
	    				out.println("<td style='border:none; background:lightblue'>");
	    				out.print("<a class='post_like' id='"+p.getId()+"' href='###'> Liked </a>");
    				}else{
    					out.println("<td style='border:none'>");
	    				out.print("<a class='post_like' id='"+p.getId()+"' href='###'> Like </a>");
    				}
    				out.println("</td>");
    				out.println("<td style='border:none'>");
    				out.print("<a class='comment_button' id='"+p.getId()+"' href='###'> Comment </a>");
    				out.println("</td>");
    				out.println("</tr>");
    				
    				out.println("<tr>");
    				out.println("<td colspan='2'>");
    				out.print("<input id='comment_"+p.getId()+"' pid='"+p.getId()+"' type='text' class='comment_place form-control' placeholder='Write a comment...'>");
    				out.println("</td>");
    				out.println("</tr>");
    				out.println("</tbody>");
    				out.println("</table>");
    			}
    		}
    			
    	%>
    </div>
    
    <div class="col-3" style="text-align:center">
    Followed by: <br/> 
    
    <% 
    	int count = 0;
    	for (User u: su){
    		out.println(u.getFirstName() + " " + u.getLastName());
    		out.println("<br/>");
    		if(count ==2)
    			break;
    		count++;
    	}
    %>
    <a href="###" id="showModal" >View More +</a>
    
    </div>
  </div>
</div>


<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Followers</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      
      <% 

    	for (User u: su){
    		out.println(u.getFirstName() + " " + u.getLastName());
    		out.println("<br/>");
    	}
   	  %>
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

</body>

