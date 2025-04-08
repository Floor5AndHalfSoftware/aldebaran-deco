<template>
  <div>
    <section
      id="project-img"
      class="project-img games-page"
      :class="{ 'sfondo-2': background == undefined, '': background == 'no' }"
    >
      <a-banner v-if="isDesktop()" category="banner games"></a-banner>
      <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
      <div>
        <a-loading :show="loading"></a-loading>
        <br /><br />
        <div
          class="row justify-content-center text-center"
          style="margin: 0px 10px 0px 10px"
          v-if="!loading"
        >
          <div class="col-lg-9 m-auto col-12 text-center button-group">
            <ul
              class="nav nav-pills scrollbar-style"
              id="pills-tab"
              role="tablist"
              style="overflow: auto; white-space: nowrap; display: inline-flex"
            >
              <li class="nav-item" v-if="gamesAll.length > 0">
                <a
                  class="nav-menu"
                  :class="{ 'active-nav': categorySelected == 'all' }"
                  id="pills-all-tab"
                  data-toggle="pill"
                  href="#pills-all"
                  role="tab"
                  aria-controls="pills-all"
                  aria-selected="true"
                  @click="
                    resetParams();
                    resetPage(gamesAll, 'all');
                  "
                >
                  <div style="margin-bottom: 7px">
                    <img
                      src="images/games_all.png"
                      style="width: 65px; height: 65px; min-width: 65px"
                    />
                  </div>
                  <a-lang value="all"> </a-lang>
                </a>
              </li>
              <li class="nav-item" v-if="gamesPopular.length > 0">
                <a
                  class="nav-menu"
                  :class="{ 'active-nav': categorySelected == 'popular' }"
                  id="pills-popular-tab"
                  data-toggle="pill"
                  href="#pills-popular"
                  role="tab"
                  aria-controls="pills-popular"
                  aria-selected="false"
                  @click="
                    resetParams();
                    resetPage(gamesPopular, 'popular');
                  "
                >
                  <div style="margin-bottom: 7px">
                    <img
                      src="images/games_popular.png"
                      style="width: 65px; height: 65px; min-width: 65px"
                    />
                  </div>
                  <a-lang value="popular"> </a-lang>
                </a>
              </li>
              <li class="nav-item" v-if="gamesNew.length > 0">
                <a
                  class="nav-menu"
                  :class="{ 'active-nav': categorySelected == 'new' }"
                  id="pills-new-tab"
                  data-toggle="pill"
                  href="#pills-new"
                  role="tab"
                  aria-controls="pills-new"
                  aria-selected="false"
                  @click="
                    resetParams();
                    resetPage(gamesNew, 'new');
                  "
                >
                  <div style="margin-bottom: 7px">
                    <img
                      src="images/games_new.png"
                      style="width: 65px; height: 65px; min-width: 65px"
                    />
                  </div>
                  <a-lang value="new"> </a-lang>
                </a>
              </li>
              <li class="nav-item" v-for="c in categories" :key="c">
                <a
                  class="nav-menu"
                  :class="{ 'active-nav': categorySelected == c }"
                  :id="'pills-' + c + '-tab'"
                  data-toggle="pill"
                  :href="'#pills-' + c"
                  role="tab"
                  :aria-controls="'pills-' + c"
                  aria-selected="false"
                  @click="
                    resetParams();
                    resetPage(gamesAll, c);
                  "
                >
                  <div style="margin-bottom: 7px">
                    <img
                      :src="'images/games_' + c + '.png'"
                      @error="replaceImgByDefault"
                      style="width: 65px; height: 65px; min-width: 65px"
                    />
                  </div>
                  {{ c }}</a
                >
              </li>
            </ul>
          </div>
        </div>
        <div
          class="row justify-content-center text-center pro-row"
          v-if="!loading"
        >
          <div class="col-6 col-md-4 col-lg-3" style="margin-top: 5px">
            <select
              class="form-control"
              style="color: 'black'"
              v-model="platform"
              @change="resetSelectedPage()"
            >
              <option value="ALL">{{ getLocate("platform") }}</option>
              <option v-for="p in platforms" :value="p" :key="p">
                {{ p }}
              </option>
            </select>
          </div>
          <div class="col-6 col-md-4 col-lg-3" style="margin-top: 5px">
            <select
              class="form-control"
              style="color: 'black'"
              v-model="order"
              @change="resetSelectedPage()"
            >
              <option value="DEFAULT">{{ getLocate("sort-by") }}</option>
              <option value="NEW">{{ getLocate("new-first") }}</option>
              <option value="A-Z">A-Z</option>
              <option value="Z-A">Z-A</option>
            </select>
          </div>
          <div class="col-11 col-md-4 col-lg-3" style="margin-top: 5px">
            <div class="input-group">
              <input
                type="search"
                :placeholder="getLocate('search-game')"
                v-model="search"
                class="form-control"
                style="color: 'black'"
                @keyup="resetSelectedPage()"
              />
              <div class="input-group-append icon-append">
                <i class="fa fa-search"></i>
              </div>
            </div>
          </div>
        </div>

        <div class="row mx-0">
          <div class="col-lg-12 px-0">
            <div class="tab-content" id="pills-tabContent">
              <div
                v-show="!loading && gamesPaginated.length == 0"
                class="row justify-content-center text-center"
                style="
                  width: auto;
                  font-family: 'Montserrat', sans-serif;
                  font-size: 20px;
                  color: #2f5597;
                "
              >
                <div class="col">
                  <a-lang value="no-game-present"></a-lang>
                </div>
              </div>
              <div
                class="tab-pane fade show active"
                id="pills-all"
                role="tabpanel"
                aria-labelledby="pills-all-tab"
              >
                <div class="row mx-0">
                  <game
                    v-for="item in gamesPaginated"
                    :key="item.gameID"
                    :title="item.name"
                    :category="item.category"
                    :imgpath="item.thumbnailPath"
                    :playlink="item.playLinkDemo"
                    :gameid="item.gameID"
                    :isnew="item.new"
                    :params="item.gameParams"
                    :port-img="'port_img'"
                    @login="$emit('login')"
                    @balance="getSessions"
                    @free="freeGameLaunch"
                  >
                  </game>
                </div>
                <div class="row" v-show="numGamesRemaining">
                  <div class="col">
                    <div class="casino-btn text-center">
                      <a
                        href="javascript:void(0)"
                        @click="nextPage(gamesAll, 'all')"
                        id="btn_next_all"
                        class="btn-4 yellow-btn"
                      >
                        <a-lang value="show-more"> </a-lang>
                        ({{ numGamesRemaining }}) ...
                      </a>
                    </div>
                  </div>
                  <!-- ESEMPIO DI PAGINAZIONE (NON PIU' UTILIZZATO)
                  <div class="col-3 nav-menu" style="margin-left: 20px">
                    Page <span id="page_all"></span> of
                    {{ numPages(gamesAll) }}
                  </div>
                  <div class="col">
                    <div class="casino-btn text-center">
                      <a
                        href="javascript:void(0)"
                        @click="prevPage(gamesAll, 'all')"
                        id="btn_prev_all"
                        class="btn-4 yellow-btn"
                        >&lt; Previous page</a
                      >
                      <a
                        href="javascript:void(0)"
                        @click="nextPage(gamesAll, 'all')"
                        id="btn_next_all"
                        class="btn-4 yellow-btn"
                        >Next page &gt;</a
                      >
                    </div>
                  </div>
                  -->
                </div>
              </div>

              <div
                class="tab-pane fade"
                id="pills-popular"
                role="tabpanel"
                aria-labelledby="pills-popular-tab"
              >
                <div class="row mx-0">
                  <game
                    v-for="item in gamesPaginated"
                    :key="item.gameID"
                    :title="item.name"
                    :category="item.category"
                    :imgpath="item.thumbnailPath"
                    :playlink="item.playLinkDemo"
                    :gameid="item.gameID"
                    :isnew="item.new"
                    :params="item.gameParams"
                    :port-img="'port_img'"
                    @login="$emit('login')"
                    @balance="getSessions"
                    @free="freeGameLaunch"
                  >
                  </game>
                </div>
                <div class="row" v-show="numGamesRemaining">
                  <div class="col">
                    <div class="casino-btn text-center">
                      <a
                        href="javascript:void(0)"
                        @click="nextPage(gamesPopular, 'popular')"
                        id="btn_next_popular"
                        class="btn-4 yellow-btn"
                      >
                        <a-lang value="show-more"> </a-lang>
                        ({{ numGamesRemaining }}) ...
                      </a>
                    </div>
                  </div>
                </div>
              </div>

              <div
                class="tab-pane fade"
                id="pills-new"
                role="tabpanel"
                aria-labelledby="pills-new-tab"
                v-if="gamesNew.length > 0"
              >
                <div class="row mx-0">
                  <game
                    v-for="item in gamesPaginated"
                    :key="item.gameID"
                    :title="item.name"
                    :category="item.category"
                    :imgpath="item.thumbnailPath"
                    :playlink="item.playLinkDemo"
                    :gameid="item.gameID"
                    :isnew="item.new"
                    :params="item.gameParams"
                    :port-img="'port_img'"
                    @login="$emit('login')"
                    @balance="getSessions"
                    @free="freeGameLaunch"
                  >
                  </game>
                </div>
                <div class="row" v-show="numGamesRemaining">
                  <div class="col">
                    <div class="casino-btn text-center">
                      <a
                        href="javascript:void(0)"
                        @click="nextPage(gamesNew, 'new')"
                        id="btn_next_new"
                        class="btn-4 yellow-btn"
                      >
                        <a-lang value="show-more"> </a-lang>
                        ({{ numGamesRemaining }}) ...
                      </a>
                    </div>
                  </div>
                </div>
              </div>

              <div
                class="tab-pane fade"
                :id="'pills-' + c"
                role="tabpanel"
                :aria-labelledby="'pills-' + c + '-tab'"
                v-for="c in categories"
                :key="c"
              >
                <div class="row mx-0">
                  <template v-for="item in gamesPaginated">
                    <game
                      v-if="
                        filterCategory(item.category, c) ||
                        item.gameParams.gscode == c
                      "
                      :key="item.gameID"
                      :title="item.name"
                      :category="item.category"
                      :imgpath="item.thumbnailPath"
                      :playlink="item.playLinkDemo"
                      :gameid="item.gameID"
                      :isnew="item.new"
                      :params="item.gameParams"
                      :port-img="'port_img'"
                      @login="$emit('login')"
                      @balance="getSessions"
                      @free="freeGameLaunch"
                    >
                    </game>
                  </template>
                </div>
                <div class="row" v-show="numGamesRemaining">
                  <div class="col">
                    <div class="casino-btn text-center">
                      <a
                        href="javascript:void(0)"
                        @click="nextPage(gamesAll, c)"
                        :id="'btn_next_' + c"
                        class="btn-4 yellow-btn"
                      >
                        <a-lang value="show-more"> </a-lang>
                        ({{ numGamesRemaining }}) ...
                      </a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--
        <div class="row justify-content-center text-center">
          <div class="col-md-12">
            <div class="casino-btn start-btn">
              <a href="games.html" class="btn-4 yellow-btn">Browse All</a>
            </div>
          </div>
        </div>
        -->
      </div>
    </section>
    <!-- Play Modal -->
    <div
      class="modal fade"
      id="playModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="playModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <!-- RESERVE AMOUNT (ITALIAN) -->
        <div
          class="modal-content back-dark"
          v-if="
            isReserve && isReserve == 'ok' && store.state.betMethod == 'ITA'
          "
        >
          <div class="modal-header">
            <h5 class="modal-title text-white" id="playModalLabel">
              <a-lang value="reserve"> </a-lang> {{ title }}
            </h5>
            <button
              type="button"
              class="close text-white"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body subscribe sub-form text-white">
            <a-lang value="select-amount-type"> </a-lang>:<br /><br />
            <div class="custom-control custom-radio custom-control-inline">
              <input
                type="radio"
                id="customRadio1"
                name="customRadio"
                v-model="type"
                class="custom-control-input"
                value="REAL"
                @change="getBalances()"
              />
              <label class="custom-control-label" for="customRadio1"
                >CASH</label
              >
            </div>
            <div
              class="custom-control custom-radio custom-control-inline"
              v-if="isPromoGamesUsers()"
            >
              <input
                type="radio"
                id="customRadio2"
                name="customRadio"
                v-model="type"
                class="custom-control-input"
                value="PROMO"
                @change="getBalances()"
              />
              <label class="custom-control-label" for="customRadio2"
                >PROMO</label
              >
            </div>
            <br /><br />
            <ul
              class="modal-footer login_menu navbar-right nav-sign justify-content-center"
            >
              <li></li>
            </ul>
            <span v-if="maxAmount > 0">
              <a-lang value="select-amount"> </a-lang>
              (<a-lang value="max"> </a-lang> {{ this.maxAmount }}
              {{ currencyView(currency) }}):<br /><br />
              <div>
                <div class="row">
                  <div class="col-1 p-0">
                    <i class="fa fa-minus" @click="subAmount()"></i>
                  </div>
                  <div class="col">
                    <vue-slider
                      v-model="amount"
                      :min="0"
                      :max="maxAmount"
                      :interval="0.01"
                    >
                    </vue-slider>
                  </div>
                  <div class="col-1 p-0">
                    <i class="fa fa-plus" @click="addAmount()"></i>
                  </div>
                </div>
              </div>
              <table style="width: 100%">
                <tr>
                  <td align="left">
                    <a-lang value="min"> </a-lang>
                  </td>
                  <td align="center">
                    <input
                      id="amount"
                      v-model.number="amount"
                      type="number"
                      step="0.01"
                      required
                      maxlength="10"
                      style="width: 40%"
                      @input="handleInput"
                    />
                    {{ currencyView(currency) }}
                  </td>
                  <td align="right">
                    <a-lang value="max"> </a-lang>
                  </td>
                </tr>
              </table>
            </span>
            <div class="alert alert-danger" role="alert" v-else>
              <span>
                <a-lang value="no-amount"> </a-lang>
                {{ type == "REAL" ? "CASH" : "PROMO" }}</span
              >
            </div>
            <br />
            <ul
              class="modal-footer login_menu navbar-right nav-sign justify-content-center"
            >
              <li>
                <a
                  class="btn-4 yellow-btn text-white"
                  v-if="amount > 0"
                  href="javascript:void(0)"
                  data-dismiss="modal"
                  @click="openGameSession()"
                  ><a-lang value="reserve"> </a-lang
                ></a>
              </li>
            </ul>
          </div>
        </div>
        <!-- PLAY (WORLDMODE) -->
        <div
          class="modal-content back-dark"
          v-if="
            isReserve && isReserve == 'ok' && store.state.betMethod != 'ITA'
          "
        >
          <div class="modal-header">
            <h5 class="modal-title text-white" id="playModalLabel">
              <a-lang value="play"> </a-lang> {{ title }}
            </h5>
            <button
              type="button"
              class="close text-white"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body subscribe sub-form text-white">
            <a-lang value="select-amount-type"> </a-lang>:<br /><br />
            <div class="custom-control custom-radio custom-control-inline">
              <input
                type="radio"
                id="customRadio1"
                name="customRadio"
                v-model="type"
                class="custom-control-input"
                value="REAL"
                @change="getBalances()"
              />
              <label class="custom-control-label" for="customRadio1"
                >CASH</label
              >
            </div>
            <div
              class="custom-control custom-radio custom-control-inline"
              v-if="isPromoGamesUsers()"
            >
              <input
                type="radio"
                id="customRadio2"
                name="customRadio"
                v-model="type"
                class="custom-control-input"
                value="PROMO"
                @change="getBalances()"
              />
              <label class="custom-control-label" for="customRadio2"
                >PROMO</label
              >
            </div>
            <br />
            <!-- IF PROMO CHOICE -->
            <div v-if="bonuses && bonuses.length > 0 && type == 'PROMO'">
              <br />
              <select
                class="custom-select form-select"
                id="bonus"
                v-model="bonus"
              >
                <option
                  v-for="b in bonuses"
                  :key="b.promoid"
                  :value="b.promoid"
                >
                  {{ b.name }} - {{ formatterNumber(b.amount, true) }}
                  {{ currencyView(b.currency) }} -
                  {{ formatterNumber(b.percentage) }}%
                </option>
              </select>
            </div>

            <br />
            <a-lang value="amount-available"> </a-lang>:
            <span v-if="!loadingBonus">
              {{ formatterNumber(maxAmount, false) }}
              {{ currencyView(store.state.currency) }}
            </span>
            <br /><br />
            <ul
              class="modal-footer login_menu navbar-right nav-sign justify-content-center"
            >
              <li>
                <a
                  class="header-btn text-white"
                  v-if="maxAmount > 0"
                  href="javascript:void(0)"
                  data-dismiss="modal"
                  @click="openGameSession()"
                  ><a-lang value="play"> </a-lang
                ></a>
              </li>
            </ul>
          </div>
        </div>

        <!-- TERMINATE OR CONTINUE -->
        <div
          class="modal-content back-dark"
          v-if="isReserve && isReserve == 'ko'"
        >
          <div class="modal-header">
            <h5 class="modal-title text-white" id="playModalLabel">
              <a-lang value="session-opened"> </a-lang> {{ title }}
            </h5>
            <button
              type="button"
              class="close text-white"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body subscribe sub-form text-white">
            <a-lang value="session-opened-1"> </a-lang>
          </div>
          <ul class="modal-footer login_menu navbar-right nav-sign">
            <li>
              <a
                class="header-btn text-white"
                href="javascript:void(0)"
                data-dismiss="modal"
                @click="openGameSession()"
              >
                <a-lang value="continue"> </a-lang>
              </a>
            </li>
            <li>
              <a
                class="header-btn text-white"
                href="javascript:void(0)"
                @click="closeGameSession()"
              >
                <a-lang value="terminate"> </a-lang>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  props: ["background", "category", "gameIdPlay"],
  mixins: [common],
  data() {
    return {
      gamesAll: [],
      gamesPopular: [],
      gamesNew: [],
      amount: 0,
      previousAmount: 0,
      balances: [],
      maxAmount: 0,
      type: "REAL",
      currency: store.state.currency,
      amountM: 0,
      amountBonusM: 0,
      gameId: null,
      title: null,
      gameParams: null,
      launcherUrl: null,
      categories: [],
      categorySelected: "all",
      platform: "ALL",
      platforms: [],
      order: "DEFAULT",
      search: null,
      isReserve: null,
      sessionIdToClose: null,
      bonuses: [],
      bonus: null,
      // pagination
      currentPage: 1,
      recordsPerPage: 48,
      gamesPaginated: [],
      numGamesRemaining: null,
      loadingBonus: false,
      categoryBonus: null,
      gamesIdBonus: null,
    };
  },
  mounted() {
    this.getGames();
  },
  methods: {
    getGames() {
      this.loading = true;
      this.$http
        .get("games")
        .then((response) => {
          this.gamesAll = response.data.gamesAll;
          this.gamesPopular = response.data.gamesPopular;
          this.gamesNew = response.data.gamesNew;
          this.categories = response.data.categories;

          let gscodes = [];
          if (this.gamesAll)
            gscodes = this.gamesAll.map((g) => g.gameParams).gscode;
          this.platforms = Array.from(new Set(gscodes)).sort();

          if (this.category) {
            //$("[href='#pills-" + this.category + "']").tab("show");
            this.resetPage(this.gamesAll, this.category);
          } else this.resetPage(this.gamesAll, "all");

          if (this.gameIdPlay && this.isUserLogged()) {
            let game = this.gamesAll.find((g) => g.gameID == this.gameIdPlay);
            $("#playModal").modal("show");
            this.getSessions(
              this.gameIdPlay,
              game.name,
              game.gameParams,
              game.category
            );
          }
        })
        .catch((response) => {
          this.showError(response, this.getLocate("game-list-1"));
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getBonuses() {
      this.loadingBonus = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("bonus/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.bonuses = result.bonuses;
            if (this.bonuses && this.bonuses.length > 0)
              this.bonuses = [this.bonuses[0]];
            this.bonuses = this.bonuses.filter((b) => {
              if (b.games_rules) {
                let jsonParsed = JSON.parse(b.games_rules);
                let found = false;
                if (jsonParsed.categories) {
                  jsonParsed.categories.forEach((element) => {
                    if (
                      this.categoryBonus
                        .toUpperCase()
                        .includes(element.toUpperCase())
                    )
                      found = true;
                  });
                }

                if (jsonParsed.games) {
                  jsonParsed.games.forEach((element) => {
                    if (
                      element.toUpperCase() == this.gamesIdBonus.toUpperCase()
                    )
                      found = true;
                  });
                }

                return found;
              }
              return true;
            });

            //creo una copia dell'array di risposta
            let bonuses = this.bonuses;
            //prendo il primo bonus
            let bonus = bonuses[0];

            if (bonus) {
              let expiredts = new Date(bonus.expiredts);
              let now = new Date();
              //caso 1: 0% obiettivo raggiunto e balance G = 0
              if (
                bonus.percentage == 0 &&
                bonus.promobalance == 0 &&
                now <= expiredts
              ) {
                let formData = new FormData();
                formData.append("usertoken", store.state.userId);
                formData.append("sessiontoken", store.state.sessionId);
                formData.append("promoid", bonus.promoid);
                this.$http
                  .post("/bonus/grant/amount", formData)
                  .then((response) => {
                    let result = response.data;
                    this.forceLogout(result.result);
                    this.getBalances(true);
                  });
              }
              //caso 2: 100% obiettivo raggiunto effettuo il redeem del bonus, accredito i soldi del balance G nel prelevabile dell'utente
              else if (bonus.percentage >= 100) {
                this.redeemBonus(bonus.promoid);
              }
              //caso 3: sono nel mezzo dell'utilizzo di quel bonus quindi devo continuare ad usarlo se non è scaduto
              else if (
                bonus.percentage >= 0 &&
                bonus.percentage < 100 &&
                bonus.promobalance > 0
              ) {
                if (now <= expiredts) {
                  // non fa nulla
                } else {
                  //effettuo il redeem (lato server c'è il controllo per l'expired quindi verrà solo azzerato il balance G e chiuso il bonus)
                  this.redeemBonus(bonus.promoid);
                }
              }
              //caso 4: stavo usando quel bonus ma ho finito il promobalance quindi non raggiungerò mai l'obiettivo, chiudo il bonus senza movimenti
              else if (
                bonus.percentage >= 0 &&
                bonus.percentage < 100 &&
                bonus.promobalance == 0
              ) {
                this.redeemBonus(bonus.promoid);
              }
            } else this.maxAmount = 0;
            this.bonuses = [];
            if (bonus) this.bonuses.push(bonus);
            if (this.bonuses && this.bonuses.length > 0) {
              this.bonus = this.bonuses[0].promoid;
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
        })
        .finally(() => {
          this.loadingBonus = false;
        });
    },
    redeemBonus(promoid) {
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("promoid", promoid);
      this.$http
        .post("bonus/redeem", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getBonuses();
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
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
              if (this.isPromoGamesUsers()) this.type = sessions[0].type;
              else this.type = "REAL";
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
    subAmount() {
      if (this.amount > 0) {
        let amount = this.amount - 0.01;
        this.amount = Number(amount.toFixed(2));
      }
    },
    addAmount() {
      if (this.amount < this.maxAmount) {
        let amount = this.amount + 0.01;
        this.amount = Number(amount.toFixed(2));
      }
    },
    handleInput(e) {
      let amount = e.target.value;

      if (Number(amount) > Number(this.maxAmount)) {
        this.amount = this.previousAmount;
      }
      if (Number(amount) < 0) {
        this.amount = this.previousAmount;
      }
      let stringValue = amount.toString();
      let regex = /^\s*[0-9]\d*(\.\d{1,2})?\s*$/;
      if (!regex.test(stringValue) && this.amount !== "") {
        this.amount = this.previousAmount;
      }

      this.previousAmount = this.amount;
    },
    /*
    prevPage(games, type) {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.changePage(this.currentPage, games, type);
      }
    },*/
    nextPage(games, type) {
      games = this.gamesFiltered(games, type);

      if (this.currentPage < this.numPages(games)) {
        this.currentPage++;
        this.changePage(this.currentPage, games, type);
      }
    },
    resetParams() {
      this.platform = "ALL";
      this.order = "DEFAULT";
      this.search = null;
    },
    resetPage(games, type) {
      games = this.gamesFiltered(games, type);
      this.sortGames(games);
      this.categorySelected = type;
      this.gamesPaginated = [];
      this.currentPage = 1;
      this.changePage(1, games, type);
    },
    resetSelectedPage() {
      if (this.categorySelected != "popular" && this.categorySelected != "new")
        this.resetPage(this.gamesAll, this.categorySelected);
      else if (this.categorySelected == "popular")
        this.resetPage(this.gamesPopular, this.categorySelected);
      else if (this.categorySelected == "new")
        this.resetPage(this.gamesNew, this.categorySelected);
    },
    gamesFiltered(games, type) {
      // filtro per categoria
      if (type != "all" && type != "popular" && type != "new")
        games = games.filter((g) => this.filterCategory(g.category, type));
      // filtro per piattaforma
      if (this.platform != "ALL") {
        games = games.filter((g) => g.gameParams.gscode == this.platform);
      }
      // filtro per name
      if (this.search)
        games = games.filter((g) =>
          g.name.toUpperCase().includes(this.search.toUpperCase())
        );
      return games;
    },
    numPages(games) {
      if (!games) games = [];
      return Math.ceil(games.length / this.recordsPerPage);
    },
    changePage(page, games, type) {
      this.loading = true;

      let btn_next = document.getElementById("btn_next_" + type);
      //let btn_prev = document.getElementById("btn_prev_" + type);
      //let page_span = document.getElementById("page_" + type);

      // Validate page
      if (page < 1) page = 1;
      if (page > this.numPages(games)) page = this.numPages();

      //this.gamesPaginated = [];
      for (
        let i = (page - 1) * this.recordsPerPage;
        i < page * this.recordsPerPage;
        i++
      ) {
        if (games[i]) this.gamesPaginated.push(games[i]);
      }
      this.numGamesRemaining = games.length - this.gamesPaginated.length;

      //page_span.innerHTML = page;
      /*
      if (page == 1) {
        btn_prev.style.display = "none";
      } else {
        btn_prev.style.display = "inline";
      }*/

      if (page == this.numPages(games)) {
        if (btn_next) btn_next.style.display = "none";
      } else {
        if (btn_next) btn_next.style.display = "inline";
      }

      this.loading = false;
    },
    sortGames(games) {
      let date = new Date().getTime();
      if (this.order == "DEFAULT") {
        games.sort((a, b) => {
          let aOrder = a.gameParams.order;
          let bOrder = b.gameParams.order;
          if (!aOrder) aOrder = a.gameID;
          else aOrder = 99999999 - aOrder;
          if (!bOrder) bOrder = b.gameID;
          else bOrder = 99999999 - bOrder;
          return bOrder - aOrder;
        });
      } else if (this.order == "NEW") {
        games.sort((a, b) => {
          let aOrder = a.gameID;
          let bOrder = b.gameID;
          return bOrder - aOrder;
        });
      } else if (this.order == "A-Z") {
        games.sort((a, b) => {
          let aOrder = a.name;
          let bOrder = b.name;
          if (aOrder > bOrder) return 1;
          else return -1;
        });
      } else if (this.order == "Z-A") {
        games.sort((a, b) => {
          let aOrder = a.name;
          let bOrder = b.name;
          if (aOrder < bOrder) return 1;
          else return -1;
        });
      }
    },
    replaceImgByDefault(e) {
      e.target.src = "images/games_default.png";
    },
    filterCategory(category, c) {
      return category.split(",").some((e) => e == c);
    },
  },
  components: {
    vueSlider: window["vue-slider-component"],
  },
};
</script>
