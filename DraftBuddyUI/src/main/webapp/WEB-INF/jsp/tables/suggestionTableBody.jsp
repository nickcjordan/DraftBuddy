<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


		<thead class="thead-inverse">
			<tr>
				<th class="id-suggest"><a href="/sortSuggestions?sortBy=ADP">ADP</a></th>
				<th class="pos_rank-suggest"><a href="/sortSuggestions?sortBy=ECR">ECR</a></th>
				<th class="value-suggest"><a href="/sortSuggestions?sortBy=ADP_VAL"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="Current number of picks player differs from their original <b>ADP</b>">Value</span> <script>$('span').tooltip();</script></a></th>
				<th class="name-suggest"><a href="/sortSuggestions?sortBy=NAME">Name</a></th>
				<th class="pos-suggest">Pos</th>
				<th class="proj-pts-suggest"><a href="/sortSuggestions?sortBy=FFB_RANK">FFB</a></th>
				<th class="proj-pts-suggest"><a href="/sortSuggestions?sortBy=FFB_FLEX_RANK">FlxRnk</a></th>
				<th class="proj-pts-suggest"><a href="/sortSuggestions?sortBy=FFB_FLEX_GRADE">FlxGrd</a></th>
				<th class="vsadp-suggest">Prj</th>
				<th class="proj-pts-suggest"><a href="/sortSuggestions?sortBy=AVG_PRIOR_PTS">PriAvg</a></th>
				<th class="tags-suggest">Tags</th>
				<th class="team-suggest">Team</th>
				<!-- <th class="team-suggest">SOS</th> -->
				<th class="bye-suggest">Bye</th>
<!-- 				<th class="stddev-suggest"><span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="How <b>unsure</b> people are of this player. This measures how much a set of values drifts from the <b>Average</b>, or <em>'a measure confidence'</em> in statistical conclusions">St-Dv</span><script>$('span').tooltip();</script></th>-->
				<th class="handcuff-suggest">Backups</th>
			</tr>
		</thead>


		<tbody class="scaled-body">
			<c:set var="pickIndexTempVar" value="${0}" scope="application" />
			<c:set var="grayLineCount" value="${0}" scope="application" />
			<c:forEach items="${playerListContent}" var="player" varStatus="status">
				<!-- set next pick number divider bar -->
				<c:set var="pickNumVar" value="${draft.getCurrentDrafter().getDraftedTeam().getDraftedPlayersInOrder().size() + grayLineCount + 2}" scope="application" />
				<c:choose>
					<c:when test="${(pickIndexTempVar != 0) && (draft.getCurrentDrafter().hasDraftPickIndex(draft.getPickNumber() + pickIndexTempVar))}">
						<tr class="center table-divider"><td><b>Pick: ${pickNumVar}</b></td><td><b>Pick: ${pickNumVar}</b></td><td><b>Pick: ${pickNumVar}</b></td><td><b>Pick: ${pickNumVar}</b></td></tr>
						<c:set var="grayLineCount" value="${grayLineCount + 1}" scope="application" />
					</c:when>
				</c:choose>
				<c:set var="pickIndexTempVar" value="${pickIndexTempVar + 1}" scope="application" />

				<tr class="tier${player.getTier()}">
					<td class="id-suggest"><a href="/pickPlayer?playerId=${player.getFantasyProsId()}"> <span class="badge-adp">${player.getRankMetadata().getAdp()}</span>
					<td class="pos_rank-suggest"><span class="badge-ecr">${player.getRankMetadata().getOverallRank()}</span></td></a></td>
					<td class="value-suggest"><span class="badge badge-val badge-${player.getDraftingDetails().getCurrentPlayerValueBadgeClass()}"><strong>${player.getDraftingDetails().getCurrentPlayerValue()}</strong></span></td>
					<td class="name-suggest">
						<a class="nameLink" data-toggle="modal" data-target="#${player.getFantasyProsId()}playerModal">
								<c:choose>
									<c:when test="${draft.getCurrentRoundHandcuffs().contains(player)}">
										<c:choose>
											<c:when
												test="${player.getDraftingDetails().isPlayerToTarget()}">
												<span class="handcuff-player-text"><strong>${player.getPlayerName()}</strong></span>
											</c:when>
											<c:otherwise>
												<span class="handcuff-player-text">${player.getPlayerName()}</span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when
												test="${player.getDraftingDetails().isPlayerToTarget()}">
												<strong>${player.getPlayerName()}</strong>
											</c:when>
											<c:when
												test="${player.getDraftingDetails().getTags().contains('B')}">
												<strike>${player.getPlayerName()}</<strike>
											</c:when>
											<c:when
												test="${player.getDraftingDetails().getTags().contains('+')}">
												<em>${player.getPlayerName()}</<em>
											</c:when>
											<c:when
												test="${player.getDraftingDetails().getTags().contains('$')}">
												<em>${player.getPlayerName()}</<em>
											</c:when>
											<c:otherwise>${player.getPlayerName()}</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							
						</a>
			</td>
					<td class="pos-suggest"><span class="badge position-badge ${player.getPosition().getBadgeClass()}">${player.getPosition().getAbbrev()}${player.getRankMetadata().getPositionRank()}</span></td>
					<td class="proj-avg-pts-suggest ffballers-col"><span class="badge position-badge medium-badge ${player.getPosition().getBadgeClass()}">${player.getFfBallersPlayerProjection().getPositionRank()}</span></td>
					<td class="proj-avg-pts-suggest ffballers-col"><span class="badge position-badge medium-badge ${player.getPosition().getBadgeClass()}">${player.getFfBallersPlayerProjection().getFlexRank()}</span></td>
					<td class="proj-avg-pts-suggest ffballers-col"><span class="badge position-badge medium-badge ${player.getFfBallersPlayerProjection().getFlexGradeBadgeClass()}">${player.getFfBallersPlayerProjection().getFlexGrade()}</span></td>
					<td class="vsadp-suggest">${player.getFfBallersPlayerProjection().getAvgPoints()}</td>
					<td class="proj-pts-suggest"><strong>${player.getPositionalStats().getPriorAveragePointsPerGame()}</strong></td>


					<td class="tags-suggest">
						<c:choose>
							<c:when test="${player.getDraftingDetails().getIcons()==null}">&nbsp;</c:when>
							<c:otherwise>
							<span title="" data-placement="top" data-html="true" data-toggle="tooltip" data-original-title="${player.getTagDescriptions()}">
								<c:forEach items="${player.getDraftingDetails().getIcons()}" var="icon">
									<span class="${icon}" aria-hidden="true"></span>
								</c:forEach>
							</span><script>$('span').tooltip();</script>
							</c:otherwise>
						</c:choose>
					</td>



					<td class="team-suggest">${player.getTeam().getAbbreviation()}</td>
					<!-- <td class="team-suggest"><span class="badge badge-val badge-${player.getSosBadgeClass(player.getPositionalSOSRank())}"><strong>${player.getPositionalSOSRank()}</strong></span></td> -->
					
					<td class="bye-suggest">${player.getBye()}</td>
					<!-- <td class="stddev-suggest">${player.getRankMetadata().getStdDev()}</td> -->
					
					<td class="handcuff-suggest">
						<c:forEach items="${player.getDraftingDetails().getPositionTeammates()}" var="teammate" varStatus="status" >
							<a class="nameLink smaller-text" data-toggle="modal" data-target="#${teammate.getId()}playerModal">${teammate.getName()}<c:if test="${!status.last}">, </c:if></a>
						</c:forEach>
					</td>
					
				</tr>

			</c:forEach>
		</tbody>
