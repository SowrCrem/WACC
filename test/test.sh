#!/bin/bash

VERBOSE=true

# TODOs: Refactor, use verbosity, add verbosity to rm_pendings (make a write(msg, file) function that echos msg if verbose is true, and writes msg to a file if not)

# Functions -------------------------------------------------------------------
get_args() {
  if [ $# -eq 0 ]; then
    echo "-----------------------------------"
    echo "Running All Tests"
    scala-cli test .
    local result=$?
    if [ $result -ne 0 ]; then
      exit 1
    fi
    exit 0 
  else if [ $# -eq 1 ]; then
    milestone=$1
  else if [ $# -eq 2 ]; then
    milestone=$1
    type=$2
  else if [ $# -eq 3 ]; then
    milestone=$1
    type=$2
    feature=$3
  else if [ $# -eq 4 ]; then
    milestone=$1
    type=$2
    feature=$3
    if [ $4 == "false" ]; then
      VERBOSE=false
    fi
  else
    echo "Invalid number of arguments"
    exit 1
  fi
  fi
  fi
  fi
  fi
}

# If VERBOSE is true then print the output of the tests and if not then store it in a singular file - test/milestone/type/feature/results.txt
# if any of milestone, type,feature are empty then omit them from the path i.e test/milestone if type is empty or test/milestone/type if feature is empty
# Verbose default is true
run_tests() {
  local name=$1
  local part=""
  if [ ! -z name ]; then
    part=".$(echo $name | tr '[:upper:]' '[:lower:]')"
  fi
  # if name is "" then we need to change name to "Milestone Type"
  if [ "$name" == "" ]; then
    name="$milestone $type"
  fi
  echo "-----------------------------------"
  echo "Running $name tests"

  # Initialize lists
  fails=()
  ignoreds=()
  pendings=()

  # Run the test command line by line
  while IFS= read -r line
  do
    echo "$line"
    if [[ "$line" == *"FAIL"* ]]; then
      fails+=("$line")
    elif [[ "$line" == *"IGNORE"* ]]; then
      ignoreds+=("$line")
    elif [[ "$line" == *"pending"* ]]; then
      pendings+=("$line")
    fi
  done < <(scala-cli test . --test-only "test.$milestone.$type$part*")

  local result=$?
  if [ $result -ne 0 ]; then
    echo "-----------------------------------"
    # TODO: Add this to a list and print all warnings at end
    echo "WARNING: $name Tests Failed"
    exit_code=1
  fi
}

# Script ----------------------------------------------------------------------

# Global Variables
exit_code=0

# Arguments
milestone=""
type=""
feature=""
chmod +x build.sh
./build.sh

# Add the grandparent directory to the PATH and go to the parent directory
export PATH="$(dirname "$(dirname "$PWD")"):$PATH"
cd ..
pwd
scala-cli clean . 
scala-cli compile .

get_args $@

# Check if the milestone is valid
if [[ "$milestone" != "frontend" && "$milestone" != "backend" && "$milestone" != "extension" ]]; then
  echo "Invalid milestone: $milestone should be one of frontend, backend, extension"
  exit 1
fi

if [[ "$type" == "" && "$milestone" == "frontend" ]]; then
  # run_tests with with all possible types
  echo ""
  echo "-----------------------------------"
  echo "Running Unit Tests"
  type="unit"
  run_tests "Syntax"
  run_tests "Semantic"
  echo ""
  echo "-----------------------------------"
  echo "Running Integration Tests"
  type="integration"
  run_tests "Syntax"
  run_tests "Semantic"
  # run_tests "Valid"
else if [[ "$type" != "unit" && "$type" != "integration" && "$type" != "" ]]; then
  echo "Invalid type: $type should be one of unit, integration"
  exit 1
fi
fi

if [[ "$milestone" == "frontend" && "$type" != "" ]]; then
  if [ "$feature" == "" ]; then
    run_tests "Syntax"
    run_tests "Semantic"
    # Valid Tests Temporarily Ignored
    # if [ "$type" == "integration" ]; then
    #   run_tests "Valid"
    # fi
  else
    run_tests "$feature"
  fi
else if [ "$milestone" == "backend" ]; then
  run_tests ""
else if [ "$milestone" == "extension" ]; then
  echo "No tests for extension"
fi
fi
fi

# ...

# Print failed tests
if [ ${#fails[@]} -ne 0 ]; then
echo "-----------------------------------"
  echo "Failed tests:"
  for fail in "${fails[@]}"; do
    echo "$fail"
  done
fi

# Print ignored tests
if [ ${#ignoreds[@]} -ne 0 ]; then
  echo "-----------------------------------"
  echo "Ignored tests:"
  for ignore in "${ignoreds[@]}"; do
    echo "$ignore"
  done
fi

# Print pending tests
if [ ${#pendings[@]} -ne 0 ]; then
  echo "-----------------------------------"
  echo "Pending tests:"
  for pending in "${pendings[@]}"; do
    echo "$pending"
  done
fi

echo "-----------------------------------"
echo "All tests ran with overall exit code $exit_code."

# Clean up
# make clean

# Exit with the stored exit code
exit $exit_code

echo "-----------------------------------"
echo "All tests ran with overall exit code $exit_code."

# Clean up
# make clean

# Exit with the stored exit code
exit $exit_code


# args=$@

# args=$(echo $args | tr '[:upper:]' '[:lower:]')

# package="test.$(echo $args | tr ' ' '.')"
# name=$args

# chmod +x build.sh
# ./build.sh

# # Add the grandparent directory to the PATH and go to the parent directory
# export PATH="$(dirname "$(dirname "$PWD")"):$PATH"
# cd ..
# # scala-cli clean . 
# # scala-cli compile .

# echo "-----------------------------------"
# echo "Running $name tests"
# scala-cli test . --test-only "$package*"
# echo "-----------------------------------"


# exit_code=$?
# echo "all tests ran with overall exit code $exit_code."

# exit $exit_code
# ```

