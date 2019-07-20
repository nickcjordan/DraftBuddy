<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:choose>
		<c:when test="${(player.currentPlayerValue <= -40)}">
			<span class="badge badge-val badge-neg-40"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		<c:when test="${(player.currentPlayerValue > -40) && (player.currentPlayerValue <= -20)}">
			<span class="badge badge-val badge-neg-20"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		<c:when test="${(player.currentPlayerValue > -20) && (player.currentPlayerValue <= -10)}">
			<span class="badge badge-val badge-neg-10"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		<c:when test="${(player.currentPlayerValue > -10) && (player.currentPlayerValue <= -5)}">
			<span class="badge badge-val badge-neg-5"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		<c:when test="${(player.currentPlayerValue > -5) && (player.currentPlayerValue <= -2)}">
			<span class="badge badge-val badge-neg-2"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		
		<c:when test="${(player.currentPlayerValue > -2) && (player.currentPlayerValue <= 2)}">
			<span class="badge badge-val badge-even"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		
		<c:when test="${(player.currentPlayerValue > 2) && (player.currentPlayerValue <= 5)}">
			<span class="badge badge-val badge-pos-2"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		<c:when test="${(player.currentPlayerValue > 5) && (player.currentPlayerValue <= 10)}">
			<span class="badge badge-val badge-pos-5"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		<c:when test="${(player.currentPlayerValue > 10) && (player.currentPlayerValue <= 20)}">
			<span class="badge badge-val badge-pos-10"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		<c:when test="${(player.currentPlayerValue > 20) && (player.currentPlayerValue <= 40)}">
			<span class="badge badge-val badge-pos-20"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
		<c:when test="${(player.currentPlayerValue > 40)}">
			<span class="badge badge-val badge-pos-40"><strong>${player.currentPlayerValue}</strong></span>
		</c:when>
	</c:choose>
