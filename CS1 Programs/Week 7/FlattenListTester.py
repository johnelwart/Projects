from lab7_0EX1 import flatten

def main():
    tests = gettests()
    errors = False
    for testID, L, expected in tests:
        result = flatten(L)
        if result != expected:
            print()
            print(f'{testID}: {L} flattened is {expected}')
            print(f'Function returned {result}')
            errors = True

    if not errors:
        print('No errors found')

def gettests():
    tests = [
             ('Test 1', [],[])
             ,('Test 2', [1], [1]) 
             ,('Test 3', [[[[[[[[[[[[[[[[[[[[[1]]]]]]]]]]]]]]]]]]]]],[1])
             ,('Test 4', [[[[[[[[[[[[1,[[[[[[[[[]]]]]]]]]]]]]]]]]]]]],[1])
             ,('Test 5',[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]],1]]]]]]],[1])
             ,('Test 6',[[1,[[],[[[[[[[[2,[],[[[[[[[[[[]]]],3]],[4,5]]]]],6,7]],8]],[[[[]],9]]]]]]]]],[1,2,3,4,5,6,7,8,9])
             ,('Test 7',[1, 2, 3, 4, 5, 6, 7, 8, 9], [1, 2, 3, 4, 5, 6, 7, 8, 9])
             ,('Test 8', [[1, 2, 3], 4, 5, [6, [7, 8, 9]]], [1,2,3,4,5,6,7,8,9])
             ,('Test 9', [1,[2,[3,[4,[5,[6,[7,[8,[9]]]]]]]]], [1,2,3,4,5,6,7,8,9])
             ,('Test 10', [[[[[[[[[1],2],3],4],5],6],7],8],9], [1,2,3,4,5,6,7,8,9])
             ,('Test 11', [None],[None])
             ,('Test 12', [flatten],[flatten])
             ,('Test 13', [{'a','b'},True],[{'b','a'},True])
             ]
    
    
    return tests

main()