<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<!-- Brand and toggle -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navvy" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/">Falifa Industries</a>
		</div>

		<!--nav links, forms -->
		<div class="collapse navbar-collapse" id="navvy">

			<ul class="nav navbar-nav navTop">
				<li><div class="navName">Drafting: <span class="nav-name-badge">${draft.getCurrentDrafter().getName()}</span></div></li>
				<li><a href="/dashboard">Draft Page</a></li>
				<li><a href="/pos">Players By Position</a></li>
				<li><a href="/nflTeams">Players By Team</a></li>
				<li><a href="/drafters">Drafters</a></li>
				<li><a href="/draftBoard">Draft Board</a></li>
				<li>
					<div class="roundHeader">
						<span class="big-badge">Round ${draft.getRoundNum()}</span>&nbsp;&nbsp;&nbsp;
						<span class="big-badge">Pick ${draft.pickNumber}</span>
					</div>
				</li>
				<c:if test="${!draft.isMockDraftMode() && draft.getDraftPicks().size() > 0}">
					<li><div class="navUndoButton"><span class="undo-button-badge"><a href="/undo">Undo</a></span></div></li>
				</c:if>
			</ul>
			
			<form class="navbar-form navbar-right" action="/filter" method="post">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="" name="filterText" id="filterText">
				</div>
				<button type="submit" class="btn btn-default">Filter</button>
			</form>

		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>

