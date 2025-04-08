<template>
  <div
    class="bet593-home project-img games-page back-light"
    style="padding-bottom: 0px"
  >
    <div style="padding: 0px">
      <a-banner category="banner home" v-if="isDesktop()"></a-banner>
    </div>
    <div style="padding: 0px">
      <a-banner category="banner home (mobile)" v-if="!isDesktop()"></a-banner>
    </div>

    <div class="pages-section">
      <div class="page-card-container">
        <a href="#/overview">
          <div
            class="page-card sportsbook"
            style="background-image: url('images/page-background-1.jpg')"
          >
            <div class="page-title-bold">PRONÓSTICOS</div>
            <div class="page-title">DEPORTIVOS</div>
          </div>
        </a>
      </div>
      <div class="page-card-container">
        <a href="#/games/view/virtuals">
          <div
            class="page-card virtual"
            style="background-image: url('images/page-background-2.jpg')"
          >
            <div class="page-title-bold">PRONÓSTICOS</div>
            <div class="page-title">VIRTUALES</div>
          </div>
        </a>
      </div>
      <div class="page-card-container">
        <a href="#/games">
          <div
            class="page-card casino"
            style="background-image: url('images/page-background-3.jpg')"
          >
            <div class="page-title-bold">JUEGOS</div>
            <div class="page-title">ONLINE</div>
          </div>
        </a>
      </div>
    </div>

    <!--
    <div class="events">
      <div class="title">
        <div>
          <span class="title-glow">EVENTOS</span>
          <span class="title-second">DESTACADOS</span>
        </div>
      </div>

      <div class="events-container">
        <div class="event-card">
          <sports-book-new
            @loginsb="$emit('loginsb')"
            @balance="$emit('balance')"
            widget="WUpcoming"
            container="containerWUpcoming"
          ></sports-book-new>
        </div>
        <div class="event-card">
          <sports-book-new
            @loginsb="$emit('loginsb')"
            @balance="$emit('balance')"
            widget="WPopularBets"
            container="containerWPopularBets"
          ></sports-book-new>
        </div>
        <div class="event-card">
          <sports-book-new
            @loginsb="$emit('loginsb')"
            @balance="$emit('balance')"
            widget="WLiveNow"
            container="containerWLiveNow"
          ></sports-book-new>
        </div>
      </div>
    </div>
    -->

    <div class="games">
      <div class="games-header">
        <div class="games-image-container">
          <img class="games-image" src="images/juegos-online.png" alt="" />
        </div>
      </div>
      <div class="buttonsGroup">
        <img
          id="slideBackcontainerNew"
          @click="backScroll('containerNew')"
          src="images/image_next.png"
          class="prevButton desktopimage"
          alt="prev"
        />
        <img
          id="slidecontainerNew"
          @click="nextScroll('containerNew')"
          src="images/image_next.png"
          class="nextButton desktopimage"
          alt="next"
        />
      </div>
      <div class="inline-wrapper mx-0" id="containerNew">
        <game
          v-for="item in top10gamesNew"
          :key="item.gameID"
          :title="item.name"
          :category="item.category"
          :imgpath="item.thumbnailPath"
          :playlink="item.playLinkDemo"
          :gameid="item.gameID"
          :isnew="item.new"
          :params="item.gameParams"
          :port-img="'port_img_1'"
          @login="$emit('login')"
          @balance="getSessions"
          @free="freeGameLaunch"
        >
        </game>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {
      top10gamesNew: [],
    };
  },
  mounted() {
    this.getGames();
  },
  methods: {
    getGames() {
      this.$http
        .get("games")
        .then((response) => {
          let gamesNew = response.data.gamesNew;
          this.top10gamesNew = gamesNew.slice(0, 10);
        })
        .catch((response) => {
          this.showError(response, this.getLocate("game-list-1"));
        });
    },
    getSessions(gameID, title, gameParams, category) {
      this.isReserve = null;
      this.sessionIdToClose = null;
      this.type = "REAL";
      this.gameId = gameID;
      this.title = title;
      this.gameParams = gameParams;
      this.categoryBonus = category;
      this.gamesIdBonus = gameID;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", "");
      formData.append("to", "");
      formData.append("gameid", this.gameId);
      formData.append("state", "OPEN");
      this.$http
        .post("session/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            let sessions = result.sessions;
            if (sessions && sessions.length > 0) {
              //this.isReserve = "ko";
              this.sessionIdToClose = sessions[0].session_id;
              this.type = sessions[0].type;
              this.currency = sessions[0].currency;
              this.closeGameSession();
            } else {
              this.getBalances();
            }
          } else {
            $("#playModal").modal("hide");
            document.getElementById("back-top-btn").click();
            this.alert = {
              type: "alert-danger",
              message: result.description || result.error,
            };
          }
        })
        .catch((response) => {
          $("#playModal").modal("hide");
          this.showError(response);
        });
    },
    getBalances(forPromo) {
      this.amount = 0;
      this.amountM = 0;
      this.amountBonusM = 0;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("balances", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.isReserve = "ok";
            this.balances = result.data;
            this.balances.forEach((e) => {
              if (e.type == "C" || e.type == "N")
                this.amountM += e.amount / 100;
              else if (e.type == "G") this.amountBonusM += e.amount / 100;
              this.currency = e.currency;
            });
            this.changeAmount(forPromo);
          } else {
            $("#playModal").modal("hide");
            document.getElementById("back-top-btn").click();
            this.alert = {
              type: "alert-danger",
              message: result.description || result.error,
            };
          }
        })
        .catch((response) => {
          $("#playModal").modal("hide");
          this.showError(response);
        });
    },
    changeAmount(forPromo) {
      this.maxAmount = 0;
      this.amount = 0;
      if (this.type == "REAL") this.maxAmount = this.amountM.toFixed(2);
      else if (this.type == "PROMO") {
        if (!forPromo) this.getBonuses();
        this.maxAmount = this.amountBonusM.toFixed(2);
      }
    },
    closeGameSession() {
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("sessionid", this.sessionIdToClose);
      this.$http
        .post("session/close", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          this.getBalances();
          /*
          if (result.result == 0) {
            this.getBalances();
          } else {
            $("#playModal").modal("hide");
            document.getElementById("back-top-btn").click();
            this.alert = {
              type: "alert-danger",
              message: result.description || result.error,
            };
          }*/
        })
        .catch((response) => {
          $("#playModal").modal("hide");
          this.showError(response);
        });
    },
    openGameSession() {
      this.loadingOnDelay();
      this.launcherUrl = null;

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("gameid", this.gameId);
      formData.append("type", this.type);
      if (store.state.betMethod == "ITA")
        formData.append("amount", this.amount);
      else formData.append("amount", 0);
      formData.append("currency", this.currency);
      if (this.bonus && this.type == "PROMO")
        formData.append("promoid", this.bonus);
      this.$http
        .post("session/open", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          // se e' tutto ok genero l'url del gioco
          if (result.result == 0) {
            this.launcherUrl = this.generateLauncherUrl(
              result.session.sessionid,
              "real"
            );
            if (store.state.gsNewLauncher.includes(this.gameParams.gscode))
              location.href =
                "/gamelauncher?usertoken=" +
                encodeURIComponent(store.state.userId) +
                "&sessiontoken=" +
                encodeURIComponent(store.state.sessionId) +
                "&sessionid=" +
                encodeURIComponent(result.session.sessionid) +
                "&link=" +
                encodeURIComponent(this.launcherUrl) +
                "&lang=" +
                encodeURIComponent(store.state.locate);
            else if (
              store.state.gsNewLauncher1.includes(this.gameParams.gscode)
            )
              location.href =
                "/gamelauncher1?usertoken=" +
                encodeURIComponent(store.state.userId) +
                "&sessiontoken=" +
                encodeURIComponent(store.state.sessionId) +
                "&sessionid=" +
                encodeURIComponent(result.session.sessionid) +
                "&link=" +
                encodeURIComponent(this.launcherUrl) +
                "&lang=" +
                encodeURIComponent(store.state.locate);
            else location.href = this.launcherUrl;
            this.amount = 0;
          } else {
            $("#playModal").modal("hide");
            document.getElementById("back-top-btn").click();
            this.alert = {
              type: "alert-danger",
              message: result.description || result.error,
            };
          }
        })
        .catch((response) => {
          $("#playModal").modal("hide");
          this.showError(response);
        });
    },
    freeGameLaunch(gameID, title, gameParams) {
      this.loadingOnDelay();
      this.gameId = gameID;
      this.title = title;
      this.gameParams = gameParams;
      this.currency = store.state.currency;
      this.launcherUrl = this.generateLauncherUrl("free", "free");

      if (store.state.gsNewLauncher.includes(this.gameParams.gscode))
        location.href =
          "/gamelauncher?link=" +
          encodeURIComponent(this.launcherUrl) +
          "&lang=" +
          encodeURIComponent(store.state.locate);
      else if (store.state.gsNewLauncher1.includes(this.gameParams.gscode))
        location.href =
          "/gamelauncher1?link=" +
          encodeURIComponent(this.launcherUrl) +
          "&lang=" +
          encodeURIComponent(store.state.locate);
      else location.href = this.launcherUrl;
    },
    generateLauncherUrl(sessionid, mode) {
      return (this.launcherUrl =
        store.state.mtpuUrlLaunch +
        "?usertoken=" +
        encodeURIComponent(store.state.userId) +
        "&sessiontoken=" +
        encodeURIComponent(store.state.sessionId) +
        "&sessionid=" +
        encodeURIComponent(sessionid) +
        "&operator=" +
        encodeURIComponent(store.state.operator) +
        "&language=" +
        this.codeLocate() +
        "&mode=" +
        encodeURIComponent(mode) +
        "&game=" +
        encodeURIComponent(this.gameId) +
        "&device=" +
        encodeURIComponent(this.deviceType()) +
        "&currency=" +
        encodeURIComponent(this.currency) +
        "&lobby=" +
        store.state.serverUrl +
        "session/lobby/close");
    },
    backScroll(containerId) {
      this.sideScroll(containerId, "left", 10, 800, 20);
    },
    nextScroll(containerId) {
      this.sideScroll(containerId, "right", 10, 800, 20);
    },
    sideScroll(containerId, direction, speed, distance, step) {
      let element = document.getElementById(containerId);
      let scrollAmount = 0;
      let slideTimer = setInterval(function () {
        if (direction == "left") {
          element.scrollLeft -= step;
        } else {
          element.scrollLeft += step;
        }
        scrollAmount += step;

        if (scrollAmount >= distance) {
          window.clearInterval(slideTimer);
        }
        let maxScroll = element.scrollWidth - element.offsetWidth;
        if (element.scrollLeft == maxScroll)
          document.getElementById("slide" + containerId).style.visibility =
            "hidden";
        else
          document.getElementById("slide" + containerId).style.visibility =
            "visible";

        if (element.scrollLeft == 0)
          document.getElementById("slideBack" + containerId).style.visibility =
            "hidden";
        else
          document.getElementById("slideBack" + containerId).style.visibility =
            "visible";
      }, speed);
    },
  },
};
</script>