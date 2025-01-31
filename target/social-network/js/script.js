(function($) {

  "use strict";

  // Search Popup
  var searchPopup = function() {
    // open search box
    $('.user-items').on('click', '.search-button', function(e) {
      $('.search-popup').toggleClass('is-visible');
    });

    $('.user-items').on('click', '.btn-close-search', function(e) {
      $('.search-popup').toggleClass('is-visible');
    });
    
    $(".search-popup-trigger").on("click", function(b) {
        b.preventDefault();
        $(".search-popup").addClass("is-visible"),
        setTimeout(function() {
            $(".search-popup").find("#search-popup").focus()
        }, 350)
    }),
    $(".search-popup").on("click", function(b) {
        ($(b.target).is(".search-popup-close") || $(b.target).is(".search-popup-close svg") || $(b.target).is(".search-popup-close path") || $(b.target).is(".search-popup")) && (b.preventDefault(),
        $(this).removeClass("is-visible"))
    }),
    $(document).keyup(function(b) {
        "27" === b.which && $(".search-popup").removeClass("is-visible")
    })
  }

  var postPopup = function() {
    // open post box
    $('.poster').on('click', '.post-button', function(e) {
      console.log("pressed");
      $('.post-popup').toggleClass('is-visible');
    });

    $('.poster').on('click', '.btn-close-search', function(e) {
      $('.post-popup').toggleClass('is-visible');
    });
    
    $(".post-popup-trigger").on("click", function(b) {
        b.preventDefault();
        $(".post-popup").addClass("is-visible"),
        setTimeout(function() {
            $(".post-popup").find("#post-popup").focus()
        }, 350)
    }),
    $(".post-popup").on("click", function(b) {
        ($(b.target).is(".post-popup-close") || $(b.target).is(".post-popup-close svg") || $(b.target).is(".post-popup-close path") || $(b.target).is(".post-popup")) && (b.preventDefault(),
        $(this).removeClass("is-visible"))
    }),
    $(document).keyup(function(b) {
        "27" === b.which && $(".post-popup").removeClass("is-visible")
    })
    
  }


  $(document).ready(function () {
    $('.closenav').on('click', function () {
        $('.sidenavbar').removeClass('active');
    });

    $('.btn-menu').on('click', function () {
        $('.sidenavbar').addClass('active');
    });
    
    searchPopup();
    postPopup();
  }); 

  window.addEventListener("load", function () {
    const preloader = document.getElementById("preloader");
    preloader.classList.add("hide-preloader");

    $('.entry-container').isotope({
      itemSelector: '.entry-item',
      layoutMode: 'masonry'
    });
    
  });

})(jQuery);
