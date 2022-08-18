<div class="row">
	<div class="col-md-12">
		<div class="progress progress-bar-tube">
			<div class="progress-bar" id="progress-bar" role="progressbar"
				aria-valuenow="${draft.getPercent()}" aria-valuemin="0"
				aria-valuemax="100">${draft.getPercent()}%</div>
		</div>
	</div>
</div>

<script>
	document.getElementById("progress-bar").style.width = ${draft.getPercent()} + "%";
</script>

<div class="container-fluid">
	<c:forEach items="${playersToBuildModalFor}" var="player">
		<%@include file="../common/modal.jsp"%>
	</c:forEach>
</div>

<div class="container-fluid">
	<c:forEach items="${drafters}" var="drafter" varStatus="iteration">
		<%@include file="../common/imageModal.jsp"%>
	</c:forEach>
</div>