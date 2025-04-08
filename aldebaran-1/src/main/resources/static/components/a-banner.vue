<template>
  <div>
    <div
      :id="'carouselBanner' + name"
      class="carousel slide"
      data-ride="carousel"
    >
      <div :class="'carousel-inner ' + name">
        <div
          v-for="(c, i) in contents"
          :key="c.id"
          :class="'carousel-item' + (i == 0 ? ' active' : '')"
        >
          <div
            v-if="
              c.url &&
              c.url.includes('https://www.youtube.com/embed/') &&
              c.url.length > 30
            "
            style="cursor: pointer"
            @click="loadVideo(c.url)"
          >
            <cms-image :id="c.id" cstyle="width: 100%"></cms-image>
          </div>
          <a v-else :href="c.url">
            <cms-image :id="c.id" cstyle="width: 100%"></cms-image>
          </a>
        </div>
      </div>
      <a
        class="carousel-control-prev"
        :href="'#carouselBanner' + name"
        role="button"
        data-slide="prev"
        v-show="contents && contents.length > 1"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">
          <a-lang value="previous"> </a-lang>
        </span>
      </a>
      <a
        class="carousel-control-next"
        :href="'#carouselBanner' + name"
        role="button"
        data-slide="next"
        v-show="contents && contents.length > 1"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">
          <a-lang value="next"> </a-lang>
        </span>
      </a>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["category", "name"],
  data() {
    return {
      contents: [],
    };
  },
  created() {
    if (!this.name) this.name = "unique";
  },
  mounted() {
    this.initSlideshow(this.name);
    this.getBannerFiles();
  },
  methods: {
    getBannerFiles() {
      let formData = new FormData();
      formData.append("language", store.state.locate);
      formData.append("category", this.category);
      this.$http
        .post("cms/file/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.contents = result.contents;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
  },
};
</script>        