---
title: "ソフトウェアの静的品質と動的品質"
date: "2016-06-24"
---

ソフトウェアの品質を語るときは、動的品質の前に**静的品質を重視せよ**というお話です。


静的品質と動的品質
----

ソフトウェアの品質には、大きく分けて**静的品質**と**動的品質**があると言えます。

* **静的品質:** ソースコードそのものの品質
  * ソースコードの可読性は高いか？第三者が読んでも理解できるようになっているか？
  * メンテナンス性の高い設計になっているか？
  * 不具合の入りにくい設計（入れることが困難なアーキテクチャ）になっているか？
  * ドキュメンテーションコメントは簡潔かつ明確に記述されているか？
* **動的品質:** 実行時の品質
  * 要求された機能が正しく動作するか？
  * 実行時のパフォーマンスはよいか？ユーザビリティは高いか？
  * ユニットテストはパスしているか？

一般的に、ソフトウェア品質というと動的品質の方ばかりが注目されがちです。
なぜなら、多くの場合、ソフトウェアベンダの評価やプログラマ自身の評価が、納品したソフトウェアを実際に動作させたときの動的品質に基いて行われることが多いからです（不具合発生件数の少なさなど）。
ソースコード自体のメンテナンス性（静的品質）が低いということで評価が下げられることはあまりないのではないでしょうか？
ひどいケースになると、一度納品したソフトウェアのメンテナンスは、別のチームが引き継ぐことに決まっているからという理由で、将来のメンテナンス性を放棄したコーディングが行われることがあります。
リリースするソフトウェアが正しく動作することを考えてコーディングするのは当然ですが、プロの職業プログラマ（プロプログラマ？）であれば、将来的なメンテナンス性を意識したコーディングを行えなければいけません。

会社の立場から言えば、最終的に**ビジネスインパクトが大きくなるのは静的品質**の方です。
なぜならば、ソースコード自体の保守性が低いと、将来的に設計変更するときに莫大なコスト（人件費）がかかり、不具合が入り込む可能性も上がってくるからです。そして、これは関連プロジェクトが存続する限り、永遠に続くコストとして積みあがっていきます。
行く末は、誰もそのソースコードをメンテナンスできない状態になり、一からすべてを作り直すことになります。
直近のリリースでうまく動作することだけを考慮するのであれば、当期の人件費は下げられるかもしれません。
ぐちゃぐちゃなスパゲッティコードを組んで、とりあえず動くように継ぎはぎを繰り返すことで完成させればよいのですから。
でも、長期的な視点でビジネスを考えていくのであれば、本当はソースコード自体のメンテナンス性（静的品質）を向上させることにこそ力を入れるべきなのです。

緊急度と重要度のマトリックスに強引に当てはめると下記のようなイメージでしょうか。

| 　　 | 緊急度<br>「高」 | 緊急度<br>「低」 |
| ---- | :----: | :----: |
| 重要度<br>「高」 | 　 | **静的品質** |
| 重要度<br>「低」 | 動的品質 | 　 |

直近のリリースの重要度ももちろん高いと思いますが、ここでの重要度とは、あくまで長期的な視点でのビジネスインパクトが大きいという意味で捉えてください。


静的品質を高めるには
----

ソフトウェアの静的品質を高めていくには、現在作成されているソースコード自体の品質が高いかどうかを判断できなければいけません。
そのために活用できるのが静的解析 (static analysis) ツールに分類される下記のようなツールです。

* PMD
* Checkstyle
* FindBugs
* 各言語用 Lint
* Coverity（有料）
* Fortify（有料）

これらのツールはソースコード上の不適切な設計（可読性が悪い、保守性が悪い、効率が悪い、危険な設計など）をレポートしてくれますが、検出の得意・不得意がありますので、可能な限り多くのツールを使用して静的解析を実施しておくべきです。
これらの解析はソースコードを修正するたびに頻繁に実行する必要がありますので、１ステップで実行できるように自動化してください（Java であれば Gradle、Ruby であれば rake といったビルド自動化のためのツールを使用します）。
複数メンバで開発を行っている場合は、中央リポジトリにソースコードをコミットする前に、警告件数が増加していないかを必ず確認するようにルールを徹底します。
中央サーバにコミットされたソースコードに関しては、CI サーバ（Jenkins など）などで自動的に静的解析を実行するようにし、警告件数が増えた場合にはメールによる通知を行うようにしておきましょう。

**静的解析ツールを導入するのは早ければ早いほどよい**です。
プロジェクトの終盤で急に導入すると、何千件もの警告が一気に出て面食らってやる気がそがれる可能性があります。
できれば初期の段階から解析を実施するようにして、警告件数は常に 0 件近くをキープするようにしましょう。
特に下記のような**複雑度やコードサイズに関する警告は、早い段階から**つぶしておかないと、どんどん症状が悪化していきます。一度こういった複雑度に関する警告が出ている状態になってしまうと、それ以上に複雑度が上がった際に警告数の増加という形で気付くことができなくなってしまうからです。

* １ファイル、１クラスあたりの行数に関する警告（例: ExcessiveClassLength）
* １メソッドあたりの行数に関する警告（例: PMD の ExcessiveMethodLength）
* コードの複雑度に関する警告（例: PMD の CyclomaticComplexity、NPathComplexity）
* クラスの責務割り当てに関する警告（例: PMD の GodClass）

そして、会社などの組織でソフトウェアを開発している場合に最も重要なのは、こういった解析に基いて計算されたソフトウェアの**静的品質によって報酬の額を決める**ということです。
静的品質によるビジネスインパクトが大きいことを考えれば、これはまっとうな考え方です。
メンテナンス性の悪いコードをコミットすることは、工数的な負債だけを将来にまわして楽をするということですから、その時点で報酬を減額しなければつじつまが合いません。
間違っても、その負債を引き継いだメンバから報酬をカットするようなことがあってはいけません（その負債を解消するために工数を取られ、目に付く新しい機能を実装する時間がなくなってしまうため、まるで成果が出ていないかのように見えてしまうものです）。

ソフトウェアの動的品質は発生した不具合の数などで計測することができますが、その時点での静的品質に関しては、上記のような静的解析ツールを導入しないと計測しにくいものです。
ソフトウェアの品質を高めていくためにも、その品質を正当に評価するためにも、ソフトウェア開発に**静的解析ツールを導入するのは必須**です。
ソフトウェアの静的品質を上げることによって、開発の効率は上がります。そして、それは実行時の動的品質を上げることにもつながります。
ソフトウェアはソースコードから作られているということを思い出しましょう。ソースコードそのものの品質が重要です。


まとめ
----
ソフトウェア開発の効率化を進め、ビジネスをうまく回していくには、動的品質よりも静的品質を高めるという意識を持つことが必要です。
下記を実施しましょう。

* 静的解析ツールをできるだけ早い段階で導入する
* 静的解析の自動実行によりコード品質を監視する（常に 0 件程度を維持することが望ましい）
* 静的解析などによって求められた静的品質に応じて成果報酬を決める（実行時の品質が高いというだけで高評価を与えないこと）
