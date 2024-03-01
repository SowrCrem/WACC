# the script will be run with one argument: path e.g. valid/basic/exit/exit-1.wacc

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

gcc -o $filename -z noexecstack $filename.s

if [ $? -ne 0 ]; then
  echo "-----------------------------------"
  echo "Failed to compile"
  exit 0
fi

echo "Successfuly Compiled"
echo "-----------------------------------"
echo "Running with Input Stream: $inputStream"
echo "Output:"

# add the rest of the arguments after the first one after the below command
./$filename <<< $inputStream

echo "-----------------------------------"
echo "Ran with exit code: $?"
