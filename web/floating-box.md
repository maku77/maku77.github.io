---
title: "フローティングボックスを利用したレイアウトを理解する"
date: "2015-10-25"
---

float プロパティにより後続要素を回りこませる
====

スタイルシートの **float プロパティ**には下記の値を設定することができます。

```css
float: none;   /* デフォルト */
float: left;   /* フローティングボックスを構成して左端に配置 */
float: right;  /* フローティングボックスを構成して右端に配置 */
```

float プロパティに left を設定すると、その要素は左端に表示され、後続の要素はその反対側（この場合は右側）に回り込んで表示されるようになります。

#### float を指定しない場合

<div class="htmlSample">
    <div style="border: solid 1px gray; height:60px; width:60px;">
    float: none;
    </div>
    <div>
    デフォルトでは、ブロック要素の後続の要素は別の段落に表示さます。
    </div>
</div>

#### float: left を指定した場合

<div class="htmlSample">
    <div style="border: solid 1px gray; height:60px; width:60px; float: left">
        float: left;
    </div>
    <div>
        float プロパティに<br>
        left や right<br>
        を指定すると、後続の要素は、<br>
        回り込んで表示されるように<br>
        なります。
    </div>
    <div style="clear:both"></div>
</div>


フローティングボックスの形成
====

float プロパティに left や right を設定した場合、その要素は**フローティングボックス**を形成します。
後続の要素は、フローティングボックスが存在しないとして配置されるので、横幅いっぱいのサイズが確保されます。
下記の例は、後続要素に緑色の背景色を付けたサンプルです。

<div class="htmlSample">
    <div style="border: solid 1px gray; height:60px; width:60px; float: left">
        float: left;
    </div>
    <div style="background: #9f9">
        フローティングボックスは<br>
        後続要素から見ると<br>
        あたかもそこには存在しない<br>
        かのように配置されます。
    </div>
    <div style="clear:both"></div>
</div>

後続要素の横幅は、フローティングボックスに重なる形で画面いっぱいのサイズになっていることが分かります。
この場合でも、実際に表示されるテキストは重ならないように考慮して描画されることになっています。


フローティングボックスの回りこみの解除
====

フローティングボックスの後続の要素は、フローティングボックスに対して回りこむように配置されていきます。
その回りこみを解除して、新しい段落に表示したい場合は、**clear プロパティ**を指定します。

```css
clear: none;   /* 回りこみを解除しない（デフォルト） */
clear: left;   /* 左側のフローティングボックスへの回りこみを解除 */
clear: right;  /* 右側のフローティングボックスへの回りこみを解除 */
clear: both;   /* 全てのフローティングボックスへの回りこみを解除 */
```

#### clear を指定しない場合

<div class="htmlSample">
    <div style="border: solid 1px gray; height:60px; width:60px; float: left">
        float: left;
    </div>
    <div>後続の 1 つ目の要素</div>
    <div>後続の 2 つ目の要素</div>
    <div style="clear:both"></div>
</div>

#### clear: left; を指定した場合

<div class="htmlSample">
    <div style="border: solid 1px gray; height:60px; width:60px; float: left">
        float: left;
    </div>
    <div>後続の 1 つ目の要素</div>
    <div style="clear:left;">後続の 2 つ目の要素 (clear: left)</div>
</div>

CSS の float プロパティは、この clear 指定を後続の要素に対して指定する必要があるため、レイアウトが難しくなってしまうという欠点があります。
段組を構成したい場合は、CSS3 のグリッドレイアウトやマルチカラムの仕組みを使うことで、よりシンプルにレイアウトを作成することができます。


連続するフローティングボックス
====

float:left を連続して配置
----

float プロパティを left に設定した要素を連続して配置すると、それら全てのフローティングボックスを回りこむように後続の要素が配置されます。

<div class="htmlSample">
    <div style="border: solid 1px gray; height:60px; width:60px; float: left">
        float: left;
    </div>
    <div style="border: solid 1px gray; height:60px; width:60px; float: left">
        float: left;
    </div>
    <div>
        連続するフローティングボックス<br>
        が配置されていると、<br>
        後続の要素は、<br>
        それらすべてを回りこむように配置されます。
    </div>
    <div style="clear:both"></div>
</div>


float:left と float:right を配置
----

float プロパティを left に設定した要素と、right に設定した要素を連続して配置すると、それぞれ左側と右側にフローティングボックスが配置されます。

<div class="htmlSample">
    <div style="border: solid 1px gray; height:60px; width:60px; float: left">
        float: left;
    </div>
    <div style="border: solid 1px gray; height:60px; width:60px; float: right">
        float: right;
    </div>
    <div>
        左右にそれぞれフローティングボックスを配置することもできます。
    </div>
    <div style="clear:both"></div>
</div>

