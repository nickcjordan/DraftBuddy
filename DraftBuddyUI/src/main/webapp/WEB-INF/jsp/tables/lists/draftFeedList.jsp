<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="">
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
					<td class="P-num">${pick.round}.<strong>${pick.getRoundIndex()}</strong></td>
					<td class="Drafter"><b class="green-title">${pick.drafter.getName()}</b></td>
					<td class="Pos"><span class="badge position-badge ${pick.player.getPosition().getBadgeClass()}">${pick.player.getPosition().getAbbrev()}</span></td>
					<td class="Player"><a class="nameLinkWhite" data-toggle="modal" data-target="#${pick.player.getFantasyProsId()}playerModal">${pick.player.getPlayerName()}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>