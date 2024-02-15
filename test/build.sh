# !/bin/bash

create_test_entries () {
    local subdirectory="$1"
    local path_var="$2"
    local test_type="$3"
    local expected_exit_code="$4"
    local this_filename="$5"

    if [ "$test_type" = "valid" ]; then
        test_type="no"
    fi


    for file in "$subdirectory"/*; do 
    (
        subdirectory_name=$(basename "$subdirectory")
        if [ -d "$file" ]; then
            create_test_entries "$file" "${path_var}/${subdirectory_name}" "$test_type" "$expected_exit_code" "$this_filename"
        elif [[ "$file" == *.wacc ]]; then
            file_name=$(basename "$file")
            src_test_path="${path_var}/${subdirectory_name}/${file_name}"
            test_name="\"${src_name} - ${subdirectory_name} tests: ${file_name}\""
            return_stmt="\"return exit code ${expected_exit_code}\""

            echo -e "  ${test_name} should ${return_stmt} in {
    pending
    throws${test_type^}Error(\"${src_test_path}\")
  }\n" >> "$this_filename"
        fi
    )
    done
}                       

src1="wacc/valid"
src2="wacc/invalid/semanticErr"
src3="wacc/invalid/syntaxErr"
declare -a directories=("$src1" "$src2" "$src3")

dst="."
# Get the directory of the script
script_dir="$(dirname "$(readlink -f "$0")")/frontend/integration"


# Iterate over subdirectories in the source directory
for src in "${directories[@]}"; do
    (
        src_name=$(basename "$src")

        # Set the expected exit code based on the source directory
        case "$src_name" in
            "valid") expected_exit_code=0;;
            "syntaxErr") expected_exit_code=100;;
            "semanticErr") expected_exit_code=200;
            * expected_exit_code=-1;;  # Default to -1 for unknown source directories
        esac

        case "$src_name" in
            "valid") test_type="valid";;
            "syntaxErr") test_type="syntax";;
            "semanticErr") test_type="semantic";
            * test_type="NONE";;  # Default to -1 for unknown source directories
        esac
        
        if [ "$src_name" = "valid" ]; then
            path_var="valid"
        else
            path_var="invalid/$src_name"
        fi
        new_dir_path="$script_dir/$test_type"
        rm -rf "$new_dir_path"
        mkdir -p "$new_dir_path"

        for subdirectory in "$src"/*/; do
            subdirectory_name=$(basename "$subdirectory")

        echo "source"
        echo $subdirectory
        echo ""

            # Echo a new Scala file in the destination directory
            file_path_arg="${test_type}/${subdirectory_name^}.scala"
            this_filename="$script_dir/$file_path_arg"
            touch "$this_filename"


            echo -e "package test.frontend.integration.${test_type}.${subdirectory_name^}

import wacc.Main
import test.Utils._
import wacc.parser._
import scala.sys.process._
import parsley.{Failure, Result, Success}
import org.scalatest.Pending
import org.scalatest.compatible.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class ${subdirectory_name^} extends AnyFlatSpec {\n" >> "$this_filename"

                create_test_entries "$subdirectory" "$path_var" "$test_type" "$expected_exit_code" "$this_filename"
                echo "}" >> "$this_filename"

        done
    )
done

echo "Scala files created for each subdirectory."
echo "Add rm_pending statements as required to this script"
sh rm_pendings.sh -d "$script_dir"/syntax/
sh rm_pendings.sh -d "$script_dir"/semantic/
sh rm_pendings.sh -d "$script_dir"/valid/
