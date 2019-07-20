<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<tr>
	<td class="id-3"><a href="/pickPlayer?playerId=${player.id}">
			<span class="badge-adp-players">${player.adp}</span>
	</a></td>
	<td class="name-3"><a class="nameLink" data-toggle="modal"
		data-target="#${player.id}playerModal"> <c:choose>
				<c:when test="${currentRoundHandcuffs.contains(player)}">
					<c:choose>
						<c:when test="${player.isPlayerToTarget()}">
							<span class="handcuff-player-text"><strong>${player.playerName}</strong></span>
						</c:when>
						<c:otherwise>
							<span class="handcuff-player-text">${player.playerName}</span>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${player.isPlayerToTarget()}">
							<strong>${player.playerName}</strong>
						</c:when>
						<c:otherwise>${player.playerName}</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
	</a></td>
	<td class="pos_rank-3">${player.pos_rank}/${player.rank}</td>
	<td class="team-3">${player.teamName}</td>
	<td class="bye-3">${player.bye}</td>
	<td class="handcuff-3">${player.checkForHandcuff()}</td>
</tr>

