<jsp:include page="../include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/cdblib" prefix="fn" %>
<section id="main">



    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>Add Computer</h1>
                <form action="addComputer" method="POST">
                    <fieldset>
						<div class="clearfix ${fn:bitwiseAnd(error_bits,1) == 1 ? 'alert-danger table-bordered' : ''}">
                            <label for="computer_name">Computer name:</label>
                            <div class="input">
                                <input type="text" id="computer_name" name="computer_name" value="${requestScope.computer.name}"/>
                                <span class="help-inline">Required</span>
                            </div>
                        </div>

						<div class="clearfix ${fn:bitwiseAnd(error_bits,2) == 2 ? 'alert-danger table-bordered' : ''}">
                            <label for="computer_introduced">Introduced date:</label>
                            <div class="input">
                                <input type="text" id="computer_introduced" name="computer_introduced" value="${requestScope.computer.introduced}" pattern="(0?[1-9]|[1-2][0-9]|3[01])/(0?[1-9]|1[012])/(19[6-9][0-9]|20[0-9][0-9])" />
                                <span class="help-inline">DD/MM/YYYY</span>
                            </div>
                        </div>
						<div class="clearfix ${fn:bitwiseAnd(error_bits,4) == 4 ? 'alert-danger table-bordered' : ''}">
                            <label for="computer_discontinued">Discontinued date:</label>
                            <div class="input">
                                <input type="text" id="computer_discontinued" name="computer_discontinued" value="${requestScope.computer.discontinued}" pattern="(0?[1-9]|[1-2][0-9]|3[01])/(0?[1-9]|1[012])/(19[6-9][0-9]|20[0-9][0-9])" />

                                <span class="help-inline">DD/MM/YYYY</span>
                            </div>
                        </div>
                        <div class="clearfix">
                            <label for="company_id">Company Name:</label>
                            <div class="input">
                                <select id="company_id" name="company_id">
                                    <option value="0">--</option>
                                    <c:forEach var="company" items="${companies}" >
										<option value="${company.id}" ${company.id == requestScope.computer.companyId ? 'selected="selected"' : ''}>${company.name}</option>    
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="Add" class="btn btn-primary">
                        or <a href="dashboard" class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../include/footer.jsp" />
