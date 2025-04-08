<template>
  <div>
    <section id="free-spin" class="project-img games-page section">
      <a-alert :alert="alert" @close="alert.message = ''"></a-alert>
      <div class="container">
        <div class="row">
          <div class="col-12">
            <div class="heading">
              <h2>
                <a-lang value="privacyPolicyTitle"> </a-lang>
              </h2>
              <hr class="hr" />
            </div>
          </div>
        </div>
        <div class="card-body darkColor">
          <cms-content
            group="privacy policy"
            name="Privacy policy"
            category="privacy policy"
          ></cms-content>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {};
  },
  methods: {},
};
</script>