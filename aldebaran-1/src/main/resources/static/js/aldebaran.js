/* -----------------------------------------------------------------
 * HTTP VUE LOADER COMPONENT (PAGES VUE)
 * -----------------------------------------------------------------  */

httpVueLoader.register(Vue, "components/home.vue");
httpVueLoader.register(Vue, "components/about-us.vue");
httpVueLoader.register(Vue, "components/contact-us.vue");
httpVueLoader.register(Vue, "components/faq.vue");
httpVueLoader.register(Vue, "components/games.vue");
httpVueLoader.register(Vue, "components/games-scroll.vue");
httpVueLoader.register(Vue, "components/game.vue");
httpVueLoader.register(Vue, "components/prelive.vue");
httpVueLoader.register(Vue, "components/sports-book.vue");
httpVueLoader.register(Vue, "components/sports-book-new.vue");
httpVueLoader.register(Vue, "components/statistics.vue");
httpVueLoader.register(Vue, "components/statistic.vue");
httpVueLoader.register(Vue, "components/404.vue");
httpVueLoader.register(Vue, "components/login.vue");

// PROFILE COMPONTENTS
httpVueLoader.register(Vue, "components/profile.vue");
httpVueLoader.register(Vue, "components/profile-userinfo.vue");
httpVueLoader.register(Vue, "components/profile-bonuses.vue");
httpVueLoader.register(Vue, "components/profile-movements.vue");
httpVueLoader.register(Vue, "components/profile-games-history.vue");
httpVueLoader.register(Vue, "components/profile-lottery.vue");
httpVueLoader.register(Vue, "components/profile-settings.vue");
httpVueLoader.register(Vue, "components/profile-withdrawal.vue");
httpVueLoader.register(Vue, "components/profile-deposits.vue");
httpVueLoader.register(Vue, "components/profile-portfolio.vue");
httpVueLoader.register(Vue, "components/profile-politics.vue");
httpVueLoader.register(Vue, "components/profile-bets.vue");
httpVueLoader.register(Vue, "components/lottery-ticket.vue");
httpVueLoader.register(Vue, "components/deposit-pill.vue");
httpVueLoader.register(Vue, "components/accept-conditions.vue");

httpVueLoader.register(Vue, "components/confirm.vue");
httpVueLoader.register(Vue, "components/changePassword.vue");
httpVueLoader.register(Vue, "components/changeEmail.vue");
httpVueLoader.register(Vue, "components/banner.vue");
httpVueLoader.register(Vue, "components/control.vue");
httpVueLoader.register(Vue, "components/counter.vue");
httpVueLoader.register(Vue, "components/how-to-start.vue");
httpVueLoader.register(Vue, "components/bingo-banner.vue");
httpVueLoader.register(Vue, "components/sports-book-banner.vue");
httpVueLoader.register(Vue, "components/cms-content.vue");
httpVueLoader.register(Vue, "components/a-frame.vue");
httpVueLoader.register(Vue, "components/placetopay-check.vue");
httpVueLoader.register(Vue, "components/cms-image.vue");
httpVueLoader.register(Vue, "components/bingo.vue");
httpVueLoader.register(Vue, "components/promotions.vue");
httpVueLoader.register(Vue, "components/privacy-policy.vue");
httpVueLoader.register(Vue, "components/a-banner.vue");
httpVueLoader.register(Vue, "components/nuvei-response.vue");
httpVueLoader.register(Vue, "components/payphone-response.vue");
httpVueLoader.register(Vue, "components/tournaments-list.vue");
httpVueLoader.register(Vue, "components/money-transfers.vue");
httpVueLoader.register(Vue, "components/video-modal.vue");

httpVueLoader.register(Vue, "components/modal-header.vue");

/* -----------------------------------------------------------------
 * VUE ROUTER
 * -----------------------------------------------------------------  */
Vue.use(VueRouter);
const router = new VueRouter({
  routes: [
    { path: "/", redirect: "/home" },
    { path: "/home", component: Vue.component("home") },
    { path: "/about-us", component: Vue.component("about-us") },
    { path: "/contact-us", component: Vue.component("contact-us") },
    { path: "/faq", component: Vue.component("faq") },
    { path: "/bingo", component: Vue.component("bingo") },
    { path: "/games", component: Vue.component("games-scroll") },
    { path: "/games-old", component: Vue.component("games") },

    {
      path: "/games/view/:category",
      component: Vue.component("games-scroll"),
      props: true,
    },
    {
      path: "/games/play/:gameIdPlay",
      component: Vue.component("games-scroll"),
      props: true,
    },
    { path: "/overview", component: Vue.component("prelive") },
    { path: "/statistics", component: Vue.component("statistics") },
    { path: "/login", component: Vue.component("login") },
    { path: "/profile", component: Vue.component("profile") },
    { path: "/privacy-policy", component: Vue.component("privacy-policy") },
    {
      path: "/tournaments/:subpath",
      component: Vue.component("tournaments-list"),
      props: true,
    },
    {
      path: "/open/:type",
      component: Vue.component("a-frame"),
      props: true,
    },
    {
      path: "/confirm/:nonce",
      component: Vue.component("confirm"),
      props: true,
    },
    {
      path: "/changePassword/:otp",
      component: Vue.component("changePassword"),
      props: true,
    },
    {
      path: "/changeEmail/:otp/:userid/:email/:hash",
      component: Vue.component("changeEmail"),
      props: true,
    },
    {
      path: "/placetopay-check/:reference",
      component: Vue.component("placetopay-check"),
      props: true,
    },
    { path: "/promotions", component: Vue.component("promotions") },
    {
      path: "/nuvei/:response",
      component: Vue.component("nuvei-response"),
      props: true,
    },
    {
      path: "/payphone/response",
      component: Vue.component("payphone-response"),
    },
    { path: "/money-transfers", component: Vue.component("money-transfers") },
    { path: "*", component: Vue.component("prelive") },
  ],
  scrollBehavior() {
    document.getElementById("app").scrollIntoView();
  },
});

/* -----------------------------------------------------------------
 * ROUTER SETTINGS
 * -----------------------------------------------------------------  */
router.beforeResolve((to, from, next) => {
  if (store.state.okLocation === false && to.path !== "/wrong-location") {
    router.replace("/wrong-location");
    return;
  }
  if (to.path == "/home")
    document.getElementById("header").classList.remove("header_area");
  else document.getElementById("header").classList.add("header_area");
  /*
  if (
    to.path == "/" ||
    to.path == "/about-us" ||
    to.path == "/contact-us" ||
    to.path == "/faq" ||
    to.path == "/bingo" ||
    to.path == "/games" ||
    to.path == "/games-scroll" ||
    to.path == "/statistics" ||
    to.path == "/promotions" ||
    to.path == "/privacy-policy"
  )
    document.getElementById("container-back-top-btn").style.display = "inline";
  else*/
  document.getElementById("container-back-top-btn").style.display = "none";

  next();
});

/* -----------------------------------------------------------------
 * VUEX
 * -----------------------------------------------------------------  */
Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    email: document.getElementById("__email").value,
    firstname: document.getElementById("__firstname").value,
    lastname: document.getElementById("__lastname").value,
    userId: document.getElementById("__userId").value,
    sessionId: document.getElementById("__sessionId").value,
    lastlogin: document.getElementById("__lastlogin").value,
    id: document.getElementById("__id").value,
    status: document.getElementById("__status").value,
    sportsBookToken: document.getElementById("__sportsBookToken").value,
    serverUrl: document.getElementById("__serverUrl").value,
    jsDebug: document.getElementById("__jsDebug").value,
    underAge: document.getElementById("__underAge").value,
    betMethod: document.getElementById("__betMethod").value,
    mtpuUrl: document.getElementById("__mtpuUrl").value,
    mtpuUrlLaunch: document.getElementById("__mtpuUrlLaunch").value,
    currency: document.getElementById("__currency").value,
    locate: document.getElementById("__locate").value,
    sportsbookWalletcode: document.getElementById("__sportsbookWalletcode")
      .value,
    operator: document.getElementById("__operator").value,
    gsNewLauncher: document.getElementById("__gsNewLauncher").value,
    gsNewLauncher1: document.getElementById("__gsNewLauncher1").value,
    loteriaUrlDetail: document.getElementById("__loteriaUrlDetail").value,
    placetopayUsers: "no",
    nuveiUsers: "no",
    monnetPayinUsers: "no",
    monnetPayoutUsers: "no",
    safetypayUsers: "no",
    paycashUsers: "no",
    nicoUsers: "no",
    pdsUsers: "no",
    banktransferUsers: "no",
    payphoneUsers: "no",
    smarticoMissionUsers: "no",
    promoGamesUsers: "",
    tabProfile: null,
    asb: null,
    externalIp: null,
    urlvideo: "",
    modalVideoKey: 0,
    linkProfile: null,
    okLocation: true,
  },
  mutations: {
    setLocate(state, locate) {
      // mutate state
      state.locate = locate;
    },
    setTabProfile(state, tabProfile) {
      state.tabProfile = tabProfile;
    },
    setAsb(state, asb) {
      state.asb = asb;
    },
    setExternalIp(state, ip) {
      state.externalIp = ip;
    },
  },
});

/* -----------------------------------------------------------------
 * VUE GLOBAL INSTANCE
 * -----------------------------------------------------------------  */
Vue.config.devtools = true;
Vue.prototype.$http = axios.create({
  timeout: 45000,
  headers: { "Content-Type": "application/json" },
});

Vue.prototype.$http.interceptors.response.use(
  (response) => {
    if (store.state.jsDebug == "true")
      console.log(
        "[" + response.config.method.toUpperCase() + "] " + response.config.url,
        response.data
      );
    return response;
  } /*, (error) => {
  if (store.state.jsDebug == "true") {
    console.log("intercept error:", error);
    console.log(error.response.status);
  }
  if (error.response.status == 401)
    location.reload();
}*/
);

const LOCAL_STORAGE_LOCATE = "locate-aldebaran-loterianacional";

const vue = new Vue({
  router: router,
  mixins: [common],
  store: store,
  el: "#app",
  data: {
    isLogin: "login",
    modalLoginKey: 0,
    modalProfileKey: 0,
    balances: [],
    notCashableCredits: null,
    currency: null,
    cash: null,
    cashableCredits: null,
    currencyWithdrawal: null,
    promotionalCredits: null,
    currencyPromotional: null,
    promotionalBetCredits: null,
    currencyPromotionalBet: null,
    deferredPrompt: null,
    loadingBalance: false,
    viewDownloadButton: false,
    refreshKey: 0,
    imgAndroid: "images/dispobleappHover.png",
  },
  created() {
    this.initLang();
    this.getExternalIpAddress();
    if (this.isUserLogged()) this.getBalances();
    this.loadDBSettings();
    this.patchAltenarPath();
  },
  mounted() {
    if (!this.isDesktop()) {
      this.captureEventAndroid();
      this.captureEventIos();
    }

    if (store.state.id) {
      window._smartico_user_id = store.state.id;
      if (store.state.locate)
        window._smartico_language = store.state.locate.split("-")[0];
      window._smartico.requestPushPermissions();
    } else {
      window._smartico_user_id = null;
      window._smartico_language = null;
    }
    window._smartico_allow_localhost = true;
  },
  methods: {
    loadDBSettings() {
      this.$http
        .get("settings/list")
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);

          if (result.result == 0) {
            let settings = result.settings;
            settings.map((setting) => {
              if (setting.name == "placetopay deposit")
                store.state.placetopayUsers = setting.value;
              if (setting.name == "nuvei deposit")
                store.state.nuveiUsers = setting.value;
              if (setting.name == "monnet deposit")
                store.state.monnetPayinUsers = setting.value;
              if (setting.name == "monnet withdraw")
                store.state.monnetPayoutUsers = setting.value;
              if (setting.name == "safetypay deposit")
                store.state.safetypayUsers = setting.value;
              if (setting.name == "paycash deposit")
                store.state.paycashUsers = setting.value;
              if (setting.name == "nico deposit")
                store.state.nicoUsers = setting.value;
              if (setting.name == "pds withdraw")
                store.state.pdsUsers = setting.value;
              if (setting.name == "banktransfer withdraw")
                store.state.banktransferUsers = setting.value;
              if (setting.name == "payphone deposit")
                store.state.payphoneUsers = setting.value;
              if (setting.name == "smartico mission")
                store.state.smarticoMissionUsers = setting.value;
              if (setting.name == "promo games users")
                store.state.promoGamesUsers = setting.value;
            });
          }
        })
        .catch((response) => {
          this.showError(response);
        });
    },
    isCurrentRoutePath(path) {
      return this.$router.currentRoute.path == path;
    },
    initLang() {
      let lang = localStorage.getItem(LOCAL_STORAGE_LOCATE)
        ? localStorage.getItem(LOCAL_STORAGE_LOCATE)
        : store.state.locate;
      store.commit("setLocate", lang);
    },
    changeLang(lang) {
      this.loadingOn();
      store.commit("setLocate", lang);
      localStorage.setItem(LOCAL_STORAGE_LOCATE, lang);
      location.reload();
    },
    getBalances() {
      this.loadingBalance = false; //disable loading balance always
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("balances", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);

          if (result.result == 0) {
            this.balances = result;
            this.balances.data.forEach((element) => {
              if (
                element.type != null &&
                element.type != "" &&
                element.type == "N"
              ) {
                this.notCashableCredits = element.amount;
                this.currency = element.currency;
              }
              if (
                element.type != null &&
                element.type != "" &&
                element.type == "C"
              ) {
                this.cashableCredits = element.amount;
                this.currencyWithdrawal = element.currency;
              }
              if (
                element.type != null &&
                element.type != "" &&
                element.type == "G"
              ) {
                this.promotionalCredits = element.amount;
                this.currencyPromotional = element.currency;
                this.promotionalCredits = this.formatterNumber(
                  this.promotionalCredits,
                  true
                );
              }
              if (
                element.type != null &&
                element.type != "" &&
                element.type == "B" &&
                element.status == 1
              ) {
                this.promotionalBetCredits = element.amount;
                this.currencyPromotionalBet = element.currency;
                this.promotionalBetCredits = this.formatterNumber(
                  this.promotionalBetCredits,
                  true
                );
              }
              if (
                element.type != null &&
                element.type != "" &&
                element.type == "B" &&
                element.status == 0
              ) {
                this.promotionalBetCredits = "0.00";
              }
            });
            let value = this.cashableCredits + this.notCashableCredits;
            this.cash = this.formatterNumber(value, true);
            this.cashableCredits = this.formatterNumber(
              this.cashableCredits,
              true
            );
          }
        })
        .finally(() => {
          this.loadingBalance = false;
        });
    },
    captureEventAndroid() {
      window.addEventListener("beforeinstallprompt", (e) => {
        // ! Prevent Chrome 67 and earlier from automatically showing the prompt
        e.preventDefault();
        // Stash the event so it can be triggered later.
        this.deferredPrompt = e;
        $("#addToHomeModal").modal("show");
        this.viewDownloadButton = true;
      });
    },
    closeMenuMobile() {
      document.getElementById("menuWrapper").style.display = "none";
      document.body.style.overflow = "auto";
    },
    installAppAndroid() {
      // Show the prompt
      this.deferredPrompt.prompt();
      // Wait for the user to respond to the prompt
      this.deferredPrompt.userChoice.then((choiceResult) => {
        if (choiceResult.outcome === "accepted") {
          // Call another function?
        }
        this.deferredPrompt = null;
      });
    },
    isIos() {
      const userAgent = window.navigator.userAgent.toLowerCase();
      return /iphone|ipad|ipod/.test(userAgent);
    },
    isInStandaloneMode() {
      const isStandalone = window.matchMedia(
        "(display-mode: standalone)"
      ).matches;
      if (document.referrer.startsWith("android-app://")) {
        return true;
      } else if (navigator.standalone || isStandalone) {
        return true;
      }
      return false;
    },
    captureEventIos() {
      // Checks if should display install popup notification:
      if (this.isIos() && !this.isInStandaloneMode()) {
        $("#addToHomeModalIos").modal("show");
        this.viewDownloadButton = true;
      }
    },
    downloadApp() {
      if (this.isIos()) this.captureEventIos();
      else this.installAppAndroid();
    },
    isDesktop() {
      const navigatorAgent =
        navigator.userAgent || navigator.vendor || window.opera;
      return !(
        /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series([46])0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(
          navigatorAgent
        ) ||
        /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br([ev])w|bumb|bw-([nu])|c55\/|capi|ccwa|cdm-|cell|chtm|cldc|cmd-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc-s|devi|dica|dmob|do([cp])o|ds(12|-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly([-_])|g1 u|g560|gene|gf-5|g-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd-([mpt])|hei-|hi(pt|ta)|hp( i|ip)|hs-c|ht(c([- _agpst])|tp)|hu(aw|tc)|i-(20|go|ma)|i230|iac([ \-/])|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja([tv])a|jbro|jemu|jigs|kddi|keji|kgt([ /])|klon|kpt |kwc-|kyo([ck])|le(no|xi)|lg( g|\/([klu])|50|54|-[a-w])|libw|lynx|m1-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t([- ov])|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30([02])|n50([025])|n7(0([01])|10)|ne(([cm])-|on|tf|wf|wg|wt)|nok([6i])|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan([adt])|pdxg|pg(13|-([1-8]|c))|phil|pire|pl(ay|uc)|pn-2|po(ck|rt|se)|prox|psio|pt-g|qa-a|qc(07|12|21|32|60|-[2-7]|i-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h-|oo|p-)|sdk\/|se(c([-01])|47|mc|nd|ri)|sgh-|shar|sie([-m])|sk-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h-|v-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl-|tdg-|tel([im])|tim-|t-mo|to(pl|sh)|ts(70|m-|m3|m5)|tx-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c([- ])|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas-|your|zeto|zte-/i.test(
          navigatorAgent.substr(0, 4)
        )
      );
    },
    getExternalIpAddress() {
      this.$http
        .get("https://api.ipify.org?format=json")
        .then((response) => {
          let ip = response.data.ip;
          store.commit("setExternalIp", ip);
          this.$http
            .get("/checkLocation?ip=" + ip)
            .then((response) => {
              let result = response.data;
              store.state.okLocation = result.result === true;
              if (result.result === false) {
                location.href = "/wrong-location";
              }
            })
            .catch((response) => {
              this.showError(response);
            });
        })
        .catch((response) => {
          store.commit("setExternalIp", null);
          this.showError(response);
        });
    },
    toggleBalanceModal() {
      this.getBalances();
      let modal = document.getElementById("balanceModal");
      modal.style.display = modal.style.display === "flex" ? "none" : "flex";
    },
    patchAltenarPath() {
      // Listen to hash changes custom router
      window.addEventListener("hashchange", function (event) {
        let oldURL = event.oldURL.split("#")[1];
        let newURL = event.newURL.split("#")[1];

        if (oldURL == "/overview") router.push(newURL); // force custom router handle to change route
      });
    },
  },
});
