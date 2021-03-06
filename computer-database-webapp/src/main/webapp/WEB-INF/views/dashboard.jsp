<jsp:include page="../include/header.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<section id="main">
    <div class="test"><fn:link page="${computerPage.number}" searchString="${searchString}" action="dashboard" sort="${computerPage.sort}" lang="fr">FR</fn:link>/
<fn:link page="${computerPage.number}" searchString="${searchString}" action="dashboard" sort="${computerPage.sort}" lang="en">EN</fn:link></div>
    <div class="container">
        <c:if test="${requestScope.submitAdd == true}">
            <div class="alert alert-success table-bordered"><spring:message code="form.computer.add.success" /></div>
        </c:if>
        <c:if test="${requestScope.submitEdit == true}">
            <div class="alert alert-success table-bordered"><spring:message code="form.computer.update.success" /></div>
        </c:if>
        <c:if test="${requestScope.submitDelete == true}">
            <div class="alert alert-success table-bordered"><spring:message code="form.computer.delete.success" /></div>
        </c:if>
        <h1 id="homeTitle"><spring:message code="dashboard.computers.found"  arguments="${computerPage.totalElements}" /></h1>
    <div id="actions" class="form-horizontal">
		<div class="pull-left">
            <form id="searchForm" action="" method="GET">
                <spring:message code="form.search.name" var="formSearchName"/>
                <input type="search" id="searchbox" name="search" value="${searchString}" class="input-append" placeholder="${formSearchName}" />
                <spring:message code="form.filter.name" var="formFilterName"/>
                <input type="submit" id="searchsubmit" value="${formFilterName}" class="btn btn-primary" />
            </form>
        </div>
        <div class="pull-right">
            <a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="form.computer.add"/></a>
            <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="form.edit"/></a>
        </div>
	</div>
    </div>

    <form id="deleteForm" action="deleteComputer" method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-curved">
            <thead>
            <tr>
                    <!-- Variable declarations for passing labels as parameters -->
                    <!-- Table header for Computer Name -->

                    <th class="editMode" style="width:60px; height: 22px;"><input type="checkbox" id="selectall" style="margin-top: 5px;"/> <span style="vertical-align: top;"> - <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"><i class="fa fa-trash-o fa-lg"></i></a></span></th>
                    <th style="width:33%;">
                        <fn:link page="${computerPage.number}" searchString="${searchString}" action="dashboard" sort="name" dir="${computerPage.sort.getOrderFor('name').direction == 'ASC' ? 'DESC' : 'ASC'}">
                            <spring:message code="computer.name"/>
                        </fn:link>
                    </th>
                    <th style="width:15%;">
                        <fn:link page="${computerPage.number}" searchString="${searchString}" action="dashboard" sort="introduced" dir="${computerPage.sort.getOrderFor('introduced').direction == 'ASC' ? 'DESC' : 'ASC'}">
                        <spring:message code="computer.introduced"/>
                        </fn:link>
                    </th>
                    <!-- Table header for Discontinued Date -->
                    <th style="width:15%;">
                        <fn:link page="${computerPage.number}" searchString="${searchString}" action="dashboard" sort="discontinued" dir="${computerPage.sort.getOrderFor('discontinued').direction == 'ASC' ? 'DESC' : 'ASC'}">
                            <spring:message code="computer.discontinued"/>
                        </fn:link>
                    </th>
                    <!-- Table header for Company -->
                    <th style="width:30%;">
                        <fn:link page="${computerPage.number}" searchString="${searchString}" action="dashboard" sort="companyName" dir="${computerPage.sort.getOrderFor('companyName').direction == 'ASC' ? 'DESC' : 'ASC'}">
                            <spring:message code="company"/>
                        </fn:link>
                    </th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
                <c:forEach var="comp" items="${computerPage.content}" >
                    <tr>
                        <td class="editMode"><input type="checkbox" name="cb" class="cb" value="${comp.id}"></td>
                        <td><a href="editComputer?id=${comp.id}" onclick="">${comp.name}</a></td>
                        <td><joda:format value="${comp.introduced}" locale="${locale}" style="M-" /></td>
                        <td><joda:format value="${comp.discontinued}" locale="${locale}" style="M-" /></td>
                        <td>${comp.company.name}</td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="text-center">
            <fn:computerPagination totalCount="${computerPage.totalElements}" recordCount="${computerPage.numberOfElements}" pageCount="${computerPage.totalPages}" currentPage="${computerPage.number}" limit="${computerPage.size}" searchString="${searchString}" action="dashboard"></fn:computerPagination>
        </div>
    </div>
</section>
<jsp:include page="../include/scripts.jsp" />
<script src="js/dashboard.js"></script>
<jsp:include page="../include/footer.jsp" />
