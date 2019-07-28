<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<tr>
	<td class="id-3"><a href="/pickPlayer?playerId=${player.getFantasyProsId()}">
			<span class="badge-adp-players">${player.getRankMetadata().getAdp()}</span>
	</a></td>
	<td class="name-3"><a class="nameLink" data-toggle="modal"
		data-target="#${player.getFantasyProsId()}playerModal"> <c:choose>
				<c:when test="${currentRoundHandcuffs.contains(player)}">
					<c:choose>
						<c:when test="${player.isPlayerToTarget()}">
							<span class="handcuff-player-text"><strong>${player.getPlayerName()}</strong></span>
						</c:when>
						<c:otherwise>
							<span class="handcuff-player-text">${player.getPlayerName()}</span>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${player.isPlayerToTarget()}">
							<strong>${player.getPlayerName()}</strong>
						</c:when>
						<c:otherwise>${player.getPlayerName()}</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
	</a></td>
	<td class="pos_rank-3">${player.getRankMetadata().getPositionRank()}/${player.getRankMetadata().getOverallRank()}</td>
	<td class="team-3">${player.getTeam().getAbbreviation()}</td>
	<td class="bye-3">${player.getBye()}</td>
	<td class="handcuff-3">${player..getDraftingDetails().getHandcuffs()}</td>
</tr>

