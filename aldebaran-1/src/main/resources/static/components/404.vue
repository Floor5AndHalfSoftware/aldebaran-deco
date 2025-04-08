<template>
  <div>
    <!-- 404 page Start -->
    <section class="error">
      <div class="container">
        <div class="row">
          <div class="col-md-12 error-center">
            <div class="error-txt">
              <h4>error</h4>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12 error-btn">
            <div class="casino-btn">
              <a :href="'#/'"> <a-lang value="go-back-to-home"> </a-lang></a>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- 404 page End -->
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

<style>
</style>