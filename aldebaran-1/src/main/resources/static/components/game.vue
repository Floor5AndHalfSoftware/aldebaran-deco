<template>
  <div :class="categoryview
    ? !largeview
      ? 'col-padding'
      : 'col-span-2 row-span-2 col-padding'
    : 'col-xl-2 col-lg-3 col-md-4 col-sm-6 col-6 col-padding'
    ">
    <div :class="portImg">
      <img src="images/new.png" class="new" alt="" v-show="isnew" />
      <img src="images/loading_slot.gif" class="img-fluid" alt="" :style="style" v-show="imageLoading" />
      <img :src="imgpath" class="img-fluid" alt="" :style="style" @error="replaceImgByDefault" @load="onImageLoad()"
        v-show="!imageLoading" />
      <div class="overlay1">
        <div class="overlay-text">
          <div class="port-text">
            <div class="run-txt">
              <h4>{{ title }}</h4>
              <h5 style="padding-bottom: 10px; font-size: 1rem">
                <!--<a-lang value="category"></a-lang>: {{ category }}-->
              </h5>
              <table v-if="!isUserSelfexcluded()" style="width: 164px">
                <tr>
                  <td v-if="playlink" style="width: 80px">
                    <div class="casino-btn">
                      <a href="javascript:void(0)" @click="$emit('free', gameid, title, params)"
                        class="btn-4 yellow-btn">
                        <a-lang value="free"></a-lang>
                      </a>
                    </div>
                  </td>
                  <td v-if="playlink">&nbsp;</td>
                  <td style="width: 80px">
                    <div class="casino-btn">
                      <a href="javascript:void(0)" v-if="!isUserLogged()" @click="$emit('login')"
                        class="btn-4 yellow-btn" data-toggle="modal" data-target="#loginModal">
                        <a-lang value="play"></a-lang>
                      </a>
                      <a href="javascript:void(0)" v-if="isUserLogged()" @click="
                        $emit('balance', gameid, title, params, category)
                        " class="btn-4 yellow-btn" data-toggle="modal" data-target="#playModal">
                        <a-lang value="play"></a-lang>
                      </a>
                    </div>
                  </td>
                </tr>
              </table>
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
    props: [
      "title",
      "imgpath",
      "category",
      "playlink",
      "gameid",
      "index",
      "isnew",
      "params",
      "portImg",
      "largeview",
      "categoryview",
    ],
    data() {
      return {
        imageLoading: true,
        style: "aspect-ratio: 248/195",
      };
    },
    created() {
      if (this.portImg == "port_img_1")
        this.style = "height: 155px; width: 223px;";
      else if (this.portImg == "port_img_1 auto_width")
        this.style = "aspect-ratio: 1.20;";
    },
    methods: {
      replaceImgByDefault(e) {
        e.target.src = "images/not-found.png";
      },
      onImageLoad() {
        this.imageLoading = false;
      },
    },
  };
</script>