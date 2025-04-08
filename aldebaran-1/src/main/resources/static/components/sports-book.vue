<template>
  <div style="padding: 0px 10px">
    <div id="altenarsportsbook"></div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {};
  },
  created() {},
  mounted() {
    this.initSportsBook();
    this.captureBackClick();
    sessionStorage.setItem("firstLoginMiniGame", "false");

    let eventMethod = window.addEventListener
      ? "addEventListener"
      : "attachEvent";
    let eventer = window[eventMethod];
    let messageEvent = eventMethod === "attachEvent" ? "onmessage" : "message";
    eventer(messageEvent, (e) => {
      if (e.data === "openlogin" || e.message === "openlogin") {
        this.$emit("loginsb");
        sessionStorage.setItem("firstLoginMiniGame", "true");
      } else if (e.data === "balance" || e.message === "balance")
        this.$emit("balance");
    });
  },
  beforeDestroy() {
    this.destroySportsBook();
  },
  methods: {
    initSportsBook() {
      let token =
        this.isUserLogged() && !this.isUserSelfexcluded()
          ? store.state.sportsBookToken
          : null;
      let initParams = {
        token: token,
        walletcode: store.state.sportsbookWalletcode,
        lang: store.state.locate,
        numformat: "en-GB",
        //configid: "2",
        page: "prelive",
        fixedTop: 87,
        statechangecallback: () => window.scrollTo(0, 0),
        placebetcallback: () => this.$emit("balance"),
        banners: {
          bottomLeft: [
            {
              url:
                "/banners?language=" +
                store.state.locate +
                "&position=bottomLeft&width=200&height=" +
                ($(window).width() <= 1396 ? "586" : "752"),
              height: ($(window).width() <= 1396 ? "586" : "752") + "px",
            },
          ],
          bottomRight: [
            {
              url:
                "/banners?language=" +
                store.state.locate +
                "&position=bottomRight&openGame=" +
                sessionStorage.getItem("firstLoginMiniGame") +
                "&width=200&heightGame=580&height=" +
                ($(window).width() <= 1396 ? "586" : "752"),
              height: ($(window).width() <= 1396 ? "1166" : "1332") + "px",
            },
          ],
        },
      };
      if (window.AltenarSportsbook) {
        store.commit(
          "setAsb",
          new AltenarSportsbook("#altenarsportsbook", initParams)
        );
        console.log("[SB] inizialize sports book");
      }
    },
    captureBackClick() {
      let rootElement = document.querySelector("body");
      rootElement.addEventListener(
        "click",
        (event) => {
          let targetElement = event.srcElement.className;
          if (
            targetElement &&
            typeof targetElement == "string" &&
            targetElement.match(
              "_asb_ui-icon _asb_ui-icon---back  asb-icon-back"
            )
          ) {
            //logic for handling the click event of li tag
            location.href = "#/prelive";
          }
        },
        true
      );
    },
    destroySportsBook() {
      if (this.isUserLogged()) {
        store.state.asb.logout();
        console.log("[SB] logout session");
        store.state.asb.destroy();
        console.log("[SB] destroy session");
      }
    },
  },
};
</script>