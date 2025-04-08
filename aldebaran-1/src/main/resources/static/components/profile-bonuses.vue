<template>
  <div>
    <div>
      <no-result v-if="!loading && bonuses.length == 0"></no-result>
      <div id="bonuses" class="list-group scroll-list" v-if="!loading">
        <span
          class="list-group-item my-align-left box-list-element"
          v-for="(item, index) in bonuses"
          :key="item.promoid"
        >
          <small>
            <i class="fa fa-calendar" v-if="item && item.grantts"></i>
            <span v-if="item && item.grantts">
              <a-lang value="grant-ts"></a-lang>
              {{ formatDateYYYYMMDDHHmmss(item.grantts) }}
            </span>
            -
            <a-lang value="goal"> </a-lang>:
            <b :style="item.percentage >= 100 ? 'color: green;' : 'color: red;'"
              >{{ formatterNumber(item.percentage) }}%</b
            >
            <span v-if="index == 0" style="color: green"
              ><b>(<a-lang value="active"> </a-lang>)</b></span
            >
            <span v-else style="color: orange"
              ><b>(<a-lang value="bet Pending"> </a-lang>)</b></span
            >
          </small>
          <div class="d-flex w-100 justify-content-between">
            <h6 class="mb-1">
              {{ item.name }}
            </h6>
            <div v-if="checkWithdrawalBonus(item)">
              <button
                class="btn btn-link-blue"
                @click="redeemBonus(item.promoid)"
              >
                <a-lang value="withdrawal"> </a-lang>
              </button>
            </div>
            <div v-else-if="checkCloseManuallyBonus(item, index)">
              <button
                class="btn btn-link-blue"
                @click="confirmBonusClose(item.promoid)"
              >
                <a-lang value="delete-bonus"> </a-lang>
              </button>
            </div>
            <div v-if="item.withdrawts">
              <b style="color: green">
                <a-lang value="redeemed"> </a-lang>
              </b>
            </div>
          </div>
          <small>
            <span>
              <a-lang value="bonus-value"> </a-lang>
            </span>
            <span
              ><b>
                {{ formatterNumber(item.amount, true) }}
                {{ currencyView(item.currency) }}
              </b>
            </span>
          </small>
          <br />
          <small>
            <span>
              <a-lang value="actual-bonus-balance"> </a-lang>
            </span>
            <b>
              <span v-if="index == 0" :style="'color: green;'">
                {{ formatterNumber(item.promobalance, true) }}
                {{ currencyView(item.currency) }}
              </span>
              <span v-else>
                {{ formatterNumber(0, true) }}
                {{ currencyView(item.currency) }}
              </span>
            </b>
          </small>
          <small v-if="item && item.expiredts">
            <br />
            <i class="fa fa-calendar"></i>
            <span>
              <a-lang value="expired-ts"> </a-lang>
              {{ formatDateYYYYMMDDHHmmss(item.expiredts) }}
            </span>
          </small>
        </span>
      </div>
    </div>
    <div
      class="modal fade"
      id="confirmCloseBonusModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="confirmCloseBonusLabel"
      aria-hidden="true"
      style="top: 3vh"
    >
      <div class="modal-dialog" role="document">
        <div
          class="modal-content modal-custom-content"
          style="min-height: 40vh; height: auto !important"
        >
          <div class="modal-header">
            <h5
              class="modal-title modal-custom-title"
              id="exampleModalLabel"
              style="margin: 0 !important"
            >
              <p style="color: white !important">
                <a-lang value="warning" style="color: white !important">
                </a-lang
                >!
              </p>
            </h5>
            <button
              type="button"
              class="close loteria-text-black"
              @click="$('#confirmCloseBonusModal').modal('hide')"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body modal-custom-body">
            <div class="container-fluid">
              <a-alert
                v-if="bonuses && bonuses.length > 0"
                :alert="{
                  type: 'alert-warning',
                  message: this.getLocate('bonuses-alert'),
                }"
              ></a-alert>
            </div>
          </div>
          <div class="modal-footer justify-content-center">
            <div class="casino-btn">
              <a
                href="javascript:void(0)"
                class="btn-4 modal-custom-input"
                @click="closeBonus(promoidBonusClose)"
                ><a-lang value="confirm" style="color: white !important">
                </a-lang
              ></a>
            </div>
            <div class="casino-btn">
              <a
                href="javascript:void(0)"
                class="btn-4 modal-custom-input"
                @click="$('#confirmCloseBonusModal').modal('hide')"
                ><a-lang value="cancel" style="color: white !important">
                </a-lang
              ></a>
            </div>
          </div>
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
      //tab bonuses
      bonuses: [],
      promoidBonusClose: null,
    };
  },
  created() {
    this.getBonuses();
  },
  methods: {
    redeemBonus(promoid) {
      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("promoid", promoid);
      this.$http
        .post("bonus/redeem", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getBonuses();
          } else {
            this.setError(result.description || result.error);
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.changeLoading(false);
        });
    },
    getBonuses() {
      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("bonus/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.bonuses = result.bonuses;
          } else {
            this.setError(result.description || result.error);
          }
        })
        .catch((response) => {
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.changeLoading(false);
        });
    },
    closeBonus(promoid) {
      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("promoid", promoid);
      this.$http
        .post("bonus/close", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getBonuses();
          } else {
            this.setError(result.description || result.error);
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          $("#confirmCloseBonusModal").modal("hide");
          this.changeLoading(false);
        });
    },
    confirmBonusClose(promoid) {
      this.promoidBonusClose = promoid;
      $("#confirmCloseBonusModal").modal({
        backdrop: "static",
        keyboard: false,
      });
    },
    checkWithdrawalBonus(item) {
      let expiredts = new Date(item.expiredts);
      let now = new Date();
      if (item.percentage >= 100 && now <= expiredts && item.withdrawts == null)
        return true;
    },
    checkCloseManuallyBonus(item, index) {
      if (item.percentage < 100 && index == 0) return true;
    },
  },
};
</script>
