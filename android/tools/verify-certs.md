---
title: "APK ファイルの署名を確認する"
date: "2014-08-22"
---

署名情報を確認する方法
----

JDK に付属している `jarsigner` コマンドを使用して、APK ファイルの署名情報を確認することができます。

~~~
$ jarsigner -verify -certs -verbose YourApp.apk

...
sm     21632 Mon Sep 01 17:08:16 JST 2014 lib/libhoge.so

      X.509, CN=Android Debug, O=Android, C=US
      [certificate is valid from 12/08/27 17:19 to 42/08/20 17:19]
      [CertPath not validated: Path does not chain with any of the trust anchors]

s      36381 Fri Oct 03 14:10:34 JST 2014 META-INF/MANIFEST.MF

      X.509, CN=Android Debug, O=Android, C=US
      [certificate is valid from 12/08/27 17:19 to 42/08/20 17:19]
      [CertPath not validated: Path does not chain with any of the trust anchors]
       36434 Fri Oct 03 14:10:34 JST 2014 META-INF/CERT.SF
        1203 Fri Oct 03 14:10:34 JST 2014 META-INF/CERT.RSA

  s = signature was verified
  m = entry is listed in manifest
  k = at least one certificate was found in keystore
  i = at least one certificate was found in identity scope

jar verified.
~~~


証明書の公開鍵 (Subject Public Key Info) を確認する方法
----

APK 内の証明書から、公開鍵情報を抽出するには、`openssl` ツールを使用して下記のように実行します。

~~~
$ jar xvf YourApp.apk META-INF    # APK内の証明書を抽出
$ openssl pkcs7 -inform DER -in META-INF/CERT.RSA -noout -print_certs -text

Certificate:
    Data:
        Version: 3 (0x2)
        Serial Number: 553438828 (0x20fcce6c)
    Signature Algorithm: sha256WithRSAEncryption
        Issuer: C=US, O=Android, CN=Android Debug
        Validity
            Not Before: Aug 27 08:19:20 2012 GMT
            Not After : Aug 20 08:19:20 2042 GMT
        Subject: C=US, O=Android, CN=Android Debug
        Subject Public Key Info:
            Public Key Algorithm: rsaEncryption
                Public-Key: (2048 bit)
                Modulus:
                    00:bb:40:43:86:b6:9e:84:5a:8b:b3:b3:c0:bc:08:
                    f0:8f:28:92:7c:7a:3d:02:44:da:17:4b:d0:a0:c5:
                    86:eb:f8:b4:2e:3e:3d:10:60:bc:dd:fe:5e:a9:17:
                    c8:ba:eb:bb:fc:96:ff:a2:02:39:77:42:ab:db:0b:
                    d1:9b:09:34:f3:3b:20:89:27:8c:d2:99:0d:2b:e1:
                    4a:34:49:5a:4a:76:ad:b4:a4:9d:4c:6a:42:fa:07:
                    0f:b3:ae:90:d3:19:7f:74:78:5d:73:27:f3:52:0b:
                    82:64:2c:d6:64:e0:c9:56:97:c8:c4:53:e7:35:e5:
                    b6:25:c5:58:cd:85:bd:64:f6:94:da:d1:0e:72:1d:
                    8f:ad:f8:b8:f7:ae:37:18:70:a2:b7:01:f4:42:c1:
                    04:bc:3b:b6:a0:85:29:95:5f:e7:c8:82:4b:b0:01:
                    db:1b:5b:06:c2:c1:e1:8d:e6:35:bf:f4:5f:ef:20:
                    b0:7f:f8:b8:36:fa:8d:48:7b:4b:35:c1:6a:a1:42:
                    1a:e7:df:b2:af:a9:8d:d3:9b:0d:6a:bf:c8:fc:94:
                    3d:99:61:1c:94:a6:40:1b:94:ac:e6:8e:07:20:77:
                    39:87:3e:86:38:a6:9e:c1:75:9a:13:99:06:73:b9:
                    d3:22:66:30:2f:e1:ea:4a:01:6e:8d:7d:ed:82:b2:
                    79:f7
                Exponent: 65537 (0x10001)
        X509v3 extensions:
            X509v3 Subject Key Identifier:
                8D:5D:22:F1:D1:23:18:0D:B2:6A:B0:2E:71:BE:83:3F:BF:C6:F1:D0
    Signature Algorithm: sha256WithRSAEncryption
         47:97:ba:aa:e6:c0:52:83:d3:52:0b:be:4f:b9:7b:bd:06:a3:
         06:d3:ee:62:89:35:e4:a7:02:fc:99:53:b6:97:23:10:c9:17:
         94:c3:e8:4a:70:79:05:5b:1b:d7:6b:f9:45:09:33:8f:a1:ac:
         cb:60:54:65:13:b4:61:ce:39:0c:40:65:08:6d:d8:ab:03:15:
         84:96:55:2e:bd:3f:c1:bb:d5:85:9d:91:91:27:e8:83:e9:17:
         0e:31:3d:f3:a4:33:ac:a0:1d:65:65:42:d8:90:e3:3b:e7:04:
         42:81:d5:51:b2:0d:cc:b1:d7:64:f1:f7:aa:c8:56:09:ea:7c:
         af:7b:b4:f0:48:49:b7:7b:1a:c1:1a:a1:8e:1b:46:c5:50:54:
         e0:a9:c0:63:0e:d4:8d:58:70:77:91:ce:d0:d6:5d:ba:38:f6:
         ab:2d:16:23:f9:de:61:b1:c0:af:ed:a9:3e:85:f8:ed:da:47:
         fe:04:58:31:1a:eb:54:8f:fe:1f:a1:68:12:1f:bf:83:31:05:
         fa:d3:30:d3:52:59:2b:4a:fb:9b:0a:aa:0e:02:b6:b3:39:75:
         f9:c0:5f:b4:0e:09:f8:f5:80:e3:54:49:c0:33:e4:36:88:42:
         34:fa:01:50:03:58:ca:20:0e:44:a1:eb:c2:c2:6e:e6:11:30:
         ec:77:71:b2
~~~

