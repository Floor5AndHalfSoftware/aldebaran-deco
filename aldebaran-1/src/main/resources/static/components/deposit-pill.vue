<template>
  <div
    class="row mb-2 box-400 ml-2 mr-2"
    style="
      border-radius: 10px;
      flex-direction: column;
      justify-content: center;
      flex-wrap: nowrap;
    "
  >
    <div class="box-flex" style="align-items: center">
      <img
        :src="imgpath"
        class="img-fluid img-money-transfers"
        :style="customstyle ? customstyle : ''"
        alt="img"
      />
    </div>
    <div class="box-flex" style="margin-left: 10px; margin-right: 10px">
      <h3
        class="title-box"
        style="
          font-family: 'Montserrat Bold' !important;
          color: #004a99 !important;
        "
      >
        {{ maintitle }}
      </h3>
      <p
        class="title-text"
        style="
          font-family: 'Montserrat SemiBold' !important;
          font-size: 14px !important;
          color: #0062cc !important;
        "
      >
        {{ subtitle }}
      </p>
      <p
        class="title-text"
        style="
          font-family: 'Montserrat SemiBold' !important;
          font-size: 14px !important;
          color: #343434 !important;
        "
      >
        {{ description }}
        <accept-conditions
          v-if="accepttextlist && accepttextlist.length > 0"
          :accepttextlist="accepttextlist"
          :mandatoryacceptindexes="mandatoryacceptindexes"
          @accept="
            (value) => {
              activateButton = value;
            }
          "
        ></accept-conditions>
      </p>
    </div>
    <div
      class="box-flex align-items-center"
      style="margin-left: 5px"
      v-if="hasbtn == true"
    >
      <button
        type="button"
        class="money-transfers-button"
        v-if="isUserLogged()"
        @click="$emit(tabname)"
        :disabled="!activateButton"
      >
        <span class="btn-money-transfer-recarga-text">{{ btntext }}</span>
        <i class="fa fa-credit-card ml-2"></i>
      </button>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: [
    "maintitle",
    "subtitle",
    "description",
    "hasbtn",
    "btntext",
    "tabname",
    "imgpath",
    "customstyle",
    "accepttextlist",
    "mandatoryacceptindexes",
  ],
  data() {
    return {
      activateButton: true,
    };
  },
};
</script>
