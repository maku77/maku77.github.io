#!/usr/bin/env python3
import glob
import re

GLOB = '**/*.md'
PATTERN = r'title:\s"*(.+)"'

def process_file(f):
    for line in f:
        match = re.search(PATTERN, line)
        if match:
            # print('  ' + match.group(1))
            return
    print(filename + '  NOT FOUND')

if __name__ == '__main__':
    for filename in glob.iglob(GLOB, recursive=True):
        with open(filename, encoding='utf-8') as f:
            process_file(f)

