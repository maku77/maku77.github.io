---
title: Swt - AWT と Swing と SWT と JFace の違い
date: "2010-08-10"
---

AWT (Abstract Window Toolkit)
----

- ウィジェットはプラットフォームネイティブなウィジェットを直接利用する。
- 高速だが、すべてのプラットフォームで共通なウィジェットを提供する必要があるため、すべてのプラットフォームで利用できるウィジェットしか使えない。

Swing
----

- ウィジェットが Java で実装されている。ウィンドウの描画なども Java で行うため、遅い。

SWT (Standard Widget Tookkit)
----

- Eclipse で利用されている Gui Toolkit。
- AWT と Swing のよいとこ取りのような思想で、ネイティブウィジェットが使用できる場合はそれを使い、使用できない場合は Java による実装を使う。
- 内部で JNI 経由でネイティブ実装を利用するため、SWT を利用して作成したアプリの実行時には、ネイティブの共有ライブラリが必要。

JFace
----

- SWT をより高度に使いたいときに利用する。
- SWT を Windows API とすると、JFace は MFC のようなもの。
- JFace 自体は Java だけで実装されている。
- JFace を利用することで、View と Model を分離したコードを記述できる。

