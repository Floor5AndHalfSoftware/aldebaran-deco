<template>
  <div>
    <div
      class="modal fade"
      id="changeEmail"
      tabindex="-1"
      role="dialog"
      aria-labelledby="sessionDetailsLabel"
      aria-hidden="true"
      data-backdrop="static"
      data-keyboard="false"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content modal-custom-content back-dark">
          <modal-header></modal-header>
          <div class="modal-body">
            <h5 class="modal-custom-title text-center" id="exampleModalLabel">
              <a-lang value="change-email"> </a-lang>
            </h5>
            <div class="card back-dark">
              <div class="d-flex align-items-center justify-content-center">
                <span class="alert" :class="alert.type">{{
                  alert.message
                }}</span>
              </div>
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
  props: ["otp", "email", "userid", "hash"],
  data() {
    return {
      alert: { type: "", message: "" },
    };
  },
  mounted() {
    $("#changeEmail").modal("show");
    this.changeEmail();
  },
  methods: {
    changeEmail() {
      console.log("changeEmail", this.otp, this.email, this.userid, this.hash);
      let formData = new FormData();
      formData.append("otp", this.otp);
      formData.append("email", this.email);
      formData.append("userid", this.userid);
      formData.append("hash", this.hash);
      this.$http
        .post("email/commit", formData)
        .then((response) => {
          let result = response.data;
          if (result.messageKo != null && result.messageKo != "") {
            this.alert = {
              type: "alert-danger",
              message: this.getLocate("email-otp-invalid"),
            };
          } else {
            this.alert = {
              type: "alert-success",
              message: this.getLocate("email-changed"),
            };
            setTimeout(() => {
              this.logout();
            }, 2000);
          }
        })
        .catch(() => {
          this.showError(this.getLocate("login-7"));
        });
    },
  },
};
</script>
