<template>
  <div>
    <div v-if="!loading">
      <div class="row">
        <div class="col-6 col-loteria">
          <div class="form-group">
            <p class="profile-input-label">
              <a-lang value="date-from"> </a-lang>
            </p>
            <input
              name="sessionDateFrom"
              v-model="sessionDateFrom"
              id="sessionDateFrom"
              class="form-control modal-custom-input"
              type="date"
              required
              @change="getGameSessions()"
            />
          </div>
        </div>
        <div class="col-6 col-loteria">
          <div class="form-group">
            <p class="profile-input-label">
              <a-lang value="date-to"> </a-lang>
            </p>
            <input
              name="sessionDateTo"
              v-model="sessionDateTo"
              id="sessionDateTo"
              class="form-control modal-custom-input"
              type="date"
              required
              @change="getGameSessions()"
            />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-4 col-6 col-loteria">
          <div class="form-group">
            <label for="sessionState"
              ><p class="profile-input-label">
                <a-lang value="session-state"> </a-lang></p
            ></label>
            <select
              class="form-control modal-custom-input"
              style="color: 'black'"
              id="sessionState"
              v-model="state"
            >
              <option value="ALL">{{ this.getLocate("all-games") }}</option>
              <option value="OPEN">
                {{ this.getLocate("open") }}
              </option>
              <option value="CLOSE">
                {{ this.getLocate("close") }}
              </option>
            </select>
          </div>
        </div>
        <div class="col-md-4 col-6 col-loteria">
          <div class="form-group">
            <label for="sessionType"
              ><p class="profile-input-label">
                <a-lang value="type"> </a-lang></p
            ></label>
            <select
              class="form-control modal-custom-input"
              style="color: 'black'"
              id="sessionType"
              v-model="type"
            >
              <option value="ALL">{{ this.getLocate("all-games") }}</option>
              <option value="WIN">{{ this.getLocate("win") }}</option>
            </select>
          </div>
        </div>
        <div class="col-md-4 col-6 col-loteria">
          <div class="form-group">
            <label for="gamesType"
              ><p class="profile-input-label">
                <a-lang value="game-type"> </a-lang></p
            ></label>
            <select
              class="form-control modal-custom-input"
              style="color: 'black'"
              id="gamesType"
              v-model="gameType"
            >
              <option value="ALL">{{ this.getLocate("all-games") }}</option>
              <option v-for="gc in gameCategories" :key="gc">
                {{ gc }}
              </option>
            </select>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-12 col-loteria">
          <div class="card">
            <no-result
              v-if="gameSessionsFiltered && gameSessionsFiltered.length == 0"
            ></no-result>

            <div id="session" class="list-group scroll-list">
              <div
                class="list-group-item list-group-item-action my-align-left box-list-element"
                v-for="item in gameSessionsFiltered"
                :key="item.session_id"
              >
                <div class="row">
                  <div class="col-10">
                    <small>
                      <i class="fa fa-calendar"></i>
                      <span v-if="item.ts_open != null">
                        {{ formatDateYYYYMMDDHHmmss(item.ts_open) }}
                      </span>
                      <a-lang value="state"></a-lang>:
                      <b v-if="item.state == 'OPEN'" style="color: green"
                        >OPEN</b
                      >
                      <b v-else-if="item.state == 'CLOSED'" style="color: red"
                        >CLOSE</b
                      >
                      <b
                        v-else-if="item.state == 'ERROR'"
                        style="color: #fdb200"
                        >ERROR</b
                      >
                    </small>
                    <span style="margin-left: 10px">
                      <button
                        class="btn btn-link-blue"
                        v-if="item.state == 'OPEN'"
                        @click.stop=""
                        @click="closeSession(item.session_id)"
                      >
                        <a-lang value="close-session"> </a-lang>
                      </button>
                    </span>
                    <small v-if="item.ts_close != null">
                      <i class="fa fa-calendar-times-o"></i>
                      {{ formatDateYYYYMMDDHHmmss(item.ts_close) }}
                    </small>
                    <div
                      class="d-flex w-100 justify-content-between"
                      v-if="item.session_id != null"
                    >
                      <b><a-lang value="session-id"> </a-lang>:</b
                      ><small>{{ item.session_id }}</small>
                    </div>
                    <div
                      class="d-flex w-100 justify-content-between"
                      v-if="item.game != null"
                    >
                      <b><a-lang value="game"> </a-lang>:</b
                      ><small>{{ item.game }}</small>
                    </div>
                    <div
                      class="d-flex w-100 justify-content-between"
                      v-if="item.type != null"
                    >
                      <b><a-lang value="type"> </a-lang>:</b
                      ><small>{{ item.type }}</small>
                    </div>
                    <div
                      class="d-flex w-100 justify-content-between"
                      v-if="item.rounds != null"
                    >
                      <b><a-lang value="rounds"> </a-lang>:</b
                      ><small>{{ item.rounds }}</small>
                    </div>
                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="balance"> </a-lang>:</b
                      ><small
                        >{{ formatterNumber(item.balance, true) }}
                        {{ currencyView(item.currency) }}</small
                      >
                    </div>
                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="bet"> </a-lang>:</b
                      ><small
                        >{{ formatterNumber(item.bet, true) }}
                        {{ currencyView(item.currency) }}</small
                      >
                    </div>
                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="win"> </a-lang>:</b
                      ><small
                        >{{ formatterNumber(item.win, true) }}
                        {{ currencyView(item.currency) }}</small
                      >
                    </div>
                  </div>
                  <div class="col-2">
                    <a
                      class="no-style-a"
                      data-toggle="modal"
                      data-target="#sessionDetails"
                      href="#"
                      @click="getSessionDetails(item.session_id)"
                    >
                      <div class="box-list-detail-button">
                        <i class="fa fa-arrow-right"></i>
                      </div>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="modal fade"
      id="sessionDetails"
      tabindex="-1"
      role="dialog"
      aria-labelledby="sessionDetailsLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content modal-custom-content back-light">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">
              <p style="color: #fff">
                <a-lang value="session-detail"> </a-lang>
              </p>
            </h5>
            <button
              type="button"
              class="close loteria-text-black"
              @click="$('#sessionDetails').modal('hide')"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <no-result
              v-if="sessionDetails && sessionDetails.length == 0"
            ></no-result>
            <div id="session-detail" class="list-group scroll-list">
              <a
                data-toggle="modal"
                data-target="#sessionDetails"
                href="#"
                class="list-group-item list-group-item-action my-align-left box-list-element"
                v-for="item in sessionDetails"
                :key="item.round"
              >
                <small>
                  <i class="fa fa-calendar"></i>
                  <span v-if="item.ts != null">{{
                    formatDateYYYYMMDDHHmmss(item.ts)
                  }}</span>
                </small>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.round != null"
                >
                  <b><a-lang value="round"> </a-lang>:</b
                  ><small>{{ item.round }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.transaction != null"
                >
                  <b><a-lang value="transaction"> </a-lang>:</b
                  ><small>{{ item.transaction }}</small>
                </div>
                <div
                  class="d-flex w-100 justify-content-between"
                  v-if="item.type != null"
                >
                  <b><a-lang value="type"> </a-lang>:</b
                  ><small>{{ item.type }}</small>
                </div>
                <div class="d-flex w-100 justify-content-between">
                  <b><a-lang value="amount"> </a-lang>:</b
                  ><small
                    >{{ formatterNumber(item.amount, true) }}
                    {{ currencyView(item.currency) }}</small
                  >
                </div>
              </a>
            </div>
          </div>
          <div class="modal-footer"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["loading"],
  data() {
    return {
      //generici
      error: false,
      errorMessage: "",

      //games history
      gameSessionsHistory: [],
      sessionDetails: [],
      state: "ALL",
      type: "ALL",
      gameType: "ALL",
      gameCategories: [],
      sessionDateFrom: this.formatDateYYYYMMDD(this.addDays(new Date(), -1)),
      sessionDateTo: this.formatDateYYYYMMDD(new Date()),
    };
  },
  created() {
    if (this.isUserLogged()) {
      this.getGameCategories();
      this.getGameSessions();
    }
  },
  computed: {
    gameSessionsFiltered() {
      let gameSessionsHistory = this.gameSessionsHistory;

      gameSessionsHistory = gameSessionsHistory.filter((c) => {
        if (this.state == "ALL") return true;
        if (c.state.toString().toUpperCase().includes(this.state.toUpperCase()))
          return true;
        else return false;
      });

      gameSessionsHistory = gameSessionsHistory.filter((c) => {
        if (this.type == "ALL") return true;
        if (c.win != null && c.bet != null && c.win > c.bet) return true;
        else return false;
      });

      gameSessionsHistory = gameSessionsHistory.filter((c) => {
        if (this.gameType == "ALL") return true;
        if (
          c.game_type
            .toString()
            .toUpperCase()
            .includes(this.gameType.toUpperCase())
        )
          return true;
        else return false;
      });

      gameSessionsHistory.sort(function (a, b) {
        let dataOrdinamento1;
        let dataOrdinamento2;
        if (a.ts_close != null) {
          dataOrdinamento1 = new Date(a.ts_close);
        } else {
          dataOrdinamento1 = new Date(a.ts_open);
        }
        if (b.ts_close != null) {
          dataOrdinamento2 = new Date(b.ts_close);
        } else {
          dataOrdinamento2 = new Date(b.ts_open);
        }
        if (dataOrdinamento1 < dataOrdinamento2) {
          return 1;
        }
        if (dataOrdinamento1 > dataOrdinamento2) {
          return -1;
        }
        return 0;
      });
      return gameSessionsHistory;
    },
  },
  methods: {
    getGameSessions() {
      this.resetMessages();
      this.gameSessionsHistory = [];
      if (!this.checkDateFilter(this.sessionDateTo, this.sessionDateFrom))
        return;

      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.sessionDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.sessionDateTo, 1))
      );
      this.$http
        .post("session/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.gameSessionsHistory = result.sessions;
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
          this.changeLoading(false);
        });
    },
    getSessionDetails(sessionId) {
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("sessionid", sessionId);
      this.$http
        .post("session/detail", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.sessionDetails = result.rounds;
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    closeSession(sessionId) {
      this.error = false;
      this.success = false;
      this.errorMessage = "";
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("sessionid", sessionId);
      this.$http
        .post("session/close", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getGameSessions();
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    checkDateFilter(date1, date2) {
      if (!date1 || !date2) {
        this.setError(this.getLocate("check-date-4"));
        return false;
      }

      const MAX_DAYS_VIEW = 30;
      this.error = false;
      this.errorMessage = null;
      const diffDays = this.differenceTwoDate(date1, date2);
      if (diffDays > MAX_DAYS_VIEW) {
        this.setError(
          this.getLocate("check-date-1") +
            " " +
            MAX_DAYS_VIEW +
            " " +
            this.getLocate("check-date-2")
        );
        return false;
      }
      if (diffDays < 0) {
        this.setError(this.getLocate("check-date-3"));
        return false;
      }
      return true;
    },
    changeLoading(val) {
      this.$emit("change-loading", val);
    },
    resetMessages() {
      this.$emit("reset-messages");
    },
    getGameCategories() {
      this.$http
        .get("gameCategories")
        .then((response) => {
          this.gameCategories = response.data.categories;
        })
        .catch((response) => {
          this.showError(response, this.getLocate("login-7"));
        });
    },
  },
};
</script>
