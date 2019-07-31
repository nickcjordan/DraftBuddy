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
			<c:forEach items="${draft.getDraftPicks()}" var="pick">
				<tr>
					<td class="P-num">${pick.round}.<strong>${pick.pick}</strong></td>
					<c:choose>
						<c:when test="${pick.drafter.getName().equals('Nick J') || pick.drafter.getName().equals(currentDrafter.getName())}">
							<td class="Drafter"><b>${pick.drafter.getName()}</b></td>
						</c:when>
						<c:otherwise>
							<td class="Drafter">${pick.drafter.getName()}</td>
						</c:otherwise>
					</c:choose>
					
					
					<c:set var="player" value="${pick.player}" scope="page"/>
					<c:choose>
						<c:when test="${player.getPosition().getAbbrev().equals('QB')}">
							<td class="Pos"><span class="badge badge-warning">${player.getPosition().getAbbrev()}</span></td>
						</c:when>
						<c:when test="${player.getPosition().getAbbrev().equals('RB')}">
							<td class="Pos"><span class="badge badge-info">${player.getPosition().getAbbrev()}</span></td>
						</c:when>
						<c:when test="${player.getPosition().getAbbrev().equals('WR')}">
							<td class="Pos"><span class="badge badge-success">${player.getPosition().getAbbrev()}</span></td>
						</c:when>
						<c:when test="${player.getPosition().getAbbrev().equals('TE')}">
							<td class="Pos"><span class="badge badge-error">${player.getPosition().getAbbrev()}</span></td>
						</c:when>
						<c:when test="${player.getPosition().getAbbrev().equals('K')}">
							<td class="Pos"><span class="badge">${player.getPosition().getAbbrev()}</span></td>
						</c:when>
						<c:otherwise>
							<td class="Pos"><span class="badge badge-inverse">${player.getPosition().getAbbrev()}</span></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${pick.drafter.getName().equals(currentDrafter.getName())}">
					<td class="Player"><a class="nameLink" data-toggle="modal" data-target="#${pick.player.getFantasyProsId()}playerModal"><b>${pick.player.getPlayerName()}</b></a></td>
						</c:when>
						<c:otherwise>
					<td class="Player"><a class="nameLink" data-toggle="modal" data-target="#${pick.player.getFantasyProsId()}playerModal">${pick.player.getPlayerName()}</a></td>
						</c:otherwise>
					</c:choose>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>