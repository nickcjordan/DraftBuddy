<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="panel panel-default strategy-panel">
		<!-- List group -->
		<!-- <ul class="list-group strategy-list-box">
            <li class="list-group-item strategy-list-header">
                <b class="green-title">QB:</b>
                
                <c:forEach items="${remainingPositionPlayersOverview.QB.getRemainingCounts()}" var="remaining" varStatus="iteration" >
                    <br> ${iteration.index + 1} : ${remaining} 
                </c:forEach>
            </li>
		</ul> -->

        <!-- <ul class="list-group strategy-list-box"> -->
            <!-- <li class="list-group-item strategy-list-header"> -->
                <div class="row">
                    <div class="strategy-wrapper">
                        <div class="col-sm-3 remaining-players-box">
                            <b class="green-title">QB: (${remainingPositionPlayersOverview.QB.getTotal()})</b>
                            <c:forEach items="${remainingPositionPlayersOverview.QB.getRemainingCounts()}" var="remaining" varStatus="iteration">
                                <div class="row">
                                    ${iteration.index + 1} : ${remaining}
                                </div>
                            </c:forEach>
                        </div>
                        <div class="col-sm-3 remaining-players-box">
                            <b class="green-title">RB: (${remainingPositionPlayersOverview.RB.getTotal()})</b>
                            <c:forEach items="${remainingPositionPlayersOverview.RB.getRemainingCounts()}" var="remaining" varStatus="iteration">
                                <div class="row">
                                    ${iteration.index + 1} : ${remaining}
                                </div>
                            </c:forEach>
                        </div>
                        <div class="col-sm-3 remaining-players-box">
                            <b class="green-title">WR: (${remainingPositionPlayersOverview.WR.getTotal()})</b>
                            <c:forEach items="${remainingPositionPlayersOverview.WR.getRemainingCounts()}" var="remaining" varStatus="iteration">
                                <div class="row">
                                    ${iteration.index + 1} : ${remaining}
                                </div>
                            </c:forEach>
                        </div>
                        <div class="col-sm-3 remaining-players-box">
                            <b class="green-title">TE: (${remainingPositionPlayersOverview.TE.getTotal()})</b>
                            <c:forEach items="${remainingPositionPlayersOverview.TE.getRemainingCounts()}" var="remaining" varStatus="iteration">
                                <div class="row">
                                    ${iteration.index + 1} : ${remaining}
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            <!-- </li> -->
		<!-- </ul> -->
</div>