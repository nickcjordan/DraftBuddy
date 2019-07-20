<%@page import="fantasy.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section">
	<div class="center"><h3 class="results-title"><strong>Draft Board:</strong></h3></div>
		<table class="table table-sm table-striped header-fixed positions result-table ">
			<thead class="thead-inverse">
				<tr>
					<th class="drafted-id">#</th>
					<c:forEach items="${draft.getOrderedNames()}" var="drafter">
						<th class="drafted-name">${drafter}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody class="draft-board">
				<c:forEach var = "i" begin = "0" end = "${roundNumber - 1}">
					<tr>
						<td class="drafted-id2"><strong>${i+1}</strong></td>
						<c:forEach items="${drafters}" var="drafter">
						      
							<c:choose>
							    <c:when test="${drafter.getDraftedTeam().getAllInDraftedOrder().size() > i }">
									<c:set var="player" value="${drafter.getDraftedTeam().getAllInDraftedOrder().get(i)}" scope="page"/>
							    	<td class="drafted">
							    		<a class="nameLink" data-toggle="modal" data-target="#${drafter.getDraftedTeam().getAllInDraftedOrder().get(i).id}playerModal">
							    			<c:choose>
												<c:when test="${player.pos.equals('QB')}"><span class="badge badge-warning draft-board-badge">${player.pos} ${player.pos_rank}</span></c:when>
												<c:when test="${player.pos.equals('RB')}"><span class="badge badge-info draft-board-badge">${player.pos} ${player.pos_rank}</span></c:when>
												<c:when test="${player.pos.equals('WR')}"><span class="badge badge-success draft-board-badge">${player.pos} ${player.pos_rank}</span></c:when>
												<c:when test="${player.pos.equals('TE')}"><span class="badge badge-error draft-board-badge">${player.pos} ${player.pos_rank}</span></c:when>
												<c:when test="${player.pos.equals('K')}"><span class="badge draft-board-badge">${player.pos} ${player.pos_rank}</span></c:when>
												<c:otherwise><span class="badge badge-inverse draft-board-badge">${player.pos} ${player.pos_rank}</span></c:otherwise>
											</c:choose>
							    			<%-- ${drafter.getDraftedTeam().getAllInDraftedOrder().get(i).getPlayerName()} --%>
							    			${player.getPlayerName()}
						    			</a>
						    		</td>
							    </c:when>    
							    <c:otherwise>
									<td class="drafted"> </td>
							    </c:otherwise>
							</c:choose>
						
				    	</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
