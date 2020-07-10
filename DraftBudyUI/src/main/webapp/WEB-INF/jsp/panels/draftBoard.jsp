<%@page import="fantasy.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="">
	<div class="center table-responsive"><h3 class="results-title white-title"><strong>Draft Board:</strong></h3></div>
		<table class="table table-sm table-striped header-fixed positions result-table ">
			<thead class="thead-inverse">
				<tr>
					<th class="drafted-id">#</th>
					<c:forEach items="${draft.getDraft().getOrderedNames()}" var="drafter">
						<th class="drafted-name">${drafter}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody class="draft-board">
				<c:forEach var = "i" begin = "0" end = "${draft.getRoundNum() - 1}">
					<tr>
						<td class="drafted-id2"><strong>${i+1}</strong></td>
						<c:forEach items="${drafters}" var="drafter">
						      
							<c:choose>
							    <c:when test="${drafter.getDraftedTeam().getAllInDraftedOrder().size() > i }">
									<c:set var="player" value="${drafter.getDraftedTeam().getAllInDraftedOrder().get(i)}" scope="page"/>
							    	<td class="drafted">
							    		<a class="nameLinkWhite" data-toggle="modal" data-target="#${player.getFantasyProsId()}playerModal">
							    			<span class="badge draft-board-badge ${player.getPosition().getBadgeClass()}">
							    			<div class="player-badge-name">${player.getFirstName()}</div>
							    			<div>${player.getLastName()}</div>
							    			</span>
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
