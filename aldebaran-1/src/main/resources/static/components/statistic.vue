<template>
  <div>
    <img
      src="images/loading_slot.gif"
      class="img-fluid"
      v-show="imageLoading"
    />
    <img
      :src="thumbnailpath"
      class="img-fluid"
      @error="replaceImgByDefault"
      @load="onImageLoad()"
      v-show="!imageLoading"
    />
    <div class="run-txt">
      <h4>{{ index + 1 }}. {{ name }}</h4>
      <p>
        {{ formatterNumber(value, true) }}
        {{ currencyView(store.state.currency) }}
      </p>
      <div class="casino-btn" v-if="isUserLogged()">
        <a :href="'#/games/play/' + gameid" class="btn-4 yellow-btn">
          <a-lang value="play"> </a-lang>
        </a>
      </div>
      <div class="casino-btn" v-if="!isUserLogged()">
        <a
          href="javascript:void(0)"
          @click="$emit('login')"
          class="btn-4 yellow-btn"
          data-toggle="modal"
          data-target="#loginModal"
        >
          <a-lang value="play"> </a-lang>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["thumbnailpath", "index", "name", "value", "gameid"],
  data() {
    return {
      imageLoading: true,
    };
  },
  methods: {
    replaceImgByDefault(e) {
      e.target.src = "images/not-found.png";
    },
    onImageLoad() {
      this.imageLoading = false;
    },
  },
};
</script>

<style>
</style>