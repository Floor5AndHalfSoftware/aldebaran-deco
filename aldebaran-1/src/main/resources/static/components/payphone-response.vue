<template>
  <div>
    <section id="faq" class="faq">
      <br /><br />
      <div class="container">
        <a-alert
          :alert="alert"
          @close="alert.message = ''"
          v-if="error"
        ></a-alert>
        <a-loading :show="loading"></a-loading>
        <div class="card-header" v-if="!loading && !error">
          <span><a-lang value="transaction-detail"> </a-lang></span>
        </div>
        <div class="list-group scroll-list" v-if="!loading && !error">
          <span class="list-group-item my-align-left">
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.id"
            >
              <b><a-lang value="payment-id"> </a-lang>:</b
              >{{ this.$route.query.id }}
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.clientTransactionId"
            >
              <b><a-lang value="client-transaction-id"> </a-lang>:</b
              >{{ this.$route.query.clientTransactionId }}
            </div>
          </span>
        </div>

        <br />
        <div class="casino-btn text-center">
          <a href="#/" class="btn-4 yellow-btn">
            <a-lang value="start-playing"> </a-lang>
          </a>
        </div>
        <br />
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],

  data() {
    return {
      error: false,
      alert: { type: "alert-danger", message: "" },
    };
  },
  created() {
    this.confirm();
  },
  methods: {
    confirm() {
      let formData = new FormData();
      formData.append("id", this.$route.query.id);
      formData.append(
        "clientTransactionId",
        this.$route.query.clientTransactionId
      );
      this.$http
        .post("deposit/payphone/response", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result != 0) {
            this.error = true;
            this.alert = {
              type: "alert-danger",
              message: result.messageKo,
            };
          } else this.$emit("balance");
        })
        .catch((response) => {
          this.showError(response);
        });
    },
  },
};
</script>