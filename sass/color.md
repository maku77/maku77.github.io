---
title: "色（カラー値）を扱う関数"
date: "2018-12-30"
---

Color 型変数の定義方法
----

CSS では下記のような色の指定方法があります。

~~~ scss
color: red;                      // 定数名で指定
color: #ff0000;                  // 16進数で指定
color: rgb(255, 0, 0);           // 10進数で指定
color: rgba(255, 0, 0, 0.3);     // 10進数で指定（α値あり）
color: hsl(0, 100%, 50%);        // HSL指定
color: hsla(0, 100%, 50%, 0.3);  // HSL指定（α値あり）
~~~

CSS の `rgba` 関数は、上記のように4つのパラメータをとりますが、Sass の `rgba` 関数はより柔軟なパラメータ指定が可能になっています。

~~~ scss
color: rgba(red, 0.3);       // 定数名 + α値
color: rgba(#ff0000, 0.3);   // 16進数 + α値
~~~

また、Sass では名前付きパラメータを使って値を指定できるので、各パラメータの意味を明確にすることができます。

~~~ scss
color: hsl($hue: 0, $saturation: 100%, $lightness: 50%);  // 赤になる
~~~

色を調整するための関数
----

### 明度を調整する lighten() / darken()

**`lighten`** 関数、**`darken`** 関数を使用すると、ある色をパーセンテージ指定で明るくしたり暗くしたりすることができます。

<table>
  <tr>
    <th>SCSS コード内での記述</th><th>変換後</th>
  </tr>
  <tr>
    <td><code>background: lighten(red, 30%);</code></td>
    <td style="background:#ff9999;width:40px;color:white;">#ff9999</td>
  </tr>
  <tr>
    <td><code>background: lighten(red, 20%);</code></td>
    <td style="background:#ff6666;width:40px;color:white;">#ff6666</td>
  </tr>
  <tr>
    <td><code>background: lighten(red, 10%);</code></td>
    <td style="background:#ff3333;width:40px;color:white;">#ff3333</td>
  </tr>
  <tr>
    <td><code>background: red;</code></td>
    <td style="background:#ff0000;width:40px;color:white;">#ff0000</td>
  </tr>
  <tr>
    <td><code>background: darken(red, 10%);</code></td>
    <td style="background:#cc0000;width:40px;color:white;">#cc0000</td>
  </tr>
  <tr>
    <td><code>background: darken(red, 20%);</code></td>
    <td style="background:#990000;width:40px;color:white;">#990000</td>
  </tr>
  <tr>
    <td><code>background: darken(red, 30%);</code></td>
    <td style="background:#660000;width:40px;color:white">#660000</td>
  </tr>
</table>

### 彩度を調整する saturate() / desaturate()

**`saturate`** 関数、**`desaturate`** 関数を使用すると、ある色の彩度をパーセンテージ指定で上げたり下げたりすることができます。

<table>
  <tr>
    <th>SCSS コード内での記述</th><th>変換後</th>
  </tr>
  <tr>
    <td><code>background: saturate(royalblue, 30%);</code></td>
    <td style="background:#235aff;width:40px;color:white;">#235aff</td>
  </tr>
  <tr>
    <td><code>background: saturate(royalblue, 20%);</code></td>
    <td style="background:#2b5ef7;width:40px;color:white;">#2b5ef7</td>
  </tr>
  <tr>
    <td><code>background: saturate(royalblue, 10%);</code></td>
    <td style="background:#3664ec;width:40px;color:white;">#3664ec</td>
  </tr>
  <tr>
    <td><code>background: royalblue;</code></td>
    <td style="background:#4169e1;width:40px;color:white;">#4169e1</td>
  </tr>
  <tr>
    <td><code>background: desaturate(royalblue, 10%);</code></td>
    <td style="background:#4c6fd6;width:40px;color:white;">#4c6fd6</td>
  </tr>
  <tr>
    <td><code>background: desaturate(royalblue, 20%);</code></td>
    <td style="background:#5774cb;width:40px;color:white;">#5774cb</td>
  </tr>
  <tr>
    <td><code>background: desaturate(royalblue, 30%);</code></td>
    <td style="background:#627ac0;width:40px;color:white;">#627ac0</td>
  </tr>
</table>

### 2つの色の中間色を作成する mix()

**`mix`** 関数を使用すると、指定した2色の中間色を作成することができます。
第3引数（`$weight` パラメータ）を使って、1色目の比率を指定することもできます（デフォルトは 50%）。

<table>
  <tr>
    <th>SCSS コード内での記述</th><th>変換後</th>
  </tr>
  <tr>
    <td><code>background: mix(white, black);</code></td>
    <td style="background:gray;width:40px;color:white;">gray</td>
  </tr>
  <tr>
    <td><code>background: mix(green, yellow);</code></td>
    <td style="background:#80c000;width:40px;color:white;">#80c000</td>
  </tr>
  <tr>
    <td><code>background: mix(white, black, 75%);</code></td>
    <td style="background:#bfbfbf;width:40px;color:white;">#bfbfbf</td>
  </tr>
  <tr>
    <td><code>background: mix(white, black, 25%);</code></td>
    <td style="background:#404040;width:40px;color:white;">#404040</td>
  </tr>
</table>

