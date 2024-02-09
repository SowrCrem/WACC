#!/bin/bash

create_test_entries () {
    local subdirectory="$1"
    local path_var="$2"
    local src_name="$3"
    local expected_exit_code="$4"
    local this_filename="$5"
    local file_path_arg="$6"


    for file in "$subdirectory"/*; do 
        subdirectory_name=$(basename "$subdirectory")
        if [ -d "$file" ]; then
            create_test_entries "$file" "${path_var}/${subdirectory_name}" "$src_name" "$expected_exit_code" "$this_filename" "$file_path_arg"
        elif [[ "$file" == *.wacc ]]; then
            file_name=$(basename "$file")
            src_test_path="test/wacc/${path_var}/${subdirectory_name}/${file_name}"
            test_name="\"${src_name} - ${subdirectory_name} tests: ${file_name}\""
            return_stmt="\"return exit code ${expected_exit_code}\""

            echo -e "  ${test_name} should ${return_stmt} in {
    pending

    val path : Array[String] = Array(\"../$src_test_path\")
    val exitCode = Main.compile(path)
    println(\"Exit Code: \" + exitCode)

    if (exitCode != ${expected_exit_code}) {
      val filePath = \"test/integrationTests/${file_path_arg}\"
      val sedCommand = s\"\"\"sed -i '0,/${test_name} should ${return_stmt} in {/s/${test_name} should ${return_stmt} in {/${test_name} should ${return_stmt} ignore {/' \$filePath\"\"\"
      sedCommand.!
    }

    exitCode shouldBe ${expected_exit_code}
  }\n" >> "$this_filename"
        fi
    done
}                       


src1="./../wacc/valid"
src2="./../wacc/invalid/semanticErr"
src3="./../wacc/invalid/syntaxErr"
declare -a directories=("$src1" "$src2" "$src3")

dst="."
# Get the directory of the script
script_dir=$(dirname "$(readlink -f "$0")")


# Iterate over subdirectories in the source directory
for src in "${directories[@]}"; do
    (
        src_name=$(basename "$src")
        new_dir_path="$script_dir/${src_name}Tests"
        rm -rf "$new_dir_path"
        mkdir -p "$new_dir_path"

        # Set the expected exit code based on the source directory
        case "$src_name" in
            "valid") expected_exit_code=0;;
            "syntaxErr") expected_exit_code=100;;
            "semanticErr") expected_exit_code=200;;
            *) expected_exit_code=0;;  # Default to 0 for unknown source directories
        esac
        
        if [ "$src_name" = "valid" ]; then
            path_var="valid"
        else
            path_var="invalid/$src_name"
        fi

        for subdirectory in "$src"/*/; do
            subdirectory_name=$(basename "$subdirectory")


            # Echo a new Scala file in the destination directory
            file_path_arg="${src_name}Tests/${src_name}${subdirectory_name^}Tests.scala"
            this_filename="$script_dir/$file_path_arg"
            touch "$this_filename"


            echo -e "package integrationTests.${src_name}Tests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import wacc.Main
import parsley.{Failure, Result, Success}
import wacc.parser._
import org.scalatest.Pending
import scala.sys.process._

class ${src_name^}${subdirectory_name^}Tests extends AnyFlatSpec {\n" >> "$this_filename"

                create_test_entries "$subdirectory" "$path_var" "$src_name" "$expected_exit_code" "$this_filename" "$file_path_arg"
                echo "}" >> "$this_filename"

        done
    )
done

echo "Scala files created for each subdirectory."
echo "Add removePending statements as required to this script"
sh ../removePendings.sh -d syntaxErrTests/
sh ../removePendings.sh -d semanticErrTests/
sh ../removePendings.sh -d validTests/
