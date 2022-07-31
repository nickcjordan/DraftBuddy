<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal fade player-modal" id="${player.getFantasyProsId()}playerModal" tabindex="-1" role="dialog" aria-labelledby="${player.getFantasyProsId()}playerModalTitle"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<form action="/pickPlayer" method="post">
					<h1 class="modal-title" id="${player.getFantasyProsId()}playerModalTitle">
						<strong>${player.getPlayerName()}</strong> <span class="modal-spacer"><small>${player.getPosition().getAbbrev()} - ${player.getRankMetadata().getPositionRank()}</small></span>
					<button type="submit" value="${player.getFantasyProsId()}" name="playerId" class="btn btn-default modal-pick-button">Pick Player</button>
					<span class="modal-id-text"><small>ID: ${player.getFantasyProsId()}</small></span>
					</h1>
				</form>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-sm-3">
							<div class="player-modal-pic-wrapper">
								<img src="${player.getPictureMetadata().getPicLocation()}" class="img-fluid img-thumbnail" alt="No Photo Available">
							</div>
						</div>
						<div class="col-sm-3 space-on-top-col">
							<table class="table table-bordered table-sm modal-table">
								<thead>
									<tr>
										<th class="modal-top-category" class="modal-top-category" scope="col" colspan="4">Player Details</th>
									</tr>
									<tr>
										<th scope="col">Tier</th>
										<th scope="col">Team</th>
										<th scope="col">OLine</th>
										<th scope="col">Bye</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${player.getTier()}</td>
										<td>${player.getTeam().getAbbreviation()}</td>
										<td>${player.getTeam().getoLineRank()}</td>
										<td>${player.getBye()}</td>
									</tr>
								</tbody>
							</table>
							<c:if test="${player.getDraftingDetails().getHandcuffs() != null}">
								<ul class="list-group backups-list">
									<li class="list-group-item modal-list-item">
										<h3>
											<b>Backups</b>
										</h3>
									</li>
									<li class="list-group-item modal-list-item">
										<div class="modal-backups-text">${player.getDraftingDetails().getHandcuffs()}</div>
									</li>
								</ul>
							</c:if>
						</div>
						<div class="col-sm-3 space-on-top-col">
							<table class="table table-bordered table-sm modal-table">
								<thead>
									<tr>
										<th class="modal-top-category" class="modal-top-category" scope="col" colspan="3">Draft Position</th>
									</tr>
									<tr>
										<th scope="col">ADP</th>
										<th scope="col">Value</th>
										<th scope="col">VsADP</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${player.getRankMetadata().getAdp()}</td>
										<td><span class="badge badge-val modal-badge badge-${player.getDraftingDetails().getCurrentPlayerValueBadgeClass()}"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span></td>
										<td><span class="badge badge-val modal-badge badge-${player.getDraftingDetails().getVsValueBadgeClass()}"><strong>${player.getRankMetadata().getVsAdp()}</strong></span></td>
									</tr>
								</tbody>
							</table>

							<table class="table table-bordered table-sm modal-table">
								<thead>
									<tr>
										<th class="modal-top-category" scope="col" colspan="3">Ranking</th>
									</tr>
									<tr>
										<th scope="col">Best</th>
										<th scope="col">Avg</th>
										<th scope="col">Worst</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${player.getRankMetadata().getBestRank()}</td>
										<td>${player.getRankMetadata().getAvgRank()}</td>
										<td>${player.getRankMetadata().getWorstRank()}</td>
									</tr>
								</tbody>
							</table>

						</div>
						<div class="col-sm-3 space-on-top-col">

							<c:if test="${player.getFfBallersPlayerProjection().getAvgPoints() != null}">
								<table class="table table-bordered table-sm modal-table">
									<thead>
										<tr>
											<th class="modal-top-category" scope="col" colspan="1">Prior Pts</th>
											<th class="modal-top-category" scope="col" colspan="1">Proj Pts</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>${player.getPositionalStats().getPriorAveragePointsPerGame()}</td>
											<td>${player.getFfBallersPlayerProjection().getAvgPoints()}</td>
										</tr>
									</tbody>
								</table>
							</c:if>

							<c:if test="${player.getPositionalStats().getPriorTotalTargets() != null}">
								<table class="table table-bordered table-sm modal-table">
									<thead>
										<tr>
											<th class="modal-top-category" scope="col" colspan="2">Prior Targets</th>
										</tr>
										<tr>
											<th scope="col">Avg</th>
											<th scope="col">Total</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>${player.getPositionalStats().getPriorAverageTargetsPerGame()}</td>
											<td>${player.getPositionalStats().getPriorTotalTargets()}</td>
										</tr>
									</tbody>
								</table>
							</c:if>
						</div>
					</div>
					
					<c:if test="${player.getFfBallersPlayerProjection().getBlurb() != null}">
						<div class="row">
								<div class="col-md-12">
									<p>${player.getFfBallersPlayerProjection().getBlurb()}</p>
								</div>
						</div>
					</c:if>
					

					<c:if test="${player.getPriorRawStatsDetails().getStats().size() != 0}">
						<div class="row">
							<div class="col-md-12">
								<table class="table table-bordered table-sm modal-table">
									<thead>
										<tr>
											<c:forEach items="${player.getPriorRawStatsDetails().getStats().entrySet()}" var="statCategoryEntry">
												<c:if test="${!statCategoryEntry.getKey().equalsIgnoreCase(\"MISC\")}">
													<th class="modal-top-category" scope="col" colspan="${statCategoryEntry.getValue().getColspan()}">${statCategoryEntry.getKey()}</th>
												</c:if>
											</c:forEach>
										</tr>
										<tr>
											<c:forEach items="${player.getPriorRawStatsDetails().getStats().entrySet()}" var="statCategoryEntry">
												<c:if test="${!statCategoryEntry.getKey().equalsIgnoreCase(\"MISC\")}">
													<c:forEach items="${statCategoryEntry.getValue().getStats().entrySet()}" var="statEntry">
														<th scope="col">${statEntry.getKey()}</th>
													</c:forEach>
												</c:if>
											</c:forEach>
										</tr>
									</thead>
									<tbody>
										<tr>
											<c:forEach items="${player.getPriorRawStatsDetails().getStats().entrySet()}" var="statCategoryEntry">
												<c:if test="${!statCategoryEntry.getKey().equalsIgnoreCase(\"MISC\")}">
													<c:forEach items="${statCategoryEntry.getValue().getStats().entrySet()}" var="statEntry">
														<td>${statEntry.getValue().getValue()}</td>
													</c:forEach>
												</c:if>
											</c:forEach>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
					
					<c:if test="${player.getPositionalStats().getPriorStatsByWeekNumber().size() != 0}">
						<div class="row">
							<div class="col-md-12">
								<table class="table table-bordered table-sm modal-table">
									<thead>
										<tr>
											<th class="modal-top-category" class="modal-top-category" scope="col" colspan="16">Prior Fantasy Points By Week</th>
										</tr>
										<tr>
											<c:forEach var="i" begin="1" end="16">
												<th scope="col">${i}</th>
											</c:forEach>
										</tr>
									</thead>
									<tbody>
										<tr>
											<c:forEach var="i" begin="1" end="16">
												<c:choose>
													<c:when test="${player.getPositionalStats().getPriorStatsByWeekNumber().get(String.valueOf(i)) != null}">
														<td>${player.getPositionalStats().getPriorStatsByWeekNumber().get(String.valueOf(i)).getStats().get("FPTS").getValue()}</td>
													</c:when>
													<c:otherwise>
														<td>0</td>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>

					<c:if test="${player.getNotesMetadata() != null}">
						<div class="row">
							<div class="col-md-12">
								<h3>
									<strong>Player Notes:</strong>
								</h3>
								<c:forEach items="${player.getNotesMetadata().getNotes()}" var="note">
									<div class="proj-stat-box">
										<div class="row modal-note-header">
											<strong>${note.getSource()}</strong> - (${note.getTimestamp()})
										</div>
										<div class="row modal-note-body">
											<p>${note.getText()}</p>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:if>
					
					
					
				</div>
			</div>
			<div class="modal-footer"></div>
			<div class="row">
						<div class="col-md-12">
							<table class="table table-sm modal-table">
								<tbody>
									<tr class="empty-table">
										<c:forEach var="tag" items="${allTags}">
										
										
										<c:choose>
													<c:when test="${!player.getNameAndTags().contains(tag.getTag())}">
														<td>
														
															<form action="/tagPlayer" method="post">
																<button type="submit" value="${player.getFantasyProsId()},${tag}" name="playerIdWithTag" class="btn btn-default modal-pick-button tag-button">${tag}</button>
															</form>
														
														</td>
													</c:when>
													<c:otherwise>
														<td>
														
															<form action="/untagPlayer" method="post">
																<button type="submit" value="${player.getFantasyProsId()},${tag}" name="playerIdWithTag" class="btn btn-default modal-pick-button untag-button">${tag}</button>
															</form>
														
														</td>
													</c:otherwise>
												</c:choose>
										
										
											
										</c:forEach>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
		</div>
	</div>
</div>

