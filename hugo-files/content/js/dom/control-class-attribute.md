---
title: "JavaScriptメモ: HTML 要素の class 属性の値を追加・削除・トグルする"
url: "p/6wxqs3z/"
date: "2015-03-01"
tags: ["javascript"]
aliases: [/js/dom/control-class-attribute.html]
---

下記の `addClass()`、`removeClass()`、`toggleClass()` 関数は、指定した要素の class 属性を操作します。

{{< code lang="js" title="指定した要素の class 属性に値（CSS クラス名）を追加" >}}
/**
 * Add a class to the specified element.
 */
function addClass(elem, clazz) {
  const classes = elem.className.split(/\s+/);
  for (let i = 0, len = classes.length; i < len; ++i) {
    if (classes[i] === clazz) {
      // Specified class is already included in the element.
      return;
    }
  }
  elem.className = elem.className + ' ' + clazz;
}
{{< /code >}}

{{< code lang="js" title="指定した要素の class 属性から値（CSS クラス名）を削除" >}}
/**
 * Remove a class from the specified element.
 */
function removeClass(elem, clazz) {
  const classes = elem.className.split(/\s+/);
  for (let i = 0, len = classes.length; i < len; ++i) {
    if (classes[i] === clazz) {
      classes.splice(i, 1);
      elem.className = classes.join(' ');
      return;
    }
  }
}
{{< /code >}}

{{< code lang="js" title="指定した要素の class 属性の値（CSS クラス名）をトグル（ON ⇔ OFF）する" >}}
/**
 * Toggle a class of the specified element.
 */
function toggleClass(elem, clazz) {
  const classes = elem.className.split(/\s+/);
  for (let i = 0, len = classes.length; i < len; ++i) {
    if (classes[i] === clazz) {
      // If specified class is found, remove it.
      classes.splice(i, 1);
      elem.className = classes.join(' ');
      return;
    }
  }
  // If the specified class is not found, add it to the end.
  elem.className = elem.className + ' ' + clazz;
}
{{< /code >}}

{{< code lang="js" title="使用例" >}}
// 初期状態で 2 つのクラスを持つ要素を取得
const elem = document.getElementById('elem');
console.log(elem.className); //=> 'class1 class2'

// class3 を追加
addClass(elem, 'class3');
console.log(elem.className); //=> 'class1 class2 class3'

// class2 を削除
removeClass(elem, 'class2');
console.log(elem.className); //=> 'class1 class3'

// class3 をトグル
toggleClass(elem, 'class3');
console.log(elem.className); //=> 'class1'

// class3 をトグル
toggleClass(elem, 'class3');
console.log(elem.className); //=> 'class1 class3'
{{< /code >}}
