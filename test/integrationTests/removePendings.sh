#!/bin/bash

# Removes all instances of any line containing "pending" in a chosen file or directory (not subdirectories)
# Allows tests to be tested rather than passed as they are pending

if [ $# -eq 0 ]; then
  echo "Usage: $0 [-f <file> | -d <directory>]"
  exit 1
fi

while getopts ":f:d:" option; do
  case $option in
    f)
      file="$OPTARG"
      if [ ! -f "$file" ]; then
        echo "Error: '$file' is not a valid file."
        exit 1
      fi
      ;;
    d)
      directory="$OPTARG"
      if [ ! -d "$directory" ]; then
        echo "Error: '$directory' is not a valid directory."
        exit 1
      fi
      ;;
    \?)
      echo "Invalid option: -$OPTARG"
      exit 1
      ;;
  esac
done

if [ -n "$file" ]; then
  sed -i '/pending/d' "$file"
  echo "Done! Removed all instances of 'pending' from '$file'."
elif [ -n "$directory" ]; then
  find "$directory" -type f -exec sed -i '/pending/d' {} \;
  echo "Done! Removed all instances of 'pending' from files in '$directory'."
fi
