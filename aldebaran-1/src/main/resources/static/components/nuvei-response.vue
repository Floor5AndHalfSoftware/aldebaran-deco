<template>
  <div>
    <section id="faq" class="faq">
      <br /><br />
      <div class="container">
        <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
        <a-loading :show="loading"></a-loading>
        <div class="card-header" v-if="!loading && !error">
          <span><a-lang value="transaction-detail"> </a-lang></span>
        </div>
        <div class="list-group scroll-list" v-if="!loading && !error">
          <span class="list-group-item my-align-left">
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.ppp_status"
            >
              <b><a-lang value="ppp-status"> </a-lang>:</b>
              <font color="green" v-if="$route.query.ppp_status == 'OK'">
                {{ this.$route.query.ppp_status }}
              </font>
              <font color="red" v-else-if="$route.query.ppp_status == 'FAIL'">
                {{ this.$route.query.ppp_status }}
              </font>
              <font color="black" v-else>
                {{ this.$route.query.ppp_status }}
              </font>
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.cardCompany"
            >
              <b><a-lang value="card-company"> </a-lang>:</b
              >{{ this.$route.query.cardCompany }}
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.nameOnCard"
            >
              <b><a-lang value="name-on-card"> </a-lang>:</b
              >{{ this.$route.query.nameOnCard }}
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.productId"
            >
              <b><a-lang value="product-id"> </a-lang>:</b
              >{{ this.$route.query.productId }}
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.responseTimeStamp"
            >
              <b><a-lang value="response-timestamp"> </a-lang>:</b
              >{{ this.$route.query.responseTimeStamp }}
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.payment_method"
            >
              <b><a-lang value="payment-method"> </a-lang>:</b
              >{{ this.$route.query.payment_method }}
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.Error"
            >
              <b><a-lang value="error"> </a-lang>:</b
              >{{ this.$route.query.Error }}
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.message"
            >
              <b><a-lang value="message"> </a-lang>:</b
              >{{ this.$route.query.message }}
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.Status"
            >
              <b><a-lang value="status"> </a-lang>:</b>
              <font color="green" v-if="$route.query.Status == 'APPROVED'">
                {{ this.$route.query.Status }}
              </font>
              <font color="red" v-else-if="$route.query.Status == 'DECLINED'">
                {{ this.$route.query.Status }}
              </font>
              <font color="black" v-else>
                {{ this.$route.query.Status }}
              </font>
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.totalAmount"
            >
              <b><a-lang value="total-amount"> </a-lang>:</b
              >{{ this.$route.query.totalAmount }}
            </div>
            <div
              class="d-flex w-100 justify-content-between"
              v-if="$route.query.currency"
            >
              <b><a-lang value="currency"> </a-lang>:</b
              >{{ this.$route.query.currency }}
            </div>
          </span>
        </div>
        <br />
        <div class="casino-btn text-center">
          <a href="#/" class="btn-4 yellow-btn">
            <a-lang
              value="start-playing"
              v-if="response == 'success' && !error"
            >
            </a-lang>
            <a-lang value="go-back-to-home" v-else> </a-lang>
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
  props: ["response"],
  data() {
    return {
      error: false,
    };
  },
  created() {
    //if (this.response == "success" || this.response == "failure")
    //  this.nuveiProcess();
    if (this.$route.query.Status == "APPROVED")
      this.alert = {
        type: "alert-warning",
        message:
          this.getLocate("nuvei-success") +
          "<br/><br/>" +
          this.getLocate("nuvei-success-1"),
      };
  },
  methods: {
    /*
    nuveiProcess() {
      this.loading = true;
      let formData = new FormData();
      formData.append("ppp_status", this.$route.query.ppp_status);
      if (this.$route.query.cardCompany)
        formData.append("cardCompany", this.$route.query.cardCompany);
      if (this.$route.query.nameOnCard)
        formData.append("nameOnCard", this.$route.query.nameOnCard);
      if (this.$route.query.currency)
        formData.append("currency", this.$route.query.currency);
      if (this.$route.query.PPP_TransactionID)
        formData.append(
          "PPP_TransactionID",
          this.$route.query.PPP_TransactionID
        );
      if (this.$route.query.productId)
        formData.append("productId", this.$route.query.productId);
      if (this.$route.query.customData)
        formData.append("customData", this.$route.query.customData);
      if (this.$route.query.payment_method)
        formData.append("payment_method", this.$route.query.payment_method);
      if (this.$route.query.responseTimeStamp)
        formData.append(
          "responseTimeStamp",
          this.$route.query.responseTimeStamp
        );
      if (this.$route.query.message)
        formData.append("message", this.$route.query.message);
      if (this.$route.query.Error)
        formData.append("Error", this.$route.query.Error);
      if (this.$route.query.userPaymentOptionId)
        formData.append(
          "userPaymentOptionId",
          this.$route.query.userPaymentOptionId
        );
      if (this.$route.query.Status)
        formData.append("Status", this.$route.query.Status);
      if (this.$route.query.ClientUniqueID)
        formData.append("ClientUniqueID", this.$route.query.ClientUniqueID);
      if (this.$route.query.ExErrCode)
        formData.append("ExErrCode", this.$route.query.ExErrCode);
      if (this.$route.query.ErrCode)
        formData.append("ErrCode", this.$route.query.ErrCode);
      if (this.$route.query.cardCompany)
        formData.append("AuthCode", this.$route.query.AuthCode);
      if (this.$route.query.ReasonCode)
        formData.append("ReasonCode", this.$route.query.ReasonCode);
      if (this.$route.query.Token)
        formData.append("Token", this.$route.query.Token);
      if (this.$route.query.tokenId)
        formData.append("tokenId", this.$route.query.tokenId);
      if (this.$route.query.totalAmount)
        formData.append("totalAmount", this.$route.query.totalAmount);
      if (this.$route.query.TransactionID)
        formData.append("TransactionID", this.$route.query.TransactionID);
      if (this.$route.query.dynamicDescriptor)
        formData.append(
          "dynamicDescriptor",
          this.$route.query.dynamicDescriptor
        );
      if (this.$route.query.uniqueCC)
        formData.append("uniqueCC", this.$route.query.uniqueCC);
      if (this.$route.query.orderTransactionId)
        formData.append(
          "orderTransactionId",
          this.$route.query.orderTransactionId
        );
      if (this.$route.query.item_amount_1)
        formData.append("item_amount_1", this.$route.query.item_amount_1);
      if (this.$route.query.item_quantity_1)
        formData.append("item_quantity_1", this.$route.query.item_quantity_1);

      formData.append(
        "advanceResponseChecksum",
        this.$route.query.advanceResponseChecksum
      );
      this.$http
        .post("deposit/nuvei/response", formData)
        .then((response) => {
          console.log(response.data);
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result != 0) {
            this.error = true;
            this.alert = {
              type: "alert-success",
              message: this.getLocate(result.description),
            };
          } else {
            this.error = false;
            this.$emit("balance");
          }
        })
        .catch((response) => {
          this.error = true;
          this.showError(response);
        })
        .finally(() => {
          this.loading = false;
        });
    },*/
  },
};
</script>