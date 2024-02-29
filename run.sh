# the script will be run with one argument: path e.g. valid/basic/exit/exit-1.wacc

# we compile "test/wacc" + path
./compile "test/wacc/$1"

# if compile exits with a code other than 0, then we output "wacc-compiler hasn't been found. running make"
# Otherwise, we continue and run gcc -o (filename.s) -z noexecstack (filename)

# then we cd .. and run ./filename
# filename in this example would be exit-1

filename=$(basename $1 .wacc)

if [ $? -ne 0 ]; then
  echo "wacc-compiler hasn't been found. running make"
  make
  ./compile $1
fi

gcc -o $filename -z noexecstack $filename.s

# add the rest of the arguments after the first one after the below command
echo "${@:2}" | ./$filename
