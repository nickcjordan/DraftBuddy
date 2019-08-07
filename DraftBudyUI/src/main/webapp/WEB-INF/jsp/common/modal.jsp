<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal fade player-modal" id="${player.getFantasyProsId()}playerModal" tabindex="-1" role="dialog" aria-labelledby="${player.getFantasyProsId()}playerModalTitle" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<form action="/pickPlayer" method="post">
				<h1 class="modal-title" id="${player.getFantasyProsId()}playerModalTitle"><strong>${player.getPlayerName()}</strong>
					<span class="modal-spacer"><small>${player.getPosition().getAbbrev()} - ${player.getRankMetadata().getPositionRank()}</small></span>
					<span class="modal-spacer"><small>ID: ${player.getFantasyProsId()}</small></span>
			        <button type="submit" value="${player.getFantasyProsId()}" name="playerId" class="btn btn-default modal-pick-button">Pick Player</button>
		      </h1></form>
			</div>
			<div class="modal-body col-sm-12">
				<div class="container-fluid">
					<div class="row">
						<div class="col-sm-10">
							<div class="row">
								<div class="col-sm-3 no-pad">
									<div class="player-modal-pic-wrapper">
										<img src="${player.getPictureMetadata().getPicLocation()}" class="img-fluid img-thumbnail" alt="No Photo Available">
									</div>
								</div>
								<div class="col-sm-9 no-pad">
								  	<ul class="list-group">
										<li class="list-group-item modal-list-item"><h3>
											<strong>Tier: </strong><span class="modal-spacer">${player.getTier()}</span><span class="modal-spacer"></span><span class="modal-spacer"></span>
											<strong>Team: </strong><span class="modal-spacer">${player.getTeam().getAbbreviation()}</span><span class="modal-spacer"></span><span class="modal-spacer"></span>
											<strong>Bye: </strong><span class="modal-spacer">${player.getBye()}</span>
										</h3></li>
										<li class="list-group-item modal-list-item"><h3><strong>O-Line: </strong>
											<span class="modal-spacer">Rank:<span class="modal-spacer">${player.getOffensiveLineMetadata().getRank()}</span></span>
											<span class="modal-spacer">Run:<span class="modal-spacer">${player.getOffensiveLineMetadata().getRushingScore()}</span></span>
											<span class="modal-spacer">Pass:<span class="modal-spacer">${player.getOffensiveLineMetadata().getPassingScore()}</span></span>
											<span class="modal-spacer">Avg:<span class="modal-spacer">${player.getOffensiveLineMetadata().getAvgScore()}</span></span>
										</h3></li>
										<li class="list-group-item modal-list-item">
											<h3><strong>Prev Season Targets: </strong>
												<span class="modal-spacer">Avg:<span class="modal-spacer">${player.getPositionalStats().getPriorAverageTargetsPerGame()}</span></span>
												<span class="modal-spacer">Total:<span class="modal-spacer">${player.getPositionalStats().getPriorTotalTargets()}</span></span>
											</h3>
										</li>
										<li class="list-group-item modal-list-item">
											<h3><strong>Rank Metrics: </strong>
												<span class="modal-spacer">Best:<span class="modal-spacer">${player.getRankMetadata().getBestRank()}</span></span>
												<span class="modal-spacer">Avg:<span class="modal-spacer">${player.getRankMetadata().getAvgRank()}</span></span>
												<span class="modal-spacer">Worst:<span class="modal-spacer">${player.getRankMetadata().getWorstRank()}</span></span>
											</h3>
										</li>
										<li class="list-group-item modal-list-item"><h3><strong>Backups: </strong><span class="modal-spacer">${player.getDraftingDetails().getHandcuffs()}</span></h3></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="col-sm-2">
							<ul class="list-group">
								<li class="list-group-item modal-list-item"><h3><strong>Rank:</strong><span class="modal-spacer modal-stat">${player.getRankMetadata().getOverallRank()}</span> </h3></li>
								<li class="list-group-item modal-list-item"><h3><strong>Adp:</strong><span class="modal-spacer modal-stat">${player.getRankMetadata().getAdp()}</span> </h3></li>
								<li class="list-group-item modal-list-item"><h3><strong>Vs. Adp:</strong><span class="modal-spacer modal-stat">${player.getRankMetadata().getVsAdp()}</span></h3></li>
							</ul>
						</div>
					</div>
					<c:if test="${!player.getPositionalStats().getProjectedTotalPoints().equals('-')}">
						<div class="row proj-row"> 
							<div class="col-sm-2 proj-title-box">
								<div class="row proj-stat-header-text proj-title"><strong>Proj Pts:</strong></div>
								<div class="row proj-stat-text-title proj-stat-text proj-title proj-title-bottom">${player.getPositionalStats().getProjectedTotalPoints()}</div>
								<div class="row proj-stat-header-text proj-title"><strong>Prior Pts:</strong></div>
								<div class="row proj-stat-text-title proj-stat-text proj-title proj-title-bottom">${Math.floor(player.getPositionalStats().getPriorTotalPoints())}</div>
							</div>
							<div class="col-sm-10 proj-stats-box">
								<div class="row proj-stat-header-text-prior"><strong>Prior Year Totals:</strong></div>
								<div class="row proj-stat-box">
									<c:forEach items="${player.getPriorRawStatsDetails().getStats().entrySet()}" var="statCategoryEntry">
										<div class="proj-stat-box">
											<c:if test="${!statCategoryEntry.getKey().equalsIgnoreCase(\"MISC\")}">
												<div class="row proj-stat-column-header-text"><strong>${statCategoryEntry.getKey()}</strong></div>
											</c:if>
											<c:forEach items="${statCategoryEntry.getValue().getColumns()}" var="stat">
												<div class="row proj-stat-header-text"><strong>${stat.getName()}</strong>: ${Math.floor(stat.getValue())}</div>
											</c:forEach>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${player.getNotesMetadata() != null}">
						<div class="row">
							<div class="col-md-12">
								<h3><strong>Player Notes:</strong></h3>
								<c:forEach items="${player.getNotesMetadata().getNotes()}" var="note">
									<div class="proj-stat-box">
										<div class="row modal-note-header"><strong>${note.getSource()}</strong> - (${note.getTimestamp()})</div>
										<div class="row modal-note-body"><p>${note.getText()}</p></div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:if>
				</div>
			</div>
			<div class="modal-footer"></div> <!-- container messes up without it -->
		</div>
	</div>
</div>
 
