<%--
  Created by IntelliJ IDEA.
  User: danie
  Date: 24/03/2022
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <div class="themeLocal">
        <c:choose>
            <c:when test="${requestContext.locale.language eq 'en'}">
                <c:url var="localeUrl" value="/">
                    <c:param name="locale" value="de"/>
                </c:url>
                <a href="${localeUrl}"><spring:message code="locale.de"/></a>
            </c:when>
            <c:otherwise>
                <c:url var="localeUrl" value="/">
                    <c:param name="locale" value="en"/>
                </c:url>
                <a href="${localeUrl}"><spring:message code="locale.en"/></a>
            </c:otherwise>
        </c:choose> |
        <c:choose>
            <c:when test="${requestContext.theme.name eq 'green'}">
                <c:url var="themeUrl" value="/">
                    <c:param name="theme" value="blue"/>
                </c:url>
                <a href="${themeUrl}"><spring:message code="theme.Blue"/></a>
            </c:when>
            <c:otherwise>
                <c:url var="themeUrl" value="/">
                    <c:param name="theme" value="green"/>
                </c:url>
                <a href="${themeUrl}"><spring:message code="theme.Green"/></a>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="menu">
        <ul>
            <li><c:if test="${menuTab eq 'home'}">
                <strong><a href="<c:url value="/"/>"><spring:message code="menu.home"/></a></strong>
            </c:if>
                <c:if test="${menuTab != 'home'}">
                    <a href="<c:url value="/"/>"><spring:message code="menu.home"/></a>
                </c:if>
            </li>
            <li><c:if test="${menuTab eq 'persons'}">
                <strong><a href="<c:url value="/persons/list"/>"><spring:message code="menu.persons"/></a></strong>
            </c:if>
                <c:if test="${menuTab != 'persons'}">
                    <a href="<c:url value="/persons/list"/>"><spring:message code="menu.persons"/></a>
                </c:if>
            </li>
            <li><c:if test="${menuTab eq 'users'}">
                <strong><a href="<c:url value="/users/list"/>"><spring:message code="menu.users"/></a></strong>
            </c:if>
                <c:if test="${menuTab != 'users'}">
                    <a href="<c:url value="/users/list"/>"><spring:message code="menu.users"/></a>
                </c:if>
            </li>
            <li><c:if test="${menuTab eq 'authors'}">
                <strong><a href="<c:url value="/authors/list"/>"><spring:message code="menu.authors"/></a></strong>
            </c:if>
                <c:if test="${menuTab != 'authors'}">
                    <a href="<c:url value="/authors/list"/>"><spring:message code="menu.authors"/></a>
                </c:if>
            </li>
            <li><c:if test="${menuTab eq 'books'}">
                <strong><a href="<c:url value="/books/list"/>"><spring:message code="menu.books"/></a></strong>
            </c:if>
                <c:if test="${menuTab != 'books'}">
                    <a href="<c:url value="/books/list"/>"><spring:message code="menu.books"/></a>
                </c:if>
            </li>
            <sec:authorize access="isAuthenticated()">
                <li>
                    <spring:url value="/logout" var="logoutUrl"/>
                    <form action="${logoutUrl}" id="logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <a href="#" onclick="document.getElementById('logout').submit();"><spring:message code="menu.logout"/></a>
                </li>
            </sec:authorize>
        </ul>
    </div>
    <div class="content">
        <h2>
            <spring:message code="persons.list.title"/>
        </h2>

        <div class="persons">
            <table>
                <thead>
                <tr>
                    <td>
                        <spring:message code="label.Person.count"/>
                    </td>
                    <td>
                        <spring:message code="label.Person.username"/>
                    </td>
                    <td>
                        <spring:message code="label.Person.firstname"/>
                    </td>
                    <td>
                        <spring:message code="label.Person.lastname"/>
                    </td>
                </tr>
                </thead>
                <c:forEach var="person" items="${persons}">
                    <tr>
                        <td>
                            <spring:url var="showUrl" value="{id}">
                                <spring:param name="id" value="${person.id}"/>
                            </spring:url>
                            <a href="${showUrl}">${person.id}</a>
                        </td>
                        <td>
                                ${person.username}
                        </td>
                        <td>
                                ${person.firstName}
                        </td>
                        <td>
                                ${person.lastName}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <div class="footer">
        <p><spring:message code="footer.text"/></p>
    </div>
</div>
</body>
</html>
