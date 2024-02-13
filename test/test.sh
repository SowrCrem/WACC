#!/bin/bash

# Functions -------------------------------------------------------------------
check_args() {
  if [ $# -ne 2 ]; then
    echo "Usage: test.sh <milestone> <type>"
    echo "milestone: frontend, backend, extension"
    echo "type: unit, integration"
    exit 1
  fi
}

run_tests() {
  local name=$1
  local part=""
  if [ ! -z name ]; then
    part=".$(echo $name | tr '[:upper:]' '[:lower:]')"
  fi
  echo "-----------------------------------"
  echo "Running $name tests"
  scala-cli test . --test-only "$milestone.$type$part*"
  scala-cli test . --test-only "$milestone.$type$part*" > test/$milestone/$type/results.txt 2>&1
  local result=$?
  if [ $result -ne 0 ]; then
    echo "-----------------------------------"
    echo "tests failed"
    exit 1
  fi
}

# Script ----------------------------------------------------------------------

# Arguments
check_args $@
milestone=$1
type=$2

# Add the grandparent directory to the PATH and go to the parent directory
export PATH="$(dirname "$(dirname "$PWD")"):$PATH"
cd ..

# Check if the milestone and type are valid
if [[ "$milestone" != "frontend" && "$milestone" != "backend" && "$milestone" != "extension" ]]; then
  echo "Invalid milestone:"
  exit 1
fi

if [[ "$type" != "unit" && "$type" != "integration" ]]; then
  echo "Invalid type"
  exit 1
fi

if [ "$milestone" == "frontend" ]; then
  run_tests "Syntax"
  run_tests "Semantic"
else
  run_tests ""
fi

echo "-----------------------------------"
echo "all tests run"
exit 0
