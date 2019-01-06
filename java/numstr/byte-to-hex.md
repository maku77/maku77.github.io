---
title: "バイト配列から 16 進数文字列を作成する"
date: "2015-02-23"
---


次の `toHexString` は、渡されたバイト配列を 16 進数表記の文字列に変換します。

~~~ java
/**
 * Convert byte array to hex format string (e.g. "01,02,FE,FF").
 */
public static final String toHexString(byte[] data) {
    final int len = data.length;
    if (len == 0) {
        return "";
    }
    if (len == 1) {
        return String.format("%02X", data[0]);
    }
    StringBuilder builder = new StringBuilder(len * 3 - 1);
    builder.append(String.format("%02X", data[0]));
    for (int i = 1; i < len; ++i) {
        builder.append(String.format(",%02X", data[i]));
    }
    return builder.toString();
}
~~~

#### 使用例

~~~
byte[] data = new byte[] { 0x01, 0x02, (byte) 0xfe, (byte) 0xff };
System.out.println(toHexString(data));
~~~

#### 実行結果

~~~
01,02,FE,FF
~~~

