<template>
  <div>
    <section class="sfondo-3">
      <div class="container h-100">
        <div class="row align-items-center h-100">
          <div class="col-8 mx-auto">
            <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["nonce"],
  data() {
    return {};
  },
  created() {
    this.confirm();
  },
  methods: {
    confirm() {
      let formData = new FormData();
      formData.append("nonce", this.nonce);
      this.$http
        .post("confirm", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.alert = {
              type: "alert-success",
              message: this.getLocate("account-successfully"),
            };
          } else {
            this.alert = {
              type: "alert-danger",
              message: this.getLocate(result.description),
            };
          }
        })
        .catch((response) => {
          this.showError(response);
        });
    },
  },
};
</script>

<style>
</style>