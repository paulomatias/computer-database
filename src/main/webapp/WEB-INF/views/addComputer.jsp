<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="WEB-INF/cdblib" prefix="fn" %>
<section id="main">



    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>Add Computer</h1>
                <form:form action="addComputer" commandName="computer" method="POST">
                    <fieldset>
                        <fn:formElement path="name" label="Computer name" helpInline="Required">
                            <form:input path="name" value="${requestScope.computer.name}"/>
                        </fn:formElement>
                        <fn:formElement path="introduced" label="Introduced date" helpInline="DD/MM/YYYY">
                            <form:input path="introduced" value="${requestScope.computer.introduced}" pattern="(0?[1-9]|[1-2][0-9]|3[01])/(0?[1-9]|1[012])/(19[6-9][0-9]|20[0-9][0-9])" />
                        </fn:formElement>
                        <fn:formElement path="discontinued" label="Discontinued date" helpInline="DD/MM/YYYY">
                            <form:input path="discontinued" value="${requestScope.computer.discontinued}" pattern="(0?[1-9]|[1-2][0-9]|3[01])/(0?[1-9]|1[012])/(19[6-9][0-9]|20[0-9][0-9])" />
                        </fn:formElement>
                        <fn:formElement path="companyId" label="Company Name" helpInline="">                            
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
                        <input type="submit" value="Add" class="btn btn-primary">
                        or <a href="dashboard" class="btn btn-default">Cancel</a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../include/footer.jsp" />
