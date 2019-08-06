<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="panel panel-default strategy-panel">
		<!-- List group -->
		<ul class="list-group strategy-list-box">
			<li class="list-group-item strategy-list-header"><b class="green-title">Current Round Strategy:</b> ${strategy.getStrategyText()}</li>
			<li class="list-group-item strategy-list-header"><b class="green-title">Target Positions:</b> ${strategy.getTargetPositions()}</li>
			<li class="list-group-item strategy-list-header"><b class="green-title">Target Players:</b> 
			<c:forEach items="${strategy.getTargetPlayers()}" var="targetPlayer">
		     - ${targetPlayer}
	    	</c:forEach></li>
		</ul>
</div>
