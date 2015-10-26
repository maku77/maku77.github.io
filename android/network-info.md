---
title: ネットワーク関連の情報を取得する
created: 2010-09-09
layout: android
---

ネットワークの情報を取得する
====

ネットワーク関連の情報は、`android.net.ConnectivityManager` の `getActiveNetworkInfo()` メソッドで取得できる `NetworkInfo` オブジェクトから参照できます。
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

`NetworkInfo` を取得するためには、`AndroidManifest.xml` に以下のように `uses-permission` の設定が必要です。

#### AndroidManifest.xml
```xml
<manifest ...>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    ...
</manifest>
```

参考
----

- [ConnectivityManager | Android Developers](http://developer.android.com/reference/android/net/ConnectivityManager.html)


ネットワークの情報を一覧表示する
====

`NetworkInfo` オブジェクトの `toString()` メソッドで、ネットワーク情報の文字列表現を取得できます。

```java
System.out.println(networkInfo.toString());
```

#### 出力結果（実際は一行）

```
NetworkInfo:
  type: ETHERNET[],
  state: CONNECTED/CONNECTED,
  reason: eth0 DHCP success,
  extra: (none),
  roaming: false,
  failover: false,
  isAvailable: false,
  iPaddress: 192.168.3.98
```


ネットワークに接続しているか調べる
====

```java
if (networkInfo.isConnected()) {
    // Connected.
}
```

これは、以下のようにするのと同等です。

```java
if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
    // Connected
}
```


どのタイプのネットワークがアクティブになっているか調べる
====

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


ネットワークのタイプ名を文字列で取得する
====

```java
String typeName = networkInfo.getTypeName();
```

戻り値としては、

- NetworkInfo.NETWORK_NAME_WIFI ("WIFI")
- NetworkInfo.NETWORK_NAME_ETHERNET ("ETHERNET")

が `static final String` で定義されていますが、`@hide` で定義されているので、これらの定数は参照しない方が無難です。


IP アドレスを調べる
====

```java
String ipAddr = networkInfo.getIpAddress();
if (ipAddr == null) {
    // No address.
}
```

