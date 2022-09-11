<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../static/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Game</title>
</head>
<body>
<div class="container shadow rounded min-vh-100 py-2 bg-dark text-white">
    <div class="row py-1">
        <jsp:include page="part/statistic.jsp"/>
        <div class="col-6">
            <h4 class="text-center">${locationName}</h4>
        </div>
        <div class="col-3">
            <div class="progress" style="height: 30px;">
                <div class="progress-bar bg-danger" role="progressbar"
                     style="width: ${userHealthInfo.getPercentOfMax()}%"
                     aria-valuenow="${userHealthInfo.getPercentOfMax()}"
                     aria-valuemin="0"
                     aria-valuemax="100"> ${userHealthInfo.getHealth()}/${userHealthInfo.getMaxHealth()} HP
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-3">
            <div class="row">
                <div class="col">
                    <jsp:include page="part/npcs.jsp"/>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="row g-2 py-1">
                <div class="col">
                    <img class="img-fluid"
                         src="${pageContext.request.contextPath}/img/DefaultMap.png"
                         alt="Quest Map">
                </div>
            </div>
            <div class="row g-2 py-1">
                <div class="col border rounded overflow-auto" id="gameLog" style="height: 100px; max-height: 100px;">
                    <c:forEach items="${gameLog}" var="logLine">
                        <span>${logLine}</span><br>
                    </c:forEach>
                </div>
            </div>
            <jsp:include page="part/paths.jsp"/>
            <jsp:include page="part/quests.jsp"/>
            <jsp:include page="part/inventory.jsp"/>
        </div>
        <div class="col-3">
            <div class="row">
                <div class="col">
                    <jsp:include page="part/items.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/jquery.redirect.js"></script>
<script src="${pageContext.request.contextPath}/static/utilities.js"></script>
</body>
</html>
