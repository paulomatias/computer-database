<%@ tag language="java" description="Link wrapper" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="searchString" required="true" type="java.lang.String"%>
<%@ attribute name="lang" required="false" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ attribute name="sort" required="false" type="java.lang.String"%>
<%@ attribute name="dir" required="false" type="java.lang.String"%>

<a href="${action}?page=${page}&search=${searchString}${sort == null ? '' : '&sort='}${sort}${dir == null ? '' : '&dir='}${dir}${lang == null ? '' : '&ln='}${lang}">
    <jsp:doBody/>
</a>
