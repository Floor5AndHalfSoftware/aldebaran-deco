<template>
  <div
    class="modal fade"
    id="userProfile"
    tabindex="-1"
    role="dialog"
    aria-labelledby="userProfileLabel"
    aria-hidden="true"
  >
    <div
      class="modal-dialog modal-lg"
      role="document"
      style="max-width: 1024px"
    >
      <div class="modal-content back-dark">
        <!-- TABS -->
        <div class="modal-header">
          <ul class="nav nav-pills nav-principal profileUl scrollbar-style">
            <li class="nav-item">
              <a
                href="#profile"
                class="nav-link active"
                data-toggle="pill"
                @click="
                  resetMessages();
                  getUserInformation();
                "
                ><i :class="'fa fa-user'"></i>
                <a-lang value="userProfile"> </a-lang>
              </a>
            </li>
            <li class="nav-item">
              <a
                href="#bonuses"
                class="nav-link"
                data-toggle="pill"
                @click="
                  resetMessages();
                  getBonuses();
                "
                ><i :class="'fa fa-user-plus'"></i>
                <a-lang value="bonuses"> </a-lang>
              </a>
            </li>
            <li class="nav-item">
              <a
                href="#movements"
                class="nav-link"
                data-toggle="pill"
                @click="
                  resetMessages();
                  getTransactions();
                "
                ><i :class="'fa fa-money'"></i>
                <a-lang value="moviments"> </a-lang
              ></a>
            </li>
            <li class="nav-item" v-if="!isUserSelfexcluded()">
              <a
                @click="getDeposits()"
                href="#deposit"
                class="nav-link"
                data-toggle="pill"
              >
                <i :class="'fa fa-credit-card'"></i>
                <a-lang value="deposit"> </a-lang
              ></a>
            </li>
            <li class="nav-item" v-if="isPdsUser() || isBanktransferUser()">
              <a
                @click="getWithdrawal()"
                href="#withdrawal"
                class="nav-link"
                data-toggle="pill"
                ><i :class="'fa fa-bank'"></i>
                <a-lang value="withdrawal"> </a-lang
              ></a>
            </li>
            <li class="nav-item" v-if="!isUserSelfexcluded()">
              <a
                @click="
                  resetMessages();
                  getGameSessions();
                "
                href="#gamesHistory"
                class="nav-link"
                data-toggle="pill"
                ><i :class="'fa fa-gamepad'"></i>
                <a-lang value="games"> </a-lang
              ></a>
            </li>
            <li class="nav-item" v-if="!isUserSelfexcluded()">
              <a
                @click="
                  resetMessages();
                  getBets();
                "
                href="#bets"
                class="nav-link"
                data-toggle="pill"
                ><i :class="'fa fa-soccer-ball-o'"></i>
                <a-lang value="bets"> </a-lang
              ></a>
            </li>
            <li class="nav-item" v-if="!isUserSelfexcluded()">
              <a
                @click="
                  resetMessages();
                  getLottery();
                "
                href="#lottery"
                class="nav-link"
                data-toggle="pill"
                ><i :class="'fa fa-star'"></i> <a-lang value="lottery"> </a-lang
              ></a>
            </li>
            <li class="nav-item" v-if="!isUserSelfexcluded()">
              <a
                @click="
                  resetMessages();
                  getUserDetail();
                  getSettings();
                "
                href="#settings"
                class="nav-link"
                data-toggle="pill"
                ><i :class="'fa fa-cog'"></i> <a-lang value="settings"> </a-lang
              ></a>
            </li>
          </ul>

          <button
            type="button"
            class="close text-white"
            data-dismiss="modal"
            aria-label="Close"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <!-- TABS END -->

        <div id="modal-body-scroll" class="modal-body scroll-tab-content">
          <a-loading :show="loading"></a-loading>
          <div class="tab-content">
            <div class="alert alert-success" role="alert" v-show="success">
              <span v-html="successMessage"></span>
              <button
                type="button"
                class="close"
                aria-label="Close"
                @click="success = false"
              >
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="alert alert-danger" role="alert" v-show="error">
              <span>{{ errorMessage }}</span>
              <button
                type="button"
                class="close"
                aria-label="Close"
                @click="error = false"
              >
                <span aria-hidden="true">&times;</span>
              </button>
            </div>

            <!-- USER INFO -->
            <div class="tab-pane fade show active" id="profile">
              <div class="row">
                <div class="col-12">
                  <div class="card mb-3">
                    <div class="card-body" v-if="pageView == 'userInfo'">
                      <div class="row" v-if="isUserSelfexcluded()">
                        <div class="col-12">
                          <h6 class="mb-0 text-danger">
                            <a-lang value="user-selfexcluded"> </a-lang>
                          </h6>
                        </div>
                      </div>
                      <hr v-if="isUserSelfexcluded()" />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">Email</h6>
                        </div>
                        <div class="col-sm-8 text-secondary">
                          <i class="fa fa-envelope" aria-hidden="true"></i
                          >&nbsp;
                          {{ store.state.email }}
                        </div>
                        <div class="col-sm-1">
                          <i
                            :class="'fa fa-pencil-square-o'"
                            style="cursor: pointer"
                            @click="$('#changeEmail').modal('show')"
                          ></i>
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="document-number"></a-lang>
                          </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                          <i class="fa fa-file-text" aria-hidden="true"></i
                          >&nbsp;
                          {{ userDetail.document_id }}
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="name"> </a-lang>
                          </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                          <i class="fa fa-user" aria-hidden="true"></i>&nbsp;
                          {{ store.state.firstname }} {{ store.state.lastname }}
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="city"> </a-lang>
                          </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                          <i class="fa fa-home" aria-hidden="true"></i>&nbsp;
                          {{ userDetail.city }}
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="birth-date"> </a-lang>
                          </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                          <i class="fa fa-birthday-cake" aria-hidden="true"></i
                          >&nbsp;
                          {{ formatDateD(userDetail.birth_date) }}
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="mobile"> </a-lang>
                          </h6>
                        </div>
                        <div class="col-sm-8 text-secondary">
                          <i class="fa fa-phone" aria-hidden="true"></i>&nbsp;
                          {{ userDetail.mobile }}
                        </div>
                        <div class="col-sm-1">
                          <i
                            :class="'fa fa-pencil-square-o'"
                            style="cursor: pointer"
                            @click="$('#changePhone').modal('show')"
                          ></i>
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="cash"> </a-lang>
                          </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                          <i class="fa fa-money" aria-hidden="true"></i>&nbsp;
                          <span v-if="cash != null">{{ cash }}</span
                          ><span v-if="currency != null">
                            {{ currencyView(currency) }}</span
                          >
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="withdrawable"> </a-lang>
                          </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                          <i class="fa fa-money" aria-hidden="true"></i>&nbsp;
                          <span v-if="cashableCredits != null">{{
                            formatterNumber(cashableCredits, true)
                          }}</span
                          ><span v-if="currency != null">
                            {{ currencyView(currency) }}</span
                          >
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="promotional"> </a-lang>
                          </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                          <i class="fa fa-money" aria-hidden="true"></i>&nbsp;
                          <span v-if="promotionalCredits != null">{{
                            formatterNumber(promotionalCredits, true)
                          }}</span
                          ><span v-if="currencyPromotional != null">
                            {{ currencyView(currencyPromotional) }}</span
                          >
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="promotional-bet"> </a-lang>
                          </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                          <i class="fa fa-money" aria-hidden="true"></i>&nbsp;
                          <span v-if="promotionalCreditsBet != null">{{
                            formatterNumber(promotionalCreditsBet, true)
                          }}</span>
                          <span v-if="currencyPromotionalBet != null">
                            {{ currencyView(currencyPromotionalBet) }}
                          </span>
                          <span v-else>
                            {{ currencyView(store.state.currency) }}
                          </span>
                        </div>
                      </div>
                      <hr />
                      <div class="row">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <a-lang value="last-login"> </a-lang>
                          </h6>
                        </div>

                        <div class="col-sm-9 text-secondary">
                          <span
                            v-if="
                              store.state.lastlogin &&
                              store.state.lastlogin != 'null'
                            "
                          >
                            <i class="fa fa-tag" aria-hidden="true"></i>&nbsp;
                            {{
                              formatDateYYYYMMDDHHmmss(store.state.lastlogin)
                            }}</span
                          >
                        </div>
                      </div>
                      <hr />
                      <div class="row" style="text-align: center">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <button
                              class="btn btn-link-blue"
                              href="#"
                              @click="pageView = 'privacyPolicy'"
                            >
                              <i
                                class="fa fa-exclamation-triangle"
                                aria-hidden="true"
                              ></i
                              >&nbsp;
                              <a-lang value="privacyPolicyTitle"> </a-lang>
                            </button>
                          </h6>
                        </div>
                        <div class="col-sm-1"></div>
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <button
                              class="btn btn-link-blue"
                              href="#"
                              @click="pageView = 'agreementsTerms'"
                            >
                              <i
                                class="fa fa-exclamation-triangle"
                                aria-hidden="true"
                              ></i
                              >&nbsp;
                              <a-lang value="agreementsTermsTitle"> </a-lang>
                            </button>
                          </h6>
                        </div>
                      </div>
                      <div class="row" style="text-align: center">
                        <div class="col-sm-3">
                          <h6 class="mb-0">
                            <button
                              class="btn btn-link-blue"
                              href="#"
                              @click="CambiaPassword()"
                            >
                              <i
                                class="fa fa-user-secret"
                                aria-hidden="true"
                              ></i
                              >&nbsp;
                              <a-lang value="change-password"> </a-lang>
                            </button>
                          </h6>
                        </div>
                        <div class="col-sm-1"></div>
                        <div
                          class="col-sm-3"
                          v-if="
                            isSmarticoMissionUser() &&
                            document.getElementById(
                              '__smarticoPublicLabelKey'
                            ) &&
                            document.getElementById('__smarticoPublicLabelKey')
                              .value
                          "
                        >
                          <h6 class="mb-0">
                            <button
                              class="btn btn-link-blue"
                              href="#"
                              @click="_smartico.dp('dp:gf')"
                            >
                              <i class="fa fa-user-plus" aria-hidden="true"></i
                              >&nbsp;
                              <a-lang value="open-mission"> </a-lang>
                            </button>
                          </h6>
                        </div>
                      </div>
                      <hr />
                      <br />
                      <div
                        class="alert alert-success"
                        role="alert"
                        v-show="successCp"
                      >
                        <span v-html="successMessageCp"></span>
                        <button
                          type="button"
                          class="close"
                          aria-label="Close"
                          @click="successCp = false"
                        >
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div
                        class="alert alert-danger"
                        role="alert"
                        v-show="errorCp"
                      >
                        <span>{{ errorMessageCp }}</span>
                        <button
                          type="button"
                          class="close"
                          aria-label="Close"
                          @click="errorCp = false"
                        >
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div
                        class="modal fade"
                        id="changeEmail"
                        tabindex="-1"
                        role="dialog"
                        aria-labelledby="changeEmailDetails"
                        aria-hidden="true"
                      >
                        <div class="modal-dialog" role="document">
                          <div class="modal-content back-dark">
                            <div class="modal-header">
                              <h5 class="modal-title" id="exampleModalLabel">
                                <p>
                                  <a-lang value="change-email"> </a-lang>
                                </p>
                              </h5>
                              <button
                                type="button"
                                class="close text-white"
                                @click="
                                  $('#changeEmail').modal('hide');
                                  newEmail = '';
                                  alertEmailError.message = '';
                                "
                                aria-label="Close"
                              >
                                <span aria-hidden="true">&times;</span>
                              </button>
                            </div>
                            <div class="modal-body">
                              <a-alert :alert="alertEmailError"></a-alert>
                              <input
                                v-model="newEmail"
                                class="form-control com.aldebaran-input-text"
                                style="margin-top: 50%"
                                :placeholder="getLocate('email-insert-new')"
                              />
                              <button
                                style="margin-left: 35%"
                                type="button"
                                class="btn btn-link-blue"
                                @click="changeEmail()"
                              >
                                Change email
                              </button>
                            </div>
                            <div class="modal-footer"></div>
                          </div>
                        </div>
                      </div>
                      <div
                        class="modal fade"
                        id="changePhone"
                        tabindex="-1"
                        role="dialog"
                        aria-labelledby="changePhoneDetails"
                        aria-hidden="true"
                      >
                        <div class="modal-dialog" role="document">
                          <div class="modal-content back-dark">
                            <div class="modal-header">
                              <h5 class="modal-title" id="exampleModalLabel">
                                <p>
                                  <a-lang value="change-phone"> </a-lang>
                                </p>
                              </h5>
                              <button
                                type="button"
                                class="close text-white"
                                @click="
                                  $('#changePhone').modal('hide');
                                  newPhone = '';
                                  alertPhoneError.message = '';
                                "
                                aria-label="Close"
                              >
                                <span aria-hidden="true">&times;</span>
                              </button>
                            </div>
                            <div class="modal-body">
                              <a-alert :alert="alertPhoneError"></a-alert>
                              <input
                                v-model="newPhone"
                                class="form-control com.aldebaran-input-text"
                                style="margin-top: 50%"
                                :placeholder="getLocate('phone-insert-new')"
                              />
                              <button
                                style="margin-left: 35%"
                                type="button"
                                class="btn btn-link-blue"
                                @click="changePhone()"
                              >
                                Change phone
                              </button>
                            </div>
                            <div class="modal-footer"></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div
                      class="card-body darkColor"
                      v-else-if="pageView == 'agreementsTerms'"
                    >
                      <small>
                        <a href="#">
                          <i
                            style="color: grey"
                            class="fa fa-chevron-left"
                            @click="pageView = 'userInfo'"
                          ></i>
                        </a>
                      </small>
                      <br />
                      <br />
                      <div class="row" style="text-align: justify">
                        <cms-content
                          group="privacy policy"
                          name="Agreements terms"
                          category="agreements terms"
                        ></cms-content>
                      </div>
                    </div>
                    <div
                      class="card-body darkColor"
                      v-else-if="pageView == 'privacyPolicy'"
                    >
                      <small>
                        <a href="#">
                          <i
                            style="color: grey"
                            class="fa fa-chevron-left"
                            @click="pageView = 'userInfo'"
                          ></i>
                        </a>
                      </small>
                      <br />
                      <br />
                      <div class="row" style="text-align: justify">
                        <cms-content
                          group="privacy policy"
                          name="Privacy policy"
                          category="privacy policy"
                        ></cms-content>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- USER INFO END -->
            <!-- BONUSES -->
            <div class="tab-pane fade" id="bonuses">
              <a-alert
                v-if="bonuses && bonuses.length == 0"
                :alert="{
                  type: 'alert-info',
                  message: this.getLocate('no-result-found'),
                }"
              ></a-alert>
              <div id="bonuses" class="list-group scroll-list" v-if="!loading">
                <span
                  class="list-group-item my-align-left"
                  v-for="(item, index) in bonuses"
                  :key="item.promoid"
                >
                  <small>
                    <i class="fa fa-calendar" v-if="item && item.grantts"></i>
                    <span v-if="item && item.grantts">
                      <a-lang value="grant-ts"></a-lang>
                      {{ formatDateYYYYMMDDHHmmss(item.grantts) }}
                    </span>
                    -
                    <a-lang value="goal"> </a-lang>:
                    <b
                      :style="
                        item.percentage >= 100 ? 'color: green;' : 'color: red;'
                      "
                      >{{ formatterNumber(item.percentage) }}%</b
                    >
                    <span v-if="index == 0" style="color: green"
                      ><b>(<a-lang value="active"> </a-lang>)</b></span
                    >
                    <span v-else style="color: orange"
                      ><b>(<a-lang value="bet Pending"> </a-lang>)</b></span
                    >
                  </small>
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">
                      {{ item.name }}
                    </h5>
                    <div v-if="checkWithdrawalBonus(item)">
                      <button
                        class="btn btn-link-blue"
                        @click="redeemBonus(item.promoid)"
                      >
                        <a-lang value="withdrawal"> </a-lang>
                      </button>
                    </div>
                    <div v-else-if="checkCloseManuallyBonus(item, index)">
                      <button
                        class="btn btn-link-blue"
                        @click="confirmBonusClose(item.promoid)"
                      >
                        <a-lang value="delete-bonus"> </a-lang>
                      </button>
                    </div>
                    <div v-if="item.withdrawts">
                      <b style="color: green">
                        <a-lang value="redeemed"> </a-lang>
                      </b>
                    </div>
                  </div>
                  <small>
                    <span>
                      <a-lang value="bonus-value"> </a-lang>
                    </span>
                    <span
                      ><b>
                        {{ formatterNumber(item.amount, true) }}
                        {{ currencyView(item.currency) }}
                      </b>
                    </span>
                  </small>
                  <br />
                  <small>
                    <span>
                      <a-lang value="actual-bonus-balance"> </a-lang>
                    </span>
                    <b>
                      <span v-if="index == 0" :style="'color: green;'">
                        {{ formatterNumber(item.promobalance, true) }}
                        {{ currencyView(item.currency) }}
                      </span>
                      <span v-else>
                        {{ formatterNumber(0, true) }}
                        {{ currencyView(item.currency) }}
                      </span>
                    </b>
                  </small>
                  <small v-if="item && item.expiredts">
                    <br />
                    <i class="fa fa-calendar"></i>
                    <span>
                      <a-lang value="expired-ts"> </a-lang>
                      {{ formatDateYYYYMMDDHHmmss(item.expiredts) }}
                    </span>
                  </small>
                </span>
              </div>
            </div>
            <div
              class="modal fade"
              id="confirmCloseBonusModal"
              tabindex="-1"
              role="dialog"
              aria-labelledby="confirmCloseBonusLabel"
              aria-hidden="true"
              style="top: 3vh"
            >
              <div class="modal-dialog" role="document">
                <div class="modal-content back-dark" style="min-height: 40vh">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">
                      <p><a-lang value="warning"> </a-lang>!</p>
                    </h5>
                    <button
                      type="button"
                      class="close text-white"
                      @click="$('#confirmCloseBonusModal').modal('hide')"
                      aria-label="Close"
                    >
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <div class="container-fluid">
                      <a-alert
                        v-if="bonuses && bonuses.length > 0"
                        :alert="{
                          type: 'alert-warning',
                          message: this.getLocate('bonuses-alert'),
                        }"
                      ></a-alert>
                    </div>
                  </div>
                  <div class="modal-footer justify-content-center">
                    <div class="casino-btn">
                      <a
                        href="javascript:void(0)"
                        class="btn-4 yellow-btn"
                        @click="closeBonus(promoidBonusClose)"
                        ><a-lang value="confirm"> </a-lang
                      ></a>
                    </div>
                    <div class="casino-btn">
                      <a
                        href="javascript:void(0)"
                        class="btn-4 yellow-btn"
                        @click="$('#confirmCloseBonusModal').modal('hide')"
                        ><a-lang value="cancel"> </a-lang
                      ></a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- BONUSES END -->
            <!-- MOVEMENTS -->
            <div class="tab-pane fade" id="movements">
              <div class="row" v-if="!loading">
                <div class="col-sm-4 col-6">
                  <div class="form-group">
                    <p>
                      <a-lang value="date-from"> </a-lang>
                    </p>
                    <input
                      name="transactionDateFrom"
                      v-model="transactionDateFrom"
                      id="transactionDateFrom"
                      class="form-control"
                      type="date"
                      required
                      @change="getTransactions()"
                    />
                  </div>
                </div>
                <div class="col-sm-4 col-6">
                  <div class="form-group">
                    <p>
                      <a-lang value="date-to"> </a-lang>
                    </p>
                    <input
                      name="transactionDateTo"
                      v-model="transactionDateTo"
                      id="transactionDateTo"
                      class="form-control"
                      type="date"
                      required
                      @change="getTransactions()"
                    />
                  </div>
                </div>
                <div class="col-sm-4 col-12">
                  <div class="form-group">
                    <p>
                      <a-lang value="type"> </a-lang>
                    </p>
                    <select
                      class="form-control"
                      style="color: 'black'"
                      id="transactionType"
                      v-model="transactionType"
                      @change="getTransactions()"
                    >
                      <option value="ALL">
                        {{ getLocate("all") }}
                      </option>
                      <option value="WITHDRAWAL">
                        {{ getLocate("withdrawal") }}
                      </option>
                      <option value="DEPOSIT">
                        {{ getLocate("deposit") }}
                      </option>
                      <option value="GAMES">
                        {{ getLocate("games") }}
                      </option>
                      <option value="BETS">
                        {{ getLocate("bets") }}
                      </option>
                      <option value="BONUS">
                        {{ getLocate("bonuses") }}
                      </option>
                      <option value="REFUND">
                        {{ getLocate("refund") }}
                      </option>
                      <option value="FEE">
                        {{ getLocate("fee") }}
                      </option>
                      <option value="LOTTERY">
                        {{ getLocate("lottery") }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
              <a-alert
                v-if="!loading && transactions && transactions.length == 0"
                :alert="{
                  type: 'alert-info',
                  message: this.getLocate('no-result-found'),
                }"
              ></a-alert>
              <div
                id="movements"
                class="list-group scroll-list"
                v-if="!loading"
              >
                <span
                  class="list-group-item my-align-left"
                  v-for="item in transactions"
                  :key="item.transaction_id"
                >
                  <small>
                    <i class="fa fa-calendar"></i>
                    <span v-if="item && item.ts_close">
                      {{ formatDateYYYYMMDDHHmmss(item.ts_close) }}
                    </span>
                    <span v-else-if="item && item.ts_open">
                      {{ formatDateYYYYMMDDHHmmss(item.ts_open) }}
                    </span>

                    <a-lang value="state"> </a-lang>:
                    <b
                      :style="
                        item.state == 'VOID'
                          ? 'color: red;'
                          : item.state == 'COMMIT'
                          ? 'color: green;'
                          : 'color: #fdb200;'
                      "
                      >{{ item.state }}</b
                    >
                  </small>
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">{{ item.description }}</h5>
                  </div>
                  <small v-if="isDescriptionRefund(item.description)"
                    ><b><a-lang value="refund-message"></a-lang></b
                  ></small>
                  <br v-if="isDescriptionRefund(item.description)" />
                  <small
                    ><b>{{ item.type }}</b></small
                  >
                  <small>
                    <span
                      :style="
                        item.type == 'DEPOSIT'
                          ? 'color: green;'
                          : item.type == 'WITHDRAWAL'
                          ? 'color: red;'
                          : ''
                      "
                      ><span v-if="item.type == 'WITHDRAWAL'">-</span>
                      <span v-else-if="item.type == 'DEPOSIT'">+</span
                      >{{ formatterNumber(item.amount, true) }}
                      {{ currencyView(item.currency) }}</span
                    >
                    <span
                      v-if="
                        item.balance_type == 'G' || item.balance_type == 'B'
                      "
                      >(<a-lang value="promo"> </a-lang>)</span
                    >
                  </small>
                  <br />
                  <small
                    ><b><a-lang value="balance"> </a-lang></b
                  ></small>
                  <small>
                    <span
                      v-if="item.total_balance"
                      :style="
                        item.total_balance > 0 ? 'color: green;' : 'color: red;'
                      "
                      >{{ formatterNumber(item.total_balance, true) }}
                      {{ currencyView(item.currency) }}
                    </span>
                    <span v-else><a-lang value="unavailable"></a-lang></span>
                  </small>
                </span>
              </div>
            </div>
            <!-- MOVEMENTS END -->
            <!-- DEPOSITS -->
            <div class="tab-pane fade" id="deposit">
              <!-- SUB TABS DEPOSITS -->
              <ul
                id="depositsTabs"
                class="nav nav-pills profileUl scrollbar-style"
                v-show="showList"
              >
                <li class="nav-item">
                  <a href="#menu" class="nav-link" data-toggle="pill"
                    ><i :class="'fa fa-credit-card'"></i>
                    Menu'
                  </a>
                </li>
                <li class="nav-item">
                  <a
                    href="#nuvei"
                    class="nav-link"
                    data-toggle="pill"
                    @click="
                      resetMessages();
                      getDepositNuveiTransactions();
                    "
                    ><i :class="'fa fa-credit-card'"></i>
                    Nuvei
                  </a>
                </li>
                <li class="nav-item" v-if="isPlacetopayUser()">
                  <a
                    href="#placetopay"
                    class="nav-link"
                    data-toggle="pill"
                    @click="
                      resetMessages();
                      getDepositPTPTransactions(true);
                      refreshDepositsOn();
                      isMetadata = false;
                    "
                    ><i :class="'fa fa-credit-card'"></i>
                    Placetopay
                  </a>
                </li>
                <li class="nav-item" v-if="isPaycashUser()">
                  <a
                    href="#paycash"
                    class="nav-link"
                    data-toggle="pill"
                    @click="
                      resetMessages();
                      urlPaycash = null;
                      getDepositPaycashTransactions();
                    "
                    ><i :class="'fa fa-credit-card'"></i>
                    Paycash
                  </a>
                </li>
                <li class="nav-item" v-if="isNicoUser()">
                  <a
                    href="#nico"
                    class="nav-link"
                    data-toggle="pill"
                    @click="
                      resetMessages();
                      getDepositNicoTransactions();
                    "
                    ><i :class="'fa fa-credit-card'"></i>
                    N1co
                  </a>
                </li>
                <li class="nav-item">
                  <a
                    href="#payphone"
                    class="nav-link"
                    data-toggle="pill"
                    @click="resetMessages()"
                    ><i :class="'fa fa-credit-card'"></i>
                    Payphone
                  </a>
                </li>
              </ul>
              <button
                type="button"
                class="button-previous-profile"
                @click="
                  error = false;
                  errorMessage = '';
                  activaTab('menu');
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
              <div class="tab-content">
                <!--MENU-->
                <div class="tab-pane" id="menu">
                  <money-transfers
                    @nuveitab="
                      resetMessages();
                      depositNuveiType = 'cc_card';
                      depositType = '';
                      activaTab('nuvei');
                      getDepositNuveiTransactions();
                    "
                    @trasferenciabancaria="
                      resetMessages();
                      depositNuveiType = 'apmgw_SafetyPay_Online';
                      depositType = 'trasferencia-bancaria';
                      activaTab('nuvei');
                      getDepositNuveiTransactions();
                    "
                    @efectivo="
                      resetMessages();
                      depositNuveiType = 'apmgw_SafetyPay_Cash';
                      depositType = 'efectivo';
                      activaTab('nuvei');
                      getDepositNuveiTransactions();
                    "
                    @comerciosafiliados="
                      resetMessages();
                      depositNuveiType = 'apmgw_SafetyPay_Cash';
                      depositType = 'comercios-afiliados';
                      activaTab('nuvei');
                      getDepositNuveiTransactions();
                    "
                    @placetopaytab="
                      resetMessages();
                      getDepositPTPTransactions(true);
                      refreshDepositsOn();
                      isMetadata = false;
                      depositType = '';
                      activaTab('placetopay');
                    "
                    @payphone="
                      resetMessages();
                      depositType = 'payphone';
                      resetMessages();
                      getDepositPayphoneTransactions();
                      activaTab('payphone');
                    "
                  ></money-transfers>
                </div>
                <!-- NUVEI -->
                <div class="tab-pane" id="nuvei">
                  <div v-if="!loading">
                    <div class="row">
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div
                          class="card"
                          v-if="depositType == 'trasferencia-bancaria'"
                        >
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
                          <div class="card-body back-dark">
                            <div class="form-group">
                              <p style="padding-bottom: 0px">
                                <a-lang value="insert-rechange"> </a-lang>:
                              </p>
                              <br />
                              <input
                                v-model="amount"
                                class="form-control com.aldebaran-input-text"
                                :placeholder="getLocate('insert-rechange1')"
                                aria-label="Amount nuvei"
                                type="number"
                                step=".01"
                                required
                              />
                            </div>
                            <div class="text-white" style="text-align: justify">
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
                          <div class="card-body back-dark">
                            <div class="form-group">
                              <p style="padding-bottom: 0px">
                                <a-lang value="insert-rechange"> </a-lang>:
                              </p>
                              <br />
                              <input
                                v-model="amount"
                                class="form-control com.aldebaran-input-text"
                                :placeholder="getLocate('insert-rechange1')"
                                aria-label="Amount nuvei"
                                type="number"
                                step=".01"
                                required
                              />
                            </div>
                            <div
                              class="text-white"
                              style="text-align: justify; margin-left: 12px"
                            >
                              <a-lang value="insert-rechange-efectivo"></a-lang>
                            </div>
                            <br />
                            <br />
                            <div class="text-white" style="text-align: justify">
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
                                  style="
                                    padding-bottom: 10px;
                                    margin-top: 10px;
                                    height: 80px;
                                    margin-left: 75px;
                                  "
                                />
                              </div>
                            </div>
                          </div>
                          <div class="card-body back-dark">
                            <div class="form-group">
                              <p style="padding-bottom: 0px">
                                <a-lang value="insert-rechange"> </a-lang>:
                              </p>
                              <br />
                              <input
                                v-model="amount"
                                class="form-control com.aldebaran-input-text"
                                :placeholder="getLocate('insert-rechange1')"
                                aria-label="Amount nuvei"
                                type="number"
                                step=".01"
                                required
                              />
                            </div>
                            <div
                              class="text-white"
                              style="text-align: justify; margin-left: 12px"
                            >
                              <a-lang value="insert-rechange-efectivo"></a-lang>
                            </div>
                            <br />
                            <br />
                            <div class="text-white" style="text-align: justify">
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
                                  src="images/tarjete.png"
                                  class="img-fluid"
                                  alt="effect"
                                  style="padding-bottom: 10px"
                                />
                              </div>
                            </div>
                          </div>
                          <div class="card-body back-dark">
                            <!-- <div class="form-group" v-if="isSafetypayUser()">
                              <p style="padding-bottom: 0px">
                                <a-lang value="payment-method"> </a-lang>
                              </p>
                              <select
                                class="form-control com.aldebaran-select"
                                style="color: 'black'"
                                id="depositNuveiType"
                                v-model="depositNuveiType"
                              >
                                <option value="cc_card">
                                  {{ getLocate("cc_card") }}
                                </option>
                                <option value="apmgw_SafetyPay">
                                  {{ getLocate("apmgw_SafetyPay") }}
                                </option>
                              </select>
                            </div>-->
                            <div class="form-group">
                              <p style="padding-bottom: 0px">
                                <a-lang value="insert-rechange"> </a-lang>:
                              </p>
                              <br />
                              <input
                                v-model="amount"
                                class="form-control com.aldebaran-input-text"
                                :placeholder="getLocate('insert-rechange1')"
                                aria-label="Amount nuvei"
                                type="number"
                                step=".01"
                                required
                              />
                            </div>

                            <div class="text-white">
                              <a-lang value="insert-rechange2"></a-lang> Nuvei
                            </div>
                            <div class="text-white">
                              <a-lang value="insert-rechange3"></a-lang>
                            </div>
                            <div class="text-white">
                              <a-lang value="insert-rechange4"></a-lang> 5 USD
                            </div>
                            <div class="text-white">
                              <a-lang value="insert-rechange5"></a-lang> 3000
                              USD
                            </div>
                          </div>
                        </div>
                        <br />
                        <div class="casino-btn">
                          <a
                            href="javascript:void(0)"
                            class="btn-4 yellow-btn"
                            @click="depositNuvei()"
                            ><a-lang value="request-deposit"> </a-lang>
                          </a>
                        </div>
                        <br />
                      </div>
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="row">
                          <div class="col">
                            <div class="form-group">
                              <label for="depositState"
                                ><p>
                                  <a-lang value="deposit-state"> </a-lang></p
                              ></label>
                              <select
                                class="form-control"
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
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-from"> </a-lang>
                              </p>
                              <input
                                name="depositDateFrom"
                                v-model="depositDateFrom"
                                id="depositDateFrom"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositNuveiTransactions()"
                              />
                            </div>
                          </div>
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-to"> </a-lang>
                              </p>
                              <input
                                name="depositDateTo"
                                v-model="depositDateTo"
                                id="depositDateTo"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositNuveiTransactions()"
                              />
                            </div>
                          </div>
                        </div>

                        <div class="card">
                          <div class="card-header">
                            <span
                              ><a-lang value="deposit-transactions"> </a-lang
                            ></span>
                            <span v-if="isMetadata">
                              <a-lang value="transaction-detail"> </a-lang>
                            </span>
                          </div>
                          <a-alert
                            v-if="
                              depositNuveiTransactionsFiltered &&
                              depositNuveiTransactionsFiltered.length == 0
                            "
                            :alert="{
                              type: 'alert-info',
                              message: this.getLocate('no-result-found'),
                            }"
                          ></a-alert>

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
                                <b
                                  v-if="item.status == 'PENDING'"
                                  style="color: orange"
                                >
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
                              <div class="row">
                                <div class="col-12">
                                  <small>
                                    <a-lang value="deposit"> </a-lang>:
                                    <span
                                      :style="
                                        item.amount > 0
                                          ? 'color: green;'
                                          : 'color: red;'
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
                <!-- PLACETOPAY -->
                <div class="tab-pane" id="placetopay">
                  <div v-if="!loading">
                    <div class="row">
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="card">
                          <div
                            class="card-header"
                            style="padding-top: 0px; padding-bottom: 0px"
                          >
                            <div class="row align-items-center">
                              <div class="col">
                                <a
                                  href="https://www.placetopay.com"
                                  target="_BLANK"
                                  ><img
                                    src="images/tarjete-place-to-pay.png"
                                    class="img-fluid"
                                    alt="effect"
                                    style="padding-bottom: 10px"
                                /></a>
                              </div>
                            </div>
                          </div>
                          <div class="card-body back-dark subscribe">
                            <div class="form-group">
                              <h5 class="card-title text-white">
                                <a-lang value="insert-rechange"> </a-lang>:
                              </h5>
                              <input
                                v-model="amount"
                                class="form-control"
                                :placeholder="getLocate('insert-rechange1')"
                                aria-label="Amount"
                                type="number"
                                step=".01"
                                required
                              />
                              <br /><br />
                              <div class="text-white">
                                <a-lang value="insert-rechange2"></a-lang>
                                Placetopay
                              </div>
                              <div class="text-white">
                                <a-lang value="insert-rechange3"></a-lang>
                              </div>
                              <div class="text-white">
                                <a-lang value="insert-rechange4"></a-lang> 5 USD
                              </div>
                              <div class="text-white">
                                <a-lang value="insert-rechange5"></a-lang> 3000
                                USD
                              </div>
                            </div>
                          </div>
                        </div>
                        <br />
                        <div class="casino-btn">
                          <a
                            href="javascript:void(0)"
                            class="btn-4 yellow-btn"
                            @click="confirmDepositPTP()"
                            ><a-lang value="request-deposit"> </a-lang>
                          </a>
                        </div>
                        <br />
                      </div>
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="row">
                          <div class="col">
                            <div class="form-group">
                              <label for="depositState"
                                ><p>
                                  <a-lang value="deposit-state"> </a-lang></p
                              ></label>
                              <select
                                class="form-control"
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
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-from"> </a-lang>
                              </p>
                              <input
                                name="depositDateFrom"
                                v-model="depositDateFrom"
                                id="depositDateFrom"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositPTPTransactions(true)"
                              />
                            </div>
                          </div>
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-to"> </a-lang>
                              </p>
                              <input
                                name="depositDateTo"
                                v-model="depositDateTo"
                                id="depositDateTo"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositPTPTransactions(true)"
                              />
                            </div>
                          </div>
                        </div>
                        <div class="card">
                          <div class="card-header">
                            <span v-if="!isMetadata"
                              ><a-lang value="deposit-transactions"> </a-lang
                            ></span>
                            <span v-if="isMetadata">
                              <a-lang value="transaction-detail"> </a-lang>
                            </span>
                            <!--
                          <button
                            class="btn btn-link-blue text-right"
                            href="#"
                            @click="getDepositPTPTransactions()"
                          >
                            <i class="fa fa-refresh" aria-hidden="true"></i>
                          </button>
                          --></div>
                          <a-alert
                            v-if="
                              depositTransactionsFiltered &&
                              depositTransactionsFiltered.length == 0
                            "
                            :alert="{
                              type: 'alert-info',
                              message: this.getLocate('no-result-found'),
                            }"
                          ></a-alert>

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
                                <b
                                  v-if="item.status == 'PENDING'"
                                  style="color: orange"
                                >
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
                              <div class="row">
                                <div class="col-8">
                                  <small>
                                    <a-lang value="deposit"> </a-lang>:
                                    <span
                                      :style="
                                        item.amount > 0
                                          ? 'color: green;'
                                          : 'color: red;'
                                      "
                                      ><span v-if="item.amount < 0">-</span>
                                      <span v-else>+</span
                                      >{{ formatterNumber(item.amount, true) }}
                                      {{ currencyView(item.currency) }}
                                    </span>
                                  </small>
                                </div>
                                <div class="col offset-2">
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
                              </div>
                            </span>
                          </div>

                          <div class="list-group scroll-list" v-if="isMetadata">
                            <div>
                              <div class="col-1">
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
                              <div class="col-12" v-if="!detailPlacetopay">
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
                                    formatDateYYYYMMDDHHmmss(
                                      detailPlacetopay.status.date
                                    )
                                  }}</span
                                >
                                <a-lang value="state"> </a-lang>:
                                <span v-if="detailPlacetopay.status">
                                  <b
                                    v-if="
                                      detailPlacetopay.status.status ==
                                      'PENDING'
                                    "
                                    style="color: orange"
                                  >
                                    {{ getLocate("pending") }}
                                  </b>
                                  <b
                                    v-else-if="
                                      detailPlacetopay.status.status ==
                                      'REJECTED'
                                    "
                                    style="color: red"
                                  >
                                    {{ getLocate("rejected") }}
                                  </b>
                                  <b
                                    v-else-if="
                                      detailPlacetopay.status.status ==
                                      'APPROVED'
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
                                ><small>{{
                                  detailPlacetopay.info[0].reference
                                }}</small>
                              </div>
                              <div
                                class="d-flex w-100 justify-content-between"
                                v-if="
                                  detailPlacetopay.info &&
                                  detailPlacetopay.info.length > 0
                                "
                              >
                                <b><a-lang value="issue"> </a-lang>:</b
                                ><small>{{
                                  detailPlacetopay.info[0].issuerName
                                }}</small>
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
                <!-- PAYCASH -->
                <div class="tab-pane" id="paycash">
                  <div v-if="!loading">
                    <div class="row" v-if="!urlPaycash">
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="card">
                          <div
                            class="card-header"
                            style="padding-top: 0px; padding-bottom: 0px"
                          >
                            <div class="row align-items-center">
                              <div class="col">
                                <a
                                  href="https://www.paycashglobal.com/"
                                  target="_BLANK"
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
                          <div class="card-body back-dark">
                            <div class="form-group">
                              <p style="padding-bottom: 0px">
                                <a-lang value="insert-rechange"> </a-lang>:
                              </p>
                              <input
                                v-model="amount"
                                class="form-control"
                                :placeholder="getLocate('insert-rechange1')"
                                aria-label="Amount paycash"
                                type="number"
                                step=".01"
                                required
                              />
                            </div>
                          </div>
                        </div>
                        <br />
                        <div class="casino-btn">
                          <a
                            href="javascript:void(0)"
                            class="btn-4 yellow-btn"
                            @click="depositPaycash()"
                            ><a-lang value="request-deposit"> </a-lang>
                          </a>
                        </div>
                        <br />
                      </div>
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="row">
                          <div class="col">
                            <div class="form-group">
                              <label for="depositState"
                                ><p>
                                  <a-lang value="deposit-state"> </a-lang></p
                              ></label>
                              <select
                                class="form-control"
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
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-from"> </a-lang>
                              </p>
                              <input
                                name="depositDateFrom"
                                v-model="depositDateFrom"
                                id="depositDateFrom"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositPaycashTransactions()"
                              />
                            </div>
                          </div>
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-to"> </a-lang>
                              </p>
                              <input
                                name="depositDateTo"
                                v-model="depositDateTo"
                                id="depositDateTo"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositPaycashTransactions()"
                              />
                            </div>
                          </div>
                        </div>
                        <div class="card">
                          <div class="card-header">
                            <span
                              ><a-lang value="deposit-transactions"> </a-lang
                            ></span>
                            <span v-if="isMetadata">
                              <a-lang value="transaction-detail"> </a-lang>
                            </span>
                          </div>
                          <a-alert
                            v-if="
                              depositPaycashTransactionsFiltered &&
                              depositPaycashTransactionsFiltered.length == 0
                            "
                            :alert="{
                              type: 'alert-info',
                              message: this.getLocate('no-result-found'),
                            }"
                          ></a-alert>

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
                                <b
                                  v-if="item.status == 'PENDING'"
                                  style="color: orange"
                                >
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
                                <b
                                  ><a-lang value="reference-id"> </a-lang>
                                  Paycash:</b
                                ><small>{{ item.paycash_reference_id }}</small>
                              </div>
                              <div class="row">
                                <div class="col-8">
                                  <small>
                                    <a-lang value="deposit"> </a-lang>:
                                    <span
                                      :style="
                                        item.amount > 0
                                          ? 'color: green;'
                                          : 'color: red;'
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
                <div class="tab-pane" id="nico">
                  <div v-if="!loading">
                    <div class="row">
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
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
                          <div class="card-body back-dark">
                            <div class="form-group">
                              <p style="padding-bottom: 0px">
                                <a-lang value="insert-rechange"> </a-lang>:
                              </p>
                              <input
                                v-model="amount"
                                class="form-control"
                                :placeholder="getLocate('insert-rechange1')"
                                aria-label="Amount paycash"
                                type="number"
                                step=".01"
                                required
                              />
                            </div>
                          </div>
                        </div>
                        <br />
                        <div class="casino-btn">
                          <a
                            href="javascript:void(0)"
                            class="btn-4 yellow-btn"
                            @click="depositNico()"
                            ><a-lang value="request-deposit"> </a-lang>
                          </a>
                        </div>
                        <br />
                      </div>
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="row">
                          <div class="col">
                            <div class="form-group">
                              <label for="depositState"
                                ><p>
                                  <a-lang value="deposit-state"> </a-lang></p
                              ></label>
                              <select
                                class="form-control"
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
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-from"> </a-lang>
                              </p>
                              <input
                                name="depositDateFrom"
                                v-model="depositDateFrom"
                                id="depositDateFrom"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositNicoTransactions()"
                              />
                            </div>
                          </div>
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-to"> </a-lang>
                              </p>
                              <input
                                name="depositDateTo"
                                v-model="depositDateTo"
                                id="depositDateTo"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositNicoTransactions()"
                              />
                            </div>
                          </div>
                        </div>
                        <div class="card">
                          <div class="card-header">
                            <span
                              ><a-lang value="deposit-transactions"> </a-lang
                            ></span>
                            <span v-if="isMetadata">
                              <a-lang value="transaction-detail"> </a-lang>
                            </span>
                          </div>
                          <a-alert
                            v-if="
                              depositNicoTransactionsFiltered &&
                              depositNicoTransactionsFiltered.length == 0
                            "
                            :alert="{
                              type: 'alert-info',
                              message: this.getLocate('no-result-found'),
                            }"
                          ></a-alert>

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
                                <b
                                  ><a-lang value="reference-id"> </a-lang>
                                  Nico:</b
                                ><small>{{ item.order_id }}</small>
                              </div>
                              <div class="row">
                                <div class="col-8">
                                  <small>
                                    <a-lang value="deposit"> </a-lang>:
                                    <span
                                      :style="
                                        item.amount > 0
                                          ? 'color: green;'
                                          : 'color: red;'
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
                <!--PAYPHONE-->
                <div class="tab-pane" id="payphone">
                  <div v-if="!loading">
                    <div class="row">
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="card">
                          <div
                            class="card-header"
                            style="padding-top: 0px; padding-bottom: 0px"
                          >
                            <div class="row align-items-center">
                              <div class="col">
                                <img
                                  src="images/payphone.png"
                                  class="img-fluid center-img"
                                  alt="effect"
                                  style="max-width: 120px"
                                />
                              </div>
                            </div>
                          </div>
                          <div class="card-body back-dark">
                            <div class="form-group">
                              <p style="padding-bottom: 0px">
                                <a-lang value="insert-rechange"> </a-lang>:
                              </p>
                              <br />
                              <input
                                v-model="amount"
                                class="form-control com.aldebaran-input-text"
                                :placeholder="getLocate('insert-rechange1')"
                                aria-label="Amount payphone"
                                type="number"
                                step=".01"
                                required
                              />
                            </div>
                            <div class="text-white" style="text-align: justify">
                              <a-lang value="insert-rechange-payphone"></a-lang>
                              <br />
                              <br />
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
                              <span style="font-size: 12px">5.000 USD</span>
                            </div>
                          </div>
                        </div>

                        <div class="casino-btn" style="margin-top: 10px">
                          <a
                            href="javascript:void(0)"
                            class="btn-4 yellow-btn"
                            @click="depositPayphone()"
                            ><a-lang value="request-deposit"> </a-lang>
                          </a>
                        </div>
                        <br />
                      </div>
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="row">
                          <div class="col">
                            <div class="form-group">
                              <label for="depositState"
                                ><p>
                                  <a-lang value="deposit-state"> </a-lang></p
                              ></label>
                              <select
                                class="form-control"
                                style="color: 'black'"
                                id="depositState"
                                v-model="statePayphoneDeposit"
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
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-from"> </a-lang>
                              </p>
                              <input
                                name="depositDateFrom"
                                v-model="depositDateFrom"
                                id="depositDateFrom"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositPayphoneTransactions()"
                              />
                            </div>
                          </div>
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-to"> </a-lang>
                              </p>
                              <input
                                name="depositDateTo"
                                v-model="depositDateTo"
                                id="depositDateTo"
                                class="form-control"
                                type="date"
                                required
                                @change="getDepositPayphoneTransactions()"
                              />
                            </div>
                          </div>
                        </div>

                        <div class="card">
                          <div class="card-header">
                            <span
                              ><a-lang value="deposit-transactions"> </a-lang
                            ></span>
                            <span v-if="isMetadata">
                              <a-lang value="transaction-detail"> </a-lang>
                            </span>
                          </div>
                          <a-alert
                            v-if="
                              depositPayphoneFiltered &&
                              depositPayphoneFiltered.length == 0
                            "
                            :alert="{
                              type: 'alert-info',
                              message: this.getLocate('no-result-found'),
                            }"
                          ></a-alert>

                          <div id="deposits" class="list-group scroll-list">
                            <span
                              class="list-group-item my-align-left"
                              v-for="item in depositPayphoneFiltered"
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
                                <b
                                  v-if="item.status == 'PENDING'"
                                  style="color: orange"
                                >
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
                              <div class="row">
                                <div class="col-12">
                                  <small>
                                    <a-lang value="deposit"> </a-lang>:
                                    <span
                                      :style="
                                        item.amount > 0
                                          ? 'color: green;'
                                          : 'color: red;'
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
                  <div class="modal-content back-dark">
                    <div class="modal-header">
                      <button
                        type="button"
                        class="close text-white"
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
                          <a-lang value="request-pending-message"> </a-lang
                          >:<br />
                          <span v-if="lastDepositPending">{{
                            lastDepositPending
                          }}</span
                          ><br />
                          <a-lang value="request-pending-message1"> </a-lang>
                        </div>
                        <br />
                      </div>
                    </div>
                    <div class="modal-footer justify-content-center">
                      <div class="casino-btn">
                        <a
                          href="javascript:void(0)"
                          class="btn-4 yellow-btn"
                          @click="depositPTP()"
                          ><a-lang value="confirm-request-deposit"> </a-lang
                        ></a>
                      </div>
                      <div class="casino-btn">
                        <a
                          href="javascript:void(0)"
                          class="btn-4 yellow-btn"
                          @click="$('#confirmDepositModal').modal('hide')"
                          ><a-lang value="cancel-request-deposit"> </a-lang
                        ></a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- DEPOSITS END -->
            <!-- WITHDRAWAL -->
            <div class="tab-pane fade" id="withdrawal">
              <!-- SUB TABS WITHDRAWAL -->
              <ul
                id="withdrawalTabs"
                class="nav nav-pills profileUl"
                v-show="!loading"
              >
                <!--
                <li class="nav-item">
                  <a
                    href="#withdrawalSub"
                    class="nav-link active"
                    data-toggle="pill"
                    @click="
                      resetMessages();
                      getWithdrawTransactions();
                      isMetadata = false;
                    "
                    ><i :class="'fa fa-bank'"></i>
                    <a-lang value="withdrawal"> </a-lang>
                  </a>
                </li>
                -->
                <li class="nav-item" v-if="isPdsUser()">
                  <a
                    href="#withdrawalPsdSub"
                    class="nav-link active"
                    data-toggle="pill"
                    @click="
                      resetWithdrawal('psd');
                      resetMessages();
                      transactionFees();
                      getWithdrawPsd();
                      checkPersonaId();
                      isMetadata = false;
                    "
                    ><i :class="'fa fa-bank'"></i>
                    <a-lang value="efectivo"> </a-lang>
                  </a>
                </li>
                <li class="nav-item" v-if="isBanktransferUser()">
                  <a
                    href="#withdrawalBankSub"
                    class="nav-link"
                    data-toggle="pill"
                    @click="
                      resetWithdrawal('bank');
                      resetMessages();
                      getBanks();
                      transactionFees();
                      getWithdrawPsd();
                      checkPersonaId();
                      isMetadata = false;
                    "
                    ><i :class="'fa fa-bank'"></i>
                    <a-lang value="bank-transfer"> </a-lang>
                  </a>
                </li>
              </ul>

              <div
                v-if="!loading"
                style="
                  width: 100%;
                  height: 10px;
                  border-bottom: 1px solid #e9ecef;
                  margin-bottom: 10px;
                "
              ></div>
              <!-- SUBTABS END -->
              <div class="tab-content">
                <div class="tab-pane show active" id="withdrawalPsdSub">
                  <div class="form-group" v-if="!loading">
                    <div class="row">
                      <div class="col-12 col-xl-4 col-lg-4 col-md-4 col-sm-4">
                        <br />
                        <p>
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
                    <div class="row">
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="card">
                          <div class="card-header">
                            <img
                              src="images/retirada-efectivo.png"
                              class="img-fluid"
                              alt="effect"
                            />
                          </div>
                          <div class="card-body back-dark">
                            <h6
                              class="card-title text-white"
                              v-if="isPsdPersonaId"
                            >
                              <!--
                              <a-lang value="punto-suerte-message-1"> </a-lang
                              ><br />
                              <a-lang value="punto-suerte-message-2"> </a-lang
                              ><br />
                              <a-lang value="punto-suerte-message-3"> </a-lang
                              ><br />
                              <a-lang value="punto-suerte-message-4"> </a-lang
                              ><br />
                              <a-lang value="punto-suerte-message-5"> </a-lang
                              ><br />
                              <a-lang value="punto-suerte-message-6"> </a-lang>
                            -->
                              <br /><br />
                              <a-lang value="insert-wire-transfer"> </a-lang>
                            </h6>
                            <div class="form-group" v-if="isPsdPersonaId">
                              <input
                                id="amountWithdrawPsd"
                                v-model="amountWithdrawPsd"
                                class="form-control"
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
                              <h6 class="card-title text-white">
                                <a-lang value="punto-suerte-message-7">
                                </a-lang>
                                <br />
                                <ul
                                  style="
                                    list-style-type: circle;
                                    margin-left: 30px;
                                  "
                                >
                                  <li>
                                    <a-lang value="punto-suerte-message-10">
                                    </a-lang>
                                  </li>
                                  <li>
                                    <a-lang value="punto-suerte-message-11">
                                    </a-lang>
                                  </li>
                                  <li>
                                    <a-lang value="punto-suerte-message-12">
                                    </a-lang>
                                  </li>
                                </ul>
                                <br />
                                <a-lang value="punto-suerte-message-13">
                                </a-lang>
                                <br /><br />
                                <a-lang value="punto-suerte-message-15">
                                </a-lang>
                                <br />
                                <a-lang value="punto-suerte-message-16">
                                </a-lang>
                                <br />
                                <a-lang value="punto-suerte-message-17">
                                </a-lang>
                              </h6>
                            </div>
                            <div v-if="!isPsdPersonaId">
                              <div class="alert alert-danger" role="alert">
                                <a-lang value="error-personaid-psd"> </a-lang>
                              </div>
                            </div>
                          </div>
                        </div>
                        <br />
                        <div class="casino-btn">
                          <a
                            v-if="isPsdPersonaId"
                            href="javascript:void(0)"
                            class="btn-4 yellow-btn"
                            @click="reserveWithdrawPsd()"
                            ><a-lang value="request-withdraw"> </a-lang
                          ></a>
                        </div>
                        <br />
                      </div>

                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="row">
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-from"> </a-lang>
                              </p>
                              <input
                                name="withdrawalDateFrom"
                                v-model="withdrawalDateFrom"
                                id="withdrawalDateFrom"
                                class="form-control"
                                type="date"
                                required
                                @change="getWithdrawPsd"
                              />
                            </div>
                          </div>
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-to"> </a-lang>
                              </p>
                              <input
                                name="withdrawalDateTo"
                                v-model="withdrawalDateTo"
                                id="withdrawalDateTo"
                                class="form-control"
                                type="date"
                                required
                                @change="getWithdrawPsd"
                              />
                            </div>
                          </div>
                        </div>
                        <div class="card">
                          <div class="card-header">
                            <a-lang value="withdrawal-transactions"> </a-lang>
                          </div>

                          <a-alert
                            v-if="withdrawalPsd && withdrawalPsd.length == 0"
                            :alert="{
                              type: 'alert-info',
                              message: this.getLocate('no-result-found'),
                            }"
                          ></a-alert>
                          <div class="list-group scroll-list">
                            <span
                              class="list-group-item my-align-left"
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
                                <b
                                  v-if="item.ts_cancel != null"
                                  style="color: red"
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
                                <b>
                                  <a-lang value="insert-coupon1"> </a-lang>:</b
                                ><small>{{ item.retiro_id }}</small>
                              </div>
                              <div
                                class="d-flex w-100 justify-content-between"
                                v-if="item.cause != null"
                              >
                                <b><a-lang value="cause"> </a-lang>:</b
                                ><small>{{ item.cause }}</small>
                              </div>
                              <div class="row">
                                <div class="col-5">
                                  <b><a-lang value="amount"> </a-lang>:</b>
                                  <small
                                    :style="
                                      item.amount > 0
                                        ? 'color: green;'
                                        : 'color: red;'
                                    "
                                    ><span v-if="item.amount < 0">-</span>
                                    <span v-else>+</span
                                    >{{ formatterNumber(item.amount, true) }}
                                    {{ currencyView(item.currency) }}
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
                <div class="tab-pane show" id="withdrawalBankSub">
                  <div class="form-group" v-if="!loading">
                    <div class="row">
                      <div class="col-12 col-xl-4 col-lg-4 col-md-4 col-sm-4">
                        <br />
                        <p>
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
                    <div class="row">
                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="card">
                          <div class="card-header">
                            <img
                              src="images/retirada-bancaria.png"
                              class="img-fluid"
                              alt="effect"
                              style="max-width: 400px"
                            />
                          </div>
                          <div class="card-body back-dark">
                            <h6
                              class="card-title text-white copy-right"
                              v-if="isPsdPersonaId"
                            >
                              <a-lang value="punto-suerte-message-1"> </a-lang>
                              <br />
                              <a-lang value="punto-suerte-message-8"> </a-lang>
                              <br />
                              <a-lang value="punto-suerte-message-14"> </a-lang>
                              <br /><br />
                              <a-lang value="punto-suerte-message-9"> </a-lang>
                            </h6>

                            <div class="form-group" v-if="isPsdPersonaId">
                              <p style="padding-bottom: 0px">
                                <a-lang value="bank"> </a-lang>
                              </p>
                              <select
                                class="form-control"
                                style="color: 'black'"
                                id="bank"
                                v-model="bankId"
                              >
                                <option
                                  v-for="b in banks"
                                  :key="b"
                                  :value="b.bankId"
                                >
                                  {{ b.name }}
                                </option>
                              </select>
                            </div>
                            <div class="form-group" v-if="isPsdPersonaId">
                              <p style="padding-bottom: 0px">
                                <a-lang value="account-type"> </a-lang>
                              </p>
                              <select
                                class="form-control"
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
                              <p style="padding-bottom: 0px">
                                <a-lang value="cta-number"> </a-lang>
                              </p>
                              <input
                                id="ctaNumber"
                                v-model="ctaNumber"
                                class="form-control"
                                required
                              />
                            </div>
                            <div class="form-group" v-if="isPsdPersonaId">
                              <p style="padding-bottom: 0px">
                                <a-lang value="withdraw-value"> </a-lang>
                              </p>
                              <input
                                id="amountWithdrawBank"
                                v-model="amountWithdrawPsd"
                                class="form-control"
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
                        <br />
                        <div class="casino-btn">
                          <a
                            v-if="isPsdPersonaId"
                            href="javascript:void(0)"
                            class="btn-4 yellow-btn"
                            @click="checkLimitBankWithdraw()"
                            ><a-lang value="request-withdraw"> </a-lang
                          ></a>
                        </div>
                        <br />
                      </div>

                      <div class="col-12 col-xl-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="row">
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-from"> </a-lang>
                              </p>
                              <input
                                name="withdrawalDateFrom"
                                v-model="withdrawalDateFrom"
                                id="withdrawalDateFrom"
                                class="form-control"
                                type="date"
                                required
                                @change="getWithdrawPsd"
                              />
                            </div>
                          </div>
                          <div class="col-6">
                            <div class="form-group">
                              <p>
                                <a-lang value="date-to"> </a-lang>
                              </p>
                              <input
                                name="withdrawalDateTo"
                                v-model="withdrawalDateTo"
                                id="withdrawalDateTo"
                                class="form-control"
                                type="date"
                                required
                                @change="getWithdrawPsd"
                              />
                            </div>
                          </div>
                        </div>
                        <div class="card">
                          <div class="card-header">
                            <a-lang value="withdrawal-transactions"> </a-lang>
                          </div>

                          <a-alert
                            v-if="withdrawalPsd && withdrawalPsd.length == 0"
                            :alert="{
                              type: 'alert-info',
                              message: this.getLocate('no-result-found'),
                            }"
                          ></a-alert>
                          <div class="list-group scroll-list">
                            <span
                              class="list-group-item my-align-left"
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
                                <b
                                  v-if="item.state == 'T'"
                                  style="color: #fdb200"
                                  ><a-lang :value="'sent'"></a-lang
                                ></b>
                                <b
                                  v-else-if="item.state == 'P'"
                                  style="color: green"
                                  ><a-lang :value="'accredited'"></a-lang
                                ></b>
                                <b
                                  v-else-if="item.state == 'R'"
                                  style="color: red"
                                  ><a-lang :value="'rejected'"></a-lang
                                ></b>
                                <b
                                  v-else-if="item.state == 'A'"
                                  style="color: red"
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
                                <b>
                                  <a-lang value="insert-coupon1"> </a-lang>:</b
                                ><small>{{ item.retiro_id }}</small>
                              </div>
                              <div class="row">
                                <div class="col-5">
                                  <b><a-lang value="amount"> </a-lang>:</b>
                                  <small
                                    :style="
                                      item.amount > 0
                                        ? 'color: green;'
                                        : 'color: red;'
                                    "
                                    ><span v-if="item.amount < 0">-</span>
                                    <span v-else>+</span
                                    >{{ formatterNumber(item.amount, true) }}
                                    {{ currencyView(item.currency) }}
                                  </small>
                                </div>
                              </div>
                              <div class="row">
                                <div class="col">
                                  <b><a-lang value="cta-number"> </a-lang>:</b>
                                  <small>
                                    {{ item.cta_number }}
                                  </small>
                                </div>
                              </div>
                              <div class="row">
                                <div class="col">
                                  <b
                                    ><a-lang value="account-type"> </a-lang>:</b
                                  >
                                  <small>
                                    {{ item.type }}
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
                <div class="modal-content back-dark" style="min-height: 40vh">
                  <div class="modal-header">
                    <button
                      type="button"
                      class="close text-white"
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
                    <div class="casino-btn">
                      <a
                        href="javascript:void(0)"
                        class="btn-4 yellow-btn"
                        @click="confirmReserveWithdrawPsd()"
                        ><a-lang value="confirm"> </a-lang
                      ></a>
                    </div>
                    <div class="casino-btn">
                      <a
                        href="javascript:void(0)"
                        class="btn-4 yellow-btn"
                        @click="$('#confirmWithdrawModal').modal('hide')"
                        ><a-lang value="cancel"> </a-lang
                      ></a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- WITHDRAWAL END -->
            <!-- GAMES HISTORY -->
            <div class="tab-pane fade" id="gamesHistory">
              <div v-if="!loading">
                <div class="row">
                  <div class="col-6">
                    <div class="form-group">
                      <p>
                        <a-lang value="date-from"> </a-lang>
                      </p>
                      <input
                        name="sessionDateFrom"
                        v-model="sessionDateFrom"
                        id="sessionDateFrom"
                        class="form-control"
                        type="date"
                        required
                        @change="getGameSessions()"
                      />
                    </div>
                  </div>
                  <div class="col-6">
                    <div class="form-group">
                      <p>
                        <a-lang value="date-to"> </a-lang>
                      </p>
                      <input
                        name="sessionDateTo"
                        v-model="sessionDateTo"
                        id="sessionDateTo"
                        class="form-control"
                        type="date"
                        required
                        @change="getGameSessions()"
                      />
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-4 col-6">
                    <div class="form-group">
                      <label for="sessionState"
                        ><p>
                          <a-lang value="session-state"> </a-lang></p
                      ></label>
                      <select
                        class="form-control"
                        style="color: 'black'"
                        id="sessionState"
                        v-model="state"
                      >
                        <option value="ALL">{{ this.getLocate("all") }}</option>
                        <option value="OPEN">
                          {{ this.getLocate("open") }}
                        </option>
                        <option value="CLOSE">
                          {{ this.getLocate("close") }}
                        </option>
                      </select>
                    </div>
                  </div>
                  <div class="col-sm-4 col-6">
                    <div class="form-group">
                      <label for="sessionType"
                        ><p>
                          <a-lang value="type"> </a-lang></p
                      ></label>
                      <select
                        class="form-control"
                        style="color: 'black'"
                        id="sessionType"
                        v-model="type"
                      >
                        <option value="ALL">{{ this.getLocate("all") }}</option>
                        <option value="WIN">{{ this.getLocate("win") }}</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-sm-4 col-12">
                    <div class="form-group">
                      <label for="gamesType"
                        ><p>
                          <a-lang value="game-type"> </a-lang></p
                      ></label>
                      <select
                        class="form-control"
                        style="color: 'black'"
                        id="gamesType"
                        v-model="gameType"
                      >
                        <option value="ALL">{{ this.getLocate("all") }}</option>
                        <option v-for="gc in gameCategories" :key="gc">
                          {{ gc }}
                        </option>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-12">
                    <div class="card">
                      <div class="card-header">
                        <a-lang value="game-sessions"> </a-lang>
                      </div>
                      <a-alert
                        v-if="
                          gameSessionsFiltered &&
                          gameSessionsFiltered.length == 0
                        "
                        :alert="{
                          type: 'alert-info',
                          message: this.getLocate('no-result-found'),
                        }"
                      ></a-alert>

                      <div id="session" class="list-group scroll-list">
                        <a
                          data-toggle="modal"
                          data-target="#sessionDetails"
                          href="#"
                          class="list-group-item list-group-item-action my-align-left"
                          v-for="item in gameSessionsFiltered"
                          :key="item.session_id"
                          @click="getSessionDetails(item.session_id)"
                        >
                          <small>
                            <i class="fa fa-calendar"></i>
                            <span v-if="item.ts_open != null">
                              {{ formatDateYYYYMMDDHHmmss(item.ts_open) }}
                            </span>
                            <a-lang value="state"></a-lang>:
                            <b v-if="item.state == 'OPEN'" style="color: green"
                              >OPEN</b
                            >
                            <b
                              v-else-if="item.state == 'CLOSED'"
                              style="color: red"
                              >CLOSE</b
                            >
                            <b
                              v-else-if="item.state == 'ERROR'"
                              style="color: #fdb200"
                              >ERROR</b
                            >
                          </small>
                          <div>
                            <button
                              class="btn btn-link-blue"
                              v-if="item.state == 'OPEN'"
                              @click.stop=""
                              @click="closeSession(item.session_id)"
                            >
                              <a-lang value="close-session"> </a-lang>
                            </button>
                          </div>
                          <small v-if="item.ts_close != null">
                            <i class="fa fa-calendar-times-o"></i>
                            {{ formatDateYYYYMMDDHHmmss(item.ts_close) }}
                          </small>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.session_id != null"
                          >
                            <b><a-lang value="session-id"> </a-lang>:</b
                            ><small>{{ item.session_id }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.game != null"
                          >
                            <b><a-lang value="game"> </a-lang>:</b
                            ><small>{{ item.game }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.type != null"
                          >
                            <b><a-lang value="type"> </a-lang>:</b
                            ><small>{{ item.type }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.rounds != null"
                          >
                            <b><a-lang value="rounds"> </a-lang>:</b
                            ><small>{{ item.rounds }}</small>
                          </div>
                          <div class="d-flex w-100 justify-content-between">
                            <b><a-lang value="balance"> </a-lang>:</b
                            ><small
                              >{{ formatterNumber(item.balance, true) }}
                              {{ currencyView(item.currency) }}</small
                            >
                          </div>
                          <div class="d-flex w-100 justify-content-between">
                            <b><a-lang value="bet"> </a-lang>:</b
                            ><small
                              >{{ formatterNumber(item.bet, true) }}
                              {{ currencyView(item.currency) }}</small
                            >
                          </div>
                          <div class="d-flex w-100 justify-content-between">
                            <b><a-lang value="win"> </a-lang>:</b
                            ><small
                              >{{ formatterNumber(item.win, true) }}
                              {{ currencyView(item.currency) }}</small
                            >
                          </div>
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div
                class="modal fade"
                id="sessionDetails"
                tabindex="-1"
                role="dialog"
                aria-labelledby="sessionDetailsLabel"
                aria-hidden="true"
              >
                <div class="modal-dialog" role="document">
                  <div class="modal-content back-dark">
                    <div class="modal-header">
                      <h5 class="modal-title" id="exampleModalLabel">
                        <p>
                          <a-lang value="session-detail"> </a-lang>
                        </p>
                      </h5>
                      <button
                        type="button"
                        class="close text-white"
                        @click="$('#sessionDetails').modal('hide')"
                        aria-label="Close"
                      >
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">
                      <a-alert
                        v-if="sessionDetails && sessionDetails.length == 0"
                        :alert="{
                          type: 'alert-info',
                          message: this.getLocate('no-result-found'),
                        }"
                      ></a-alert>
                      <div id="session-detail" class="list-group scroll-list">
                        <a
                          data-toggle="modal"
                          data-target="#sessionDetails"
                          href="#"
                          class="list-group-item list-group-item-action my-align-left"
                          v-for="item in sessionDetails"
                          :key="item.round"
                        >
                          <small>
                            <i class="fa fa-calendar"></i>
                            <span v-if="item.ts != null">{{
                              formatDateYYYYMMDDHHmmss(item.ts)
                            }}</span>
                          </small>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.round != null"
                          >
                            <b><a-lang value="round"> </a-lang>:</b
                            ><small>{{ item.round }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.transaction != null"
                          >
                            <b><a-lang value="transaction"> </a-lang>:</b
                            ><small>{{ item.transaction }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.type != null"
                          >
                            <b><a-lang value="type"> </a-lang>:</b
                            ><small>{{ item.type }}</small>
                          </div>
                          <div class="d-flex w-100 justify-content-between">
                            <b><a-lang value="amount"> </a-lang>:</b
                            ><small
                              >{{ formatterNumber(item.amount, true) }}
                              {{ currencyView(item.currency) }}</small
                            >
                          </div>
                        </a>
                      </div>
                    </div>
                    <div class="modal-footer"></div>
                  </div>
                </div>
              </div>
            </div>
            <!-- GAMES HISTORY END -->
            <!-- BETS -->
            <div class="tab-pane fade" id="bets">
              <div v-if="!loading">
                <div class="row" v-if="!loading">
                  <div class="col-6">
                    <div class="form-group">
                      <p>
                        <a-lang value="date-from"> </a-lang>
                      </p>
                      <input
                        name="betDateFrom"
                        v-model="betDateFrom"
                        id="betDateFrom"
                        class="form-control"
                        type="date"
                        required
                        @change="getBets()"
                      />
                    </div>
                  </div>
                  <div class="col-6">
                    <div class="form-group">
                      <p>
                        <a-lang value="date-to"> </a-lang>
                      </p>
                      <input
                        name="betDateTo"
                        v-model="betDateTo"
                        id="betDateTo"
                        class="form-control"
                        type="date"
                        required
                        @change="getBets()"
                      />
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-12">
                    <div class="card">
                      <div class="card-header">
                        <span v-if="!isMetadata"
                          ><a-lang value="bets"> </a-lang></span
                        ><span v-if="isMetadata"
                          ><a-lang value="bet-info"> </a-lang
                        ></span>
                      </div>
                      <a-alert
                        v-if="bets && bets.length == 0"
                        :alert="{
                          type: 'alert-info',
                          message: this.getLocate('no-result-found'),
                        }"
                      ></a-alert>

                      <div
                        id="bet"
                        class="list-group scroll-list"
                        v-if="!isMetadata"
                      >
                        <a
                          data-toggle="modal"
                          data-target="#betDetails"
                          href="#"
                          class="list-group-item list-group-item-action my-align-left"
                          @click="getBetsDetail(item.bet_id)"
                          v-for="item in bets"
                          :key="item.bet_id"
                        >
                          <div class="row">
                            <div class="col">
                              <small>
                                <i class="fa fa-calendar"></i>
                                <span v-if="item.ts_open != null">{{
                                  formatDateYYYYMMDDHHmmss(item.ts_open)
                                }}</span>
                                - <a-lang value="bet-id"> </a-lang>:
                                <b>{{ item.bet_id }}</b>
                              </small>
                            </div>
                            <!-- <div class="col offset-6">
                              <span @click.stop="">
                                <i
                                  style="color: grey"
                                  class="fa fa-chevron-right"
                                  @click="
                                    isMetadata = true;
                                    getBetMetadata(item.betid);
                                  "
                                ></i>
                              </span>
                            </div>-->
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.bet != null"
                          >
                            <b><a-lang value="bet"> </a-lang>:</b
                            ><small>{{
                              formatterNumber(item.bet, true)
                            }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.win != null"
                          >
                            <b><a-lang value="win"> </a-lang>:</b
                            ><small>{{
                              formatterNumber(item.win, true)
                            }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.refund != null"
                          >
                            <b><a-lang value="refund"> </a-lang>:</b
                            ><small>{{
                              formatterNumber(item.refund, true)
                            }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.description != null"
                          >
                            <b><a-lang value="description"> </a-lang>:</b
                            ><small>{{ item.description }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.description != null"
                          >
                            <b><a-lang value="status"> </a-lang>:</b
                            ><small
                              :style="
                                item.status_desc == 'Won'
                                  ? 'color:green'
                                  : item.status_desc == 'Lost'
                                  ? 'color:red'
                                  : ''
                              "
                              >{{ getLocate("bet " + item.status_desc) }}</small
                            >
                          </div>
                        </a>
                      </div>
                      <div class="list-group scroll-list" v-if="isMetadata">
                        <div>
                          <div class="col-1">
                            <span>
                              <a href="#">
                                <i
                                  style="color: grey"
                                  class="fa fa-chevron-left"
                                  @click="isMetadata = false"
                                ></i>
                              </a>
                            </span>
                          </div>
                          <div
                            class="col-12"
                            v-if="
                              betMetadataList && betMetadataList.length == 0
                            "
                          >
                            <a-alert
                              :alert="{
                                type: 'alert-info',
                                message: 'No details found',
                              }"
                            ></a-alert>
                          </div>
                        </div>
                        <span
                          class="list-group-item my-align-left"
                          v-for="item in betMetadataList"
                          :key="item.name"
                        >
                          <div class="d-flex w-100 justify-content-between">
                            <b>{{ item.name }}:</b
                            ><small>{{ item.value }}</small>
                          </div>
                          <div class="d-flex w-100 justify-content-between">
                            <b><a-lang value="date"> </a-lang>:</b
                            ><small>{{
                              formatDateYYYYMMDDHHmmss(item.ts)
                            }}</small>
                          </div>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div
                class="modal fade"
                id="betDetails"
                tabindex="-1"
                role="dialog"
                aria-labelledby="betDetailsLabel"
                aria-hidden="true"
              >
                <div class="modal-dialog" role="document">
                  <div class="modal-content back-dark">
                    <div class="modal-header">
                      <h5 class="modal-title" id="exampleModalLabel">
                        <p>
                          <a-lang value="bet-detail"> </a-lang>
                        </p>
                      </h5>
                      <button
                        type="button"
                        class="close text-white"
                        @click="$('#betDetails').modal('hide')"
                        aria-label="Close"
                      >
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">
                      <a-alert
                        v-if="betDetails && betDetails.length == 0"
                        :alert="{
                          type: 'alert-info',
                          message: this.getLocate('no-result-found'),
                        }"
                      ></a-alert>
                      <div id="bet-detail" class="list-group scroll-list">
                        <a
                          data-toggle="modal"
                          data-target="#betDetails"
                          href="#"
                          class="list-group-item list-group-item-action my-align-left"
                          v-for="item in betDetails"
                          :key="item.round"
                        >
                          <small v-if="item.ts != null && item.ts != ''">
                            <i class="fa fa-calendar"></i>
                            <span>{{ formatDateYYYYMMDDHHmmss(item.ts) }}</span>
                          </small>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="
                              item.description != null && item.description != ''
                            "
                          >
                            <small>{{ item.description }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.device != null && item.device != ''"
                          >
                            <b><a-lang value="device"> </a-lang>:</b
                            ><small>{{ item.device }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.status != null && item.status != ''"
                          >
                            <b><a-lang value="state"> </a-lang>:</b
                            ><small>{{
                              getLocate("bet " + item.status)
                            }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.bet != null && item.bet > 0"
                          >
                            <b><a-lang value="bet"> </a-lang>:</b
                            ><small>{{
                              formatterNumber(item.bet, true)
                            }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.win != null && item.win > 0"
                          >
                            <b><a-lang value="win"> </a-lang>:</b
                            ><small>{{
                              formatterNumber(item.win, true)
                            }}</small>
                          </div>
                          <div
                            class="d-flex w-100 justify-content-between"
                            v-if="item.refund != null && item.refund > 0"
                          >
                            <b><a-lang value="refund"> </a-lang>:</b
                            ><small>{{
                              formatterNumber(item.refund, true)
                            }}</small>
                          </div>
                        </a>
                      </div>
                    </div>
                    <div class="modal-footer"></div>
                  </div>
                </div>
              </div>
            </div>
            <!-- BETS END -->
            <!-- LOTTERY -->
            <div class="tab-pane fade" id="lottery">
              <div v-if="!loading">
                <div class="row" v-if="!loading">
                  <div class="col-6">
                    <div class="form-group">
                      <p>
                        <a-lang value="date-from"> </a-lang>
                      </p>
                      <input
                        name="lotteryDateFrom"
                        v-model="lotteryDateFrom"
                        id="lotteryDateFrom"
                        class="form-control"
                        type="date"
                        required
                        @change="getLottery()"
                      />
                    </div>
                  </div>
                  <div class="col-6">
                    <div class="form-group">
                      <p>
                        <a-lang value="date-to"> </a-lang>
                      </p>
                      <input
                        name="lotteryDateTo"
                        v-model="lotteryDateTo"
                        id="lotteryDateTo"
                        class="form-control"
                        type="date"
                        required
                        @change="getLottery()"
                      />
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-12">
                    <div class="card">
                      <div class="card-header">
                        <a-lang value="lottery"> </a-lang>
                      </div>
                      <a-alert
                        v-if="lotteryList && lotteryList.length == 0"
                        :alert="{
                          type: 'alert-info',
                          message: this.getLocate('no-result-found'),
                        }"
                      ></a-alert>

                      <div id="lottery" class="list-group scroll-list">
                        <div v-for="item in lotteryList" :key="item.ticket_id">
                          <a
                            href="#"
                            class="list-group-item list-group-item-action my-align-left"
                            @click="openWindow(item.user_id, item.ticket_id)"
                          >
                            <div class="row">
                              <div class="col">
                                <small>
                                  <i class="fa fa-calendar"></i>
                                  <span v-if="item.ts != null">{{
                                    formatDateYYYYMMDDHHmmss(item.ts)
                                  }}</span>
                                  - <a-lang value="ticket-id"> </a-lang>:
                                  <b>{{ item.ticket_id }}</b>
                                </small>
                              </div>
                            </div>
                            <div class="d-flex w-100 justify-content-between">
                              <b><a-lang value="details"> </a-lang></b
                              ><i
                                class="fa fa-search"
                                @click="
                                  openDetailModal();
                                  getLotteryPayPrize(item.ticket_id);
                                "
                                @click.stop=""
                              ></i>
                            </div>
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
                            <b
                              ><small style="color: green">
                                {{ item.status }}</small
                              ></b
                            >
                          </a>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div
                class="modal fade"
                id="lotteryDetails"
                tabindex="-1"
                role="dialog"
                aria-labelledby="lotteryDetailsLabeled"
                aria-hidden="true"
              >
                <div class="modal-dialog" role="document">
                  <div class="modal-content back-dark">
                    <div class="modal-header">
                      <h5 class="modal-title" id="exampleModalLabel">
                        <p>
                          <a-lang value="prize"> </a-lang>
                        </p>
                      </h5>
                      <button
                        type="button"
                        class="close text-white"
                        @click="$('#lotteryDetails').modal('hide')"
                        aria-label="Close"
                      >
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">
                      <a-alert
                        v-if="
                          lotteryPayPrizeList && lotteryPayPrizeList.length == 0
                        "
                        :alert="{
                          type: 'alert-info',
                          message: this.getLocate('no-result-found'),
                        }"
                      ></a-alert>
                      <div id="bet-detail" class="list-group scroll-list">
                        <a
                          v-for="item in lotteryPayPrizeList"
                          :key="item.transaction_id"
                          href="#"
                          class="list-group-item list-group-item-action my-align-left"
                          @click="openWindow(item.user_id, item.ticket_id)"
                        >
                          <div class="row">
                            <div class="col">
                              <small>
                                <i class="fa fa-calendar"></i>
                                <span v-if="item.ts != null">{{
                                  formatDateYYYYMMDDHHmmss(item.ts)
                                }}</span>
                                - <a-lang value="ticket-id"> </a-lang>:
                                <b>{{ item.ticket_id }}</b>
                              </small>
                            </div>
                          </div>
                          <small
                            ><b><a-lang value="prize"> </a-lang></b
                          ></small>
                          <small>
                            <span style="color: green"
                              >+{{ formatterNumber(item.prize, true) }}
                              {{ currencyView(item.currency) }}</span
                            >
                          </small>
                          <br />
                          <small
                            ><b><a-lang value="status"> </a-lang>: </b></small
                          >
                          <b
                            ><small style="color: green">
                              {{ item.status }}</small
                            ></b
                          >
                        </a>
                      </div>
                    </div>
                    <div class="modal-footer"></div>
                  </div>
                </div>
              </div>
            </div>
            <!-- LOTTERY END -->
            <!-- SETTINGS -->
            <div class="tab-pane fade" id="settings">
              <div class="subscribe sub-form" v-if="!loading">
                <div class="form-group">
                  <div class="row">
                    <div class="col-12" v-for="s in settings" :key="s.type">
                      <div class="row">
                        <div class="col-12">
                          <div class="form-group">
                            <div class="row">
                              <div class="col text-center">
                                <label
                                  :class="
                                    !isNumberInteger(s.value) || s.value < 0
                                      ? 'text-danger'
                                      : 'text-white'
                                  "
                                >
                                  {{ getLocate(s.description) }}
                                </label>
                              </div>
                            </div>
                            <div class="row">
                              <div class="col-4"></div>
                              <div
                                class="col-4 text-center"
                                v-if="s.type == 'SessionExpirationTimeout'"
                              >
                                <select
                                  class="form-control select-settings"
                                  required
                                  step="1"
                                  :id="s.type"
                                  style="
                                    max-width: 150px;
                                    color: white;
                                    display: inline-block;
                                    background: transparent;
                                  "
                                  v-model="s.value"
                                  @change="
                                    changeSettings(
                                      s.type,
                                      s.description,
                                      s.value
                                    )
                                  "
                                >
                                  <option value="5">5</option>
                                  <option value="10">10</option>
                                  <option value="15">15</option>
                                  <option value="20">20</option>
                                  <option value="25">25</option>
                                  <option value="30">30</option>
                                  <option value="35">35</option>
                                  <option value="40">40</option>
                                  <option value="45">45</option>
                                  <option value="50">50</option>
                                  <option value="55">55</option>
                                  <option value="60">60</option>
                                </select>
                              </div>
                              <div class="col-4 text-center" v-else>
                                <input
                                  maxlength="7"
                                  v-model="s.value"
                                  :id="s.type"
                                  class="form-control"
                                  :class="
                                    !isNumberInteger(s.value) || s.value < 0
                                      ? 'is-invalid'
                                      : ''
                                  "
                                  type="number"
                                  required
                                  step="1"
                                  style="max-width: 150px"
                                  @keyup="
                                    changeSettings(
                                      s.type,
                                      s.description,
                                      s.value
                                    )
                                  "
                                  @change="
                                    changeSettings(
                                      s.type,
                                      s.description,
                                      s.value
                                    )
                                  "
                                />
                              </div>
                              <div class="col-4">
                                <small
                                  class="text-danger"
                                  v-if="
                                    !isNumberInteger(s.value) || s.value < 0
                                  "
                                >
                                  <a-lang value="invalid-number"> </a-lang>
                                </small>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-12">
                      <div class="form-group">
                        <div class="row">
                          <div class="col text-center">
                            <label class="text-white">
                              {{ getLocate("selfexclusion") }}
                            </label>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col text-center">
                            <select
                              class="form-control select-settings"
                              style="
                                max-width: 150px;
                                color: white;
                                display: inline-block;
                                background: transparent;
                              "
                              v-model="selfExcluded"
                              @change="confirmSelfexclusion()"
                            >
                              <option value="true">
                                {{ getLocate("yes") }}
                              </option>
                              <option value="false">No</option>
                            </select>
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
              id="confirmSelfexclusionModal"
              tabindex="-1"
              role="dialog"
              aria-labelledby="confirmSelfexclusionLabel"
              aria-hidden="true"
              style="top: 3vh"
            >
              <div class="modal-dialog" role="document">
                <div class="modal-content back-dark" style="min-height: 40vh">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">
                      <p><a-lang value="warning"> </a-lang>!</p>
                    </h5>
                    <button
                      type="button"
                      class="close text-white"
                      @click="
                        selfExcluded = false;
                        $('#confirmSelfexclusionModal').modal('hide');
                      "
                      aria-label="Close"
                    >
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <div class="container-fluid">
                      <a-alert
                        :alert="{
                          type: 'alert-warning',
                          message: this.getLocate('selfexclusion-alert'),
                        }"
                      ></a-alert>
                    </div>
                  </div>
                  <div class="modal-footer justify-content-center">
                    <div class="casino-btn">
                      <a
                        href="javascript:void(0)"
                        class="btn-4 yellow-btn"
                        @click="setSelfexcluded()"
                        ><a-lang value="confirm"> </a-lang
                      ></a>
                    </div>
                    <div class="casino-btn">
                      <a
                        href="javascript:void(0)"
                        class="btn-4 yellow-btn"
                        @click="
                          selfExcluded = false;
                          $('#confirmSelfexclusionModal').modal('hide');
                        "
                        ><a-lang value="cancel"> </a-lang
                      ></a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- SETTINGS END -->
        </div>
      </div>
      <ul class="modal-footer login_menu navbar-right nav-sign"></ul>
    </div>
  </div>
</template>

<script>
module.exports = {
  mixins: [common],
  data() {
    return {
      //generici
      loading: false,
      error: false,
      errorMessage: "",
      success: null,
      successMessage: "",
      errorCp: false,
      errorMessageCp: "",
      successCp: null,
      successMessageCp: "",
      metadata: [],
      isMetadata: false,
      pageView: "userInfo",
      userDetail: {},

      //tab profile
      newEmail: "",
      newPhone: "",
      alertEmailError: { type: "alert-danger", message: "" },
      alertPhoneError: { type: "alert-danger", message: "" },

      //tab bonuses
      bonuses: [],
      promoidBonusClose: null,

      //tab movements
      transactions: [],
      amount: null,
      reference: "",
      requestId: "",
      transactionId: "",
      transactionDateFrom: this.formatDateYYYYMMDD(
        this.addDays(new Date(), -1)
      ),
      transactionDateTo: this.formatDateYYYYMMDD(new Date()),
      transactionType: "ALL",

      //tab deposit
      depositTransactions: [],
      depositNuveiTransactions: [],
      detailPlacetopay: {},
      stateDeposit: "ALL",
      stateNuveiDeposit: "ALL",
      statePayphoneDeposit: "ALL",
      loadingCheck: false,
      intervalDeposit: null,
      depositOff: true,
      codeCoupon: null,
      loadingCoupon: false,
      alertCoupon: { type: "alert-success", message: "" },
      isPsdTransferFunds: false,
      isPsdPswError: false,
      isPsdPersonaId: false,
      depositDateFrom: this.formatDateYYYYMMDD(this.addDays(new Date(), -1)),
      depositDateTo: this.formatDateYYYYMMDD(new Date()),
      depositNuveiType: "cc_card",
      depositPaycashTransactions: [],
      statePaycashDeposit: "ALL",
      depositNicoTransactions: [],
      stateNicoDeposit: "ALL",
      urlPaycash: null,
      depositType: null,
      depositPayphoneTransactions: [],

      //tab withdrawal
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

      //games history
      gameSessionsHistory: [],
      sessionDetails: [],
      state: "ALL",
      type: "ALL",
      gameType: "ALL",
      gameCategories: [],
      sessionDateFrom: this.formatDateYYYYMMDD(this.addDays(new Date(), -1)),
      sessionDateTo: this.formatDateYYYYMMDD(new Date()),

      //bets
      bets: [],
      betDetails: [],
      betMetadataList: [],
      betDateFrom: this.formatDateYYYYMMDD(this.addDays(new Date(), -1)),
      betDateTo: this.formatDateYYYYMMDD(new Date()),

      //lottery
      lotteryList: [],
      lotteryDateFrom: this.formatDateYYYYMMDD(this.addDays(new Date(), -1)),
      lotteryDateTo: this.formatDateYYYYMMDD(new Date()),
      lotteryPayPrizeList: [],

      // settings
      settings: [],
      selfExcluded: false,

      showList: false,
      showPreviousButton: true,
    };
  },
  mounted() {
    // catturo la chiusura del modale
    $("#userProfile").on("hide.bs.modal", () => {
      if (this.depositOff) this.refreshDepositsOff();
      else this.depositOff = true;
    });
    if (
      store.state.tabProfile == "nuvei" ||
      store.state.tabProfile == "nuvei_safety" ||
      store.state.tabProfile == "placetopay" ||
      store.state.tabProfile == "paycash" ||
      store.state.tabProfile == "nico" ||
      store.state.tabProfile == "payphone"
    ) {
      this.activeTab("menu");
    } else this.activeTab(store.state.tabProfile);

    if (this.isUserLogged()) {
      if (store.state.tabProfile == "profile") {
        this.getUserInformation();
      } else if (store.state.tabProfile == "movements") {
        this.getTransactions();
      } else if (store.state.tabProfile == "deposit") {
        this.activaTab("menu");
      }
      this.getGameCategories();
    }

    store.state.tabProfile = null;
    if (store.state.linkProfile) {
      this.pageView = store.state.linkProfile;
    }
    store.state.linkProfile = null;
  },
  beforeDestroy() {
    this.refreshDepositsOff();
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
    gameSessionsFiltered() {
      let gameSessionsHistory = this.gameSessionsHistory;

      gameSessionsHistory = gameSessionsHistory.filter((c) => {
        if (this.state == "ALL") return true;
        if (c.state.toString().toUpperCase().includes(this.state.toUpperCase()))
          return true;
        else return false;
      });

      gameSessionsHistory = gameSessionsHistory.filter((c) => {
        if (this.type == "ALL") return true;
        if (c.win != null && c.bet != null && c.win > c.bet) return true;
        else return false;
      });

      gameSessionsHistory = gameSessionsHistory.filter((c) => {
        if (this.gameType == "ALL") return true;
        if (
          c.game_type
            .toString()
            .toUpperCase()
            .includes(this.gameType.toUpperCase())
        )
          return true;
        else return false;
      });

      gameSessionsHistory.sort(function (a, b) {
        let dataOrdinamento1;
        let dataOrdinamento2;
        if (a.ts_close != null) {
          dataOrdinamento1 = new Date(a.ts_close);
        } else {
          dataOrdinamento1 = new Date(a.ts_open);
        }
        if (b.ts_close != null) {
          dataOrdinamento2 = new Date(b.ts_close);
        } else {
          dataOrdinamento2 = new Date(b.ts_open);
        }
        if (dataOrdinamento1 < dataOrdinamento2) {
          return 1;
        }
        if (dataOrdinamento1 > dataOrdinamento2) {
          return -1;
        }
        return 0;
      });
      return gameSessionsHistory;
    },
    depositPayphoneFiltered() {
      let depositPayphoneTransactions = this.depositPayphoneTransactions;

      if (
        this.statePayphoneDeposit != null &&
        this.statePayphoneDeposit != "ALL"
      ) {
        depositPayphoneTransactions = depositPayphoneTransactions.filter(
          (d) => {
            if (
              d.status &&
              d.status
                .toString()
                .toUpperCase()
                .includes(this.statePayphoneDeposit.toUpperCase())
            )
              return true;
            else return false;
          }
        );
      }
      return depositPayphoneTransactions;
    },
  },
  methods: {
    getUserInformation() {
      this.getUserDetail();
      this.getBalances();
    },
    activeTab(tab) {
      $('.nav-principal a[href="#' + tab + '"]').tab("show");
    },
    getUserDetail() {
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      this.$http
        .post("user-details", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.userDetail = result.users[0];
            if (this.userDetail.status == 2) this.selfExcluded = true;
            else this.selfExcluded = false;
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getGameCategories() {
      this.$http
        .get("gameCategories")
        .then((response) => {
          this.gameCategories = response.data.categories;
        })
        .catch((response) => {
          this.showError(response, this.getLocate("login-7"));
        });
    },
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
    getBonuses() {
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("bonus/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.bonuses = result.bonuses;
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    confirmBonusClose(promoid) {
      this.promoidBonusClose = promoid;
      $("#confirmCloseBonusModal").modal({
        backdrop: "static",
        keyboard: false,
      });
    },
    confirmSelfexclusion() {
      $("#confirmSelfexclusionModal").modal({
        backdrop: "static",
        keyboard: false,
      });
    },
    checkWithdrawalBonus(item) {
      let expiredts = new Date(item.expiredts);
      let now = new Date();
      if (item.percentage >= 100 && now <= expiredts && item.withdrawts == null)
        return true;
    },
    checkCloseManuallyBonus(item, index) {
      if (item.percentage < 100 && index == 0) return true;
    },
    redeemBonus(promoid) {
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("promoid", promoid);
      this.$http
        .post("bonus/redeem", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getBonuses();
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    closeBonus(promoid) {
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("promoid", promoid);
      this.$http
        .post("bonus/close", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getBonuses();
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          $("#confirmCloseBonusModal").modal("hide");
          this.loading = false;
        });
    },
    //ottengo le transazioni per user id e session id
    getTransactions() {
      this.transactions = [];
      if (
        !this.checkDateFilter(this.transactionDateTo, this.transactionDateFrom)
      )
        return;

      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.transactionDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.transactionDateTo, 1))
      );
      formData.append("optype", this.transactionType);

      this.$http
        .post("transaction/listbytokens", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.transactions = result.transactions;
            this.transactions.sort(function (a, b) {
              let dataOrdinamento1;
              let dataOrdinamento2;
              if (a.ts_close != null) {
                dataOrdinamento1 = new Date(a.ts_close);
              } else {
                dataOrdinamento1 = new Date(a.ts_open);
              }
              if (b.ts_close != null) {
                dataOrdinamento2 = new Date(b.ts_close);
              } else {
                dataOrdinamento2 = new Date(b.ts_open);
              }
              if (dataOrdinamento1 < dataOrdinamento2) {
                return 1;
              }
              if (dataOrdinamento1 > dataOrdinamento2) {
                return -1;
              }
              return 0;
            });
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getDeposits() {
      this.resetMessages();
      this.urlPaycash = null;
      this.isMetadata = false;
      this.checkPsd();
      this.activaTab("menu");
    },
    getWithdrawal() {
      this.resetMessages();
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
      }
    },
    getDepositPTPTransactions(isLoading) {
      if (isLoading) this.loading = true;
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom)) {
        this.loading = false;
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          if (isLoading) this.loading = false;
        });
    },
    getDepositNuveiTransactions() {
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom))
        return;
      this.loading = true;
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getDepositPaycashTransactions() {
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom))
        return;
      this.loading = true;
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getDepositNicoTransactions() {
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom))
        return;
      this.loading = true;
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getDepositPayphoneTransactions() {
      if (!this.checkDateFilter(this.depositDateTo, this.depositDateFrom))
        return;
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.depositDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.depositDateTo, 1))
      );
      this.$http
        .post("deposit/payphone/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.depositPayphoneTransactions = result.deposits;
            this.depositPayphoneTransactions.sort(function (a, b) {
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getWithdrawPsd() {
      let action = "psd/list";
      if (this.typeWithdraw == "bank") action = "bank/list";

      if (!this.checkDateFilter(this.withdrawalDateTo, this.withdrawalDateFrom))
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getGameSessions() {
      this.gameSessionsHistory = [];
      if (!this.checkDateFilter(this.sessionDateTo, this.sessionDateFrom))
        return;

      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("from", this.localDateToUtc(this.sessionDateFrom));
      formData.append(
        "to",
        this.localDateToUtc(this.addDays(this.sessionDateTo, 1))
      );
      this.$http
        .post("session/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.gameSessionsHistory = result.sessions;
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getSessionDetails(sessionId) {
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("sessionid", sessionId);
      this.$http
        .post("session/detail", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.sessionDetails = result.rounds;
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    closeSession(sessionId) {
      this.error = false;
      this.success = false;
      this.errorMessage = "";
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("sessionid", sessionId);
      this.$http
        .post("session/close", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.getGameSessions();
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    checkAmountDeposit() {
      this.error = false;
      this.success = false;
      this.errorMessage = "";

      if (!this.amount) {
        this.error = true;
        this.errorMessage = this.getLocate("profile-1");
        return false;
      }

      //converto in centesimi il valore inserito
      let value = this.convertNumberToCent(this.amount);
      //valido controllando che il valore inserito non sia minore/uguale a zero
      if (value <= 0) {
        this.error = true;
        this.errorMessage = this.getLocate("profile-1");
        return false;
      }
      //valido controllando che la currency sia stata correttamente caricata (viene inizializzata da getBalance: Non Cashable)
      if (
        this.currency == null ||
        this.currency == "" ||
        this.currency == undefined
      ) {
        this.error = true;
        this.errorMessage = this.getLocate("login-7");
        return false;
      }

      return true;
    },
    checkAmountNuvei() {
      this.error = false;
      this.success = false;
      this.errorMessage = "";

      if (
        this.depositNuveiType == "apmgw_SafetyPay_Online" ||
        this.depositNuveiType == "apmgw_SafetyPay_Cash"
      ) {
        let amountMax = 700;
        if (this.depositNuveiType == "apmgw_SafetyPay_Online") amountMax = 5000;

        if (this.amount < 5 || this.amount > amountMax) {
          this.error = true;
          this.errorMessage =
            this.getLocate("insert-rechange4") +
            " 5 USD, " +
            this.getLocate("insert-rechange5") +
            " " +
            amountMax +
            " USD";
          return false;
        }
      }

      if (this.depositNuveiType == "cc_card") {
        if (this.amount < 5 || this.amount > 3000) {
          this.error = true;
          this.errorMessage =
            this.getLocate("insert-rechange4") +
            " 5 USD, " +
            this.getLocate("insert-rechange5") +
            " " +
            "3000 USD";
          return false;
        }
      }

      return true;
    },
    checkAmountPlacetopay() {
      this.error = false;
      this.success = false;
      this.errorMessage = "";

      if (this.amount < 5 || this.amount > 3000) {
        this.error = true;
        this.errorMessage =
          this.getLocate("insert-rechange4") +
          " 5 USD, " +
          this.getLocate("insert-rechange5") +
          " " +
          "3000 USD";
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
            this.error = true;
            this.errorMessage = result.messageKo;
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    depositNuvei() {
      let modal = document.getElementById("modal-body-scroll");
      modal.scrollTop = 0;
      if (!this.checkAmountDeposit()) return;
      if (!this.checkAmountNuvei()) return;
      this.loading = true;
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
            this.loading = false;
            this.error = true;
            this.errorMessage = result.messageKo;
          } else {
            this.openPopup(result.processUrl);
            this.amount = null;
          }
        })
        .catch((response) => {
          this.loading = false;
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    depositPaycash() {
      let modal = document.getElementById("modal-body-scroll");
      modal.scrollTop = 0;
      this.urlPaycash = null;
      if (!this.checkAmountDeposit()) return;
      this.loading = true;
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
            this.error = true;
            this.errorMessage = result.messageKo;
          } else {
            //window.open(result.processUrl, "_blank");
            this.urlPaycash = result.processUrl;
            this.amount = null;
            // $("#userProfile").modal("hide");
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    depositNico() {
      let modal = document.getElementById("modal-body-scroll");
      modal.scrollTop = 0;
      if (!this.checkAmountDeposit()) return;
      this.loading = true;
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
            this.error = true;
            this.errorMessage = result.messageKo;
          } else {
            window.open(result.processUrl, "_blank");
            this.amount = null;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    depositPayphone() {
      let modal = document.getElementById("modal-body-scroll");
      modal.scrollTop = 0;
      if (!this.checkAmountDeposit()) return;
      this.loading = true;
      //mando la richiesta
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("amount", this.convertNumberToCent(this.amount));
      this.$http
        .post("deposit/payphone/reserve", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.error = true;
            this.errorMessage = result.messageKo;
          } else {
            this.openPopup(result.processUrl);
            this.amount = null;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    openPopup(url) {
      //window.open(url, "_blank");
      location.href = url;
    },
    wireTransfer() {
      this.error = false;
      this.success = false;
      this.errorMessage = "";
      this.successMessage = "";
      //converto in centesimi il valore inserito
      var value = this.convertNumberToCent(this.amountWireTransfer);
      //valido controllando che il valore inserito non sia minore/uguale a zero
      if (value <= 0) {
        this.error = true;
        this.errorMessage = this.getLocate("profile-1");
        return;
      }

      if (this.amountWireTransferCent > this.cashableCredits) {
        this.error = true;
        this.errorMessage = this.getLocate("insufficient-funds");
        return;
      }

      //valido controllando che la currency sia stata correttamente caricata (viene inizializzata da getBalance: Non Cashable)
      if (
        this.currency == null ||
        this.currency == "" ||
        this.currency == undefined
      ) {
        this.error = true;
        this.errorMessage = this.getLocate("login-7");
        return;
      }

      if (
        this.accountHolder == null ||
        this.accountHolder == "" ||
        this.accountHolder == undefined
      ) {
        this.error = true;
        this.errorMessage = this.getLocate("profile-4");
        return;
      }

      if (
        this.accountIban == null ||
        this.accountIban == "" ||
        this.accountIban == undefined
      ) {
        this.error = true;
        this.errorMessage = this.getLocate("profile-5");
        return;
      }

      if (!this.isValidIBANNumber(this.accountIban)) {
        this.error = true;
        this.errorMessage = this.getLocate("profile-6");
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
            this.error = true;
            this.errorMessage = this.getLocate(result.messageKo);
          } else {
            this.success = true;
            this.successMessage = this.getLocate("profile-7");
            this.getWithdrawTransactions();
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    reserveWithdrawPsd() {
      this.error = false;
      this.success = false;
      this.errorMessage = "";
      this.successMessage = "";
      let modal = document.getElementById("modal-body-scroll");
      if (modal) modal.scrollTop = 0;

      if (!this.amountWithdrawPsd) {
        this.error = true;
        this.errorMessage = this.getLocate("profile-1");
        return false;
      }

      if (
        this.typeWithdraw == "bank" &&
        (this.amountWithdrawPsd < 20 || this.amountWithdrawPsd > 2000)
      ) {
        this.error = true;
        this.errorMessage = this.getLocate("profile-8");
        return false;
      }

      if (
        this.typeWithdraw == "bank" &&
        !this.validateCtaNumber(this.ctaNumber)
      ) {
        this.error = true;
        this.errorMessage = this.getLocate("cta-number-message");
        return false;
      }

      //converto in centesimi il valore inserito
      let value = this.convertNumberToCent(this.amountWithdrawPsd);
      //valido controllando che il valore inserito non sia minore/uguale a zero
      if (value <= 0) {
        this.error = true;
        this.errorMessage = this.getLocate("profile-1");
        return;
      }

      if (value > this.cashableCredits) {
        this.error = true;
        this.errorMessage = this.getLocate("insufficient-funds");
        return;
      }

      //valido controllando che la currency sia stata correttamente caricata (viene inizializzata da getBalance: Non Cashable)
      if (
        this.currency == null ||
        this.currency == "" ||
        this.currency == undefined
      ) {
        this.error = true;
        this.errorMessage = this.getLocate("login-7");
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
            this.error = true;
            this.errorMessage = this.getLocate(result.messageKo);
          } else {
            this.success = true;
            this.successMessage =
              (this.typeWithdraw == "bank"
                ? this.getLocate("profile-9")
                : this.getLocate("profile-7")) +
              (result.description == "success-first-withdraw"
                ? ".<br><br>" + this.getLocate("first-withdraw")
                : "");
            this.getWithdrawPsd();
            this.amountWithdrawPsd = null;
            if (this.typeWithdraw == "bank") {
              this.ctaNumber = null;
              this.accountType = "Ahorros";
            }
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
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
            this.error = true;
            this.errorMessage = result.description;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    transactionFees() {
      this.$http
        .post("transaction/fees")
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.error = true;
            this.errorMessage = this.getLocate(result.messageKo);
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    calculateFeeAmount() {
      let amountWithdrawPsdCent = this.convertNumberToCent(
        this.amountWithdrawPsd
      );
      // calculate amount fee
      this.feeAmount = ((this.feePerc / 100) * amountWithdrawPsdCent) / 100;
    },
    resetMessages() {
      this.amount = null;
      this.success = false;
      this.successMessage = "";
      this.error = false;
      this.errorMessage = "";
      this.refreshDepositsOff();
    },
    resetWithdrawal(type) {
      this.typeWithdraw = type;
      this.amountWithdrawPsd = null;
      this.withdrawalPsd = null;
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
            this.error = true;
            this.errorMessage = result.description;
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
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    getBets() {
      this.bets = [];
      if (!this.checkDateFilter(this.betDateTo, this.betDateFrom)) return;

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("datefrom", this.localDateToUtc(this.betDateFrom));
      formData.append(
        "dateto",
        this.localDateToUtc(this.addDays(this.betDateTo, 1))
      );
      this.$http
        .post("bets/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.bets = result.bets;
            this.bets.sort(function (a, b) {
              let dateA = new Date(a.ts);
              let dateB = new Date(b.ts);
              if (dateA > dateB) return -1;
              else return 1;
            });
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    getBetsDetail(betid) {
      let formData = new FormData();
      formData.append("betid", betid);
      this.$http
        .post("bets/detail", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.betDetails = result.bets;
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    getBetMetadata(betid) {
      let formData = new FormData();
      formData.append("betid", betid);
      this.$http
        .post("bets/metadata/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.betMetadataList = result.metadata;
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    getLottery() {
      this.lotteryList = [];
      if (!this.checkDateFilter(this.lotteryDateTo, this.lotteryDateFrom))
        return;

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("datefrom", this.localDateToUtc(this.lotteryDateFrom));
      formData.append(
        "dateto",
        this.localDateToUtc(this.addDays(this.lotteryDateTo, 1))
      );
      this.$http
        .post("lottery/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.lotteryList = result.lotteryTickets;
            this.lotteryList.sort(function (a, b) {
              let dateA = new Date(a.ts);
              let dateB = new Date(b.ts);
              if (dateA > dateB) return -1;
              else return 1;
            });
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    getLotteryPayPrize(ticketId) {
      this.lotteryPayPrizeList = [];
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("ticketId", ticketId);
      this.$http
        .post("lottery/payprize/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.lotteryPayPrizeList = result.lotteryPayPrizeTickets;
            this.lotteryPayPrizeList.sort(function (a, b) {
              let dateA = new Date(a.ts);
              let dateB = new Date(b.ts);
              if (dateA > dateB) return -1;
              else return 1;
            });
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    getSettings() {
      this.loading = true;
      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("limits/list", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.result == 0) {
            this.settings = result.limits;
            if (this.settings)
              this.settings = this.settings.sort((a, b) =>
                a.type > b.type ? 1 : -1
              );
          } else {
            this.error = true;
            this.errorMessage = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        })
        .finally(() => {
          this.loading = false;
        });
    },
    changeSettings(type, description, value) {
      this.error = false;
      this.errorMessage = null;
      if (!this.isNumberInteger(value) || value < 0) return;

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      formData.append("type", type);
      formData.append("value", value);
      formData.append("description", description);
      formData.append("operator", store.state.userId);
      this.$http
        .post("limits/set", formData)
        .then((response) => {
          let result = response.data;
          if (result.result != 0) {
            this.alertType = "error";
            this.message = result.description || result.error;
          }
        })
        .catch((response) => {
          this.showError(response);
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    CambiaPassword() {
      let formData = new FormData();
      formData.append("email", store.state.email);
      this.$http
        .post("reserve", formData)
        .then((response) => {
          var result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.errorCp = true;
            this.errorMessageCp = result.messageKo;
          } else {
            this.successCp = true;
            this.successMessageCp = this.getLocate("login-5");
          }
        })
        .catch((response) => {
          this.showError(response);
          this.errorCp = true;
          this.errorMessageCp = this.getLocate("login-7");
        });
    },
    activaTab(tab) {
      if (tab == "menu") this.showPreviousButton = false;
      else this.showPreviousButton = true;
      $('a[href="#' + tab + '"]').tab("show");
    },
    refreshDepositsOn() {
      this.intervalDeposit = setInterval(() => {
        this.getDepositPTPTransactions();
      }, 5000);
    },
    refreshDepositsOff() {
      clearInterval(this.intervalDeposit);
    },
    checkDateFilter(date1, date2) {
      if (!date1 || !date2) {
        this.error = true;
        this.errorMessage = this.getLocate("check-date-4");
        return false;
      }

      const MAX_DAYS_VIEW = 30;
      this.error = false;
      this.errorMessage = null;
      const diffDays = this.differenceTwoDate(date1, date2);
      if (diffDays > MAX_DAYS_VIEW) {
        this.error = true;
        this.errorMessage =
          this.getLocate("check-date-1") +
          " " +
          MAX_DAYS_VIEW +
          " " +
          this.getLocate("check-date-2");
        return false;
      }
      if (diffDays < 0) {
        this.error = true;
        this.errorMessage = this.getLocate("check-date-3");
        return false;
      }
      return true;
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
          this.error = true;
          this.errorMessage = this.getLocate("login-7");
        });
    },
    openWindow(userId, ticketId) {
      window.open(
        store.state.loteriaUrlDetail + userId + "-" + ticketId,
        "",
        "toolbar=no, width=700,height=500"
      );
    },
    openDetailModal() {
      $("#lotteryDetails").modal();
    },
    isDescriptionRefund(description) {
      return description.includes("refund deposit");
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
                this.error = true;
                this.errorMessage = this.getLocate("limit-bank-transfers");
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
    changeEmail() {
      this.success = false;
      this.successMessage = "";
      if (!this.newEmail || this.newEmail == "") {
        this.alertEmailError.message = this.getLocate("email-not-empty");
        return;
      }
      if (!this.validateEmail(this.newEmail)) {
        this.alertEmailError.message = this.getLocate("email-not-valid");
        return;
      }
      let formData = new FormData();
      formData.append("email", this.newEmail);
      this.$http
        .post("email/reserve", formData)
        .then((response) => {
          let result = response.data;
          this.forceLogout(result.result);
          if (result.messageKo != null && result.messageKo != "") {
            this.alertEmailError.message = result.messageKo;
          } else {
            this.newEmail = "";
            this.alertEmailError.message = "";
            $("#changeEmail").modal("hide");
            this.success = true;
            this.successMessage = this.getLocate("email-change-sent");
          }
        })
        .catch((response) => {
          this.showError(response);
          this.alertEmailError.message = this.getLocate("email-error");
        });
    },
    changePhone() {
      this.success = false;
      this.successMessage = "";
      if (!this.newPhone || this.newPhone == "") {
        this.alertPhoneError.message = this.getLocate("phone-not-empty");
        return;
      }
      if (!this.validateMobilePhone(this.newPhone)) {
        this.alertPhoneError.message = this.getLocate("phone-not-valid");
        return;
      }
      let formData = new FormData();
      formData.append("phone", this.newPhone);
      this.$http
        .post("phone/change", formData)
        .then((response) => {
          let result = response.data;
          if (result.messageKo != null && result.messageKo != "") {
            this.alertPhoneError.message = result.messageKo;
          } else {
            this.newPhone = "";
            this.alertPhoneError.message = "";
            $("#changePhone").modal("hide");
            this.success = true;
            this.successMessage = this.getLocate("phone-changed");
            location.href = "#";
            location.reload();
          }
        })
        .catch((response) => {
          this.showError(response);
          this.alertPhoneError.message = this.getLocate("phone-error");
        });
    },
    setSelfexcluded() {
      this.success = false;
      this.successMessage = "";

      let formData = new FormData();
      formData.append("usertoken", store.state.userId);
      formData.append("sessiontoken", store.state.sessionId);
      this.$http
        .post("set/selfexcluded", formData)
        .then((response) => {
          let result = response.data;
          if (result.result == 0) {
            this.forceLogout(-100);
          }
        })
        .catch((response) => {
          this.showError(response);
        });
    },
  },
};
</script>
