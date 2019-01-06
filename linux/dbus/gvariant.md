---
title: "glib の GVariant を使ってみる"
date: "2012-06-20"
---

GVariant 型とは
----

* 本家ドキュメント
  * [GVariantType — introduction to the GVariant type system](http://developer.gnome.org/glib/stable/glib-GVariantType.html)
  * [GVariant — strongly typed value datatype](http://developer.gnome.org/glib/stable/glib-GVariant.html)
  * [GVariant Format Strings](http://developer.gnome.org/glib/stable/gvariant-format-strings.html)

GDBus のライブラリを使って、array や dictionary などのデータをやりとりしようとすると、C の実装としては `GVariant` 型で扱うことになります。
`GVariant` オブジェクトが保持するデータの型は、**type strings** という文字列で表現されます。
例えば、`GVariant` オブジェクトの type strings が、

```
"s"
```

と定義されている場合、その `GVariant` オブジェクトは 1 つの文字列データを保持しています。
以下のサンプルコードは、1 つの文字列データを保持する `GVariant` オブジェクトを作成し、そこから文字列データを取り出す例です。

#### Makefile

```makefile
CXX = clang++
MY_LIBS = glib-2.0
CPPFLAGS = $(shell pkg-config --cflags ${MY_LIBS})
LDLIBS = $(shell pkg-config --libs ${MY_LIBS})

sample: sample.cpp
```

#### sample.cpp

```cpp
#include <glib.h>

int main() {
    // Create a GVariant object.
    GVariant *variant = g_variant_new("s", "Hello");

    // Obtain a value from a GVariant object.
    gchar *val;
    g_variant_get(variant, "s", &val);
    g_print("value = %s\n", val);
    g_free(val);
}
```

特に、`GVariant` オブジェクトが１つのデータだけを保持している場合は、専用の関数を使えば type strings の指定は必要なくなります。

例: GVariant オブジェクトに INT32 データを入れる
----

```cpp
// 作成
GVariant *variant = g_variant_new_int32(100);

// 取得
gint32 val = g_variant_get_int32(variant);
```

`GVariant` オブジェクトは、複数のデータを保持することができます。

例: GVariant に文字列 (s) と数値 (i) と真偽値 (b) を入れる
----

```cpp
// 作成
GVariant *variant = g_variant_new("(sib)", "Hello", 100, TRUE);

// 取得
gchar *valStr;
gint32 valInt32;
gboolean valBoolean;
g_variant_get(variant, "(sib)", &valStr, &valInt32, &valBoolean);
g_print("%s, %d, %d\n", valStr, valInt32, valBoolean);
g_free(valStr);
```

`GVariant` オブジェクトに可変サイズの配列データを持たせる場合は、`GVariantBuilder` を使って動的にデータを詰めていくことができます。
逆に、`GVariant` オブジェクトから配列データを順に取り出すには、`GVariantIter` を使います。

例: バイナリデータ (byte[]) を GVariant で取得する
----

Type string で、`ay` は byte 配列を表します。
このデータを `GVariant` で取得するには、`g_variant_get()` で指定する type string で、`@ay` のように頭に `@` を付けます。
以下の例では、`GVariant` から「文字列、文字列、byte 配列」を取り出しています。

```cpp
// 作成
GVariant *variant = ...;

// 取得
const char* TYPE_STRING = "(ss@ay)";
gchar* str1 = NULL;
gchar* str2 = NULL;
GVariant* binary = NULL;
g_variant_get(variant, TYPE_STRING, &str1, &str2, &binary);

// バイトデータへのポインタと、サイズを取得
gconstpointer dataAddr = g_variant_get_data(binary);
gsize dataSize = g_variant_get_size(binary);

// バッファ削除
g_free(str1);
g_free(str2);
g_variant_unref(binary);
```

`GVariant` データを解放するときは、`g_free()` ではなく、`g_variant_unref()` を使うことに注意してください。

例: GVariant に配列データを入れる
----

```cpp
// 作成
GVariant *create_variant() {
    GVariantBuilder *builder = g_variant_builder_new(G_VARIANT_TYPE_ARRAY);
    for (int i = 0; i < 5; ++i) {
        g_variant_builder_add(builder, "i", i);
    }
    return g_variant_builder_end(builder);
}

...
GVariant *variant = create_variant();

// 取得
gint32 val;
GVariantIter it;
g_variant_iter_init(&it, variant);
while (g_variant_iter_next(&it, "i", &val)) {
    g_print("%d, ", val);
}
```

dictionary データのループ処理にも、`GVariantBuilder` や `GVariantIter` を使うことができます。

```cpp
// 作成
GVariant *create_variant() {
    GVariantBuilder *builder = g_variant_builder_new(G_VARIANT_TYPE_ARRAY);
    for (int i = 0; i < 5; ++i) {
        g_variant_builder_add(builder, "{ii}", i, i*i);
    }
    return g_variant_builder_end(builder);
}

...
GVariant *variant = create_variant();

// 取得
gint32 val1, val2;
GVariantIter it;
g_variant_iter_init(&it, variant);
while (g_variant_iter_next(&it, "{ii}", &val1, &val2)) {
    g_print("%d, %d\n", val1, val2);
}
```

`GVariant` オブジェクトに含まれている配列データは、`GVariantIter` を使わずに、インデックス指定によって取り出すこともできます。

```cpp
// INT32 の配列 ("ai") を作成
GVariant *create_variant() {
    GVariantBuilder *builder = g_variant_builder_new(G_VARIANT_TYPE_ARRAY);
    for (int i = 0; i < 5; ++i) {
        g_variant_builder_add(builder, "i", i);
    }
    return g_variant_builder_end(builder);
}

int main() {
    // 作成
    GVariant *variant = create_variant();

    // 取得
    gsize size = g_variant_n_children(variant);
    for (int i = 0; i < size; ++i) {
        gint32 val;
        g_variant_get_child(variant, i, "i", &val);
        g_print("%d\n", val);
    }
}
```

`GVariant` オブジェクトを入れ子にすれば、「`INT32` 配列の配列」なども作れます。

例: INT32 配列の配列を入れる
----

```cpp
GVariant *create_child() {
    GVariantBuilder *builder = g_variant_builder_new(G_VARIANT_TYPE_ARRAY);
    g_variant_builder_add(builder, "i", 111);
    g_variant_builder_add(builder, "i", 222);
    g_variant_builder_add(builder, "i", 333);
    return g_variant_builder_end(builder);
}

int main() {
    // 作成 "aai"
    GVariantBuilder *builder = g_variant_builder_new(G_VARIANT_TYPE_ARRAY);
    for (int i = 0; i < 3; ++i) {
        GVariant *child = create_variant();
        g_variant_builder_add(builder, "v", child);
    }
    GVariant* variant = g_variant_builder_end(builder);

    // 取得
    gsize size1 = g_variant_n_children(variant);
    for (int i = 0; i < size1; ++i) {
        GVariant *child;
        g_variant_get_child(variant, i, "v", &child);
        gsize size2 = g_variant_n_children(child);
        for (int j = 0; j < size2; ++j) {
            gint32 val;
            g_variant_get_child(child, j, "i", &val);
            g_print("%d\n", val);
        }
    }
}
```

例: GVariant に連想配列の配列を入れる ("aa{ss}")
----

`a{ss}` という type string は、連想配列を表しています。
なので、`aa{ss}` は連想配列の配列ということになります。
以下のコードは `aa{ss}` のデータを含む `GVariant` オブジェクトを処理しています。

```cpp
gsize num = g_variant_n_children(variant);
for (int i = 0; i < num; ++i) {
    GVariant* child = g_variant_get_child_value(variant, i);
    gsize mapSize = g_variant_n_children(child);
    for (int j = 0; j < mapSize; ++j) {
        gchar* key;
        gchar* val;
        g_variant_get_child(child, j, "{ss}", &key, &val);
        // ここで key, val を使用
        g_free(key);
        g_free(val);
    }
    g_variant_unref(child);
}
```

