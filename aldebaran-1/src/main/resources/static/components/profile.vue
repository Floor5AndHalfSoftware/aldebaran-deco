<template>
  <div
    class="modal fade"
    id="userProfile"
    tabindex="-1"
    role="dialog"
    aria-labelledby="userProfileLabel"
    aria-hidden="true"
  >
    <div
      class="modal-dialog modal-lg"
      role="document"
      style="max-width: 1024px"
    >
      <div class="modal-content profile-modal">
        <div
          id="modal-body-scroll"
          class="modal-body"
          :style="!isDesktop() ? 'padding:0' : 'overflow-y:hidden'"
        >
          <button
            type="button"
            class="close"
            data-dismiss="modal"
            aria-label="Close"
            :style="
              !isDesktop() ? 'position:absolute; right: 7px; top:19px' : ''
            "
          >
            <span aria-hidden="true">&times;</span>
          </button>
          <!--MENU' PROFILE MOBILE-->
          <div
            v-if="!isDesktop()"
            class="button"
            @click="toggleMenuMobile()"
            style="z-index: 1"
          >
            <i class="more-less fa fa-bars fa-2x close"></i>
          </div>
          <!--MENU' PROFILE MOBILE END-->
          <!--MENU' LATERALE PROFILE-->
          <div
            id="profile-left-menu"
            class="container h-100"
            :style="!isDesktop() ? 'padding-left:8px;padding-right:8px;' : ''"
          >
            <div class="row h-100">
              <div class="col-3 profile-left-menu" v-if="isDesktop()">
                <img
                  src="images/profile-lateral.png"
                  class="profile-left-menu-logo"
                  alt=""
                />
                <!-- TABS -->
                <profile-tabs
                  :page="lastPage"
                  @change-page="changePage"
                  @reset-messages="resetMessages"
                ></profile-tabs>
                <!-- TABS END -->
              </div>
              <!--MENU' LATERALE PROFILE END-->

              <!--CONTENUTO PROFILE-->
              <div
                :class="{ 'col-9': isDesktop(), 'col-12': !isDesktop() }"
                :style="
                  isDesktop() ? 'padding-left: 20px' : 'padding-left: 0px'
                "
              >
                <div
                  class="tab-content conteiner h-100"
                  id="v-pills-tabContent"
                  style="max-height: 99vh; overflow: hidden; padding-left: 15px"
                >
                  <div class="row mb-2 mt-1">
                    <div class="col-12">
                      <img
                        src="images/profile-banner.png"
                        alt=""
                        style="border-radius: 6px"
                      />
                    </div>
                  </div>
                  <div>
                    <div
                      class="tab-pane fade show active"
                      id="menuMobileTab"
                      v-if="!isDesktop() && pageView == 'menuMobileTabOpen'"
                    >
                      <div>
                        <!-- TABS -->
                        <profile-tabs
                          :page="lastPage"
                          @change-page="changePage"
                          @reset-messages="resetMessages"
                        ></profile-tabs>
                        <!-- TABS END -->
                      </div>
                    </div>

                    <!-- USER INFO -->
                    <div
                      class="tab-pane fade show active"
                      id="profile"
                      v-if="pageView == 'profile'"
                    >
                      <profile-userinfo
                        :key="profileKey"
                        :loading="loading"
                        @change-loading="changeLoading"
                        @set-error="setError"
                        @set-success="setSuccess"
                      ></profile-userinfo>
                    </div>
                    <!-- USER INFO END -->
                    <!-- BONUSES -->
                    <div
                      class="tab-pane fade show active"
                      id="bonuses"
                      v-else-if="pageView == 'bonuses'"
                    >
                      <profile-bonuses
                        :key="bonusesKey"
                        :loading="loading"
                        @change-loading="changeLoading"
                        @set-error="setError"
                        @set-success="setSuccess"
                      ></profile-bonuses>
                    </div>
                    <!-- BONUSES END -->
                    <!-- PORTFOLIO -->
                    <div
                      class="tab-pane fade show active"
                      id="portfolio"
                      v-else-if="pageView == 'portfolio'"
                    >
                      <profile-portfolio
                        :key="portfolioKey"
                        :loading="loading"
                        :subpage="subpage"
                        @change-loading="changeLoading"
                        @set-error="setError"
                        @set-success="setSuccess"
                        @reset-messages="resetMessages"
                      ></profile-portfolio>
                    </div>
                    <!-- PORTFOLIO END -->
                    <!-- GAMES HISTORY -->
                    <div
                      class="tab-pane fade show active"
                      id="gamesHistory"
                      v-else-if="pageView == 'gamesHistory'"
                    >
                      <profile-games-history
                        :key="gamesHistoryKey"
                        :loading="loading"
                        @change-loading="changeLoading"
                        @set-error="setError"
                        @set-success="setSuccess"
                        @reset-messages="resetMessages"
                      ></profile-games-history>
                    </div>
                    <!-- GAMES HISTORY END -->
                    <!-- BETS -->
                    <div
                      class="tab-pane fade show active"
                      id="bets"
                      v-else-if="pageView == 'bets'"
                    >
                      <profile-bets
                        :key="betsKey"
                        :loading="loading"
                        @change-loading="changeLoading"
                        @set-error="setError"
                        @set-success="setSuccess"
                        @reset-messages="resetMessages"
                      ></profile-bets>
                    </div>
                    <!-- BETS END -->
                    <!-- LOTTERY -->
                    <div
                      class="tab-pane fade show active"
                      id="lottery"
                      v-else-if="pageView == 'lottery'"
                    >
                      <profile-lottery
                        :key="lotteryKey"
                        :loading="loading"
                        @change-loading="changeLoading"
                        @set-error="setError"
                        @set-success="setSuccess"
                        @reset-messages="resetMessages"
                      ></profile-lottery>
                    </div>
                    <!-- LOTTERY END -->
                    <!--PROFILE-POLITICS-->
                    <div
                      class="tab-pane fade show active"
                      id="politics"
                      v-else-if="
                        pageView == 'agreementsTerms' ||
                        pageView == 'privacyPolicy'
                      "
                    >
                      <profile-politics
                        :key="politicsKey"
                        :pageview="pageView"
                      ></profile-politics>
                    </div>
                    <!--PROFILE-POLITICS END-->
                    <!-- SETTINGS -->
                    <div
                      class="tab-pane fade show active"
                      id="settings"
                      v-else-if="pageView == 'settings'"
                    >
                      <profile-settings
                        :key="settingsKey"
                        :loading="loading"
                        @change-loading="changeLoading"
                        @set-error="setError"
                        @set-success="setSuccess"
                        @reset-messages="resetMessages"
                      ></profile-settings>
                    </div>
                    <!-- SETTINGS END -->
                  </div>

                  <div
                    class="modal fade"
                    id="modalMessageSuccess"
                    tabindex="-1"
                    aria-labelledby="modalMessageLabel"
                    aria-hidden="true"
                  >
                    <div class="modal-dialog modal-dialog-centered">
                      <div
                        class="modal-content modal-custom-content"
                        style="min-height: 20vh"
                      >
                        <div class="modal-header">
                          <button
                            type="button"
                            class="close"
                            @click="
                              successMessage = '';
                              $('#modalMessageSuccess').modal('hide');
                            "
                          >
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <div class="alert alert-success" role="alert">
                            <span v-html="successMessage"></span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div
                    class="modal fade"
                    id="modalMessageAlert"
                    tabindex="-1"
                    aria-labelledby="modalMessageLabel"
                    aria-hidden="true"
                  >
                    <div class="modal-dialog modal-dialog-centered">
                      <div
                        class="modal-content modal-custom-content"
                        style="min-height: 20vh"
                      >
                        <div class="modal-header">
                          <h5 class="modal-title" id="staticBackdropLabel">
                            Atenci&oacute;n:
                          </h5>
                          <button
                            type="button"
                            class="close"
                            @click="
                              errorMessage = '';
                              $('#modalMessageAlert').modal('hide');
                            "
                          >
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <div class="alert alert-danger" role="alert">
                            <span v-html="errorMessage"></span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!--
                  <div class="alert alert-danger" role="alert" v-show="error">
                    <span>{{ errorMessage }}</span>
                    <button
                      type="button"
                      class="close"
                      aria-label="Close"
                      @click="error = false"
                    >
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  -->
                  <a-loading :show="loading"></a-loading>
                </div>
              </div>
            </div>
          </div>

          <!---->
        </div>
        <!-- <ul class="modal-footer login_menu navbar-right nav-sign"></ul> -->
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["pageView"],
  data() {
    return {
      //generici
      loading: false,
      error: false,
      errorMessage: "",
      success: null,
      successMessage: "",
      metadata: [],
      isMetadata: false,
      lastPage: "profile",
      subpage: null,

      //tabs keys
      profileKey: 0,
      bonusesKey: 0,
      lotteryKey: 0,
      settingsKey: 0,
      gamesHistoryKey: 0,
      portfolioKey: 0,
      betsKey: 0,
      politicsKey: 0,
    };
  },
  created() {
    if (store.state.tabProfile == "deposit") {
      console.log("deposit");
      this.subpage = "deposit";
      this.changePage("portfolio");
    }
  },
  mounted() {},
  methods: {
    changeLoading(value) {
      this.loading = value;
    },
    activeTab(tab) {
      $('.nav-principal a[href="#' + tab + '"]').tab("show");
    },
    setError(message) {
      if (message) {
        this.error = true;
        this.errorMessage = message;
        $("#modalMessageAlert").modal("show");
      } else {
        this.error = false;
        this.errorMessage = "";
        $("#modalMessageAlert").modal("hide");
      }
    },
    setSuccess(message) {
      if (message) {
        this.success = true;
        this.successMessage = message;
        $("#modalMessageSuccess").modal("show");
      } else {
        this.success = false;
        this.successMessage = "";
        $("#modalMessageSuccess").modal("hide");
      }
    },
    resetMessages() {
      this.success = false;
      this.successMessage = "";
      this.error = false;
      this.errorMessage = "";
      $("#modalMessageAlert").modal("hide");
      $("#modalMessageSuccess").modal("hide");
    },
    toggleMenuMobile() {
      if (this.pageView == "menuMobileTabOpen") {
        this.changePage(this.lastPage);
      } else {
        this.pageView = "menuMobileTabOpen";
      }
    },
    changePage(page) {
      this.pageView = page;
      this.lastPage = page;
      switch (page) {
        case "profile":
          this.profileKey += 1 % 2;
          break;
        case "bonuses":
          this.bonusesKey += 1 % 2;
          break;
        case "lottery":
          this.lotteryKey += 1 % 2;
          break;
        case "settings":
          this.settingsKey += 1 % 2;
          break;
        case "gamesHistory":
          this.gamesHistoryKey += 1 % 2;
          break;
        case "portfolio":
          this.portfolioKey += 1 % 2;
          break;
        case "bets":
          this.betsKey += 1 % 2;
          break;
        case "agreementsTerms":
        case "privacyPolicy":
          this.politicsKey += 1 % 2;
          break;
      }
    },
  },
};
</script>
