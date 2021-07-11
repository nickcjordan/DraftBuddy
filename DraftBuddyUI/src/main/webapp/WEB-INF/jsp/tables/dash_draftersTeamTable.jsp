<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<div class="fill-body-wrapper">
	<table class="table table-sm table-striped header-fixed dash">
		<thead class="thead-inverse">
			<tr>
				<th class="id-db_drafterTeam">Pos</th>
				<th class="name-db_drafterTeam">Name</th>
				<th class="team-db_drafterTeam">Team</th>
				<th class="bye-db_drafterTeam">Bye</th>
			</tr>
		</thead>
		<tbody>
			<tr class="posLine"><td class="no-border"><strong>QB</strong></td></tr>
			<c:forEach items="${currentDrafter.getDraftedTeam().qb}" var="player">
				<%@include file="lists/dash_draftersTeamList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>RB</strong></td></tr>
			<c:forEach items="${currentDrafter.getDraftedTeam().rb}" var="player">
				<%@include file="lists/dash_draftersTeamList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>WR</strong></td></tr>
			<c:forEach items="${currentDrafter.getDraftedTeam().wr}" var="player">
				<%@include file="lists/dash_draftersTeamList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>TE</strong></td></tr>
			<c:forEach items="${currentDrafter.getDraftedTeam().te}" var="player">
				<%@include file="lists/dash_draftersTeamList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>K</strong></td></tr>
			<c:forEach items="${currentDrafter.getDraftedTeam().k}" var="player">
				<%@include file="lists/dash_draftersTeamList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>DST</strong></td></tr>
			<c:forEach items="${currentDrafter.getDraftedTeam().d}" var="player">
				<%@include file="lists/dash_draftersTeamList.jsp"%>
			</c:forEach>
		</tbody>
	</table>
</div>
