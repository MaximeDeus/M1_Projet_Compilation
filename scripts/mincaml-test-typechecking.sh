#! /bin/sh
cd "$(dirname "$0")"/.. || exit 1

# TODO change this to point to your mincamlc executable if it's different, or add
# it to your PATH. Use the appropriate option to run the parser as soon
# as it is implemented
MINCAMLC=java/ocamlCompiler/src/main/java/mincamlc

# run all test cases in syntax/valid and make sure they are parsed without error
# run all test cases in syntax/invalid and make sure the parser returns an error

# TODO extends this script to run test in subdirectories
# 
echo VALID
for test_case in tests/typechecking/valid/*.ml
do
    echo "testing parser on: $test_case"
    if $MINCAMLC $test_case -t 2> /dev/null 1> /dev/null
    then
        echo "OK"
    else 
        echo "KO"
    fi
done
echo INVALID
for test_case in tests/typechecking/invalid/*.ml
do
    echo "testing parser on: $test_case"
    if $MINCAMLC $test_case -t 2> /dev/null 1> /dev/null
    then
        echo "OK"
    else 
        echo "KO"
    fi
done
