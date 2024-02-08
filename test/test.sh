#!/bin/bash

# Add the grandparent directory to the PATH
export PATH="$(dirname "$(dirname "$PWD")"):$PATH"

cd ..

# Collect the outputs of scala-cli test into a file without printing them to console
scala-cli test . > test/testResults.txt

# examine each line of the file and check if it contains "FAILED" or "pending". if so, print the line and line before it to console
# prevline=$(read -r line)
while IFS= read -r line
do
  # if [[ $line == *"FAILED"* ]] || [[ $line == *"pending"* ] || [ $line == *"IGNORED"* ]]; then
  if [[ $line == *"FAILED"* ]] || [[ $line == *"pending"* ]]; then
    echo $prevLine
    echo $line
  fi
  prevLine=$line
done < "test/testResults.txt"

echo "all tests run"
