from lab5_0EX1 import sumIntegers

def main():
    tests = [([],0), ([sumIntegers], 0), (['a', 'b', 1.0], 0), 
             ([1, 1, -2], 0), ([1, 'a', 2.0, 2, 3, 4], 10)]
    
    errors = False
    for L, expected in tests:
        result = sumIntegers(L)
        if result != expected:
            print(f'List {L}')
            print(f'Expected {expected}. Function returned {result}.')
            errors = True
    
    if not errors:
        print('No errors found')
        
main()