<template>
  <div class="col-md-6 col-12 list-group-item lottery-element mb-1">
    <div class="row" style="width: 100%">
      <div class="col-10">
        <a
          href="#"
          @click="openWindow(item.user_id, item.ticket_id)"
          class="no-style-a"
        >
          <div class="row">
            <div class="col">
              <small>
                <a-lang value="ticket-id" style="width: 100%"> </a-lang>:
                <b>{{ item.ticket_id }}</b
                ><br />
                <i class="fa fa-calendar"></i>
                <span v-if="item.ts != null">{{
                  formatDateYYYYMMDDHHmmss(item.ts)
                }}</span>
              </small>
            </div>
          </div>

          <div class="d-flex w-100 justify-content-between">
            <b><a-lang value="details"> </a-lang></b>
          </div>
          <small
            ><b><a-lang value="transaction"> </a-lang></b
          ></small>
          <small>
            <span>{{ item.reserve_id }}</span>
          </small>
          <br />
          <small
            ><b><a-lang value="amount"> </a-lang></b
          ></small>
          <small>
            <span style="color: red"
              >-{{ formatterNumber(item.amount, true) }}
              {{ currencyView(item.currency) }}</span
            >
          </small>
          <br />
          <small
            ><b><a-lang value="real-amount"> </a-lang></b
          ></small>
          <small>
            <span style="color: red"
              >-{{ formatterNumber(item.real_amount, true) }}
              {{ currencyView(item.currency) }}</span
            >
          </small>
          <br />
          <small
            ><b><a-lang value="status"> </a-lang>: </b></small
          >
          <b>
            <small
              style="
                color: white;
                background-color: green;
                padding: 2px;
                border-radius: 5px;
              "
              v-if="item.status == 'PAID'"
            >
              PAGADA
            </small>
            <small
              style="
                color: white;
                background-color: green;
                padding: 2px;
                border-radius: 5px;
              "
              v-else-if="item.status == 'SELL'"
            >
              VENTA
            </small>
            <small
              style="
                color: white;
                background-color: red;
                padding: 2px;
                border-radius: 5px;
              "
              v-else-if="item.status == 'CANCEL'"
            >
              {{ item.status }}
            </small>
            <small
              style="
                color: white;
                background-color: green;
                padding: 2px;
                border-radius: 5px;
              "
              v-else
            >
              {{ item.status }}
            </small>
          </b>
        </a>
      </div>
      <div class="col-2">
        <a
          class="no-style-a"
          href="#"
          @click="
            openDetailModal();
            getLotteryPayPrize(item.ticket_id);
          "
        >
          <div class="box-list-detail-button" style="margin-left: 0px">
            <i class="fa fa-arrow-right"></i>
          </div>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["item"],
  data() {},
  methods: {
    getLotteryPayPrize(ticketId) {
      this.$emit("get-lottery-pay-prize", ticketId);
    },
    openDetailModal() {
      this.$emit("open-detail-modal");
    },
    openWindow(userId, ticketId) {
      this.$emit("open-window", userId, ticketId);
    },
  },
};
</script>