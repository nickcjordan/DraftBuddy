<div class="row">
	<div class="col-md-12">
		<div class="progress progress-bar-tube">
			<div class="progress-bar" id="progress-bar" role="progressbar"
				aria-valuenow="${draftState.getPercent()}" aria-valuemin="0"
				aria-valuemax="100">${draftState.getPercent()}%</div>
		</div>
	</div>
</div>

<script>
	document.getElementById("progress-bar").style.width = ${draftState.getPercent()} + "%";
</script>