<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${paths}" var="path">
    <div class="row g-2 py-1">
        <div class="col rounded bg-secondary">
            <div class="form-check mb-2 mt-2">
                <input class="form-check-input" onclick="nextPath(${path.getLocationInfo().getId()})"
                       type="radio" name="nextLocationId"
                       id="nextLocationId${path.getLocationInfo().getId()}"
                       <c:if test="${!path.isOpened()}">disabled</c:if>>
                <label class="form-check-label"
                       for="nextLocationId${path.getLocationInfo().getId()}">${path.getLocationInfo().getName()}
                    <c:if test="${!path.isOpened()}">[Need a quest key]</c:if></label>
            </div>
        </div>
    </div>
</c:forEach>
