<template>
  <div v-if="!loading">
    <div class="row">
      <div class="col-sm-4 col-6 col-loteria">
        <div class="form-group">
          <p class="profile-input-label">
            <a-lang value="date-from"> </a-lang>
          </p>
          <input
            name="transactionDateFrom"
            v-model="transactionDateFrom"
            id="transactionDateFrom"
            class="form-control modal-custom-input"
            type="date"
            required
            @change="getTransactions()"
          />
        </div>
      </div>
      <div class="col-sm-4 col-6 col-loteria">
        <div class="form-group">
          <p class="profile-input-label">
            <a-lang value="date-to"> </a-lang>
          </p>
          <input
            name="transactionDateTo"
            v-model="transactionDateTo"
            id="transactionDateTo"
            class="form-control modal-custom-input"
            type="date"
            required
            @change="getTransactions()"
          />
        </div>
      </div>
      <div class="col-sm-4 col-12 col-loteria">
        <div class="form-group">
          <p class="profile-input-label">
            <a-lang value="type"> </a-lang>
          </p>
          <select
            class="form-control modal-custom-input"
            style="color: 'black'"
            id="transactionType"
            v-model="transactionType"
            @change="getTransactions()"
          >
            <option value="ALL">
              {{ getLocate("all") }}
            </option>
            <option value="WITHDRAWAL">
              {{ getLocate("withdrawal") }}
            </option>
            <option value="DEPOSIT">
              {{ getLocate("deposit") }}
            </option>
            <option value="GAMES">
              {{ getLocate("games") }}
            </option>
            <option value="BONUS">
              {{ getLocate("bonuses") }}
            </option>
            <option value="REFUND">
              {{ getLocate("refund") }}
            </option>
            <option value="FEE">
              {{ getLocate("fee") }}
            </option>
            <option value="LOTTERY">
              {{ getLocate("lottery") }}
            </option>
          </select>
        </div>
      </div>
    </div>
    <no-result v-if="!loading && transactions && transactions.length == 0">
    </no-result>
    <div id="movements" class="list-group scroll-list">
      <span
        class="list-group-item my-align-left box-list-element box-list-element"
        v-for="item in transactions"
        :key="item.transaction_id"
      >
        <small>
          <i class="fa fa-calendar"></i>
          <span v-if="item && item.ts_close">
            {{ formatDateYYYYMMDDHHmmss(item.ts_close) }}
          </span>
          <span v-else-if="item && item.ts_open">
            {{ formatDateYYYYMMDDHHmmss(item.ts_open) }}
          </span>

          <a-lang value="state"> </a-lang>:
          <b
            :style="
              item.state == 'VOID'
                ? 'color: red;'
                : item.state == 'COMMIT'
                ? 'color: green;'
                : 'color: #fdb200;'
            "
            >{{ item.state }}</b
          >
        </small>
        <div class="d-flex w-100 justify-content-between">
          <h6 class="mb-1">{{ item.description }}</h6>
        </div>
        <small v-if="isDescriptionRefund(item.description)"
          ><b><a-lang value="refund-message"></a-lang></b
        ></small>
        <br v-if="isDescriptionRefund(item.description)" />
        <small
          ><b>{{ item.type }}</b></small
        >
        <small>
          <span
            :style="
              item.type == 'DEPOSIT'
                ? 'color: green;'
                : item.type == 'WITHDRAWAL'
                ? 'color: red;'
                : ''
            "
            ><span v-if="item.type == 'WITHDRAWAL'">-</span>
            <span v-else-if="item.type == 'DEPOSIT'">+</span
            >{{ formatterNumber(item.amount, true) }}
            {{ currencyView(item.currency) }}</span
          >
          <span v-if="item.balance_type == 'G' || item.balance_type == 'B'"
            >(<a-lang value="promo"> </a-lang>)</span
          >
        </small>
        <br />
        <small
          ><b><a-lang value="balance"> </a-lang></b
        ></small>
        <small>
          <span
            v-if="item.total_balance"
            :style="item.total_balance > 0 ? 'color: green;' : 'color: red;'"
            >{{ formatterNumber(item.total_balance, true) }}
            {{ currencyView(item.currency) }}
          </span>
          <span v-else><a-lang value="unavailable"></a-lang></span>
        </small>
      </span>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["loading"],
  data() {
    return {
      //tab movements
      transactions: [],
      amount: null,
      reference: "",
      requestId: "",
      transactionId: "",
      transactionDateFrom: this.formatDateYYYYMMDD(
        this.addDays(new Date(), -1)
      ),
      transactionDateTo: this.formatDateYYYYMMDD(new Date()),
      transactionType: "ALL",
    };
  },
  created() {
    this.getTransactions();
  },
  methods: {
    //ottengo le transazioni per user id e session id
    getTransactions() {
      this.resetMessages();
      this.transactions = [];
      if (
        !this.checkDateFilter(this.transactionDateTo, this.transactionDateFrom)
      )
        return;

      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.transactionDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.transactionDateTo, 1))
      );
      formData.append("optype", this.transactionType);
      this.$http
        .post("transaction/listbytokens", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.transactions = result.transactions;
            this.transactions.sort(function (a, b) {
              let dataOrdinamento1;
              let dataOrdinamento2;
              if (a.ts_close != null) {
                dataOrdinamento1 = new Date(a.ts_close);
              } else {
                dataOrdinamento1 = new Date(a.ts_open);
              }
              if (b.ts_close != null) {
                dataOrdinamento2 = new Date(b.ts_close);
              } else {
                dataOrdinamento2 = new Date(b.ts_open);
              }
              if (dataOrdinamento1 < dataOrdinamento2) {
                return 1;
              }
              if (dataOrdinamento1 > dataOrdinamento2) {
                return -1;
              }
              return 0;
            });
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
    isDescriptionRefund(description) {
      return description.includes("refund deposit");
    },
    changeLoading(value) {
      this.$emit("change-loading", value);
    },
    setError(message) {
      this.$emit("set-error", message);
    },
    setSuccess(message) {
      this.$emit("set-success", message);
    },
    resetMessages() {
      this.$emit("reset-messages");
    },
  },
};
</script>
