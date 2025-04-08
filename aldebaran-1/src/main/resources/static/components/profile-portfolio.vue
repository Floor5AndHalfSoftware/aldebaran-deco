<template>
  <div>
    <div>
      <div class="custom-wallet-nav nav flex-row nav-pills text-center">
        <a
          v-if="!isUserSelfexcluded()"
          @click="
            depositsKey += 1 % 2;
            pageView = 'deposit';
          "
          href="#deposit"
          class="nav-link profile-nav-link"
          :class="{ 'active show': pageView == 'deposit' }"
          data-toggle="pill"
        >
          <i :class="'fa fa-credit-card'" class="profile-primary"></i>
          <a-lang value="deposit"> </a-lang
        ></a>
        <a
          href="#movements"
          class="nav-link profile-nav-link"
          :class="{ 'active show': pageView == 'movements' }"
          data-toggle="pill"
          @click="
            resetMessages();
            movementsKey += 1 % 2;
            pageView = 'movements';
          "
          ><i :class="'fa fa-money'" class="profile-primary"></i>
          <a-lang value="moviments"> </a-lang
        ></a>

        <a
          v-if="isPdsUser() || isBanktransferUser()"
          @click="
            withdrawalKey += 1 % 2;
            pageView = 'withdrawal';
          "
          href="#withdrawal"
          class="nav-link profile-nav-link"
          :class="{ 'active show': pageView == 'withdrawal' }"
          data-toggle="pill"
          ><i :class="'fa fa-bank'" class="profile-primary"></i>
          <a-lang value="withdrawal"> </a-lang
        ></a>
      </div>
    </div>
    <div class="tab-content container h-100" style="margin-top: 10px">
      <!-- MOVEMENTS -->
      <div
        v-if="pageView == 'movements'"
        class="tab-pane show active"
        id="movements"
      >
        <profile-movements
          class="scroll-tab-content-no-height"
          style="overflow-x: hidden"
          :key="movementsKey"
          :loading="loading"
          @change-loading="changeLoading"
          @set-error="setError"
          @set-success="setSuccess"
          @reset-messages="resetMessages"
        ></profile-movements>
      </div>
      <!-- MOVEMENTS END -->
      <!-- DEPOSITS -->
      <div
        v-if="pageView == 'deposit'"
        class="tab-pane show active"
        id="deposit"
      >
        <profile-deposits
          style="overflow-x: hidden"
          :key="depositsKey"
          :loading="loading"
          @change-loading="changeLoading"
          @set-error="setError"
          @set-success="setSuccess"
          @reset-messages="resetMessages"
        ></profile-deposits>
      </div>
      <!-- DEPOSITS END -->
      <!-- WITHDRAWAL -->
      <div
        v-if="pageView == 'withdrawal'"
        class="tab-pane show active"
        id="withdrawal"
      >
        <profile-withdrawal
          style="overflow-x: hidden"
          :key="withdrawalKey"
          :loading="loading"
          @change-loading="changeLoading"
          @set-error="setError"
          @set-success="setSuccess"
          @reset-messages="resetMessages"
        ></profile-withdrawal>
      </div>
      <!-- WITHDRAWAL END -->
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["loading", "subpage"],
  data() {
    return {
      movementsKey: 0,
      depositsKey: 0,
      withdrawalKey: 0,
      pageView: this.subpage ? this.subpage : "deposit",
    };
  },
  mounted() {
    this.resetMessages();
  },
  methods: {
    changeLoading(value) {
      this.$emit("change-loading", value);
    },
    setError(message) {
      this.$emit("set-error", message);
    },
    setSuccess(message) {
      this.$emit("set-success", message);
    },
    resetMessages() {
      this.$emit("reset-messages");
    },
  },
};
</script>
