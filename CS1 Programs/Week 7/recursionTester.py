# recursionGrader.py

from recursion import *
allPassed = True

def removeSubstringsMain():
    global allPassed
    
    testCases = [(1, 'abcdef', 'abc', 'def'),
                 (2, 'abcdef', 'def', 'abc'),
                 (3, 'abcdef', 'bcd', 'aef'),
                 (4, 'abcdef', '', 'abcdef'),
                 (5, 'abcdef', 'abcdef', ''), 
                 (6, 'aabbaabb', 'aa', 'bbbb'),
                 (7, 'abcdef', 'xyz', 'abcdef'),
                 (8, 'aabbaabb', 'a', 'bbbb')]
    
    for num, message, substring, expected in testCases:
        result = removeSubstrings(message, substring)
        if result != expected:
            print(f'Remove Substrings Test {num} Failed. Expected {expected} got {result}')
            allPassed = False
            

def closestMain():
    global allPassed    
    testCases = [(1, [3, 7, 67, 68, 210, 215], [67, 68]),
                 (2, [3, 7, 67, 168, 210, 215], [3, 7]),
                 (3, [3, 47, 67, 168, 210, 215], [210, 215]),
                 (4, [3, 7], [3, 7]),
                 (5, [3, 3, 3, 3, 3, 3], [3, 3]),
                 (6, [1, 2, 3, 4, 5, 6], [5, 6]),
                 (7, [5, 10, 100, 105, 305, 310], [305, 310]),
                 (8, [5, 10, 15], [10, 15])]
    
    for num, L, expected in testCases:
        result = closest(L)
        if result != expected:
            print(f'Closest Test {num} Failed. Expected {expected} got {result}')
            allPassed = False
            

def strDistMain():
    global allPassed    
    testCases = [(1, 'cat dog cat dog cat', 'cat', 19),
                 (2, 'cat dog cat dog ca', 'cat', 11),
                 (3, 'at dog cat dog cat', 'cat', 11),
                 (4, 'dog cat dog', 'cat', 3),
                 (5, 'dog dog', 'cat', 0),
                 (6, 'cat', 'cat', 3),
                 (7, 'ca', 'cat', 0),
                 (8, 'c', 'cat', 0),
                 (9, '', 'cat', 0),
                 (10, 'cat', '', 3),
                 (11, '', '', 0)
                 ]
    
    for num, message, mark, expected in testCases:
        result = strDist(message, mark)
        if result != expected:
            print(f'String Distance Test {num} Failed. Expected {expected} got {result}')
            allPassed = False

def main():
    removeSubstringsMain()
    closestMain()       
    strDistMain()
    if allPassed:
        print('All tests passed')

    
main()  