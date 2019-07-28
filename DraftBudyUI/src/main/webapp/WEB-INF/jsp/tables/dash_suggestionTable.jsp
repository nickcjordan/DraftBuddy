<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section">
	<table class="table table-sm header-fixed dash tabbed-table thin-celled-table outer-scrollbar">
		<thead class="thead-inverse">
			<tr>
				<th class="id-suggest">Adp</th>
				<th class="name-suggest">Name</th>
				<th class="proj-pts-suggest">PrjPts</th>
				<th class="value-suggest">Value</th>
				<th class="tags-suggest">Tags</th>
				<th class="pos-suggest">Pos</th>
				<th class="pos_rank-suggest">Rnk</th>
				<th class="team-suggest">Team</th>
				<th class="bye-suggest">Bye</th>
				<th class="stddev-suggest">
					<span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title=
					"How <b>unsure</b> people are of this player. This measures how much a set of values drifts from the <b>Average</b>, or <em>'a measure confidence'</em> in statistical conclusions">
													St-Dv</span><script> $('span').tooltip();</script></th>
				<th class="vsadp-suggest">
					<span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title=
					"How far away the player's <b>ADP</b> is from the player's <b>Overall Rank</b>">
													Vs.</span><script> $('span').tooltip();</script></th>
				<th class="handcuff-suggest">Backups</th>
			</tr>
		</thead>
		
		<tbody class="scaled-body">
			<c:set var="pickIndex" value="${0}" scope="application"/>
			<c:forEach items="${playerListContent}" var="player" varStatus="status">
				<!-- set next pick number divider bar -->
				<c:choose>
					<c:when test="${draftersPickNumberList.size() > pickIndex && draftersPickNumberList.get(pickIndex) == (status.index + pickNumber)}">
						<c:set var="pickIndex" value="${pickIndex + 1}" scope="application"/>
						<tr class="center table-divider"><td><b>Your Next Pick</b></td><td><b>Your Next Pick</b></td><td><b>Your Next Pick</b></td><td><b>Your Next Pick</b></td></tr>
					</c:when>
				</c:choose>
				<tr class="tier${player.getTier()}">
					<td class="id-suggest">
						<a href="/pickPlayer?playerId=${player.getFantasyProsId()}">
				        	<span class="badge-adp">${player.getRankMetadata().getAdp()}</span>
			      		</a>
					</td>
					<td class="name-suggest">
						<a class="nameLink" data-toggle="modal" data-target="#${player.getFantasyProsId()}playerModal">
							<c:choose>
								<c:when test="${currentRoundHandcuffs.contains(player)}">
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
					<td class="proj-pts-suggest"><strong>${player.projectedPts}</strong></td>
					
					<td class="value-suggest">
						<%@include file="../common/valueBadge.jsp"%>
					</td>

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
					
					<td class="pos-suggest">
						<c:choose>
							<c:when test="${player.getPosition().getAbbrev().equals('QB')}"><span class="badge badge-warning">${player.getPosition().getAbbrev()} ${player.getRankMetadata().getPositionRank()}</span></c:when>
							<c:when test="${player.getPosition().getAbbrev().equals('RB')}"><span class="badge badge-info">${player.getPosition().getAbbrev()} ${player.getRankMetadata().getPositionRank()}</span></c:when>
							<c:when test="${player.getPosition().getAbbrev().equals('WR')}"><span class="badge badge-success">${player.getPosition().getAbbrev()} ${player.getRankMetadata().getPositionRank()}</span></c:when>
							<c:when test="${player.getPosition().getAbbrev().equals('TE')}"><span class="badge badge-error">${player.getPosition().getAbbrev()} ${player.getRankMetadata().getPositionRank()}</span></c:when>
							<c:when test="${player.getPosition().getAbbrev().equals('K')}"><span class="badge">${player.getPosition().getAbbrev()} ${player.getRankMetadata().getPositionRank()}</span></c:when>
							<c:otherwise><span class="badge badge-inverse">${player.getPosition().getAbbrev()} ${player.getRankMetadata().getPositionRank()}</span></c:otherwise>
						</c:choose>
					</td>
					
					<td class="pos_rank-suggest"><span class="badge-adp">${player.getRankMetadata().getOverallRank()}</span></td>
					
					<td class="team-suggest">${player.getTeam().getAbbreviation()}</td>
					<td class="bye-suggest">${player.getBye()}</td>
					<td class="stddev-suggest">${player.getRankMetadata().getStdDev()}</td>
					<td class="vsadp-suggest">${player.getRankMetadata().getVsAdp()}</td>
					<td class="handcuff-suggest">${player.getDraftingDetails().getHandcuffs()}</td>
				</tr>
				
			</c:forEach>
		</tbody>
	</table>
</div>


