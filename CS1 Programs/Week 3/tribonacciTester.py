# tribonacciTester.py
from tribonacci import *

def main():
    tests = [(1,1), (2,1), (3,2), (10,149), (15,3136), (20,66012)]
    errors = False

    for i, expected in tests:
        result = tribonacci(i)
        if result != expected:
            print(f'ERROR: {i=} {expected=}  {result=}')
            errors = True
            
    if not errors:
        print('No errors found')
    
main()