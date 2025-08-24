---
title: "Androidメモ: Android Studio の便利なショートカット (1) コードの調査／メソッド間のジャンプ"
url: "p/3vejawn/"
date: "2016-04-12"
tags: ["android"]
aliases: ["/android/studio/shortcut-method-jump.html"]
description: "Android Studio などの IDE の大きな利点は、メソッドの使用場所や定義場所へジャンプする機能が優れていることです。ショートカットキーを使いこなすと、実装作業だけでなく、既存コードの分析も効率的に進めることができるようになります。"
---

<style>
.local-focus {
  background: pink;
}
th.local-key-column {
  width: 13em;
}
</style>

コード探索に便利なショートカットキー一覧
----

ショートカットキーの組み合わせはたくさんあってややこしいですが、**<kbd>B</kbd> キー系のショートカットは定義へのジャンプ**、**<kbd>F7</kbd> キー系のショートカットは参照箇所へのジャンプ**と覚えれば迷わずに使用できるようになります。
<kbd>B</kbd> キーに関しては、マウスの<kbd>左クリック</kbd>で置き換えられるようになっています。

ひとまず、下記を使いこなせばほとんどのケースはカバーできるはずです。

- <kbd>Ctrl-Alt-B</kbd> -- クラス実装、メソッド実装、変数定義へのジャンプ
- <kbd>Ctrl-Shift-B</kbd> -- 変数の型（クラス）へのジャンプ
- <kbd>Alt-F7</kbd> -- 参照箇所へのジャンプ
- <kbd>Ctrl-Alt-左</kbd> -- ジャンプ元へ戻る

下記はコードを調査するときに便利なショートカットキーの一覧です。
特に使いこなしたいキーに関しては、★マークを付けてあります。


### B キー系（実装、定義へのジャンプ）

<table>
  <tr>
    <th></th><th class="local-key-column">ショートカットキー</th><th>説明</th>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Ctrl-B</kbd><br><kbd>Ctrl-クリック</kbd><br><kbd>中央ボタンクリック</kbd></td>
    <td><b>Declaration</b><br>コンテキストに応じて振る舞いが変わる。例えばカーソルがメソッド定義にあるときは参照箇所へジャンプ、カーソルが参照箇所にあるときは定義へジャンプする。できればこれに頼らず、より用途のはっきりした別のショートカットを使うとよい（下記2つ）。</td>
  </tr>
  <tr>
    <td>★</td>
    <td><kbd>Ctrl-Alt-B</kbd><br><kbd>Ctrl-Alt-クリック</kbd></td>
    <td><b>Implementation(s)</b><br>子クラスやインタフェースの実装クラス、メソッド実装へジャンプ。インタフェース→具象クラス、メソッド参照→メソッド実装、というジャンプ。</td>
  </tr>
  <tr>
    <td>★</td>
    <td><kbd>Ctrl-Shift-B</kbd><br><kbd>Ctrl-Shift-クリック</kbd></td>
    <td><b>Type Declaration</b><br>テキストカーソル下のオブジェクトの実装クラスへジャンプ。あるいは、テキストカーソル下のメソッドの戻り値のオブジェクトの実装クラスへジャンプ（戻り値が void の場合は何も起こらない）。オブジェクト→クラス、というジャンプ。</td>
  </tr>
</table>


### F7 キー系（参照箇所へのジャンプ）

<table>
  <tr>
    <th></th><th class="local-key-column">ショートカットキー</th><th>説明</th>
  </tr>
  <tr>
    <td>★</td>
    <td><kbd>Alt-F7</kbd></td>
    <td><b>Find Usages</b><br>メソッドや変数の参照箇所をツリー表示</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Ctrl-Alt-F7</kbd></td>
    <td><b>Show Usages</b><br>メソッドや変数の参照箇所をリスト表示（1か所だけなら即ジャンプ）</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Ctrl-F7</kbd></td>
    <td><b>Find Usages in File</b><br>ファイル内の参照箇所へジャンプ。<kbd>F3</kbd>で次へ移動。</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Ctrl-Shift-F7</kbd></td>
    <td><b>Highlight Usages in File</b><br>ファイル内の同じキーワードをハイライト、どんどん追加していける。<kbd>Esc</kbd> ですべてのハイライトをクリア。</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Ctrl-Alt-Shift-F7</kbd></td>
    <td><b>Find Usages Settings...</b></td>
  </tr>
</table>


### F キー系（テキストサーチ）

<table>
  <tr>
    <th></th><th class="local-key-column">ショートカットキー</th><th>説明</th>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Ctrl-F</kbd></td>
    <td><b>Find...</b><br>ファイル内をテキスト検索</td>
  </tr>
  <tr>
    <td>★</td>
    <td><kbd>Ctrl-Shift-F</kbd></td>
    <td><b>Find in Path...</b><br>プロジェクト内をテキスト検索</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Ctrl-Alt-F</kbd></td>
    <td><b>Field...</b><br>フィールドをテキスト検索</td>
  </tr>
</table>

### その他

<table>
  <tr>
    <th></th><th class="local-key-column">ショートカットキー</th><th>説明</th>
  </tr>
  <tr>
    <td>★</td>
    <td><kbd>Ctrl-Alt-左/右</kbd></td>
    <td><b>Back/Forward</b><br>ジャンプ元へ戻る／進む</td>
  </tr>
  <tr>
    <td>★</td>
    <td><kbd>Ctrl-N</kbd></td>
    <td><b>Class...</b><br>クラス名を指定してファイルオープン</td>
  </tr>
  <tr>
    <td>★</td>
    <td><kbd>Ctrl-H</kbd></td>
    <td><b>Type Hierarchy</b><br>クラスやインタフェースの階層構造を表示</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Ctrl-F12</kbd></td>
    <td><b>File Structure</b><br>クラス内のメンバ、フィールドをリスト表示</td>
  </tr>
</table>


実装へジャンプする B キー系ショートカット
----

<kbd>Ctrl-B</kbd>（<kbd>Ctrl-クリック</kbd>）はコンテキストによって異なる振る舞いをするため、可能であれば、このショートカットは使わずに、より用途が明確な <kbd>Ctrl-Alt-B</kbd>、<kbd>Ctrl-Shift-B</kbd> の方を使えるようになると、スムーズにコード調査を行えるようになります。

- <kbd>Ctrl-B</kbd> (Declaration) -- 振る舞いがころころ変わる
- <kbd>Ctrl-Alt-B</kbd> (Implementations) -- クラス実装、メソッド実装、変数定義へのジャンプ
- <kbd>Ctrl-Shift-B</kbd> (Type Declaration) -- 変数の型（クラス）へのジャンプ

ここでは、テキストカーソルの位置によってどのように振る舞いが変わるかを具体的に見ていきましょう。

### クラス／インタフェース定義にテキストカーソルがある場合

<pre>
public class <span class="local-focus">MyClass</span> {
    ...
}

public interface <span class="local-focus">MyInterface</span> {
    ...
}
</pre>

- <kbd>Ctrl-B</kbd> -- クラスの参照箇所へジャンプ（<kbd>Ctrl-Alt-F7</kbd> と同様）
- <kbd>Ctrl-Alt-B</kbd> -- 実装クラス（or子クラス）へジャンプ（<kbd>Ctrl-H</kbd> で継承ツリー表示した方がわかりやすい）
- <kbd>Ctrl-Shift-B</kbd> -- 該当なし（オブジェクトじゃないため）


### 親クラス／インタフェースにテキストカーソルがある場合

<pre>
public class Child extends <span class="local-focus">Parent</span> {
    ...
}

public class Concrete implements <span class="local-focus">IInterface</span> {
    ...
}
</pre>

- <kbd>Ctrl-B</kbd> -- 親クラスの定義へジャンプ
- <kbd>Ctrl-Alt-B</kbd> -- 実装クラスへジャンプ
- <kbd>Ctrl-Shift-B</kbd> -- 該当なし（オブジェクトじゃないため）


### メソッドの定義にテキストカーソルがある場合

<pre>
public class MyClass {
    void <span class="local-focus">myMethod</span>() {
        ...
    }
}
</pre>

- <kbd>Ctrl-B</kbd> -- メソッドの参照箇所へジャンプ
- <kbd>Ctrl-Alt-B</kbd> -- 該当なし（自分自身が実装のため）
- <kbd>Ctrl-Shift-B</kbd> -- 該当なし（ただし、戻り値があればそのオブジェクトのクラスへジャンプ）


### メソッドの参照箇所にテキストカーソルがある場合

<pre>
public class MyClass {
    void myMethod() {
        obj.<span class="local-focus">method</span>();
    }
}
</pre>

- <kbd>Ctrl-B</kbd> -- メソッドの実装へジャンプ
- <kbd>Ctrl-Alt-B</kbd> -- メソッドの実装へジャンプ
- <kbd>Ctrl-Shift-B</kbd> -- 該当なし（オブジェクトじゃないため）


### 変数定義にテキストカーソルがある場合

<pre>
public class MyClass {
    private MyClass <span class="local-focus">mField</span>;
    ...
    private void method(MyClass <span class="local-focus">param</span>) {
        ...
    }
}
</pre>

- <kbd>Ctrl-B</kbd> -- 変数の参照箇所へジャンプ（<kbd>Ctrl+Alt+F7</kbd>と同様）
- <kbd>Ctrl-Alt-B</kbd> -- 該当なし（変数自体に実装はない）
- <kbd>Ctrl-Shift-B</kbd> -- そのオブジェクトのクラスへジャンプ


### 変数の参照箇所にテキストカーソルがある場合

<pre>
public class MyClass {
    private void method() {
        <span class="local-focus">mMyField</span>.method();
    }

    private void method(MyClass param) {
        hoge(<span class="local-focus">param</span>);
    }
}
</pre>

- <kbd>Ctrl-B</kbd> -- 変数の定義へジャンプ
- <kbd>Ctrl-Alt-B</kbd> -- 同上
- <kbd>Ctrl-Shift-B</kbd> -- そのオブジェクトのクラスへジャンプ


参照箇所へジャンプする F7 キー系ショートカット
----

参照箇所へジャンプする <kbd>F7</kbd> キー系のショートカットは、定義／実装へジャンプする <kbd>B</kbd> キー系のショートカットと違って振る舞いが一定しているため簡単に使いこなすことができます。
基本は、<kbd>Alt-F7</kbd> で参照箇所をツリー表示してジャンプ、という使い方で OK です。
ツリー表示された状態で、<kbd>Ctrl-Alt-上/下</kbd> を押すことで、参照箇所に順番にジャンプすることができます（行入れ替えの <kbd>Ctrl-Shift-上/下</kbd> と間違えないように！）。


Ctrl+H （クラスやインタフェースの継承構造を表示）
----

クラス名にカーソルがある状態で、<kbd>Ctrl-H</kbd> を押すと、そのクラスの継承構造がすべてツリー表示されます。

調査対象としているクラスが複雑な継承構造を持っているとき、やみくもにコードを読み進めても全体の振る舞いを理解しにくいことがあります。
**まずは、調査したいクラスの継承構造を把握する**のがよいでしょう。
インタフェース名にカーソルがある状態で <kbd>Ctrl-H</kbd> を押すと、そのインタフェースを実装するクラスをすべて把握することができます。

この操作は、クラス名、あるいは、インタフェース名にテキストカーソルが置かれている状態でしかうまく機能しないことに注意してください。
フィールドや、変数にテキストカーソルが置かれている状態で実行した場合は、そのフィールドを保持しているクラスに関しての情報が表示されます。

