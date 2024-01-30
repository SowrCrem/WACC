#!/bin/bash

src1="./../wacc/valid"
src2="./../wacc/invalid/semanticErr"
src3="./../wacc/invalid/syntaxErr"
directories=("$src1" "$src2" "$src3")

dst="."
# Get the directory of the script
script_dir=$(dirname "$(readlink -f "$0")")


# Iterate over subdirectories in the source directory
for src in "${directories[@]}"; do
    (
        src_name=$(basename "$src")
        mkdir -p "$script_dir/${src_name}Tests"

        # Set the expected exit code based on the source directory
        case "$src_name" in
            "valid") expected_exit_code=0;;
            "semanticErr") expected_exit_code=100;;
            "syntaxErr") expected_exit_code=200;;
            *) expected_exit_code=0;;  # Default to 0 for unknown source directories
        esac

        for subdirectory in "$src"/*/; do
            subdirectory_name=$(basename "$subdirectory")

            # Echo a new Scala file in the destination directory
            this_filename="$script_dir/${src_name}Tests/${src_name}${subdirectory_name}Tests.scala"
            touch "$this_filename"


            echo -e "import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending


class ${src_name^}${subdirectory_name^}Tests extends AnyFlatSpec {\n" >> "$this_filename"

                for file in "$subdirectory"/*; do 
                    file_name=$(basename "$file")
                    src_test_path="test/wacc/${src_name}/${subdirectory_name^}/${file_name}"

                    echo -e "  \"${src_name} - ${subdirectory_name} tests: ${file_name}\" should \"return exit code ${expected_exit_code}\" in {
    val path : Array[String] = Array(\"$src_test_path\")
    val exitCode = Main.compile(path)
    println(\"Exit Code: \" + exitCode)
    exitCode shouldBe ${expected_exit_code}
        
    pending
  }\n" >> "$this_filename"
                done                
                echo "}" >> "$this_filename"

        done
    )
done

echo "Scala files created for each subdirectory."
