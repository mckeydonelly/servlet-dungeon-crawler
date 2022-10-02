<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../static/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Figth</title>
</head>
<body>
<div class="container shadow rounded min-vh-100 py-2 bg-dark text-white">
    <div class="row py-1">
        <div class="col">
            <h4 class="text-center">Fighting</h4>
        </div>
    </div>
    <div class="row py-1">
        <div class="col-5 text-white bg-secondary rounded-end">
            <div class="row">
                <div class="col">
                    <h4 class="text-center">{Character name}</h4>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="progress" style="height: 30px;">
                        <div class="progress-bar bg-danger" role="progressbar" style="width: 67%" aria-valuenow="67" aria-valuemin="0" aria-valuemax="100"> 90/153 HP </div>
                    </div>
                </div>
            </div>
            <div class="row py-1">
                <div class="col border-end">
                    <div class="row">
                        <div class="col">
                            <p class="mb-0">Strength: {count}</p>
                            <p class="mb-0">Defense: {count}</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <p class="mb-1">Potions:</p>
                            <div class="d-inline">
                                <img style="max-width:30px; max-height:5px;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaTzxE_io2QRZUUmLYPBGc9mNkxtf1HOFiQIgAb-svBkfkTtlXq383ejFfrrap3iE3204&usqp=CAU" alt="Card image">
                            </div>
                            <div class="d-inline">
                                <img style="max-width:30px; max-height:5px;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaTzxE_io2QRZUUmLYPBGc9mNkxtf1HOFiQIgAb-svBkfkTtlXq383ejFfrrap3iE3204&usqp=CAU" alt="Card image">
                            </div>
                            <div class="d-inline">
                                <img style="max-width:30px; max-height:5px;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaTzxE_io2QRZUUmLYPBGc9mNkxtf1HOFiQIgAb-svBkfkTtlXq383ejFfrrap3iE3204&usqp=CAU" alt="Card image">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <p class="mb-0">Attack:</p>
                    <div class="col rounded bg-secondary">
                        <div class="form-check mb-2 mt-2">
                            <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1">
                            <label class="form-check-label" for="flexRadioDefault1"> Head </label>
                        </div>
                        <div class="form-check mb-2 mt-2">
                            <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked>
                            <label class="form-check-label" for="flexRadioDefault2"> Body </label>
                        </div>
                        <div class="form-check mb-2 mt-2">
                            <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked>
                            <label class="form-check-label" for="flexRadioDefault2"> Legs </label>
                        </div>
                    </div>
                </div>
                <div class="col border-start">
                    <p class="mb-0">Defense:</p>
                    <div class="col rounded bg-secondary">
                        <div class="form-check mb-2 mt-2">
                            <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1">
                            <label class="form-check-label" for="flexRadioDefault1"> Head </label>
                        </div>
                        <div class="form-check mb-2 mt-2">
                            <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked>
                            <label class="form-check-label" for="flexRadioDefault2"> Body </label>
                        </div>
                        <div class="form-check mb-2 mt-2">
                            <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked>
                            <label class="form-check-label" for="flexRadioDefault2"> Legs </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-2"></div>
        <div class="col-5 text-white bg-secondary rounded-start">
            <div class="row">
                <div class="col">
                    <h4 class="text-center">{Unit name}</h4>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="progress" style="height: 30px;">
                        <div class="progress-bar bg-danger" role="progressbar" style="width: 67%" aria-valuenow="67" aria-valuemin="0" aria-valuemax="100"> 90/153 HP </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col border-end">
                    <img style="width:200px; height:200px;" src="https://cache.desktopnexus.com/thumbseg/2538/2538564-bigthumbnail.jpg" alt="Npc image">
                </div>
                <div class="col">
                    <p class="mb-0">Strength: {count}</p>
                    <p class="mb-0">Defense: {count}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row py-1">
        <div class="col text-center">
            <button type="button" class="btn btn-lg text-white btn-secondary btn-block">Attack</button>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
