---
title: ".NET - XML 形式の設定ファイルを XPath で操作するサンプル"
date: "2006-03-09"
---

~~~ csharp
using System;
using System.Xml;

//
// XML 形式の設定ファイルを XPath で操作するサンプル
//
class MyAppConfig {
    private XmlDocument doc = new XmlDocument();
    private String fileName;

    // 値を取得するための XPath
    private const String XPATH_DATABASE_PATH = "/MyApp/Database/@path";
    private const String XPATH_REPOSITORY_PATH = "/MyApp/Repository/@path";

    // XML ファイルのロード
    public MyAppConfig(String fileName) {
        doc.Load(fileName);
        this.fileName = fileName;
    }

    // 設定の保存
    public void Save() {
        doc.Save(fileName);
    }

    // 設定値の取得＆更新
    public String DatabasePath {
        get {
            XmlNode node = doc.SelectSingleNode(XPATH_DATABASE_PATH);
            return node.Value;
        }
        set {
            XmlNode node = doc.SelectSingleNode(XPATH_DATABASE_PATH);
            node.Value = value;
        }
    }

    public String RepositoryPath {
        get {
            XmlNode node = doc.SelectSingleNode(XPATH_REPOSITORY_PATH);
            return node.Value;
        }
        set {
            XmlNode node = doc.SelectSingleNode(XPATH_REPOSITORY_PATH);
            node.Value = value;
        }
    }
}
~~~

