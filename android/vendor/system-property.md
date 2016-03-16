---
title: システムプロパティのあれこれ
created: 2010-05-08
---

システムプロパティの定義方法
----

### ビルド時に埋め込む方法

プロダクトごとの `Makefile`（`your_product.mk` など） に、以下のように記述しておくと、ビルド時に `build.prop` に追加されます。

```makefile
PRODUCT_PROPERTY_OVERRIDES += \
    persist.sys.timezone = Asia/Tokyo
```

### イメージのビルド後にプロパティファイルをいじる方法

`default.prop` に以下のような感じで追加すると、デバイス起動時にシステムプロパティとして設定されます。

```
persist.sys.timezone=Asia/Tokyo
```

### システム起動時の init の中で設定する

`init.rc` あるいは、プロダクトごとの `init` ファイルに、以下のような感じで追加しておくことができます。

```
setprop persist.sys.timezone Asia/Tokyo
```


Android デバイス起動時にシステムプロパティが設定される流れ
----

1. init process が以下からプロパティをロードする 
  - /default.prop
  - /system/build.prop
  - /system/default.prop
  - /data/local.prop
2. システムプロパティ情報を提供するための unix domain socket server が作成される。socket のパスは `/dev/socket/property_service` で、サイズは環境変数の `ANDROID_PROPERTY_WORKSPACE` で取得する。Bionic libc のライブラリ `libcutils` がこの shared memory に対してシステムプロパティを読み書きするインタフェースを提供する。


システムプロパティの get/set
----

### from C/C++ native code

Bionic libc の `libcutils` をリンクし、`cutils/properties.h` を include して、以下の関数を使用します。

```cpp
int property_get(const char *key, char *value, const char *default_value);
int property_set(const char *key, const char *value);
int property_list(void (*propfn)(const char *key, const char *value, void *cookie), void *cookie);
```

使い方はこんな感じ。

```cpp
#include "utils/properties.h"

char value[PROPERTY_VALUE_MAX];
if (property_get("ro.hardware", value, "") > 0) {
    if (strcmp(value, "hoge") == 0) {
        // ...
    }
}
```

### from Java code

`android.os.SystemProperties` の下記のメソッドを使用します。

```java
public static String get(String key, String def)
public static int getInt(String key, int def)
public static long getLong(String key, long def)
public static boolean getBoolean(String key, boolean def)
public static void set(String key, String val)
```

これは Android platform で使われているプライベートなクラスなので、Android アプリからは通常使用しません。
扱い的には internal usage only とされているが、internal usage って何のことを言っているのかが曖昧。

`SystemProperties` は JNI を使って、`libcutils` のネイティブコードを叩いています。
get は通常できるが、set は UID が適切にセットされていないとできないっぽいです（2016-03-16 追記: 現状これらは hide API 扱いなので、いずれも通常のアプリケーションからは使用できません）。

似たようなものに、通常の J2SE アプリケーションで使用可能な `java.lang.System` クラスの `getProperty()` 系 API がありますが、この API で取得するプロパティは VM に関するプロパティであり、情報が異なります。

### from shell (script)

Bionic libc の `libcutils` に、コマンドラインツールとして以下のコマンドが用意されています。これらは shell script からも実行可能です。

```
getprop (例: getprop com.example.key)
setprop (例: setprop com.example.key value)
```

