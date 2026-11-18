#!/bin/bas
file="$1"

if [ ! -f "$file" ]; then
	echo "ファイルがありません： $file"
	exit 1
fi

echo "ERROR 件数： $(grep -c ERROR "$file")"
grep ERROR "$file" | sort | uniq -c | sort -rn | head -3