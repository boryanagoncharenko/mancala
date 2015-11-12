var env = "http://localhost:8080/";

$("#play-btn").click(function() {
    $.ajax({
        type: "POST",
        url: env + "games",
        success: function (result) {
            window.location = env + result["gameID"];
        },
        error: function (result) {
            $("#error-msg")
                .text("There was a problem with the request. Please try again later.")
                .show();
        }
    });

    return false;
});

function updateBoard(game) {
    var state = getUserGameState(game);
    var n = 6;
    for (i = 1; i <= n; i++) {
        $("#own-pit" + i).text(state[i - 1]);
        $("#opp-pit" + i).text(state[i + n]);
    }
    $("#own-kalah").text(state[n]);
    $("#opp-kalah").text(state[2 * n + 1]);
}

function getUserGameState(game) {
    var state = game["state"];
    if (game["host"] !== mancalaObject["userID"]) {
        var offset = 7;
        for (i = 0; i < offset; i++) {
            var buf = state[i];
            state[i] = state[i + offset];
            state[i + offset] = buf;
        }
    }
    return state;
}

function loadGame(game) {
    updateBoard(game);
    updateIsInTurn(game);

    if (game["playerInTurn"] !== mancalaObject["userID"]) {
        ping();
    } else {
        // unblock UI
    }
}

var interval;
function ping() {
    interval = setInterval(getGameState, 1000);
}

function getGameState() {
    $.get(env + "games/" + mancalaObject["gameID"],
        function (result) {
            if (result["playerInTurn"] === mancalaObject["userID"]) {
                clearInterval(interval);
                loadGame(result);
            }
        });
}

function updateIsInTurn(game) {
    mancalaObject["isInTurn"] = game["playerInTurn"] === mancalaObject["userID"];
}

if (typeof mancalaObject !== 'undefined') {
    $.ajax({
        type: "POST",
        url: env + "games/" + mancalaObject["gameID"] + "/add/" + mancalaObject["userID"],
        success: function (result) {
            console.log(mancalaObject["userID"] + " was added successfully to the game!");
            console.log(result);
            loadGame(result);
            mancalaObject.hostID = result["host"]
        },
        error: function (result) {
            $("#error-msg")
                .text("There was a problem with the request. Please try again later.")
                .show();
        }
    });
}

$(".own-pit").click(function (event){
    //console.log(mancalaObject["isInTurn"]);
    if (!mancalaObject["isInTurn"]) {
        return
    }

    var pit = parseInt(event.target.id.slice(-1)) - 1;
    if (mancalaObject.hostID !== mancalaObject.userID) {
        pit += 7;
    }
    console.log(pit);

    $.post(env + "games/" + mancalaObject["gameID"] + "/move",
        {userID: mancalaObject["userID"], pit: pit},
        function(result){
            console.log(result);
            loadGame(result);
    });
});



