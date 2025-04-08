<template>
  <div>
    <section
      id="free-spin"
      class="free-spin section"
      :class="{ 'sfondo-2': background == undefined, '': background == 'no' }"
    >
      <div class="container">
        <div class="row">
          <div class="col-lg-6">
            <div class="heading">
              <h2>
                <a-lang value="bingo-online"> </a-lang>
              </h2>
              <hr class="hr" />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6 no-padding">
            <div class="spin-img">
              <img src="images/bingo1.png" alt="casino" />
            </div>
          </div>

          <div class="col-md-6">
            <div class="spin-text">
              <span v-html="bingo.content"></span>

              <div class="casino-btn">
                <a :href="'#/bingo'" class="btn-4 yellow-btn">
                  <a-lang value="play-now"> </a-lang>
                </a>
              </div>
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
  props: ["background"],
  data() {
    return {
      bingo: [],
    };
  },
  created() {
    this.getBingoCms();
  },
  methods: {
    getBingoCms() {
      this.loading = true;
      let formData = new FormData();
      formData.append("language", store.state.locate);
      formData.append("group", "bingo");
      formData.append("category", "presentation");
      formData.append("status", 1);
      this.$http
        .post("cms/text/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            if (result.contents && result.contents.length > 0)
              this.bingo = result.contents[0];
          } else {
            this.alert = { type: "alert-danger", message: result.description };
          }
        })
        .catch((response) => {
          this.showError(response);
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>  
