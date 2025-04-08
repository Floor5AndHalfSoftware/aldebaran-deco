<template>
  <div>
    <div
      id="carouselBanner"
      class="carousel slide"
      data-ride="carousel"
      style="top: 7vh; min-height: 230px"
    >
      <div class="carousel-inner">
        <div
          v-for="(c, i) in contents"
          :key="c.id"
          :class="'carousel-item' + (i == 0 ? ' active' : '')"
        >
          <a :href="c.url">
            <cms-image :id="c.id" cstyle="width: 100%"></cms-image>
          </a>
        </div>
      </div>
      <a
        class="carousel-control-prev"
        href="#carouselBanner"
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
        href="#carouselBanner"
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
    <a
      href="#"
      @click="$emit('signup')"
      class="hidden"
      data-toggle="modal"
      data-target="#loginModal"
      id="aSignup"
    >
    </a>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {
      contents: [],
    };
  },
  mounted() {
    this.initSlideshow();
    this.getBannerFiles();
  },
  methods: {
    getBannerFiles() {
      let formData = new FormData();
      formData.append("language", store.state.locate);
      formData.append("category", "banner home");
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
      