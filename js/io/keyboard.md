---
title: "キーボードからの入力を取得する"
date: "2015-03-31"
---

キーハンドルの基本
----

`window.addEventListener` 関数を使用して、`keydown` や `keyup` イベントのハンドラを設定しておくと、ページ内で発生したキー入力を捕捉することができます。
下記の例では、入力されたキー情報をページ内に表示しています。

~~~ html
<DOCTYPE! html>
<html>
<script>
  window.onload = function() {
    'use strict';
    var resultElem = document.getElementById('result');

    /**
     * Handles a key down/up event.
     * @param {KeyboardEvent} e
     */
    function onKeyDownOrUp(e) {
      // Display key information
      resultElem.innerText = 'code=' + e.code + ', key=' + e.key +
          ', shiftKey=' + e.shiftKey + ', ctrlKey=' + e.ctrlKey +
          ', type=' + e.type + ', repeat=' + e.repeat;
      // Prevent the keyboard events from bubbling up to the broser itself
      e.preventDefault();
    };

    // Set up key event handlers
    window.addEventListener('keydown', onKeyDownOrUp);
    window.addEventListener('keyup', onKeyDownOrUp);
  };
</script>
<body>
  <p>ここを一度クリックしてから、キー入力してみてください。</p>
  <div id="result"></div>
</body>
</html>
~~~

### デモ

<iframe class="xHtmlDemo" src="keyboard-demo.html"></iframe>
<a target="_blank" href="keyboard-demo.html">デモページを開く</a>

下記は、キーボードから a b c とタイプしたときの出力です。
`keydown` と `keyup` の2つのイベントを捕捉しているため、キー入力ごとに2度情報が表示されます。

~~~
code=KeyA, key=a, shiftKey=false, ctrlKey=false, type=keydown, repeat=false
code=KeyA, key=a, shiftKey=false, ctrlKey=false, type=keyup, repeat=false
code=KeyB, key=b, shiftKey=false, ctrlKey=false, type=keydown, repeat=false
code=KeyB, key=b, shiftKey=false, ctrlKey=false, type=keyup, repeat=false
code=KeyC, key=c, shiftKey=false, ctrlKey=false, type=keydown, repeat=false
code=KeyC, key=c, shiftKey=false, ctrlKey=false, type=keyup, repeat=false
~~~

`keydown` イベントと `keyup` イベント用のイベントハンドラは、下記のように設定することができます。
ここでは、共通のイベントハンドラとして、`onKeyDownOrUp` 関数を設定しています。

~~~ javascript
// Set up key event handlers
window.addEventListener('keydown', onKeyDownOrUp);
window.addEventListener('keyup', onKeyDownOrUp);
~~~

あるいは、次のように設定することもできます（こちらの方法の場合、高々1つのイベントハンドラしか設定できません）。

~~~ javascript
window.onkeydown = onKeyDownOrUp;
window.onkeyup = onKeyDownOrUp;
~~~

キーイベントが発生すると、上記で指定したイベントハンドラが呼び出され、パラメータとして `KeyboardEvent` オブジェクトが渡されます。
`KeyboardEvent` のプロパティにどんなものが用意されているかは、下記のサイトにまとめられています。

- [KeyboardEvent - Web APIs｜MDN](https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent)

例えば、どのようなキーが押されたかを調べるには、`code` プロパティや、`key` プロパティの値を参照します。

<div class="note">
似たようなプロパティとして、<code>charCode</code> や、<code>keyCode</code>、<code>which</code> プロパティがありますが、これらは既に deprecated（非推奨）メソッドとなっているため、使用してはいけません。
</div>

`KeyboardEvent.code` プロパティや、`KeyboardEvent.key` プロパティの値が具体的にどのような値になるかは、下記のページにまとめられています。

- [KeyboardEvent.code - Web APIs｜MDN](https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/code)
- [Key Values - Web APIs｜MDN](https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/key/Key_Values)

イベントハンドラ内で、`KeyboardEvent.preventDefault()` を呼び出しておくと、上位要素（ここではブラウザ自身）にキー入力が引き渡されなくなります。
これを入れておかないと、例えば TAB キーを入力したときに、最後にフォーカス位置が移動してしまいます。

~~~ javascript
// Prevent the keyboard events from bubbling up to the broser itself
e.preventDefault();
~~~


押されたキーによって振る舞いを変える
----

押されたキーによって処理方法を変えるには、例えば下記のように `KeyboardEvent.code` の値を見て switch で分岐するようにします。
今回は、`keydown` イベントのみをハンドルしています。

~~~ javascript
window.onload = function() {
  'use strict';
  var MOVE_BY = 5;  // 移動量
  var puppetElem = document.getElementById('puppet');

  /** Handles a key down/up event. */
  function onKeyDown(e) {
    // Display key information
    switch (e.code) {
      case 'ArrowLeft':
      case 'KeyA':
        moveElem(puppetElem, -MOVE_BY, 0);
        break;
      case 'ArrowRight':
      case 'KeyD':
        moveElem(puppetElem, MOVE_BY, 0);
        break;
      case 'ArrowUp':
      case 'KeyW':
        moveElem(puppetElem, 0, -MOVE_BY);
        break;
      case 'ArrowDown':
      case 'KeyS':
        moveElem(puppetElem, 0, MOVE_BY);
        break;
    }
    // Prevent the keyboard events from bubbling up to the broser itself
    e.preventDefault();
  };

  /** Moves a specified HTML element. */
  function moveElem(elem, offsetX, offsetY) {
    var rect = elem.getBoundingClientRect();
    elem.style.left = rect.left + offsetX + 'px';
    elem.style.top = rect.top + offsetY + 'px';
  }

  // Set up key event handlers
  window.addEventListener('keydown', onKeyDown);
};
~~~

次のデモウィンドウをマウスでクリックしてから、カーソルキーや、<kbd>A</kbd>、<kbd>S</kbd>、<kbd>D</kbd>、<kbd>W</kbd> を入力すると、画面内の要素を動かすことができます。

### デモ

<iframe class="xHtmlDemo" src="keyboard-demo2.html"></iframe>
<a target="_blank" href="keyboard-demo2.html">デモページを開く</a>


jQuery でキーハンドル処理を行う
----

jQuery を使用すると、イベントハンドラの登録部分を若干シンプルに記述することができます。

~~~ javascript
$(function() {
  function onKeyDownOrUp(event) {
    console.log('code=' + event.code);
    event.preventDefault();
  }
  $(document).bind('keydown keyup', onKeyDownOrUp);
});
~~~

上記のように、`keydown` と `keyup` イベントのハンドラを、一度に設定してしまうことができます。

