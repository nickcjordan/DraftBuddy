<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3 class="drafter-title">${drafter.name}:</h3>

<table class="table table-sm table-striped header-fixed dash condensed-players">
	<thead class="thead-inverse">
		<tr>
			<th class="id-drafter-team-condensed">ID</th>
			<th class="name-drafter-team-condensed">Name</th>
		</tr>
	</thead>
	<tbody>
		<tr class="posLine">
			<td class="no-border"><strong>QB</strong></td>
		</tr>
		<c:forEach items="${drafter.draftedTeam.qb}" var="player">
			<%@include file="lists/drafterTeamListCondensed.jsp"%>
		</c:forEach>
		<tr class="posLine">
			<td class="no-border"><strong>RB</strong></td>
		</tr>
		<c:forEach items="${drafter.draftedTeam.rb}" var="player">
			<%@include file="lists/drafterTeamListCondensed.jsp"%>
		</c:forEach>
		<tr class="posLine">
			<td class="no-border"><strong>WR</strong></td>
		</tr>
		<c:forEach items="${drafter.draftedTeam.wr}" var="player">
			<%@include file="lists/drafterTeamListCondensed.jsp"%>
		</c:forEach>
		<tr class="posLine">
			<td class="no-border"><strong>TE</strong></td>
		</tr>
		<c:forEach items="${drafter.draftedTeam.te}" var="player">
			<%@include file="lists/drafterTeamListCondensed.jsp"%>
		</c:forEach>
		<tr class="posLine">
			<td class="no-border"><strong>K</strong></td>
		</tr>
		<c:forEach items="${drafter.draftedTeam.k}" var="player">
			<%@include file="lists/drafterTeamListCondensed.jsp"%>
		</c:forEach>
		<tr class="posLine">
			<td class="no-border"><strong>DST</strong></td>
		</tr>
		<c:forEach items="${drafter.draftedTeam.d}" var="player">
			<%@include file="lists/drafterTeamListCondensed.jsp"%>
		</c:forEach>
	</tbody>
</table>