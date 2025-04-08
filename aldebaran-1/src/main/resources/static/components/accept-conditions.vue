<template>
  <div>
    <div v-for="(accepttext, index) in accepttextlist" :key="index">
      <br />
      <label
        style="
          display: inline-flex;
          align-items: center;
          gap: 10px;
          font-size: 13px;
        "
      >
        <input
          style="margin-left: 5px"
          type="checkbox"
          v-model="accepttext.accept"
          @change="checkAccept"
        />
        {{ accepttext.text }}
      </label>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["accepttextlist", "mandatoryacceptindexes"],
  created() {
    this.checkAccept();
  },
  methods: {
    checkAccept() {
      let mandatoryAccept = true;
      for (let i = 0; i < this.accepttextlist.length; i++) {
        if (
          this.mandatoryacceptindexes &&
          this.mandatoryacceptindexes.includes(i)
        ) {
          if (!this.accepttextlist[i].accept) {
            mandatoryAccept = false;
            break;
          }
        }
      }
      this.$emit("accept", mandatoryAccept);
    },
  },
};
</script>
