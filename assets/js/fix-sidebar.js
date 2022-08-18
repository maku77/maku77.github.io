$(function() {
  $(window).on('resize', handleResize);
  handleResize();

  function handleResize() {
    var $sidebarNotFixed = $('#xContainer_sidebar_notFixed');
    var $sidebarFixed = $('#xContainer_sidebar_fixed');
    var $elems = $('#xContainer_sidebar').find('[data-sticky]');
    var winHeight = $(window).height();

    // data-sticky 属性を持つ要素を後ろから見ていき、
    // ウィンドウ内に収まる要素にフラグを立てる
    //（data-sticky 属性の値を true にする）。
    // $(...get().reverse()) は要素を逆順に処理するイディオム。
    var sum = 0;
    $($elems.get().reverse()).each(function() {
      sum += $(this).outerHeight(true);
      $(this).data('sticky', sum < winHeight);
    });

    // サイドバー内の要素を #sidebar_notFixed と、
    // #sidebar-fixed の子要素として振り分ける。
    // 順番がおかしくならないようにループを分ける。
    $elems.each(function() {
      if ($(this).data('sticky')) {
        $sidebarFixed.append($(this));
      } else {
        $sidebarNotFixed.append($(this));
      }
    });
  }
});

