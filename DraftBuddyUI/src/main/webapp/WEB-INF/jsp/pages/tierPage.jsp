<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Tier Page</title>
<script src="js/jquery-slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link href="css/style.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body class="dark-falifa-bg">
	<div class="container-fluid">

		<%@include file="../common/nav.jsp"%>

		 <div class="col-md-2">
			<h3 class="center underline white-title">
				<strong>Tier Source:</strong>
			</h3>
			<div class="menu-teams">
				<div class="navbar-header">
					<ul class="nav nav-pills nav-stacked teamPageNav nav-stacked-teams">
							<li><a href="/tiers?source=jj">JJ Zachariason</a></li>
							<li><a href="/tiers?source=fantasy_pros">Fantasy Pros</a></li>
					</ul>
				</div>
			</div>
		</div>

		<!-- <div class="col-md-10"> -->
		<div class="col-md-10">
			<%@include file="../tables/tierTable.jsp"%>
		</div>
		
		<c:if test="${error} != null">
			<script>
				alert("${error}");
			</script>
		</c:if>

	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/fliplightbox.min.js"></script>
	<script type="text/javascript">$('#graphics').flipLightBox()
	</script>

</body>
</html>