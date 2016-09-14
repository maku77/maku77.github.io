---
title: Best Practices メモ
layout: category-index
---

設計ノウハウ
====
* [不具合修正に対する向き合い方](attitude-to-defect.html)
* [動的品質よりも静的品質の向上に力を入れる](static-and-dynamic-quality.html)
* [プログラム内のコメントの書き方 (Javadoc ドキュメンテーションコメントの書き方）](how-to-write-comment.html)
* [アプリ内の名前空間（Java のパッケージ階層）に迷った時のヒント](namespace-in-app.html)
* [PMD が警告するゴッドクラス (God Class) とは](god-class.html)
* [可変オブジェクトのメンバ参照を返さない](avoid-returning-mutable-reference.html)
* [プロジェクト内での null の扱い方をルール化する](clarify-how-to-use-null.html)
* [時制や単数形・複数形を考慮して命名する](tense-and-plural.html)
* [肯定形で表現する](prefer-positive-sentence.html)
* [単位を明確にする](clarify-unit.html)
* [設定値の伝搬タイミングを意識する](timing-of-propagation.html)
* [型変換用メソッドは受け取り側クラスに作る](api-convert-type.html)
* [静的チェックのレベルはプロジェクト初期に厳しくする](strict-analysis-in-the-beginning.html)
* [リソースは finally ブロックで閉じる](finally-close.html)

デザインパターン
====
* [Visitor パターン](dp-visitor-pattern.html)

テスト
====
* [テストピラミッドを意識してテストの自動化を進める](test-pyramid.html)
* [単体テスト、結合テスト、システムテスト、受入テストの関係を理解する](test-relation.html)

アジャイルプラクティス
====
* [無駄な汎用性ではなくシンプルな設計を (YAGNI)](simple-design.html)

設計ドキュメント／設計ツール
====
* [Enterprise Architect の図をシンプルにして Power Point に貼り付ける](tool/ea-to-powerpoint.html)
* [テキストからシーケンス図を作成するツール](tool-sdedit.html)

ブックレビュー／読書メモ
====
* [『仕事は楽しいかね』デイル・ドーテン](book/shigo-tano1.html)
* [『仕事は楽しいかね２』デイル・ドーテン](book/shigo-tano2.html)
* [『ピープルウェア』トム・デマルコ](book/peopleware.html)
* [『レバレッジ・シンキング』本田直之](book/reverage-thinking.html)
* [『ピーター流外国語習得術』ピーター・フランクル](book/peter.html)
* [『わたしの外国語学習法』ロンブ・カトー](book/kato-lomb.html)

Jenkins
====
* [Jenkins CLI を使ってコマンドラインから Jenkins を操作する](jenkins/cli.html)
* [Groovy スクリプトで Jenkins サーバを制御する](jenkins/cli-groovy.html)
* [Groovy スクリプトで Jenkins 上のすべての Job を制御する](jenkins/handle-jobs.html)
* [Groovy スクリプトで Jenkins 上のすべてのスレーブを制御する](jenkins/handle-slaves.html)
* [お試しインスタンスとして Jenkins サーバを起動する](jenkins/trial-server.html)
* [Jenkins サーバの設定ファイルの場所](jenkins/settings-dir.html)

XML
====
* [意外と知られていない XML 記述のルール](xml/xml-rules.html)
* [XML の名前空間](xml/namespace.html)
* 言語別 XML のパース: [Python](/python/#html/xml-のパース) / [Ruby](/ruby/#xml)

言語別の構文
====

|      | Bash | PHP  | Python | Ruby | Perl |
| ---- | ---- | ---- | ------ | ---- | ---- |
| if-else | [Bash](/shell/syntax/if.html) | [PHP](/php/syntax/if.html) | [Python](/python/syntax/if.html)  |  |  |
| switch-case |  |  | [Python](/python/syntax/switch.html) |  |  |
| ループ |  | [PHP](/php/syntax/loop.html) |  | [Ruby](/ruby/syntax-loop.html) |  |
| クラス |  | [PHP](/php/syntax/class.html) | [Python](/python/syntax/class.html) |  |  |
| 文字列リテラル |  |  |  [Python](/python/syntax/string-literal.html) |  |  |
| ヒアドキュメント | | [PHP](/php/syntax/here-document.html) | [Python](/python/syntax/here-document.html) | [Ruby](/ruby/here-document.html) | [Perl](/perl/string/here-document.html) |
| 正規表現 |  |  | [Python](/python/regexp.html) |  |  |

ツール
====
* [CPD でコードクローンを発見する](tool/detect-code-clones.html)
* [TFTP の使い方（TFTP によるファイル転送）](tool/tftp.html)
* [Windows (MinGW) で GLUT を使用する](tool/glut-in-windows.html)
* [Tera Term のマクロで特定の文字列を検出して処理を実行する](tool/teraterm-detect-pattern.html)

パッケージ管理
----
* [パッケージ管理ツールいろいろ](common-package-management.html)
* [Python のパッケージ管理ツール (pip)](/python/pip.html)

ネットワーク
====
* [IP によるインターネットワーキングの基礎（昔々2000年くらいに書いた記事）](./ip-networking/)

ソフトウェア開発参考リンク
====
* [Introduction to Software Engineering/Quality/Metrics](https://en.wikibooks.org/wiki/Introduction_to_Software_Engineering/Quality/Metrics) いろんなソフトウェアメトリクス

