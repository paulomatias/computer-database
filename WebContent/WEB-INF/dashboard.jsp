<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section id="main">
    <c:if test="${requestScope.submitAdd == true}">
        <div class="alert-message">Computer added successfully</div>
    </c:if>
    <c:if test="${requestScope.submitEdit == true}">
        <div class="alert-message">Successfully updated</div>
    </c:if>

	<h1 id="homeTitle">456 Computers found</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<a class="btn success" id="add" href="addComputer">Add Computer</a>
	</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
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
                <c:forEach var="comp" items="${computers}" >
                    <tr>
                        <td><a href="editComputer?id=${comp.id}" onclick="">${comp.name}</a></td>
                        <!-- Calls getIntroducedFormated() method using reflection -->
                        <td>${comp.introducedFormated}</td>
                        <td>${comp.discontinuedFormated}</td>
                        <td>${comp.company.name}</td>
                    </tr>
                </c:forEach>
			</tbody>
		</table>
</section>

<jsp:include page="include/footer.jsp" />
