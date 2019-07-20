<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section">
	<div class="center"><h3><strong>Available Players on ${teamName}:</strong></h3></div>
		<table class="table table-sm header-fixed positions">
			<thead class="thead-inverse">
				<tr>
					<th class="id-3">ID</th>
					<th class="name-3">Name</th>
					<th class="pos_rank-3">Pos/Ovr</th>
					<th class="team-3">Team</th>
					<th class="bye-3">Bye</th>
					<th class="handcuff-3">Backups</th>
				</tr>
			</thead>
			<tbody>
				
			<tr class="posLine"><td class="no-border"><strong>QB</strong></td></tr>
			<c:forEach items="${team.qb}" var="player">
				<%@include file="lists/playerList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>RB</strong></td></tr>
			<c:forEach items="${team.rb}" var="player">
				<%@include file="lists/playerList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>WR</strong></td></tr>
			<c:forEach items="${team.wr}" var="player">
				<%@include file="lists/playerList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>TE</strong></td></tr>
			<c:forEach items="${team.te}" var="player">
				<%@include file="lists/playerList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>K</strong></td></tr>
			<c:forEach items="${team.k}" var="player">
				<%@include file="lists/playerList.jsp"%>
			</c:forEach>
			<tr class="posLine"><td class="no-border"><strong>DST</strong></td></tr>
			<c:forEach items="${team.d}" var="player">
				<%@include file="lists/playerList.jsp"%>
			</c:forEach>
				
			</tbody>
		</table>
</div>
