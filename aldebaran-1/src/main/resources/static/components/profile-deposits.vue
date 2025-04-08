<template>
  <!-- SUB TABS DEPOSITS -->
  <div v-if="!loading">
    <ul
      id="depositsTab"
      class="custom-wallet-nav nav flex-row nav-pills text-center"
      v-show="!loading && tab == 'menu'"
      style="margin-bottom: 10px"
    >
      <li class="nav-item">
        <a
          class="nav-link withdraws-nav-link"
          :class="{
            active: paymentView == 'tarjetas',
            '': paymentView != 'tarjetas',
          }"
          data-toggle="pill"
          @click="paymentView = 'tarjetas'"
          style="cursor: pointer"
        >
          <a-lang value="tarjetas"> </a-lang>
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link withdraws-nav-link"
          :class="{
            active: paymentView == 'bank-transfer',
            '': paymentView != 'bank-transfer',
          }"
          data-toggle="pill"
          @click="paymentView = 'bank-transfer'"
          style="cursor: pointer"
        >
          <a-lang value="bank-transfer"> </a-lang>
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link withdraws-nav-link"
          :class="{
            active: paymentView == 'efectivo',
            '': paymentView != 'efectivo',
          }"
          data-toggle="pill"
          @click="paymentView = 'efectivo'"
          style="cursor: pointer"
        >
          <a-lang value="efectivo"> </a-lang>
        </a>
      </li>
      <!--
      <li class="nav-item">
        <a
          class="nav-link withdraws-nav-link"
          :class="{
            active: paymentView == 'billeteras',
            '': paymentView != 'billeteras',
          }"
          data-toggle="pill"
          @click="paymentView = 'billeteras'"
        >
          <a-lang value="billeteras"> </a-lang>
        </a>
      </li>-->
    </ul>

    <button
      type="button"
      class="button-previous-profile"
      @click="
        error = false;
        errorMessage = '';
        tab = 'menu';
        showPreviousButton = false;
      "
      style="margin-bottom: 10px"
      v-if="showPreviousButton && !loading"
    >
      <img
        src="images/left_arrow.png"
        class="img-fluid"
        alt="effect"
        style="max-width: 20px"
      />
      <a-lang value="previous"></a-lang>
    </button>

    <!-- SUBTABS END -->
    <div class="tab-content scroll-tab-content">
      <!--MENU-->
      <div class="tab-pane fade show active" id="menu" v-if="tab == 'menu'">
        <money-transfers
          :payment-view="paymentView"
          @nuveitab="
            resetMessages();
            depositNuveiType = 'cc_card';
            depositType = '';
            tab = 'nuvei';
            showPreviousButton = true;
            getDepositNuveiTransactions();
          "
          @monnetpayintab="
            resetMessages();
            tab = 'monnetpayintab';
            depositType = '';
            showPreviousButton = true;
            getDepositMonnetTransactions();
          "
          @trasferenciabancaria="
            resetMessages();
            depositNuveiType = 'apmgw_SafetyPay_Online';
            depositType = 'trasferencia-bancaria';
            tab = 'nuvei';
            showPreviousButton = true;
            getDepositNuveiTransactions();
          "
          @efectivo="
            resetMessages();
            depositNuveiType = 'apmgw_SafetyPay_Cash';
            depositType = 'efectivo';
            tab = 'nuvei';
            showPreviousButton = true;
            getDepositNuveiTransactions();
          "
          @comerciosafiliados="
            resetMessages();
            depositNuveiType = 'apmgw_SafetyPay_Cash';
            depositType = 'comercios-afiliados';
            tab = 'nuvei';
            showPreviousButton = true;
            getDepositNuveiTransactions();
          "
          @placetopaytab="
            resetMessages();
            getDepositPTPTransactions(true);
            isMetadata = false;
            depositType = '';
            tab = 'placetopay';
            showPreviousButton = true;
          "
        ></money-transfers>
      </div>
      <!-- NUVEI -->
      <div v-if="!loading && tab == 'nuvei'">
        <div>
          <div class="row row-loteria">
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="card" v-if="depositType == 'trasferencia-bancaria'">
                <div
                  class="card-header"
                  style="padding-top: 0px; padding-bottom: 0px"
                >
                  <div class="row align-items-center">
                    <div class="col">
                      <img
                        src="images/tarjete-bancaria.png"
                        class="img-fluid center-img"
                        alt="effect"
                        style="
                          padding-bottom: 10px;
                          margin-top: 10px;
                          width: 300px;
                        "
                      />
                    </div>
                  </div>
                </div>
                <div class="card-body back-light">
                  <div class="form-group">
                    <p style="padding-bottom: 0px" class="profile-input-label">
                      <a-lang value="insert-rechange"> </a-lang>:
                    </p>
                    <br />
                    <input
                      v-model="amount"
                      class="form-control modal-custom-input"
                      :placeholder="getLocate('insert-rechange1')"
                      aria-label="Amount nuvei"
                      type="number"
                      step=".01"
                      required
                    />
                  </div>
                  <div class="text-black" style="text-align: justify">
                    <a-lang value="insert-rechange-bancaria"></a-lang>
                    <br />
                    <br />
                    <a-lang
                      value="insert-rechange4"
                      style="font-size: 14px"
                    ></a-lang>
                    <span style="font-size: 12px">5 USD</span>
                    <br />
                    <a-lang
                      value="insert-rechange5"
                      style="font-size: 14px"
                    ></a-lang>
                    <span style="font-size: 12px">5.000 USD</span>
                  </div>
                </div>
              </div>
              <div class="card" v-else-if="depositType == 'efectivo'">
                <div
                  class="card-header"
                  style="padding-top: 0px; padding-bottom: 0px"
                >
                  <div class="row align-items-center">
                    <div class="col">
                      <img
                        src="images/tarjete-efectivo.png"
                        class="img-fluid center-img"
                        alt="effect"
                        style="padding-bottom: 10px; margin-top: 10px"
                      />
                    </div>
                  </div>
                </div>
                <div class="card-body back-light">
                  <div class="form-group">
                    <p style="padding-bottom: 0px" class="profile-input-label">
                      <a-lang value="insert-rechange"> </a-lang>:
                    </p>
                    <br />
                    <input
                      v-model="amount"
                      class="form-control modal-custom-input"
                      :placeholder="getLocate('insert-rechange1')"
                      aria-label="Amount nuvei"
                      type="number"
                      step=".01"
                      required
                    />
                  </div>
                  <div
                    class="text-black"
                    style="text-align: justify; margin-left: 12px"
                  >
                    <a-lang value="insert-rechange-efectivo"></a-lang>
                  </div>
                  <br />
                  <br />
                  <div class="text-black" style="text-align: justify">
                    <a-lang
                      value="insert-rechange4"
                      style="font-size: 14px"
                    ></a-lang>
                    <span style="font-size: 12px">3 USD</span>
                    <br />
                    <a-lang
                      value="insert-rechange5"
                      style="font-size: 14px"
                    ></a-lang>
                    <span style="font-size: 12px">700 USD</span>
                  </div>
                </div>
              </div>
              <div
                class="card"
                v-else-if="depositType == 'comercios-afiliados'"
              >
                <div
                  class="card-header"
                  style="padding-top: 0px; padding-bottom: 0px"
                >
                  <div class="row align-items-center">
                    <div class="col">
                      <img
                        src="images/tarjete-comisariato.png"
                        class="img-fluid center"
                        alt="effect"
                        style="padding-bottom: 10px; margin-top: 10px"
                      />
                    </div>
                  </div>
                </div>
                <div class="card-body back-light">
                  <div class="form-group">
                    <p style="padding-bottom: 0px" class="profile-input-label">
                      <a-lang value="insert-rechange"> </a-lang>:
                    </p>
                    <br />
                    <input
                      v-model="amount"
                      class="form-control modal-custom-input"
                      :placeholder="getLocate('insert-rechange1')"
                      aria-label="Amount nuvei"
                      type="number"
                      step=".01"
                      required
                    />
                  </div>
                  <div
                    class="text-black"
                    style="text-align: justify; margin-left: 12px"
                  >
                    <a-lang value="insert-rechange-efectivo"></a-lang>
                  </div>
                  <br />
                  <br />
                  <div class="text-black" style="text-align: justify">
                    <a-lang
                      value="insert-rechange4"
                      style="font-size: 14px"
                    ></a-lang>
                    <span style="font-size: 12px">5 USD</span>
                    <br />
                    <a-lang
                      value="insert-rechange5"
                      style="font-size: 14px"
                    ></a-lang>
                    <span style="font-size: 12px">700 USD</span>
                  </div>
                </div>
              </div>
              <div class="card" v-else>
                <div
                  class="card-header"
                  style="padding-top: 0px; padding-bottom: 0px"
                >
                  <div class="row align-items-center">
                    <div class="col">
                      <img
                        src="images/payment/nuvei_cards.png"
                        class="img-fluid"
                        alt="effect"
                        style="padding-bottom: 10px"
                      />
                    </div>
                  </div>
                </div>
                <div class="card-body back-light">
                  <div class="form-group">
                    <p style="padding-bottom: 0px" class="profile-input-label">
                      <a-lang value="insert-rechange"> </a-lang>:
                    </p>
                    <br />
                    <input
                      v-model="amount"
                      class="form-control modal-custom-input"
                      :placeholder="getLocate('insert-rechange1')"
                      aria-label="Amount nuvei"
                      type="number"
                      step=".01"
                      required
                    />
                  </div>

                  <div class="loteria-text-white">
                    <a-lang value="insert-rechange2"></a-lang> Nuvei
                  </div>
                  <div class="loteria-text-white">
                    <a-lang value="insert-rechange3"></a-lang>
                  </div>
                  <div class="loteria-text-white">
                    <a-lang value="insert-rechange4"></a-lang> 5 USD
                  </div>
                  <div class="loteria-text-white">
                    <a-lang value="insert-rechange5"></a-lang> 3.000 USD
                  </div>
                </div>
              </div>
              <div class="button-center">
                <button class="profile-button" @click="depositNuvei()">
                  <a-lang value="request-deposit"> </a-lang>
                </button>
              </div>
              <br />
            </div>
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="row">
                <div class="col">
                  <div class="form-group">
                    <label for="depositState"
                      ><p class="profile-input-label">
                        <a-lang value="deposit-state"> </a-lang></p
                    ></label>
                    <select
                      class="form-control modal-custom-input"
                      style="color: 'black'"
                      id="depositState"
                      v-model="stateNuveiDeposit"
                    >
                      <option value="ALL">
                        {{ getLocate("all") }}
                      </option>
                      <option value="PENDING">
                        {{ getLocate("pending") }}
                      </option>
                      <option value="APPROVED">
                        {{ getLocate("approved") }}
                      </option>
                      <option value="DECLINED">
                        {{ getLocate("rejected") }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-from"> </a-lang>
                    </p>
                    <input
                      name="depositDateFrom"
                      v-model="depositDateFrom"
                      id="depositDateFrom"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositNuveiTransactions()"
                    />
                  </div>
                </div>
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-to"> </a-lang>
                    </p>
                    <input
                      name="depositDateTo"
                      v-model="depositDateTo"
                      id="depositDateTo"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositNuveiTransactions()"
                    />
                  </div>
                </div>
              </div>

              <div class="card">
                <no-result
                  v-if="
                    depositNuveiTransactionsFiltered &&
                    depositNuveiTransactionsFiltered.length == 0
                  "
                ></no-result>

                <div id="deposits" class="list-group scroll-list">
                  <span
                    class="list-group-item my-align-left"
                    v-for="item in depositNuveiTransactionsFiltered"
                    :key="item.request_id"
                  >
                    <small>
                      <i class="fa fa-calendar"></i>
                      <span v-if="item.ts_commit">{{
                        formatDateYYYYMMDDHHmmss(item.ts_commit)
                      }}</span
                      ><span v-else>{{
                        formatDateYYYYMMDDHHmmss(item.ts_reserve)
                      }}</span>
                      <a-lang value="state"> </a-lang>:
                      <b v-if="item.status == 'PENDING'" style="color: orange">
                        {{ getLocate("pending") }}
                      </b>
                      <b
                        v-else-if="item.status == 'DECLINED'"
                        style="color: red"
                      >
                        {{ getLocate("rejected") }}
                      </b>
                      <b
                        v-else-if="item.status == 'APPROVED'"
                        style="color: green"
                      >
                        {{ getLocate("approved") }}
                      </b>
                      <b v-else>
                        {{ item.status }}
                      </b>
                    </small>

                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="reference-id"> </a-lang>:</b
                      ><small>{{ item.reference_id }}</small>
                    </div>
                    <div class="d-flex w-100 justify-content-between">
                      <b> <a-lang value="deposit"> </a-lang>:</b>
                      <small>
                        <span
                          :style="
                            item.amount > 0 ? 'color: green;' : 'color: red;'
                          "
                          ><span v-if="item.amount < 0">-</span>
                          <span v-else>+</span
                          >{{ formatterNumber(item.amount, true) }}
                          {{ currencyView(item.currency) }}
                        </span>
                      </small>
                    </div>
                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="payment-method"> </a-lang>:</b>
                      <small>
                        {{ item.payment_method }}
                      </small>
                    </div>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- PLACETOPAY -->
      <div v-else-if="!loading && tab == 'placetopay'">
        <div>
          <div class="row row-loteria">
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="card">
                <div
                  class="card-header"
                  style="padding-top: 0px; padding-bottom: 0px"
                >
                  <div class="row align-items-center">
                    <div class="col">
                      <a href="https://www.placetopay.com" target="_BLANK"
                        ><img
                          src="images/payment/placetopay_cards.png"
                          class="img-fluid"
                          alt="effect"
                          style="padding-bottom: 10px"
                      /></a>
                    </div>
                  </div>
                </div>
                <div class="card-body back-light subscribe">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="insert-rechange"> </a-lang>:
                    </p>
                    <input
                      v-model="amount"
                      class="form-control modal-custom-input"
                      :placeholder="getLocate('insert-rechange1')"
                      aria-label="Amount"
                      type="number"
                      step=".01"
                      required
                    />
                    <br /><br />
                    <div class="loteria-text-white">
                      <a-lang value="insert-rechange2"></a-lang>
                      Placetopay
                    </div>
                    <div class="loteria-text-white">
                      <a-lang value="insert-rechange3"></a-lang>
                    </div>
                    <div class="loteria-text-white">
                      <a-lang value="insert-rechange4"></a-lang> 5 USD
                    </div>
                    <div class="loteria-text-white">
                      <a-lang value="insert-rechange5"></a-lang> 3000 USD
                    </div>
                  </div>
                </div>
              </div>
              <div class="button-center">
                <button class="profile-button" @click="confirmDepositPTP()">
                  <a-lang value="request-deposit"> </a-lang>
                </button>
              </div>
              <br />
            </div>
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="row">
                <div class="col">
                  <div class="form-group">
                    <label for="depositState"
                      ><p class="profile-input-label">
                        <a-lang value="deposit-state"> </a-lang></p
                    ></label>
                    <select
                      class="form-control modal-custom-input"
                      style="color: 'black'"
                      id="depositState"
                      v-model="stateDeposit"
                    >
                      <option value="ALL">
                        {{ getLocate("all") }}
                      </option>
                      <option value="PENDING">
                        {{ getLocate("pending") }}
                      </option>
                      <option value="APPROVED">
                        {{ getLocate("approved") }}
                      </option>
                      <option value="REJECTED">
                        {{ getLocate("rejected") }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-from"> </a-lang>
                    </p>
                    <input
                      name="depositDateFrom"
                      v-model="depositDateFrom"
                      id="depositDateFrom"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositPTPTransactions(true)"
                    />
                  </div>
                </div>
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-to"> </a-lang>
                    </p>
                    <input
                      name="depositDateTo"
                      v-model="depositDateTo"
                      id="depositDateTo"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositPTPTransactions(true)"
                    />
                  </div>
                </div>
              </div>
              <div class="card">
                <no-result
                  v-if="
                    depositTransactionsFiltered &&
                    depositTransactionsFiltered.length == 0
                  "
                ></no-result>
                <div
                  id="deposits"
                  class="list-group scroll-list"
                  v-if="!isMetadata"
                >
                  <span
                    class="list-group-item my-align-left"
                    v-for="item in depositTransactionsFiltered"
                    :key="item.request_id"
                  >
                    <small>
                      <i class="fa fa-calendar"></i>
                      <span v-if="item.ts_commit">{{
                        formatDateYYYYMMDDHHmmss(item.ts_commit)
                      }}</span
                      ><span v-else>{{
                        formatDateYYYYMMDDHHmmss(item.ts_reserve)
                      }}</span>
                      <a-lang value="state"> </a-lang>:
                      <b v-if="item.status == 'PENDING'" style="color: orange">
                        {{ getLocate("pending") }}
                      </b>
                      <b
                        v-else-if="item.status == 'REJECTED'"
                        style="color: red"
                      >
                        {{ getLocate("rejected") }}
                      </b>
                      <b
                        v-else-if="item.status == 'APPROVED'"
                        style="color: green"
                      >
                        {{ getLocate("approved") }}
                      </b>
                      <b v-else>
                        {{ item.status }}
                      </b>
                    </small>

                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="reference-id"> </a-lang>:</b
                      ><small>{{ item.reference_id }}</small>
                    </div>
                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="deposit"> </a-lang>:</b>
                      <small>
                        <span
                          :style="
                            item.amount > 0 ? 'color: green;' : 'color: red;'
                          "
                          ><span v-if="item.amount < 0">-</span>
                          <span v-else>+</span
                          >{{ formatterNumber(item.amount, true) }}
                          {{ currencyView(item.currency) }}
                        </span>
                      </small>
                      <small>
                        <a href="#">
                          <i
                            style="color: grey"
                            class="fa fa-chevron-right"
                            @click="
                              getPlacetopayCheck(
                                item.reference_id,
                                item.request_id
                              );
                              isMetadata = true;
                            "
                          ></i>
                        </a>
                      </small>
                    </div>
                  </span>
                </div>

                <div class="list-group scroll-list" v-if="isMetadata">
                  <div>
                    <div class="col-1 col-loteria">
                      <small>
                        <a href="#">
                          <i
                            style="color: grey"
                            class="fa fa-chevron-left"
                            @click="isMetadata = false"
                          ></i>
                        </a>
                      </small>
                    </div>
                    <div class="col-12 col-loteria" v-if="!detailPlacetopay">
                      <a-alert
                        :alert="{
                          type: 'alert-info',
                          message: 'No details found',
                        }"
                      ></a-alert>
                    </div>
                  </div>
                  <a-loading :show="loadingCheck"></a-loading>
                  <span
                    class="list-group-item my-align-left"
                    v-if="detailPlacetopay && !loadingCheck"
                  >
                    <small>
                      <i class="fa fa-calendar"></i>
                      <span
                        v-if="
                          detailPlacetopay.status &&
                          detailPlacetopay.status.date
                        "
                        >{{
                          formatDateYYYYMMDDHHmmss(detailPlacetopay.status.date)
                        }}</span
                      >
                      <a-lang value="state"> </a-lang>:
                      <span v-if="detailPlacetopay.status">
                        <b
                          v-if="detailPlacetopay.status.status == 'PENDING'"
                          style="color: orange"
                        >
                          {{ getLocate("pending") }}
                        </b>
                        <b
                          v-else-if="
                            detailPlacetopay.status.status == 'REJECTED'
                          "
                          style="color: red"
                        >
                          {{ getLocate("rejected") }}
                        </b>
                        <b
                          v-else-if="
                            detailPlacetopay.status.status == 'APPROVED'
                          "
                          style="color: green"
                        >
                          {{ getLocate("approved") }}
                        </b>
                        <b v-else>
                          {{ detailPlacetopay.status.status }}
                        </b>
                      </span>
                    </small>
                    <div
                      class="d-flex w-100 justify-content-between"
                      v-if="
                        detailPlacetopay.info &&
                        detailPlacetopay.info.length > 0
                      "
                    >
                      <b><a-lang value="reference-id"> </a-lang>:</b
                      ><small>{{ detailPlacetopay.info[0].reference }}</small>
                    </div>
                    <div
                      class="d-flex w-100 justify-content-between"
                      v-if="
                        detailPlacetopay.info &&
                        detailPlacetopay.info.length > 0
                      "
                    >
                      <b><a-lang value="issue"> </a-lang>:</b
                      ><small>{{ detailPlacetopay.info[0].issuerName }}</small>
                    </div>
                    <div
                      class="d-flex w-100 justify-content-between"
                      v-if="
                        detailPlacetopay.info &&
                        detailPlacetopay.info.length > 0
                      "
                    >
                      <b><a-lang value="payment-method"> </a-lang>:</b
                      ><small>{{
                        detailPlacetopay.info[0].paymentMethodName
                      }}</small>
                    </div>
                    <div class="row" v-if="detailPlacetopay.status">
                      <div class="col">
                        <small>
                          {{ detailPlacetopay.status.message }}
                        </small>
                      </div>
                    </div>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- MONNET PAYIN -->
      <div v-else-if="!loading && tab == 'monnetpayintab'">
        <div>
          <div class="row row-loteria">
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="card">
                <div
                  class="card-header"
                  style="padding-top: 0px; padding-bottom: 0px"
                >
                  <div class="row align-items-center">
                    <div class="col">
                      <a href="https://www.monnetpayments.com" target="_BLANK"
                        ><img
                          src="images/payment/monnet.png"
                          class="img-fluid"
                          alt="effect"
                          style="padding-bottom: 10px"
                      /></a>
                    </div>
                  </div>
                </div>
                <div class="card-body back-light subscribe">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="insert-rechange"> </a-lang>:
                    </p>
                    <input
                      v-model="amount"
                      class="form-control modal-custom-input"
                      :placeholder="getLocate('insert-rechange1')"
                      aria-label="Amount"
                      type="number"
                      step=".01"
                      required
                    />
                    <br /><br />
                    <p class="profile-input-label">
                      <a-lang value="insert-discount"> </a-lang>:
                    </p>
                    <input
                      v-model="monnetDiscount"
                      class="form-control modal-custom-input"
                      :placeholder="getLocate('code')"
                      aria-label="Discount coupon"
                      type="text"
                    />
                    <br /><br />
                    <p class="profile-input-label">
                      <a-lang value="select-payin-method"> </a-lang>:
                    </p>
                    <select
                      class="form-control modal-custom-input"
                      style="color: 'black'"
                      v-model="monnetPayinMethod"
                      required
                    >
                      <option value="TCTD">
                        {{ getLocate("tarjetas") }}
                      </option>
                      <option value="Cash">
                        {{ getLocate("cash") }}
                      </option>
                      <option value="BankTransfer">
                        {{ getLocate("bank-transfer") }}
                      </option>
                      <option value="Wallet">
                        {{ getLocate("wallet") }}
                      </option>
                    </select>                    
                    <br /><br />
                    <div class="loteria-text-white">
                      <a-lang value="insert-rechange2"></a-lang>
                      Monnet
                    </div>
                    <div class="loteria-text-white">
                      <a-lang value="insert-rechange3"></a-lang>
                    </div>
                    <div class="loteria-text-white">
                      <a-lang value="insert-rechange4"></a-lang> 5 USD
                    </div>
                    <div class="loteria-text-white">
                      <a-lang value="insert-rechange5"></a-lang> 3000 USD
                    </div>
                  </div>
                </div>
              </div>
              <div class="button-center">
                <button class="profile-button" @click="depositMonnet()">
                  <a-lang value="request-deposit"> </a-lang>
                </button>
              </div>
              <br />
            </div>
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="row">
                <div class="col">
                  <div class="form-group">
                    <label for="depositState"
                      ><p class="profile-input-label">
                        <a-lang value="deposit-state"> </a-lang></p
                    ></label>
                    <select
                      class="form-control modal-custom-input"
                      style="color: 'black'"
                      id="depositState"
                      v-model="stateDeposit"
                    >
                      <option value="ALL">
                        {{ getLocate("all") }}
                      </option>
                      <option value="PENDING">
                        {{ getLocate("pending") }}
                      </option>
                      <option value="APPROVED">
                        {{ getLocate("approved") }}
                      </option>
                      <option value="REJECTED">
                        {{ getLocate("rejected") }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-from"> </a-lang>
                    </p>
                    <input
                      name="depositDateFrom"
                      v-model="depositDateFrom"
                      id="depositDateFrom"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositMonnetTransactions()"
                    />
                  </div>
                </div>
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-to"> </a-lang>
                    </p>
                    <input
                      name="depositDateTo"
                      v-model="depositDateTo"
                      id="depositDateTo"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositMonnetTransactions()"
                    />
                  </div>
                </div>
              </div>
              <div class="card">
                <no-result
                  v-if="
                    depositTransactionsFiltered &&
                    depositTransactionsFiltered.length == 0
                  "
                ></no-result>
                <div
                  id="deposits"
                  class="list-group scroll-list"
                >
                  <span
                    class="list-group-item my-align-left"
                    v-for="item in depositTransactionsFiltered"
                    :key="item.request_id"
                  >
                    <small>
                      <i class="fa fa-calendar"></i>
                      <span v-if="item.ts_commit">{{
                        formatDateYYYYMMDDHHmmss(item.ts_commit)
                      }}</span
                      ><span v-else>{{
                        formatDateYYYYMMDDHHmmss(item.ts_reserve)
                      }}</span>
                      <a-lang value="state"> </a-lang>:
                      <b v-if="item.status == 'PENDING'" style="color: orange">
                        {{ getLocate("pending") }}
                      </b>
                      <b
                        v-else-if="item.status == 'REJECTED'"
                        style="color: red"
                      >
                        {{ getLocate("rejected") }}
                      </b>
                      <b
                        v-else-if="item.status == 'APPROVED'"
                        style="color: green"
                      >
                        {{ getLocate("approved") }}
                      </b>
                      <b v-else>
                        {{ item.status }}
                      </b>
                    </small>

                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="reference-id"> </a-lang>:</b
                      ><small>{{ item.reference_id }}</small>
                    </div>
                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="deposit"> </a-lang>:</b>
                      <small>
                        <span
                          :style="
                            item.amount > 0 ? 'color: green;' : 'color: red;'
                          "
                          ><span v-if="item.amount < 0">-</span>
                          <span v-else>+</span
                          >{{ formatterNumber(item.amount, true) }}
                          {{ currencyView(item.currency) }}
                        </span>
                      </small>
                      <small>
                        <a href="#">
                          <i
                            style="color: grey"
                            class="fa fa-chevron-right"
                            @click="
                              getPlacetopayCheck(
                                item.reference_id,
                                item.request_id
                              );
                              isMetadata = true;
                            "
                          ></i>
                        </a>
                      </small>
                    </div>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- PAYCASH -->
      <div v-else-if="!loading && tab == 'paycash'">
        <div>
          <div class="row" v-if="!urlPaycash">
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="card">
                <div
                  class="card-header"
                  style="padding-top: 0px; padding-bottom: 0px"
                >
                  <div class="row align-items-center">
                    <div class="col">
                      <a href="https://www.paycashglobal.com/" target="_BLANK"
                        ><img
                          src="images/paycash.png"
                          class="img-fluid"
                          alt="effect"
                          style="
                            max-width: 140px;
                            padding-bottom: 10px;
                            padding-top: 10px;
                          "
                      /></a>
                    </div>
                  </div>
                </div>
                <div class="card-body back-light">
                  <div class="form-group">
                    <p style="padding-bottom: 0px" class="profile-input-label">
                      <a-lang value="insert-rechange"> </a-lang>:
                    </p>
                    <input
                      v-model="amount"
                      class="form-control modal-custom-input"
                      :placeholder="getLocate('insert-rechange1')"
                      aria-label="Amount paycash"
                      type="number"
                      step=".01"
                      required
                    />
                  </div>
                </div>
              </div>
              <div class="button-center">
                <button class="profile-button" @click="depositPaycash()">
                  <a-lang value="request-deposit"> </a-lang>
                </button>
              </div>
              <br />
            </div>
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="row">
                <div class="col">
                  <div class="form-group">
                    <label for="depositState"
                      ><p class="profile-input-label">
                        <a-lang value="deposit-state"> </a-lang></p
                    ></label>
                    <select
                      class="form-control modal-custom-input"
                      style="color: 'black'"
                      id="depositState"
                      v-model="statePaycashDeposit"
                    >
                      <option value="ALL">
                        {{ getLocate("all") }}
                      </option>
                      <option value="PENDING">
                        {{ getLocate("pending") }}
                      </option>
                      <option value="APPROVED">
                        {{ getLocate("approved") }}
                      </option>
                      <option value="REJECTED">
                        {{ getLocate("rejected") }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-from"> </a-lang>
                    </p>
                    <input
                      name="depositDateFrom"
                      v-model="depositDateFrom"
                      id="depositDateFrom"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositPaycashTransactions()"
                    />
                  </div>
                </div>
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-to"> </a-lang>
                    </p>
                    <input
                      name="depositDateTo"
                      v-model="depositDateTo"
                      id="depositDateTo"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositPaycashTransactions()"
                    />
                  </div>
                </div>
              </div>
              <div class="card">
                <no-result
                  v-if="
                    depositPaycashTransactionsFiltered &&
                    depositPaycashTransactionsFiltered.length == 0
                  "
                ></no-result>

                <div id="deposits" class="list-group scroll-list">
                  <span
                    class="list-group-item my-align-left"
                    v-for="item in depositPaycashTransactionsFiltered"
                    :key="item.reference_id"
                  >
                    <small>
                      <i class="fa fa-calendar"></i>
                      <span v-if="item.ts_commit">{{
                        formatDateYYYYMMDDHHmmss(item.ts_commit)
                      }}</span
                      ><span v-else>{{
                        formatDateYYYYMMDDHHmmss(item.ts_reserve)
                      }}</span>
                      <a-lang value="state"> </a-lang>:
                      <b v-if="item.status == 'PENDING'" style="color: orange">
                        {{ getLocate("pending") }}
                      </b>
                      <b
                        v-else-if="item.status == 'REJECTED'"
                        style="color: red"
                      >
                        {{ getLocate("rejected") }}
                      </b>
                      <b
                        v-else-if="item.status == 'APPROVED'"
                        style="color: green"
                      >
                        {{ getLocate("approved") }}
                      </b>
                      <b v-else>
                        {{ item.status }}
                      </b>
                    </small>

                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="reference-id"> </a-lang>:</b
                      ><small>{{ item.reference_id }}</small>
                    </div>
                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="reference-id"> </a-lang> Paycash:</b
                      ><small>{{ item.paycash_reference_id }}</small>
                    </div>
                    <div class="row">
                      <div class="col-8 col-loteria">
                        <small>
                          <a-lang value="deposit"> </a-lang>:
                          <span
                            :style="
                              item.amount > 0 ? 'color: green;' : 'color: red;'
                            "
                            ><span v-if="item.amount < 0">-</span>
                            <span v-else>+</span
                            >{{ formatterNumber(item.amount, true) }}
                            {{ currencyView(item.currency) }}
                          </span>
                        </small>
                      </div>
                    </div>
                  </span>
                </div>
              </div>
            </div>
          </div>
          <iframe
            id="iframeRef"
            v-if="urlPaycash"
            :src="urlPaycash"
            width="100%"
            style="height: 72vh"
            framespacing="0"
            border="0"
            frameborder="0"
            scrolling="yes"
          ></iframe>
        </div>
      </div>
      <!-- NICO -->
      <div v-else-if="!loading && tab == 'nico'">
        <div>
          <div class="row">
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="card">
                <div
                  class="card-header"
                  style="padding-top: 0px; padding-bottom: 0px"
                >
                  <div class="row align-items-center">
                    <div class="col">
                      <a href="https://n1co.com/" target="_BLANK"
                        ><img
                          src="images/nico.png"
                          class="img-fluid"
                          alt="effect"
                          style="
                            max-width: 140px;
                            padding-bottom: 10px;
                            padding-top: 10px;
                          "
                      /></a>
                    </div>
                  </div>
                </div>
                <div class="card-body back-light">
                  <div class="form-group">
                    <p style="padding-bottom: 0px" class="profile-input-label">
                      <a-lang value="insert-rechange"> </a-lang>:
                    </p>
                    <input
                      v-model="amount"
                      class="form-control modal-custom-input"
                      :placeholder="getLocate('insert-rechange1')"
                      aria-label="Amount paycash"
                      type="number"
                      step=".01"
                      required
                    />
                  </div>
                </div>
              </div>
              <div class="button-center">
                <button class="profile-button" @click="depositNico()">
                  <a-lang value="request-deposit"> </a-lang>
                </button>
              </div>
            </div>
            <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria">
              <div class="row">
                <div class="col">
                  <div class="form-group">
                    <label for="depositState"
                      ><p class="profile-input-label">
                        <a-lang value="deposit-state"> </a-lang></p
                    ></label>
                    <select
                      class="form-control modal-custom-input"
                      style="color: 'black'"
                      id="depositState"
                      v-model="stateNicoDeposit"
                    >
                      <option value="ALL">
                        {{ getLocate("all") }}
                      </option>
                      <option value="SuccessPayment">
                        {{ getLocate("approved") }}
                      </option>
                      <option value="PaymentError">
                        {{ getLocate("rejected") }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-from"> </a-lang>
                    </p>
                    <input
                      name="depositDateFrom"
                      v-model="depositDateFrom"
                      id="depositDateFrom"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositNicoTransactions()"
                    />
                  </div>
                </div>
                <div class="col-12">
                  <div class="form-group">
                    <p class="profile-input-label">
                      <a-lang value="date-to"> </a-lang>
                    </p>
                    <input
                      name="depositDateTo"
                      v-model="depositDateTo"
                      id="depositDateTo"
                      class="form-control modal-custom-input"
                      type="date"
                      required
                      @change="getDepositNicoTransactions()"
                    />
                  </div>
                </div>
              </div>
              <div class="card">
                <no-result
                  v-if="
                    depositNicoTransactionsFiltered &&
                    depositNicoTransactionsFiltered.length == 0
                  "
                ></no-result>

                <div id="deposits" class="list-group scroll-list">
                  <span
                    class="list-group-item my-align-left"
                    v-for="item in depositNicoTransactionsFiltered"
                    :key="item.reference_id"
                  >
                    <small>
                      <i class="fa fa-calendar"></i>
                      <span v-if="item.ts_commit">{{
                        formatDateYYYYMMDDHHmmss(item.ts_commit)
                      }}</span
                      ><span v-else>{{
                        formatDateYYYYMMDDHHmmss(item.ts_reserve)
                      }}</span>
                      <a-lang value="state"> </a-lang>:
                      <b
                        v-if="item.status == 'PaymentError'"
                        style="color: red"
                      >
                        {{ getLocate("rejected") }}
                      </b>
                      <b
                        v-else-if="item.status == 'SuccessPayment'"
                        style="color: green"
                      >
                        {{ getLocate("approved") }}
                      </b>
                      <b v-else>
                        {{ item.status }}
                      </b>
                    </small>

                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="reference-id"> </a-lang>:</b
                      ><small>{{ item.reference_id }}</small>
                    </div>
                    <div class="d-flex w-100 justify-content-between">
                      <b><a-lang value="reference-id"> </a-lang> Nico:</b
                      ><small>{{ item.order_id }}</small>
                    </div>
                    <div class="row">
                      <div class="col-8 col-loteria">
                        <small>
                          <a-lang value="deposit"> </a-lang>:
                          <span
                            :style="
                              item.amount > 0 ? 'color: green;' : 'color: red;'
                            "
                            ><span v-if="item.amount < 0">-</span>
                            <span v-else>+</span
                            >{{ formatterNumber(item.amount, true) }}
                            {{ currencyView(item.currency) }}
                          </span>
                        </small>
                      </div>
                    </div>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- modal confirm deposit -->
    <div
      class="modal fade"
      id="confirmDepositModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="confirmDepositLabel"
      aria-hidden="true"
      style="top: 3vh"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content modal-custom-content back-light">
          <div class="modal-header">
            <button
              type="button"
              class="close loteria-text-white"
              @click="$('#confirmDepositModal').modal('hide')"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="container-fluid">
              <br />
              <div class="alert alert-danger" role="alert">
                <a-lang value="request-pending-message"> </a-lang>:<br />
                <span v-if="lastDepositPending">{{ lastDepositPending }}</span
                ><br />
                <a-lang value="request-pending-message1"> </a-lang>
              </div>
              <br />
            </div>
          </div>
          <div class="modal-footer justify-content-center">
            <button class="profile-button" @click="depositPTP()">
              <a-lang value="request-deposit"> </a-lang>
            </button>
            <button
              class="profile-button"
              @click="$('#confirmDepositModal').modal('hide')"
            >
              <a-lang value="request-deposit"> </a-lang>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  props: ["loading"],
  data() {
    return {
      //tab deposit
      depositTransactions: [],
      depositNuveiTransactions: [],
      detailPlacetopay: {},
      stateDeposit: "ALL",
      stateNuveiDeposit: "ALL",
      loadingCheck: false,
      intervalDeposit: null,
      depositOff: true,
      depositDateFrom: this.formatDateYYYYMMDD(this.addDays(new Date(), -1)),
      depositDateTo: this.formatDateYYYYMMDD(new Date()),
      depositNuveiType: "cc_card",
      depositPaycashTransactions: [],
      statePaycashDeposit: "ALL",
      depositNicoTransactions: [],
      stateNicoDeposit: "ALL",
      monnetPayinMethod: "TCTD",
      monnetDiscount: "",
      depositMonnetTransactions: [],
      stateMonnetDeposit: "ALL",
      urlPaycash: null,
      depositType: null,
      showPreviousButton: false,
      showList: true,
      amount: null,
      isMetadata: false,
      tab: "menu",
      currency: store.state.currency,
      paymentView: "tarjetas",
    };
  },
  created() {
    this.getDeposits();
  },
  computed: {
    lastDepositPending() {
      let depositTransactions = this.depositTransactions;
      // escludo quelle in stato ok
      depositTransactions = depositTransactions.filter((d) => {
        if (
          d.status.toString().toUpperCase().includes("OK") ||
          d.status.toString().toUpperCase().includes("PENDING")
        )
          return true;
        else return false;
      });
      if (depositTransactions && depositTransactions.length > 0)
        return depositTransactions[0].reference_id;
      return null;
    },
    depositTransactionsFiltered() {
      let depositTransactions = this.depositTransactions;
      // escludo quelle in stato ok
      depositTransactions = depositTransactions.filter((d) => {
        if (!d.status.toString().toUpperCase().includes("OK")) return true;
        else return false;
      });

      if (this.stateDeposit != null && this.stateDeposit != "ALL") {
        depositTransactions = depositTransactions.filter((d) => {
          if (
            d.status
              .toString()
              .toUpperCase()
              .includes(this.stateDeposit.toUpperCase())
          )
            return true;
          else return false;
        });
      }
      return depositTransactions;
    },
    depositNuveiTransactionsFiltered() {
      let depositNuveiTransactions = this.depositNuveiTransactions;

      if (this.stateNuveiDeposit != null && this.stateNuveiDeposit != "ALL") {
        depositNuveiTransactions = depositNuveiTransactions.filter((d) => {
          if (
            d.status &&
            d.status
              .toString()
              .toUpperCase()
              .includes(this.stateNuveiDeposit.toUpperCase())
          )
            return true;
          else return false;
        });
      }
      return depositNuveiTransactions;
    },
    depositMonnetTransactionsFiltered() {
      let depositMonnetTransactions = this.depositMonnetTransactions;

      if (this.stateMonnetDeposit != null && this.stateMonnetDeposit != "ALL") {
        depositMonnetTransactions = depositMonnetTransactions.filter((d) => {
          if (
            d.status &&
            d.status
              .toString()
              .toUpperCase()
              .includes(this.stateMonnetDeposit.toUpperCase())
          )
            return true;
          else return false;
        });
      }
      return depositMonnetTransactions;
    },
    depositPaycashTransactionsFiltered() {
      let depositPaycashTransactions = this.depositPaycashTransactions;

      if (
        this.statePaycashDeposit != null &&
        this.statePaycashDeposit != "ALL"
      ) {
        depositPaycashTransactions = depositPaycashTransactions.filter((d) => {
          if (
            d.status &&
            d.status
              .toString()
              .toUpperCase()
              .includes(this.statePaycashDeposit.toUpperCase())
          )
            return true;
          else return false;
        });
      }
      return depositPaycashTransactions;
    },
    depositNicoTransactionsFiltered() {
      let depositNicoTransactions = this.depositNicoTransactions;

      if (this.stateNicoDeposit != null && this.stateNicoDeposit != "ALL") {
        depositNicoTransactions = depositNicoTransactions.filter((d) => {
          if (
            d.status &&
            d.status
              .toString()
              .toUpperCase()
              .includes(this.stateNicoDeposit.toUpperCase())
          )
            return true;
          else return false;
        });
      }
      return depositNicoTransactions;
    },
  },
  methods: {
    checkPsd() {
      this.$http
        .post("checkPsd")
        .then((response) => {
          let result = response.data;
          this.isPsdTransferFunds = result.isPsdTransferFunds;
          this.isPsdPswError = result.isPsdPswError;
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    getDeposits() {
      this.resetMessages();
      this.urlPaycash = null;
      this.isMetadata = false;
      this.checkPsd();
    },
    getDepositPTPTransactions(isLoading) {
      this.resetMessages();
      if (isLoading) this.changeLoading(true);
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom)) {
        this.changeLoading(false);
        return;
      }

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.depositDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.depositDateTo, 1))
      );
      this.$http
        .post("deposit/placetopay/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.depositTransactions = result.deposits;
            this.depositTransactions.sort(function (a, b) {
              if (a.ts_reserve != null && b.ts_reserve != null) {
                if (new Date(a.ts_reserve) < new Date(b.ts_reserve)) {
                  return 1;
                }
                if (new Date(a.ts_reserve) > new Date(b.ts_reserve)) {
                  return -1;
                }
                return 0;
              }
              return 0;
            });
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          if (isLoading) this.changeLoading(false);
        });
    },
    getDepositNuveiTransactions() {
      this.resetMessages();
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom))
        return;
      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.depositDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.depositDateTo, 1))
      );
      formData.append("paymentMethod", this.depositNuveiType);
      this.$http
        .post("deposit/nuvei/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.depositNuveiTransactions = result.deposits;
            this.depositNuveiTransactions.sort(function (a, b) {
              if (a.ts_reserve != null && b.ts_reserve != null) {
                if (new Date(a.ts_reserve) < new Date(b.ts_reserve)) {
                  return 1;
                }
                if (new Date(a.ts_reserve) > new Date(b.ts_reserve)) {
                  return -1;
                }
                return 0;
              }
              return 0;
            });
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.changeLoading(false);
        });
    },
    getDepositMonnetTransactions() {
      this.resetMessages();
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom))
        return;
      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.depositDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.depositDateTo, 1))
      );
      this.$http
        .post("deposit/monnet/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.depositMonnetTransactions = result.deposits;
            this.depositMonnetTransactions.sort(function (a, b) {
              if (a.ts_reserve != null && b.ts_reserve != null) {
                if (new Date(a.ts_reserve) < new Date(b.ts_reserve)) {
                  return 1;
                }
                if (new Date(a.ts_reserve) > new Date(b.ts_reserve)) {
                  return -1;
                }
                return 0;
              }
              return 0;
            });
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.changeLoading(false);
        });
    },
    getDepositPaycashTransactions() {
      this.resetMessages();
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom))
        return;
      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.depositDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.depositDateTo, 1))
      );
      this.$http
        .post("deposit/paycash/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.depositPaycashTransactions = result.deposits;
            this.depositPaycashTransactions.sort(function (a, b) {
              if (a.ts_reserve != null && b.ts_reserve != null) {
                if (new Date(a.ts_reserve) < new Date(b.ts_reserve)) {
                  return 1;
                }
                if (new Date(a.ts_reserve) > new Date(b.ts_reserve)) {
                  return -1;
                }
                return 0;
              }
              return 0;
            });
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.changeLoading(false);
        });
    },
    getDepositNicoTransactions() {
      this.resetMessages();
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom))
        return;
      this.changeLoading(true);
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.depositDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.depositDateTo, 1))
      );
      this.$http
        .post("deposit/nico/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.depositNicoTransactions = result.deposits;
            this.depositNicoTransactions.sort(function (a, b) {
              if (a.ts_reserve != null && b.ts_reserve != null) {
                if (new Date(a.ts_reserve) < new Date(b.ts_reserve)) {
                  return 1;
                }
                if (new Date(a.ts_reserve) > new Date(b.ts_reserve)) {
                  return -1;
                }
                return 0;
              }
              return 0;
            });
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.changeLoading(false);
        });
    },
    checkAmountDeposit() {
      this.resetMessages();

      if (!this.amount) {
        this.setError(this.getLocate("profile-1"));
        return false;
      }

      //converto in centesimi il valore inserito
      let value = this.convertNumberToCent(this.amount);
      //valido controllando che il valore inserito non sia minore/uguale a zero
      if (value <= 0) {
        this.setError(this.getLocate("profile-1"));
        return false;
      }

      //valido controllando che la currency sia stata correttamente caricata (viene inizializzata da getBalance: Non Cashable)
      if (
        this.currency == null ||
        this.currency == "" ||
        this.currency == undefined
      ) {
        this.setError(this.getLocate("login-7"));
        return false;
      }

      console.log("4");

      return true;
    },
    checkAmountNuvei() {
      this.setError(null);
      this.setSuccess(null);

      if (
        this.depositNuveiType == "apmgw_SafetyPay_Online" ||
        this.depositNuveiType == "apmgw_SafetyPay_Cash"
      ) {
        let amountMax = 700;
        if (this.depositNuveiType == "apmgw_SafetyPay_Online") amountMax = 5000;

        if (this.amount < 5 || this.amount > amountMax) {
          this.setError(
            this.getLocate("insert-rechange4") +
              " 5 USD, " +
              this.getLocate("insert-rechange5") +
              " " +
              amountMax +
              " USD"
          );
          return false;
        }
      }

      if (this.depositNuveiType == "cc_card") {
        if (this.amount < 5 || this.amount > 3000) {
          this.setError(
            this.getLocate("insert-rechange4") +
              " 5 USD, " +
              this.getLocate("insert-rechange5") +
              " 3000 USD"
          );
          return false;
        }
      }

      return true;
    },
    checkAmountPlacetopay() {
      this.setError(null);
      this.setSuccess(null);

      if (this.amount < 5 || this.amount > 3000) {
        this.setError(
          this.getLocate("insert-rechange4") +
            " 5 USD, " +
            this.getLocate("insert-rechange5") +
            " 3000 USD"
        );
        return false;
      }

      return true;
    },
    confirmDepositPTP() {
      let modal = document.getElementById("modal-body-scroll");
      modal.scrollTop = 0;
      if (!this.checkAmountDeposit()) return;
      if (!this.checkAmountPlacetopay()) return;

      // check if request pending
      if (this.lastDepositPending) {
        this.depositOff = false;
        $("#confirmDepositModal").modal({
          backdrop: "static",
          keyboard: false,
        });
      } else this.depositPTP();
    },
    // richiesta di ricarica, se va a buon fine ritorna deposit referenceid
    depositPTP() {
      if (!this.checkAmountDeposit()) return;
      //mando la richiesta
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("amount", this.convertNumberToCent(this.amount));
      formData.append("currency", this.currency);
      this.$http
        .post("deposit/placetopay/reserve", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.setError(result.messageKo);
          } else {
            this.depositOff = false;
            $("#confirmDepositModal").modal("hide");
            this.reference = result.reference;
            this.requestId = result.requestId;
            this.openPopup(result.processUrl);
            this.amount = null;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    depositNuvei() {
      let modal = document.getElementById("modal-body-scroll");
      modal.scrollTop = 0;
      if (!this.checkAmountDeposit()) return;
      if (!this.checkAmountNuvei()) return;
      this.changeLoading(true);
      //mando la richiesta
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("amount", this.convertNumberToCent(this.amount));
      formData.append("currency", this.currency);
      formData.append("origin", this.deviceType());
      formData.append("paymentMethod", this.depositNuveiType);
      this.$http
        .post("deposit/nuvei/reserve", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.changeLoading(false);
            this.setError(result.messageKo);
          } else {
            this.openPopup(result.processUrl);
            this.amount = null;
          }
        })
        .catch((response) => {
          this.changeLoading(false);
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    depositMonnet() {
      let modal = document.getElementById("modal-body-scroll");
      modal.scrollTop = 0;
      if (!this.checkAmountDeposit()) return;
      this.changeLoading(true);
      //mando la richiesta
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("amount", this.convertNumberToCent(this.amount));
      formData.append("currency", this.currency);
      formData.append("discountcoupon", this.monnetDiscount);
      formData.append("payinmethod", this.monnetPayinMethod);
      this.$http
        .post("deposit/monnet/reserve", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.setError(result.messageKo);
          } else {
            this.amount = null;
            window.open(result.processUrl, "_blank");
            // $("#userProfile").modal("hide");
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.changeLoading(false);
        });
    },
    depositPaycash() {
      let modal = document.getElementById("modal-body-scroll");
      modal.scrollTop = 0;
      this.urlPaycash = null;
      if (!this.checkAmountDeposit()) return;
      this.changeLoading(true);
      //mando la richiesta
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("amount", this.convertNumberToCent(this.amount));
      formData.append("currency", this.currency);
      this.$http
        .post("deposit/paycash/reserve", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.setError(result.messageKo);
          } else {
            //window.open(result.processUrl, "_blank");
            this.urlPaycash = result.processUrl;
            this.amount = null;
            // $("#userProfile").modal("hide");
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.changeLoading(false);
        });
    },
    depositNico() {
      let modal = document.getElementById("modal-body-scroll");
      modal.scrollTop = 0;
      if (!this.checkAmountDeposit()) return;
      this.changeLoading(true);
      //mando la richiesta
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("amount", this.convertNumberToCent(this.amount));
      formData.append("currency", this.currency);
      this.$http
        .post("deposit/nico/reserve", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.setError(result.messageKo);
          } else {
            window.open(result.processUrl, "_blank");
            this.amount = null;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.changeLoading(false);
        });
    },
    getPlacetopayCheck(reference, requestid) {
      this.loadingCheck = true;
      this.detailPlacetopay = {};
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("reference", reference);
      //formData.append("requestid", requestid);
      this.$http
        .post("deposit/placetopay/check", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.detailPlacetopay = result;
          } else {
            this.setError(result.description);
          }
        })
        .catch((response) => {
          this.showError(response);
        })
        .finally(() => {
          this.loadingCheck = false;
        });
    },
    getMetadata(transactionid) {
      this.metadata = [];
      let formData = new FormData();
      formData.append("transactionid", transactionid);
      this.$http
        .post("transaction/metadata", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.metadata = result.metadata;
          } else {
            this.setError(result.description || result.error);
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    openPopup(url) {
      //window.open(url, "_blank");
      location.href = url;
    },
    resetMessages() {
      this.$emit("reset-messages");
    },
    changeLoading(value) {
      this.$emit("change-loading", value);
    },
    setError(message) {
      this.$emit("set-error", message);
    },
    setSuccess(message) {
      this.$emit("set-success", message);
    },
    checkDateFilter(date1, date2) {
      if (!date1 || !date2) {
        this.setError(this.getLocate("check-date-4"));
        return false;
      }

      const MAX_DAYS_VIEW = 30;
      this.error = false;
      this.errorMessage = null;
      const diffDays = this.differenceTwoDate(date1, date2);
      if (diffDays > MAX_DAYS_VIEW) {
        this.setError(
          this.getLocate("check-date-1") +
            " " +
            MAX_DAYS_VIEW +
            " " +
            this.getLocate("check-date-2")
        );
        return false;
      }
      if (diffDays < 0) {
        this.setError(this.getLocate("check-date-3"));
        return false;
      }
      return true;
    },
  },
};
</script>
