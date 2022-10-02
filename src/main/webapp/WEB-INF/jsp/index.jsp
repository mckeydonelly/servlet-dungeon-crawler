<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../static/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Start</title>
</head>
<body>
<div class="container shadow rounded min-vh-100 py-2 bg-dark text-white bg-image" style="
    background-image: url('../../img/indexfont.png');
    height: 100%;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;">
    <div class="row py-1">
        <jsp:include page="part/statistic.jsp"/>
        <div class="col-6 text-white bg-secondary rounded">
            <p class="h3">Prologue</p>
            <p class="text">${questProlog}</p>
        </div>
        <div class="col-3"></div>
    </div>
    <div class="row py-1">
        <div class="col-3"></div>
        <div class="col-6 text-white bg-secondary rounded">
            <form action="${request.contextPath}/start" class="row g-3" method="post">
                <div class="mt-3">
                    <label for="nickname-input" class="form-label">Enter your name:</label>
                    <input type="text" class="form-control" id="nickname-input" placeholder="Name" name="user_name"
                           value="${userName}" required>
                    <div class="invalid-feedback">Please enter name</div>
                </div>
                <div class="col-12 mb-2">
                    <button class="btn btn-dark" id="start-button" type="submit">Start</button>
                </div>
            </form>
        </div>
        <div class="col-3"></div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/utilities.js"></script>
</body>
</html>
