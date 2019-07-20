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
<body>
	<div class="container-fluid">

		<%@include file="../common/nav.jsp"%>

		<div class="col-md-1">
			<div class="menu">
				<div class="navbar-header">
					<ul class="nav nav4 nav-pills nav-stacked nav4">
						<li><h3 class="center posPad">
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
			<%@include file="../tables/playerTableByPosition.jsp"%>
		</div>

		<c:if
			test="<%=fantasy.controller.BaseController.errorMessage != null%>">
			<script>
				alert("<%=fantasy.controller.BaseController.errorMessage%>");
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