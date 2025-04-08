<template>
  <div>
    <div v-if="!loading">
      <div class="row">
        <div class="col-6 col-loteria">
          <div class="form-group">
            <p class="profile-input-label">
              <a-lang value="date-from"> </a-lang>
            </p>
            <input
              name="lotteryDateFrom"
              v-model="lotteryDateFrom"
              id="lotteryDateFrom"
              class="form-control modal-custom-input"
              type="date"
              required
              @change="getLottery()"
            />
          </div>
        </div>
        <div class="col-6 col-loteria">
          <div class="form-group">
            <p class="profile-input-label">
              <a-lang value="date-to"> </a-lang>
            </p>
            <input
              name="lotteryDateTo"
              v-model="lotteryDateTo"
              id="lotteryDateTo"
              class="form-control modal-custom-input"
              type="date"
              required
              @change="getLottery()"
            />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-12 col-loteria">
          <div class="card">
            <no-result
              v-if="lotteryList && lotteryList.length == 0"
            ></no-result>

            <div id="lottery" class="list-group scroll-list">
              <div
                class="row"
                style="margin: 0"
                v-if="lotteryList && lotteryList.length > 0"
              >
                <lottery-ticket
                  v-for="item in lotteryList"
                  :key="item.ticket_id"
                  :item="item"
                  @open-detail-modal="openDetailModal"
                  @open-window="openWindow"
                  @get-lottery-pay-prize="getLotteryPayPrize"
                ></lottery-ticket>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="modal fade"
      id="lotteryDetails"
      tabindex="-1"
      role="dialog"
      aria-labelledby="lotteryDetailsLabeled"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content modal-custom-content back-light">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">
              <p style="color: #fff">
                <a-lang value="prize"> </a-lang>
              </p>
            </h5>
            <button
              type="button"
              class="close loteria-text-black"
              @click="$('#lotteryDetails').modal('hide')"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <no-result
              v-if="lotteryPayPrizeList && lotteryPayPrizeList.length == 0"
            ></no-result>

            <div id="bet-detail" class="list-group scroll-list">
              <a
                v-for="item in lotteryPayPrizeList"
                :key="item.transaction_id"
                style="cursor: pointer"
                class="list-group-item list-group-item-action my-align-left box-list-element"
                @click="openWindow(item.user_id, item.ticket_id)"
              >
                <div class="row">
                  <div class="col">
                    <small>
                      <i class="fa fa-calendar"></i>
                      <span v-if="item.ts != null">{{
                        formatDateYYYYMMDDHHmmss(item.ts)
                      }}</span>
                      - <a-lang value="ticket-id"> </a-lang>:
                      <b>{{ item.ticket_id }}</b>
                    </small>
                  </div>
                </div>
                <small
                  ><b><a-lang value="prize"> </a-lang></b
                ></small>
                <small>
                  <span style="color: green"
                    >+{{ formatterNumber(item.prize, true) }}
                    {{ currencyView(item.currency) }}</span
                  >
                </small>
                <br />
                <small
                  ><b><a-lang value="status"> </a-lang>: </b></small
                >
                <b
                  ><small style="color: green"> {{ item.status }}</small></b
                >
              </a>
            </div>
          </div>
          <div class="modal-footer"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["loading"],
  data() {
    return {
      //lottery
      lotteryList: [],
      lotteryDateFrom: this.formatDateYYYYMMDD(this.addDays(new Date(), -1)),
      lotteryDateTo: this.formatDateYYYYMMDD(new Date()),
      lotteryPayPrizeList: [],
    };
  },
  created() {
    this.getLottery();
  },
  methods: {
    getLotteryPayPrize(ticketId) {
      this.lotteryPayPrizeList = [];
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("ticketId", ticketId);
      this.$http
        .post("lottery/payprize/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.lotteryPayPrizeList = result.lotteryPayPrizeTickets;
            this.lotteryPayPrizeList.sort(function (a, b) {
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
    getLottery() {
      this.resetMessages();
      this.lotteryList = [];
      if (!this.checkDateFilter(this.lotteryDateTo, this.lotteryDateFrom))
        return;

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("datefrom", this.localDateToUtc(this.lotteryDateFrom));
      formData.append(
        "dateto",
        this.localDateToUtc(this.addDays(this.lotteryDateTo, 1))
      );
      this.$http
        .post("lottery/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.lotteryList = result.lotteryTickets;
            this.lotteryList.sort(function (a, b) {
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
    openDetailModal() {
      $("#lotteryDetails").modal();
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
    openWindow(userId, ticketId) {
      if (ticketId)
        window.open(
          "/#/prj_compra_tus_juegos/detalle/" + userId + "/" + ticketId,
          "_blank"
        );
    },
    resetMessages() {
      this.$emit("reset-messages");
    },
  },
};
</script>
