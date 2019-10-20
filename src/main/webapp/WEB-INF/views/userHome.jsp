<%@ include file="/WEB-INF/views/include.jsp" %>

<body>

<%@ include file="/WEB-INF/views/navbarUser.jsp" %>

<br/>
<br/>

<%@ page import="au.charityhub.app.domain.Charity" %>
<%@ page import="au.charityhub.app.domain.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="au.charityhub.app.domain.Post" %>
<%@ page import="au.charityhub.app.domain.Liked" %>
<%@ page import="au.charityhub.app.domain.Comment" %>
<%@ page import="java.util.Base64" %>

<%
User u  =  (User) ((Map<String, Object>) request.getAttribute("model")).get("user");
List<Post> posts =  (List<Post>) ((Map<String, Object>) request.getAttribute("model")).get("posts");
Map<Long, Boolean> postsLiked = (Map<Long, Boolean>)  ((Map<String, Object>) request.getAttribute("model")).get("postsLiked");
Set<Charity> csSuggested = (Set<Charity>) ((Map<String, Object>) request.getAttribute("model")).get("csSuggested");
%>

<style>
.tableSC td, .tableSC th {
    border: none;
}

table.tableSC {
    border: 1px solid #dee2e6;
}
</style>
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
						  $("#table_comment_"+this1.attr('pid')).append("<tr><td><%out.print(u.getFirstName()+" : ");%>"+this1.val()+"</td></tr>");
					  }
						  
				  });
		    }
		    
		});
		
		$(".follow").click(function(){
			var this1 = $(this);
			$.ajax({
			  method: "POST",
			  url: "ajax/follow",
			  data: { id: this1.attr('id') }
			})
			  .done(function( msg ) {
			     if (msg == "Followed"){
			    	 this1.text("Unfollow");
			    	 this1.removeClass("btn-primary");
			    	 this1.addClass("btn-info");
			     }else if (msg == "Unfollowed") {
			    	 this1.text("Follow");
			    	 this1.removeClass("btn-info");
			    	 this1.addClass("btn-primary");
			     }
			  });
		});
		
	});
</script>

<div class="container">
  <div class="row">
    <div class="col-3" style="text-align:center">
   		<div> <img height="100px" width="100px" src="data:image/png;base64,<c:out value="${model.encoded}"/>"> </div>
    	<div> <c:out value="${model.user.firstName}"/> <c:out value="${model.user.lastName}"/> </div>
    	<div> <% out.println(u.getCharities().size() + " followed");  %></div>
    </div>
    <div class="col-6" style="text-align:center">
    
    <% 
    if (posts == null) {
    	out.println ("No post to show, follow more charity");
    }else {
	    for (Post p: posts){
	    	out.println("<table class='table table-bordered' >");
			out.println("<tbody>");
			out.println("<tr>");
			out.println("<td style='text-align:left;' colspan='2'>");
			out.println(p.getCharity().getOrgName() + " wrote:" + "<br/> <br/>");
			
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
     <table class='table tableSC'>
     <tr>
     <td colspan=2 style="border-bottom: 1px solid #dee2e6">Suggested Charity</td>
     </tr>
     
     	<% 
     		for (Charity c : csSuggested){
     			out.println("<tr>");
     			out.println("<td> "+c.getOrgName()+" </td>");
     			if ( ! u.getCharities().contains(c) ){
     				out.println("<td> <button type='button' id="+c.getId()+" class='btn btn-primary follow btn-sm'>Follow</button></td>");
     			}else{
     				out.println("<td> <button type='button' id="+c.getId()+" class='btn btn-info follow btn-sm'>Unfollow</button></td>");
     			}
     			out.println("</tr>");
     		}
     	
     	%>
     
     </table>
     
    </div>
  </div>
</div>

</body>