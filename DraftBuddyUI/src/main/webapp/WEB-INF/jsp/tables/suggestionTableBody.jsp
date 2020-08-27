<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<tbody class="scaled-body">
			<c:set var="pickIndexTempVar" value="${0}" scope="application" />
			<c:set var="grayLineCount" value="${0}" scope="application" />
			<c:forEach items="${playerListContent}" var="player" varStatus="status">
				<!-- set next pick number divider bar -->
				<c:set var="pickNumVar" value="${draft.getCurrentDrafter().getDraftedTeam().getDraftedPlayersInOrder().size() + grayLineCount + 2}" scope="application" />
				<c:choose>
					<c:when test="${(pickIndexTempVar != 0) && (draft.getCurrentDrafter().hasDraftPickIndex(draft.getPickNumber() + pickIndexTempVar))}">
						<tr class="center table-divider"><td><b>Pick: ${pickNumVar}</b></td><td><b>Pick: ${pickNumVar}</b></td><td><b>Pick: ${pickNumVar}</b></td><td><b>Pick: ${pickNumVar}</b></td></tr>
						<c:set var="grayLineCount" value="${grayLineCount + 1}" scope="application" />
					</c:when>
				</c:choose>
				<c:set var="pickIndexTempVar" value="${pickIndexTempVar + 1}" scope="application" />

				<tr class="tier${player.getTier()}">
					<td class="id-suggest"><a href="/pickPlayer?playerId=${player.getFantasyProsId()}"> <span class="badge-adp">${player.getRankMetadata().getAdp()}</span>
					<td class="pos_rank-suggest"><span class="badge-ecr">${player.getRankMetadata().getOverallRank()}</span></td>
					</a></td>
					<td class="name-suggest">
						<a class="nameLink" data-toggle="modal" data-target="#${player.getFantasyProsId()}playerModal"> 
							<c:choose>
								<c:when test="${draft.getCurrentRoundHandcuffs().contains(player)}">
									<c:choose>
										<c:when test="${player.getDraftingDetails().isPlayerToTarget()}"><span class="handcuff-player-text"><strong>${player.getPlayerName()}</strong></span></c:when>
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
					<td class="proj-avg-pts-suggest">${player.getPositionalStats().getProjectedAveragePointsPerGame()}</td>
					<td class="value-suggest"><span class="badge badge-val badge-${player.getDraftingDetails().getCurrentPlayerValueBadgeClass()}"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span></td>
					<td class="vsadp-suggest"><span class="badge badge-val badge-${player.getDraftingDetails().getVsValueBadgeClass()}"><strong>${player.getRankMetadata().getVsAdp()}</strong></span></td>
					<td class="proj-pts-suggest"><strong>${player.getPositionalStats().getPriorTotalPoints()}</strong></td>
					<td class="proj-pts-suggest"><strong>${player.getPositionalStats().getPriorAveragePointsPerGame()}</strong></td>
					<td class="proj-pts-suggest"><strong>${player.getPositionalStats().getPriorAverageTargetsPerGame()}</strong></td>


					<td class="tags-suggest">
						<c:choose>
							<c:when test="${player.getDraftingDetails().getIcons()==null}">&nbsp;</c:when>
							<c:otherwise>
								<c:forEach items="${player.getDraftingDetails().getIcons()}" var="icon">
									<span class="${icon}" aria-hidden="true"></span>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</td>

					<td class="pos-suggest"><span class="badge position-badge ${player.getPosition().getBadgeClass()}">${player.getPosition().getAbbrev()}${player.getRankMetadata().getPositionRank()}</span></td>


					<td class="team-suggest">${player.getTeam().getAbbreviation()}</td>
					<td class="team-suggest">${player.getTeam().getoLineRank()}</td>
					<td class="bye-suggest">${player.getBye()}</td>
					<td class="stddev-suggest">${player.getRankMetadata().getStdDev()}</td>
					
					<td class="handcuff-suggest">
						<c:forEach items="${player.getDraftingDetails().getBackups()}" var="backup" varStatus="status" >
							<a class="nameLink smaller-text" data-toggle="modal" data-target="#${backup.getFantasyProsId()}playerModal">${backup.getPlayerName()}<c:if test="${!status.last}">, </c:if></a>
						</c:forEach>
					</td>
					
				</tr>

			</c:forEach>
		</tbody>
