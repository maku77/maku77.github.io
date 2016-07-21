---
title: "Chrome で Ajax (XMLHttpRequest) によるクロスドメイン通信の制約をなくす"
created: 2014-11-17
---

HTTP サーバなしの Chrome ローカル環境でウェブアプリの開発をしているときに、`XMLHttpRequest` による Ajax 通信を行おうとすると、クロスドメイン制約によって以下のようなエラーが出て通信できないことがあります。

```
XMLHttpRequest cannot load XXXXXX. Cross origin requests are only
supported for protocol schemes: http, data, chrome-extension, https,
chrome-extension-resource.
```

Chrome は起動オプションで、この制約を一時的に無効にすることができます。

```
C:\> chrome.exe --disable-web-security
```

