<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="panel panel-default strategy-panel">
  <div class="panel-body strategy-body"><b>Current Round Strategy</b>: ${strategy.getStrategyText()}</div>

  <!-- List group -->
  <ul class="list-group">
   	<%-- <li class="list-group-item strategy-list-header"><b>Target Positions:</b> ${strategy.getTargetPositions()}</li> --%>
   	<li class="list-group-item strategy-list-header"><b>Target Players:</b>
	    <c:forEach items="${strategy.getTargetPlayers()}" var="targetPlayer">
		     - ${targetPlayer}
	    </c:forEach>
    </li>
  </ul>
</div>
