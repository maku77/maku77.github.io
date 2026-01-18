import fs from 'node:fs';
import path from 'node:path';

export function walkDir(dir, callback) {
  let result = [];

  fs.readdir(dir, (err, list) => {
    if (err) throw err;
    let pending = list.length;
    if (pending === 0) {
      callback(null, result);
      return;
    }

    list.forEach((f) => {
      const joined = path.join(dir, f);
      fs.stat(joined, (err, stats) => {
        if (err) throw err;
        if (stats && stats.isDirectory()) {
          // Directory
          walkDir(joined, (err, res) => {
            if (err) throw err;
            result = result.concat(res);
            if (--pending === 0) callback(null, result);
          });
        } else {
          // File
          result.push(joined);
          if (--pending === 0) callback(null, result);
        }
      });
    });
  });
}

