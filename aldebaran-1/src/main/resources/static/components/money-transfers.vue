<template>
  <div>
    <section>
      <div class="container" style="padding: 0px 5px" :key="paymentView">
        <deposit-pill
          v-if="paymentView == 'efectivo'"
          :imgpath="'images/puntodelasuerte.png'"
          :maintitle="'EFECTIVO'"
          :subtitle="'Mínimo 1 usd | Máximo 1000 usd'"
          :description="'Recarga desde US$ 1 en tu punto de venta favorito solo con tu número de cédula.'"
          :hasbtn="false"
          :customstyle="'max-width: 60%;max-height: none;'"
        ></deposit-pill>
        <deposit-pill
          v-if="isNuveiUser() && paymentView == 'tarjetas'"
          :imgpath="'images/payment/nuvei_cards.png'"
          :maintitle="'VISA - MASTERCARD - AMERICAN EXPRESS'"
          :description="'Utiliza la tarjeta de crédito o débito de tu preferencia Visa,  Mastercard o American Express'"
          :subtitle="'Mínimo: 5 usd | Máximo: 3000 usd'"
          :hasbtn="true"
          :btntext="'RECARGAR'"
          :tabname="'nuveitab'"
          @nuveitab="$emit('nuveitab')"
        ></deposit-pill>
        <deposit-pill
          v-if="isMonnetPayinUser() && paymentView == 'tarjetas'"
          :imgpath="'images/payment/monnet.png'"
          :maintitle="'DINERS - DISCOVER - VISA - MASTERCARD'"
          :description="'Utiliza tu tarjeta de crédito o débito de tu preferencia Diners, Discover, Visa o Mastercard'"
          :subtitle="'Mínimo: 5 usd | Máximo: 3000 usd'"
          :hasbtn="true"
          :btntext="'RECARGAR'"
          :tabname="'monnetpayintab'"
          @monnetpayintab="$emit('monnetpayintab')"
        ></deposit-pill>
        <deposit-pill
          v-if="isPlacetopayUser() && paymentView == 'tarjetas'"
          :imgpath="'images/payment/placetopay_cards.png'"
          :maintitle="'DINERS - DISCOVER - VISA - MASTERCARD'"
          :description="'Utiliza tu tarjeta de crédito o débito de tu preferencia Diners, Discover, Visa o Mastercard'"
          :subtitle="'Mínimo: 5 usd | Máximo: 3000 usd'"
          :hasbtn="true"
          :btntext="'RECARGAR'"
          :tabname="'placetopaytab'"
          @placetopaytab="$emit('placetopaytab')"
        ></deposit-pill>
        <deposit-pill
          v-if="isSafetypayUser() && paymentView == 'bank-transfer'"
          :imgpath="'images/bank-transfer.png'"
          :maintitle="'TRANSFERENCIAS BANCARIAS'"
          :description="'Recarga tu cuenta Bet593 de forma segura e instantánea a través de tu banca en linea'"
          :subtitle="'Mínimo 5 usd | Máximo 5000 usd'"
          :hasbtn="true"
          :btntext="'RECARGAR'"
          :tabname="'trasferenciabancaria'"
          @trasferenciabancaria="$emit('trasferenciabancaria')"
        ></deposit-pill>
        <!--<deposit-pill
          v-if="paymentView == 'efectivo'"
          :imgpath="'images/red-activa.png'"
          :maintitle="'RED ACTIVA - WESTERN UNION'"
          :description="'Acercate cualquier agencia de Red Activa-Western Union a nivel nacional y recarga tu cuenta Bet593'"
          :subtitle="'Mínimo 1 usd | Máximo 300 usd'"
          :hasbtn="false"
        ></deposit-pill>-->
        <deposit-pill
          v-if="isSafetypayUser() && paymentView == 'efectivo'"
          :imgpath="'images/western-union.png'"
          :maintitle="'PAGOEFECTIVO'"
          :description="'Recarga en cualquiera de nuestros comercios afiliados a nivel nacional.'"
          :subtitle="'Mínimo 5 usd | Máximo 700 usd'"
          :hasbtn="true"
          :btntext="'RECARGAR'"
          :tabname="'comerciosafiliados'"
          @comerciosafiliados="$emit('comerciosafiliados')"
        ></deposit-pill>
        <!--<deposit-pill
          v-if="paymentView == 'efectivo'"
          :imgpath="'images/logo_bemovil.png'"
          :maintitle="'BEMOVIL'"
          :description="'Utiliza la red de recargas y transacciones electrónicas BeMovil y recarga tu cuenta Bet593.'"
          :subtitle="'Mínimo 1 usd | Máximo 300 usd'"
          :hasbtn="false"
        ></deposit-pill>-->
      </div>
    </section>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["paymentView"],
  data() {
    return {};
  },
  methods: {},
};
</script>
