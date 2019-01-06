---
title: "SharedPreference でアプリの設定値を保存する"
date: "2010-05-29"
---

SharedPreferences とは
----

SharedPreferences に格納した値はファイル（不揮発メモリ）に保存され、次回のアプリケーション実行時に読み出すことができます。
アプリケーション内部のコンポーネント間で、key/value の組み合わせのデータをやりとりする場合にも使用できます。


SharedPreferences の基本
----

### 値の書き込み

key/value のペアを格納するための `SharedPreferences` オブジェクトを作成するには、以下のように `getSharedPreferences()` メソッドを使用します。
`SharedPreferences.Editor` オブジェクトの `commit` メソッドにより、実際にファイルに内容が保存されます。

```java
// import android.content.SharedPreferences;

SharedPreferences pref = getSharedPreferences("MyPref", Activity.MODE_PRIVATE);
SharedPreferences.Editor editor = pref.edit();
editor.putString("stringValue", "HogeHoge");
editor.putBoolean("booleanValue", true);
editor.putInt("intValue", 100);
editor.putFloat("floatValue", 1.2345f);
editor.commit();
```

この例では、`/data/data/org.example.hello/shared_prefs/MyPref.xml` に以下のように内容が保存されます。

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
<string name="stringValue">HogeHoge</string>
<boolean name="booleanValue" value="true" />
<int name="intValue" value="100" />
<float name="floatValue" value="1.2345" />
</map>
```


### 値の読み出し

`/data/data/org.example.hello/shared_prefs/MyPref.xml` に保存された key/value のペアを読み出すには以下のようにします。
指定したキーが存在しない場合には、第２引数で指定したデフォルト値が返されます。

```java
SharedPreferences pref = getSharedPreferences("MyPref", Activity.MODE_PRIVATE);
String stringValue = pref.getString("stringValue", "");
boolean booleanValue = pref.getBoolean("booleanValue", false);
int intValue = pref.getInt("intValue", 0);
float floatvalue = pref.getFloat("floatValue", 0f);
```


### SharedPreferences オブジェクトの変更を監視する

`SharedPreferences` オブジェクトに、`OnSharedPreferencesChangeListener` を登録すると、内容の変更をリアルタイムに監視することができます。

まずは、`SharedPreferences` オブジェクトの変更を監視したい任意のクラスで `OnSharedPreferenceChangeListener` を実装します。

```java
// import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class MyActivity extends Activity implements OnSharedPreferenceChangeListener {
    ...
    public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
        Toast.makeText(this, key + " changed", 0).show();
    }
}
```

あとは、`SharedPreferences` オブジェクトの `registerOnSharedPreferenceChangeListener` を使って登録するだけです。

```java
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // デフォルトの SharedPreferences の変更を監視
    Context context = getApplicationContext();
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
    pref.registerOnSharedPreferenceChangeListener(this);
}
```

上記の例では、`PreferenceActivity` を使った設定画面を作成していると仮定し、デフォルトの `SharedPreferences` オブジェクトにリスナを登録しています。
`PrerenceActivity` の画面で変更した内容が、自動的にデフォルトの `SharedPreferences` オブジェクトに反映されるようになっているからこそできる芸当です。
もちろん、明示的に名前を指定して作成した SharedPreferences の変更を監視することもできます。

`onSharedPreferenceChanged` は、設定画面でひとつの設定値を変更する度に呼び出されます。
設定画面を閉じてからまとめて変更値を取得したい場合は、設定画面を `startActivityForResult()` で開き、設定画面を閉じたタイミング (`onActivityResult`) で `SharedPreferences` から情報を取得するとよいでしょう。

