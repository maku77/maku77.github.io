---
title: GDBus でバイナリデータ（バイト配列）を受け取る方法
created: 2012-06-22
---

D-Bus メソッド経由でバイト配列を送ろうとして、パラメータのタイプを `ay` と定義して、`gdbus-codegen` でコード生成すると、デフォルトでは `gchar*` 型の文字列データとしてパラメータが定義されてしまいます。
`gchar*` データとしてやりとりしようとすると、0 を含むデータを受け取れなくなってしまうので、`GVariant*` としてバイナリデータを受け取る必要があります。
そのためには、Introspection XML の `arg` 要素の子要素として、以下のように `annotation` 要素を追加します。

#### introspection.xml（抜粋）

```xml
...
<method name="PushBinaryData">
    <arg type="ay" name="data" direction="in">
        <annotation name="org.gtk.GDBus.C.ForceGVariant" value="true" />
    </arg>
</method>
...
```

すると、GDBus サーバ側のハンドラでは `GVariant*` 型のパラメータとしてデータを受信できるようになるので、以下のように内部のデータを取得できます。
データのサイズも、`GVariant` オブジェクトから取得することができます。

```cpp
// GVariant* variant;
const char* p = (const char*) g_variant_get_data(variant);
gsize size = g_variant_get_size(variant);

for (int i = 0; i < size; ++i) {
    g_print("data[%d]=%d\n", i, p[i]);
}
```

