from nChoose2 import *
def main():
    errors = False
    tests = [(0,0), (1,0), (2,1), (3,3), (4,6), (10,45), (100,4950)]
    for n, expected in tests:
        answer = nChoose2(n)
        if answer != expected:
            print(f'ERROR: {n} choose 2 = {expected}. Your function returned {answer}')
            errors = True
            
    if not errors:
        print('No errors found')
    
main()