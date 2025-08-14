---
title: "Androidメモ: AsyncTask による非同期処理と UI 更新処理"
url: "p/o3hggfz/"
date: "2011-08-22"
tags: ["android"]
aliases: [/android/async-task.html]
---

AsyncTask とは
----

[`android.os.AsyncTask`](https://developer.android.com/reference/android/os/AsyncTask) クラスは、バックグラウンドで何か処理をしながら、逐次 UI を更新していくときに便利に使用できるユーティリティ・クラスです。
UI の更新処理はメインスレッドで行わなければいけないため、ワーカースレッドから UI 更新する場合は、通常は `Handler` オブジェクトを介して処理をメインスレッドに委譲しなければいけません。
`AsyncTask` を使用すると、このあたりの処理を隠ぺいすることができます。


AsyncTask で実装するメソッド
----

`AsyncTask` のサブクラスで以下のようなメソッドをオーバーライドし、処理内容を記述します。
ワーカースレッドでの処理内容を `doInBackground()` 内に記述し、UI の更新処理を `onProgressUpdate()` で記述するようにします。

- `doInBackground(Params...)`
  - ワーカースレッドで行いたい処理を記述する。UI スレッドとは違うスレッドで実行される。
- `onProgressUpdate(Progress...)`
  - UI の更新処理を記述する。UI スレッドで実行される。`doInBackground()` 内から `progressUpdate()` をコールすることで、明示的に呼び出す。

`doInBackground()` の実行前と、実行後には、以下のようなメソッドが UI スレッドから呼び出されます。
このメソッドもオーバーライドして、UI の更新処理を記述することができます。

- `onPreExecute()`
- `onPostExecute(Result)`


AsyncTask のキャンセル処理について
----

- `doInBackground()` 内から `cancel()` を呼ぶと、その時点で `onPostExecute()` が実行され、その後も `doInBackground()` は継続されます。
- `AsyncTask` の外から `cancel()` を呼ぶと、その時点で `onCanceled()` が実行され、その後も `doInBackground()` は継続されます。この場合は、`onPostExecute()` は呼び出されません。

