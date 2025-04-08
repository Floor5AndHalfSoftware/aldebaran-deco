<template>
  <div>
    <section class="about-page back-dark">
      <br /><br />
      <div class="container">
        <div class="row">
          <div class="col-lg-6">
            <div class="about-video">
              <img
                src="images/about-video-image.jpg"
                class="img-fluid"
                alt="img"
              />
              <div class="youtube_video">
                <a
                  class="bla-1 play-video-button"
                  href="https://www.youtube.com/watch?v=yRyC7WJ3YIk"
                >
                  <i class="fa fa-play"></i>
                </a>
              </div>
            </div>
          </div>
          <div class="col-lg-6 contact-about">
            <div class="heading">
              <h2>
                <a> <a-lang value="about-casine"> </a-lang></a>
              </h2>
              <hr class="hr hr-white hr-no-margin-left" />
            </div>
            <p class="mb30">
              <cms-content
                group="about us"
                name="about us"
                category="about us"
              ></cms-content>
            </p>

            <div class="casino-btn" v-if="!isUserLogged()">
              <a
                href="javascript:void(0)"
                @click="$emit('signup')"
                data-toggle="modal"
                data-target="#loginModal"
              >
                <a-lang value="join-us-now"> </a-lang>
              </a>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {};
  },
  methods: {},
};
</script>