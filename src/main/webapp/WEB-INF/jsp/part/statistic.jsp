<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="col-3">
    <div class="d-grid">
        <button type="button" class="btn text-white btn-secondary btn-block" onclick="getStatistic()"
                data-bs-toggle="modal" data-bs-target="#statisticModal">
            Show statistic
        </button>
    </div>
</div>
<div class="modal fade" id="statisticModal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header border-0">
                <h5 class="modal-title" id="modalLabel">Statistic</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <table id="statistic-table" class="table table-sm table-striped">
                    <thead>
                    <tr>
                        <th scope="col">Parameter</th>
                        <th scope="col">Value</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer border-0">
                <button type="button" class="btn text-white btn-secondary btn-block" data-bs-dismiss="modal">Close
                </button>
            </div>
        </div>
    </div>
</div>
