<template>
  <div>
    <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
    <a-loading :show="loading"></a-loading>
    <span v-html="rawHtml"></span>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["group", "category", "name"],
  data() {
    return {
      rawHtml: "",
    };
  },
  created() {
    this.getContent();
  },
  methods: {
    async getContent() {
      try {
        this.loading = true;
        let formData = new FormData();
        formData.append("language", store.state.locate);
        formData.append("group", this.group);
        formData.append("category", this.category);
        formData.append("name", this.name);
        let response = await this.$http.post("cms/text/list", formData);
        let result = response.data;
        this.forceLogout(result.result);
        if (result.result == 0) {
          this.rawHtml = result.contents[0].content;
        } else {
          this.alert = { type: "alert-danger", message: result.description };
        }
      } catch (response) {
        this.showError(response);
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<style>
</style>