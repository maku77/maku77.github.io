---
title: "JavaScriptメモ: 関数オブジェクトから関数名を取得する"
url: "p/wxogbq4/"
date: "2014-03-17"
tags: ["javascript"]
aliases: [/js/syntax/get-func-name.html]
---

関数オブジェクトの `name` プロパティで、関数名を取得することができます。

{{< code lang="javascript" title="sample.js" >}}
function func1() {}
var func2 = function () {};
var func3 = function func3() {};

console.log(func1.name);  //=> 'func1'
console.log(func2.name);  //=> ''
console.log(func3.name);  //=> 'func3'
{{< /code >}}
