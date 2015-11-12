<html>
<head>
</head>
<body>
  <p>This is where the game is displayed</p>
  <div style="display: table">

      <%--generate the labels instead --%>

      <span id="opp-kalah" class="kalah">0</span>

      <span style="display: table-row">
          <span id="opp-pit6" class="opp-pit">6</span>
          <span id="opp-pit5" class="opp-pit">6</span>
          <span id="opp-pit4" class="opp-pit">6</span>
          <span id="opp-pit3" class="opp-pit">6</span>
          <span id="opp-pit2" class="opp-pit">6</span>
          <span id="opp-pit1" class="opp-pit">6</span>
      </span>

      <a style="display: table-row">
          <span id="own-pit1" class="own-pit">6</span>
          <span id="own-pit2" class="own-pit">6</span>
          <span id="own-pit3" class="own-pit">6</span>
          <span id="own-pit4" class="own-pit">6</span>
          <span id="own-pit5" class="own-pit">6</span>
          <span id="own-pit6" class="own-pit">6</span>
      </a>

      <span id="own-kalah" class="kalah">0</span>

  </div>
  <script src="js/mancala-object.js"></script>
  <script>
      mancalaObject.userID = ${userID};
      mancalaObject.gameID = "${gameID}";
  </script>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.min.js"></script>
  <script src="js/play.js"></script>
</body>
</html>
