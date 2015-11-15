var env = "http://localhost:8080/";


var getPit = function(element) {
    var pit = parseInt(element.id.slice(-1)) - 1;
    if (mancalaObject.hostID !== mancalaObject.userID) {
        pit += 7;
    }
    return pit;
};


function getUserGameState(game) {
    var state = game["state"];
    if (game["host"] !== mancalaObject.userID) {
        var offset = 7;
        for (i = 0; i < offset; i++) {
            var buf = state[i];
            state[i] = state[i + offset];
            state[i + offset] = buf;
        }
    }
    return state;
}

var updateGame = function(game) {
    updateBoard(game);
    updateIsInTurn(game);

    if (mancalaObject.isInTurn) {
        getGameState();
    }
};

var updateBoard = function(game) {
    var state = getUserGameState(game);
    var n = $(".own-pit").size();

    for (i = 1; i <= n; i++) {
        $("#own-pit" + i).text(state[i - 1]);
        $("#opp-pit" + i).text(state[i + n]);
    }
    $("#own-kalah").text(state[n]);
    $("#opp-kalah").text(state[2 * n + 1]);
};

var updateIsInTurn = function(game) {
    mancalaObject.isInTurn = game.playerInTurn === mancalaObject.userID;
};

var getGameState = function() {
    $.get(env + "games/" + mancalaObject.gameID,
        function (result) {
            if (result["playerInTurn"] === mancalaObject.userID) {
                updateGame(result);
            } else {
                setTimeout(getGameState, 1000);
            }
        });
};

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

$(".own-pit").click(function (event){
    if (!mancalaObject.isInTurn) {
        return
    }

    var pit = getPit(event.target);
    $.post(env + "games/" + mancalaObject.gameID + "/move",
        {userID: mancalaObject.userID, pit: pit},
        function(result){
            updateGame(result);
    });
});


if (typeof mancalaObject !== 'undefined') {
    $.ajax({
        type: "POST",
        url: env + "games/" + mancalaObject.gameID + "/add/" + mancalaObject.userID,
        success: function (result) {
            console.log(mancalaObject["userID"] + " was added successfully to the game!");
            console.log(result);
            updateGame(result);
            mancalaObject.hostID = result["host"]
        },
        error: function (result) {
            $("#error-msg")
                .text("There was a problem with the request. Please try again later.")
                .show();
        }
    });
}

