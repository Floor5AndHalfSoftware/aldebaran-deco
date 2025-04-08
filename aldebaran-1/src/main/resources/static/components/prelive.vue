<template>
  <div>
    <!-- Related other games Start -->
    <section id="project-img" class="project-img games-page back-light">
      <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
      <div style="padding: 0px; margin-bottom: 10px">
        <a-banner category="banner sportsbook" v-if="isDesktop()"></a-banner>
        <a-banner
          category="banner sportsbook (mobile)"
          v-if="!isDesktop()"
        ></a-banner>
      </div>
      <!-- GAMES Start -->
      <!--
      <games-scroll
        @login="$emit('login')"
        from-home="new"
        background="no"
      ></games-scroll>
      -->
      <!-- GAMES End -->
      <sports-book-new
        @loginsb="$emit('loginsb')"
        @balance="$emit('balance')"
      ></sports-book-new>
    </section>
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
  created() {},
  methods: {},
};
</script>