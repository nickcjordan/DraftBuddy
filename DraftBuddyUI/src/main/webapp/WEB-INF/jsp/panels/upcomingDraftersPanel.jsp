<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <div class="container-fluid panel panel-default upcoming-drafters-panel">
            <c:forEach items="${upcomingDrafters}" var="drafterSummary" varStatus="iteration">
                <div class="col-sm-1 upcoming-drafter-wrapper">
                    <div class="row condensed-row">
                        <div class="col-sm-3 very-condensed-col gray-title left-offset-title">
                            ${drafterSummary.getRoundNumber()}.${drafterSummary.getRoundIndex()}
                        </div>
                        <div class="col-sm-9 very-condensed-col green-title drafter-title">
                            <strong>
                                ${drafterSummary.getDrafter().getName()} 
                            </strong>
                        </div>
                    </div>
                    <div class="row condensed-row">
                        <div class="col-sm-6 very-condensed-col center-title">
                            <span class="badge position-badge badge-warning translucent-badge">QB</span> ${drafterSummary.getDrafter().getDraftedTeam().getQb().size()}
                        </div>
                        <div class="col-sm-6 very-condensed-col center-title">
                            <span class="badge position-badge badge-info translucent-badge">RB</span> ${drafterSummary.getDrafter().getDraftedTeam().getRb().size()}
                        </div>
                    </div>
                    <div class="row condensed-row">
                        <div class="col-sm-6 very-condensed-col center-title">
                            <span class="badge position-badge badge-success translucent-badge">WR</span> ${drafterSummary.getDrafter().getDraftedTeam().getWr().size()}
                        </div>
                        <div class="col-sm-6 very-condensed-col center-title">
                            <span class="badge position-badge badge-error translucent-badge">TE</span> ${drafterSummary.getDrafter().getDraftedTeam().getTe().size()}
                        </div>
                    </div>
                    
                </div>
            </c:forEach>

    </div>