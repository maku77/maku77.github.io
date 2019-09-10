---
title: "SharedPreferences でアプリの設定値を保存する"
date: "2010-05-29"
lastmod: "2019-08-07"
---

SharedPreferences とは
----

`SharedPreferences` は、キー＆バリューの組み合わせをファイル（不揮発メモリ）に保存する仕組みで、アプリ内で使用する設定情報などを保存するのに使用できます。
設定値は XML ファイルの形で保存されるのですが、ファイル操作に関する処理は隠ぺいされているので、アプリの設計者はキー＆バリューの取り扱いのみに集中して実装することができます。
アプリ内のコンポーネント間で、キー＆バリューのデータをやりとりする場合にも使用できます。

`SharedPreferences` には下記のようなタイプの値を保存することができます。

- `boolean`
- `string`
- `int`
- `long`
- `float`

逆にもっと複雑なデータを保存したいときは、`SharedPreferences` ではなく、別の方法（通常のファイル I/O など）で永続化する必要があります。


SharedPreferences による値を書き込む
----

キー＆バリューのペアを扱う `SharedPreferences` オブジェクトを生成するには、以下のように [Context#getSharedPreferences()](https://developer.android.com/reference/android/content/Context.html#getSharedPreferences(java.lang.String,%2520int)) メソッドを使用します。
このとき第 1 パラメータで指定した名前が XML ファイルの名前に使用されます（拡張子 `.xml` は省略して指定します）。
第 2 パラメータには、Android 7.0 (API level24) 以降では `Context.MODE_PRIVATE` しか指定できません。

`SharedPreferences` の値の編集を編集したいときは、[edit()](https://developer.android.com/reference/android/content/SharedPreferences.html#edit()) メソッドを使って [SharedPreferences.Editor](https://developer.android.com/reference/android/content/SharedPreferences.Editor.html) オブジェクトを取得する必要があります。
値の編集が終わったら、最後に [Editor#commit()](https://developer.android.com/reference/android/content/SharedPreferences.Editor.html#commit()) メソッドを呼び出すことで、`SharedPreferences` に変更が反映されます。
このメソッドを呼び出し忘れると何も保存されないので注意してください。
`commit()` の呼び出しが面倒に感じるかもしれませんが、このような仕組みになっているおかげで、変更内容を一括で（アトミックに）反映することができ、効率的なファイルへの書き込みやイベント通知を行えるようになっています。

#### Java の場合

```java
// import android.content.SharedPreferences;
SharedPreferences pref = context.getSharedPreferences("my_settings", Context.MODE_PRIVATE);
SharedPreferences.Editor editor = pref.edit();
editor.putString("stringValue", "ほげ");
editor.putBoolean("booleanValue", true);
editor.putInt("intValue", 100);
editor.commit();
```

#### Kotlin の場合

```kotlin
getSharedPreferences("my_settings", Context.MODE_PRIVATE).edit().apply {
    putString("stringValue", "ほげ")
    putBoolean("booleanValue", true)
    putInt("intValue", 100)
    commit()
}
```

アプリのパッケージ名が `com.example.hello` だとすると、上記のような `SharedPreferences` 設定は `/data/data/com.example.hello/shared_prefs/my_settings.xml` というファイルに保存されます。

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="stringValue">ほげ</string>
    <int name="intValue" value="100" />
    <boolean name="booleanValue" value="true" />
</map>
```


SharedPreferences から値を読み出す
----

保存された `SharedPreferences` の値を読み出すには、次のようにします。
指定したキーが存在しない場合には、第２引数で指定したデフォルト値が返されます。

#### Java の場合

```java
SharedPreferences pref = context.getSharedPreferences("my_settings", Context.MODE_PRIVATE);
String stringValue = pref.getString("stringValue", "");
boolean booleanValue = pref.getBoolean("booleanValue", false);
int intValue = pref.getInt("intValue", 0);
```

#### Kotlin の場合

```kotlin
val pref = getSharedPreferences("my_settings", Context.MODE_PRIVATE)
val stringValue = pref.getString("stringValue", "")
val booleanValue = pref.getBoolean("booleanValue", false)
val intValue = pref.getInt("intValue", 0)
```

値の編集は行わないので、`SharedPreferences.Editor` オブジェクトを生成する必要はないところがポイントです。


SharedPreferences の値を削除する
----

`SharedPreferences` にすでに保存されているキー＆バリューを削除するには、`SharedPreferences.Editor` の `remove(key)` メソッドを使用します。
この場合も、最後に `commit()` が必要なことに注意してください。

#### Java の場合

```java
SharedPreferences pref = context.getSharedPreferences("my_settings", Context.MODE_PRIVATE);
SharedPreferences.Editor editor = pref.edit();
editor.remove("stringValue");
editor.remove("booleanValue");
editor.remove("intValue");
editor.commit();
```

#### Kotlin の場合

```kotlin
getSharedPreferences("my_settings", Context.MODE_PRIVATE).edit().apply {
    remove("stringValue")
    remove("booleanValue")
    remove("intValue")
    commit()
}
```

キー名を指定せずに、すべてのキー＆バリューを削除するには、`Editor#clear()` メソッドを使用します。

```kotlin
getSharedPreferences("my_settings", Context.MODE_PRIVATE).edit().apply {
    clear()
    commit()
}
```


アプリ全体、Activity 単位で使用する SharedPreferences
----

上記では、`Context#getSharedPreferences()` メソッドを使って `SharedPreferences` オブジェクトを作成していましたが、他にも、アプリ全体用、Activity 用に `SharedPreferences` オブジェクトを作成するメソッドが用意されています。
それぞれ、作成される XML ファイルのパスが変わってきます。

- <b>アプリ用（デフォルト SharedPreferences）</b>
    - API: **`PreferenceManager.getDefaultSharedPreferences(context)`**
    - ファイルパス: `/data/data/<package名>/shared_prefs/<pacakge名>_preferences.xml`
    - アプリ全体で使用する `SharedPreferences` を作成します。アプリ全体で 1 つのファイルが使用されます。[Preference フレームワークを使って設定画面](./preference-fw.html) を作成した場合も、このデフォルトの SharedPreferences ファイルが使用されます。
- <b>Activity 用</b>
    - API: **`Activity#getPreferences(Context.MODE_PRIVATE)`**
    - ファイルパス: `/data/data/<package名>/shared_prefs/<Activity名>.xml`
    - `Activity` ごとに専用の `SharedPreferences` を作成します。その `Activity` の設定のために使用します。
- <b>ファイル名指定</b>
    - API: **`Context#getSharedPreferences("sample", Context.MODE_PRIVATE)`**
    - ファイルパス: `/data/data/<package名>/shared_prefs/sample.xml`
    - 指定したファイル名の単位で `SharedPreferences` を作成します。用途別に設定ファイルを分けて管理することができます。

### （コラム）デフォルトの SharedPreferences ファイルだけで十分？

SharedPreferences の保存先 XML は、ファイル名を指定できる `Context#getSharedPreferences(String, int)` のバージョンを使用すれば切り替えることができますが、多くのアプリケーションでは `PreferenceManager.getDefaultSharedPreferences(Context)` によるデフォルトの SharedPreferences ファイルを使えば十分だったりします。
というのも、設定項目のキー名を工夫すればある程度の数の設定項目はうまく階層化して管理できるからです。
例えば、デバッグ用の設定項目を `debug.notification` のようなキー名にしておけば、他の重要な設定項目と混ざってしまうことはありません。

Google の [Settings Design Guidelines](https://source.android.com/devices/tech/settings/settings-guidelines) では、同一の設定項目を、異なる設定画面で表示するというデザインパターンが提示されています。

> In some cases, it may be helpful to duplicate an individual setting on two different screens. Different situations can trigger users to change a setting, so including the setting in multiple places will help users find this item.

このようなケースにおいても、設定ファイルが分かれていない方が都合がよいです。
また、Android は [設定画面を作成する](./preference-fw.html) ために `PreferenceFragmentCompat` クラスを提供していますが、このクラスも内部では、デフォルトの SharedPreferences ファイルを使用することを前提にした作りになっています。


SharedPreferences オブジェクトの変更を監視する
----

`SharedPreferences` オブジェクトに **`SharedPreferences.OnSharedPreferencesChangeListener`** を登録すると、値の変更を監視することができます。
まずは、値の変更を監視したい任意のクラスでこのリスナーを実装します。

```kotlin
class MainActivity : FragmentActivity(R.layout.activity_main), SharedPreferences.OnSharedPreferenceChangeListener {
    ...
    override fun onSharedPreferenceChanged(pref: SharedPreferences?, key: String?) {
        if (key == "user_name") {
            val value = pref?.getString(key, "")
            Toast.makeText(this, "key '$key' is changed: $value", Toast.LENGTH_LONG).show()
        }
    }
}
```

あとは、`SharedPreferences` オブジェクトの `registerOnSharedPreferenceChangeListener` を使ってリスナーを登録するだけです。

```kotlin
val pref = getSharedPreferences("my_settings", Context.MODE_PRIVATE)
pref.registerOnSharedPreferenceChangeListener(this)
```

### 設定画面での変更をまとめて受け取る

`onSharedPreferenceChanged` は、設定画面で**ひとつの設定値を変更する度に**呼び出されます。
設定画面を閉じてからまとめて変更値を取得したい場合は、設定画面の `Activity` を `startActivityForResult()` で開き、画面を閉じたタイミング (`onActivityResult`) で `SharedPreferences` から情報を取得するとよいでしょう。

