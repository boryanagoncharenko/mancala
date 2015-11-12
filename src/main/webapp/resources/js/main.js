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
    // visualize game
    updateBoard(game);
    if (game["playerInTurn"] !== mancalaObject["userID"]) {
        // block UI and start pinging for update
    } else {
        // unblock UI
    }
}


if (typeof mancalaObject !== 'undefined') {
    $.ajax({
        type: "POST",
        url: env + "games/" + mancalaObject["gameID"] + "/add/" + mancalaObject["userID"],
        success: function (result) {
            console.log(mancalaObject["userID"] + " was added successfully to the game!");
            console.log(result);
            loadGame(result);



        },
        error: function (result) {
            $("#error-msg")
                .text("There was a problem with the request. Please try again later.")
                .show();
        }
    });
}



// try to join game -> player1, player2, guest
// this endpoint returns the Game object
// set mancalaObject.isInTurn


$(".own-pit").click(function (event){
    //if (!IS_IN_TURN) {
    //    return false;
    //}
    var str = event.toElement.id.slice(-1);
    var number = parseInt(str);

    // if this is the user in turn
    // make a move

    //$.ajax({
    //    type: "POST",
    //    url: "http://localhost:8080/games/game_id/",
    //    success: function (result) {
    //        window.location = "http://localhost:8080/" + result["gameID"];
    //    },
    //    error: function (result) {
    //        alert("eror");
    //    }
    //});

    console.log(number);
});



