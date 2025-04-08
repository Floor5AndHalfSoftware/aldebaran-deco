<template>
  <div>
    <section id="bingoId" class="sfondo-2">
      <a-banner v-if="isDesktop()" category="banner bingo"></a-banner>
      <br />
      <div class="container">
        <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
        <a-loading :show="loading"></a-loading>
        <!--
        <div class="container control section" v-if="!loading">
          <div class="row">
            <div class="col-lg-6">
              <div class="heading">
                <h2>
                  <a-lang value="game-features"> </a-lang>
                </h2>
                <hr class="hr" />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-3 col-md-6">
              <div
                class="row justify-content-center text-center"
                v-if="bingoSpecialItems"
              >
                <div class="row">
                  <div class="col-12">
                    <div
                      class="control-img"
                      style="width: 120px; height: 120px"
                    >
                      <i class="fa flaticon-bill"></i>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-12">
                    <br />
                    <div class="control-text text-center">
                      <h3>
                        <span v-html="bingoSpecialItems.name"></span>
                      </h3>
                      <p>
                        <span v-html="bingoSpecialItems.content"></span>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-lg-3 col-md-6">
              <div
                class="row justify-content-center text-center"
                v-if="bingoProgressiveGames"
              >
                <div class="row">
                  <div class="col-12">
                    <div
                      class="control-img"
                      style="width: 120px; height: 120px"
                    >
                      <i class="fa flaticon-money"></i>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-12">
                    <br />
                    <div class="control-text text-center">
                      <h3>
                        <span v-html="bingoProgressiveGames.name"></span>
                      </h3>
                      <p>
                        <span v-html="bingoProgressiveGames.content"></span>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-lg-3 col-md-6">
              <div
                class="row justify-content-center text-center"
                v-if="bingoApparence"
              >
                <div class="row">
                  <div class="col-12">
                    <div
                      class="control-img"
                      style="width: 120px; height: 120px"
                    >
                      <i class="fa fa-eye"></i>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-12">
                    <br />
                    <div class="control-text text-center">
                      <h3>
                        <span v-html="bingoApparence.name"></span>
                      </h3>
                      <p>
                        <span v-html="bingoApparence.content"></span>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-lg-3 col-md-6">
              <div
                class="row justify-content-center text-center"
                v-if="bingoPersonalization"
              >
                <div class="row">
                  <div class="col-12">
                    <div
                      class="control-img"
                      style="width: 120px; height: 120px"
                    >
                      <i class="fa fa-cogs"></i>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-12">
                    <br />
                    <div class="control-text text-center">
                      <h3>
                        <span v-html="bingoPersonalization.name"></span>
                      </h3>
                      <p>
                        <span v-html="bingoPersonalization.content"></span>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        -->
      </div>
    </section>
    <div v-if="!loading">
      <div class="row" v-if="bingoSaleOfCartons1">
        <div class="col-lg-5 col-md-5" style="background-color: #181434">
          <div class="row justify-content-center">
            <div class="col-2"></div>
            <div class="col-8" style="padding-top: 40px; padding-bottom: 40px">
              <div class="heading white">
                <h2>
                  <span v-html="bingoSaleOfCartons1.name"></span>
                </h2>
                <hr class="hr hr-white hr-no-margin-left" />
              </div>
              <span v-html="bingoSaleOfCartons1.content"></span>
            </div>
          </div>
        </div>
        <div class="col-lg-7 col-md-7" style="background-color: #f2f2f2">
          <div class="row justify-content-center">
            <img
              src="images/venta-info-del-jugador.png"
              class="img-responsive"
              alt=""
            />
          </div>
        </div>
      </div>
      <div class="row" v-if="bingoSaleOfCartons2">
        <div class="col-lg-7 col-md-7" style="background-color: #f2f2f2">
          <div class="row justify-content-center">
            <img
              src="images/venta-que-puede-hacer-el-jugador.png"
              class="img-responsive"
              alt=""
            />
          </div>
        </div>
        <div class="col-lg-5 col-md-5" style="background-color: #181434">
          <div class="row justify-content-center">
            <div class="col-2"></div>
            <div class="col-8" style="padding-top: 40px; padding-bottom: 40px">
              <div class="heading white">
                <h2>
                  <span v-html="bingoSaleOfCartons2.name"></span>
                </h2>
                <hr class="hr hr-white hr-no-margin-left" />
              </div>
              <span v-html="bingoSaleOfCartons2.content"></span>
            </div>
          </div>
        </div>
      </div>
      <div class="row" v-if="bingoInGame1">
        <div class="col-lg-5 col-md-5" style="background-color: #181434">
          <div class="row justify-content-center">
            <div class="col-2"></div>
            <div class="col-8" style="padding-top: 40px; padding-bottom: 40px">
              <div class="heading white">
                <h2>
                  <span v-html="bingoInGame1.name"></span>
                </h2>
                <hr class="hr hr-white hr-no-margin-left" />
              </div>
              <span v-html="bingoInGame1.content"></span>
            </div>
          </div>
        </div>
        <div class="col-lg-7 col-md-7" style="background-color: #f2f2f2">
          <div class="row justify-content-center">
            <img
              src="images/juego-info-para-el-jugador.png"
              class="img-responsive"
              alt=""
            />
          </div>
        </div>
      </div>
      <div class="row" v-if="bingoInGame2">
        <div class="col-lg-7 col-md-7" style="background-color: #f2f2f2">
          <div class="row justify-content-center">
            <img
              src="images/juego-que-puede-hacer-el-jugador.png"
              class="img-responsive"
              alt=""
            />
          </div>
        </div>
        <div class="col-lg-5 col-md-5" style="background-color: #181434">
          <div class="row justify-content-center">
            <div class="col-2"></div>
            <div class="col-8" style="padding-top: 40px; padding-bottom: 40px">
              <div class="heading white">
                <h2>
                  <span v-html="bingoInGame2.name"></span>
                </h2>
                <hr class="hr hr-white hr-no-margin-left" />
              </div>
              <span v-html="bingoInGame2.content"></span>
            </div>
          </div>
        </div>
      </div>
      <section id="faq" class="faq sfondo-4">
        <br /><br />
        <div class="container">
          <div class="row justify-content-center text-center">
            <div class="col">
              <h2 class="text-white"><a-lang value="waiting-for"> </a-lang></h2>
              <br />
              <hr class="hr hr-white" style="margin-left: auto" />
              <h5 class="text-white">
                <a-lang value="waiting-for-1"> </a-lang>
              </h5>
              <br />
              <div class="casino-btn" v-if="isUserLogged()">
                <a :href="'#/games/play/36'" class="btn-4 yellow-btn">
                  <a-lang value="play"> </a-lang>
                </a>
              </div>
              <div class="casino-btn" v-if="!isUserLogged()">
                <a
                  href="javascript:void(0)"
                  @click="$emit('login')"
                  class="btn-4 yellow-btn"
                  data-toggle="modal"
                  data-target="#loginModal"
                >
                  <a-lang value="play-now"> </a-lang>
                </a>
              </div>
              <br /><br />
            </div>
          </div>
        </div>
      </section>
      <div class="row" v-if="bingoGamePersonalization">
        <div class="col-lg-5 col-md-5" style="background-color: #181434">
          <div class="row justify-content-center">
            <div class="col-2"></div>
            <div class="col-8" style="padding-top: 40px; padding-bottom: 40px">
              <div class="heading white">
                <h2>
                  <span v-html="bingoGamePersonalization.name"></span>
                </h2>
                <hr class="hr hr-white hr-no-margin-left" />
              </div>
              <span v-html="bingoGamePersonalization.content"></span>
            </div>
          </div>
        </div>
        <div class="col-lg-7 col-md-7" style="background-color: #f2f2f2">
          <br /><br />
          <div class="row justify-content-center">
            <img
              src="images/Personalizacion-del-juego.png"
              class="img-responsive"
              width="300"
              alt=""
            />
          </div>
        </div>
      </div>
      <section class="section sfondo-2">
        <div class="container">
          <div class="row" v-if="!loading">
            <div class="col">
              <div class="heading">
                <h2><a-lang value="change-view"> </a-lang></h2>
                <hr class="hr" />
                <span
                  class="justify-content-center text-center"
                  style="font-family: 'Montserrat', sans-serif"
                  ><a-lang value="change-view-1"> </a-lang
                ></span>
              </div>
            </div>
          </div>
          <div class="row justify-content-center text-center">
            <div class="col-md-4">
              <img src="images/3cartones.png" class="img-responsive" alt="" />
              <br />
              <span style="font-family: 'Montserrat', sans-serif"
                ><a-lang value="3cartons"></a-lang
              ></span>
            </div>
            <div class="col-md-4">
              <img src="images/12cartones.png" class="img-responsive" alt="" />
              <br />
              <span style="font-family: 'Montserrat', sans-serif"
                ><a-lang value="12cartons"></a-lang
              ></span>
            </div>
            <div class="col-md-4">
              <img
                src="images/carton-y-bolas.png"
                class="img-responsive"
                alt=""
              />
              <br />
              <span style="font-family: 'Montserrat', sans-serif"
                ><a-lang value="ball-cartons"></a-lang
              ></span>
            </div>
          </div>
          <br /><br />
        </div>
      </section>
      <div v-if="!loading">
        <div class="row" v-if="bingoAwards1">
          <div class="col-lg-5 col-md-5" style="background-color: #181434">
            <div class="row justify-content-center">
              <div class="col-2"></div>
              <div
                class="col-8"
                style="padding-top: 40px; padding-bottom: 40px"
              >
                <div class="heading white">
                  <h2>
                    <span v-html="bingoAwards1.name"></span>
                  </h2>
                  <hr class="hr hr-white hr-no-margin-left" />
                </div>
                <span v-html="bingoAwards1.content"></span>
              </div>
            </div>
          </div>
          <div class="col-lg-7 col-md-7" style="background-color: #f2f2f2">
            <br /><br />
            <div class="row justify-content-center">
              <img
                src="images/premio.png"
                class="img-responsive"
                alt=""
                width="300"
              />
            </div>
          </div>
        </div>
        <div class="row" v-if="bingoShoppingArea">
          <div class="col-lg-7 col-md-7" style="background-color: #f2f2f2">
            <div class="row justify-content-center">
              <img
                src="images/zona-de-compra.png"
                class="img-responsive"
                alt=""
              />
            </div>
          </div>
          <div class="col-lg-5 col-md-5" style="background-color: #181434">
            <div class="row justify-content-center">
              <div class="col-2"></div>
              <div
                class="col-8"
                style="padding-top: 40px; padding-bottom: 40px"
              >
                <div class="heading white">
                  <h2>
                    <span v-html="bingoShoppingArea.name"></span>
                  </h2>
                  <hr class="hr hr-white hr-no-margin-left" />
                </div>
                <span v-html="bingoShoppingArea.content"></span>
              </div>
            </div>
          </div>
        </div>
        <div class="row" v-if="bingoAwards1">
          <div class="col-lg-5 col-md-5" style="background-color: #181434">
            <div class="row justify-content-center">
              <div class="col-2"></div>
              <div
                class="col-8"
                style="padding-top: 40px; padding-bottom: 40px"
              >
                <div class="heading white">
                  <h2>
                    <span v-html="bingoAwards1.name"></span>
                  </h2>
                  <hr class="hr hr-white hr-no-margin-left" />
                </div>
                <span v-html="bingoAwards1.content"></span>
              </div>
            </div>
          </div>
          <div class="col-lg-7 col-md-7" style="background-color: #f2f2f2">
            <br /><br />
            <div class="row justify-content-center">
              <img
                src="images/extraccion-de-bolas.png"
                class="img-responsive"
                alt=""
                width="200"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {
      bingoPresentation: null,
      bingoSpecialItems: null,
      bingoProgressiveGames: null,
      bingoApparence: null,
      bingoPersonalization: null,
      bingoSaleOfCartons1: null,
      bingoSaleOfCartons2: null,
      bingoInGame1: null,
      bingoInGame2: null,
      bingoGamePersonalization: null,
      bingoAwards1: null,
      bingoShoppingArea: null,
      bingoAwards2: null,
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
      formData.append("status", 1);
      this.$http
        .post("cms/text/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            let faqs = result.contents;
            faqs.forEach((e) => {
              switch (e.category_id) {
                case "presentation":
                  this.bingoPresentation = e;
                  break;
                case "special items":
                  this.bingoSpecialItems = e;
                  break;
                case "progressive games":
                  this.bingoProgressiveGames = e;
                  break;
                case "apparence":
                  this.bingoApparence = e;
                  break;
                case "personalization":
                  this.bingoPersonalization = e;
                  break;
                case "sale of cartons 1":
                  this.bingoSaleOfCartons1 = e;
                  break;
                case "sale of cartons 2":
                  this.bingoSaleOfCartons2 = e;
                  break;
                case "in game 1":
                  this.bingoInGame1 = e;
                  break;
                case "in game 2":
                  this.bingoInGame2 = e;
                  break;
                case "game personalization":
                  this.bingoGamePersonalization = e;
                  break;
                case "awards 1":
                  this.bingoAwards1 = e;
                  break;
                case "shopping area":
                  this.bingoShoppingArea = e;
                  break;
                case "awards 2":
                  this.bingoAwards2 = e;
                  break;
                default:
                  break;
              }
            });
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