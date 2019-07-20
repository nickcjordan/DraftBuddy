<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section">
	<table class="table table-sm table-striped header-fixed dash">
		<thead class="thead thead-inverse">
			<tr>
				<th class="P-num">Rd.Pk</th>
				<th class="Drafter">Drafter</th>
				<th class="Pos">Pos</th>
				<th class="Player">Player</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${draftPicks}" var="pick">
				<tr>
					<td class="P-num">${pick.round}.<strong>${pick.pick}</strong></td>
					<c:choose>
						<c:when test="${pick.drafter.name.equals('Nick J') || pick.drafter.name.equals(currentDrafter.name)}">
							<td class="Drafter"><b>${pick.drafter.name}</b></td>
						</c:when>
						<c:otherwise>
							<td class="Drafter">${pick.drafter.name}</td>
						</c:otherwise>
					</c:choose>
					
					
					<c:set var="player" value="${pick.player}" scope="page"/>
					<c:choose>
						<c:when test="${player.pos.equals('QB')}">
							<td class="Pos"><span class="badge badge-warning">${player.pos}</span></td>
						</c:when>
						<c:when test="${player.pos.equals('RB')}">
							<td class="Pos"><span class="badge badge-info">${player.pos}</span></td>
						</c:when>
						<c:when test="${player.pos.equals('WR')}">
							<td class="Pos"><span class="badge badge-success">${player.pos}</span></td>
						</c:when>
						<c:when test="${player.pos.equals('TE')}">
							<td class="Pos"><span class="badge badge-error">${player.pos}</span></td>
						</c:when>
						<c:when test="${player.pos.equals('K')}">
							<td class="Pos"><span class="badge">${player.pos}</span></td>
						</c:when>
						<c:otherwise>
							<td class="Pos"><span class="badge badge-inverse">${player.pos}</span></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${pick.drafter.name.equals(currentDrafter.name)}">
					<td class="Player"><a class="nameLink" data-toggle="modal" data-target="#${pick.player.id}playerModal"><b>${pick.player.getPlayerName()}</b></a></td>
						</c:when>
						<c:otherwise>
					<td class="Player"><a class="nameLink" data-toggle="modal" data-target="#${pick.player.id}playerModal">${pick.player.getPlayerName()}</a></td>
						</c:otherwise>
					</c:choose>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>