<template>
  <div>
    <section
      id="free-spin"
      class="project-img games-page section"
      style="background-color: #0f1229"
    >
      <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
      <div class="container">
        <a-loading :show="loading"></a-loading>
        <div class="row mx-0" v-if="!loading && imgList && imgList.length > 0">
          <div
            class="col-lg-6 col-12"
            v-for="record in imgList"
            :key="record.id"
          >
            <div class="port_img">
              <img
                v-if="record.mime_type"
                :src="'data:' + record.mime_type + ';base64,' + record.content"
                class="img-fluid"
                alt=""
              />
              <div class="overlay1">
                <div class="overlay-text">
                  <div class="port-text">
                    <div class="run-txt">
                      <h4>
                        {{ record.name }}
                      </h4>
                      <br />
                      <table style="width: 350px">
                        <tr>
                          <td>
                            <div class="casino-btn">
                              <a
                                href="javascript:void(0)"
                                class="btn-4 yellow-btn"
                                data-toggle="modal"
                                data-target="#infoModal"
                                @click="selectRecord(record.id)"
                                ><a-lang value="more-info"> </a-lang
                              ></a>
                            </div>
                          </td>
                          <td v-if="record.url">
                            <div class="casino-btn">
                              <a
                                :href="record.url"
                                target="_BLANK"
                                class="btn-4 yellow-btn"
                                ><a-lang value="go-to"> </a-lang
                              ></a>
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
        </div>
        <div
          v-show="!loading && noPromotions"
          class="row justify-content-center text-center"
          style="
            width: auto;
            font-family: 'Montserrat', sans-serif;
            font-size: 20px;
            color: #2f5597;
          "
        >
          <div class="col">
            <a-lang value="no-promotions"></a-lang>
          </div>
        </div>

        <!-- Info Modal -->
        <div
          class="modal fade"
          id="infoModal"
          tabindex="-1"
          role="dialog"
          aria-labelledby="infoModalLabel"
          aria-hidden="true"
        >
          <div class="modal-dialog" role="document">
            <div class="modal-content back-dark">
              <div class="modal-header">
                <h5 class="modal-title text-white" id="infoModalLabel">
                  {{ record.name }}
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
                <img
                  v-if="record.mime_type"
                  :src="
                    'data:' + record.mime_type + ';base64,' + record.content
                  "
                  class="img-fluid"
                  alt=""
                />
              </div>
              <div class="modal-footer text-white">
                <div id="info" class="scroll-list scrollbar-style-vertical">
                  <span v-html="record.text_content"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  props: ["background"],
  mixins: [common],
  data() {
    return {
      contents: [],
      imgList: [],
      record: {},
      noPromotions: false,
    };
  },
  created() {},
  mounted() {
    this.getPromotions();
  },
  methods: {
    getPromotions() {
      this.loading = true;
      let formData = new FormData();
      formData.append("language", store.state.locate);
      formData.append("category", "promotions");
      this.$http
        .post("cms/file/list", formData)
        .then((response) => {
          let result = response.data;
          if (result.result == 0) {
            this.contents = result.contents;
            if (this.contents && this.contents.length > 0) {
              for (i in this.contents) {
                formData = new FormData();
                formData.append("id", this.contents[i].id);
                this.$http
                  .post("cms/file/content", formData)
                  .then((response1) => {
                    let result1 = response1.data;
                    if (result1.result == 0) {
                      this.imgList.push(result1.record);
                    }
                  });
              }
            } else this.noPromotions = true;
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
    selectRecord(id) {
      this.record = this.imgList.find((r) => r.id == id);
    },
  },
};
</script>  
