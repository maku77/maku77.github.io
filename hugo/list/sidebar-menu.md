---
title: "サイドバー用のページツリーを表示する（現在表示しているページを考慮した階層表示）"
date: "2018-01-23"
description: "サイドバーメニューに、サイトの階層構造に応じたリンクを表示しておくと、サイト内の様々なページに簡単に移動できるようになります。"
---

サイドバーでのページツリー表示のイメージ
----

サイト全体のページ一覧をツリー構造で表示する方法は、下記のページで紹介しています。

* [サイト内の全ページの一覧をセクションの階層構造に従って表示する](page-hierarchy.html)

しかし、上記のページで説明している方法でツリーを表示すると、すべてのページが展開された状態で表示されてしまうため、サイドバーに表示するツリーとしてはちょっと情報量が多すぎます。
ここでは、もう少しコンパクトに表示されるように、現在のページの上位ノードだけを展開したツリーを表示するようにしてみます。

イメージとしては次のような感じで、表示中のページ（ここでは page1）の上位のセクションだけを展開したツリーを表示することを考えます。

~~~
- sec1/
- sec2/
    - sec2-1/
    - sec2-2/
        - page1 （表示中のページ）
        - page2
        - sec2-2-1/
        - sec2-2-2/
    - sec2-3/
- sec3/
- sec4/
~~~

このようなツリーをサイドバーなどに表示しておくことで、ユーザが今、サイト全体でどの位置の記事を読んでいるかを簡単に把握できるようになります（パンくずリストなども同様の効果がありますが、ツリー表示の方が、より全体を把握しやすいといえます）。


展開すべきノードを知る
----

自分自身のページが所属するセクションだけを展開したツリーを表示するには、テンプレートコード内で、セクションの親子関係を（先祖まで含めて）把握する必要があります。
そのために、Page オブジェクトの下記のようなメソッドを利用することができます（参考: [Hugo - Section Variables and Methods](https://gohugo.io/variables/page/#section-variables-and-methods)）。

.InSection $anotherPage
: $anotherPage が同じセクションであれば true<br>(whether the given page is in the current section. Note that this will always return false for pages that are not either regular, home or section pages.)

.IsAncestor $anotherPage
: そのページが $anotherPage の先祖ページであれば true<br>(whether the current page is an ancestor of the given page. Note that this method is not relevant for taxonomy lists and taxonomy terms pages.)

.IsDescendant $anotherPage
: そのページが $anotherPage の子孫ページであれば true<br>(whether the current page is a descendant of the given page. Note that this method is not relevant for taxonomy lists and taxonomy terms pages.)

<div class="note">
Hugo 本家のマニュアルページでは、<code>.InSection</code> や <code>.IsAncestor</code>、<code>.IsDescendant</code> メソッドは、<b>Section の Methods</b> として記述されていますが、通常ページを含む Page オブジェクトのメソッドとして参照することができます。
</div>

なかなか分かりにくいですね。このような場合は実際にテストしてみるのが一番です。
これらのメソッドがどういう振る舞いをするのかを明確にするため、次のようなディレクトリ構成（セクション構成）のダミーサイトでテストしてみます。

~~~
/_index.md
/page.md
    /sec1/_index.md
    /sec1/page.md
        /sec1/sec1-1/_index.md
        /sec1/sec1-1/page1.md
        /sec1/sec1-1/page2.md
            /sec1/sec1-1/sec1-1-1/_index.md
            /sec1/sec1-1/sec1-1-1/page.md
        /sec1/sec1-2/_index.md
        /sec1/sec1-2/page1.md
        /sec1/sec1-2/page2.md
~~~

次の表は、各ページの Page オブジェクト (`$p1`) の `.InSection`、`.IsAncestor`、`.IsDescendant` メソッドに、様々な Page オブジェクト (`$p2`) を渡したときにどう判定されるかの一覧です（この表を作成するために、[こちらのホームページテンプレート (/layouts/index.html)](sidebar-menu-index.txt) を使用しています）。

### デモ（<a target="_blank" href="sidebar-menu-demo.html">別ウィンドウで開く</a>）
<iframe class="maku-htmlDemo" src="sidebar-menu-demo.html"></iframe>

実際に動作を確認してみると、実際には下記のような振る舞いをするようですね（Hugo 0.32.3 で確認）。
ホームページに関しては直感と異なる振る舞いをするようなので要注意です。

`$p1.InSection $p2`
: $p1 と $p2 が同一セクションに所属していれば true。それ以外は false。

`$p1.IsAncestor $p2`
: $p1 が $p2 よりも上位にあるページやセクションであれば true。$p1 がカレントセクションの場合も true。ただし、$1 がホームページの場合、$1＝$2＝ホームページのときのみ true。$1 がルート階層にある通常ページの場合は必ず false。それ以外は false。

`$p1.IsDescendant $p2`
: $p1 がセクションの場合、$p2 がそのセクション以下のページ or セクションの場合 true。$p1 が通常ページの場合、$p2 が $p1 の所属するセクションより下の階層のページ or セクションの場合 true。ただし、$p1 がホームページの場合、$p1＝$p2＝ホームページのとき or $p2 がルート階層の通常ページのときのみ true。$p1 がルート階層にある通常ページの場合は必ず false。それ以外は false。

非常にややこしいですが、**これらのメソッドは、セクションページのみで使用する（ホームページの場合のみ特殊処理する）**ようにすれば比較的わかりやすいコードを記述できると思います。
特に、サイドバーなどに表示するページツリーを生成するときは、**`.IsAncestor` が true になるセクションだけを、さらに深く辿っていく**ようにすれば、カレントページの上位セクションのみを開いたページツリーを生成することができます。


サイドバー用のページツリーを作成する
----

下記のパーシャルテンプレートは、現在表示中のページよりも上位のセクションを展開して、ページツリーを表示します。

#### /layouts/partials/nav-tree.html

~~~
<h3>メニュー</h3>
{{ "{{" }}- template "nav-tree-internal" (dict "section" .Site.Home "current" .) }}

{{ "{{" }}- define "nav-tree-internal" }}
  {{ "{{" }}- $section := .section }}{{ "{{" }}/* 今回処理するセクション */}}
  {{ "{{" }}- $current := .current }}{{ "{{" }}/* 現在表示中のページ */}}

  <ul>
    {{ "{{" }}- /* セクション直下のセクションページをループ表示 */}}
    {{ "{{" }}- range $section.Sections }}
      <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a> {{ "{{" }} if (eq . $current) }}★{{ "{{" }} end }}
      {{ "{{" }}- if .IsAncestor $current }}
        {{ "{{" }}- /* 開いているページよりも上位のセクションであればさらに辿る */}}
        {{ "{{" }}- template "nav-tree-internal" (dict "section" . "current" $current) }}
      {{ "{{" }}- end }}
    {{ "{{" }}- end }}

    {{ "{{" }}- /* セクション直下の通常ページをループ表示 */}}
    {{ "{{" }}- range cond $section.IsHome (where $section.Site.RegularPages "Section" "") $section.Pages }}
      <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a> {{ "{{" }} if (eq . $current) }}★{{ "{{" }} end }}
    {{ "{{" }}- end }}
  </ul>
{{ "{{" }}- end }}
~~~

例えば、サイドバー用のパーシャルテンプレートなどから次のような感じで使用します。

#### /layouts/partials/side-bar.html

~~~
{{ "{{" }} partial "nav-tree" . }}
~~~

すると、次のような感じのツリーメニューが表示されます。
現在表示中のページには ★ マークが表示され、それよりも上位のセクションだけが展開されて表示されます。

~~~
メニュー

- sec1/
    - sec1-1/
        - sec1-1-1/
        - page1.html ★
        - page2.html
    - sec1-2/
    - page.html
- page.html
~~~

ちなみに、ここでは最上位のホームページへのリンクは表示しないようにしています（無駄に階層が１つ深くなってしまうため）。
トップページのリンクを表示したいときは、下記のように個別に表示すればよいでしょう。

~~~
<a href="{{ "{{" }} "/" | relURL }}">Home</a>
~~~


