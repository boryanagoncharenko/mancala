<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
    <p>This is where the game is displayed</p>

    <table border="1" cellpadding="1" cellspacing="1" style="width: 500px">
        <tbody>
        <tr>
            <td rowspan="2" id="opp-kalah" class="pit"></td>
            <td id="opp-pit6" class="pit"></td>
            <td id="opp-pit5" class="pit"></td>
            <td id="opp-pit4" class="pit"></td>
            <td id="opp-pit3" class="pit"></td>
            <td id="opp-pit2" class="pit"></td>
            <td id="opp-pit1" class="pit"></td>
            <td rowspan="2" id="own-kalah" class="pit"></td>
        </tr>
        <tr>
            <td id="own-pit1" class="own-pit">ha</td>
            <td id="own-pit2" class="own-pit">ha</td>
            <td id="own-pit3" class="own-pit">ha</td>
            <td id="own-pit4" class="own-pit">ha</td>
            <td id="own-pit5" class="own-pit">ha</td>
            <td id="own-pit6" class="own-pit">ha</td>
        </tr>
        </tbody>
    </table>


    <div style="display: table">

    </div>
    <script src="js/mancala-object.js"></script>
    <script>
        mancalaObject.userID = "${userID}";
        mancalaObject.gameID = "${gameID}";
    </script>
</t:generic-page>
