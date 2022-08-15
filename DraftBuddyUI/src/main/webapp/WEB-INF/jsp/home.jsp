<!DOCTYPE html>

<html lang="en">

<head>
<meta charset="UTF-8">
<title>Draft Buddy</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
</head>

<body class="dark-falifa-bg">


	<!-- Brand and toggle -->
	<div class="navbar-header">
		<a class="navbar-brand front-brand white-title" href="/">Falifa Industries Presents: The Draft Buddy</a>
		<div class="intro-alternate">
			<p>
				<a class="btn btn-block btn-primary update-data-files-button" href="/updateApi">Update API Data</a>
			</p>
			<p>
				<a class="btn btn-block btn-primary update-data-files-button" href="/updateAll">Update Player Data</a>
			</p>
		</div>
	</div>



	<div class="container">
		<div class="intro">
			<p>
				<form class="" action="/startSleeper" method="post">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="857115695748603904" name="draftId" id="draftId">
					</div>
					<button type="submit" class=class="btn btn-block btn-primary">Sleeper Draft</button>
				</form>
			</p>
		</div>
		<div class="intro">
			<p>
				<a class="btn btn-block btn-primary" href="/start?appRunType=mock">Mock Draft</a>
			</p>
		</div>
		<div class="intro">
			<p>
				<a class="btn btn-block btn-primary" href="/start?appRunType=real">Real Draft</a>
			</p>
		</div>
		<div class="intro">
			<p>
				<a class="btn btn-block btn-primary" href="/start?appRunType=auto">Auto Draft</a>
			</p>
		</div>
	</div>

	<div class="container">
	
		
	
	</div>



</body>
</html>

