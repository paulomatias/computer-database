<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="WEB-INF/cdblib" prefix="fn" %>
<%@ attribute name="totalCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="recordCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="pageCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer"%>
<%@ attribute name="limit" required="true" type="java.lang.Integer"%>
<%@ attribute name="searchString" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ attribute name="sort" required="true" type="java.lang.Integer"%>

<ul id="pagination">


    <c:if test="${ currentPage == 1 }">
        <li class="prev disabled"><b>&lt;&lt;</b></li>
        <li class="prev disabled"><b>&larr; Previous</b></li>
    </c:if>
    <c:if test="${ currentPage > 1 }">
        <li class="prev">
            <fn:link page="${1}" searchString="${searchString}" action="${action}" sort="${sort}">
                <b>&lt;&lt;</b>
            </fn:link></li>
        <li class="prev ">
            <fn:link page="${currentPage-1}" searchString="${searchString}" action="${action}" sort="${sort}">
                <b>&larr; Previous</b>
            </fn:link></li>
    </c:if>

    <li class="current"><a><b>${(currentPage-1)*limit+1}</b> to <b>${(currentPage-1)*limit+recordCount}</b> of <b>${totalCount}</b></a>
    </li>

    <c:if test="${ currentPage == pageCount }">
        <li class="next disabled"><b>Next &rarr;</b></li>
        <li class="next disabled"><b>&gt;&gt;</b></li>
    </c:if>
    <c:if test="${ currentPage != pageCount }">
        <li class="next ">
            <fn:link page="${currentPage+1}" searchString="${searchString}" action="${action}" sort="${sort}">
                <b>Next &rarr;</b>
            </fn:link>
        </li>
        <li class="next">
            <fn:link page="${pageCount}" searchString="${searchString}" action="${action}" sort="${sort}">
                <b>&gt;&gt;</b>
            </fn:link>
        </li>
    </c:if>

</ul>