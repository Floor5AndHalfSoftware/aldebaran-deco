<template>
  <div>
    <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
    <a-loading :show="loading"></a-loading>
    <img
      v-if="record.mime_type"
      :src="'data:' + record.mime_type + ';base64,' + record.content"
      :style="cstyle"
    />
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["category", "name", "cstyle", "id"],
  data() {
    return {
      record: {},
    };
  },
  created() {
    this.getFileContent();
  },
  methods: {
    async getFileContent() {
      try {
        let result = { result: -10, contents: [] };
        if (!this.id) {
          this.loading = true;
          let formData = new FormData();
          formData.append("language", store.state.locate);
          formData.append("category", this.category);
          formData.append("name", this.name);
          let response = await this.$http.post("cms/file/list", formData);
          result = response.data;
          this.forceLogout(result.result);
        } else result.result = 0;

        if (result.result == 0) {
          if ((result.contents && result.contents.length > 0) || this.id) {
            let id = this.id;
            if (result.contents && result.contents.length > 0)
              id = result.contents[0].id;
            let formData = new FormData();
            formData.append("id", id);
            response = await this.$http.post("cms/file/content", formData);
            result = response.data;
            this.forceLogout(result.result);
            if (result.result == 0) {
              this.record = result.record;
            } else {
              this.alert = {
                type: "alert-danger",
                message: result.description,
              };
            }
          }
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