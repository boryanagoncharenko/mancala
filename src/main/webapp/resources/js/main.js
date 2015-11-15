var env = "http://localhost:8080/";


var getPit = function(element) {
    var pit = parseInt(element.id.slice(-1)) - 1;
    if (!mancalaObject.isHost) {
        pit += 7;
    }
    return pit;
};

var updateGame = function(game) {
    updateMancalaObject(game);
    updateBoard();

    if (!mancalaObject.isInTurn) {
        getGameState();
    }
};

var getGameState = function() {
    $.get(env + "games/" + mancalaObject.gameID,
        function (result) {
            if (isStateChanged(result.state)) {
                updateGame(result);
            } else {
                setTimeout(getGameState, 1000);
            }
        });
};

var isStateChanged = function(newState) {
    return newState !== mancalaObject.state;
};

var updateBoard = function() {
    var state = getUserGameState();
    var n = $(".own-pit").size();

    for (i = 1; i <= n; i++) {
        $("#own-pit" + i).text(state[i - 1]);
        $("#opp-pit" + i).text(state[i + n]);
    }
    $("#own-kalah").text(state[n]);
    $("#opp-kalah").text(state[2 * n + 1]);
};

function getUserGameState() {
    var state = mancalaObject.state;
    if (!mancalaObject.isHost) {
        var offset = 7;
        for (i = 0; i < offset; i++) {
            var buf = state[i];
            state[i] = state[i + offset];
            state[i + offset] = buf;
        }
    }

    return state;
}

var updateMancalaObject = function(game) {
    mancalaObject.state = game.state;
    mancalaObject.isInTurn = game.playerInTurn === mancalaObject.userID;
};

$("#play-btn").click(function() {
    $.ajax({
        type: "POST",
        url: env + "games",
        success: function (result) {
            window.location = env + result.id;
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
            console.log(mancalaObject.userID + " was added successfully to the game!");
            console.log(result);
            updateGame(result);
            mancalaObject.isHost = mancalaObject.userID == result.host;
        },
        error: function (result) {
            $("#error-msg")
                .text("There was a problem with the request. Please try again later.")
                .show();
        }
    });
}

