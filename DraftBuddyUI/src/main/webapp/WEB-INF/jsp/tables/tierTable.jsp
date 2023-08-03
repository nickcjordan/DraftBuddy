<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="">
	<div class="center">
		<h3 class="white-title">
			<strong>Remaining Players by Tier</strong>
		</h3>
	</div>
	
	<c:if test="${tierSource.equals(\"jj\")}">
	
		<table class="table table-sm header-fixed positions white-cells">
			<thead class="thead-inverse">
				<tr>
					<th class="pos-suggest">Overall</th>
					<th class="name-suggest">Name</th>
					<th class="pos-suggest">Position</th>
					<th class="pos-suggest">Team</th>
				</tr>
			</thead>
			<tbody>
	
				<c:forEach items="${tierList}" var="tier">
					<c:if test="${tier.getPlayers().size() > 0}">
						<tr class="posLineLight">
							<td class="no-border"><strong>Tier ${tier.getTier()}</strong></td>
						</tr>
					</c:if>
					<c:forEach items="${tier.getPlayers()}" var="player">
						<tr class="white-cell">
							<td class="pos-suggest">${player.getLateRoundRank().getOverall()}</td>
							<td class="name-suggest">${player.getPlayerName()}</td>
							<td class="pos-suggest"><span class="badge position-badge ${player.getPosition().getBadgeClass()}">${player.getPosition().getAbbrev()}${player.getLateRoundRank().getPositionRank()}</span></td>
							<td class="pos-suggest">${player.getTeam().getAbbreviation()}</td>
						</tr>
					</c:forEach>
				</c:forEach>
	
			</tbody>
		</table>
	
	</c:if>
	
	<c:if test="${tierSource.equals(\"fantasy_pros\")}">
	
		<table class="table table-sm header-fixed positions white-cells">
			<thead class="thead-inverse">
				<tr>
					<th class="pos-suggest">Overall</th>
					<th class="name-suggest">Name</th>
					<th class="pos-suggest">Position</th>
					<th class="pos-suggest">Team</th>
				</tr>
			</thead>
			<tbody>
	
				<c:forEach items="${tierList}" var="tier">
					<c:if test="${tier.getPlayers().size() > 0}">
						<tr class="posLineLight">
							<td class="no-border"><strong>Tier ${tier.getTier()}</strong></td>
						</tr>
					</c:if>
					<c:forEach items="${tier.getPlayers()}" var="player">
						<tr class="white-cell">
							<td class="pos-suggest">${player.getRankMetadata().getOverallRank()}</td>
							<td class="name-suggest">${player.getPlayerName()}</td>
							<td class="pos-suggest"><span class="badge position-badge ${player.getPosition().getBadgeClass()}">${player.getPosition().getAbbrev()}${player.getRankMetadata().getPositionRank()}</span></td>
							<td class="pos-suggest">${player.getTeam().getAbbreviation()}</td>
						</tr>
					</c:forEach>
				</c:forEach>
	
			</tbody>
		</table>
	
	</c:if>
</div>
