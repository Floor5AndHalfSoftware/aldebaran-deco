<template>
  <div class="subscribe sub-form">
    <div class="form-group" v-if="!loading">
      <div class="row">
        <div class="col-12 col-loteria" v-for="s in settings" :key="s.type">
          <div class="row">
            <div class="col-12 col-loteria">
              <div class="form-group">
                <div class="row">
                  <div class="col text-center">
                    <label
                      :class="
                        !isNumberInteger(s.value) || s.value < 0
                          ? 'text-danger'
                          : 'profile-input-label'
                      "
                    >
                      {{ getLocate(s.description) }}
                    </label>
                  </div>
                </div>
                <div class="row">
                  <div class="col-4 col-loteria"></div>
                  <div
                    class="col-4 col-loteria text-center"
                    v-if="s.type == 'SessionExpirationTimeout'"
                  >
                    <select
                      class="form-control modal-custom-input select-settings"
                      required
                      step="1"
                      :id="s.type"
                      style="
                        max-width: 150px;
                        display: inline-block;
                        background: transparent;
                      "
                      v-model="s.value"
                      @change="changeSettings(s.type, s.description, s.value)"
                    >
                      <option value="5">5</option>
                      <option value="10">10</option>
                      <option value="15">15</option>
                      <option value="20">20</option>
                      <option value="25">25</option>
                      <option value="30">30</option>
                      <option value="35">35</option>
                      <option value="40">40</option>
                      <option value="45">45</option>
                      <option value="50">50</option>
                      <option value="55">55</option>
                      <option value="60">60</option>
                    </select>
                  </div>
                  <div class="col-4 col-loteria text-center" v-else>
                    <input
                      maxlength="7"
                      v-model="s.value"
                      :id="s.type"
                      class="form-control modal-custom-input"
                      :class="
                        !isNumberInteger(s.value) || s.value < 0
                          ? 'is-invalid'
                          : ''
                      "
                      type="number"
                      required
                      step="1"
                      style="max-width: 150px"
                      @keyup="changeSettings(s.type, s.description, s.value)"
                      @change="changeSettings(s.type, s.description, s.value)"
                    />
                  </div>
                  <div class="col-4 col-loteria align-items-center">
                    <small
                      class="text-danger"
                      v-if="!isNumberInteger(s.value) || s.value < 0"
                    >
                      <a-lang value="invalid-number"> </a-lang>
                    </small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-12">
          <div class="form-group">
            <div class="row">
              <div class="col text-center">
                <label class="profile-input-label">
                  {{ getLocate("selfexclusion") }}
                </label>
              </div>
            </div>
            <div class="row">
              <div class="col text-center">
                <select
                  class="form-control modal-custom-input select-settings"
                  style="
                    max-width: 150px;
                    display: inline-block;
                    background: transparent;
                  "
                  v-model="selfExcluded"
                  @change="setSelfexcluded()"
                >
                  <option value="true">{{ getLocate("yes") }}</option>
                  <option value="false">No</option>
                </select>
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
  props: ["loading"],
  data() {
    return {
      // settings
      settings: [],
      selfExcluded: false,
    };
  },
  created() {
    this.getSettings();
  },
  methods: {
    getSettings() {
      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("limits/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.settings = result.limits;
            if (this.settings)
              this.settings = this.settings.sort((a, b) =>
                a.type > b.type ? 1 : -1
              );
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
    changeSettings(type, description, value) {
      this.setError(null);
      if (!this.isNumberInteger(value) || value < 0) return;

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("type", type);
      formData.append("value", value);
      formData.append("description", description);
      formData.append("operator", store.state.userId);
      this.$http
        .post("limits/set", formData)
        .then((response) => {
          let result = response.data;
          if (result.result != 0) {
            this.alertType = "error";
            this.message = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    setSelfexcluded() {
      this.success = false;
      this.successMessage = "";

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("set/selfexcluded", formData)
        .then((response) => {
          let result = response.data;
          if (result.result == 0) {
            this.forceLogout(-100);
          }
        })
        .catch((response) => {
          this.showError(response);
        });
    },
  },
};
</script>
