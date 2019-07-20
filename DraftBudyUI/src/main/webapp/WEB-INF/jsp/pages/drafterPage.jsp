<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Drafter Page</title>
<script src="js/jquery-slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link href="css/style.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="container-fluid">

		<%@include file="../common/nav.jsp"%>
		
		<div class="drafters-tables-wrapper">

			<c:forEach items="${drafters}" var="drafter">
				<div class="col-sm-1 condensed-column">
					<%@include file="../tables/drafterTeamTableCondensed.jsp"%>
				</div>
			</c:forEach>
		
		</div>

		<c:if test="<%=fantasy.controller.BaseController.errorMessage != null%>">
			<script>alert("<%=fantasy.controller.BaseController.errorMessage%>");</script>
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