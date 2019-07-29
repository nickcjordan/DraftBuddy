<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<c:choose>
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() <= -40)}">
			<span class="badge badge-val badge-neg-40"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > -40) && (player.getDraftingDetails().getCurrentPlayerValue() <= -20)}">
			<span class="badge badge-val badge-neg-20"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > -20) && (player.getDraftingDetails().getCurrentPlayerValue() <= -10)}">
			<span class="badge badge-val badge-neg-10"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > -10) && (player.getDraftingDetails().getCurrentPlayerValue() <= -5)}">
			<span class="badge badge-val badge-neg-5"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > -5) && (player.getDraftingDetails().getCurrentPlayerValue() <= -2)}">
			<span class="badge badge-val badge-neg-2"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > -2) && (player.getDraftingDetails().getCurrentPlayerValue() <= 2)}">
			<span class="badge badge-val badge-even"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > 2) && (player.getDraftingDetails().getCurrentPlayerValue() <= 5)}">
			<span class="badge badge-val badge-pos-2"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > 5) && (player.getDraftingDetails().getCurrentPlayerValue() <= 10)}">
			<span class="badge badge-val badge-pos-5"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > 10) && (player.getDraftingDetails().getCurrentPlayerValue() <= 20)}">
			<span class="badge badge-val badge-pos-10"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > 20) && (player.getDraftingDetails().getCurrentPlayerValue() <= 40)}">
			<span class="badge badge-val badge-pos-20"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
		<c:when test="${(player.getDraftingDetails().getCurrentPlayerValue() > 40)}">
			<span class="badge badge-val badge-pos-40"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
		</c:when>
	</c:choose>
