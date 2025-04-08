<template>
  <div>
    <section
      id="control"
      class="control section"
      :class="{ 'sfondo-2': background == undefined, '': background == 'no' }"
    >
      <div class="container">
        <div class="row">
          <div class="col-lg-6">
            <div class="heading">
              <h2>
                <a-lang value="be-in-control"> </a-lang>
              </h2>
              <hr class="hr" />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-3 col-md-6">
            <div class="row justify-content-center text-center">
              <div class="row">
                <div class="col-12">
                  <div class="control-img" style="width: 120px; height: 120px">
                    <i class="fa flaticon-bill"></i>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <br />
                  <div class="control-text text-center">
                    <h3>
                      <a-lang value="payment-limitation"> </a-lang>
                    </h3>
                    <p>
                      Lorem ipsum dolor sit amet, consecteturt, sed do eiusmod
                      tempor incididunt.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-3 col-md-6">
            <div class="row justify-content-center text-center">
              <div class="row">
                <div class="col-12">
                  <div class="control-img" style="width: 120px; height: 120px">
                    <i class="fa flaticon-money"></i>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <br />
                  <div class="control-text text-center">
                    <h3>
                      <a-lang value="profit-limitation"> </a-lang>
                    </h3>
                    <p>
                      Lorem ipsum dolor sit amet, consecteturt, sed do eiusmod
                      tempor incididunt.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-3 col-md-6">
            <div class="row justify-content-center text-center">
              <div class="row">
                <div class="col-12">
                  <div class="control-img" style="width: 120px; height: 120px">
                    <i class="fa flaticon-loss"></i>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <br />
                  <div class="control-text text-center">
                    <h3>
                      <a-lang value="loss-limitation"> </a-lang>
                    </h3>
                    <p>
                      Lorem ipsum dolor sit amet, consecteturt, sed do eiusmod
                      tempor incididunt.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-3 col-md-6">
            <div class="row justify-content-center text-center">
              <div class="row">
                <div class="col-12">
                  <div class="control-img" style="width: 120px; height: 120px">
                    <i class="fa flaticon-wallet-1"></i>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <br />
                  <div class="control-text text-center">
                    <h3>
                      <a-lang value="deposit-limit"> </a-lang>
                    </h3>
                    <p>
                      Lorem ipsum dolor sit amet, consecteturt, sed do eiusmod
                      tempor incididunt.
                    </p>
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
  mixins: [common],
  props: ["background"],
  data() {
    return {};
  },
  created() {},
  methods: {},
};
</script>        
      