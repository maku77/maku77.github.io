---
title: "Rust のパニック (panic) の扱いについて"
url: "p/nfxwcc2/"
date: "2023-07-06"
tags: ["Rust"]
---

パニックとは
----

Rust のパニックはプログラムに不具合があるときに発生するもので、パニックが発生すると、通常はプログラムの動作を継続することができません。
パニックは発生しないようにコーディングすべきです。

- パニックを発生させる処理の例:
  - 数値のゼロ除算
  - 配列の範囲外アクセス
  - `Option` 変数の値が `None` なのに `unwrap` したとき
  - `Result` 変数の値が `Err` なのに `unwrap` したとき
  - `panic!` マクロを呼び出したとき

`panic!` マクロで意図的にパニックを発生させることができますが、プロダクトコードでは呼び出さないようにします（`println!` と同様の引数を渡すことができます）。

パニック発生時には、__unwind the stack（スタック巻き戻し）__ あるいは、__abort the process（プロセス強制終了）__ のいずれかの処理が実行されます。

unwind the stack（スタック巻き戻し）
: パニック発生時のデフォルトの動作です。
スタック上のデータが逆順に解放されて、ファイルなどのリソースが閉じられます。
このとき、ユーザー定義の drop メソッドも呼び出されます。
そして、最後に当該スレッドが終了します。
終了するのがメインスレッドであれば、プロセスが終了することになります。
このように、パニックはカレントスレッドに対して発生するものであり、本質的にはプロセス全体を止めるものではないことに注意してください。

abort the process（プロセス強制終了）
: パニック発生後の drop 中に別のパニックが発生すると、Rust はスタックの巻き戻し処理 (unwind the stack) をあきらめて、プロセスを強制終了します。
コンパイル時に `-C panic=abort` オプションを指定すると、最初のパニック発生時に、ただちにプロセスが終了するようになります。


エラーを表現する場合は Result 型を使うべし
----

プログラムの実行中には様々なエラーが発生する可能性があります。
Rust で何らかの処理が成功、あるいは失敗（エラー）したことを表現するには、`Result` 型を使用します。

- 参考: [Result 型の基本 ─ 成功と失敗を表現する型](/p/us2ahpw/)

例えば、ユーザーが指定したファイルを開こうとした際に、ファイルが存在しない場合、エラーとして扱われますが、これはプログラムの不具合ではないため、パニックを引き起こすのは不適切です。
このような一般的なユースケースで発生するエラーを処理する際には、`Result` 型の `Err` バリアントを使用してエラーを表現します。
Result 型を使用することで、エラーを適切にハンドルし、処理を続行することが可能です。

