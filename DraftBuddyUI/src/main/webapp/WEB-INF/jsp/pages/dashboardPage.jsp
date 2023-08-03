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
		<!-- <div class="row"> -->
			<%@include file="../panels/upcomingDraftersPanel.jsp"%>
		<!-- </div> -->
		<div class="row">
			<div class="col-md-2">
				<div class="center thin-text">
					<h3 class="white-title"><strong>Draft Feed:</strong></h3>
				</div>
				<%@include file="../tables/lists/draftFeedList.jsp"%>
				<div class="center thin-text">
					<%@include file="../panels/remainingPositionalOverview.jsp"%>
				</div>
			</div>
			
			<div class="col-md-2">
				<div class="row">
					<div class="center thin-text"><h3 class="white-title"><strong>${currentDrafter.name}'s Team: (${currentDraftedProjectedPoints})</strong></h3></div>
					<%@include file="../tables/dash_draftersTeamTable.jsp"%>
				</div>
				<div class="row">
					<%@include file="../panels/strategyBox.jsp"%>
				</div>
				
			</div>
			
			<div class="col-md-1 thin-lane">
				<c:forEach items="${remainingTierTO.getTos()}" var="to">
					<div class="row remaining-player-tier-row">
						<div class="center thin-text">
							<h4 class="white-title"><strong>Tier ${to.getTier()} ${to.getPos()}</strong></h4>
						</div>	
						
						<ul class="list-group tier-list list-group-flush">
							<c:forEach items="${to.getPlayers()}" var="p">
								<li class="tier-player-list-tiem"><span class="badge tier-player-badge position-badge big-badge ${p.getPosition().getBadgeClass()} tier-badge-text">${p.getRankMetadata().getPositionRank()}) ${p.getPlayerName()}</span><li>
					    	</c:forEach>
						</ul>
						
					</div>
		    	</c:forEach>
			</div>
			
			<div class="col-md-7">
				<div class="tab-content" id="suggestionTableTabContent">
					<div class="section">
						<table data-toggle="table" class="table table-sm header-fixed dashboard-dash tabbed-table thin-celled-table outer-scrollbar">
		
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