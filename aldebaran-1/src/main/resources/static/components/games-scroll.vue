<template>
  <div
    id="sfondoCasino"
    style="display: flex"
    :class="{
      'sfondo-casino': background == undefined,
      '': background == 'no',
    }"
  >
    <section id="project-img" class="project-img games-page">
      <div class="desktopimage" v-if="!fromHome">
        <div class="row" style="margin-left: 10px; margin-right: 10px">
          <div class="col-12" style="padding-left: 2px; padding-right: 2px">
            <a-banner
              category="banner games"
              name="banner0"
              v-if="isDesktop()"
            ></a-banner>
          </div>
          <div class="col-4" style="padding-left: 2px; padding-right: 2px">
            <a-banner
              category="banner games new left"
              name="banner1"
              v-if="isDesktop()"
            ></a-banner>
          </div>
          <div class="col-4" style="padding-left: 2px; padding-right: 2px">
            <a-banner
              category="banner games new center"
              name="banner2"
              v-if="isDesktop()"
            ></a-banner>
          </div>
          <div class="col-4" style="padding-left: 2px; padding-right: 2px">
            <a-banner
              category="banner games new right"
              name="banner3"
              v-if="isDesktop()"
            ></a-banner>
          </div>
        </div>
      </div>
      <div class="mobileimage tabletimage" v-if="!fromHome">
        <a-banner
          category="banner games (mobile)"
          name="banner4"
          v-if="!isDesktop()"
        ></a-banner>
      </div>
      <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
      <div>
        <a-loading :show="loading"></a-loading>
        <br class="desktopimage" />
        <!-- MENU CASINO CENTER DESKTOP E MOBILE -->
        <div v-if="!fromHome">
          <div
            class="row justify-content-center text-center centralCasinoBar"
            style="margin: 0px"
            v-if="!loading"
          >
            <div class="col-lg-9 m-auto col-12 text-center button-group">
              <ul
                class="nav nav-pills scrollbar-style"
                id="pills-tab"
                role="tablist"
                style="
                  overflow: auto;
                  white-space: nowrap;
                  display: inline-flex;
                "
              >
                <li class="nav-item menuCasino1" v-if="gamesAll.length > 0">
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
                        src="images/games_all.svg"
                        style="width: 50px; height: 50px; min-width: 50px"
                        alt="games all"
                      />
                    </div>
                    <a-lang value="all"> </a-lang>
                  </a>
                </li>
                <li class="nav-item menuCasino1" v-if="gamesPopular.length > 0">
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
                        src="images/games_popular.svg"
                        style="width: 50px; height: 50px; min-width: 50px"
                        alt="games popular"
                      />
                    </div>
                    <a-lang value="popular"> </a-lang>
                  </a>
                </li>
                <li class="nav-item menuCasino1" v-if="gamesNew.length > 0">
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
                        src="images/games_new.svg"
                        style="width: 50px; height: 50px; min-width: 50px"
                        alt="games new"
                      />
                    </div>
                    <a-lang value="new"> </a-lang>
                  </a>
                </li>
                <li
                  class="nav-item menuCasino1"
                  v-for="c in carouselCategories"
                  :key="c"
                >
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
                        :src="'images/games_' + c + '.svg'"
                        @error="replaceImgByDefault"
                        style="width: 50px; height: 50px; min-width: 50px"
                        :alt="c"
                      />
                    </div>
                    {{ c }}
                  </a>
                </li>

                <li v-if="gamesAll.length > 0" style="display: none">
                  <a
                    class="nav-menu"
                    :class="{ 'active-nav': categorySelected == 'search' }"
                    id="pills-search-tab"
                    data-toggle="pill"
                    href="#pills-search"
                    role="tab"
                    aria-controls="pills-search"
                    aria-selected="false"
                    @click="resetPage(gamesAll, 'search')"
                  >
                  </a>
                </li>
                <li v-if="gamesAll.length > 0" style="display: none">
                  <a
                    class="nav-menu"
                    :class="{ 'active-nav': categorySelected == 'allold' }"
                    id="pills-allold-tab"
                    data-toggle="pill"
                    href="#pills-allold"
                    role="tab"
                    aria-controls="pills-allold"
                    aria-selected="false"
                    @click="
                      resetParams();
                      resetPage(gamesAll, 'allold');
                    "
                  >
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <!-- ----- -->
        <div
          class="row justify-content-center text-center pro-row centralCasinoBar1"
          style="
            margin: 0px;
            border-bottom-left-radius: 15px;
            border-bottom-right-radius: 15px;
          "
          v-if="!loading && !fromHome"
        >
          <div class="col-12 col-md-4" style="margin-top: 10px">
            <div class="input-group">
              <select
                class="form-control"
                style="color: 'black'; border-radius: 3px !important"
                v-model="platform"
                @change="resetSelectedPage()"
              >
                <option value="ALL">{{ getLocate("platform") }}</option>
                <option v-for="p in platforms" :value="p" :key="p">
                  {{ p }}
                </option>
              </select>
            </div>
          </div>
          <div class="col-12 col-md-4" style="margin-top: 10px">
            <div class="input-group">
              <input
                type="search"
                :placeholder="getLocate('search-game')"
                v-model="search"
                class="form-control"
                style="color: 'black'; border-radius: 3px !important"
                @keyup="resetSelectedPage()"
              />
              <div class="input-group-append icon-append">
                <i class="fa fa-search"></i>
              </div>
            </div>
          </div>
        </div>
        <div class="tab-content" id="pills-tabContent">
          <!-- NEW VIEW ALL BY CATEGORY -->
          <div
            class="tab-pane fade show active"
            id="pills-all"
            role="tabpanel"
            aria-labelledby="pills-all-tab"
          >
            <div
              v-if="!fromHome || fromHome == 'popular'"
              style="min-height: 249px"
            >
              <div class="games-title" v-if="!fromHome">
                <img
                  src="images/games_popular.svg"
                  style="height: 30px; margin-right: 10px"
                  alt="games popular"
                />
                <a-lang value="popular"> </a-lang>
                &nbsp;&nbsp;
                <a
                  href="javascript:void(0)"
                  class="aNoStyle"
                  style="float: right"
                  @click="
                    document.getElementById('back-top-btn').click();
                    document.getElementById('pills-popular-tab').click();
                  "
                >
                  <div class="mas-container tabletimage desktopimage">
                    <span style="color: #fff">|</span>
                    <span class="mas-text">
                      <a-lang value="explore-more-games"> </a-lang>
                    </span>
                  </div>
                  <img
                    src="images/arrow-mas.png"
                    alt="mas"
                    style="position: relative; top: -2px"
                  />
                </a>
              </div>

              <div class="buttonsGroup">
                <img
                  id="slideBackcontainerPopular"
                  @click="backScroll('containerPopular')"
                  src="images/image_next.png"
                  class="prevButton desktopimage"
                  alt="prev"
                />
                <img
                  id="slidecontainerPopular"
                  @click="nextScroll('containerPopular')"
                  src="images/image_next.png"
                  class="nextButton desktopimage"
                  alt="next"
                />
              </div>
              <div class="inline-wrapper mx-0" id="containerPopular">
                <game
                  v-for="item in gamesPopularSlice"
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
            <div
              v-if="!fromHome || fromHome == 'new'"
              style="min-height: 249px"
            >
              <div class="games-title" v-if="!fromHome">
                <img
                  src="images/games_new.svg"
                  style="height: 30px; margin-right: 10px"
                  alt="games new"
                />
                <a-lang value="new"> </a-lang>
                &nbsp;&nbsp;
                <a
                  href="javascript:void(0)"
                  class="aNoStyle"
                  style="float: right"
                  @click="
                    document.getElementById('back-top-btn').click();
                    document.getElementById('pills-new-tab').click();
                  "
                >
                  <div class="mas-container tabletimage desktopimage">
                    <span style="color: #fff">|</span>
                    <span class="mas-text">
                      <a-lang value="explore-more-games"> </a-lang>
                    </span>
                  </div>
                  <img
                    src="images/arrow-mas.png"
                    alt="mas"
                    style="position: relative; top: -2px"
                  />
                </a>
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
                  v-for="item in gamesNewSlice"
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
            <div class="desktopimage" style="margin: 6px 10px" v-if="!fromHome">
              <a-banner
                category="banner games new middle"
                name="banner4"
                v-if="isDesktop()"
              ></a-banner>
            </div>
            <template v-if="!loading && !fromHome">
              <div v-for="c in listCategoriesPaginated" :key="c">
                <div class="games-title">
                  <img
                    :src="'images/games_' + c + '.svg'"
                    @error="replaceImgByDefault"
                    style="height: 30px; margin-right: 10px"
                    :alt="c"
                  />
                  {{ c }}
                  &nbsp;&nbsp;
                  <a
                    href="javascript:void(0)"
                    class="aNoStyle"
                    style="float: right"
                    @click="
                      document.getElementById('back-top-btn').click();
                      showView(c);
                    "
                  >
                    <div class="mas-container tabletimage desktopimage">
                      <span style="color: #fff">|</span>
                      <span class="mas-text">
                        <a-lang value="explore-more-games"> </a-lang>
                      </span>
                    </div>
                    <img
                      src="images/arrow-mas.png"
                      alt="mas"
                      style="position: relative; top: -2px"
                    />
                  </a>
                </div>

                <div class="buttonsGroup">
                  <img
                    :id="'slideBackcontainer' + c"
                    @click="backScroll('container' + c)"
                    src="images/image_next.png"
                    class="prevButton desktopimage"
                    alt="prev"
                  />
                  <img
                    :id="'slidecontainer' + c"
                    @click="nextScroll('container' + c)"
                    src="images/image_next.png"
                    class="nextButton desktopimage"
                    alt="next"
                  />
                </div>
                <div class="inline-wrapper mx-0" :id="'container' + c">
                  <template
                    v-for="item in gamesCategories(c)"
                    :key="item.gameID"
                  >
                    <game
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
                  </template>
                </div>
              </div>
            </template>
            <div id="addElementDiv"></div>
            <div v-if="!loading && gamesAll.length > 0 && !fromHome">
              <div class="games-title">
                <a-lang value="all"> </a-lang>
                &nbsp;&nbsp;
                <a
                  href="javascript:void(0)"
                  class="aNoStyle"
                  style="float: right"
                  @click="
                    document.getElementById('back-top-btn').click();
                    document.getElementById('pills-allold-tab').click();
                  "
                >
                  <div class="mas-container tabletimage desktopimage">
                    <span style="color: #fff">|</span>
                    <span class="mas-text">
                      <a-lang value="explore-more-games"> </a-lang>
                    </span>
                  </div>
                  <img
                    src="images/arrow-mas.png"
                    alt="mas"
                    style="position: relative; top: -2px"
                  />
                </a>
              </div>

              <div class="buttonsGroup">
                <img
                  id="slideBackcontainerAll"
                  @click="backScroll('containerAll')"
                  src="images/image_next.png"
                  class="prevButton desktopimage"
                  alt="prev"
                />
                <img
                  id="slidecontainerAll"
                  @click="nextScroll('containerAll')"
                  src="images/image_next.png"
                  class="nextButton desktopimage"
                  alt="next"
                />
              </div>
              <div class="inline-wrapper mx-0" id="containerAll">
                <game
                  v-for="item in gamesAllSlice"
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
          <!-- ---- -->
          <!-- OLD VIEW -->
          <div
            class="tab-pane fade"
            id="pills-search"
            role="tabpanel"
            aria-labelledby="pills-search-tab"
          >
            <div class="games-title" style="top: -5px">
              <a-lang value="search"> </a-lang> ({{
                gamesPaginated.length + numGamesRemaining
              }}
              &nbsp;<a-lang value="games-founds"> </a-lang>)
            </div>
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
                :port-img="'port_img_1 auto_width'"
                @login="$emit('login')"
                @balance="getSessions"
                @free="freeGameLaunch"
              >
              </game>
            </div>
            <div
              class="row"
              style="position: relative; top: 20px"
              v-show="numGamesRemaining"
            >
              <div class="col">
                <div class="casino-btn text-center">
                  <a
                    href="javascript:void(0)"
                    @click="nextPage(gamesAll, 'search')"
                    id="btn_next_search"
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
            id="pills-allold"
            role="tabpanel"
            aria-labelledby="pills-allold-tab"
          >
            <div class="games-title" style="top: -5px">
              <a-lang value="all"> </a-lang> ({{ gamesAll.length }})
            </div>
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
                :port-img="'port_img_1 auto_width'"
                @login="$emit('login')"
                @balance="getSessions"
                @free="freeGameLaunch"
              >
              </game>
            </div>
            <div
              class="row"
              style="position: relative; top: 20px"
              v-show="numGamesRemaining"
            >
              <div class="col">
                <div class="casino-btn text-center">
                  <a
                    href="javascript:void(0)"
                    @click="nextPage(gamesAll, 'search')"
                    id="btn_next_search"
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
            id="pills-popular"
            role="tabpanel"
            aria-labelledby="pills-popular-tab"
            v-if="gamesPopular.length > 0"
          >
            <div class="games-title" style="top: -5px">
              <a-lang value="popular"> </a-lang>
              ({{ gamesPopular.length }})
            </div>
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
                :port-img="'port_img_1 auto_width'"
                @login="$emit('login')"
                @balance="getSessions"
                @free="freeGameLaunch"
              >
              </game>
            </div>
            <div
              class="row"
              style="position: relative; top: 20px"
              v-show="numGamesRemaining"
            >
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
            <div class="games-title" style="top: -5px">
              <a-lang value="new"> </a-lang>
              ({{ gamesNew.length }})
            </div>
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
                :port-img="'port_img_1 auto_width'"
                @login="$emit('login')"
                @balance="getSessions"
                @free="freeGameLaunch"
              >
              </game>
            </div>
            <div
              class="row"
              style="position: relative; top: 20px"
              v-show="numGamesRemaining"
            >
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
            v-for="c in allListCategories"
            :key="c"
          >
            <div class="games-title" style="top: -5px">
              {{ c }}
              ({{ getGamesCategoriesSize(c) }})
            </div>
            <div class="grid-games">
              <template v-for="item in gamesLargeView">
                <game
                  v-if="filterCategory(item.category, c)"
                  :key="item.gameID"
                  :title="item.name"
                  :category="item.category"
                  :imgpath="item.thumbnailPath"
                  :playlink="item.playLinkDemo"
                  :gameid="item.gameID"
                  :isnew="item.new"
                  :params="item.gameParams"
                  :port-img="'port_img_1 auto_width'"
                  @login="$emit('login')"
                  @balance="getSessions"
                  @free="freeGameLaunch"
                  largeview="true"
                  categoryview="true"
                >
                </game>
              </template>
              <template v-for="item in gamesPaginated">
                <game
                  v-if="filterCategory(item.category, c)"
                  :key="item.gameID"
                  :title="item.name"
                  :category="item.category"
                  :imgpath="item.thumbnailPath"
                  :playlink="item.playLinkDemo"
                  :gameid="item.gameID"
                  :isnew="item.new"
                  :params="item.gameParams"
                  :port-img="'port_img_1 auto_width'"
                  @login="$emit('login')"
                  @balance="getSessions"
                  @free="freeGameLaunch"
                  categoryview="true"
                >
                </game>
              </template>
            </div>

            <div
              class="row"
              v-show="numGamesRemaining"
              style="position: relative; top: 20px"
            >
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
          <!-- ------ -->
        </div>
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
          class="modal-content modal-custom-content"
          v-if="
            isReserve && isReserve == 'ok' && store.state.betMethod == 'ITA'
          "
        >
          <div class="modal-header">
            <h5 class="modal-title text-center" id="playModalLabel">
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
                  class="header-btn text-white"
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
          class="modal-content modal-custom-content"
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
          class="modal-content modal-custom-content"
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
  props: ["background", "category", "gameIdPlay", "fromHome"],
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
      carouselCategories: [],
      listCategories: [],
      listCategoriesPaginated: [],
      allListCategories: [],
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
      counter: 0,
      launchGamesReal: false,
      gameLargeView: [],

      filterCategoryCache: {},
    };
  },
  created() {
    //if (store.state.userId) this.isBonuses();
  },
  mounted() {
    this.getGames();
    this.safariBackground();
    window.addEventListener("scroll", this.onScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
  },
  computed: {
    gamesAllSlice() {
      return this.gamesAll.slice(0, 15);
    },
    gamesNewSlice() {
      return this.gamesNew.slice(0, 15);
    },
    gamesPopularSlice() {
      return this.gamesPopular.slice(0, 15);
    },
  },
  methods: {
    safariBackground() {
      if (
        navigator.userAgent.search("Safari") >= 0 &&
        navigator.userAgent.search("Chrome") < 0 &&
        !this.fromHome
      ) {
        document
          .getElementById("sfondoCasino")
          .classList.add("sfondo-casino-safari");
        document
          .getElementById("sfondoCasino")
          .classList.remove("sfondo-casino");
      }
    },
    getGames() {
      this.$http
        .get("games")
        .then(({ data }) => {
          const {
            gamesAll,
            gamesPopular,
            gamesNew,
            carouselCategories,
            listCategories,
            allListCategories,
          } = data;

          this.gamesAll = gamesAll;
          this.gamesPopular = gamesPopular;
          this.gamesNew = gamesNew;
          this.carouselCategories = carouselCategories;
          this.listCategories = listCategories;
          this.allListCategories = allListCategories;
          this.addListCategoriesPaginated();

          this.sortGames(this.gamesAll);

          // Ottimizzazione: Ricaviamo `gscodes` e `gamesLargeView` in un solo passaggio
          this.platforms = [
            ...new Set(this.gamesAll.map((g) => g.gameParams.gscode)),
          ].sort();

          this.gamesLargeView = this.gamesAll.filter(
            (g) => g.gameParams.largeView
          );

          // Mostra la categoria attiva
          this.$nextTick(() => {
            if (this.category) {
              $(`[href='#pills-${this.category}']`).tab("show");
              this.resetPage(this.gamesAll, this.category);
            }
          });

          // Mostra il modal di gioco se l'utente è loggato e ha un gameIdPlay
          if (this.gameIdPlay && this.isUserLogged()) {
            const game = this.gamesAll.find(
              (g) => g.gameID === this.gameIdPlay
            );
            if (game) {
              $("#playModal").modal("show");
              this.getSessions(
                this.gameIdPlay,
                game.name,
                game.gameParams,
                game.category
              );
            }
          }
        })
        .catch((error) => {
          this.showError(error, this.getLocate("game-list-1"));
        });
    },
    isBonuses() {
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("bonus/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (
            result.result == 0 &&
            result.bonuses &&
            result.bonuses.length == 0
          ) {
            this.launchGamesReal = true;
            $("#playModal").on("show.bs.modal", function (e) {
              return e.preventDefault();
            });
          }
        })
        .catch((response) => {
          this.showError(response);
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
                      this.categoryBonus &&
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
                bonus.ts_use == null &&
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
      this.gamesIdBonus = "" + gameID;
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

            if (this.launchGamesReal) this.openGameSession();
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
      document.getElementById("back-top-btn").click();
      document.getElementById("pills-search-tab").click();
    },
    gamesFiltered(games, type) {
      const searchUpper = this.search ? this.search.toUpperCase() : null;
      const platform = this.platform !== "ALL" ? this.platform : null;

      // Precomputa tipi validi per evitare di farlo ad ogni iterazione
      const validTypes = new Set(["all", "popular", "new", "search", "allold"]);

      return games.filter((g) => {
        // Filtra per categoria
        if (!validTypes.has(type) && !this.filterCategory(g.category, type)) {
          return false;
        }

        // Filtra per piattaforma
        if (platform && g.gameParams.gscode !== platform) {
          return false;
        }

        // Filtra per nome
        return !(searchUpper && !g.name.toUpperCase().includes(searchUpper));
      });
    },
    numPages(games) {
      if (!games) games = [];
      return Math.ceil(games.length / this.recordsPerPage);
    },
    changePage(page, games, type) {
      this.loading = true;

      let btn_next = document.getElementById("btn_next_" + type);

      if (page < 1) page = 1;
      if (page > this.numPages(games)) page = this.numPages();

      for (
        let i = (page - 1) * this.recordsPerPage;
        i < page * this.recordsPerPage;
        i++
      ) {
        if (games[i]) this.gamesPaginated.push(games[i]);
      }
      this.numGamesRemaining = games.length - this.gamesPaginated.length;
      if (page == this.numPages(games)) {
        if (btn_next) btn_next.style.display = "none";
      } else if (btn_next) btn_next.style.display = "inline";

      this.loading = false;
    },
    addListCategoriesPaginated() {
      const size = this.listCategoriesPaginated.length;
      this.listCategoriesPaginated = this.listCategories.slice(0, size + 3);
    },
    isElementVisible(el) {
      if (!el) return false;
      const rect = el.getBoundingClientRect();
      return rect.top >= 0 && rect.bottom <= window.innerHeight;
    },
    onScroll() {
      const addElementDiv = document.getElementById("addElementDiv");
      if (
        this.listCategoriesPaginated.length < this.listCategories.length &&
        this.isElementVisible(addElementDiv)
      ) {
        this.addListCategoriesPaginated();
      } else if (
        this.listCategoriesPaginated.length == this.listCategories.length
      )
        window.removeEventListener("scroll", this.onScroll);
    },
    sortGames(games) {
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
      e.target.src = "images/games_default.svg";
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
    getGamesCategoriesSize(c) {
      return this.gamesAll.filter((item) =>
        this.filterCategory(item.category, c)
      ).length;
    },
    gamesCategories(c) {
      return this.gamesAll
        .filter((item) => this.filterCategory(item.category, c))
        .slice(0, 15);
    },
    filterCategory(category, c) {
      // Create a unique key for the cache based on the inputs
      const cacheKey = `${category}|${c}`;

      // Check if the result is already in the cache
      if (this.filterCategoryCache.hasOwnProperty(cacheKey)) {
        return this.filterCategoryCache[cacheKey];
      }

      // Compute the result if it's not in the cache
      const result = category.split(",").some((e) => e == c);

      // Store the result in the cache
      this.filterCategoryCache[cacheKey] = result;

      return result;
    },
    showView(c) {
      let carouselPill = document.getElementById("pills-" + c + "-tab");
      if (carouselPill) carouselPill.click();
      else {
        let hiddenPill = document.createElement("a");
        hiddenPill.setAttribute("style", "display:none");
        hiddenPill.setAttribute("id", "pills-" + c + "-tab");
        hiddenPill.setAttribute("class", "nav-link");
        hiddenPill.setAttribute("data-toggle", "pill");
        hiddenPill.setAttribute("href", "#pills-" + c);
        hiddenPill.setAttribute("role", "tab");
        hiddenPill.setAttribute("aria-controls", "pills-" + c);
        hiddenPill.setAttribute("aria-selected", "false");
        hiddenPill.innerHTML = c;
        document.getElementById("pills-tab").appendChild(hiddenPill);
        hiddenPill.click();
        this.resetParams();
        this.resetPage(this.gamesAll, c);
      }
    },
  },
  components: {
    vueSlider: window["vue-slider-component"],
  },
};
</script>
