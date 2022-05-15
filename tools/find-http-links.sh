#!/bin/bash
find . -name '*.html' -not -path '*/vendor/*' | xargs grep 'http:' | grep -v 'http://localhost'
find . -name '*.md'   -not -path '*/vendor/*' | xargs grep 'http:' | grep -v 'http://localhost'

