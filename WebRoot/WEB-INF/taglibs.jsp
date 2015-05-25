<%@page import="com.cc.core.utils.DateUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<c:set var="dateTimeFormat" value="<%=DateUtils.DATE_FORMAT_SS%>"/>
<c:set var="dateFormat" value="<%=DateUtils.DATE_FORMAT_DD%>"/>
