<template>
  <div
    class="modal fade"
    id="videoModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="videoModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog" role="document">
      <div class="modal-content back-dark">
        <div class="modal-header">
          <button
            type="button"
            class="close text-white"
            data-dismiss="modal"
            aria-label="Close"
            @click="stopVideo()"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" style="text-align: center">
          <iframe
            :src="store.state.urlvideo"
            width="100%"
            height="315"
            allowfullscreen
          ></iframe>
        </div>
      </div>
    </div>
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
