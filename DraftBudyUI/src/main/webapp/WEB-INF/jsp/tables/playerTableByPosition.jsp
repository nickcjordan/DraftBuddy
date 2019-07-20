<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="fill-body-wrapper">
	<div class="center"><h3><strong>${positionName}:</strong></h3></div>
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
				<tr class="tier${player.tier}">
					<td class="id-posList">
						<a href="/pickPlayer?playerId=${player.id}">
				        	<span class="badge-adp">${player.rank}</span>
			      		</a>
					</td>
					<%-- <td class="name-posList"><a class="nameLink" data-toggle="modal" data-target="#${player.id}playerModal"><strong>${player.getNameAndTags()}</strong></a></td> --%>
					<td class="name-posList">
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
					<td class="proj-pts-posList"><strong>${player.projectedPts}</strong></td>
					
					<td class="value-posList">
						<%@include file="../common/valueBadge.jsp"%>
					</td>
					
					<td class="tags-posList">
						<c:choose>
							<c:when test="${player.icons==null}">&nbsp;</c:when>
							<c:otherwise>
								<c:forEach items="${player.icons}" var="icon">
									<span class="${icon}" aria-hidden="true"></span>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="pos-posList">
						<c:choose>
							<c:when test="${player.pos.equals('QB')}"><span class="badge badge-warning">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('RB')}"><span class="badge badge-info">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('WR')}"><span class="badge badge-success">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('TE')}"><span class="badge badge-error">${player.pos} ${player.pos_rank}</span></c:when>
							<c:when test="${player.pos.equals('K')}"><span class="badge">${player.pos} ${player.pos_rank}</span></c:when>
							<c:otherwise><span class="badge badge-inverse">${player.pos} ${player.pos_rank}</span></c:otherwise>
						</c:choose>
					</td>
					<td class="adp-posList">${player.adp}</td>
					<td class="team-posList">${player.teamName}</td>
					<td class="bye-posList">${player.bye}</td>
					<td class="id-posList">${player.best}</td>
					<td class="id-posList">${player.avg}</td>
					<td class="id-posList">${player.worst}</td>
					<td class="id-posList">${player.std_dev}</td>
					<td class="id-posList">${player.versus}</td>
					<td class="handcuff-posList">${player.checkForHandcuff()}</td>
				</tr>
				
			</c:forEach>
		</tbody>
		</table>
</div>


