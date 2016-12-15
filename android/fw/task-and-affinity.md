---
title: Activity の起動モードと起動フラグ（タスクとアフィニティ）
created: 2011-08-03
---

タスク
----

Android のアプリは「タスク」に複数の Activity をスタックすることで実現されています。
デフォルトでは、新しい Activity を起動すると、その起動元のタスクに Activity がスタックされていきます。
戻るキーで戻った時に表示される Activity は、このタスクにどのように Activity がスタックされているかによります。


アフィニティ
----

Activity とタスクはそれぞれ１つの「アフィニティ」を持っており、どのような「アフィニティ」を持っているかによって、Activity がどのタスクに配置されるかが制御されています。

- 同じアプリ内の Activity は基本的に同じ「アフィニティ」を持ちます。
- タスクの「アフィニティ」は、そのタスクを最初に起動した Activity のものになります。
- Activity の配置先を決めるのに「アフィニティ」が使用されるのは、Activity の起動モードに `singleTask` を指定した場合か、起動フラグに `Intent.FLAG_ACTIVITY_NEW_TASK` を指定した場合です。
- Activity の「アフィニティ」は、`activity` 要素の `android:taskAffinity` 属性で指定できます。１つの APK 内に、ユーザ視点から見て、複数のアプリを含めたい場合は、各 Activity の「アフィニティ」をアプリごとに設定することになります。


起動モードと起動フラグ
----

Activity の起動モード（`activity` 要素の `android:launchMode` 属性）と、`startActivity()` の Intent にセットする起動フラグ (`setFlag`) によって、Activity の起動する条件や、どのタスクに配置されるかが決まります。

起動モードは、起動される Activity 側の設定として定義され、起動フラグは、呼び出し側の都合で指定することになります。
基本的に、呼び出し側で設定する Intent フラグの方が優先されます。


### 起動モード（android:launchMode 属性）

| launchMode | startActivity() で、新しい Activity を起動するか？ | 新しく Activity が起動した場合、どのタスクに配置されるか？ |
| ---- | ---- |
| **standard**（デフォルト） | 常に新しい Activity を起動する。 | 起動元のタスクにスタックされる。 |
| **singleTop** | 同じ Activity がタスクの先頭にいる場合は、`Activity.onNewIntent()` を呼び出す。同じ Activity がタスクの先頭にいない場合、新しい Activity を起動する（つまり、A-B-C-B のように、同じ Activity B が１つのタスク内に共存できる）。 | 起動元のタスクにスタックされる。 |
| **singleTask** | 同じ Activity がシステム内に存在する場合、そのタスクをフォアグラウンドにし、`Activity.onNewIntent()` を呼び出す。さらに、その上にある Activity をすべて破棄する。同じ Activity がシステム内に存在しない場合、新しい Activity を起動する。 | 同じアフィニティを持つタスクが存在する場合、そこにスタックする。同じアフィニティを持つタスクが存在しない場合、新しいタスクに配置する（`FLAG_ACTIVITY_NEW_TASK` を指定したのと同様）。 |
| **singleInstance** | 同じ Activity がシステム内に存在する場合、そのタスクをフォアグラウンドにし、`Activity.onNewIntent()` を呼び出す。システム内に存在しない場合、新しい Activity を起動する。 | 新しいタスクに配置する。 |

例外的に、`singleInstance` の Activity から、別の Activity を起動した場合は、必ず新しいタスクに配置されます。
これは、`singleInstance` の Activtity を含むタスクは、その Activity 専用のタスクになり、別の Activity をスタックすることができないからです。


### 起動フラグ（Intent.setFlag）

- **Intent.FLAG_ACTIVITY_SINGLE_TOP**
  - このフラグを指定した場合、起動モード `singleTop` と同じ振る舞いをします。既存のタスクの先頭にその Activity が存在する場合は、新しい Activity を起動しません。
- **Intent.FLAG_ACTIVITY_NEW_TASK**
  - Activity の起動モードが `standard`、`singleTop` のときに、新しい Activity が起動する場合は、通常、起動元のタスクにスタックされますが、このフラグを指定した場合は、新しいタスクに配置されるようになります（同じアフィニティを持つタスクがあれば、そのタスクに配置されます）。つまり、起動モード `singleTask` と同じ振る舞いをします。
  - Activity の起動モードが `singleTask`、`singleInstance` のときは、このフラグは無視されます（もともと新しいタスク上に配置するなどの振る舞いをするから）。
  - ランチャーのようなアプリから、別のアプリを起動するようなときには、このフラグを指定して、既存の Activity をタスクの先頭に持ってくるか、新しいタスク上で Activity を起動するようにします。こうすることで、その Activity がそのアプリのエントリポイントのように見えるようになります（その Activity で戻るキーを押すとアプリが終了する）。
  - 既に同じ Activity が存在している場合は、そのタスクがフォアグラウンドに表示され、`Activity.onNewIntent()` が呼び出されるだけで、新しいタスクは開始されません。この振る舞いは後述の `ACTIVITY_FLAG_MULTIPLE_TASK` で制御できます。
  - 同じアフィニティのタスク上に Activity がスタックされるのを防ぎたい場合は、Activity のアフィニティを空文字（`""`）にします。すると、Activity は常に新しいタスク上で起動するようになります。もちろんこれは新しい Activity が起動する場合の話で、既に同じ Activity が存在する場合は、新しいタスクは起動しません。
  - Activity の起動モードが `singleTop` のときに、新しい Activity が起動されない場合は、当然タスクも起動されません。
- **Intent.FLAG_ACTIVITY_MULTIPLE_TASK**
  - `FLAG_ACTIVITY_NEW_TASK` を指定した場合だけ、このフラグを同時に指定することができます。
  - `FLAG_ACTIVITY_NEW_TASK` と同時に指定することで、Activity がすでに存在している場合にも、新しいタスク上に Activity が起動されるようになります。


参考サイト
----

- [Intent │ Android Developers](https://developer.android.com/reference/android/content/Intent.html)
- [Tasks and Back Stack │ Android Developers](https://developer.android.com/guide/components/tasks-and-back-stack.html)

