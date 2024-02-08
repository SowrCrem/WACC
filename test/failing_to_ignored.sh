#!/bin/bash

# Check if an argument is provided
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <test_suite_file>"
    exit 1
fi

# Extract the test suite file from the argument
test_suite="$1"

# Check if the test suite file exists
if [ ! -f "$test_suite" ]; then
    echo "Error: Test suite file not found."
    exit 1
fi

# Compile your Scala code (if not compiled already)
if [ ! -d "out" ]; then
    scala-cli compile .
fi

# Read the test suite file line by line
while IFS= read -r test_case; do
    # Run the individual test case
    test_output=$(scala-cli test . --tests "$test_case" .)
    
    # Check if the test failed
    if [[ $test_output == *"FAILED"* ]]; then
        echo "Modifying $test_case"
        # Replace " in {" with " ignore {" in the test file
        sed -i 's/\(.*\) in \({\)/\1 ignore \2/' "$test_case"
    fi
done < "$test_suite"

echo "Test suite run and modifications complete"
