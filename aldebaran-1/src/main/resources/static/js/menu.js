(function ($) {
  $.fn.menumaker = function (options) {
    var cssmenu = $(this),
      settings = $.extend(
        {
          format: "dropdown",
          sticky: false,
        },
        options
      );

    return this.each(function () {
      $(this)
        .find(".button")
        .on("click", function () {
          let menuWrapper = cssmenu.find("#menuWrapper");
          if (menuWrapper.length == 0) return;

          if (menuWrapper.css("display") == "flex") {
            document.body.style.overflow = "auto";
            menuWrapper.css("display", "none");
          } else {
            document.body.style.overflow = "hidden";
            menuWrapper.css("display", "flex");
          }
        });

      let headerLogo = document.querySelector(
        "#header .container .row .col-4 .logo"
      );
      if (headerLogo) {
        headerLogo.addEventListener("click", function () {
          let menuWrapper = cssmenu.find("#menuWrapper");
          menuWrapper.css("display", "none");
          document.body.style.overflow = "auto";
        });
      }
      let headerShoppingCart = document.querySelector(
        "#header .container .row .col-2 .fa-shopping-cart"
      );
      if (headerShoppingCart) {
        headerShoppingCart.addEventListener("click", function () {
          let menuWrapper = cssmenu.find("#menuWrapper");
          menuWrapper.css("display", "none");
          document.body.style.overflow = "auto";
        });
      }
      let menuLi = cssmenu.find("ul div li");
      if (menuLi.length > 0) {
        for (let i = 0; i < menuLi.length; i++) {
          // se ha un sotto menu aggiungo il listener ai li del sotto menu
          if (menuLi[i].classList.contains("custom-sub-menu")) {
            let subMenu = menuLi[i].querySelectorAll("div ul li");
            if (subMenu) {
              for (let j = 0; j < subMenu.length; j++) {
                subMenu[j].addEventListener("click", function () {
                  let menuWrapper = cssmenu.find("#menuWrapper");
                  menuWrapper.css("display", "none");
                  document.body.style.overflow = "auto";
                });
              }
            }
          } else {
            //altrimenti aggiungo il listener ai li del menu principale
            menuLi[i].addEventListener("click", function () {
              let menuWrapper = cssmenu.find("#menuWrapper");
              menuWrapper.css("display", "none");
              document.body.style.overflow = "auto";
            });
          }
        }
      }

      if (settings.sticky === true) cssmenu.css("position", "fixed");
      resizeFix = function () {
        var mediasize = 1190;
        if ($(window).width() > mediasize) {
          cssmenu.find("ul").show();
        }
      };
      resizeFix();
      return $(window).on("resize", resizeFix);
    });
  };
})(jQuery);

(function ($) {
  $(document).ready(function () {
    $("#cssmenu").menumaker({
      format: "multitoggle",
    });
  });
})(jQuery);
