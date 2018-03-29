---
title: "Blender の基本操作まとめ"
date: "2018-02-26"
---

<style>
  .local-example {
    min-width: 80%;
  }
  .local-example img {
    height: 50px;
  }
  .local-example th {
    background: #333;
    color: white;
  }
</style>


まとめ中

3Dビュー／共通
----

### 視点（ビュー）
<table class="local-example">
  <tr>
    <td></td>
    <td><kbd>Numpad[-]</kbd> / <kbd>Numpad[+]</kbd> / マウスホイール</td>
    <td>ズームイン／アウト</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Shift</kbd>+中央ボタンドラッグ</td>
    <td>視点の平行移動</td>
  </tr>
  <tr>
    <td></td>
    <td>中央ボタンドラッグ</td>
    <td>視点の回転</td>
  </tr>
  <tr>
    <td><img src="view/view-camera.png"></td>
    <td><kbd>Numpad[0]</kbd></td>
    <td>カメラ視点</td>
  </tr>
  <tr>
    <td><img src="view/view-front.png"></td>
    <td><kbd>Numpad[1]</kbd></td><td>前</td>
  </tr>
  <tr>
    <td><img src="view/view-back.png"></td>
    <td><kbd>Ctrl-Numpad[1]</kbd></td><td>後ろ</td>
  </tr>
  <tr>
    <td><img src="view/view-right.png"></td>
    <td><kbd>Numpad[3]</kbd></td><td>右</td>
  </tr>
  <tr>
    <td><img src="view/view-left.png"></td>
    <td><kbd>Ctrl-Numpad[3]</kbd></td><td>左</td>
  </tr>
  <tr>
    <td><img src="view/view-top.png"></td>
    <td><kbd>Numpad7</kbd></td><td>上</td>
  </tr>
  <tr>
    <td><img src="view/view-bottom.png"></td>
    <td><kbd>Ctrl-Numpad[7]</kbd></td><td>下</td>
  </tr>
  <tr>
    <td><img src="view/view-persp-ortho.png"></td>
    <td><kbd>Numpad[5]</kbd></td>
    <td>透視投影 ↔︎ 平行投影</td>
  </tr>
  <tr>
    <td><img src="view/view-quad-view.png"></td>
    <td><kbd>Ctrl-Alt-Q</kbd></td>
    <td>四分割表示 (Quad View)</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Home</kbd></td>
    <td>全てのオブジェクトを表示するようにズーム (View All)</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Numpad[.]</kbd></td>
    <td>選択したオブジェクトにズーム ★ある個所にフォーカスして編集するときに便利</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>Shift-B</kbd> → 矩形選択</td>
    <td>矩形選択した部分にズーム</td>
  </tr>
  <tr>
    <td></td>
    <td><kbd>[`]</kbd>（バッククォート）</td>
    <td>全レイヤの表示／非表示</td>
  </tr>
</table>

### 選択
<table class="local-example">
  <tr>
    <td><img src="select-all.svg"></td>
    <td><kbd>A</kbd></td>
    <td>全てを選択／解除 ((De)select All)</td>
  </tr>
  <tr>
    <td><img src="select-inverse.svg"></td>
    <td><kbd>Ctrl-I</kbd></td>
    <td>選択の反転 (Inverse)</td>
  </tr>
  <tr>
    <td rowspan="2"><img src="select-click.svg"></td>
    <td>右クリック</td>
    <td>カーソル下の要素を選択</td>
  </tr>
  <tr>
    <td><kbd>Shift</kbd>+右クリック</td>
    <td>連続選択</td>
  </tr>
  <tr>
    <td rowspan="2"><img src="select-circle.svg"></td>
    <td><kbd>C</kbd> → クリック</td>
    <td>円内の要素を選択 (Circle Select)<br>（ホイールで円サイズ変更）</td>
  </tr>
  <tr>
    <td><kbd>C</kbd> → <kbd>Shift</kbd>+クリック</td>
    <td>〃解除</td>
  </tr>
  <tr>
    <td rowspan="2"><img src="select-border.svg"></td>
    <td><kbd>B</kbd> → ドラッグ</td>
    <td>矩形で囲んで選択 (Border Select)</td>
  </tr>
  <tr>
    <td><kbd>B</kbd> → <kbd>Shift</kbd>+ドラッグ</td>
    <td>〃解除</td>
  </tr>
  <tr>
    <td rowspan="2"><img src="select-region.svg"></td>
    <td><kbd>Ctrl</kbd>+ドラッグ</td>
    <td>自由領域で囲んで選択</td>
  </tr>
  <tr>
    <td><kbd>Ctrl-Shift</kbd>+ドラッグ</td>
    <td>〃解除</td>
  </tr>
</table>

### 全般
<table class="local-example">
  <tr>
    <td><img src="manipulator-off.png"></td>
    <td><img src="manipulator-on.png"></td>
    <td><kbd>Ctrl-Space</kbd></td>
    <td>マニピュレーターの ON/OFF</td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td><kbd>Tab</kbd></td>
    <td>オブジェクトモード ⇔ 編集モードの切り替え</td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td><kbd>Ctrl-Tab</kbd></td>
    <td>オブジェクトモード ⇔ ウェイトペイントモードの切り替え</td>
  </tr>
</table>


3Dビュー／オブジェクトモード
----
### オブジェクト編集
<table class="local-example">
  <tr>
    <td></td>
    <td></td>
    <td><kbd>U</kbd></td>
    <td>UV マッピングメニューを表示 (UV Mapping)</td>
  </tr>
  <tr>
    <td><kbd>G</kbd></td>
    <td>移動 (Grab/Move)</td>
  </tr>
  <tr>
    <td><kbd>R</kbd></td>
    <td>回転 (Rotate)</td>
  </tr>
  <tr>
    <td><kbd>S</kbd></td>
    <td>拡大縮小 (Scale)</td>
  </tr>
  <tr>
    <td><kbd>X</kbd></td>
    <td>削除 (Delete)</td>
  </tr>
  <tr>
    <td><kbd>Shift</kbd>+<kbd>D</kbd></td>
    <td>複製 (Duplicate)</td>
  </tr>
</table>


### プレビューモード (Viewport Shading) の切り替え
<table class="local-example">
  <tr>
    <td><img src="preview-solid.png"></td>
    <td><img src="preview-rendered.png"></td>
    <td><kbd>Shift-Z</kbd></td>
    <td>レンダー表示</td>
  </tr>
  <tr>
    <td><img src="preview-solid.png"></td>
    <td><img src="preview-material.png"></td>
    <td>（なし）</td>
    <td>マテリアル表示</td>
  </tr>
  <tr>
    <td><img src="preview-solid.png"></td>
    <td><img src="preview-texture.png"></td>
    <td><kbd>Alt-Z</kbd></td>
    <td>テクスチャー表示</td>
  </tr>
  <tr>
    <td><img src="preview-solid.png"></td>
    <td><img src="preview-wireframe.png"></td>
    <td><kbd>Z</kbd></td>
    <td>ワイヤーフレーム表示</td>
  </tr>
  <tr>
    <td><img src="preview-solid.png"></td>
    <td><img src="preview-bounding-box.png"></td>
    <td>（なし）</td>
    <td>バウンディングボックス表示</td>
  </tr>
</table>

### その他
<table class="local-example">
</table>


3Dビュー／編集モード (Edit Mode)
----
### メニュー
<table class="local-example">
  <tr>
    <td><kbd>Ctrl-E</kbd></td>
    <td>辺メニューを表示 (Edges Menu)</td>
  </tr>
</table>

### メッシュ
<table class="local-example">
  <tr>
    <td><img src="mesh-make-face.svg"></td>
    <td><kbd>F</kbd></td>
    <td>面の作成 (Make Face)</td>
  </tr>
  <tr>
    <td><img src="mesh-insert-faces.svg"></td>
    <td><kbd>I</kbd></td>
    <td>面を差し込む (Insert Faces)</td>
  </tr>
  <tr>
    <td><img src="mesh-extrude-region.svg"></td>
    <td><kbd>E</kbd></td>
    <td>面の押し出し (Insert Faces)</td>
  </tr>
  <tr>
    <td><img src="mesh-loopcut.svg"></td>
    <td><kbd>Ctrl-R</kbd></td>
    <td>ループカットとスライド (Loopcut and Slide)</td>
  </tr>
  <tr>
    <td><img src="mesh-knife.svg"></td>
    <td><kbd>K</kbd> → クリック繰り返し</td>
    <td>ナイフ (Knife)</td>
  </tr>
</table>

### 頂点の結合いろいろ
<table class="local-example">
<tr>
  <th>操作方法</th>
  <th>元のメッシュ形状</th>
  <th>操作後のメッシュ形状</th>
</tr>
<tr>
  <td>頂点を選択 → <kbd>Alt+M</kbd> → 中心に</td>
  <td><img src="merge.png" /></td>
  <td><img src="merge-center.png" /></td>
</tr>
<tr>
  <td>頂点を選択 → <kbd>Alt+M</kbd> → 最初に選択した頂点に</td>
  <td><img src="merge.png" /></td>
  <td><img src="merge-first.png" /></td>
</tr>
<tr>
  <td>頂点を選択 → <kbd>Alt+M</kbd> → 最後に選択した頂点に</td>
  <td><img src="merge.png" /></td>
  <td><img src="merge-last.png" /></td>
</tr>
<tr>
  <td>頂点を選択 → <kbd>Alt+M</kbd> → カーソル位置に</td>
  <td><img src="merge.png" /></td>
  <td><img src="merge-cursor.png" /></td>
</tr>
<tr>
  <td>頂点を選択 → <kbd>Alt+M</kbd> → 束ねる</td>
  <td><img src="merge.png" /></td>
  <td><img src="merge-center.png" /></td>
</tr>
</table>

