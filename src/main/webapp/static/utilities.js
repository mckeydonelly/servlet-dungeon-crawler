function getStatistic() {
    $("#statistic-table > tbody").html("");

    $.get('/statistic', function (response) {
        $.each(response, function (name, value) {
            let row = $('<tr>').html(
                "<th scope='row'>" + name + "</th>" +
                "<td>" + value + "</td>"
            ).appendTo('#statistic-table');
        });
    });
}

function getInventory() {
    $("#inventory-table > tbody").html("");

    $.get('/inventory', function (response) {
        $.each(response, function (index, item) {
            let row = $('<tr>').html("<td>"
                + "<img src='" + item.imgPath + "' style='max-width:30px; max-height:30px;'>" + "</td><td>"
                + item.name + "</td><td>"
                + item.type + "</td><td>"
                + item.weight + "</td><td>"
                + item.damage + "</td><td>"
                + item.magicDamage + "</td><td>"
                + item.defense + "</td><td>"
                + item.magicDefense + "</td><td>"
                + item.healAmount + "</td>"
            ).appendTo("#inventory-table");
        });
    });
}

function getQuests() {

}

function objectAction(type, id, objectId, cardId) {
    let url;
    if(type === "CRATE") {
        url = '/open'
    } else {
        url = '/take'
    }
    $.get(url, { id: id, objectId: objectId }, function (response) {
        $(cardId).remove();
    });
}

function nextPath(id) {
    $.redirect("/move", {
        nextLocationId: id
    }, "POST");
}

function nextPhrase(npcId, answerId, phraseId) {
    $.redirect("/speak", {
        npcId: npcId,
        answerId: answerId,
        phraseId: phraseId
    }, "GET");
}