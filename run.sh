# the script will be run with one argument: path e.g. valid/basic/exit/exit-1.wacc

# if path ends in .wacc, proceed and compile it. Otherwise if it ends in .s, we skip to the gcc instruction
if [[ $1 == *.wacc ]]; then
  fullpath="test/wacc/$1"
  inputStream="${@:2}"

  # Check if wacc-compiler exists
  if [ ! -f "wacc-compiler" ]; then
    echo "wacc-compiler hasn't been found. running make"
    make
  fi

  echo ""
  echo "-----------------------------------"
  echo "Compiling $fullpath:"
  echo ""
  ./compile $fullpath

  filename=$(basename $fullpath .wacc)
else
  filename=$(basename $1 .s)
  inputStream="${@:2}"
fi

gcc -o $filename -z noexecstack $filename.s

if [ $? -ne 0 ]; then
  echo "-----------------------------------"
  echo "Failed to Compile"
  exit 0
fi

echo "Successfuly Assembled to $filename"
echo "-----------------------------------"
echo "Running with Input Stream: $inputStream"
echo "Output:"

# add the rest of the arguments after the first one after the below command
./$filename <<< $inputStream

echo "-----------------------------------"
echo "Ran with exit code: $?"
