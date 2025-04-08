<template>
  <div>
    <!-- Statistics Start -->
    <section id="jackpots" class="jackpots back-dark">
      <br /><br />
      <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
      <div class="container">
        <div class="row">
          <div class="col-lg-6">
            <div class="heading">
              <h2>
                <a-lang value="statistics"> </a-lang>
              </h2>
              <hr class="hr hr-white" />
            </div>
          </div>
        </div>
        <a-loading :show="loading"></a-loading>
        <div class="row justify-content-center text-center" v-if="!loading">
          <div class="col-lg-6 col-md-6">
            <div class="sub-heading upcom">
              <h3>
                <a-lang value="top-wins"> </a-lang>
              </h3>
              <div class="upcom-slick">
                <div
                  class="running-img"
                  style="height: 298px"
                  v-for="(item, index) in gamesWins"
                  :key="item.gameID"
                >
                  <statistic
                    :thumbnailpath="item.thumbnailPath"
                    :index="index"
                    :name="item.name"
                    :value="item.value"
                    :gameid="item.gameID"
                    @login="$emit('login')"
                  ></statistic>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-6 col-md-6">
            <div class="sub-heading upcom">
              <h3>
                <a-lang value="top-rounds"> </a-lang>
              </h3>
              <div class="upcom-slick">
                <div
                  class="running-img"
                  style="height: 298px"
                  v-for="(item, index) in gamesRounds"
                  :key="item.gameID"
                >
                  <statistic
                    :thumbnailpath="item.thumbnailPath"
                    :index="index"
                    :name="item.name"
                    :value="item.value"
                    :gameid="item.gameID"
                    @login="$emit('login')"
                  ></statistic>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- Casino Jackpots End -->
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {
      gamesWins: [],
      gamesRounds: [],
    };
  },
  created() {
    this.topGames();
  },
  methods: {
    topGames() {
      this.loading = true;
      this.$http
        .get("topGames")
        .then((response) => {
          this.gamesWins = response.data.gamesWins;
          this.gamesRounds = response.data.gamesRounds;
        })
        .catch((response) => {
          this.showError(response, this.getLocate("game-list-1"));
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>

<style>
</style>