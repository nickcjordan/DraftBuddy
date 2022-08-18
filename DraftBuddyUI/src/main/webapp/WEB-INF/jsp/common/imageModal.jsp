<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <div class="modal fade player-modal" id="${iteration.index + 1}IntelModal" tabindex="-1" role="dialog" aria-labelledby="${iteration.index + 1}IntelModalTitle" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="player-modal-pic-wrapper">
                                    <img src="/images/intel/draft_slot_${iteration.index + 1}.png"
                                        class="img-fluid img-thumbnail" alt="No Photo Available">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>