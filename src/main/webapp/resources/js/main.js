window.onload = function() {


    document.getElementById("play-btn").addEventListener("click", function() {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/games",
            success: function (result) {
                window.location = "http://localhost:8080/" + result["gameID"];
            },
            error: function (result) {
                alert("eror");
            }
        });

    }, false);
};

