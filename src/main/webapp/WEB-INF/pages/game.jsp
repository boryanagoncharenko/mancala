<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:generic-page>
    <p>This is where the game is displayed</p>

    <table border="1" cellpadding="1" cellspacing="1" style="width: 500px">
        <tbody>
        <tr>
            <td rowspan="2">
                &nbsp;</td>
            <td>
                &nbsp;</td>
            <td>
                &nbsp;</td>
            <td>
                &nbsp;</td>
            <td>
                &nbsp;</td>
            <td>
                &nbsp;</td>
            <td>
                &nbsp;</td>
            <td rowspan="2">
                &nbsp;</td>
        </tr>
        <tr>
            <td id="own-pit1" class="own-pit">
                &nbsp;</td>
            <td id="own-pit2" class="own-pit">
                &nbsp;</td>
            <td id="own-pit3" class="own-pit">
                &nbsp;</td>
            <td id="own-pit4" class="own-pit">
                &nbsp;</td>
            <td id="own-pit5" class="own-pit">
                &nbsp;</td>
            <td id="own-pit6" class="own-pit">
                &nbsp;</td>
        </tr>
        </tbody>
    </table>

    <%----%>
    <%--<table>--%>
        <%--<%for(int i=0;i<=count;i++){%>--%>
        <%--<tr>--%>
            <%--<td>Phone Numbers</td>--%>
            <%--<td><%= address.getPhonenumber()%></td>--%>
        <%--</tr>--%>
        <%--<%}%>--%>
    <%--</table>--%>

    <div style="display: table">



            <%--generate the labels instead --%>

        <%--<span id="opp-kalah" class="kalah">0</span>--%>

      <%--<span style="display: table-row">--%>
          <%--<span id="opp-pit6" class="opp-pit">6</span>--%>
          <%--<span id="opp-pit5" class="opp-pit">6</span>--%>
          <%--<span id="opp-pit4" class="opp-pit">6</span>--%>
          <%--<span id="opp-pit3" class="opp-pit">6</span>--%>
          <%--<span id="opp-pit2" class="opp-pit">6</span>--%>
          <%--<span id="opp-pit1" class="opp-pit">6</span>--%>
      <%--</span>--%>

        <%--<a style="display: table-row">--%>
            <%--<span id="own-pit1" class="own-pit">6</span>--%>
            <%--<span id="own-pit2" class="own-pit">6</span>--%>
            <%--<span id="own-pit3" class="own-pit">6</span>--%>
            <%--<span id="own-pit4" class="own-pit">6</span>--%>
            <%--<span id="own-pit5" class="own-pit">6</span>--%>
            <%--<span id="own-pit6" class="own-pit">6</span>--%>
        <%--</a>--%>

        <%--<span id="own-kalah" class="kalah">0</span>--%>

    </div>
    <script src="js/mancala-object.js"></script>
    <script>
        mancalaObject.userID = "${userID}";
        mancalaObject.gameID = "${gameID}";
    </script>
</t:generic-page>
