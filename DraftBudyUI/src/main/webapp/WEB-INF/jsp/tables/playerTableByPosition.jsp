<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="fill-body-wrapper">
	<div class="center"><h3 class="white-title"><strong>${positionName}:</strong></h3></div>
		<table class="table table-sm header-fixed positions outer-scrollbar">
					<thead class="thead-inverse">
			<tr>
				<th class="id-posList">Rank</th>
				<th class="name-posList">Name</th>
				<th class="proj-pts-posList">PrjPts</th>
				<th class="value-posList">Value</th>
				<th class="tags-posList">Tags</th>
				<th class="pos-posList">Pos</th>
				<th class="adp-posList">Adp</th>
				<th class="team-posList">Team</th>
				<th class="bye-posList">Bye</th>
				<th class="id-posList">Best</th>
				<th class="id-posList">Avg</th>
				<th class="id-posList">Worst</th>
				<th class="id-posList">St-Dv</th>
				<th class="id-posList">Vs.</th>
				<th class="handcuff-posList">Backups</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${playerList}" var="player">
				<tr class="tier${player.getTier()}">
					<td class="id-posList">
						<a href="/pickPlayer?playerId=${player.getFantasyProsId()}">
				        	<span class="badge-adp">${player.getRankMetadata().getOverallRank()}</span>
			      		</a>
					</td>
					<%-- <td class="name-posList"><a class="nameLink" data-toggle="modal" data-target="#${player.getFantasyProsId()}playerModal"><strong>${player.getNameAndTags()}</strong></a></td> --%>
					<td class="name-posList">
						<a class="nameLink" data-toggle="modal" data-target="#${player.getFantasyProsId()}playerModal">
							<c:choose>
								<c:when test="${draft.getCurrentRoundHandcuffs().contains(player)}">
									<c:choose>
										<c:when test="${player.getDraftingDetails().isPlayerToTarget()}">
											<span class="handcuff-player-text"><strong>${player.getPlayerName()}</strong></span>
										</c:when>
										<c:otherwise><span class="handcuff-player-text">${player.getPlayerName()}</span></c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${player.getDraftingDetails().isPlayerToTarget()}"><strong>${player.getPlayerName()}</strong></c:when>
										<c:otherwise>${player.getPlayerName()}</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</a>
					</td>
					<td class="proj-pts-posList"><strong>${player.getPositionalStats().getProjectedTotalPoints()}</strong></td>
					
					<td class="value-posList">
						<span class="badge badge-val badge-${player.getDraftingDetails().getCurrentPlayerValueBadgeClass()}"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span>
					</td>
					
					<td class="tags-posList">
						<c:choose>
							<c:when test="${player.getDraftingDetails().getIcons()==null}">&nbsp;</c:when>
							<c:otherwise>
								<c:forEach items="${player.getDraftingDetails().getIcons()}" var="icon">
									<span class="${icon}" aria-hidden="true"></span>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="pos-posList">
						<span class="badge draft-board-badge ${player.getPosition().getBadgeClass()}">${player.getPosition().getAbbrev()} ${player.getRankMetadata().getPositionRank()}</span>
					</td>
					<td class="adp-posList">${player.getRankMetadata().getAdp()}</td>
					<td class="team-posList">${player.getTeam().getAbbreviation()}</td>
					<td class="bye-posList">${player.getBye()}</td>
					<td class="id-posList">${player.getRankMetadata().getBestRank()}</td>
					<td class="id-posList">${player.getRankMetadata().getAvgRank()}</td>
					<td class="id-posList">${player.getRankMetadata().getWorstRank()}</td>
					<td class="id-posList">${player.getRankMetadata().getStdDev()}</td>
					<td class="id-posList">${player.getRankMetadata().getVsAdp()}</td>
					<td class="handcuff-posList">${player.getDraftingDetails().getHandcuffs()}</td>
				</tr>
				
			</c:forEach>
		</tbody>
		</table>
</div>


