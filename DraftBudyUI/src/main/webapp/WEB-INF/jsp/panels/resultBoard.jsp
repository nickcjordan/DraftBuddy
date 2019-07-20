<%@page import="fantasy.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section">
	<div class="center"><h3 class="results-title"><strong>Draft Analysis:</strong></h3></div>
		<table class="table table-sm table-striped header-fixed">
			<thead class="thead-inverse">
				<tr>
					<th class="drafted-id"><strong>Avgs</strong></th>
					<c:forEach items="${draft.getOrderedNames()}" var="drafter">
						<th class="drafted-name">${drafter}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				
					<tr>
						<td class="drafted-id"><strong>Ovr:</strong></td>
						<c:forEach items="${drafterResults}" var="drafter" >
							<td class="drafted-name">${drafter.getDraftResultStats().getAverageOverallRank()}</td>
				    	</c:forEach>
					</tr>
					<tr>
						<td class="drafted-id"><strong>Qb:</strong></td>
						<c:forEach items="${drafters}" var="drafter" >
							<td class="drafted-name">${drafter.getDraftResultStats().getQbRank()}</td>
				    	</c:forEach>
					</tr>
					<tr>
						<td class="drafted-id"><strong>Rb:</strong></td>
						<c:forEach items="${drafters}" var="drafter" >
							<td class="drafted-name">${drafter.getDraftResultStats().getRbRank()}</td>
				    	</c:forEach>
					</tr>
					<tr>
						<td class="drafted-id"><strong>Wr:</strong></td>
						<c:forEach items="${drafters}" var="drafter" >
							<td class="drafted-name">${drafter.getDraftResultStats().getWrRank()}</td>
				    	</c:forEach>
					</tr>
					<tr>
						<td class="drafted-id"><strong>Te:</strong></td>
						<c:forEach items="${drafters}" var="drafter" >
							<td class="drafted-name">${drafter.getDraftResultStats().getTeRank()}</td>
				    	</c:forEach>
					</tr>
					<tr>
						<td class="drafted-id"><strong>K:</strong></td>
						<c:forEach items="${drafters}" var="drafter" >
							<td class="drafted-name">${drafter.getDraftResultStats().getkRank()}</td>
				    	</c:forEach>
					</tr>
					<tr>
						<td class="drafted-id"><strong>Dst:</strong></td>
						<c:forEach items="${drafters}" var="drafter" >
							<td class="drafted-name">${drafter.getDraftResultStats().getDstRank()}</td>
				    	</c:forEach>
					</tr>
					
					 <!-- TODO finish adding stats -->
					
					
			</tbody>
		</table>
</div>
