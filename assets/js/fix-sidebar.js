document.addEventListener('DOMContentLoaded', function() {
  window.addEventListener('resize', handleResize);
  handleResize();

  function handleResize() {
    const sidebarNotFixed = document.getElementById('xContainer_sidebar_notFixed');
    const sidebarFixed = document.getElementById('xContainer_sidebar_fixed');
    const elems = document.getElementById('xContainer_sidebar').querySelectorAll('[data-sticky]');
    const winHeight = window.innerHeight;

    // data-sticky 属性を持つ要素を後ろから見ていき、
    // ウィンドウ内に収まる要素にフラグを立てる
    //（data-sticky 属性の値を 'true' にする）。
    // $(...get().reverse()) は要素を逆順に処理するイディオム。
    let sum = 0;
    Array.from(elems).reverse().forEach(function(elem) {
      sum += elem.offsetHeight;
      elem.dataset.sticky = sum < winHeight;
    });

    // サイドバー内の要素を #sidebar_notFixed と、
    // #sidebar-fixed の子要素として振り分ける。
    // 順番がおかしくならないようにループを分けている。
    elems.forEach(function(elem) {
      if (elem.dataset.sticky === 'true') {
        sidebarFixed.appendChild(elem);
      } else {
        sidebarNotFixed.appendChild(elem);
      }
    });
  }
});
