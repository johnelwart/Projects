# fibonacciOrderTester.py
from fibonacciOrder import *

def main():
    tests = [(3,1,1), (3,2,1), (3,3,2), (3,10,149), (3,15,3136), (3,20,66012)]
    tests += [(4,1,1), (4,2,1), (4,3,2), (4,4,4), (4,5,8), (4,6,15), (4,15,5536), (4,20,147312)]
    tests += [(15,1,1), (15, 10, 256), (15, 15, 8192), (15, 20, 262124)]
    tests += [(1, 1, 1), (1, 2, 1), (1, 10, 1)]
    tests += [(100, 1, 1), (100, 10, 256), (100, 101, 633825300114114700748351602688)]
    errors = False

    for n, k, expected in tests:
        result = fibonacciOrder(n, k)
        if result != expected:
            print(f'ERROR: {n=} {k=} {expected=}  {result=}')
            errors = True
            
    if not errors:
        print('No errors found')
    
main()