#!/bin/bash
muffet --max-connections-per-host=3 --exclude="https://.*" http://localhost:4000/

