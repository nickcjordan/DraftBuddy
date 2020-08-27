<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Position Page</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<script src="js/jquery-slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body class="dark-falifa-bg">
	<div class="container-fluid">

		<%@include file="../common/nav.jsp"%>

		<div class="col-md-1">
			<div class="menu">
				<div class="navbar-header">
					<ul class="nav nav-pills nav-stacked positionPageNav">
						<li><h3 class="center posPad white-title">
								<strong>Positions:</strong>
							</h3></li>
						<li><a href="/pos">All Available</a></li>
						<li><a href="/pos?pos=QB">Quarterbacks</a></li>
						<li><a href="/pos?pos=RB">Running backs</a></li>
						<li><a href="/pos?pos=WR">Wide Receivers</a></li>
						<li><a href="/pos?pos=TE">Tight Ends</a></li>
						<li><a href="/pos?pos=K">Kickers</a></li>
						<li><a href="/pos?pos=DST">Defense/ST</a></li>
					</ul>
				</div>
			</div>
		</div>


		<div class="col-md-11">
			<%-- <%@include file="../tables/playerTableByPosition.jsp"%> --%>
			<div class="fill-body-wrapper">
				<div class="center">
					<h3 class="white-title">
						<strong>${positionName}:</strong>
					</h3>
				</div>
				<c:set var="position" value="${position}" scope="application"></c:set>
				<div class="">
					<table data-toggle="table" class="table table-sm header-fixed dash tabbed-table thin-celled-table outer-scrollbar">
						<thead class="thead-inverse">
							<tr>
								<th class="id-suggest"><a href="/sortPositionPage?sortBy=ADP&position=${positionAbbrev}">ADP</a></th>
								<th class="pos_rank-suggest"><a href="/sortPositionPage?sortBy=ECR&position=${positionAbbrev}">ECR</a></th>
								<th class="name-suggest"><a href="/sortPositionPage?sortBy=NAME&position=${positionAbbrev}">Name</a></th>
								<th class="proj-pts-suggest"><a href="/sortPositionPage?sortBy=PROJ_PTS&position=${positionAbbrev}">PrjAvg</a></th>
								<th class="value-suggest"><a href="/sortPositionPage?sortBy=ADP_VAL&position=${positionAbbrev}"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="Current number of picks player differs from their original <b>ADP</b>">Value</span> <script>$('span').tooltip();</script></a></th>
								<th class="vsadp-suggest"><a href="/sortPositionPage?sortBy=VS_ADP_VAL&position=${positionAbbrev}"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="How far away the player's <b>ADP</b> is from the player's <b>Overall Rank</b>">VsAdp</span> <script>$('span').tooltip();</script></a></th>
								<th class="proj-pts-suggest"><a href="/sortPositionPage?sortBy=PRIOR_PTS&position=${positionAbbrev}">PriTot</a></th>
								<th class="proj-pts-suggest"><a href="/sortPositionPage?sortBy=AVG_PRIOR_PTS&position=${positionAbbrev}">PriAvg</a></th>
								<th class="proj-pts-suggest"><a href="/sortPositionPage?sortBy=AVG_TARGETS&position=${positionAbbrev}">TrgtAvg</a></th>
								<th class="tags-suggest">Tags</th>
								<th class="pos-suggest">Pos</th>
								<th class="team-suggest">Team</th>
								<th class="team-suggest">OLine</th>
								<th class="bye-suggest">Bye</th>
								<th class="stddev-suggest"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="How <b>unsure</b> people are of this player. This measures how much a set of values drifts from the <b>Average</b>, or <em>'a measure confidence'</em> in statistical conclusions">St-Dv</span><script>$('span').tooltip();</script></th>
								<th class="handcuff-suggest">Backups</th>
							</tr>
						</thead>
				
						<c:set var="playerListContent" value="${playerList}" scope="application"></c:set>
						<%@include file="../tables/suggestionTableBody.jsp"%>
					</table>
				</div>
				
			</div>

			<c:if test="${error} != null">
				<script>
				alert("${error}");
			</script>
			</c:if>

			<%@include file="../common/progressBar.jsp"%>

		</div>





		<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/fliplightbox.min.js"></script>
	<script type="text/javascript">$('#graphics').flipLightBox()
	</script>

</body>
</html>