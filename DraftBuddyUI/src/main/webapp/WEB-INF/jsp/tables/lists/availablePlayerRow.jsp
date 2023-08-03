<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<tr class="white-cell">
	<td class="id-3">${player.getRankMetadata().getPositionRank()}</td>
	<td class="name-3"><a class="nameLink" data-toggle="modal" data-target="#${player.getFantasyProsId()}playerModal">${player.getPlayerName()}</a></td>
	<td class="pos-3">${player.getPosition().getAbbrev()}</td>
	<td class="team-3">${player.getTeam().getAbbreviation()}</td>
</tr>

