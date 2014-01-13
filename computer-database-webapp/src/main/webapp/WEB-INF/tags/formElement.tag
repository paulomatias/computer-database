<%@tag language="java" description="Wrapper div for spring form elements and error handling" pageEncoding="UTF-8" body-content="scriptless"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="label" required="true" type="java.lang.String"%>
<%@attribute name="helpInline" required="false" type="java.lang.String"%>

<spring:bind path="${path}">
    <div class="clearfix ${status.error ? 'table-bordered alert-danger' : '' }">
   		<label for="${path}"><spring:message code="${label}"/></label>
   	 	<div class="input">
   	 		<jsp:doBody/>
            <c:if test="${helpInline != null && helpInline != ''}">
                <span class="help-inline"><spring:message code="${helpInline}"/></span>
            </c:if>
   	 	</div>        	
    </div>
</spring:bind>