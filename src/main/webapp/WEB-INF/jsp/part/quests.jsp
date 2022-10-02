<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row g2 py-1">
  <div class="col">
    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
      <button type="button" class="btn text-white btn-secondary btn-block" onclick="getQuests()"
              data-bs-toggle="modal" data-bs-target="#questsModal">
        Quests
      </button>
    </div>
  </div>
</div>

<div class="modal fade" id="questsModal" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header border-0">
        <h5 class="modal-title" id="modalLabel">Quests</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <table id="quests-table" class="table table-sm">
          <thead class="thead-secondary">
          <tr>
            <th scope="col">Issued by</th>
            <th scope="col">Status</th>
            <th scope="col">Type</th>
            <th scope="col">Target name</th>
            <th scope="col">info</th>
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