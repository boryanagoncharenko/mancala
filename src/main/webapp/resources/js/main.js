
$("#play-btn").click(function() {
    var env = "http://localhost:8080/";
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


