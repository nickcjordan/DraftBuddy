<div class="row">
	<div class="col-md-12">
		<div class="progress progress-bar-tube">
			<div class="progress-bar" id="progress-bar" role="progressbar"
				aria-valuenow="${progressPercent}" aria-valuemin="0"
				aria-valuemax="100">${progressPercent}%</div>
		</div>
	</div>
</div>

<script>
	document.getElementById("progress-bar").style.width = ${progressPercent} + "%";
</script>