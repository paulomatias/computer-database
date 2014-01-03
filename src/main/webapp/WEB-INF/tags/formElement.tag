<%@tag language="java" description="Wrapper div for spring form elements and error handling" pageEncoding="UTF-8" body-content="scriptless"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="label" required="true" type="java.lang.String"%>
<%@attribute name="helpInline" required="true" type="java.lang.String"%>
<spring:bind path="${path}">
    <div class="clearfix ${status.error ? 'table-bordered alert-danger' : '' }">
   		<label for="${path}">${label}</label>   
   	 	<div class="input">
   	 		<jsp:doBody/>
   	 		<span class="help-inline">${helpInline}</span>
   	 	</div>        	
    </div>
</spring:bind>