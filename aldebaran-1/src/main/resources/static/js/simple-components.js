/* -----------------------------------------------------------------
 * COMPONENTS
 * -----------------------------------------------------------------  */

// MULTI LANG COMPONENT
Vue.component("a-lang", {
  props: ["value"],
  template: `<span v-html="getLanguage[value]"></span>`,
  data() {
    return {
      languages: {},
    };
  },
  computed: {
    getLanguage() {
      return this.languages[store.state.locate];
    },
  },
  methods: {
    init() {
      this.languages = LANGUAGES;
    },
  },
  created() {
    this.init();
  },
});

Vue.component("a-alert", {
  props: ["alert"],
  template: `<div class="container-fluid" v-show="alert.message!=''">
              <br/>
              <div class="alert"  :class="alert.type" role="alert">
                <b><span v-html="alert.message"></span></b>
                <!--<button type="button" class="close" aria-label="Close" @click="close" style="position: absolute; right: 6px; top: 0px;">
                  <span aria-hidden="true">&times;</span>
                </button>-->
              </div>
              <br/>
            </div>`,
  methods: {
    close() {
      this.$emit("close");
    },
  },
});

Vue.component("no-result", {
  template: `<div class="box-flex align-items-center">
                <img
                  src="/images/no-results.png"
                  class="img-fluid"
                  style="max-width: 150px"
                />
                <a-alert
                  :alert="{
                    type: 'alert-info',
                    message: this.getLocate('no-result-found'),
                  }"
                  class="text-center background-transparent alert-custom-text"
                ></a-alert>
              </div>`,
  mixins: [common],
});

Vue.component("profile-tabs", {
  template: `<div class="nav flex-column nav-pills profile-nav-menu">
              <a
                href="#profile"
                class='nav-link profile-nav-link'
                :class="page=='profile'?'active':''"
                data-toggle="pill"
                @click="
                  resetMessages();
                  changePage('profile');
                "
                ><i :class="'fa fa-user'" class="profile-primary"></i>
                <a-lang value="userProfile"> </a-lang>
              </a>

              <a
                href="#portfolio"
                class='nav-link profile-nav-link'
                :class="page=='portfolio'?'active':''"
                data-toggle="pill"
                @click="
                  resetMessages();
                  changePage('portfolio');
                "
                ><i :class="'fa fa-money'" class="profile-primary"></i>
                <a-lang value="portfolio"> </a-lang
              ></a>

              <a
                href="#bonuses"
                class='nav-link profile-nav-link'
                :class="page=='bonuses'?'active':''"
                data-toggle="pill"
                @click="
                  resetMessages();
                  changePage('bonuses');
                "
                ><i :class="'fa fa-user-plus'" class="profile-primary"></i>
                <a-lang value="bonuses"> </a-lang>
              </a>

              <a
                v-if="!isUserSelfexcluded()"
                @click="
                  resetMessages();
                  changePage('gamesHistory');
                "
                href="#gamesHistory"
                class='nav-link profile-nav-link'
                :class="page=='gamesHistory'?'active':''"
                data-toggle="pill"
                ><i :class="'fa fa-gamepad'" class="profile-primary"></i>
                <a-lang value="games"> </a-lang
              ></a>

              <a
                v-if="!isUserSelfexcluded()"
                @click="
                  resetMessages();
                  changePage('bets');
                "
                href="#bets"
                class='nav-link profile-nav-link'
                :class="page=='bets'?'active':''"
                data-toggle="pill"
                ><i :class="'fa fa-soccer-ball-o'" class="profile-primary"></i>
                <a-lang value="bets"> </a-lang
              ></a>

              <a
                v-if="!isUserSelfexcluded()"
                @click="
                  resetMessages();
                  changePage('lottery');
                "
                href="#lottery"
                class='nav-link profile-nav-link'
                :class="page=='lottery'?'active':''"
                data-toggle="pill"
                ><i :class="'fa fa-star'" class="profile-primary"></i>
                <a-lang value="my-tickets"> </a-lang
              ></a>

              <a
                v-if="!isUserSelfexcluded()"
                @click="
                  resetMessages();
                  changePage('privacyPolicy');
                "
                href="#privacyPolicy"
                class='nav-link profile-nav-link'
                :class="page=='privacyPolicy'||page=='agreementsTerms'?'active':''"
                data-toggle="pill"
                ><i :class="'fa fa-file-text-o'" class="profile-primary"></i>
                <a-lang value="legals"> </a-lang
              ></a>

              <a
                v-if="!isUserSelfexcluded()"
                @click="
                  resetMessages();
                  changePage('settings');
                "
                href="#settings"
                class='nav-link profile-nav-link'
                :class="page=='settings'?'active':''"
                data-toggle="pill"
                ><i :class="'fa fa-cog'" class="profile-primary"></i>
                <a-lang value="settings"> </a-lang
              ></a>
              <a
                style="margin-top: 80px"
                @click="logout()"
                href="#"
                class='nav-link profile-nav-link'
                data-toggle="pill"
                ><i :class="'fa fa-sign-out'" class="profile-primary"></i>
                <a-lang value="logout"> </a-lang
              ></a>
            </div>`,
  props: ["page"],
  mixins: [common],
  methods: {
    resetMessages() {
      this.$emit("reset-messages");
    },
    changePage(page) {
      this.$emit("change-page", page);
    },
  },
});

Vue.component("a-loading", {
  props: ["show"],
  template: `<div class="preloaderLocal" :style="'display: '+(show?'inline':'none')">
              <div class="loaderLocal">
                <div class="demo-container">
                  <div class="progress-bar">
                    <div class="progress-bar-value"></div>
                  </div>
                </div>
              </div>
            </div>`,
});