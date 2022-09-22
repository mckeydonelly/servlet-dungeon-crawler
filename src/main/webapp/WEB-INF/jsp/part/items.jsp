<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${objects}" var="object">
    <div id="object${object.getId()}card" class="card mb-1 text-white bg-secondary">
        <img class="card-img-top"
             src="${pageContext.request.contextPath}/${object.getImgPath()}"
             alt="Card image">
        <div class="card-body">
            <h4 class="card-title">${object.getName()} <c:if
                    test="${!object.isActive()}">[Empty]</c:if></h4>
            <button type="button" class="btn btn-dark"
                    onclick='objectAction("${object.getType()}", ${object.getId()}, "${object.getObjectId()}", "#object${object.getId()}card")'
                    <c:if test="${!object.isActive()}">disabled</c:if>>
                <c:choose>
                    <c:when test="${object.getType() == 'CRATE'}">Open</c:when>
                    <c:otherwise>Take</c:otherwise>
                </c:choose>
            </button>
        </div>
    </div>
</c:forEach>
