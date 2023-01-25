<%--
  Created by IntelliJ IDEA.
  User: danie
  Date: 25/01/2023
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>
        <spring:message code="home.title" />
    </title>
    <spring:theme var="cssStyle" code="css.style"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="${cssStyle}" />"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/styles/general.css" />"/>
</head>
<body>
<div class="banner"></div>
    <div class="content">
        <div class="login">
<form:form method="POST" action="addUser" modelAttribute="user3" >
        <h1>Register</h1>
        <p>Please fill in this form to create an account.</p>
    <table>
        <tr>
            <td>Username:</td>
            <td>
                <form:input path="username" />
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <form:input path="password" />
            </td>
        </tr>
        <tr>
            <td>First Name:</td>
            <td>
                <form:input path="firstName" />
            </td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td>
                <form:input path="lastName" />
            </td>
        </tr>
        <tr>
            <td>Roles:</td>
            <td>
                <form:radiobuttons path="roles" items="${roles}" />
            </td>
        </tr>
    </table>

    <div class="button">
        <input type="submit" value="Register" />
    </div>
</form:form>
        </div>
    </div>



</body>
</html>
