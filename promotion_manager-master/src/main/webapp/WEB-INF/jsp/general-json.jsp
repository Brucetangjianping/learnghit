<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{
"errCode":<c:out value="${result.errCode}"/>,
"retCode":<c:out value="${result.retCode}"/>,
"msg":"<c:out value="${result.msg}"/>",
"dtag":"<c:out value="${result.dtag}"/>",
"data":<c:out value="${dataStr}" escapeXml="false"/>
}
