---
title: "Androidメモ: ネットワーク情報を取得する (ConnectivityManager, NetworkInfo)"
url: "p/rm9ve9q/"
date: "2010-09-09"
tags: ["android"]
aliases: [/android/network-info.html]
---

ネットワークの情報を取得する
----

ネットワーク関連の情報は、**`ConnectivityManager`** の `getActiveNetworkInfo()` メソッドで取得できる **`NetworkInfo`** オブジェクトを使って参照できます。
`NetworkInfo` オブジェクトは以下のように取得します。

```java
// import android.net.ConnectivityManager;
// import android.net.NetworkInfo;

ConnectivityManager manager =
        (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
if (manager == null) {
    return false;
}

NetworkInfo networkInfo = manager.getActiveNetworkInfo();
if (networkInfo == null) {
    return false;
}
```

`NetworkInfo` を取得するためには **`ACCESS_NETWORK_STATE`** パーミッションが必要なため、`AndroidManifest.xml` で以下のように宣言しておきます。

{{< code lang="xml" title="AndroidManifest.xml" >}}
<manifest ...>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    ...
</manifest>
{{< /code >}}

- 参考: [ConnectivityManager｜Android Developers](https://developer.android.com/reference/android/net/ConnectivityManager)


ネットワークの情報を一覧表示する
----

`NetworkInfo` オブジェクトの **`toString()`** メソッドで、ネットワーク情報の文字列表現を取得できます。

```java
System.out.println(networkInfo.toString());
```

{{< code title="出力結果（実際は一行）" >}}
NetworkInfo:
  type: ETHERNET[],
  state: CONNECTED/CONNECTED,
  reason: eth0 DHCP success,
  extra: (none),
  roaming: false,
  failover: false,
  isAvailable: false,
  iPaddress: 192.168.3.98
{{< /code >}}


ネットワークに接続されているか調べる
---

Android デバイスがネットワークに接続されているかどうかは、`NetworkInfo` オブジェクトの **`isConnected()`** メソッドで調べることができます。

```java
if (networkInfo.isConnected()) {
    // ネットワークに接続されている
}
```

下記のように **`getState()`** メソッドを使って調べることもできます。

```java
if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
    // ネットワークに接続されている
}
```


どのタイプのネットワークがアクティブになっているか調べる
----

現在アクティブになっているネットワークが、WiFi なのか、Ethernet なのか、モバイルデータ通信なのかなどを調べるには、`NetworkInfo` オブジェクトの **`getType()`** メソッドを使います。

```java
NetworkInfo networkInfo = manager.getActiveNetworkInfo();

switch (networkInfo.getType()) {
case ConnectivityManager.TYPE_WIFI:
    // WiFi network
    break;
case ConnectivityManager.TYPE_ETHERNET:
    // Ethernet
    break;
default:
    // Unknown
    break;
}
```

次のように、タイプ名を文字列で取得することもできます。

```java
String typeName = networkInfo.getTypeName();
```

戻り値として、`"WIFI"` や `"ETHERNET"` などの文字列が返されます。


IP アドレスを調べる
----

現在アクティブなネットワークの IP アドレスを調べるには、`NetworkInfo` オブジェクトの **`getIpAddress()`** メソッドを使います。

```java
String ipAddr = networkInfo.getIpAddress();
if (ipAddr == null) {
    // No address
}
```

