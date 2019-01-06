---
title: "curl で HTTP のレスポンスヘッダのみを確認する"
date: "2016-09-14"
---

```
$ curl -s -D - localhost:8080 -o /dev/null
HTTP/1.1 403 Forbidden
Date: Wed, 14 Sep 2016 12:46:40 GMT
Server: Jetty(8.y.z-SNAPSHOT)
Expires: Thu, 01 Jan 1970 00:00:00 GMT
Content-Type: text/html;charset=UTF-8
X-Hudson: 1.395
X-Jenkins: 1.565.1
X-Jenkins-Session: a53528f4
X-Hudson-CLI-Port: 41906
X-Jenkins-CLI-Port: 41906
X-Jenkins-CLI2-Port: 41906
X-You-Are-Authenticated-As: anonymous
X-You-Are-In-Group:
X-Required-Permission: hudson.model.Hudson.Read
X-Permission-Implied-By: hudson.security.Permission.GenericRead
X-Permission-Implied-By: hudson.model.Hudson.Administer
Content-Length: 793
Vary: Accept-Encoding
Proxy-Connection: Keep-Alive
Connection: Keep-Alive
Set-Cookie: JSESSIONID.e92abcec=jhmj6nosqyo51rcld8mgf20jc;Path=/
```

