<template>
  <div>
    <div
      class="modal fade"
      id="changePassword"
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
          <h5 class="modal-custom-title" id="exampleModalLabel">
            <a-lang value="change-password"> </a-lang>
          </h5>
          <div class="modal-body modal-custom-body">
            <div class="card back-dark" style="border: 0">
              <div class="card-body subscribe sub-form">
                <form novalidate>
                  <div class="form-group">
                    <span class="floating-span">
                      {{ getLocate("new-password") }}
                    </span>
                    <div class="input-group mb-3">
                      <input
                        maxlength="64"
                        v-model="newPassword"
                        :type="showPass ? 'text' : 'password'"
                        class="form-control modal-custom-input"
                        required
                      />
                      <div
                        style="cursor: pointer; z-index: 200"
                        class="input-group-append newsletter"
                        @click="showPass = !showPass"
                      >
                        <div class="input-group-text">
                          <i
                            :class="showPass ? 'fa fa-eye' : 'fa fa-eye-slash'"
                          ></i>
                        </div>
                      </div>
                    </div>
                    <span class="floating-span">{{
                      getLocate("confirm-password")
                    }}</span>
                    <div class="input-group mb-3">
                      <input
                        maxlength="64"
                        v-model="confirmPassword"
                        :type="showPass2 ? 'text' : 'password'"
                        class="form-control modal-custom-input"
                        required
                      />
                      <div
                        style="cursor: pointer; z-index: 200"
                        class="input-group-append newsletter"
                        @click="showPass2 = !showPass2"
                      >
                        <div class="input-group-text">
                          <i
                            :class="showPass2 ? 'fa fa-eye' : 'fa fa-eye-slash'"
                          ></i>
                        </div>
                      </div>
                    </div>
                  </div>
                </form>
                <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
              </div>
            </div>
          </div>

          <ul
            class="modal-footer modal-custom-footer login_menu navbar-right nav-sign justify-content-center"
          >
            <li>
              <a-loading :show="loading"></a-loading>
              <a
                class="modal-footer-button text-center"
                @click="changePassword()"
                v-if="!loading && !success"
                href="javascript:void(0)"
                ><a-lang value="apply" style="vertical-align: sub"> </a-lang
              ></a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <section class="confirm-page"></section>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["otp"],
  data() {
    return {
      success: false,
      error: false,
      errorMessage: "",
      newPassword: "",
      showPass: false,
      showPass2: false,
      confirmPassword: "",
      alert: { type: "", message: "" },
    };
  },
  mounted() {
    $("#changePassword").modal();
  },
  methods: {
    changePassword() {
      if (!(this.newPassword === this.confirmPassword)) {
        this.alert = {
          type: "alert-danger",
          message: this.getLocate("password-confirmation"),
        };
        return;
      }
      if (!this.validatePassword(this.newPassword)) {
        this.alert = {
          type: "alert-danger",
          message: this.getLocate("password-confirmation-1"),
        };
        return;
      }

      this.loading = true;
      let formData = new FormData();
      formData.append("otp", this.otp);
      formData.append("password", this.newPassword);
      this.$http
        .post("commit", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.alert = {
              type: "alert-danger",
              message: result.messageKo,
            };
          } else {
            this.success = true;
            this.alert = {
              type: "alert-success",
              message: this.getLocate("password-confirmation-2"),
            };
            setTimeout(() => {
              location.href = "#";
              location.reload();
            }, 2000);
          }
        })
        .catch(() => {
          this.showError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>
