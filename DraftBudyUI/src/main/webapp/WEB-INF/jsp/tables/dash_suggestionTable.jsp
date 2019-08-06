<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section">
	<table class="table table-sm header-fixed dash tabbed-table thin-celled-table outer-scrollbar">
		<thead class="thead-inverse">
			<tr>
				<th class="id-suggest">Adp</th>
				<th class="name-suggest">Name</th>
				<th class="proj-pts-suggest">PrjPts</th>
				<th class="value-suggest"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="Current number of picks player differs from their original <b>ADP</b>">Value</span> <script>$('span').tooltip();</script></th>
				<th class="vsadp-suggest"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="How far away the player's <b>ADP</b> is from the player's <b>Overall Rank</b>">VsAdp</span> <script>$('span').tooltip();</script></th>
				<th class="tags-suggest">Tags</th>
				<th class="pos-suggest">Pos</th>
				<th class="pos_rank-suggest">Rnk</th>
				<th class="team-suggest">Team</th>
				<th class="bye-suggest">Bye</th>
				<th class="stddev-suggest"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="How <b>unsure</b> people are of this player. This measures how much a set of values drifts from the <b>Average</b>, or <em>'a measure confidence'</em> in statistical conclusions">St-Dv</span><script>$('span').tooltip();</script></th>
				<th class="handcuff-suggest">Backups</th>
			</tr>
		</thead>

		<tbody class="scaled-body">
			<c:set var="pickIndexTempVar" value="${0}" scope="application" />
			<c:forEach items="${playerListContent}" var="player" varStatus="status">
				<!-- set next pick number divider bar -->
				<c:choose>
					<c:when test="${draft.getCurrentDrafter().hasDraftPickIndex(draft.getPickNumber() + pickIndexTempVar)}">
						<tr class="center table-divider"><td><b>Your Next Pick</b></td><td><b>Your Next Pick</b></td><td><b>Your Next Pick</b></td><td><b>Your Next Pick</b></td></tr>
					</c:when>
				</c:choose>
				<c:set var="pickIndexTempVar" value="${pickIndexTempVar + 1}" scope="application" />

				<tr class="tier${player.getTier()}">
					<td class="id-suggest"><a href="/pickPlayer?playerId=${player.getFantasyProsId()}"> <span class="badge-adp">${player.getRankMetadata().getAdp()}</span>
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
					<td class="proj-pts-suggest"><strong>${player.getPositionalStats().getProjectedTotalPoints()}</strong></td>

					<td class="value-suggest"><span class="badge badge-val badge-${player.getDraftingDetails().getCurrentPlayerValueBadgeClass()}"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span></td>
					
					<td class="vsadp-suggest"><span class="badge badge-val badge-${player.getDraftingDetails().getVsValueBadgeClass()}"><strong>${player.getRankMetadata().getVsAdp()}</strong></span></td>

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

					<td class="pos-suggest"><span class="badge draft-board-badge ${player.getPosition().getBadgeClass()}">${player.getPosition().getAbbrev()}${player.getRankMetadata().getPositionRank()}</span></td>

					<td class="pos_rank-suggest"><span class="badge-adp">${player.getRankMetadata().getOverallRank()}</span></td>

					<td class="team-suggest">${player.getTeam().getAbbreviation()}</td>
					<td class="bye-suggest">${player.getBye()}</td>
					<td class="stddev-suggest">${player.getRankMetadata().getStdDev()}</td>
					
					<td class="handcuff-suggest">
						<c:forEach items="${player.getDraftingDetails().getBackups()}" var="backup" varStatus="status" >
							<a class="nameLink" data-toggle="modal" data-target="#${backup.getFantasyProsId()}playerModal">${backup.getPlayerName()}<c:if test="${!status.last}">, </c:if></a>
						</c:forEach>
					</td>
					
				</tr>

			</c:forEach>
		</tbody>
	</table>
</div>


