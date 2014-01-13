<jsp:include page="../include/header.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<section id="main">
    <div class="test"><fn:link page="${computerPage.currentPage}" searchString="${computerPage.searchString}" action="dashboard" sort="${computerPage.sort}" lang="fr">FR</fn:link>/
        <fn:link page="${computerPage.currentPage}" searchString="${computerPage.searchString}" action="dashboard" sort="${computerPage.sort}" lang="en">EN</fn:link></div>
    <div class="container">
        <div class="alert alert-danger">
            <spring:message code="page.error.404" />
            <br/>${exception}
        </div>
    </div>
</section>
<jsp:include page="../include/scripts.jsp" />
<script src="js/dashboard.js"></script>
<jsp:include page="../include/footer.jsp" />