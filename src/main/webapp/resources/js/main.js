var env = "http://www.mancala.xyz/";
var stoneColors = ["#1abc9c", "#2ecc71", "#3498db", "#f1c40f", "#e67e22", "#e74c3c", "#9b59b6", "#34495e"];

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
    updateStatus();

    if (!mancalaObject.isInTurn) {
        getGameState();
    }
};

var getGameState = function() {
    $.get(env + "games/" + mancalaObject.gameId,
        function (result) {
            if (isStateChanged(result.board.state)) {
                console.log(result);
                updateGame(result);
            } else {
                setTimeout(getGameState, 1000);
            }
        });
};

var isStateChanged = function(newState) {
    for (i = 0; i < newState.length; i++) {
        if (newState[i] !== mancalaObject.state[i]) {
            return true;
        }
    }
    return false;
};

var updateBoard = function() {
    var state = getUserGameState();
    var n = $(".own-pit").size();

    for (var i = 1; i <= n; i++) {
        updateStones("#own-pit" + i, state[i - 1], ".own-pit");
        updateStones("#opp-pit" + i, state[i + n], ".pit");
    }

    updateStones("#own-kalah", state[n], ".kalah");
    updateStones("#opp-kalah", state[2 * n + 1], ".kalah");
};

var updateStones = function(pitId, newValue, pitClass) {
    var stonesCount = $(pitId);
    stonesCount.text(newValue);

    var pit = $(stonesCount.siblings(pitClass)[0]);
    adjustStones(pit, newValue);
};

var adjustStones = function(pit, number) {
    var currentNumber = pit.children().length;
    var action = addStone;
    if (currentNumber > number) {
        action = removeStone;
    }
    for (var i = 0; i < Math.abs(currentNumber - number); i++) {
        action(pit);
    }
};

var addStone = function(pit) {
    var stone = $("<span>").addClass("stone");
    //var stone = $("<span class=\"stone\"></span>");
    setStonePositionAndColor(pit, stone);
    pit.append(stone);
};

var removeStone = function(pit) {
    pit.children()[0].remove();
};

var setStonePositionAndColor = function(pit, stone) {
    var height = pit.height();
    var width = pit.width();
    var top = height/4 + getRandomNumber(height/3);
    var left = width/4 + getRandomNumber(width/3);
    var color = getRandomColor();
    stone.css({top: top, left: left, background: color});
};

var getRandomColor = function() {
    return stoneColors[getRandomNumber(stoneColors.length) - 1];
};

var getRandomNumber = function(n) {
    return Math.floor((Math.random() * n) + 1);
};

var updateStatus = function() {
    var status = "Opponent's turn";

    if (mancalaObject.isGameOver) {
        status = mancalaObject.isWinner ? "You win" : "You lose";
    }
    else if (mancalaObject.isInTurn === true) {
        status = "Your turn";
    }
    $("#game-status").text(status);
};

var getUserGameState = function() {
    var state = mancalaObject.state.slice();
    if (!mancalaObject.isHost) {
        var offset = 7;
        for (i = 0; i < offset; i++) {
            var buf = state[i];
            state[i] = state[i + offset];
            state[i + offset] = buf;
        }
    }

    return state;
};

var updateMancalaObject = function(game) {
    mancalaObject.isGameOver = game.winner !== null;
    if (mancalaObject.isGameOver) {
        mancalaObject.isWinner = (game.winner === "HOST" && mancalaObject.isHost) ||
        (game.winner === "GUEST" && !mancalaObject.isHost);
    }
    mancalaObject.state = game.board.state;
    mancalaObject.isInTurn = (game.inTurn === "HOST" && mancalaObject.isHost) ||
        (game.inTurn === "GUEST" && !mancalaObject.isHost);
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

$("#leave-game-btn").click(function() {
    window.location = env;
    return false;
});

$(".own-pit").click(function (event){
    var stones = $(this).siblings(".stones-count")[0];
    if (!mancalaObject.isInTurn || stones.innerText == 0) {
        return
    }

    var pit = getPit(stones);
    $.post(env + "games/" + mancalaObject.gameId + "/move/" + pit,
        {userId: mancalaObject.userId},
        function(result){
            updateGame(result);
    });
});


if (typeof mancalaObject !== 'undefined') {
    $.ajax({
        type: "POST",
        url: env + "games/" + mancalaObject.gameId + "/add/" + mancalaObject.userId,
        success: function (result) {
            console.log(result);
            mancalaObject.isHost = mancalaObject.userId == result.host.id;
            updateGame(result);
        },
        error: function (result) {
            $("#error-msg")
                .text("There was a problem with the request. Please try again later.")
                .show();
        }
    });
}

