---
title: "Android アプリのパフォーマンス改善に使用できるツール"
url: "p/39cfimo/"
date: "2016-06-07"
tags: ["Android"]
aliases: /android/tools/performance-tools.html
---

systrace / perfetto: システム全体の負荷状況などを調べる
----

[systrace](https://developer.android.com/studio/profile/systrace.html) を使用すると、カーネルレベルでの CPU 負荷状況などを調べられるため、システム全体で重くなっている場合などの一次分析に使用できます。
DDMS ツール (monitor.bat) から任意のタイミングでプロファイル取得できます。

__（追記）__ Android 10 (Q) 以降は、より洗練された [Perfetto](https://perfetto.dev/) を使用してください。

- 参考: [Perfetto でシステム全体のパフォーマンスを計測する](/p/ehu5eox/)


traceview: アプリ内のメソッド呼び出し数などを調べる
----

[traceview](https://developer.android.com/studio/profile/traceview.html) を使用すると、特定のアプリ内のメソッド単位のプロファイル情報を取得することができます。
メソッド呼び出し回数が想定よりも多くなっているとか、処理に時間がかかりすぎているメソッドなどを発見できます。

DDMS ツールから任意のタイミングでプロファイル取得できます。あるいは、コードに開始／終了タイミングを明示することで、ピンポイントで特定の区間のプロファイルを取得することもできます。


Show GPU overdraw: 同じ領域を何度も描画しないか調べる
----

開発者オプションで [Show GPU overdraw](https://developer.android.com/studio/profile/dev-options-overdraw.html) の機能を有効にすると、画面上に重ねて描画される領域があったときに、その領域が描画回数に応じた色で矩形表示されるようになります。
例えば、赤色で矩形表示された場合は、その領域が 3 回以上重複して描画されていることを示しており、プログラム内の描画シーケンスを見直すべきだと分かります。

Show GPU overdraw の機能は、Android の開発者オプションから有効にできます (`Settings` → `Developer options` → `Drawing` → `Show GPU overdraw`)。


Profile GPU rendering: 60FPS で描画できている調べる
----

開発者オプションで [Profile GPU rendering](https://developer.android.com/studio/profile/dev-options-rendering.html) の機能を有効にすると、描画フレームごとに UI スレッドでかかった時間を画面上にバー表示することができます。
緑色のラインは 16.6ms を示しており、このラインを超えるということは 60 FPS で描画できていないということです。メインスレッド上で描画以外の処理を行っていないか見直す必要があります。

Profile GPU rendering の機能は、Android の開発者オプションから有効にできます (`Settings` → `Developer options` → `Monitoring` → `Profile GPU rendering`)。


StrictMode: メインスレッドでの I/O アクセスなどを検出する
----

メインスレッドの処理時間は、UI レスポンスに直接影響を与えるため、I/O アクセスなどの短時間に完了することが保証されない処理はメインスレッド上で実行すべきではありません。
Android には、このようなメインスレッド上で実行すべきではない処理を検出するための [StrictMode（厳格モード）](https://developer.android.com/reference/android/os/StrictMode.html) が用意されています。

StrictMode の機能は、Android の開発者オプションから有効にできます (`Settings` → `Developer options` → `Monitoring` → `Strict mode enabled`)。
StrictMode は、このように Android の設定から有効にすることもできますし、アプリのコード上で有効にすることもできます。
コード上で StrictMode を有効にすることで、より柔軟な検出方法を設定することができます。
例えば、問題のある処理 (policy violation) が実行されたときに、LogCat のログ上にその呼び出し情報（コールスタックなど）を表示したりできます。
本格的にパフォーマンス改善を進めるときには、このログ出力を頼りにすれば、ぬるぬるサクサクなアプリケーションを作成することができます。

