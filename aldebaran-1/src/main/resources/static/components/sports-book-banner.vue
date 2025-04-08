<template>
  <div>
    <section
      id="free-spin"
      class="project-img games-page section"
      :class="{ 'sfondo-2': background == undefined, '': background == 'no' }"
    >
      <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
      <div class="container">
        <div class="row">
          <div class="col-lg-6">
            <div class="heading">
              <h2>
                <a-lang value="our-sports"> </a-lang>
              </h2>
              <hr class="hr" />
            </div>
          </div>
        </div>
        <a-loading :show="loading"></a-loading>
        <div class="row mx-0">
          <div class="col-lg-3 col-6">
            <div class="port_img">
              <img src="images/i1.png" class="img-fluid" alt="" />
              <div class="overlay1">
                <div class="overlay-text">
                  <div class="port-text">
                    <div class="run-txt">
                      <table style="width: 100%">
                        <tr>
                          <td>
                            <div class="casino-btn">
                              <a href="#/overview" class="btn-4 yellow-btn"
                                ><a-lang value="play"> </a-lang
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

          <div class="col-lg-3 col-6">
            <div class="port_img">
              <img src="images/i2.png" class="img-fluid" alt="" />
              <div class="overlay1">
                <div class="overlay-text">
                  <div class="port-text">
                    <div class="run-txt">
                      <table style="width: 100%">
                        <tr>
                          <td>
                            <div class="casino-btn">
                              <a href="#/overview" class="btn-4 yellow-btn"
                                ><a-lang value="play"> </a-lang
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

          <div class="col-lg-3 col-6">
            <div class="port_img">
              <img src="images/i3.png" class="img-fluid" alt="" />
              <div class="overlay1">
                <div class="overlay-text">
                  <div class="port-text">
                    <div class="run-txt">
                      <table style="width: 100%">
                        <tr>
                          <td>
                            <div class="casino-btn">
                              <a href="#/overview" class="btn-4 yellow-btn"
                                ><a-lang value="play"> </a-lang
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

          <div class="col-lg-3 col-6">
            <div class="port_img">
              <img src="images/i4.png" class="img-fluid" alt="" />
              <div class="overlay1">
                <div class="overlay-text">
                  <div class="port-text">
                    <div class="run-txt">
                      <table style="width: 100%">
                        <tr>
                          <td>
                            <div class="casino-btn">
                              <a href="#/overview" class="btn-4 yellow-btn"
                                ><a-lang value="play"> </a-lang
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
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  props: ["background"],
  mixins: [common],
  data() {
    return {};
  },
  created() {},
  methods: {},
};
</script>  
