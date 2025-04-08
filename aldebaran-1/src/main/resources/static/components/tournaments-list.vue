<template>
  <div>
    <section id="contact-page" class="contact-page">
      <div style="position: relative">
        <img
          v-if="tournament.image"
          :src="'data:' + tournament.mime_type + ';base64,' + tournament.image"
          style="width: 100%"
        />
      </div>
      <br />
      <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
      <div class="container">
        <div class="row">
          <div class="col-md-8">
            <div class="heading" style="color: #fff">
              <h2><a-lang value="tournament-title"> </a-lang></h2>
              <h6>
                <a-lang value="update-every-hour"> </a-lang>
              </h6>
              <h7 v-if="lastUpdate">
                <a-lang value="last-update"> </a-lang>
                {{ getLastUpdate() }}
              </h7>
            </div>
            <a-loading :show="loading"></a-loading>
            <div v-if="tournamentsList.length == 0 && !loading">
              <a-alert
                v-if="new Date() < new Date(startDate)"
                :alert="{
                  type: 'alert-success',
                  message: this.getLocate('message-tournament').replace(
                    '.',
                    this.startDate.substring(8, 10) +
                      '/' +
                      this.startDate.substring(5, 7) +
                      ' ' +
                      this.startDate.substring(11, 16) +
                      '.'
                  ),
                }"
              ></a-alert>
              <a-alert
                v-else-if="
                  new Date() >= new Date(startDate) &&
                  new Date() <= new Date(endDate)
                "
                :alert="{
                  type: 'alert-success',
                  message: this.getLocate('message-tournament-in-progress'),
                }"
              ></a-alert>
              <a-alert
                v-else-if="new Date() > new Date(endDate)"
                :alert="{
                  type: 'alert-success',
                  message: this.getLocate('message-tournament-end'),
                }"
              ></a-alert>
            </div>
            <div
              class="row score-slick"
              v-for="(e, index) in tournamentsList"
              :key="index"
            >
              <div class="col-md-12 scl_pad">
                <div class="row">
                  <div class="col-lg-7 col-8 col-md-8">
                    <div class="winner-name tun_border">
                      <h5 style="width: 334px">
                        {{ index + 1 }}. {{ e.firstname }}
                        {{ e.lastname.substring(0, 1) }}.
                      </h5>
                    </div>
                  </div>
                  <div class="col-lg-3 col-4 col-md-4 text-right">
                    <div class="winner-name tun_border">
                      <p>{{ formatterNumber(e.points, true) }}</p>
                    </div>
                  </div>
                </div>
                <img
                  src="images/taunament-border.png"
                  alt="border"
                  class="img-fluid jack-bor"
                />
              </div>
            </div>
          </div>
          <div class="col-md-3 justify-content-center text-center">
            <div class="heading" style="color: #fff">
              <h2><a-lang value="countdown"> </a-lang></h2>
            </div>
            <div class="row counter-jack">
              <div class="col-lg-12 text-center">
                <div class="coundown">
                  <span class="count-down4"></span>
                  <p>Días</p>
                </div>
                <div class="coundown count-dot">
                  <span class="count-down"></span>
                  <p>Horas</p>
                </div>
                <div class="coundown count-dot">
                  <span class="count-down2"></span>
                  <p style="font-size: 26px">Minutos</p>
                </div>
                <div class="coundown">
                  <span class="count-down3"></span>
                  <p style="font-size: 26px">Segundos</p>
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
  props: ["subpath"],
  data() {
    return {
      tournament: {},
      tournamentsList: [],
      lastUpdate: null,
      startDate: null,
      endDate: null,
    };
  },
  created() {
    this.loadTournament();
  },
  mounted() {},
  methods: {
    async loadTournament() {
      try {
        this.loading = true;
        let formData = new FormData();
        formData.append("subpath", this.subpath);
        let response = await this.$http.post("tournament/get", formData);
        let result = response.data;
        this.forceLogout(result.result);
        if (result.result == 0) {
          this.tournament = result.tournament;
          if (this.tournament.enabled == 0) location.href = "#/";
          else {
            this.startDate = this.formatDateYYYYMMDDHHmmss(
              this.convertUTCDateToLocalDate(
                new Date(
                  this.tournament.start_date +
                    "T" +
                    this.tournament.start_time +
                    ":00"
                )
              )
            );
            this.endDate = this.formatDateYYYYMMDDHHmmss(
              this.convertUTCDateToLocalDate(
                new Date(
                  this.tournament.end_date +
                    "T" +
                    this.tournament.end_time +
                    ":00"
                )
              )
            );
            await this.getTournamentsList();
            if (new Date() >= new Date(this.startDate))
              await this.loadCountdown();
          }
        } else {
          this.alert = { type: "alert-danger", message: result.description };
          location.href = "#/";
        }
      } catch (response) {
        this.showError(response);
      } finally {
        this.loading = false;
      }
    },
    loadCountdown() {
      const finishDate = this.endDate.replaceAll("-", "/");
      $(".count-down4").countdown(finishDate, function (event) {
        $(this).html(event.strftime("%D"));
      });
      $(".count-down").countdown(finishDate, function (event) {
        $(this).html(event.strftime("%H"));
      });
      $(".count-down2").countdown(finishDate, function (event) {
        $(this).html(event.strftime("%M"));
      });
      $(".count-down3").countdown(finishDate, function (event) {
        $(this).html(event.strftime("%S"));
      });
    },
    async getTournamentsList() {
      try {
        this.loading = true;
        let formData = new FormData();
        formData.append(
          "start_date",
          this.tournament.start_date + " " + this.tournament.start_time
        );
        formData.append(
          "end_date",
          this.tournament.end_date + " " + this.tournament.end_time
        );
        formData.append("subpath", this.subpath);
        let response = await this.$http.post("tournaments/list", formData);
        let result = response.data;
        this.forceLogout(result.result);
        if (result.result == 0) {
          this.tournamentsList = result.data;
          this.lastUpdate = result.lastupdate.lastupdate;
        } else {
          this.alert = { type: "alert-danger", message: result.description };
        }
      } catch (response) {
        this.showError(response);
      } finally {
        this.loading = false;
      }
    },
    getLastUpdate() {
      if (this.lastUpdate)
        return (
          this.formatDateYYYYMMDDHHmmss(this.lastUpdate).substring(0, 13) +
          ":59"
        );
      return null;
    },
  },
};
</script>