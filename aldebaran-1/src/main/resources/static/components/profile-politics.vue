<template>
  <div>
    <div class="custom-wallet-nav nav flex-row nav-pills text-center mb-2">
      <a
        @click="pageView = 'privacyPolicy'"
        href="#privacyPolicy"
        class="nav-link profile-nav-link"
        data-toggle="pill"
      >
        <a-lang value="privacyPolicyTitle"> </a-lang
      ></a>
      <a
        href="#agreementsTerms"
        class="nav-link profile-nav-link"
        data-toggle="pill"
        @click="pageView = 'agreementsTerms'"
      >
        <a-lang value="agreementsTermsTitle"> </a-lang
      ></a>
    </div>
    <div id="politics" :key="pageView" class="scroll-list">
      <!--AGREEMENTS TERMS-->
      <div class="card-body darkColor" v-if="pageView == 'agreementsTerms'">
        <div
          class="row profile-card-large-text"
          style="
            text-align: justify;
            font-size: 13px;
            font-family: 'Montserrat Medium';
          "
        >
          <cms-content
            group="privacy policy"
            name="Agreements terms"
            category="agreements terms"
            removestyle="false"
          ></cms-content>
        </div>
      </div>
      <!--AGREEMENTS TERMS END-->

      <!--PRIVACY POLICY-->
      <div class="card-body darkColor" v-else-if="pageView == 'privacyPolicy'">
        <div
          class="row profile-card-large-text"
          style="
            text-align: justify;
            font-size: 13px;
            font-family: 'Montserrat Medium';
          "
        >
          <cms-content
            group="privacy policy"
            name="Privacy policy"
            category="privacy policy"
            removestyle="false"
          ></cms-content>
        </div>
      </div>
      <!--PRIVACY POLICY END-->
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["pageview"],
  data() {
    return {
      pageView: this.pageview,
    };
  },
  mounted() {
    $('a[href="#' + this.pageView + '"]')
      .addClass("active")
      .addClass("show");
  },
};
</script>
