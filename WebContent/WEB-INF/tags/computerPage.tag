<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="totalCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="recordCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="pageCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer"%>
<%@ attribute name="limit" required="true" type="java.lang.Integer"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>

<ul id="pagination">


    <c:if test="${ currentPage == 1 }">
        <li class="prev disabled"><b>&lt;&lt;</b></li>
        <li class="prev disabled"><b>&larr; Previous</b></li>
    </c:if>
    <c:if test="${ currentPage > 1 }">
        <li class="prev"><a href="${action}&page=${1}"><b>&lt;&lt;</b></a></li>
        <li class="prev "><a href="${action}&page=${currentPage-1}"><b>&larr; Previous</b></a></li>
    </c:if>

    <li class="current"><a><b>${(currentPage-1)*limit+1}</b> to <b>${(currentPage-1)*limit+recordCount}</b> of <b>${totalCount}</b></a>
    </li>

    <c:if test="${ currentPage == pageCount }">
        <li class="next disabled"><b>Next &rarr;</b></li>
        <li class="next disabled"><b>&gt;&gt;</b></li>
    </c:if>
    <c:if test="${ currentPage != pageCount }">
        <li class="next "><a href="${action}&page=${currentPage+1}"><b>Next &rarr;</b></a></li>
        <li class="next"><a href="${action}&page=${pageCount}"><b>&gt;&gt;</b></a></li>
    </c:if>

</ul>