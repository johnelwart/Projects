from nChooseKRecursive import nChooseK

def main():
    errors = False
    
    tests = []
    tests += [(1,1,1), (1,0,1), (5,5,1), (5,0,1), (5,1,5), (5,4,5), (5,2,10), (5,3,10)]
    tests += [(24,24,1), (24, 0, 1), (24,1,24), (24,23,24)]
    tests += [(0,0,1)]
    
    for n, k, expected in tests:
        result = nChooseK(n, k)
        if result != expected:
            print(f'{n} choose {k} = {expected}. Function returned {result}')
            errors = True
            
    if not errors:
        print('No errors found')
        
main()