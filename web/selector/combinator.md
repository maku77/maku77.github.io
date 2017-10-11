---
title: "結合子を使用して構造に応じてスタイルシートを適用する"
created: 2017-10-09
description: "CSS のセレクタで結合子を利用すると、HTML 要素の構造に応じた要素の選択を行うことができます。"
---

４種類の結合子
----

結合子 (combinator) には下記のような種類があります。

| セレクタ名 | 記述方法 | 選択する要素 |
| ---- | :----: | ---- |
| 子孫セレクタ (descendant combinator) | A B | A 要素より下の階層にある B 要素 |
| 子セレクタ（直下セレクタ）(child combinator) | A &gt; B | A 要素の１階層下の B 要素 |
| 隣接兄弟セレクタ（隣接セレクタ）(adjacent sibling combinator) | A + B | A 要素の直後の同階層の B 要素 |
| 一般兄弟セレクタ（間接セレクタ）(general sibling selectors) | A ~ B | A 要素より後ろにある同階層の B 要素 |


子孫セレクタ (A B)
----

~~~ css
main p {
  color: red;
}
~~~

とすると、`main` 要素よりも下の階層にある全ての `p` 要素にスタイルを適用します。

#### HTML サンプル

~~~ html
<main>
  <p>一階層目</p>
  <div>
    <p>二階層目</p>
    <div>
      <p>三階層目</p>
    </div>
  </div>
</main>
<p>同一階層</p>
~~~

#### 表示サンプル

<div class="htmlSample">
  <p style="color:red;">一階層目</p>
  <div>
    <p style="color:red;">二階層目</p>
    <div>
      <p style="color:red;">三階層目</p>
    </div>
  </div>
  <p>同一階層</p>
</div>


子セレクタ（直下セレクタ）(A &gt; B)
----

~~~ css
main > p {
  color: red;
}
~~~

とすると、`main` 要素の１階層下に配置されたすべての `p` 要素にスタイルを適用します。

#### HTML サンプル

~~~ html
<main>
  <p>一階層目</p>
  <div>
    <p>二階層目</p>
  </div>
  <p>一階層目</p>
  <div>
    <p>二階層目</p>
  </div>
</main>
~~~

#### 表示サンプル

<div class="htmlSample">
  <p style="color:red;">一階層目</p>
  <div>
    <p>二階層目</p>
  </div>
  <p style="color:red;">一階層目</p>
  <div>
    <p>二階層目</p>
  </div>
</div>


隣接兄弟セレクタ（隣接セレクタ）(A + B)
----

~~~ css
h3 + p {
  color: red;
}
~~~

とすると、`h3` 要素と同じ階層にある直後の `p` 要素にスタイルを適用します。

#### HTML サンプル

~~~ html
<h3>ヘッダ</h3>
<p>内容</p>
<p>内容</p>
<p>内容</p>
<h3>ヘッダ</h3>
<p>内容</p>
<p>内容</p>
<p>内容</p>
~~~

#### 表示サンプル

<div class="htmlSample">
  <h3>ヘッダ</h3>
  <p style="color:red;">内容</p>
  <p>内容</p>
  <p>内容</p>
  <h3>ヘッダ</h3>
  <p style="color:red;">内容</p>
  <p>内容</p>
  <p>内容</p>
</div>


兄弟セレクタ（間接セレクタ）(A ~ B)
----

~~~ css
h3 ~ p {
  color: red;
}
~~~

とすると、`h3` 要素の後ろにあり、かつ同じ階層にある `p` 要素にスタイルを適用します。

#### HTML サンプル

~~~ html
<h2>ヘッダ大</h2>
<p>内容</p>
<p>内容</p>
<h3>ヘッダ中</h3>
<p>内容</p>
<p>内容</p>
<h4>ヘッダ小</h4>
<p>内容</p>
<p>内容</p>
~~~

#### 表示サンプル

<div class="htmlSample">
  <h2>ヘッダ大</h2>
  <p>内容</p>
  <p>内容</p>
  <h3>ヘッダ中</h3>
  <p style="color:red;">内容</p>
  <p style="color:red;">内容</p>
  <h4>ヘッダ小</h4>
  <p>内容</p>
  <p>内容</p>
</div>

