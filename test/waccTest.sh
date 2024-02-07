#!/bin/bash

# Add the grandparent directory to the PATH
export PATH="$(dirname "$(dirname "$PWD")"):$PATH"

cd ..

# Collect the outputs of scala-cli test into a file without printing them to console
scala-cli test . > test/testResults.txt

echo "all tests run"
