---
title: "オブジェクトを回転する (R)"
date: "2018-03-18"
---

マニピュレータを使って回転する方法
----

<figure>
  <img src="rotate.png" />
  <figcaption>
    <ol>
      <li>オブジェクトを選択（オブジェクトモードで右クリック）</li>
      <li>回転マニピュレータに切り替える（上図のピンク枠のアイコン）</li>
      <li>2つの白い円、あるいは、3色（赤緑青）の円をドラッグして回転<br>（回転中に、右クリック or <kbd>ESC</kbd> でキャンセル）</li>
    </ol>
  </figcaption>
</figure>

小さい白い円をドラッグすると、任意の方向にオブジェクトを回転させることができます。
大きい白い円をドラッグすると、現在の視点に対して垂直な方向にオブジェクトを回転させることができます。
赤、緑、青の3色の円をドラッグすると、それぞれ、x軸方向（赤）に垂直に、y軸方向（緑）に垂直に、z軸方向（青）に垂直にオブジェクトを回転させることができます。


ショートカットキーを使って回転する方法
----

1. オブジェクトを選択（オブジェクトモードで右クリック）
2. <kbd>R</kbd> キーを押す（Rotate の略）
3. マウスを動かしてオブジェクトを回転させる
4. クリックで角度を決定<br>（回転中に、右クリック or <kbd>ESC</kbd> でキャンセル）

<kbd>R</kbd> キーを押した瞬間に、現在の視点に対して垂直な方向に回転できるようになります。
マウスを動かしてオブジェクトを回転させている最中に、<kbd>X</kbd> キー、<kbd>Y</kbd> キー、<kbd>Z</kbd> キーを押すと、回転の軸を固定することができます（マニピュレータの3色の円をドラッグするのと同様）。

<div class="note">
より具体的には、オブジェクトの回転中に <kbd>X</kbd> を押すと、グローバルなx軸上で回転するようになります。続けてもう一度 <kbd>X</kbd> を押すと、ローカルなx軸上で回転するようになります。
</div>


マウスでの回転量を大きくする／小さくする
----

オブジェクトをマウスで回転させるとき、デフォルトでは、0.1 度単位で回転します。
<kbd>Ctrl</kbd> キーを押しながら回転させると、もう少し大きい 5 度単位での回転になります。
逆に、<kbd>Shift</kbd> キーを押しながら回転させると、回転の単位が細かくなり、微調整できるようになります。

このあたりの振る舞いの変化は、オブジェクトを移動させるときや (<kbd>G</kbd>)、拡大／縮小するとき (<kbd>S</kbd>) も同様です。


ショートカットキーで回転量を指定する
----

次のようにすると、ショートカットキーだけでオブジェクトを回転させることができます。

1. オブジェクトを選択状態にする
2. <kbd>R</kbd> キーで回転開始
3. <kbd>X</kbd> or <kbd>Y</kbd> or <kbd>Z</kbd> キーで回転の軸を指定する
4. 数字キーで回転角度を入力（90度回転させるなら <kbd>90</kbd> と入力）
5. <kbd>Enter</kbd> キーで決定

回転角度は、回転後のオブジェクトの角度ではなく、現在の状態からの回転量を指定することに注意してください。
回転角度にマイナスの値を入力することもできます。

例えば次のようにすると、正面を向いたモンキーに左を向かせることができます。

#### 例: Z軸で 90 度回転させる

~~~
R Z 90 Enter
~~~

次のようにすると、正面を向いたモンキーに上を向かせることができます。

#### 例: X軸で -90 度回転させる

~~~
R X -90 Enter
~~~

最初は、プラスの角度で回転させればいいのか、マイナスの角度で回転させればいいのか迷うかもしれません。
このようなときのために、マイナス (`-`) は数字の後で入力することができるようになっています。
つまり、上記の -90 度の回転は、次のようにも実行できます。

~~~
R X 90 - Enter
~~~

回転の結果はリアルタイムに確認できるので、まずはプラスの角度で回転させておいて、思った方と逆方向に回転してしまったら `-` を入力すればよいでしょう。
`-` を入力するたびに、回転の方向がトグルします。

