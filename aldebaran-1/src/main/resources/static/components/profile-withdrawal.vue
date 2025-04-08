<template>
  <div>
    <div>
      <!-- SUB TABS WITHDRAWAL -->
      <ul
        id="withdrawalTabs"
        class="custom-wallet-nav nav flex-row nav-pills text-center"
        v-show="!loading"
      >
        <li class="nav-item" v-if="isPdsUser()">
          <a
            href="#withdrawalPsdSub"
            class="nav-link withdraws-nav-link"
            data-toggle="pill"
            @click="
              resetWithdrawal('psd');
              resetMessages();
              transactionFees();
              getWithdrawPsd();
              checkPersonaId();
              activaTab('withdrawalPsdSub');
              isMetadata = false;
            "
            ><i :class="'fa fa-bank'"></i>
            <a-lang value="efectivo"> </a-lang>
          </a>
        </li>
        <li class="nav-item" v-if="isBanktransferUser()">
          <a
            href="#withdrawalBankSub"
            class="nav-link withdraws-nav-link"
            data-toggle="pill"
            @click="
              resetWithdrawal('bank');
              resetMessages();
              getBanks();
              transactionFees();
              getWithdrawPsd();
              checkPersonaId();
              activaTab('withdrawalBankSub');
              isMetadata = false;
              acceptConditions = false;
              acceptConditionKey = (acceptConditionKey + 1) % 2;
            "
            ><i :class="'fa fa-bank'"></i>
            <a-lang value="bank-transfer"> </a-lang>
          </a>
        </li>
        <li class="nav-item" v-if="isMonnetPayoutUser()">
          <a
            href="#monnetWithdrawSub"
            class="nav-link withdraws-nav-link"
            data-toggle="pill"
            @click="
              resetWithdrawal('monnet');
              resetMessages();
              getBanksMonnet();
              transactionFees();
              getWithdrawMonnet();
              activaTab('monnetWithdrawSub');
              isMetadata = false;
              acceptConditions = false;
              acceptConditionKey = (acceptConditionKey + 1) % 2;
            "
            ><i :class="'fa fa-bank'"></i>
            <a-lang value="monnet"> </a-lang>
          </a>
        </li>
      </ul>

      <!-- SUBTABS END -->
      <div
        class="tab-content scroll-tab-content"
        :style="!isDesktop() ? 'max-height:76vh' : ''"
      >
        <div
          :class="isPdsUser() ? 'tab-pane show active' : 'tab-pane show'"
          id="withdrawalPsdSub"
        >
          <div class="form-group" v-if="!loading">
            <div class="row">
              <div class="col-12 col-loteria">
                <br />
                <p class="profile-input-label">
                  <b><a-lang value="cashable-credits"> </a-lang>: </b
                  ><span v-if="cashableCredits != null">{{
                    formatterNumber(cashableCredits, true)
                  }}</span>
                  <span v-if="currencyWithdrawal != null">{{
                    currencyWithdrawal
                  }}</span>
                </p>
                <br />
              </div>
            </div>
            <div class="row row-loteria">
              <div
                class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria"
              >
                <div class="card">
                  <div class="card-header">
                    <img
                      src="images/retirada-efectivo.png"
                      class="img-fluid"
                      alt="effect"
                    />
                  </div>
                  <div class="card-body back-light">
                    <p class="profile-input-label" v-if="isPsdPersonaId">
                      <a-lang value="insert-wire-transfer"> </a-lang>
                    </p>

                    <div class="form-group" v-if="isPsdPersonaId">
                      <input
                        id="amountWithdrawPsd"
                        v-model="amountWithdrawPsd"
                        class="form-control modal-custom-input"
                        :placeholder="
                          getLocate('amount') +
                          ' ' +
                          currencyView(store.state.currency) +
                          ' 0.00'
                        "
                        aria-label="Amount"
                        type="number"
                        step=".01"
                        required
                      />
                      <br />
                      <span class="profile-input-label">
                        <a-lang value="punto-suerte-message-7"> </a-lang>
                        <br />
                        <ul style="list-style-type: circle; margin-left: 30px">
                          <li>
                            <a-lang value="punto-suerte-message-10"> </a-lang>
                          </li>
                          <li>
                            <a-lang value="punto-suerte-message-11"> </a-lang>
                          </li>
                          <li>
                            <a-lang value="punto-suerte-message-12"> </a-lang>
                          </li>
                        </ul>
                        <br />
                        <a-lang value="punto-suerte-message-13"> </a-lang>
                      </span>
                    </div>
                    <div v-if="!isPsdPersonaId">
                      <div class="alert alert-danger" role="alert">
                        <a-lang value="error-personaid-psd"> </a-lang>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="button-center">
                  <button
                    v-if="isPsdPersonaId"
                    class="profile-button"
                    @click="reserveWithdrawPsd()"
                  >
                    <a-lang value="request-withdraw"> </a-lang>
                  </button>
                </div>
                <br />
              </div>

              <div
                class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria"
              >
                <div class="row">
                  <div class="col-6 col-loteria">
                    <div class="form-group">
                      <p class="profile-input-label">
                        <a-lang value="date-from"> </a-lang>
                      </p>
                      <input
                        name="withdrawalDateFrom"
                        v-model="withdrawalDateFrom"
                        id="withdrawalDateFrom"
                        class="form-control modal-custom-input"
                        type="date"
                        required
                        @change="getWithdrawPsd"
                      />
                    </div>
                  </div>
                  <div class="col-6 col-loteria">
                    <div class="form-group">
                      <p class="profile-input-label">
                        <a-lang value="date-to"> </a-lang>
                      </p>
                      <input
                        name="withdrawalDateTo"
                        v-model="withdrawalDateTo"
                        id="withdrawalDateTo"
                        class="form-control modal-custom-input"
                        type="date"
                        required
                        @change="getWithdrawPsd"
                      />
                    </div>
                  </div>
                </div>
                <div class="card">
                  <no-result
                    v-if="withdrawalPsd && withdrawalPsd.length == 0"
                  ></no-result>
                  <div class="list-group scroll-list">
                    <span
                      class="list-group-item my-align-left box-list-element"
                      v-for="item in withdrawalPsd"
                      :key="item.withdraw_id"
                    >
                      <small>
                        <i class="fa fa-calendar"></i>
                        <span v-if="item.ts_commit != null">{{
                          formatDateYYYYMMDDHHmmss(item.ts_commit)
                        }}</span
                        ><span v-else-if="item.ts_reserve != null">{{
                          formatDateYYYYMMDDHHmmss(item.ts_reserve)
                        }}</span>
                        State:
                        <b v-if="item.ts_cancel != null" style="color: red"
                          ><a-lang value="bet Void"></a-lang
                        ></b>
                        <b
                          v-else-if="item.ts_commit != null"
                          style="color: green"
                          ><a-lang value="commit1"></a-lang></b
                        ><b
                          v-else-if="item.ts_reserve != null"
                          style="color: #fdb200"
                          ><a-lang value="pending"></a-lang
                        ></b>
                      </small>
                      <div
                        class="d-flex w-100 justify-content-between"
                        v-if="item.withdraw_id != null"
                      >
                        <b> <a-lang value="withdraw-id"> </a-lang>:</b
                        ><small>{{ item.withdraw_id }}</small>
                      </div>
                      <div
                        class="d-flex w-100 justify-content-between"
                        v-if="item.retiro_id != null"
                      >
                        <b> <a-lang value="insert-coupon1"> </a-lang>:</b
                        ><small>{{ item.retiro_id }}</small>
                      </div>
                      <div
                        class="d-flex w-100 justify-content-between"
                        v-if="item.cause != null"
                      >
                        <b><a-lang value="cause"> </a-lang>:</b
                        ><small>{{ item.cause }}</small>
                      </div>
                      <div class="d-flex w-100 justify-content-between">
                        <b><a-lang value="amount"> </a-lang>:</b>
                        <small
                          :style="
                            item.amount > 0 ? 'color: green;' : 'color: red;'
                          "
                          ><span v-if="item.amount < 0">-</span>
                          <span v-else>+</span
                          >{{ formatterNumber(item.amount, true) }}
                          {{ currencyView(item.currency) }}
                        </small>
                      </div>
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div
          :class="
            isBanktransferUser() && !isPdsUser()
              ? 'tab-pane show active'
              : 'tab-pane show'
          "
          id="withdrawalBankSub"
        >
          <accept-conditions
            :accepttextlist="[
              {
                text: getLocate('accept-transfer-data'),
                accept: false,
              },
            ]"
            :mandatoryacceptindexes="[0]"
            @accept="
              (value) => {
                changeAcceptConditions(value, 'bank');
              }
            "
            :key="acceptConditionKey"
          ></accept-conditions>
          <div class="form-group" v-if="!loading && acceptConditions">
            <div class="row">
              <div class="col-12 col-loteria">
                <br />
                <p class="profile-input-label">
                  <b><a-lang value="cashable-credits"> </a-lang>: </b
                  ><span v-if="cashableCredits != null">{{
                    formatterNumber(cashableCredits, true)
                  }}</span>
                  <span v-if="currencyWithdrawal != null">{{
                    currencyWithdrawal
                  }}</span>
                </p>
                <br />
              </div>
            </div>
            <div class="row row-loteria">
              <div
                class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria"
              >
                <div class="card">
                  <div class="card-header">
                    <img
                      src="images/retirada-bancaria.png"
                      class="img-fluid"
                      alt="effect"
                      style="max-width: 250px"
                    />
                  </div>
                  <div class="card-body back-light">
                    <span class="profile-input-label" v-if="isPsdPersonaId">
                      <a-lang value="punto-suerte-message-1"> </a-lang>
                      <br />
                      <a-lang value="punto-suerte-message-8"> </a-lang>
                      <br />
                      <a-lang value="punto-suerte-message-14"> </a-lang>
                      <br /><br />
                      <a-lang value="punto-suerte-message-9"> </a-lang>
                    </span>
                    <br />
                    <div
                      class="form-group"
                      v-if="isPsdPersonaId"
                      style="margin-top: 10px"
                    >
                      <p
                        style="padding-bottom: 0px"
                        class="profile-input-label"
                      >
                        <a-lang value="bank"> </a-lang>
                      </p>
                      <select
                        class="form-control modal-custom-input"
                        style="color: 'black'"
                        id="bank"
                        v-model="bankId"
                      >
                        <option v-for="b in banks" :key="b" :value="b.bankId">
                          {{ b.name }}
                        </option>
                      </select>
                    </div>
                    <div class="form-group" v-if="isPsdPersonaId">
                      <p
                        style="padding-bottom: 0px"
                        class="profile-input-label"
                      >
                        <a-lang value="account-type"> </a-lang>
                      </p>
                      <select
                        class="form-control modal-custom-input"
                        style="color: 'black'"
                        id="accountType"
                        v-model="accountType"
                      >
                        <option value="Ahorros">
                          {{ getLocate("ahorros") }}
                        </option>
                        <option value="Corriente">
                          {{ getLocate("corriente") }}
                        </option>
                      </select>
                    </div>
                    <div class="form-group" v-if="isPsdPersonaId">
                      <p
                        style="padding-bottom: 0px"
                        class="profile-input-label"
                      >
                        <a-lang value="cta-number"> </a-lang>
                      </p>
                      <input
                        id="ctaNumber"
                        v-model="ctaNumber"
                        class="form-control modal-custom-input"
                        required
                      />
                    </div>
                    <div class="form-group" v-if="isPsdPersonaId">
                      <p
                        style="padding-bottom: 0px"
                        class="profile-input-label"
                      >
                        <a-lang value="withdraw-value"> </a-lang>
                      </p>
                      <input
                        id="amountWithdrawBank"
                        v-model="amountWithdrawPsd"
                        class="form-control modal-custom-input"
                        :placeholder="
                          getLocate('amount') +
                          ' ' +
                          currencyView(store.state.currency) +
                          ' 0.00'
                        "
                        aria-label="Amount"
                        type="number"
                        step=".01"
                        required
                      />
                    </div>
                    <div v-if="!isPsdPersonaId">
                      <div class="alert alert-danger" role="alert">
                        <a-lang value="error-personaid-psd"> </a-lang>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="button-center">
                  <button
                    class="profile-button"
                    @click="checkLimitBankWithdraw()"
                  >
                    <a-lang value="request-withdraw"> </a-lang>
                  </button>
                </div>
                <br />
              </div>

              <div
                class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria"
                style="margin-top: 10px"
              >
                <div class="row">
                  <div class="col-6 col-loteria">
                    <div class="form-group">
                      <p class="profile-input-label">
                        <a-lang value="date-from"> </a-lang>
                      </p>
                      <input
                        name="withdrawalDateFrom"
                        v-model="withdrawalDateFrom"
                        id="withdrawalDateFrom"
                        class="form-control modal-custom-input"
                        type="date"
                        required
                        @change="getWithdrawPsd"
                      />
                    </div>
                  </div>
                  <div class="col-6 col-loteria">
                    <div class="form-group">
                      <p class="profile-input-label">
                        <a-lang value="date-to"> </a-lang>
                      </p>
                      <input
                        name="withdrawalDateTo"
                        v-model="withdrawalDateTo"
                        id="withdrawalDateTo"
                        class="form-control modal-custom-input"
                        type="date"
                        required
                        @change="getWithdrawPsd"
                      />
                    </div>
                  </div>
                </div>
                <div class="card">
                  <no-result
                    v-if="withdrawalPsd && withdrawalPsd.length == 0"
                  ></no-result>
                  <div class="list-group scroll-list">
                    <span
                      class="list-group-item my-align-left box-list-element"
                      v-for="item in withdrawalPsd"
                      :key="item.withdraw_id"
                    >
                      <small>
                        <i class="fa fa-calendar"></i>
                        <span v-if="item.ts_commit != null">{{
                          formatDateYYYYMMDDHHmmss(item.ts_commit)
                        }}</span
                        ><span v-else-if="item.ts_reserve != null">{{
                          formatDateYYYYMMDDHHmmss(item.ts_reserve)
                        }}</span>
                        <a-lang value="state"></a-lang>:
                        <b v-if="item.state == 'T'" style="color: #fdb200"
                          ><a-lang :value="'sent'"></a-lang
                        ></b>
                        <b v-else-if="item.state == 'P'" style="color: green"
                          ><a-lang :value="'accredited'"></a-lang
                        ></b>
                        <b v-else-if="item.state == 'R'" style="color: red"
                          ><a-lang :value="'rejected'"></a-lang
                        ></b>
                        <b v-else-if="item.state == 'A'" style="color: red"
                          ><a-lang :value="'canceled'"></a-lang
                        ></b>
                      </small>
                      <div
                        class="d-flex w-100 justify-content-between"
                        v-if="item.withdraw_id != null"
                      >
                        <b> <a-lang value="withdraw-id"> </a-lang>:</b
                        ><small>{{ item.withdraw_id }}</small>
                      </div>
                      <div
                        class="d-flex w-100 justify-content-between"
                        v-if="item.retiro_id != null"
                      >
                        <b> <a-lang value="insert-coupon1"> </a-lang>:</b
                        ><small>{{ item.retiro_id }}</small>
                      </div>
                      <div class="d-flex w-100 justify-content-between">
                        <b><a-lang value="amount"> </a-lang>:</b>
                        <small
                          :style="
                            item.amount > 0 ? 'color: green;' : 'color: red;'
                          "
                          ><span v-if="item.amount < 0">-</span>
                          <span v-else>+</span
                          >{{ formatterNumber(item.amount, true) }}
                          {{ currencyView(item.currency) }}
                        </small>
                      </div>
                      <div class="d-flex w-100 justify-content-between">
                        <b><a-lang value="cta-number"> </a-lang>:</b>
                        <small>
                          {{ item.cta_number }}
                        </small>
                      </div>
                      <div class="d-flex w-100 justify-content-between">
                        <b><a-lang value="account-type"> </a-lang>:</b>
                        <small>
                          {{ item.type }}
                        </small>
                      </div>
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div
          :class="
            isMonnetPayoutUser() && !isBanktransferUser() && !isPdsUser()
              ? 'tab-pane show active'
              : 'tab-pane show'
          "
          id="monnetWithdrawSub"
        >
          <accept-conditions
            :accepttextlist="[
              {
                text: getLocate('accept-transfer-data'),
                accept: false,
              },
            ]"
            :mandatoryacceptindexes="[0]"
            @accept="
              (value) => {
                changeAcceptConditions(value, 'monnet');
              }
            "
            :key="acceptConditionKey"
          ></accept-conditions>
          <div class="form-group" v-if="!loading && acceptConditions">
            <div class="row">
              <div class="col-12 col-loteria">
                <br />
                <p class="profile-input-label">
                  <b><a-lang value="cashable-credits"> </a-lang>: </b
                  ><span v-if="cashableCredits != null">{{
                    formatterNumber(cashableCredits, true)
                  }}</span>
                  <span v-if="currencyWithdrawal != null">{{
                    currencyWithdrawal
                  }}</span>
                </p>
                <br />
              </div>
            </div>
            <div class="row row-loteria">
              <div
                class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria"
              >
                <div class="card">
                  <div class="card-header">
                    <img
                      src="images/payment/monnet.png"
                      class="img-fluid"
                      alt="effect"
                      style="max-width: 250px"
                    />
                  </div>
                  <div class="card-body back-light">
                    <span class="profile-input-label" v-if="isMonnetPayoutUser">
                      <a-lang value="punto-suerte-message-1"> </a-lang>
                      <br />
                      <a-lang value="punto-suerte-message-8"> </a-lang>
                      <br />
                      <a-lang value="punto-suerte-message-14"> </a-lang>
                      <br /><br />
                    </span>
                    <br />
                    <div
                      class="form-group"
                      v-if="isMonnetPayoutUser"
                      style="margin-top: 10px"
                    >
                      <p
                        style="padding-bottom: 0px"
                        class="profile-input-label"
                      >
                        <a-lang value="bank"> </a-lang>
                      </p>
                      <select
                        class="form-control modal-custom-input"
                        style="color: 'black'"
                        id="bank"
                        v-model="bankId"
                      >
                        <option
                          v-for="b in monnetBanks"
                          :key="b"
                          :value="b.bankId"
                        >
                          {{ b.name }}
                        </option>
                      </select>
                    </div>
                    <div class="form-group" v-if="isMonnetPayoutUser">
                      <p
                        style="padding-bottom: 0px"
                        class="profile-input-label"
                      >
                        <a-lang value="account-type"> </a-lang>
                      </p>
                      <select
                        class="form-control modal-custom-input"
                        style="color: 'black'"
                        id="accountType"
                        v-model="monnetAccountType"
                      >
                        <option value="2">
                          {{ getLocate("ahorros") }}
                        </option>
                        <option value="1">
                          {{ getLocate("corriente") }}
                        </option>
                      </select>
                    </div>
                    <div class="form-group" v-if="isMonnetPayoutUser">
                      <p
                        style="padding-bottom: 0px"
                        class="profile-input-label"
                      >
                        <a-lang value="cta-number"> </a-lang>
                      </p>
                      <input
                        id="ctaNumber"
                        v-model="ctaNumber"
                        class="form-control modal-custom-input"
                        maxlength="20"
                        required
                      />
                    </div>
                    <div class="form-group" v-if="isMonnetPayoutUser">
                      <p
                        style="padding-bottom: 0px"
                        class="profile-input-label"
                      >
                        <a-lang value="withdraw-value"> </a-lang>
                      </p>
                      <input
                        id="amountWithdrawMonnet"
                        v-model="amountWithdrawMonnet"
                        class="form-control modal-custom-input"
                        :placeholder="
                          getLocate('amount') +
                          ' ' +
                          currencyView(store.state.currency) +
                          ' 0.00'
                        "
                        aria-label="Amount"
                        type="number"
                        step=".01"
                        required
                      />
                    </div>
                    <div v-if="!isMonnetPayoutUser">
                      <div class="alert alert-danger" role="alert">
                        <a-lang value="error-personaid-monnet"> </a-lang>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="button-center">
                  <button
                    class="profile-button"
                    @click="reserveWithdrawMonnet()"
                  >
                    <a-lang value="request-withdraw"> </a-lang>
                  </button>
                </div>
                <br />
              </div>

              <div
                class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-loteria"
                style="margin-top: 10px"
              >
                <div class="row">
                  <div class="col-6 col-loteria">
                    <div class="form-group">
                      <p class="profile-input-label">
                        <a-lang value="date-from"> </a-lang>
                      </p>
                      <input
                        name="withdrawalDateFrom"
                        v-model="withdrawalDateFrom"
                        id="withdrawalDateFrom"
                        class="form-control modal-custom-input"
                        type="date"
                        required
                        @change="getWithdrawMonnet"
                      />
                    </div>
                  </div>
                  <div class="col-6 col-loteria">
                    <div class="form-group">
                      <p class="profile-input-label">
                        <a-lang value="date-to"> </a-lang>
                      </p>
                      <input
                        name="withdrawalDateTo"
                        v-model="withdrawalDateTo"
                        id="withdrawalDateTo"
                        class="form-control modal-custom-input"
                        type="date"
                        required
                        @change="getWithdrawMonnet"
                      />
                    </div>
                  </div>
                </div>
                <div class="card">
                  <no-result
                    v-if="withdrawalMonnet && withdrawalMonnet.length == 0"
                  ></no-result>
                  <div class="list-group scroll-list">
                    <span
                      class="list-group-item my-align-left box-list-element"
                      v-for="item in withdrawalMonnet"
                      :key="item.withdraw_id"
                    >
                      <small>
                        <i class="fa fa-calendar"></i>
                        <span v-if="item.ts_commit != null">{{
                          formatDateYYYYMMDDHHmmss(item.ts_commit)
                        }}</span
                        ><span v-else-if="item.ts_reserve != null">{{
                          formatDateYYYYMMDDHHmmss(item.ts_reserve)
                        }}</span>
                        <a-lang value="state"></a-lang>:
                        <b v-if="item.status == 'APPROVED'" style="color: green"
                          ><a-lang value="approved"></a-lang
                        ></b>
                        <b
                          v-else-if="item.status == 'PENDING'"
                          style="color: orange"
                          ><a-lang value="pending"></a-lang
                        ></b>
                        <b
                          v-else-if="item.status == 'REJECTED'"
                          style="color: red"
                          ><a-lang value="rejected"></a-lang
                        ></b>
                      </small>
                      <div
                        class="d-flex w-100 justify-content-between"
                        v-if="item.withdraw_id != null"
                      >
                        <b> <a-lang value="withdraw-id"> </a-lang>:</b
                        ><small>{{ item.withdraw_id }}</small>
                      </div>
                      <div class="d-flex w-100 justify-content-between">
                        <b><a-lang value="amount"> </a-lang>:</b>
                        <small
                          :style="
                            item.amount > 0 ? 'color: green;' : 'color: red;'
                          "
                          ><span v-if="item.amount < 0">-</span>
                          <span v-else>+</span
                          >{{ formatterNumber(item.amount, true) }}
                          {{ currencyView(item.currency) }}
                        </small>
                      </div>
                      <div class="d-flex w-100 justify-content-between">
                        <b><a-lang value="cta-number"> </a-lang>:</b>
                        <small>
                          {{ item.cta_number }}
                        </small>
                      </div>
                      <div class="d-flex w-100 justify-content-between">
                        <b><a-lang value="account-type"> </a-lang>:</b>
                        <small v-if="item.cta_type == 1"
                          ><a-lang value="corriente"></a-lang
                        ></small>
                        <small v-else-if="item.cta_type == 2"
                          ><a-lang value="ahorros"></a-lang
                        ></small>
                      </div>
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div
      class="modal fade"
      id="confirmWithdrawModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="confirmWithdrawLabel"
      aria-hidden="true"
      style="top: 3vh"
    >
      <div class="modal-dialog" role="document">
        <div
          class="modal-content modal-custom-content back-light"
          style="min-height: 40vh"
        >
          <div class="modal-header">
            <button
              type="button"
              class="close loteria-text-black"
              @click="$('#confirmWithdrawModal').modal('hide')"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="container-fluid">
              <br />
              <div class="alert alert-danger" role="alert">
                <a-lang value="withdraw-confirm-1"></a-lang>
                {{ formatterNumber(feeFrom, true) }}
                {{ currencyView(store.state.currency) }}.
                <a-lang value="withdraw-confirm-2"></a-lang>
                {{ formatterNumber(feePerc) }}%.<br />
                <a-lang value="withdraw-confirm-3"></a-lang>:
                {{ formatterNumber(amountWithdrawPsd) }}
                {{ currencyView(store.state.currency) }}<br />
                <a-lang value="withdraw-confirm-4"></a-lang>:
                {{ formatterNumber(feeAmount) }}
                {{ currencyView(store.state.currency) }}<br />
                <a-lang value="withdraw-confirm-5"></a-lang>:
                {{ formatterNumber(amountWithdrawPsd - feeAmount) }}
                {{ currencyView(store.state.currency) }}
              </div>
              <br />
            </div>
          </div>
          <div class="modal-footer justify-content-center">
            <button class="profile-button" @click="confirmReserveWithdrawPsd()">
              <a-lang value="confirm"> </a-lang>
            </button>
            <button class="profile-button" @click="confirmReserveWithdrawPsd()">
              <a-lang value="confirm"> </a-lang>
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
      //tab withdrawal
      isPsdTransferFunds: false,
      isPsdPswError: false,
      isPsdPersonaId: false,
      amountWireTransfer: null,
      accountHolder: "",
      accountIban: "",
      withdrawalTransactions: [],
      amountWithdrawPsd: null,
      withdrawalPsd: [],
      feeAmount: 0,
      feePerc: 0,
      feeFrom: 0,
      feeTo: 0,
      withdrawalDateFrom: this.formatDateYYYYMMDD(this.addDays(new Date(), -1)),
      withdrawalDateTo: this.formatDateYYYYMMDD(new Date()),
      typeWithdraw: null,
      bankId: null,
      banks: [],
      accountType: "Ahorros",
      ctaNumber: null,
      withdrawalMonnet: [],
      amountWithdrawMonnet: null,
      firstname: null,
      lastname: null,
      email: null,
      documentType: 1,
      documentNumber: null,
      monnetBanks: [],
      monnetAccountType: 2,
      acceptConditions: false,
      acceptConditionKey: 0,

      //balances
      balances: [],
      currency: store.state.currency,
      cashableCredits: null,
      notCashableCredits: null,
      currencyWithdrawal: null,
      promotionalCredits: null,
      currencyPromotional: null,
      promotionalCreditsBet: null,
      currencyPromotionalBet: null,
      cash: null,
    };
  },
  mounted() {
    this.getWithdrawal();
  },
  methods: {
    //ottengo i balance per user id e session id
    getBalances() {
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http.post("balances", formData).then((response) => {
        let result = response.data;
        this.forceLogout(result.result);

        if (result.result == 0) {
          this.balances = result;
          this.balances.data.forEach((element) => {
            if (
              element.type != null &&
              element.type != "" &&
              element.type == "N"
            ) {
              this.notCashableCredits = element.amount;
              this.currency = element.currency;
            }
            if (
              element.type != null &&
              element.type != "" &&
              element.type == "C"
            ) {
              this.cashableCredits = element.amount;
              this.currencyWithdrawal = element.currency;
            }
            if (
              element.type != null &&
              element.type != "" &&
              element.type == "G"
            ) {
              this.promotionalCredits = element.amount;
              this.currencyPromotional = element.currency;
            }
            if (
              element.type != null &&
              element.type != "" &&
              element.type == "B" &&
              element.status == 1
            ) {
              this.promotionalCreditsBet = element.amount;
              this.currencyPromotionalBet = element.currency;
            } else {
              this.promotionalCreditsBet = 0;
              this.currencyPromotionalBet = element.currency;
            }
          });
          let value = this.cashableCredits + this.notCashableCredits;
          this.cash = this.formatterNumber(value, true);
        }
      });
    },
    getWithdrawal() {
      this.transactionFees();
      this.isMetadata = false;

      if (this.isPdsUser()) {
        this.checkPersonaId();
        this.getWithdrawPsd();
        this.resetWithdrawal("psd");
        this.activaTab("withdrawalPsdSub");
      } else if (this.isBanktransferUser()) {
        this.checkPersonaId();
        this.getBanks();
        this.getWithdrawPsd();
        this.resetWithdrawal("bank");
        this.activaTab("withdrawalBankSub");
      } else if (this.isMonnetPayoutUser()) {
        this.checkPersonaId();
        this.getBanksMonnet();
        this.getWithdrawMonnet();
        this.resetWithdrawal("monnet");
        this.activaTab("monnetWithdrawSub");
      }
    },
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
          this.setError(this.getLocate("login-7"));
        });
    },
    checkPersonaId() {
      this.$http
        .post("checkPersonaId")
        .then((response) => {
          let result = response.data;
          this.isPsdPersonaId = result.isPsdPersonaId;
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    getWithdrawTransactions() {
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("wiretransfer/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getBalances();
            this.withdrawalTransactions = result.withdrawals;
            this.withdrawalTransactions.sort(function (a, b) {
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
          this.loading = false;
        });
    },
    getWithdrawMonnet(noreset) {
      this.loading = true;
      if (
        !this.checkDateFilter(
          this.withdrawalDateTo,
          this.withdrawalDateFrom,
          noreset
        )
      )
        return;
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.withdrawalDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.withdrawalDateTo, 1))
      );
      this.$http
        .post("monnet/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getBalances();
            this.withdrawalMonnet = result.withdrawals;
            this.withdrawalMonnet.sort(function (a, b) {
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
          this.loading = false;
        });
    },
    getWithdrawPsd(noreset) {
      let action = "psd/list";
      if (this.typeWithdraw == "bank") action = "bank/list";

      if (
        !this.checkDateFilter(
          this.withdrawalDateTo,
          this.withdrawalDateFrom,
          noreset
        )
      )
        return;
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.withdrawalDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.withdrawalDateTo, 1))
      );
      this.$http
        .post("withdraw/" + action, formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getBalances();
            this.withdrawalPsd = result.withdrawals;
            this.withdrawalPsd.sort(function (a, b) {
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
          this.loading = false;
        });
    },

    wireTransfer() {
      this.resetMessages();
      //converto in centesimi il valore inserito
      var value = this.convertNumberToCent(this.amountWireTransfer);
      //valido controllando che il valore inserito non sia minore/uguale a zero
      if (value <= 0) {
        this.setError(this.getLocate("profile-1"));
        return;
      }

      if (this.amountWireTransferCent > this.cashableCredits) {
        this.setError(this.getLocate("insufficient-funds"));
        return;
      }

      //valido controllando che la currency sia stata correttamente caricata (viene inizializzata da getBalance: Non Cashable)
      if (
        this.currency == null ||
        this.currency == "" ||
        this.currency == undefined
      ) {
        this.setError(this.getLocate("login-7"));
        return;
      }

      if (
        this.accountHolder == null ||
        this.accountHolder == "" ||
        this.accountHolder == undefined
      ) {
        this.setError(this.getLocate("profile-4"));
        return;
      }

      if (
        this.accountIban == null ||
        this.accountIban == "" ||
        this.accountIban == undefined
      ) {
        this.setError(this.getLocate("profile-5"));
        return;
      }

      if (!this.isValidIBANNumber(this.accountIban)) {
        this.setError(this.getLocate("profile-6"));
        return;
      }

      //mando la richiesta
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("amount", value);
      formData.append("currency", this.currency);
      formData.append("accountholder", this.accountHolder);
      formData.append("accountiban", this.accountIban);
      this.$http
        .post("wiretransfer", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.setError(this.getLocate(result.messageKo));
          } else {
            this.setSuccess(this.getLocate("profile-7"));
            this.getWithdrawTransactions();
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    reserveWithdrawMonnet() {
      this.resetMessages();

      //valido controllando che la currency sia stata correttamente caricata (viene inizializzata da getBalance: Non Cashable)
      if (
        this.currency == null ||
        this.currency == "" ||
        this.currency == undefined
      ) {
        this.setError(this.getLocate("login-7"));
        return;
      }

      if (
        this.ctaNumber == null ||
        this.ctaNumber == "" ||
        this.ctaNumber == undefined ||
        (this.bankId == "010" && this.ctaNumber.length != 10) ||
        (this.bankId == "017" && this.ctaNumber.length > 10) ||
        !this.validateCtaNumber(this.ctaNumber) ||
        (this.bankId != "010" &&
          this.bankId == "017" &&
          (this.ctaNumber.length > 20 || this.ctaNumber.length < 7))
      ) {
        this.setError(this.getLocate("cta-error"));
        return;
      }

      //converto in centesimi il valore inserito
      var value = this.convertNumberToCent(this.amountWithdrawMonnet);
      //valido controllando che il valore inserito non sia minore/uguale a zero
      if (value <= 0 || value == null) {
        this.setError(this.getLocate("profile-1"));
        return;
      }

      if (this.amountWithdrawMonnet > this.cashableCredits) {
        this.setError(this.getLocate("insufficient-funds"));
        return;
      }

      //mando la richiesta
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("amount", value);
      formData.append("currency", this.currency);
      formData.append("bankcode", this.bankId);
      formData.append("accounttype", this.monnetAccountType);
      formData.append("accountnumber", this.ctaNumber);
      this.$http
        .post("monnet", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.setError(this.getLocate(result.messageKo));
          } else {
            this.setSuccess(this.getLocate("profile-7"));
            this.getWithdrawMonnet(true);
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    reserveWithdrawPsd() {
      this.resetMessages();
      let modal = document.getElementById("modal-body-scroll");
      if (modal) modal.scrollTop = 0;

      if (!this.amountWithdrawPsd) {
        this.setError(this.getLocate("profile-1"));
        return false;
      }

      if (
        this.typeWithdraw == "bank" &&
        (this.amountWithdrawPsd < 20 || this.amountWithdrawPsd > 2000)
      ) {
        this.setError(this.getLocate("profile-8"));
        return false;
      }

      if (
        this.typeWithdraw == "bank" &&
        !this.validateCtaNumber(this.ctaNumber)
      ) {
        this.setError(this.getLocate("cta-number-message"));
        return false;
      }

      //converto in centesimi il valore inserito
      let value = this.convertNumberToCent(this.amountWithdrawPsd);
      //valido controllando che il valore inserito non sia minore/uguale a zero
      if (value <= 0) {
        this.setError(this.getLocate("profile-1"));
        return;
      }

      if (value > this.cashableCredits) {
        this.setError(this.getLocate("insufficient-funds"));
        return;
      }

      //valido controllando che la currency sia stata correttamente caricata (viene inizializzata da getBalance: Non Cashable)
      if (
        this.currency == null ||
        this.currency == "" ||
        this.currency == undefined
      ) {
        this.setError(this.getLocate("login-7"));
        return;
      }
      let total = this.convertNumberToCent(this.amountWithdrawPsd);
      if (
        (this.feeFrom <= total && total < this.feeTo) ||
        (this.feeFrom <= total && this.feeTo == 0)
      ) {
        this.calculateFeeAmount();
        $("#confirmWithdrawModal").modal({
          backdrop: "static",
          keyboard: false,
        });
      } else this.confirmReserveWithdrawPsd();
    },
    confirmReserveWithdrawPsd() {
      let action = "psd/reserve";
      if (this.typeWithdraw == "bank") action = "bank/reserve";

      $("#confirmWithdrawModal").modal("hide");
      //mando la richiesta
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append(
        "amount",
        this.convertNumberToCent(this.amountWithdrawPsd)
      );
      formData.append("currency", this.currency);
      if (this.typeWithdraw == "bank") {
        formData.append("bankId", this.bankId);
        formData.append("type", this.accountType);
        formData.append("ctaNumber", this.ctaNumber);
      }
      this.$http
        .post("withdraw/" + action, formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.setError(this.getLocate(result.messageKo));
          } else {
            this.setSuccess(
              (this.typeWithdraw == "bank"
                ? this.getLocate("profile-9")
                : this.getLocate("profile-7")) +
                (result.description == "success-first-withdraw"
                  ? ".<br><br>" + this.getLocate("first-withdraw")
                  : "")
            );
            this.getWithdrawPsd(true);
            this.amountWithdrawPsd = null;
            if (this.typeWithdraw == "bank") {
              this.ctaNumber = null;
              this.accountType = "Ahorros";
            }
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getBanks() {
      this.$http
        .post("bank/list")
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.banks = result.banks;
            this.bankId = this.banks[0].bankId;
          } else {
            this.setError(result.description);
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    getBanksMonnet() {
      this.$http
        .post("monnet/bank/list")
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.monnetBanks = result.banks;
            this.bankId = this.monnetBanks[0].bankId;
          } else {
            this.setError(result.description);
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    transactionFees() {
      this.$http
        .post("transaction/fees")
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.setError(this.getLocate(result.messageKo));
          } else {
            if (result.fees && result.fees.length > 0) {
              this.feePerc = result.fees[0].amount;
              this.feeFrom = result.fees[0].from;
              this.feeTo = result.fees[0].to;
            }
          }
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    calculateFeeAmount() {
      let amountWithdrawPsdCent = this.convertNumberToCent(
        this.amountWithdrawPsd
      );
      // calculate amount fee
      this.feeAmount = ((this.feePerc / 100) * amountWithdrawPsdCent) / 100;
    },
    resetWithdrawal(type) {
      this.typeWithdraw = type;
      this.amountWithdrawPsd = null;
      this.withdrawalPsd = null;
    },
    checkProxypayReferenceId() {
      this.$http
        .post("checkProxypayReferenceId")
        .then((response) => {
          let result = response.data;
          this.proxypayReferenceId = result.proxypayReferenceId;
        })
        .catch((response) => {
          this.showError(response);
          this.setError(this.getLocate("login-7"));
        });
    },
    checkLimitBankWithdraw() {
      if (this.typeWithdraw == "bank") {
        let formData = new FormData();
        formData.append("usertoken", store.state.userId);
        formData.append("sessiontoken", store.state.sessionId);
        formData.append(
          "from",
          this.localDateToUtc(this.formatDateYYYYMMDD(new Date()))
        );
        formData.append(
          "to",
          this.localDateToUtc(
            this.addDays(this.formatDateYYYYMMDD(this.withdrawalDateTo), 1)
          )
        );
        this.$http
          .post("withdraw/bank/list", formData)
          .then((response) => {
            let result = response.data;
            this.forceLogout(result.result);
            if (result.result == 0) {
              if (result.withdrawals.length >= 2) {
                this.setError(this.getLocate("limit-bank-transfers"));
              } else {
                this.reserveWithdrawPsd();
              }
            }
          })
          .catch((response) => {
            this.showError(response);
          });
      } else {
        this.reserveWithdrawPsd();
      }
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
    resetMessages() {
      this.$emit("reset-messages");
    },
    checkDateFilter(date1, date2, notreset) {
      if (!notreset) this.resetMessages();

      if (!date1 || !date2) {
        this.setError(this.getLocate("check-date-4"));
        return false;
      }

      const MAX_DAYS_VIEW = 30;
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
    activaTab(tab) {
      $('a[href="#' + tab + '"]').tab("show");
      $('a[href="#' + tab + '"]').addClass("active");
      $("#" + tab).addClass("show active");
    },
    changeAcceptConditions(value, type) {
      this.acceptConditions = value;
      this.resetWithdrawal(type);
      if (type == "monnet") this.getWithdrawMonnet();
      else this.getWithdrawPsd();
    },
  },
};
</script>
