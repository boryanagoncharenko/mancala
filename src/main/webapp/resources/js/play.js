window.onload = function() {

    var user_id = model["userID"];

    function theFuncToCall(event){
        var str = event.toElement.id.slice(-1);
        var number = parseInt(str);

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
    }

    $(document).on('click', '.own-pit', theFuncToCall);
    //document.getElementById("play-btn").addEventListener("click", function() {
    //    $.ajax({
    //        type: "POST",
    //        url: "http://localhost:8080/games",
    //        success: function (result) {
    //            window.location = "http://localhost:8080/" + result["gameID"];
    //        },
    //        error: function (result) {
    //            alert("eror");
    //        }
    //    });
    //
    //}, false);
};
