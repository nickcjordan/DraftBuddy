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
				<tr class="tier${player.tier}">
					<td class="id-suggest">
						<a href="/pickPlayer?playerId=${player.id}">
				        	<span class="badge-adp">${player.adp}</span>
			      		</a>
					</td>
					<td class="name-suggest">
						<a class="nameLink" data-toggle="modal" data-target="#${player.id}playerModal">
							<c:choose>
								<c:when test="${currentRoundHandcuffs.contains(player)}">
									<c:choose>
										<c:when test="${player.isPlayerToTarget()}">
											<span class="handcuff-player-text"><strong>${player.playerName}</strong></span>
										</c:when>
										<c:otherwise><span class="handcuff-player-text">${player.playerName}</span></c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${player.isPlayerToTarget()}"><strong>${player.playerName}</strong></c:when>
										<c:otherwise>${player.playerName}</c:otherwise>
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
							<c:when test="${player.icons==null}">&nbsp;</c:when>
							<c:otherwise>
								<c:forEach items="${player.icons}" var="icon">
									<span class="${icon}" aria-hidden="true"></span>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</td>
					
					<td class="pos-suggest">
						<c:choose>
							<c:when test="${player.pos.equals('QB')}"><span class="badge badge-warning">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('RB')}"><span class="badge badge-info">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('WR')}"><span class="badge badge-success">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('TE')}"><span class="badge badge-error">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('K')}"><span class="badge">${player.pos} ${player.pos_rank}</span></c:when>
							<c:otherwise><span class="badge badge-inverse">${player.pos} ${player.pos_rank}</span></c:otherwise>
						</c:choose>
					</td>
					
					<td class="pos_rank-suggest"><span class="badge-adp">${player.rank}</span></td>
					
					<td class="team-suggest">${player.teamName}</td>
					<td class="bye-suggest">${player.bye}</td>
					<td class="stddev-suggest">${player.std_dev}</td>
					<td class="vsadp-suggest">${player.versus}</td>
					<td class="handcuff-suggest">${player.checkForHandcuff()}</td>
				</tr>
				
			</c:forEach>
		</tbody>
	</table>
</div>


