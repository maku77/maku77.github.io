---
title: "SWT - SWT/JFace における色 (Color) 情報の扱い方"
date: "2010-10-17"
---

SWT Color Model
----

- [Eclipse Corner Article - SWT Color Model](http://www.eclipse.org/articles/Article-SWT-Color-Model/swt-color-model.htm)


RGB 指定で Color オブジェクトを作る
----

SWT/JFace で扱われる色は、`org.eclipse.swt.graphics.Color` オブジェクトとして表現されます。
Color オブジェクトは以下のコンストラクタを使って生成します。
RGB の各値は 0 ～ 255 の値を指定します。

~~~ java
Color(Device device, int red, int green, int blue)
Color(Device device, RGB rgb)
~~~

SWT の Color オブジェクトにはネイティブリソースが割り当てられるので、使用し終わったら `dispose()` メソッドで破棄する必要があります。

~~~ java
Color color = new Color(getDevice(), 0xFF, 0x80, 0x80);
...
color.dispose();
~~~


システムカラーを取得する
----

Display オブジェクトの `getSystemColor()` メソッドを使用すると、システムカラーに対応する Color オブジェクトを取得することができます。
Color クラスのコンストラクタで生成した場合と違い、`getSystemColor()` で取得した Color オブジェクトは、`dispose()` で破棄する必要はありません。

~~~
Color org.eclipse.swt.widgets.Display#getSystemColor(int id)
~~~

`id` パラメータには、`SWT.COLOR_RED` のような、システムカラーに対応する定数値を指定します。

#### 使用例

~~~ java
Color red = getDisplay().getSystemColor(SWT.COLOR_RED);
~~~

システムカラーは、以下の 16 色を使用できます。

- `COLOR_BLACK`
- `COLOR_BLUE`
- `COLOR_CYAN`
- `COLOR_DARK_BLUE`
- `COLOR_DARK_CYAN`
- `COLOR_DARK_GRAY`
- `COLOR_DARK_GREEN`
- `COLOR_DARK_MAGENTA`
- `COLOR_DARK_RED`
- `COLOR_DARK_YELLOW`
- `COLOR_GRAY`
- `COLOR_GREEN`
- `COLOR_MAGENTA`
- `COLOR_RED`
- `COLOR_WHITE`
- `COLOR_YELLOW`

