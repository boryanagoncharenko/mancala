<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:generic-page>
	<div class="play-container">
		<h1 class="text-center">Mancala</h1>
		<p class="lead text-justify">
			Mancala is an ancient family of board games. This is a two-row version with 6 pits in each row and 6 stones in each pit. The objective of the game is to capture more stones than your opponent. Capturing is allowed only on opponents stones. The game ends when either side has no stones left.</p>
		<button id="play-btn" class="btn btn-primary btn-lg">Play</button>
		<div class="text-center" id="error-msg"></div>
	</div>
</t:generic-page>