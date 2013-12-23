<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="js/dashboard.js"></script>

<section id="main">
    <div class="container">
        <c:if test="${requestScope.submitAdd == true}">
            <div class="alert alert-success table-bordered">Computer added successfully</div>
        </c:if>
        <c:if test="${requestScope.submitEdit == true}">
            <div class="alert alert-success table-bordered">Successfully updated</div>
        </c:if>
        <c:if test="${requestScope.submitDelete == true}">
            <div class="alert alert-success table-bordered">Successfully deleted</div>
        </c:if>
        <h1 id="homeTitle">${computerPage.recordCount} Computers found</h1>
    <div id="actions" class="form-horizontal">
		<div class="pull-left">
            <form id="searchForm" action="" method="GET">
                <input type="search" id="searchbox" name="search" value="" class="input-append" placeholder="Search name" />
                <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
            </form>
        </div>
        <div class="pull-right">
            <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a>
            <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
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
                    <th>Computer Name</th>
                    <th>Introduced Date</th>
                    <!-- Table header for Discontinued Date -->
                    <th>Discontinued Date</th>
                    <!-- Table header for Company -->
                    <th>Company</th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody>
                <c:forEach var="comp" items="${computerPage.items}" >
                    <tr>
                        <td class="editMode"><input type="checkbox" name="cb" class="cb" value="${comp.id}"></td>
                        <td><a href="editComputer?id=${comp.id}" onclick="">${comp.name}</a></td>
                        <!-- Calls getIntroducedFormated() method using reflection -->
                        <td>${comp.introducedFormated}</td>
                        <td>${comp.discontinuedFormated}</td>
                        <td>${comp.company.name}</td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<jsp:include page="include/footer.jsp" />
