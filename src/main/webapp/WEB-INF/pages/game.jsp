<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>

<div class="board-container">
<div id="game-status" class="lead">Opponent's turn</div>
    <table class="board">
        <tbody>
        <tr>
            <td rowspan="2">
                <div class="opp-kalah-container">
                    <span id="opp-kalah" class="stones-count">0</span>
                    <span class="kalah"></span>
                </div>
            </td>
            <c:forEach begin="1" end="6" varStatus="loop">
                <td>
                    <div>
                        <span id="opp-pit${7 - loop.index}" class="stones-count">6</span>
                        <span class="pit"></span>
                    </div>
                </td>
            </c:forEach>
            <td rowspan="2">
                <div class="own-kalah-container">
                    <span class="kalah"></span>
                    <span id="own-kalah" class="stones-count">0</span>
                </div>
            </td>
        </tr>
        <tr>
            <c:forEach begin="1" end="6" varStatus="loop">
                <td>
                    <div>
                        <span class="own-pit"></span>
                        <span id="own-pit${loop.index}" class="stones-count">6</span>
                    </div>
                </td>
            </c:forEach>
        </tr>
        </tbody>
    </table>
</div>

<script src="js/mancala-object.js"></script>
<script>
    mancalaObject.userID = "${userID}";
    mancalaObject.gameID = "${gameID}";
</script>

</t:generic-page>
