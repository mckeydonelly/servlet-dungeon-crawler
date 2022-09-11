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
    <title>Dialog</title>
</head>
<body>
<div class="container shadow rounded min-vh-100 py-2 bg-dark text-white">
    <div class="row py-1">
        <div class="col-3"></div>
        <div class="col-6">
            <h4 class="text-center">${dialogInfo.getNpcName()}</h4>
        </div>
        <div class="col-3">
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <a class="btn-close btn-close-white" href="${pageContext.request.contextPath}/room"></a>
            </div>
        </div>
    </div>
    <div class="row py-1">
        <div class="col-4">
            <div id="npc${npc.getNpcId()}card" class="card mb-1 text-white bg-secondary">
                <img class="card-img-top"
                     src="${dialogInfo.getImgPath()}"
                     alt="Card image">
            </div>
        </div>
        <div class="col-6">
            <div class="row g-1 py-1">
                <div class="row g-1 py-1">
                    <div class="col rounded bg-secondary">
                        ${dialogInfo.getGreeting()}<br>
                    </div>
                </div>
            </div>
            <div class="row g-1 py-1">
                <div class="row g-1 py-1">
                    <div class="col rounded bg-secondary">
                        ${dialogInfo.getPhrase()}<br>
                    </div>
                </div>
            </div>
            <div class="row g-1 py-1">
                <c:forEach items="${dialogInfo.getAnswers()}" var="answer">
                    <div class="row g-1 py-1">
                        <div class="col rounded bg-secondary">
                            <div class="form-check mb-2 mt-2">
                                <input class="form-check-input" onclick='nextPhrase("${dialogInfo.getNpcId()}",${answer.getId()},${dialogInfo.getPhraseId()})'
                                       type="radio" name="answer${answer.getId()}"
                                       id="answer${answer.getId()}">
                                <label class="form-check-label"
                                       for="answer${answer.getId()}">${answer.getLabel()}</label>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>
        <div class="col-2"></div>
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
