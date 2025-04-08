<template>
  <div>
    <!-- contact-page Start -->
    <section id="contact-page" class="contact-page section">
      <br /><br /><br />
      <div class="container">
        <div class="row">
          <div class="col-md-6">
            <div class="heading" style="color: #fff">
              <h2>
                <a-lang value="contact-us"> </a-lang>
              </h2>
              <hr class="hr" />
            </div>
            <!--
            <div class="row">
              <div class="col-md-12">
                <div class="row">
                  <div class="col-lg-2 col-md-3 col-3">
                    <div class="location-icon">
                      <i class="fa fa-map-marker"></i>
                    </div>
                  </div>
                  <div class="col-lg-10 col-md-9 col-9">
                    <div class="location-txt">
                      <span>5236, Park Street Avenue, Newyork America</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <div class="row">
                  <div class="col-lg-2 col-md-3 col-3">
                    <div class="location-icon">
                      <i class="fa fa-phone"></i>
                    </div>
                  </div>
                  <div class="col-lg-10 col-md-9 col-9">
                    <div class="location-txt">
                      <span
                        >(+15) 152 125 32, (+35) 215 236 71 (02) 125 567. (02)
                        236 875</span
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>
            -->
            <div class="row">
              <div class="col-md-12">
                <div class="row">
                  <div class="col-lg-2 col-md-3 col-3">
                    <div class="location-icon">
                      <i class="fa fa-envelope"></i>
                    </div>
                  </div>
                  <div class="col-lg-10 col-md-9 col-9">
                    <div class="location-txt">
                      <span
                        ><a-lang value="contact-us-mail"></a-lang>
                        <a href="mailto:ayuda@loteria.com.ec"
                          >ayuda@loteria.com.ec</a
                        ></span
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-6">
            <div class="heading" style="color: #fff">
              <h2>
                <a-lang value="get-in-touch"> </a-lang>
              </h2>
              <hr class="hr" />
            </div>
            <div class="faq-form">
              <div class="row">
                <div class="form-group col-sm-12">
                  <input
                    type="text"
                    class="form-control modal-custom-input"
                    name="cedula"
                    v-model="cedula"
                    :placeholder="getLocate('enter-your-cedula')"
                    style="color: black !important"
                  />
                </div>
                <div class="form-group col-sm-12">
                  <input
                    type="text"
                    class="form-control modal-custom-input"
                    name="name"
                    v-model="name"
                    :placeholder="getLocate('enter-your-name')"
                    style="color: black !important"
                  />
                </div>
                <div class="form-group col-sm-12">
                  <input
                    type="text"
                    class="form-control modal-custom-input"
                    name="email"
                    v-model="email"
                    :placeholder="getLocate('enter-your-email')"
                    style="color: black !important"
                  />
                </div>
                <div class="form-group col-sm-12">
                  <textarea
                    class="modal-custom-input"
                    name="meassage"
                    v-model="comments"
                    :placeholder="getLocate('enter-comments')"
                    style="color: black !important"
                  ></textarea>
                </div>
                <div class="form-group col-sm-11">
                  <label style="font-size: 12px; color: white">
                    {{ getLocate("contact-us-text-1") }}
                    <a
                      target="_BLANK"
                      href="doc/politica_de_seguridad_y_privacidad.pdf"
                    >
                      {{ getLocate("contact-us-text-2") }}</a
                    >
                    <span class="checkmark"></span>
                  </label>
                </div>
                <div class="form-group col-sm-1">
                  <input
                    type="checkbox"
                    name="condiciones"
                    v-model="condiciones"
                    checked=""
                    value="1"
                    style="height: 13px; width: auto"
                  />
                </div>

                <div class="form-group col-sm-11">
                  <label style="font-size: 13px; color: white">
                    {{ getLocate("contact-us-text-3") }}

                    <span class="checkmark"></span>
                  </label>
                </div>

                <div class="form-group col-sm-1">
                  <input
                    type="checkbox"
                    name="condiciones"
                    v-model="consensoComercial"
                    checked=""
                    value="1"
                    style="height: 13px; width: auto"
                  />
                </div>
              </div>

              <div class="casino-btn col-sm-12" v-if="!loading">
                <a
                  href="javascript:void(0)"
                  class="btn-4 yellow-btn mb-3"
                  @click="sendEmail()"
                >
                  <a-lang value="send"> </a-lang>
                </a>
              </div>
              <a-loading :show="loading"></a-loading>
              <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- contact-page End -->
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {
      name: null,
      email: null,
      comments: null,
      cedula: null,
      condiciones: null,
      consensoComercial: null,
    };
  },
  created() {},
  methods: {
    sendEmail() {
      this.alert = { type: "alert-success", message: "" };
      if (
        !this.cedula ||
        (!this.validateCedula(this.cedula) &&
          !this.validatePassport(this.cedula)) ||
        !this.email ||
        !this.name ||
        !this.comments ||
        !this.condiciones
      ) {
        this.alert = {
          type: "alert-danger",
          message: this.getLocate("send-validate"),
        };
        return;
      }
      this.loading = true;
      let formData = new FormData();
      formData.append("name", this.name);
      formData.append("email", this.email);
      formData.append("comments", this.comments);
      formData.append("cedula", this.cedula);
      formData.append(
        "consensoComercial",
        this.consensoComercial ? "true" : "false"
      );
      this.$http
        .post("sendContactUs", formData)
        .then((response) => {
          let result = response.data;
          if (result.message == "OK") {
            this.alert = {
              type: "alert-success",
              message: this.getLocate("send-ok"),
            };
            this.name = null;
            this.email = null;
            this.comments = null;
          } else {
            this.alert = {
              type: "alert-danger",
              message: this.getLocate("send-error"),
            };
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
