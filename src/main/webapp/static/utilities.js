function getStatistic() {
    $("#statistic-table > tbody").html("");

    $.get('/user/statistic', function (response) {
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

    $.get('/user/inventory', function (response) {
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
    $("#quests-table > tbody").html("");

    $.get('/user/quests', function (response) {
        $.each(response, function (index, quest) {
            let row = $('<tr>').html("<td>"
                + quest.issuedBy + "</td><td>"
                + quest.status + "</td><td>"
                + quest.questType + "</td><td>"
                + quest.targetName + "</td><td>"
                + quest.info + "</td>"
            ).appendTo("#quests-table");
        });
    });
}

function objectAction(type, id, objectId, cardId) {
    let url;
    if(type === "CRATE") {
        url = '/crate/open'
    } else {
        url = '/item/take'
    }
    $.get(url, { id: id, objectId: objectId }, function (response) {
        $(cardId).remove();
    });
}

function nextPath(id) {
    $.redirect("/map/move", {
        nextLocationId: id
    }, "POST");
}

function nextPhrase(npcId, answerId, phraseId) {
    $.redirect("/user/speak", {
        npcId: npcId,
        answerId: answerId,
        phraseId: phraseId
    }, "GET");
}