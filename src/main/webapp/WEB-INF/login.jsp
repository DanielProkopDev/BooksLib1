<%--
  Created by IntelliJ IDEA.
  User: danie
  Date: 08/04/2022
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<div id="page">
    <div class="banner"></div>
    <div class="content">
        <div class="login">
            <h4><spring:message code="login.hint"/></h4>

            <p><h3><spring:message code="login.message"/></h3></p>

            <spring:url value="/login" var="loginUrl" />
            <form action="${loginUrl}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <table>
                    <tr>
                        <td><label for="username"><spring:message code="login.username"/></label></td>
                        <td> <input type='text' id='username' name='username' value='<c:out value="${user}"/>'/></td>
                    </tr>
                    <tr>
                        <td><label for="password"><spring:message code="login.password"/></label></td>
                        <td><input type='password' id='password' name='password'/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button type="submit"><spring:message code="login.submit"/></button>
                        </td>
                    </tr>
                </table>
            </form>


            <c:if test="${not empty param.auth_error}">
                <div id="errors" class="error">
                    <p><spring:message code="login.fail"/>: ${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
                </div>
            </c:if>
<div class="register" id="register">
    <li>
              <strong> <a type="button" href="<c:url value="/register"/>"><spring:message code="menu.register"/></a></strong>
    </li>
</div>
        </div>
    </div>
    <div class="footer">
        <p><spring:message code="footer.text"/></p>
    </div>
</div>
</body>
</html>
