---
title: "JavaScriptメモ: jQuery でフォームに入力した値を取得する"
url: "p/t2ifk4j/"
date: "2017-03-20"
tags: ["javascript"]
aliases: [/js/jquery/form.html]
---


フォーム上のボタンを押した時に値を取得する
----

下記は、テキストフィールドとボタンを１つずつ持つフォームのサンプルです。

#### 表示例

<input id="text1" type="text" size="20" value="Hello" />
<button id="button1">Submit</button>

{{< code lang="html" title="sample.html" >}}
<!DOCTYPE html>
<meta charset="UTF-8">
<title>jQuery Test</title>
<form>
  <input id="text1" type="text" size="20" value="Hello"></input>
  <button id="button1">Submit</button>
</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
$(() => {
  $('#button1').click((e) => {
    e.preventDefault();  // ボタン押下時のサブミット動作を抑制
    const val = $('#text1').val();
    console.log(val);
  });
});
</script>
{{< /code >}}

ボタンの `click` イベントをハンドルし、テキストフィールドに入力した値を `val()` メソッドを使用して取得しています。
ボタンを押した時のデフォルトの振る舞い（サブミット先の画面へ遷移）を抑制するために、`click` イベントを `preventDefault()` しています。


フォーム上で Enter キーを押したときのイベントをハンドルする
----

フォーム上のテキストフィールドなどで入力中に Enter キーを押したときと、Submit ボタンを押したときのイベントを同じように処理したいのであれば、以下のようにフォーム要素の `submit` イベントをハンドルします。

```javascript
$('#form1').submit((e) => {
  e.preventDefault();  // サブミット動作をキャンセル
  const val = $('#text1').val();
  console.log(val);
});
```

このように `submit` イベントのハンドラを設定しておけば、ボタン要素がない場合でも Enter キーの入力を捕捉することができます。
一方で、個々のボタン要素の `click` イベントをハンドルする方法だと、ブラウザによっては、input 領域で Enter キーを押したときにイベントが発生しないので注意してください。


フォーム要素を name 属性で参照する
----

下記のように、`input` 要素に `id` 属性の代わりに、`name` 属性だけが付けられている場合は、親の `form` 要素から相対的に参照するように記述するとわかりやすくなります。

```html
<form id="form1">
  <input name="text1" type="text" value="Hello"></input>
  ...
</form>
```

```javascript
const $text1 = $('#form1 [name=text1]');
const value = $text1.val();
```


テキスト入力のたびに処理する
----

テキストフィールド上でテキストを編集するたびにイベントをハンドルしたい場合は、下記のように `input` イベントをハンドルします。

```html
<input id="text1" type="text" value="Hello"></input>
```

```javascript
$('#text1').on('input', (e) => {
  const val = $(this).val();  // #('#text1').val() と同じ
  console.log(val);
});
```

上記の例では、テキストフィールドの `input` イベントをハンドルしていますが、フォーム要素の `input` イベントをハンドルするように記述すれば、フォーム内での入力をすべてハンドルできるようになります。

```html
<form id="form1">
  <input id="text1" type="text" value="Hello1"></input>
  <input id="text2" type="text" value="Hello2"></input>
</form>
```

```javascript
$('#form1').on('input', (e) => {
  const val1 = $('#text1').val();
  const val2 = $('#text2').val();
  console.log(val1, val2);
});
```
