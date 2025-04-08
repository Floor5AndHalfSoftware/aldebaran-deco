<template>
  <div>
    <section id="counter" class="back-white">
      <div class="container">
        <div class="row conter-res yellow-bg text-center">
          <div class="col-md-5 counter-left text-center">
            <div>
              <h4>
                <a-lang value="today-win"> </a-lang>
              </h4>
            </div>
          </div>
          <div class="col-md-7 col-12 text-center counterinner">
            <ul>
              <li class="counter-text border-count">
                <h3 class="counter-after" style="margin-left: -7px">Kz</h3>
              </li>
              <li class="counter-text border-count">
                <h3 class="counter counter-after">{{ win[0] }}</h3>
              </li>
              <li class="counter-text border-count">
                <h3 class="counter counter-after">{{ win[1] }}</h3>
              </li>
              <li class="counter-text border-count">
                <h3 class="counter counter-after">{{ win[2] }}</h3>
              </li>
              <li class="counter-text border-count">
                <h3 class="counter counter-after">{{ win[3] }}</h3>
              </li>
              <li class="counter-text border-count">
                <h3 class="counter counter-after">{{ win[4] }}</h3>
              </li>
              <li class="counter-text border-count">
                <h3 class="counter counter-after">{{ win[5] }}</h3>
              </li>
              <li class="counter-text border-count">
                <h3 class="counter counter-after">{{ win[6] }}</h3>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {
      win: "0",
    };
  },
  created() {
    winUpTo = 12345;
    this.win = winUpTo.toString().padStart(7, "0");
  },
  methods: {},
};
</script>        
      