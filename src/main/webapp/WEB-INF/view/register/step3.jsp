<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="member.register" /></title>
</head>
<body>
    <p>
		<spring:message code="register.done">
        	<spring:argument value="${formData.name}" />
        	<spring:argument value="${formData.email}" />
        </spring:message>
    </p>
    <p><a href="<c:url value='/main'/>">[첫 화면 이동]</a></p>
</body>
</html>