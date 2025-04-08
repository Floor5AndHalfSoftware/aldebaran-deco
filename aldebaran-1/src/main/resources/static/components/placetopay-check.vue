<template>
  <div>
    <section id="faq" class="faq">
      <br /><br />
      <div class="container">
        <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
        <a-loading :show="loading"></a-loading>
        <div class="card" v-if="!loading">
          <div class="card-header">
            <span>
              <a-lang value="transaction-detail"> </a-lang>
            </span>
          </div>
          <a-alert
            v-if="!detailPlacetopay"
            :alert="{
              type: 'alert-info',
              message: this.getLocate('no-result-found'),
            }"
          ></a-alert>
          <div class="list-group scroll-list" v-if="detailPlacetopay">
            <span class="list-group-item my-align-left">
              <small>
                <i class="fa fa-calendar"></i>
                <span
                  v-if="detailPlacetopay.status && detailPlacetopay.status.date"
                  >{{
                    formatDateYYYYMMDDHHmmss(detailPlacetopay.status.date)
                  }}</span
                >
                <a-lang value="state"> </a-lang>:
                <span v-if="detailPlacetopay.status">
                  <b v-if="detailPlacetopay.status.status == 'OK'">
                    {{ getLocate("ok") }}
                  </b>
                  <b
                    v-else-if="detailPlacetopay.status.status == 'PENDING'"
                    style="color: orange"
                  >
                    {{ getLocate("pending") }}
                  </b>
                  <b
                    v-else-if="detailPlacetopay.status.status == 'REJECTED'"
                    style="color: red"
                  >
                    {{ getLocate("rejected") }}
                  </b>
                  <b
                    v-else-if="detailPlacetopay.status.status == 'APPROVED'"
                    style="color: green"
                  >
                    {{ getLocate("approved") }}
                  </b>
                  <b v-else>
                    {{ detailPlacetopay.status.status }}
                  </b>
                </span>
              </small>
              <div
                class="d-flex w-100 justify-content-between"
                v-if="detailPlacetopay.info && detailPlacetopay.info.length > 0"
              >
                <b><a-lang value="reference-id"> </a-lang>:</b
                ><small>{{ detailPlacetopay.info[0].reference }}</small>
              </div>
              <div
                class="d-flex w-100 justify-content-between"
                v-if="detailPlacetopay.info && detailPlacetopay.info.length > 0"
              >
                <b><a-lang value="issue"> </a-lang>:</b
                ><small>{{ detailPlacetopay.info[0].issuerName }}</small>
              </div>
              <div
                class="d-flex w-100 justify-content-between"
                v-if="detailPlacetopay.info && detailPlacetopay.info.length > 0"
              >
                <b><a-lang value="payment-method"> </a-lang>:</b
                ><small>{{ detailPlacetopay.info[0].paymentMethodName }}</small>
              </div>
              <div class="row" v-if="detailPlacetopay.status">
                <div class="col">
                  <small>
                    {{ detailPlacetopay.status.message }}
                  </small>
                </div>
              </div>
              <div
                class="row"
                v-if="
                  detailPlacetopay.info &&
                  detailPlacetopay.info[0].amount &&
                  detailPlacetopay.info[0].amount.from
                "
              >
                <div class="col-12">
                  <small>
                    <a-lang value="deposit"> </a-lang>:
                    <span
                      :style="
                        detailPlacetopay.info[0].amount.from.total > 0
                          ? 'color: green;'
                          : 'color: red;'
                      "
                      ><span
                        v-if="detailPlacetopay.info[0].amount.from.total < 0"
                        >-</span
                      >
                      <span v-else>+</span
                      >{{
                        formatterNumber(
                          detailPlacetopay.info[0].amount.from.total
                        )
                      }}
                      {{ detailPlacetopay.info[0].amount.from.currency }}
                    </span>
                  </small>
                </div>
              </div>
            </span>
          </div>
        </div>
        <br />
        <br />
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["reference"],
  data() {
    return {
      detailPlacetopay: {},
    };
  },
  created() {
    this.getPlacetopayCheck();
  },
  methods: {
    getPlacetopayCheck() {
      this.loading = true;
      this.detailPlacetopay = {};
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("reference", this.reference);
      this.$http
        .post("deposit/placetopay/check", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.detailPlacetopay = result;
          } else {
            this.alert = { type: "alert-danger", message: result.description };
          }
        })
        .catch((response) => {
          this.showError(response);
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>