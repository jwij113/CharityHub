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
<%@ page import="java.util.Base64" %>

<style>
.tableSC td, .tableSC th {
    border: none;
}

table.tableSC {
    border: 1px solid #dee2e6;
}
</style>

<script type="text/javascript">

$(document).ready(function(){
	
	var url_str = window.location.href;
	var url = new URL(url_str);
	var txt = url.searchParams.get("txt");
	
	if (txt == null)
		txt="";
	
	$(".follow").click(function(){
		var this1 = $(this);
		window.location.href = 'follow?txt='+txt+'&id='+this1.attr("id");
	});
	
	$(".unfollow").click(function(){
		var this1 = $(this);
		window.location.href = 'unfollow?txt='+txt+'&id='+this1.attr("id");
	});
	
	
	$(".ajaxFollow").click(function(){
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

<%
List<Charity> cs = (List<Charity>) ((Map<String, Object>) request.getAttribute("model")).get("charities");
User u = (User) ((Map<String, Object>) request.getAttribute("model")).get("user");
Set<Charity> csSuggested = (Set<Charity>) ((Map<String, Object>) request.getAttribute("model")).get("csSuggested");
%>
<div class="container">
  <div class="row">
    <div class="col-3" style="text-align:center">
   		<div> <img height="100px" width="100px" src="data:image/png;base64,<c:out value="${model.encoded}"/>"> </div>
    	<div> <c:out value="${model.user.firstName}"/> <c:out value="${model.user.lastName}"/> </div>
    	<div> <% out.println(u.getCharities().size() + " followed");  %></div>
    </div>
    <div class="col-6" style="text-align:center">
    
    <form method="get">
	  <div class="form-row">
	    <div class="col-8">
	      <input type="text" name="txt" value = "${model.txt}" class="form-control" placeholder="Type organisation name...">
	    </div>
	    <div class="col-4">
	     	<button type="submit" class="btn btn-outline-primary ">Search</button>
	    </div>
	  </div>
	</form>
	
	<br/>
    
    	<%
	    	out.println("<table class='table table-bordered' >");
			out.println("<tbody>");
			if (cs.isEmpty())
				out.println("No result found");
			
    		for (Charity c : cs){
    			out.println("<tr>");
				out.println("<td style='text-align:left;'>");
    			if (c.getProfilePic().length != 0){
					byte[] encoded = Base64.getEncoder().encode(c.getProfilePic());
					String encodeds = new String(encoded);
					out.println("<img height='80px' width='80px' src='data:image/png;base64,"+encodeds+"'/> <br/>");
				}
    			out.print ("</td>");
				out.println("<td style='text-align:left;'>");
    			out.print (c.getOrgName());
    			out.print ("</td>");
    			out.println("<td style='text-align:middle;'>");
    				if ( ! u.getCharities().contains(c) ){
    					out.print ("<button type='button' id= '"+c.getId()+"' class='btn btn-primary follow'>Follow</button>");
    				}else {
    					out.print ("<button type='button' id= '"+c.getId()+"' class='btn btn-info unfollow'>Unfollow</button>");
    				}
    			out.print ("</td>");
    			out.println("</tr>");
    			
    		}
    		out.println("</tbody>");
			out.println("</table>");
			out.print ("<br/>");
    	
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
     				out.println("<td> <button type='button' id="+c.getId()+" class='btn btn-primary ajaxFollow btn-sm'>Follow</button></td>");
     			}else{
     				out.println("<td> <button type='button' id="+c.getId()+" class='btn btn-info ajaxFollow btn-sm'>Unfollow</button></td>");
     			}
     			out.println("</tr>");
     		}
     	
     	%>
     
     </table>
    </div>
  </div>
</div>

</body>