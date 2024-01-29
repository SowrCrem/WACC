output="syntaxTestNames.txt"
echo -n > $output
for dir in ./*; do
  for filename in "$dir"/*.wacc; do
    echo "$filename" >> $output
  done
done