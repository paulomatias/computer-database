<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="WEB-INF/cdblib" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1><spring:message code="form.computer.add"/></h1>
                <form:form action="addComputer" commandName="computer" method="POST">
                    <fieldset>
                        <fn:formElement path="name" label="computer.name" helpInline="form.hint.required">
                            <form:input path="name" value="${requestScope.computer.name}"/>
                        </fn:formElement>
                        <fn:formElement path="introduced" label="computer.introduced" helpInline="form.hint.date">
                            <form:input path="introduced" value="${requestScope.computer.introduced}" />
                        </fn:formElement>
                        <fn:formElement path="discontinued" label="computer.discontinued" helpInline="form.hint.date">
                            <form:input path="discontinued" value="${requestScope.computer.discontinued}" />
                        </fn:formElement>
                        <fn:formElement path="companyId" label="company.name" helpInline="">
                                <form:select path="companyId">
                                    <option value="0">--</option>
                                    <c:forEach var="company" items="${companies}" >
                                        <c:choose>
                                            <c:when test="${company.id == requestScope.computer.companyId}">
                                                <option value="${company.id}" selected="selected">${company.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${company.id}">${company.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </form:select>
                        </fn:formElement>
                    </fieldset>
                    <div class="actions pull-right">
                        <spring:message code="form.add" var="lblAdd"/>
                        <input type="submit" value="${lblAdd}" class="btn btn-primary">
                        <spring:message code ="lbl.or" />
                        <a href="dashboard" class="btn btn-default"><spring:message code="form.cancel"/></a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../include/footer.jsp" />
