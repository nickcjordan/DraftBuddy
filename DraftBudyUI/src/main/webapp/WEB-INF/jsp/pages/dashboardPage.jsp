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
  <body>
  
  
	<div class="container-fluid">
	<%@include file="../common/nav.jsp"%>
	
	<div class="row">
		<div class="col-md-2">
			<div class="center thin-text"><h3><strong>Draft Feed:</strong></h3></div>
			<%@include file="../tables/lists/draftFeedList.jsp"%>
		</div>
		
		
		<div class="col-md-2">
			<div class="row">
				<div class="center thin-text"><h3><strong>${currentDrafter.name}'s Team:</strong></h3></div>
				<%@include file="../tables/dash_draftersTeamTable.jsp"%>
			</div>
			<div class="row">
				<%@include file="../panels/strategyBox.jsp"%>
			</div>
		</div>
		
		
		<div class="col-md-8">
			<ul class="nav nav-tabs all-suggestion-tabs" id="suggestionTableTab" role="tablist">
				<li class="nav-item active suggestion-tab"><a class="nav-link suggestion-link" id="suggs-tab" data-toggle="tab" href="#suggs" role="tab" aria-controls="suggs" aria-selected="true">Suggestions</a></li>
				<li class="nav-item suggestion-tab"><a class="nav-link suggestion-link" id="adp-tab" data-toggle="tab" href="#adp" role="tab" aria-controls="adp" aria-selected="false">ADP</a></li>
				<li class="nav-item suggestion-tab"><a class="nav-link suggestion-link" id="rank-tab" data-toggle="tab" href="#rank" role="tab" aria-controls="rank" aria-selected="false">ECR</a></li>
				<li class="center thin-text suggestion-title-tag"> <strong>Suggested Available Players:</strong></li>
			</ul>
			<div class="tab-content" id="suggestionTableTabContent">
				<div class="tab-pane fade active in" id="suggs" role="tabpanel" aria-labelledby="suggs-tab">
					<c:set var="playerListContent" value="${playersSortedBySuggestions}" scope="application"></c:set>
					<%@include file="../tables/dash_suggestionTable.jsp"%>
				</div>
				<div class="tab-pane fade" id="adp" role="tabpanel" aria-labelledby="adp-tab">
					<c:set var="playerListContent" value="${playersSortedByAdp}" scope="application"></c:set>
					<%@include file="../tables/dash_suggestionTable.jsp"%>
				</div>
				<div class="tab-pane fade" id="rank" role="tabpanel" aria-labelledby="rank-tab">
					<c:set var="playerListContent" value="${playersSortedByRank}" scope="application"></c:set>
					<%@include file="../tables/dash_suggestionTable.jsp"%>
				</div>
			</div>
	</div>
</div>


	<c:if test="<%=fantasy.controller.BaseController.errorMessage != null%>">
		<script>
			alert("<%=fantasy.controller.BaseController.errorMessage%>");
		</script>
	</c:if>

	<%@include file="../common/progressBar.jsp"%>
			
	</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/fliplightbox.min.js"></script>
	<script type="text/javascript">$('#graphics').flipLightBox()</script>
    
</body>
</html>