<template>
  <div>
    <div v-if="!loading">
      <div class="row">
        <div class="col-6">
          <div class="form-group">
            <p class="profile-input-label">
              <a-lang value="date-from"> </a-lang>
            </p>
            <input
              name="betDateFrom"
              v-model="betDateFrom"
              id="betDateFrom"
              class="form-control modal-custom-input"
              type="date"
              required
              @change="getBets()"
            />
          </div>
        </div>
        <div class="col-6">
          <div class="form-group">
            <p class="profile-input-label">
              <a-lang value="date-to"> </a-lang>
            </p>
            <input
              name="betDateTo"
              v-model="betDateTo"
              id="betDateTo"
              class="form-control modal-custom-input"
              type="date"
              required
              @change="getBets()"
            />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <div class="card">
            <no-result v-if="bets && bets.length == 0"></no-result>

            <div id="bet" class="list-group scroll-list" v-if="!isMetadata">
              <a
                data-toggle="modal"
                data-target="#betDetails"
                href="#"
                class="list-group-item list-group-item-action my-align-left"
                @click="getBetsDetail(item.bet_id)"
                v-for="item in bets"
                :key="item.bet_id"
              >
                <div class="row">
                  <div class="col">
                    <small>
                      <i class="fa fa-calendar"></i>
                      <span v-if="item.ts_open != null">{{
                        formatDateYYYYMMDDHHmmss(item.ts_open)
                      }}</span>
                      - <a-lang value="bet-id"> </a-lang>:
                      <b>{{ item.bet_id }}</b>
                    </small>
                  </div>
                  <!-- <div class="col offset-6">
                              <span @click.stop="">
                                <i
                                  style="color: grey"
                                  class="fa fa-chevron-right"
                                  @click="
                                    isMetadata = true;
                                    getBetMetadata(item.betid);
                                  "
                                ></i>
                              </span>
                            </div>-->
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.bet != null"
                >
                  <b><a-lang value="bet"> </a-lang>:</b
                  ><small>{{ formatterNumber(item.bet, true) }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.win != null"
                >
                  <b><a-lang value="win"> </a-lang>:</b
                  ><small>{{ formatterNumber(item.win, true) }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.refund != null"
                >
                  <b><a-lang value="refund"> </a-lang>:</b
                  ><small>{{ formatterNumber(item.refund, true) }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.description != null"
                >
                  <b><a-lang value="description"> </a-lang>:</b
                  ><small>{{ item.description }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.description != null"
                >
                  <b><a-lang value="status"> </a-lang>:</b
                  ><small
                    :style="
                      item.status_desc == 'Won'
                        ? 'color:green'
                        : item.status_desc == 'Lost'
                        ? 'color:red'
                        : ''
                    "
                    >{{ getLocate("bet " + item.status_desc) }}</small
                  >
                </div>
              </a>
            </div>
            <div class="list-group scroll-list" v-if="isMetadata">
              <div>
                <div class="col-1">
                  <span>
                    <a href="#">
                      <i
                        style="color: grey"
                        class="fa fa-chevron-left"
                        @click="isMetadata = false"
                      ></i>
                    </a>
                  </span>
                </div>
                <div
                  class="col-12"
                  v-if="betMetadataList && betMetadataList.length == 0"
                >
                  <a-alert
                    :alert="{
                      type: 'alert-info',
                      message: 'No details found',
                    }"
                  ></a-alert>
                </div>
              </div>
              <span
                class="list-group-item my-align-left"
                v-for="item in betMetadataList"
                :key="item.name"
              >
                <div class="d-flex w-100 justify-content-between">
                  <b>{{ item.name }}:</b><small>{{ item.value }}</small>
                </div>
                <div class="d-flex w-100 justify-content-between">
                  <b><a-lang value="date"> </a-lang>:</b
                  ><small>{{ formatDateYYYYMMDDHHmmss(item.ts) }}</small>
                </div>
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="modal fade"
      id="betDetails"
      tabindex="-1"
      role="dialog"
      aria-labelledby="betDetailsLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content back-dark">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">
              <p>
                <a-lang value="bet-detail"> </a-lang>
              </p>
            </h5>
            <button
              type="button"
              class="close text-white"
              @click="$('#betDetails').modal('hide')"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <no-result v-if="betDetails && betDetails.length == 0"></no-result>
            <div id="bet-detail" class="list-group scroll-list">
              <a
                data-toggle="modal"
                data-target="#betDetails"
                href="#"
                class="list-group-item list-group-item-action my-align-left"
                v-for="item in betDetails"
                :key="item.round"
              >
                <small v-if="item.ts != null && item.ts != ''">
                  <i class="fa fa-calendar"></i>
                  <span>{{ formatDateYYYYMMDDHHmmss(item.ts) }}</span>
                </small>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.description != null && item.description != ''"
                >
                  <small>{{ item.description }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.device != null && item.device != ''"
                >
                  <b><a-lang value="device"> </a-lang>:</b
                  ><small>{{ item.device }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.status != null && item.status != ''"
                >
                  <b><a-lang value="state"> </a-lang>:</b
                  ><small>{{ getLocate("bet " + item.status) }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.bet != null && item.bet > 0"
                >
                  <b><a-lang value="bet"> </a-lang>:</b
                  ><small>{{ formatterNumber(item.bet, true) }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.win != null && item.win > 0"
                >
                  <b><a-lang value="win"> </a-lang>:</b
                  ><small>{{ formatterNumber(item.win, true) }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.refund != null && item.refund > 0"
                >
                  <b><a-lang value="refund"> </a-lang>:</b
                  ><small>{{ formatterNumber(item.refund, true) }}</small>
                </div>
              </a>
            </div>
          </div>
          <div class="modal-footer"></div>
        </div>
      </div>
    </div>
  </div>
  <!-- BETS END -->
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["loading"],
  data() {
    return {
      bets: [],
      betDetails: [],
      betMetadataList: [],
      betDateFrom: this.formatDateYYYYMMDD(this.addDays(new Date(), -1)),
      betDateTo: this.formatDateYYYYMMDD(new Date()),
      isMetadata: false,
    };
  },
  mounted() {
    this.getBets();
  },
  methods: {
    getBets() {
      this.bets = [];
      if (!this.checkDateFilter(this.betDateTo, this.betDateFrom)) return;

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("datefrom", this.localDateToUtc(this.betDateFrom));
      formData.append(
        "dateto",
        this.localDateToUtc(this.addDays(this.betDateTo, 1))
      );
      this.$http
        .post("bets/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.bets = result.bets;
            this.bets.sort(function (a, b) {
              let dateA = new Date(a.ts);
              let dateB = new Date(b.ts);
              if (dateA > dateB) return -1;
              else return 1;
            });
          } else {
            this.setError(result.description || result.error);
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    getBetsDetail(betid) {
      let formData = new FormData();
      formData.append("betid", betid);
      this.$http
        .post("bets/detail", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.betDetails = result.bets;
          } else {
            this.setError(result.description || result.error);
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    getBetMetadata(betid) {
      let formData = new FormData();
      formData.append("betid", betid);
      this.$http
        .post("bets/metadata/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.betMetadataList = result.metadata;
          } else {
            this.setError(result.description || result.error);
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    checkDateFilter(date1, date2) {
      if (!date1 || !date2) {
        this.setError(this.getLocate("check-date-4"));
        return false;
      }

      const MAX_DAYS_VIEW = 30;
      this.error = false;
      this.errorMessage = null;
      const diffDays = this.differenceTwoDate(date1, date2);
      if (diffDays > MAX_DAYS_VIEW) {
        this.setError(
          this.getLocate("check-date-1") +
            " " +
            MAX_DAYS_VIEW +
            " " +
            this.getLocate("check-date-2")
        );
        return false;
      }
      if (diffDays < 0) {
        this.setError(this.getLocate("check-date-3"));
        return false;
      }
      return true;
    },
  },
};
</script>