<template>
  <div v-if="!loading">
    <!--USER INFO-->
    <div>
      <div class="row">
        <div class="col-2">
          <img src="images/profile_avatar.png" alt="" class="profile-avatar" />
        </div>
        <div class="col-9">
          <span v-if="store.state.lastlogin && store.state.lastlogin != 'null'" class="profile-text-label"
            :style="isDesktop() ? 'margin-left: -15px' : 'margin-left: 0px'"><a-lang
              value="last-access"></a-lang></span>
          <span class="profile-text-label" v-if="store.state.lastlogin && store.state.lastlogin != 'null'">{{
            formatDateYYYYMMDDHHmmss(store.state.lastlogin) }}</span>
        </div>
        <div class="col-md-2 col-3"></div>
        <div class="col-9" :style="isDesktop()
            ? 'margin-left: -20px; margin-top: -15px'
            : 'margin-left: -28px; margin-top: -5px'
          ">
          <b class="profile-label">
            {{ store.state.firstname }} {{ store.state.lastname }}
          </b>
          <br />
          <div class="d-flex align-items-center">
            <span class="profile-label-normal" style="margin-top: 0">{{
              store.state.email
            }}</span>
            <i :class="'fa fa-pencil-square-o ml-2'" style="cursor: pointer; color: #202659"
              @click="$('#changeEmail').modal('show')"></i>
          </div>
        </div>
      </div>
      <br />
      <div style="height: 76vh; overflow-y: auto; overflow-x: hidden">
        <div class="row">
          <div class="col-md-7 col-12">
            <div class="row mb-4 ml-3 mr-3">
              <div class="col">
                <b class="profile-label">
                  <a-lang value="document-number"></a-lang></b>
                <div class="loteria-text-secondary">
                  <div class="input-group">
                    <span class="profile-input-icon">
                      <img src="images/profile_documento_icon.png" alt="dinero" />
                    </span>
                    <input id="documentNumber" maxlength="20" name="documentNumber" class="form-control profile-input"
                      style="color: #5e5e5e !important" v-model="userDetail.document_id" readonly />
                  </div>
                </div>
              </div>
            </div>
            <div class="row mb-4 ml-3 mr-3">
              <div class="col">
                <b class="profile-label">
                  <a-lang value="birth-date"></a-lang></b>
                <div class="loteria-text-secondary">
                  <div class="input-group">
                    <span class="profile-input-icon">
                      <img src="images/profile_gift_icon.png" alt="dinero" />
                    </span>
                    <input id="birthDate" maxlength="10" name="birthDate" class="form-control profile-input" readonly
                      v-model="userDetail.birthDateFormatted" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row mb-4 ml-3 mr-3">
              <div class="col">
                <b class="profile-label"> <a-lang value="mobile"></a-lang></b>
                <div class="loteria-text-secondary">
                  <div class="input-group">
                    <span class="profile-input-icon">
                      <img src="images/profile_phone_icon.png" alt="dinero" />
                    </span>
                    <input id="phoneNumber" maxlength="20" name="phoneNumber" class="form-control profile-input"
                      readonly v-model="userDetail.mobile" />
                    <span class="profile-input-icon" style="right: 10px; height: 33px">
                      <i :class="'fa fa-pencil-square-o'" style="cursor: pointer"
                        @click="$('#changePhone').modal('show')"></i>
                    </span>
                  </div>
                </div>
              </div>
            </div>
            <div class="row mb-4 ml-3 mr-3">
              <div class="col">
                <b class="profile-label"> <a-lang value="city"></a-lang></b>
                <div class="loteria-text-secondary">
                  <div class="input-group">
                    <span class="profile-input-icon">
                      <img src="images/profile_maps_icon.png" alt="dinero" />
                    </span>
                    <input id="city" maxlength="50" name="city" class="form-control profile-input"
                      style="color: #5e5e5e !important" readonly v-model="userDetail.city" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row mb-4 ml-3 mr-3">
              <div class="col">
                <b class="profile-label"> <a-lang value="password"></a-lang></b>
                <div class="loteria-text-secondary">
                  <div class="input-group">
                    <span class="profile-input-icon">
                      <img src="images/profile_password_icon.png" alt="dinero" />
                    </span>
                    <input id="password" maxlength="50" name="password" class="form-control profile-input" readonly
                      v-model="userDetail.hiddenPassword" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row mb-4 ml-3 mr-3">
              <div class="col-md col-12 justify-content-center">
                <button class="profile-button" @click="CambiaPassword()">
                  <i class="fa fa-user-secret" aria-hidden="true"></i>&nbsp;<a-lang value="change-password"></a-lang>
                </button>
              </div>
            </div>
          </div>
          <div class="col-md-5 col-12">
            <div v-if="
              isSmarticoMissionUser() &&
              document.getElementById('__smarticoPublicLabelKey') &&
              document.getElementById('__smarticoPublicLabelKey').value
            " class="row text-center justify-content-center" style="height: 85px">
              <div class="col">
                <div class="profile-card-large-blue" style="cursor: pointer" @click="_smartico.dp('dp:gf')">
                  <img src="images/rocket.png" alt="rocket" class="profile-card-img text-left" />
                  <div class="row" style="margin-top: -20px; margin-left: -15px">
                    <div class="col-12">
                      <span class="profile-card-large-text text-left" style="
                          color: #fff;
                          font-family: 'Montserrat';
                          font-size: 14px;
                        "><a-lang value="opened-mission"></a-lang></span>
                    </div>
                  </div>
                  <div class="row" style="margin-top: 0px; margin-left: -15px">
                    <div class="col-12">
                      <span class="profile-card-large-text" style="
                          color: #fff;
                          font-family: 'Montserrat Bold';
                          font-size: 16px;
                        ">Bettor Master</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row text-center justify-content-center">
              <div>
                <div class="profile-card-small">
                  <img src="images/profile_icon_dinero.png" alt="dinero" class="profile-card-img" />
                  <div class="row">
                    <div class="col-12">
                      <span class="profile-card-text"><a-lang value="cash"></a-lang></span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-12 text-center">
                      <div class="d-flex">
                        <span v-if="cash != null" class="profile-card-text profile-card-text-large">{{
                          currencyView(currency)
                          }}{{ separateDecimalCash(cash).integer }}</span>
                        <span v-if="cash != null" class="profile-card-text profile-card-text-small">.{{
                          separateDecimalCash(cash).decimal }}</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="profile-card-small">
                  <img src="images/profile_icon_dinero.png" alt="dinero" class="profile-card-img" />
                  <div class="row">
                    <div class="col-12">
                      <span class="profile-card-text"><a-lang value="withdrawable"></a-lang></span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-12 text-center">
                      <div class="d-flex">
                        <span v-if="cash != null" class="profile-card-text profile-card-text-large">{{
                          currencyView(currency)
                          }}{{
                            separateDecimalCash(
                              formatterNumber(cashableCredits, true)
                            ).integer
                          }}</span>
                        <span v-if="cash != null" class="profile-card-text profile-card-text-small">.{{
                          separateDecimalCash(
                            formatterNumber(cashableCredits, true)
                          ).decimal
                        }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row text-center" style="height: 85px">
              <div class="col">
                <div class="profile-card-large">
                  <img src="images/profile_icon_dinero.png" alt="dinero" class="profile-card-img" />
                  <div class="row" style="margin-top: -20px; margin-left: -15px">
                    <div class="col-12">
                      <span class="profile-card-large-text"><a-lang value="promotional"></a-lang></span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-12 text-center">
                      <div class="d-flex justify-content-center">
                        <span v-if="cash != null" class="profile-card-text profile-card-text-large" style="top: 0px">{{
                          currencyView(currency)
                          }}{{
                            separateDecimalCash(
                              formatterNumber(promotionalCredits, true)
                            ).integer
                          }}</span>
                        <span v-if="cash != null" class="profile-card-text profile-card-text-small" style="top: 0px">.{{
                          separateDecimalCash(
                            formatterNumber(promotionalCredits, true)
                          ).decimal
                        }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row text-center" style="height: 85px">
              <div class="col">
                <div class="profile-card-large">
                  <img src="images/profile_icon_dinero.png" alt="dinero" class="profile-card-img" />
                  <div class="row" style="margin-top: -20px; margin-left: -15px">
                    <div class="col-12">
                      <span class="profile-card-large-text"><a-lang value="promotional-bet"></a-lang></span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-12 text-center">
                      <div class="d-flex justify-content-center">
                        <span v-if="cash != null" class="profile-card-text profile-card-text-large" style="top: 0px">{{
                          currencyView(currency)
                          }}{{
                            separateDecimalCash(
                              formatterNumber(promotionalCreditsBet, true)
                            ).integer
                          }}</span>
                        <span v-if="cash != null" class="profile-card-text profile-card-text-small" style="top: 0px">.{{
                          separateDecimalCash(
                            formatterNumber(promotionalCreditsBet, true)
                          ).decimal
                        }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div style="margin-top: 10px" class="alert alert-success" role="alert" v-show="successCp">
          <span v-html="successMessageCp"></span>
          <button type="button" class="close" aria-label="Close" @click="successCp = false">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div style="margin-top: 10px" class="alert alert-danger" role="alert" v-show="errorCp">
          <span>{{ errorMessageCp }}</span>
          <button type="button" class="close" aria-label="Close" @click="errorCp = false">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal fade" id="changeEmail" tabindex="-1" role="dialog" aria-labelledby="changeEmailDetails"
          aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content modal-custom-content back-dark">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">
                  <p>
                    <a-lang value="change-email"> </a-lang>
                  </p>
                </h5>
                <button type="button" class="close text-white" @click="
                  $('#changeEmail').modal('hide');
                newEmail = '';
                alertEmailError.message = '';
                " aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body modal-custom-body d-flex align-items-center justify-content-center"
                style="flex-direction: column">
                <span class="floating-span" style="align-self: start">
                  {{ getLocate("email-insert-new") }}
                </span>
                <input v-model="newEmail" class="form-control modal-custom-input"
                  placeholder="E.j.: art_calle@correo.com" />
                <button type="button" class="modal-footer-button mt-3" @click="changeEmail()">
                  Change email
                </button>
                <a-alert :alert="alertEmailError"></a-alert>
              </div>
            </div>
          </div>
        </div>
        <div class="modal fade" id="changePhone" tabindex="-1" role="dialog" aria-labelledby="changePhoneDetails"
          aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content modal-custom-content back-dark">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">
                  <p>
                    <a-lang value="change-phone"> </a-lang>
                  </p>
                </h5>
                <button type="button" class="close text-white" @click="
                  $('#changePhone').modal('hide');
                newPhone = '';
                alertPhoneError.message = '';
                " aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body modal-custom-body d-flex align-items-center justify-content-center"
                style="flex-direction: column">
                <span class="floating-span" style="align-self: start">
                  {{ getLocate("phone-insert-new") }}
                </span>
                <input v-model="newPhone" class="form-control modal-custom-input" placeholder="E.j.: 0999999999" />
                <button type="button" class="modal-footer-button mt-3" @click="changePhone()">
                  Change phone
                </button>
                <a-alert :alert="alertPhoneError"></a-alert>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--USER INFO END-->
  </div>
</template>

<script>
  module.exports = {
    mixins: [common],
    props: ["loading"],
    data() {
      return {
        //generici
        errorCp: false,
        errorMessageCp: "",
        successCp: null,
        successMessageCp: "",
        userDetail: {},

        //email
        newEmail: "",
        alertEmailError: { type: "alert-danger", message: "" },
        //phone
        newPhone: "",
        alertPhoneError: { type: "alert-danger", message: "" },

        //balances
        balances: [],
        currency: store.state.currency,
        cashableCredits: null,
        notCashableCredits: null,
        currencyWithdrawal: null,
        promotionalCredits: null,
        currencyPromotional: null,
        promotionalCreditsBet: null,
        currencyPromotionalBet: null,
        cash: null,
      };
    },
    created() {
      this.getUserInformation();
    },
    methods: {
      getUserInformation() {
        this.getUserDetail();
        this.getBalances();
      },
      getUserDetail() {
        this.changeLoading(true);
        let formData = new FormData();
        formData.append("usertoken", store.state.userId);
        this.$http
          .post("user-details", formData)
          .then((response) => {
            let result = response.data;
            this.forceLogout(result.result);
            if (result.result == 0) {
              this.userDetail = result.users[0];
              this.userDetail.birthDateFormatted = this.formatDateD(
                this.userDetail.birth_date
              );
              this.userDetail.hiddenPassword = "********";
              if (this.userDetail.status == 2) this.selfExcluded = true;
              else this.selfExcluded = false;
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
      //ottengo i balance per user id e session id
      getBalances() {
        let formData = new FormData();
        formData.append("usertoken", store.state.userId);
        formData.append("sessiontoken", store.state.sessionId);
        this.$http.post("balances", formData).then((response) => {
          let result = response.data;
          this.forceLogout(result.result);

          if (result.result == 0) {
            this.balances = result;
            this.balances.data.forEach((element) => {
              if (
                element.type != null &&
                element.type != "" &&
                element.type == "N"
              ) {
                this.notCashableCredits = element.amount;
                this.currency = element.currency;
              }
              if (
                element.type != null &&
                element.type != "" &&
                element.type == "C"
              ) {
                this.cashableCredits = element.amount;
                this.currencyWithdrawal = element.currency;
              }
              if (
                element.type != null &&
                element.type != "" &&
                element.type == "G"
              ) {
                this.promotionalCredits = element.amount;
                this.currencyPromotional = element.currency;
              }
              if (
                element.type != null &&
                element.type != "" &&
                element.type == "B" &&
                element.status == 1
              ) {
                this.promotionalCreditsBet = element.amount;
                this.currencyPromotionalBet = element.currency;
              } else {
                this.promotionalCreditsBet = 0;
                this.currencyPromotionalBet = element.currency;
              }
            });
            let value = this.cashableCredits + this.notCashableCredits;
            this.cash = this.formatterNumber(value, true);
          }
        });
      },
      CambiaPassword() {
        let formData = new FormData();
        formData.append("email", store.state.email);
        this.$http
          .post("reserve", formData)
          .then((response) => {
            var result = response.data;
            this.forceLogout(result.result);
            if (result.messageKo != null && result.messageKo != "") {
              this.errorCp = true;
              this.errorMessageCp = result.messageKo;
            } else {
              this.successCp = true;
              this.successMessageCp = this.getLocate("login-5");
            }
          })
          .catch((response) => {
            this.showError(response);
            this.errorCp = true;
            this.errorMessageCp = this.getLocate("login-7");
          });
      },
      changeEmail() {
        this.success = false;
        this.successMessage = "";
        if (!this.newEmail || this.newEmail == "") {
          this.alertEmailError.message = this.getLocate("email-not-empty");
          return;
        }
        if (!this.validateEmail(this.newEmail)) {
          this.alertEmailError.message = this.getLocate("email-not-valid");
          return;
        }
        let formData = new FormData();
        formData.append("email", this.newEmail);
        this.$http
          .post("email/reserve", formData)
          .then((response) => {
            let result = response.data;
            this.forceLogout(result.result);
            if (result.messageKo != null && result.messageKo != "") {
              this.alertEmailError.message = result.messageKo;
            } else {
              this.newEmail = "";
              this.alertEmailError.message = "";
              $("#changeEmail").modal("hide");
              this.success = true;
              this.successMessage = this.getLocate("email-change-sent");
            }
          })
          .catch((response) => {
            this.showError(response);
            this.alertEmailError.message = this.getLocate("email-error");
          });
      },
      changePhone() {
        this.success = false;
        this.successMessage = "";
        if (!this.newPhone || this.newPhone == "") {
          this.alertPhoneError.message = this.getLocate("phone-not-empty");
          return;
        }
        if (!this.validateMobilePhone(this.newPhone)) {
          this.alertPhoneError.message = this.getLocate("phone-not-valid");
          return;
        }
        let formData = new FormData();
        formData.append("phone", this.newPhone);
        this.$http
          .post("phone/change", formData)
          .then((response) => {
            let result = response.data;
            if (result.messageKo != null && result.messageKo != "") {
              this.alertPhoneError.message = result.messageKo;
            } else {
              this.newPhone = "";
              this.alertPhoneError.message = "";
              $("#changePhone").modal("hide");
              this.success = true;
              this.successMessage = this.getLocate("phone-changed");
              location.href = "#";
              location.reload();
            }
          })
          .catch((response) => {
            this.showError(response);
            this.alertPhoneError.message = this.getLocate("phone-error");
          });
      },
    },
  };
</script>
