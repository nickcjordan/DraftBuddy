<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Position Page</title>

     <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
    <script src="js/jquery-slim.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </head>
  <body class="dark-falifa-bg">
  
  
	<div class="container-fluid">
		<%@include file="../common/nav.jsp"%>
		<div class="row">
			<div class="col-md-2">
				<div class="center thin-text">
					<h3 class="white-title"><strong>Draft Feed:</strong></h3>
				</div>
				<%@include file="../tables/lists/draftFeedList.jsp"%>
			</div>
			
			<div class="col-md-2">
				<div class="row">
					<div class="center thin-text"><h3 class="white-title"><strong>${currentDrafter.name}'s Team:</strong></h3></div>
					<%@include file="../tables/dash_draftersTeamTable.jsp"%>
				</div>
				<div class="row">
					<%@include file="../panels/strategyBox.jsp"%>
				</div>
			</div>
			
			<div class="col-md-1">
				<c:forEach items="${remainingTierTO.getTos()}" var="to">
					<div class="row remaining-player-tier-row">
						<div class="center thin-text">
							<h4 class="white-title"><strong>Tier ${to.getTier()} ${to.getPos()}</strong></h4>
						</div>	
						
						<ul class="list-group strategy-list-box list-group-flush">
							<c:forEach items="${to.getPlayers()}" var="p">
								<li class="list-group-item strategy-list-header">${p.getPlayerName()}<li>
					    	</c:forEach>
						</ul>
						
					</div>
		    	</c:forEach>
			</div>
			
			<div class="col-md-7">
				<div class="tab-content" id="suggestionTableTabContent">
					<div class="section">
						<table data-toggle="table" class="table table-sm header-fixed dashboard-dash tabbed-table thin-celled-table outer-scrollbar">
							<thead class="thead-inverse">
								<tr>
									<th class="id-suggest"><a href="/sortSuggestions?sortBy=ADP">ADP</a></th>
									<th class="pos_rank-suggest"><a href="/sortSuggestions?sortBy=ECR">ECR</a></th>
									<th class="name-suggest"><a href="/sortSuggestions?sortBy=NAME">Name</a></th>
									<th class="proj-pts-suggest"><a href="/sortSuggestions?sortBy=PROJ_PTS">PrjAvg</a></th>
									<th class="value-suggest"><a href="/sortSuggestions?sortBy=ADP_VAL"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="Current number of picks player differs from their original <b>ADP</b>">Value</span> <script>$('span').tooltip();</script></a></th>
									<th class="vsadp-suggest"><a href="/sortSuggestions?sortBy=VS_ADP_VAL"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="How far away the player's <b>ADP</b> is from the player's <b>Overall Rank</b>">VsAdp</span> <script>$('span').tooltip();</script></a></th>
									<th class="proj-pts-suggest"><a href="/sortSuggestions?sortBy=AVG_PRIOR_PTS">PriAvg</a></th>
									<th class="tags-suggest">Tags</th>
									<th class="pos-suggest">Pos</th>
									<th class="team-suggest">Team</th>
									<th class="team-suggest">SOS</th>
									<th class="bye-suggest">Bye</th>
									<th class="stddev-suggest"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="How <b>unsure</b> people are of this player. This measures how much a set of values drifts from the <b>Average</b>, or <em>'a measure confidence'</em> in statistical conclusions">St-Dv</span><script>$('span').tooltip();</script></th>
									<th class="handcuff-suggest">Backups</th>
								</tr>
							</thead>
		
							<c:set var="playerListContent" value="${playersSortedBySuggestions}" scope="application"></c:set>
							<%@include file="../tables/suggestionTableBody.jsp"%>
						</table>
					</div>
				</div>
			</div>
		</div>
	
		<c:if test="${error} != null">
			<script>
				alert("${error}");
			</script>
		</c:if>
	
		<%@include file="../common/progressBar.jsp"%>
	</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/fliplightbox.min.js"></script>
	<script type="text/javascript">$('#graphics').flipLightBox()</script>
    
</body>
</html>