<template>
  <div>
    <section>
      <div>
        <iframe
          id="iframeRef"
          :src="url"
          width="100%"
          height="800"
          framespacing="0"
          border="0"
          frameborder="0"
          scrolling="yes"
        ></iframe>
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  props: ["type"],
  mixins: [common],
  data() {
    return {
      url: null,
    };
  },
  created() {
    this.initFrame();
  },
  methods: {
    initFrame() {
      if (this.type == "lottery")
        this.url = "https://ventas-prueba.loteria.com.ec/inicio";
      else if (this.type == "buy-tickets")
        this.url = "https://ventas-prueba.loteria.com.ec/compra_tus_juegos/";
      else if (this.type == "blog") this.url = "https://www.bet593.ec/blog";
    },
  },
};
</script>

<style>
</style>