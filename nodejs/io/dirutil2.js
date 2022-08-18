var fs = require('fs');
var path = require('path');

function walkDir(dir, callback) {
  var result = [];

  fs.readdir(dir, function (err, list) {
    if (err) throw err;
    var pending = list.length;
    if (pending == 0) {
      callback(null, result);
      return;
    }

    list.forEach(function (f) {
      var joined = path.join(dir, f);
      fs.stat(joined, function (err, stats) {
        if (err) throw err;
        if (stats && stats.isDirectory()) {
          // Directory
          walkDir(joined, function (err, res) {
            if (err) throw err;
            result = result.concat(res);
            if (--pending == 0) callback(null, result);
          });
        } else {
          // File
          result.push(joined);
          if (--pending == 0) callback(null, result);
        }
      });
    });
  });
}

module.exports = {
  walkDir: walkDir
};

