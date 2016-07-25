---
title: ２つのオブジェクトのプロパティをマージする
created: 2015-02-11
---

下記の `extend` 関数は、第１引数で渡されたオブジェクトに、第２引数で渡されたオブジェクトのプロパティをすべてコピーします。
Prototype チェーンを辿ってプロパティをコピーしないようにするために、`hasOwnProperty()` を使い、第２引数で渡されたオブジェクト自身で定義されたプロパティのみをコピーしています。

```javascript
// Extend the first object with all the properties in the second object.
function extend(obj1, obj2) {
  for (key in obj2) {
    if (obj2.hasOwnProperty(key)) {
      obj1[key] = obj2[key];
    }
  }
  return obj1;
}

// 使用例
var a = {'A':1};
var b = {'B':2, 'C':3};
extend(a, b);
console.log(a);  //=> {'A':1, 'B':2, 'C':3}
```

下記は、参考にした underscore.js の `extend` 関数の抜粋です。

```javascript
// Extend a given object with all the properties in passed-in object(s).
_.extend = function(obj) {
  if (!_.isObject(obj)) return obj;
  var source, prop;
  for (var i = 1, length = arguments.length; i < length; i++) {
    source = arguments[i];
    for (prop in source) {
      if (hasOwnProperty.call(source, prop)) {
        obj[prop] = source[prop];
      }
    }
  }
  return obj;
};

// 上記の hasOwnProperty は、Object.prototype.hasOwnProperty
```

