<template>
  <div style="padding: 0px 10px">
    <div :id="container"></div>
  </div>
</template>

<script>
module.exports = {
  props: ["widget", "container"],
  mixins: [common],
  data() {
    return {};
  },
  created() {
    if (!this.container) this.container = "altenarsportsbook";
  },
  mounted() {
    this.initSportsBook();

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

      window.altenarWSDK.init({
        integration: "bet593",
        culture: store.state.locate,
        token: token,
        numformat: "en-GB",
        dateFormat: {
          time: "hh:mm",
          day: "DD",
          date: "DD/MM/YY",
          dateTime: "DD/MM/YY \u2022 hh:mm",
          dayMonth: "DD/MM",
          dayMonthTime: "DD/MM \u2022 hh:mm",
        },
      });

      // add sports book
      if (!this.widget) {
        window.ASB = window.altenarWSDK.addSportsBook({
          props: {
            page: "overview",
            onRouteChange: (data) => {
              console.log(data);
            },
            onBetPlacement: () => this.$emit("balance"),
            onBetPlacementError: () => this.$emit("balance"),
            banners: [
              {
                id: "midCenter",
                url:
                  store.state.serverUrl +
                  "banners?language=" +
                  store.state.locate +
                  "&position=midCenter",
              },
              {
                id: "bottomRight",
                url:
                  store.state.serverUrl +
                  "banners?language=" +
                  store.state.locate +
                  "&position=bottomRight&openGame=" +
                  sessionStorage.getItem("firstLoginMiniGame") +
                  "&heightGame=752",
                aspectRatio: "0.22",
              },
              {
                id: "bottomLeft",
                url:
                  store.state.serverUrl +
                  "banners?language=" +
                  store.state.locate +
                  "&position=bottomLeft&heightGame=500",
                aspectRatio: "0.35",
              },
            ],
          },
          container: document.getElementById(this.container),
        });
      } else if (this.widget == "WUpcoming") {
        window.WSB1 = window.altenarWSDK.addWidget({
          widget: "WUpcoming",
          props: {
            eventCount: 5,
            onOddSelect: (odd) => {
              location.href = "#/overview";
            },
          },
          tokens: {
            UpcomingTab: {
              textTransform: "uppercase",
            },
            UpcomingTabSelected: {
              borderColor: "red",
            },
          },
          container: document.getElementById(this.container),
        });
      } else if (this.widget == "WPopularBets") {
        window.WSB2 = window.altenarWSDK.addWidget({
          widget: "WPopularBets",
          props: {
            onOddSelect: (odd) => {
              location.href = "#/overview";
            },
          },
          tokens: {
            PopularBets: {
              paddingHorizontal: 10,
            },
          },
          container: document.getElementById(this.container),
        });
      } else if (this.widget == "WLiveNow") {
        window.WSB2 = window.altenarWSDK.addWidget({
          widget: "WLiveNow",
          props: {
            eventCount: 5,
            onEventSelect: (event) => {
              location.href = "#/overview";
            },
            onOddSelect: (odd) => {
              location.href = "#/overview";
            },
          },
          container: document.getElementById(this.container),
        });
      }

      console.log("[SB] inizialize SportsBook");
    },
    destroySportsBook() {
      if (window.ASB) {
        window.ASB.remove();
        console.log("[SB] destroy SportsBook widget");
      }
      if (window.WSB1) {
        window.WSB1.remove();
        console.log("[SB] destroy WUpcoming widget");
      }
      if (window.WSB2) {
        window.WSB2.remove();
        console.log("[SB] destroy WPopularBets widget");
      }
    },
  },
};
</script>