#! /bin/sh
cd "$(dirname "$0")"/.. || exit 1

# TODO change this to point to your mincamlc executable if it's different, or add
# it to your PATH. Use the appropriate option to run the parser as soon
# as it is implemented
MINCAMLC=scripts/mincamlc
# run all test cases in syntax/valid and make sure they are parsed without error
# run all test cases in syntax/invalid and make sure the parser returns an error

# TODO extends this script to run test in subdirectories
# 

echo VALID
for test_case in tests/backend/valid/*.ml
do
	 echo "testing arm on: $test_case"
	 if $MINCAMLC $test_case -o output/result.s 2> /dev/null 1> /dev/null
	 then
		cd output/
		if  ( make )  #2> /dev/null 1> /dev/null   
		then
			echo "OK"
		else
			echo "KO"
		fi
		cd ../
	 else
	 	echo " ERREUR le programme le transforme pas en ARM"
	 fi
done


