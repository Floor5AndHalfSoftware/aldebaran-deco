const common = {
  data() {
    return {
      rules: {},
      error: true,
      errorMessage: null,
      alert: { type: "alert-success", message: "" },
      loading: false,
    };
  },
  filters: {},
  methods: {
    async loadVideo(url) {
      await (() => {
        store.state.urlvideo = url;
        store.state.modalVideoKey++;
      })();
      $("#videoModal").modal("show");
    },
    stopVideo() {
      store.state.urlvideo = "";
    },
    formatDateD(date) {
      if (!date) return null;
      const [beforeT] = date.split("T");
      return `${beforeT}`;
    },
    isUserLogged() {
      return store.state.userId != null && store.state.userId != "";
    },
    isUserSelfexcluded() {
      return parseInt(store.state.status) == 2;
    },
    logout() {
      this.$http
        .post("logout")
        .then((response) => {
          let result = response.data;
          if (
            result.logout != null &&
            result.logout != "" &&
            result.logout == "ok"
          ) {
            location.href = "/";
          }
        })
        .catch((response) => {
          this.showError(response);
        });
    },
    validatePassport(passport) {
      const re = /^[a-zA-Z0-9]{6,}$/;
      return re.test(passport.trim());
    },
    validateEmail(email) {
      const re =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email.trim());
    },
    validateNumber(text) {
      const re = /^[0-9]*$/;
      return re.test(text);
    },
    validateCtaNumber(text) {
      const re = /^[0-9]{5,20}$/;
      return re.test(text);
    },
    validateMobilePhone(text) {
      const re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{2,8}$/im;
      return re.test(text);
    },
    validatePassword(text) {
      //const re = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!$()=^[\]\.:,;]).{8,12}$/;
      const re = /^[A-Za-z0-9]{8,12}$/;
      return re.test(text);
    },
    validateTaxcode(text) {
      const re = /^[A-Za-z0-9]*\d+[A-Za-z0-9]*$/;
      return re.test(text);
    },
    validateNameSurname(text) {
      if (text) {
        const re =
          /^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$/u;
        return re.test(text);
      }
      return false;
    },
    validateGeneric(text, regularExpression) {
      if (regularExpression) return new RegExp(regularExpression).test(text);
      else return true;
    },
    underAgeValidate(birthday) {
      // it will accept two types of format yyyy-mm-dd and yyyy/mm/dd
      let optimizedBirthday = birthday.replace(/-/g, "/");

      //set date based on birthday at 01:00:00 hours GMT+0100 (CET)
      let myBirthday = new Date(optimizedBirthday);

      // set current day on 01:00:00 hours GMT+0100 (CET)
      let currentDate = new Date().toJSON().slice(0, 10) + " 01:00:00";

      // calculate age comparing current date and borthday
      let myAge = ~~((Date.now(currentDate) - myBirthday) / 31557600000);

      return !(myAge < parseInt(store.state.underAge) || myAge > 100);
    },
    formatterNumber(value, divideBy100 = false) {
      // Formatta in 1.231,23:
      if (value) {
        let newValue;
        if (divideBy100) newValue = this.roundN(value / 100, 2);
        else newValue = parseFloat(value).toFixed(2);

        // Ogni 3 cifre intere inserisce un "."
        let itFormat = newValue
          .toString()
          .replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");

        return itFormat;
      } else return "0.00";
    },
    convertNumberToCent(value) {
      if (value) {
        let newValue = parseFloat(Math.round(value * 100));
        return newValue;
      }
    },
    roundN(num, n) {
      return parseFloat(
        Math.round(num * Math.pow(10, n)) / Math.pow(10, n)
      ).toFixed(n);
    },
    deviceType() {
      const ua = navigator.userAgent;
      if (
        /(tablet|ipad|playbook|silk)|(android(?!.*mobi))/i.test(ua) ||
        /Mobile|Android|iP(hone|od)|IEMobile|BlackBerry|Kindle|Silk-Accelerated|(hpw|web)OS|Opera M(obi|ini)/.test(
          ua
        )
      ) {
        return "mobile";
      }
      return "desktop";
    },
    showError(response, customResponse) {
      document.getElementById("back-top-btn").click();
      if (customResponse)
        this.alert = { type: "alert-danger", message: customResponse };
      else {
        if (response?.response?.status == 500)
          this.alert = {
            type: "alert-danger",
            message: this.getLocate("login-7"),
          };
        else this.alert = { type: "alert-danger", message: response };
      }

      console.log("catch error:", response);
      if (response?.response?.status == 401) location.href = "#/";
    },
    forceLogout(resultCode) {
      if (resultCode == -100) {
        console.log("force logout");
        this.logout();
      }
    },
    loadingOn() {
      $(".preloader").css("display", "inline");
    },
    loadingOff() {
      $(".preloader").css("display", "none");
    },
    loadingOnDelay(delay) {
      this.refreshKey++;
      this.loadingOn();
      if (delay) $(".preloader").delay(delay).fadeOut(0);
      else $(".preloader").delay(400).fadeOut(0);
    },
    destroySportsBook() {
      if (window.ASB) {
        window.ASB.remove();
        console.log("[SB] destroy SportsBook widget");
      }
      if (window.WSB1) {
        window.WSB1.remove();
        console.log("[SB] destroy WUpcoming widget");
      }
      if (window.WSB2) {
        window.WSB2.remove();
        console.log("[SB] destroy WPopularBets widget");
      }
    },
    /*
     * Returns 1 if the IBAN is valid
     * Returns FALSE if the IBAN's length is not as should be (for CY the IBAN Should be 28 chars long starting with CY )
     * Returns any other number (checksum) when the IBAN is invalid (check digits do not match)
     */
    isValidIBANNumber(input) {
      const CODE_LENGTHS = {
        AD: 24,
        AE: 23,
        AT: 20,
        AZ: 28,
        BA: 20,
        BE: 16,
        BG: 22,
        BH: 22,
        BR: 29,
        CH: 21,
        CR: 21,
        CY: 28,
        CZ: 24,
        DE: 22,
        DK: 18,
        DO: 28,
        EE: 20,
        ES: 24,
        FI: 18,
        FO: 18,
        FR: 27,
        GB: 22,
        GI: 23,
        GL: 18,
        GR: 27,
        GT: 28,
        HR: 21,
        HU: 28,
        IE: 22,
        IL: 23,
        IS: 26,
        IT: 27,
        JO: 30,
        KW: 30,
        KZ: 20,
        LB: 28,
        LI: 21,
        LT: 20,
        LU: 20,
        LV: 21,
        MC: 27,
        MD: 24,
        ME: 22,
        MK: 19,
        MR: 27,
        MT: 31,
        MU: 30,
        NL: 18,
        NO: 15,
        PK: 24,
        PL: 28,
        PS: 29,
        PT: 25,
        QA: 29,
        RO: 24,
        RS: 22,
        SA: 24,
        SE: 24,
        SI: 19,
        SK: 24,
        SM: 27,
        TN: 24,
        TR: 26,
        AL: 28,
        BY: 28,
        CR: 22,
        EG: 29,
        GE: 22,
        IQ: 23,
        LC: 32,
        SC: 31,
        ST: 25,
        SV: 28,
        TL: 23,
        UA: 29,
        VA: 22,
        VG: 24,
        XK: 20,
        AO: 25,
      };
      let iban = String(input)
          .toUpperCase()
          .replace(/[^A-Z0-9]/g, ""), // keep only alphanumeric characters
        code = iban.match(/^([A-Z]{2})(\d{2})([A-Z\d]+)$/), // match and capture (1) the country code, (2) the check digits, and (3) the rest
        digits;
      // check syntax and length
      if (!code || iban.length !== CODE_LENGTHS[code[1]]) {
        return false;
      }
      // rearrange country code and check digits, and convert chars to ints
      digits = (code[3] + code[1] + code[2]).replace(
        /[A-Z]/g,
        function (letter) {
          return letter.charCodeAt(0) - 55;
        }
      );
      // final check
      return this.mod97(digits);
    },
    validateCedula(cedula) {
      let cedula1 = cedula.trim();
      if (
        typeof cedula1 == "string" &&
        cedula.length == 10 &&
        /^\d+$/.test(cedula1)
      ) {
        let digitos = cedula1.split("").map(Number);
        let codigo_provincia = digitos[0] * 10 + digitos[1];

        //if (codigo_provincia >= 1 && (codigo_provincia <= 24 || codigo_provincia == 30) && digitos[2] < 6) {

        if (
          codigo_provincia >= 1 &&
          (codigo_provincia <= 24 || codigo_provincia == 30)
        ) {
          let digito_verificador = digitos.pop();

          let digito_calculado =
            digitos.reduce(function (valorPrevio, valorActual, indice) {
              return (
                valorPrevio -
                ((valorActual * (2 - (indice % 2))) % 9) -
                (valorActual == 9) * 9
              );
            }, 1000) % 10;

          return digito_calculado === digito_verificador;
        }
      }
      return false;
    },
    isNumberInteger(value) {
      let x = parseFloat(value);
      return !isNaN(value) && (x | 0) === x;
    },
    mod97(string) {
      let checksum = string.slice(0, 2),
        fragment;
      for (let offset = 2; offset < string.length; offset += 7) {
        fragment = String(checksum) + string.substring(offset, offset + 7);
        checksum = parseInt(fragment, 10) % 97;
      }
      return checksum;
    },
    formatDateYYYYMMDD(date) {
      if (!date) return;
      let newDate = this.convertUTCDateToLocalDate(new Date(date));
      return newDate.toISOString().split("T")[0];
    },
    formatDateYYYYMMDDHHmmss(datetime) {
      if (!datetime) return;
      let newDate = this.convertUTCDateToLocalDate(new Date(datetime));
      let time = newDate.toISOString().split("T")[1].split(".")[0];
      return newDate.toISOString().split("T")[0] + " " + time;
    },
    convertUTCDateToLocalDate(date) {
      let newDate = new Date(date);
      newDate.setMinutes(date.getMinutes() - date.getTimezoneOffset());
      return newDate;
    },
    localDateToUtc(value) {
      const [year, month, day] = value.split("-");
      let date = new Date(year, month - 1, day, 0, 0, 0);
      return date.toISOString().slice(0, 19).replace("T", " ");
    },
    addDays(value, days) {
      let date = new Date(value);
      return this.dateISOString(
        new Date(date.getTime() + days * 24 * 60 * 60 * 1000)
      );
    },
    dateISOString(date) {
      return date.toISOString().slice(0, 10);
    },
    differenceTwoDate(date1, date2) {
      const dateA1 = new Date(date1);
      const dateA2 = new Date(date2);
      const diffTime = dateA1 - dateA2;
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      return diffDays;
    },
    getLocate(key) {
      if (LANGUAGES[store.state.locate][key])
        return LANGUAGES[store.state.locate][key];
      else return key;
    },
    codeLocate() {
      if (store.state.locate == "en-GB") return "en_GB";
      else if (store.state.locate == "es-ES") return "es_ES";
      return "en_GB";
    },
    isReloadOnly(currentPath) {
      const paths = ["/games", "/promotions", "/overview"];
      return paths.some((p) => p.includes(currentPath));
    },
    currencyView(currency) {
      if (currency == "AOA") return "Kz";
      else if (currency == "USD") return "$";
      return currency;
    },
    initSlideshow(name = "unique") {
      $(".carousel").carousel({
        interval: 6000,
        pause: "false",
      });

      $(".carousel .carousel-inner ." + name).swipe({
        swipeLeft() {
          this.parent().carousel("next");
        },
        swipeRight() {
          this.parent().carousel("prev");
        },
        threshold: 0,
        tap() {
          // get the location: in my case the target is my link
          window.location = $(this)
            .find(".carousel-item.active a")
            .attr("href");
        },
        excludedElements: "label, button, input, select, textarea, .noSwipe",
      });

      $(".carousel .carousel-inner ." + name).on("dragstart", "a", function () {
        return false;
      });
    },
    isDesktop() {
      const navigatorAgent =
        navigator.userAgent || navigator.vendor || window.opera;
      return !(
        /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series([46])0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(
          navigatorAgent
        ) ||
        /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br([ev])w|bumb|bw-([nu])|c55\/|capi|ccwa|cdm-|cell|chtm|cldc|cmd-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc-s|devi|dica|dmob|do([cp])o|ds(12|-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly([-_])|g1 u|g560|gene|gf-5|g-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd-([mpt])|hei-|hi(pt|ta)|hp( i|ip)|hs-c|ht(c([- _agpst])|tp)|hu(aw|tc)|i-(20|go|ma)|i230|iac([ \-/])|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja([tv])a|jbro|jemu|jigs|kddi|keji|kgt([ /])|klon|kpt |kwc-|kyo([ck])|le(no|xi)|lg( g|\/([klu])|50|54|-[a-w])|libw|lynx|m1-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t([- ov])|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30([02])|n50([025])|n7(0([01])|10)|ne(([cm])-|on|tf|wf|wg|wt)|nok([6i])|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan([adt])|pdxg|pg(13|-([1-8]|c))|phil|pire|pl(ay|uc)|pn-2|po(ck|rt|se)|prox|psio|pt-g|qa-a|qc(07|12|21|32|60|-[2-7]|i-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h-|oo|p-)|sdk\/|se(c([-01])|47|mc|nd|ri)|sgh-|shar|sie([-m])|sk-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h-|v-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl-|tdg-|tel([im])|tim-|t-mo|to(pl|sh)|ts(70|m-|m3|m5)|tx-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c([- ])|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas-|your|zeto|zte-/i.test(
          navigatorAgent.substr(0, 4)
        )
      );
    },
    isPlacetopayUser() {
      return (
        store.state.placetopayUsers == "" ||
        (store.state.userId &&
          store.state.placetopayUsers.includes(store.state.userId))
      );
    },
    isNuveiUser() {
      return (
        store.state.nuveiUsers == "" ||
        (store.state.userId &&
          store.state.nuveiUsers.includes(store.state.userId))
      );
    },
    isMonnetPayinUser() {
      return (
        store.state.monnetPayinUsers == "" ||
        (store.state.userId &&
          store.state.monnetPayinUsers.includes(store.state.userId))
      );
    },
    isMonnetPayoutUser() {
      return (
        store.state.monnetPayoutUsers == "" ||
        (store.state.userId &&
          store.state.monnetPayoutUsers.includes(store.state.userId))
      );
    },
    isSafetypayUser() {
      return (
        store.state.safetypayUsers == "" ||
        (store.state.userId &&
          store.state.safetypayUsers.includes(store.state.userId))
      );
    },
    isPaycashUser() {
      return (
        store.state.paycashUsers == "" ||
        (store.state.userId &&
          store.state.paycashUsers.includes(store.state.userId))
      );
    },
    isNicoUser() {
      return (
        store.state.nicoUsers == "" ||
        (store.state.userId &&
          store.state.nicoUsers.includes(store.state.userId))
      );
    },
    isPdsUser() {
      return (
        store.state.pdsUsers == "" ||
        (store.state.userId &&
          store.state.pdsUsers.includes(store.state.userId))
      );
    },
    isBanktransferUser() {
      return (
        store.state.banktransferUsers == "" ||
        (store.state.userId &&
          store.state.banktransferUsers.includes(store.state.userId))
      );
    },
    isPayphoneUser() {
      return (
        store.state.payphoneUsers == "" ||
        (store.state.userId &&
          store.state.payphoneUsers.includes(store.state.userId))
      );
    },
    isSmarticoMissionUser() {
      return (
        store.state.smarticoMissionUsers == "" ||
        (store.state.userId &&
          store.state.smarticoMissionUsers.includes(store.state.userId))
      );
    },
    changeLoading(val) {
      this.$emit("change-loading", val);
    },
    setError(message) {
      this.$emit("set-error", message);
    },
    setSuccess(message) {
      this.$emit("set-success", message);
    },
    separateDecimalCash(numberString) {
      const [integer, decimal = "0"] = numberString.split(".");
      return { integer, decimal };
    },
    isPromoGamesUsers() {
      return (
        store.state.promoGamesUsers == "" ||
        (store.state.userId &&
          store.state.promoGamesUsers.includes(store.state.userId))
      );
    },
  },
};
