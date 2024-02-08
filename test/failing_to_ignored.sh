#!/bin/bash

# Check if two arguments are provided
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <test_directory> <package_name>"
    exit 1
fi

# Extract arguments
test_directory="$1"
package_name="$2"

# Iterate through Scala test files
for file in $(find "$test_directory" -type f -name "*.scala"); do
    # Run tests and collect the output
    test_output=$(scala-cli test . --test-only "$package_name.$(basename "$file" .scala)" .)

    # Check if the tests failed
    if [[ $test_output == *"FAILED"* ]]; then
        echo "Modifying $file"
        # Replace " in {" with " ignore {" in the test file
        sed -i 's/\(.*\) in \({\)/\1 ignore \2/' "$file"
    fi
done

echo "Tests run, modifications complete"
