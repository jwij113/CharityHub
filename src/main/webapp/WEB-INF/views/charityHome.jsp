<%@ include file="/WEB-INF/views/include.jsp" %>

<body>

<%@ include file="/WEB-INF/views/navbarCharity.jsp" %>

<br/>
<br/>

<%@ page import="au.charityhub.app.domain.Charity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="au.charityhub.app.domain.Post" %>
<%@ page import="java.util.Base64" %>

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
    
    <div class="col-6" style="text-align:center">
    	<% 
    		Charity c = (Charity) ((Map<String, Object>) request.getAttribute("model")).get("charity");
		
    		List<Post> posts = c.getPosts();
    		
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
    				
    				out.println("<tr>");
    				out.println("<td style='border:none'>");
    				out.print("<a href='#'> Like </a>");
    				out.println("</td>");
    				out.println("<td style='border:none'>");
    				out.print("<a href='#'> Comment </a>");
    				out.println("</td>");
    				out.println("</tr>");
    				
    				out.println("<tr>");
    				out.println("<td colspan='2'>");
    				out.print("<input type='text' class='form-control' placeholder='Write a comment...'>");
    				out.println("</td>");
    				out.println("</tr>");
    				out.println("</tbody>");
    				out.println("</table>");
    			}
    		}
    			
    	%>
    </div>
    
    <div class="col-3" style="text-align:center">
    Followed by: <br/> none
    </div>
  </div>
</div>

</body>

