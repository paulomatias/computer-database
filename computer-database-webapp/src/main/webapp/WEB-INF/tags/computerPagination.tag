<%@ tag language="java" description="Pager implementation" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="WEB-INF/cdblib" prefix="fn" %>
<%@ attribute name="totalCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="recordCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="pageCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer"%>
<%@ attribute name="limit" required="true" type="java.lang.Integer"%>
<%@ attribute name="searchString" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ attribute name="sort" required="false" type="java.lang.String"%>
<%@ attribute name="dir" required="false" type="java.lang.String"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<ul id="pagination">
    <c:if test="${ currentPage == 0 }">
        <li class="prev disabled"><b>&lt;&lt;</b></li>
        <li class="prev disabled"><b>&larr; <spring:message code="pager.previous"/></b></li>
    </c:if>
    <c:if test="${ currentPage > 0 }">
        <li class="prev">
        	<fn:link page="${1}" searchString="${searchString}" action="${action}" sort="${sort}">
            	<b>&lt;&lt;</b>
           	</fn:link>
        </li>
        <li class="prev ">
            <fn:link page="${currentPage}" searchString="${searchString}" action="${action}" sort="${sort}">
                <b>&larr; <spring:message code="pager.previous"/></b>
            </fn:link></li>
    </c:if>

    <li class="current"><a><spring:message code="pager.actual"
       arguments="${(currentPage)*limit+1};${(currentPage)*limit+recordCount};${totalCount}"
       htmlEscape="false"
       argumentSeparator=";"/></a>
    </li>

    <c:if test="${ currentPage+1 == pageCount }">
        <li class="next disabled"><b><spring:message code="pager.next"/> &rarr;</b></li>
        <li class="next disabled"><b>&gt;&gt;</b></li>
    </c:if>
    <c:if test="${ currentPage+1 != pageCount }">
        <li class="next ">
            <fn:link page="${currentPage+2}" searchString="${searchString}" action="${action}" sort="${sort}" dir="${dir}">
                <b><spring:message code="pager.next"/> &rarr;</b>
            </fn:link>
        </li>
        <li class="next">
            <fn:link page="${pageCount}" searchString="${searchString}" action="${action}" sort="${sort}" dir="${dir}">
                <b>&gt;&gt;</b>
            </fn:link>
        </li>
    </c:if>

</ul>