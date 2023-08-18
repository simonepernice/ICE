#!/bin/bash
for i in `find ./src -name "*.java" -print`;
do

        aspell -d en_US --mode=ccpp --dont-backup check $i 
 
done
