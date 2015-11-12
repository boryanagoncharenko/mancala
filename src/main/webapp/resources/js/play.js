
var user_id = userID["userID"];
console.log(gameID);



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


