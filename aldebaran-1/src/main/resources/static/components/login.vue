<template>
  <div
    class="modal fade"
    id="loginModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="loginModalLabel"
    aria-hidden="true"
  >
    <div
      class="modal-dialog"
      role="document"
      style="max-width: 2024px; margin: 0px"
    >
      <div class="modal-content modal-custom-content">
        <modal-header></modal-header>
        <h5
          class="modal-custom-title text-center"
          id="loginModalLabel"
          v-if="pageView == 'login'"
        >
          {{ getLocate("start-session-login") }}
        </h5>
        <div
          v-if="pageView == 'login'"
          class="modal-custom-text"
          style="font-family: 'Montserrat'; margin: 0 auto"
        >
          {{ getLocate("choose-login-method") }}
        </div>
        <h5
          class="modal-custom-title text-center"
          id="loginModalLabel"
          v-else-if="pageView == 'signUp'"
        >
          <a-lang value="sign-up"> </a-lang>
        </h5>
        <h5
          class="modal-custom-title text-center"
          id="loginModalLabel"
          v-else-if="pageView == 'passwordDimenticata'"
        >
          <a-lang value="forgot-password"> </a-lang>
        </h5>
        <h5
          class="modal-custom-title text-center"
          id="loginModalLabel"
          v-if="pageView == 'agreementsTerms'"
        >
          <a-lang value="agreementsTermsTitle"> </a-lang>
        </h5>
        <h5
          class="modal-custom-title text-center"
          id="loginModalLabel"
          v-if="pageView == 'secretQuestions'"
        >
          <a-lang value="secret-questions"> </a-lang>
        </h5>
        <h5
          class="modal-custom-title text-center"
          id="loginModalLabel"
          v-if="pageView == 'privacyPolicy'"
        >
          <a-lang value="privacyPolicyTitle"> </a-lang>
        </h5>
        <div
          v-if="
            pageView == 'agreementsTerms' ||
            pageView == 'privacyPolicy' ||
            pageView == 'secretQuestions'
          "
        >
          <button
            type="button"
            class="close text-white"
            @click="
              clearSecretAnswer(pageView);
              changePage('signUp');
            "
            aria-label="Close"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>

        <div
          class="modal-body subscribe sub-form"
          v-if="pageView == 'login'"
          style="text-align: center"
        >
          <div class="modal-custom-container">
            <span
              id="login-document"
              @click="changeViewLogin('document')"
              class="modal-custom-span-active"
              >{{ getLocate("login-with-doc") }}</span
            >
            <span id="login-email" @click="changeViewLogin('email')">{{
              getLocate("login-with-email")
            }}</span>
          </div>
          <form id="loginForm" novalidate @submit.prevent="login()">
            <div class="form-group modal-form-container">
              <span
                v-if="loginBody == 'document'"
                class="modal-custom-floating-label"
                >{{ getLocate("document-number") }}</span
              >
              <span v-else class="modal-custom-floating-label">{{
                getLocate("email")
              }}</span>
              <div class="input-group mb-3">
                <input
                  v-model="loginUser"
                  maxlength="100"
                  type="text"
                  class="form-control modal-custom-input"
                  aria-label="Username"
                  aria-describedby="basic-addon1"
                  :placeholder="
                    loginBody == 'document'
                      ? 'Es: 09876543210'
                      : 'Es: uriveral@correo.com'
                  "
                  required
                  @keyup.enter="login()"
                />
                <div style="z-index: 200" class="input-group-append newsletter">
                  <div class="input-group-text modal-custom-input-group-text">
                    <i
                      v-if="loginBody == 'document'"
                      class="fa fa-user"
                      style="color: #142984"
                    ></i>
                    <i v-else class="fa fa-envelope" style="color: #142984"></i>
                  </div>
                </div>
              </div>
              <span class="modal-custom-floating-label">{{
                getLocate("password")
              }}</span>
              <div class="input-group mb-3">
                <input
                  maxlength="64"
                  v-model="loginPassword"
                  :type="showPass ? 'text' : 'password'"
                  class="form-control modal-custom-input"
                  aria-label="Password"
                  required
                  @keyup.enter="login()"
                />
                <div
                  style="cursor: pointer; z-index: 200"
                  class="input-group-append newsletter"
                  @click="showPass = !showPass"
                >
                  <div class="input-group-text modal-custom-input-group-text">
                    <i
                      :class="showPass ? 'fa fa-unlock' : 'fa fa-lock'"
                      style="color: #142984"
                    ></i>
                  </div>
                </div>
              </div>
            </div>
          </form>
          <div
            style="display: flex; justify-content: center; align-items: center"
          >
            <a-lang
              value="login-2"
              style="font-family: 'Montserrat'; font-size: 14px; color: #eaebf1"
            ></a-lang>
            <button
              @click="changePage('passwordDimenticata')"
              class="btn btn-link"
              style="
                font-family: 'Montserrat Bold';
                font-size: 14px;
                margin-top: 2px;
              "
            >
              <a-lang value="recover"></a-lang>
            </button>
          </div>
          <a-alert
            v-if="error"
            @close="error = false"
            :alert="{
              type: 'alert-danger',
              message: this.errorMessage,
            }"
          ></a-alert>
          <a-alert
            v-if="success"
            @close="success = false"
            :alert="{
              type: 'alert-success',
              message: this.getLocate('login-3'),
            }"
          ></a-alert>
        </div>
        <div
          class="modal-body subscribe sub-form"
          v-else-if="pageView == 'signUp'"
        >
          <form id="signUpForm" novalidate>
            <div class="form-group">
              <div class="input-group mb-3">
                <input
                  id="firstName"
                  maxlength="150"
                  name="firstName"
                  v-model="firstName"
                  class="form-control"
                  required
                  @keyup="
                    validateGenericField(
                      'firstName',
                      firstName,
                      validateNameSurname,
                      null
                    )
                  "
                />
                <span class="floating-label">{{
                  getLocate("first-name") + " *"
                }}</span>
              </div>
              <div class="input-group mb-3">
                <input
                  id="lastName"
                  maxlength="150"
                  name="lastName"
                  v-model="lastName"
                  class="form-control"
                  required
                  @keyup="
                    validateGenericField(
                      'lastName',
                      lastName,
                      validateNameSurname,
                      null
                    )
                  "
                />
                <span class="floating-label">{{
                  getLocate("last-name") + " *"
                }}</span>
              </div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label
                    class="input-group-text form-select"
                    for="document-type"
                  >
                    <a-lang value="document-type"></a-lang>
                    *
                  </label>
                </div>
                <select
                  class="custom-select form-select"
                  id="document-type"
                  v-model="documentType"
                >
                  <option
                    v-for="dt in documentTypes"
                    :key="dt.code"
                    :value="dt.code"
                  >
                    {{ dt.description }}
                  </option>
                </select>
              </div>
              <div class="input-group mb-3">
                <input
                  maxlength="150"
                  name="documentNumber"
                  id="documentNumber"
                  v-model="documentNumber"
                  class="form-control"
                  @keyup="
                    validateGenericField(
                      'documentNumber',
                      documentNumber,
                      documentType == 'CI' || documentType == 'CE'
                        ? validateCedula
                        : validatePassport,
                      null
                    )
                  "
                  required
                />
                <span class="floating-label">{{
                  getLocate("document-number") + " *"
                }}</span>
              </div>
              <div class="input-group mb-3">
                <input
                  maxlength="150"
                  name="mobile"
                  id="mobile"
                  v-model="mobile"
                  class="form-control"
                  required
                  @keyup="
                    validateGenericField(
                      'mobile',
                      mobile,
                      validateMobilePhone,
                      null
                    )
                  "
                />
                <span class="floating-label">{{
                  getLocate("mobile") + " *"
                }}</span>
              </div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label class="input-group-text form-select" for="province">
                    <a-lang value="province"></a-lang>
                    *
                  </label>
                </div>
                <select
                  class="custom-select form-select"
                  id="province"
                  v-model="province"
                >
                  <option v-for="p in provinces" :key="p.id" :value="p.id">
                    {{ p.name }}
                  </option>
                </select>
              </div>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label class="input-group-text form-select" for="city">
                    <a-lang value="city"></a-lang>
                    *
                  </label>
                </div>
                <select
                  class="custom-select form-select"
                  id="city"
                  v-model="city"
                  required
                >
                  <option
                    v-for="c in cityByProvince"
                    :key="c.id"
                    :value="c.name"
                  >
                    {{ c.name }}
                  </option>
                </select>
              </div>
              <div class="input-group mb-3">
                <input
                  name="birthdate"
                  v-model="birthDate"
                  id="birthdate"
                  class="form-control padding-date"
                  type="date"
                  required
                  @keyup="
                    validateGenericField(
                      'birthdate',
                      birthDate,
                      underAgeValidate,
                      null
                    )
                  "
                />
                <span class="floating-label-date">{{
                  getLocate("birth-date") + " *"
                }}</span>
              </div>
              <div class="input-group mb-3">
                <input
                  maxlength="100"
                  name="username"
                  id="username"
                  v-model="username"
                  class="form-control"
                  required
                  @keyup="
                    validateGenericField(
                      'username',
                      username,
                      validateEmail,
                      null
                    )
                  "
                />
                <span class="floating-label">{{ "Email *" }}</span>
              </div>
              <div class="input-group mb-3">
                <input
                  name="password"
                  id="password"
                  v-model="password"
                  :type="showPass1 ? 'text' : 'password'"
                  class="form-control"
                  required
                  @keyup="
                    validateGenericField(
                      'password',
                      password,
                      validatePassword,
                      null
                    )
                  "
                />
                <div
                  style="cursor: pointer; z-index: 200"
                  class="input-group-append newsletter"
                  @click="showPass1 = !showPass1"
                >
                  <div
                    class="input-group-text"
                    style="
                      border-top-right-radius: 25px !important;
                      border-bottom-right-radius: 25px !important;
                    "
                  >
                    <i :class="showPass1 ? 'fa fa-eye' : 'fa fa-eye-slash'"></i>
                  </div>
                </div>
                <span class="floating-label">{{
                  getLocate("password") + " *"
                }}</span>
              </div>
              <div class="input-group mb-3">
                <input
                  id="registrationCode"
                  maxlength="150"
                  name="registrationCode"
                  v-model="registrationCode"
                  class="form-control"
                  :placeholder="getLocate('registration-code')"
                />
              </div>
              <button
                @click="changePage('secretQuestions')"
                href="#"
                class="btn btn-link-alt"
                style="text-align: left; text-decoration: underline"
              >
                - <a-lang value="secret-questions"></a-lang> ({{
                  secretAnswerRespond
                }}/{{ secretQuestions.length }})
                <i
                  v-if="secretAnswerRespond > 2"
                  class="fa fa-check"
                  style="color: #fff; position: relative; left: 5px"
                ></i>
                <br />
                <span class="sec-question">
                  <a-lang value="secret-questions-1"></a-lang>
                </span>
              </button>
            </div>
          </form>
          <button @click="changePage('login')" href="#" class="btn btn-link">
            <a-lang value="login-4"></a-lang>
          </button>
          <div
            class="alert alert-danger"
            role="alert"
            v-if="errors && errors.length > 0"
          >
            <span v-for="error in errors" :key="error">
              - {{ error }} <br />
            </span>
          </div>
        </div>
        <!-- MODAL PASSWORD DIMENTICATA -->
        <div
          class="modal-body subscribe sub-form"
          v-else-if="pageView == 'passwordDimenticata'"
        >
          <div class="form-group modal-form-container">
            <span class="modal-custom-floating-label">{{
              getLocate("email")
            }}</span>
            <div class="input-group mb-3">
              <input
                name="email"
                v-model="email"
                :placeholder="'Es: uriveral@correo.com'"
                class="form-control modal-custom-input"
              />
              <div style="z-index: 200" class="input-group-append newsletter">
                <div class="input-group-text modal-custom-input-group-text">
                  <i class="fa fa-envelope" style="color: #142984"></i>
                </div>
              </div>
            </div>
          </div>
          <div class="alert alert-danger" role="alert" v-if="error">
            <b>{{ errorMessage }}</b>
          </div>
          <div class="alert alert-success" role="alert" v-if="changePassword">
            <b>
              <a-lang value="login-5"></a-lang>
            </b>
          </div>
        </div>
        <div
          class="modal-body subscribe sub-form"
          v-else-if="pageView == 'passwordDimenticataSuccess'"
        >
          <div class="form-group modal-form-container">
            <img
              src="/images/mail_letter.png"
              style="width: 150px; margin-top: -30px"
            />
            <span
              class="modal-custom-title text-center"
              style="margin: 0 !important"
              >{{ getLocate("mail-sent") }}</span
            >
            <p style="font-size: 20px">
              <a-lang value="mail-check-1"></a-lang>
              <b>{{ email }}</b>
              <a-lang value="mail-check-2"></a-lang>
            </p>
          </div>
        </div>
        <!-- ------- -->
        <!-- MODAL SECRET QUESTIONS -->
        <div
          class="modal-body subscribe sub-form"
          v-if="pageView == 'secretQuestions'"
          style="text-align: justify"
        >
          <span class="sec-question">
            <a-lang value="secret-questions-1"></a-lang><br />
            {{ secretAnswerRespond }}/{{ secretQuestions.length }}
            <a-lang value="secret-questions-2"></a-lang>
            <i
              v-if="secretAnswerRespond > 2"
              class="fa fa-check"
              style="color: #fff; position: relative; left: 5px"
            ></i>
          </span>
          <br /><br />
          <div v-for="(e, index) in secretQuestions" :key="index">
            <div class="form-group">
              <div class="input-group mb-3">
                <input
                  maxlength="150"
                  name="sec_answer"
                  v-model="secretAnswer[index]"
                  class="form-control"
                  required
                />
                <i
                  v-if="secretAnswer[index] && secretAnswer[index].length > 0"
                  class="fa fa-check"
                  style="color: #fff; position: relative; top: 16px; left: 5px"
                ></i>
                <i
                  v-else
                  class="fa fa-check"
                  style="
                    color: #181434;
                    position: relative;
                    top: 16px;
                    left: 5px;
                  "
                ></i>
                <span
                  class="floating-label"
                  v-html="secretQuestions[index].content"
                ></span>
              </div>
            </div>
          </div>
        </div>
        <!-- ------- -->
        <!-- MODAL AGREEMENTS TERMS -->
        <div
          class="modal-body subscribe sub-form"
          v-if="pageView == 'agreementsTerms'"
          style="text-align: justify"
        >
          <cms-content
            group="privacy policy"
            name="Agreements terms"
            category="agreements terms"
          ></cms-content>
        </div>
        <!-- ------- -->
        <!-- MODAL PRIVACY POLICY TERMS -->
        <div
          class="modal-body subscribe sub-form"
          v-if="pageView == 'privacyPolicy'"
          style="text-align: justify"
        >
          <cms-content
            group="privacy policy"
            name="Privacy policy"
            category="privacy policy"
          ></cms-content>
        </div>
        <!-- ------- -->
        <!-- AGREEMENTS TERMS CHECKBOX-->
        <div class="container mb-2" v-if="pageView == 'signUp'">
          <div class="row" style="flex-wrap: inherit">
            <div class="col-1"></div>
            <div class="col-1 text-right">
              <input
                class="form-check-input big-checkbox"
                type="checkbox"
                id="privacyAndTerms"
                required
                v-model="privacyAndTerms"
              />
            </div>
            <div class="col-10">
              <span class="text-white"
                ><a-lang value="agreements-1"></a-lang>
                <a href="#" @click="changePage('agreementsTerms')"
                  ><a-lang value="agreements-2"></a-lang
                ></a>
                <a-lang value="agreements-3"></a-lang>
                <a href="#" @click="changePage('privacyPolicy')">
                  <a-lang value="agreements-4"></a-lang>
                </a>
              </span>
            </div>
          </div>
        </div>
        <!-- ------- -->

        <!-- FOOTER -->
        <div
          class="modal-footer login_menu navbar-right nav-sign justify-content-center modal-custom-footer"
        >
          <a-loading :show="loading"></a-loading>
          <div
            class="modal-footer-button mb-3"
            v-if="!loading && pageView == 'login'"
          >
            <a @click="login()" href="javascript:void(0)"
              ><a-lang value="start-session-login"></a-lang
              ><i
                class="fa fa-sign-in fa-2x ml-2 pos-relative top--3"
                aria-hidden="true"
              ></i>
            </a>
          </div>
          <div
            class="modal-footer-button mb-2 reverse"
            v-if="!loading && pageView == 'login'"
          >
            <a href="javascript:void(0)" @click="location.href = 'signin'"
              ><a-lang value="create-account"></a-lang
              ><i
                class="fa fa-user-plus fa-2x ml-2 pos-relative top--3"
                aria-hidden="true"
              ></i
            ></a>
          </div>
          <div
            class="modal-footer-button"
            v-if="!loading && pageView == 'signUp'"
          >
            <a class="header-btn" @click="signUp()" href="javascript:void(0)"
              ><a-lang value="sign-up"></a-lang
            ></a>
          </div>
          <div
            class="modal-footer-button"
            v-if="!loading && pageView == 'passwordDimenticata'"
          >
            <a
              href="javascript:void(0)"
              @click="passwordDimenticata()"
              v-if="!changePassword"
              ><a-lang value="send-email"></a-lang
              ><i class="fa fa-paper-plane-o fa-2x ml-2 pos-relative top-3"></i>
            </a>
          </div>
          <div
            v-if="!loading && pageView == 'passwordDimenticata'"
            style="margin-top: 20px; margin-bottom: 10px"
          >
            <a-lang
              value="login-4"
              class="modal-custom-text"
              style="font-family: 'Montserrat SemiBold'; font-size: 14px"
            ></a-lang>
            <button class="btn btn-link" @click="changePage('login')">
              <a-lang
                value="start-session-login"
                style="font-family: 'Montserrat SemiBold'; font-size: 14px"
              ></a-lang>
            </button>
          </div>
          <div
            class="modal-footer-button"
            v-if="!loading && pageView == 'passwordDimenticataSuccess'"
          >
            <a
              href="javascript:void(0)"
              @click="changePage('login')"
              v-if="changePassword"
              ><a-lang value="continue"></a-lang>
            </a>
          </div>
          <div
            class="modal-footer-button"
            v-if="pageView == 'agreementsTerms' || pageView == 'privacyPolicy'"
          >
            <a
              class="btn-4 yellow-btn"
              @click="changePage('signUp')"
              href="javascript:void(0)"
            >
              <a-lang value="previous"></a-lang
            ></a>
          </div>
          <div
            class="modal-footer-button"
            v-if="pageView == 'secretQuestions' && secretAnswerRespond > 2"
          >
            <a
              class="btn-4 yellow-btn"
              @click="changePage('signUp')"
              href="javascript:void(0)"
            >
              <a-lang value="confirm-secret-answers"></a-lang
            ></a>
          </div>
        </div>
        <!-- FOOTER END -->
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["pageView"],
  data() {
    return {
      //generici
      error: false,
      errorMessage: "",
      errors: [],
      success: false,
      //login
      loginUser: "",
      loginPassword: "",

      showPass: false,
      showPass1: false,

      //registrazione
      username: "",
      password: "",
      firstName: "",
      lastName: "",
      age: "",
      mobile: "",
      province: "",
      city: "",
      country: "Ecuador",
      birthDate: "1985-01-01",
      secretQuestions: [],
      secretAnswer: [],
      registrationCode: "",
      taxcode: "",
      documentType: "",
      documentTypes: [],
      documentNumber: "",
      privacyAndTerms: false,
      newsletterLoteria: false,
      newsletterLotto: false,
      newsletterPozo: false,
      provinces: [],
      cities: [],
      email: "",
      changePassword: false,
      loginBody: "document",
    };
  },
  created() {
    //il valore iniziale di pageView dipende dal pulsante premuto su index html
    if (this.pageView == "signUp") {
      this.getProvinces();
      this.getCities();
      this.getDocumentTypes();
      this.getSecretQuestions();
    }
  },
  computed: {
    cityByProvince() {
      return this.cities.filter((c) => c.province_id == this.province);
    },
    secretAnswerRespond() {
      let num = 0;
      for (let i = 0; i < this.secretQuestions.length; i++) {
        if (this.secretAnswer[i] && this.secretAnswer[i].length > 0) num++;
      }
      return num;
    },
  },
  methods: {
    getSecretQuestions() {
      let formData = new FormData();
      formData.append("language", store.state.locate);
      formData.append("group", "secret questions");
      formData.append("category", "secret questions");
      this.$http
        .post("cms/text/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.secretQuestions = result.contents;
          } else {
            this.alert = { type: "alert-danger", message: result.description };
          }
        })
        .catch((response) => {
          this.showError(response);
        });
    },
    getDocumentTypes() {
      this.$http
        .get("documentTypes?lang=" + store.state.locate)
        .then((response) => {
          this.documentTypes = response.data.documentTypes;
          this.documentType = this.documentTypes[0].code;
        })
        .catch(() => {});
    },
    getProvinces() {
      this.$http
        .get("provinces")
        .then((response) => {
          this.provinces = response.data.provinces;
          this.province = this.provinces[0].id;
        })
        .catch(() => {});
    },
    getCities() {
      this.$http
        .get("cities")
        .then((response) => {
          this.cities = response.data.cities;
        })
        .catch((response) => {
          this.showError(response);
        });
    },
    getRegularExpressionDocumentTypes() {
      let documentType = this.documentTypes.find(
        (dt) => dt.code == this.documentType
      );
      if (documentType) return documentType.regularExpression;
      return null;
    },
    validateDocumentNumber() {
      let documentNumber = document.getElementById("documentNumber");

      if (
        ((this.documentType == "CI" || this.documentType == "CE") &&
          !this.validateCedula(this.documentNumber)) ||
        !this.validateGeneric(
          this.documentNumber,
          this.getRegularExpressionDocumentTypes()
        )
      ) {
        this.errors.push(this.getLocate("login-14"));
        documentNumber.classList.add("my-is-invalid");
      } else documentNumber.classList.remove("my-is-invalid");
    },
    validateGenericField(fieldId, field, funcValidate, messageError) {
      let fieldElement = document.getElementById(fieldId);
      if (!funcValidate(field)) {
        if (messageError) this.errors.push(messageError);
        fieldElement.classList.add("my-is-invalid");
      } else {
        fieldElement.classList.remove("my-is-invalid");
      }
    },
    login() {
      let loginForm = document.getElementById("loginForm");
      this.resetMessages();
      if (loginForm.checkValidity() === false) {
        this.error = true;
        this.errorMessage = this.getLocate("login-6");
        return;
      }
      if (this.loginBody == "document" && this.loginUser.includes("@")) {
        this.error = true;
        this.errorMessage = this.getLocate("login-14");
        return;
      } else if (
        this.loginBody == "email" &&
        this.validateEmail(this.loginUser) == false
      ) {
        this.error = true;
        this.errorMessage = this.getLocate("login-9");
        return;
      }

      let formData = new FormData();
      formData.append("username", this.loginUser.trim());
      formData.append("password", this.loginPassword);
      formData.append("externalip", store.state.externalIp);
      this.loading = true;
      this.$http
        .post("login", formData)
        .then((response) => {
          let result = response.data;
          if (result.messageKo != null && result.messageKo != "") {
            //error
            this.error = true;
            this.errorMessage = this.getLocate(result.messageKo);
            this.loading = false;
          } else {
            if (!this.isReloadOnly(this.$route.path)) location.href = "#";
            location.reload();
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
          this.loading = false;
        });
    },
    signUp() {
      this.errors = [];

      //check dei campi required
      let signUpForm = document.getElementById("signUpForm");
      if (signUpForm.checkValidity() === false) {
        signUpForm.classList.add("was-validated");
        this.errors.push(this.getLocate("login-6"));
        return;
      }

      //validiamo le domande segrete
      let secQuestionsJson = "";
      if (this.secretAnswerRespond > 2) {
        let sec_questions = {};
        for (let i = 0; i < this.secretQuestions.length; i++) {
          if (this.secretAnswer[i] && this.secretAnswer[i].length > 0)
            sec_questions[this.removeTag(this.secretQuestions[i].content)] =
              this.secretAnswer[i];
        }
        secQuestionsJson = JSON.stringify(sec_questions);
      } else {
        this.errors.push(this.getLocate("secret-questions-1"));
      }

      //controllo sui campi particolari
      this.validateGenericField(
        "firstName",
        this.firstName,
        this.validateNameSurname,
        this.getLocate("login-15")
      );

      this.validateGenericField(
        "lastName",
        this.lastName,
        this.validateNameSurname,
        this.getLocate("login-16")
      );

      this.validateGenericField(
        "mobile",
        this.mobile,
        this.validateMobilePhone,
        this.getLocate("login-19")
      );

      this.validateGenericField(
        "username",
        this.username,
        this.validateEmail,
        this.getLocate("login-9")
      );

      this.validateDocumentNumber();

      this.validateGenericField(
        "password",
        this.password,
        this.validatePassword,
        this.getLocate("password-confirmation-1")
      );

      this.validateGenericField(
        "birthdate",
        this.birthDate,
        this.underAgeValidate,
        this.getLocate("login-12") +
          store.state.underAge +
          " " +
          this.getLocate("login-13")
      );

      if (!this.privacyAndTerms)
        this.errors.push(this.getLocate("policiesAccept"));

      //se ci sono problemi nella validazione dei campi esco senza eseguire la chiamata
      if (this.errors && this.errors.length > 0) return;

      this.loading = true;
      this.$http
        .post("register", {
          firstname: this.firstName.trim(),
          lastname: this.lastName.trim(),
          taxcode: this.documentNumber,
          address: null,
          city: this.city,
          zip: null,
          country: this.country,
          birth_date: this.birthDate,
          email: this.username.trim(),
          password: this.password,
          sec_answer: "",
          sec_question: "",
          language: store.state.locate,
          currency: store.state.currency,
          document_id: this.documentNumber,
          document_type: this.documentType,
          mobile: this.mobile.trim(),
          sec_questions: secQuestionsJson,
          registration_code: this.registrationCode.trim(),
          externalip: store.state.externalIp,
        })
        .then((response) => {
          if (response.data.result == 0) {
            this.resetMessages();
            this.success = true;
            this.changePage("login");
          } else {
            this.errors = [this.getLocate(response.data.description)];
          }
        })
        .catch((response) => {
          this.showError(response);
          this.errors = [this.getLocate("login-7")];
        })
        .finally(() => {
          this.loading = false;
        });
    },
    passwordDimenticata() {
      this.resetMessages();
      if (
        this.email != null &&
        this.email != "" &&
        this.validateEmail(this.email)
      ) {
        let formData = new FormData();
        formData.append("email", this.email);
        this.loading = true;
        this.$http
          .post("reserve", formData)
          .then((response) => {
            var result = response.data;
            this.forceLogout(result.result);
            if (result.messageKo != null && result.messageKo != "") {
              this.error = true;
              this.errorMessage = this.getLocate(result.messageKo);
            } else {
              this.changePassword = true;
              this.changePage("passwordDimenticataSuccess");
            }
          })
          .catch((response) => {
            this.showError(response);
            this.error = true;
            this.errorMessage = this.getLocate("login-7");
          })
          .finally(() => {
            this.loading = false;
          });
      } else {
        this.error = true;
        this.errorMessage = this.getLocate("login-9");
      }
    },
    removeTag(content) {
      return content.replace("<p>", "").replace("</p>", "");
    },
    clearSecretAnswer(page) {
      if (page == "secretQuestions") {
        this.secretAnswer = [];
      }
    },
    changeViewLogin(view) {
      //edit css for login view selected option
      this.resetMessages();
      $("#login-" + view).addClass("modal-custom-span-active");
      if (view == "document") {
        $("#login-email").removeClass("modal-custom-span-active");
      } else {
        $("#login-document").removeClass("modal-custom-span-active");
      }
      this.loginBody = view;
    },
    resetMessages() {
      this.error = false;
      this.success = false;
      this.errorMessage = "";
    },
    changePage(page) {
      this.resetMessages();
      this.pageView = page;
    },
  },
};
</script>