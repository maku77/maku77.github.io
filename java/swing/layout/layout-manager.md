---
title: Swing - レイアウトマネージャの種類
created: 2011-01-09
---

Swing の主なレイアウトマネージャには下記のようなものがあります。

1. **BorderLayout**: 上下左右、中央の５カ所に配置する。JFrame のデフォルトレイアウト。
2. **BoxLayout**: 縦あるいは横に一列に配置する。
3. **CardLayout**: レイアウトを動的に切り替える。
4. **FlowLayout**: 横に順番に並べていき、収まらなくなったら次の行へ移動する。JPanel のデフォルトレイアウト。
5. **GridBagLayout**: セル分割してレイアウト。セルの結合や高さ、幅の指定も可能。
6. **GridLayout**: セルに分割してレイアウト。全てのセルは同サイズになる。
7. **GroupLayout**: コンポーネントを縦、あるいは横にグルーピングする。アラインメント処理（位置合わせ）などを行える。
8. **SpringLayout**: コンポーネントのサイズを比率で指定する。spring は「バネ」の意味の方。

上記の全てのクラスは、`java.awt.LayoutManager` インタフェースを実装しています。

SpringLayout は GUI ビルダアプリケーション用に開発されたレイアウトマネージャです。
一般のアプリケーションの実装では、できるだけ NetBeans IDE などのツールを使用してレイアウト作業を行うことが推奨されています。

- 参考: [A Visual Guide to Layout Managers](http://download.oracle.com/javase/tutorial/uiswing/layout/visual.html)
  - このサイトで、各レイアウトの表示イメージを確認できます。

