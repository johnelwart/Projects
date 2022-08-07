# Tester for Week 8 Lab for section 001
# Stack Class
from lab8_0EX1 import stack

tests = []


def test(got, expected, message):
    tests.append(got == expected)
    if expected != got:
        print(f'ERROR: {message}, Expected {expected}, got {got}')


def initializer_tests():
    s = stack()
    test(len(s), 0, 'init Test 1')

    s = stack([])
    test(len(s), 0, 'init Test 2')

    s = stack([1, 2, 3])
    test(len(s), 3, 'init Test 3')


def push_pop_top_tests():
    s = stack()

    s.push('A')
    test(len(s), 1, 'Test 4')
    test(s.top(), 'A', 'Test 5')
    test(s.pop(), 'A', 'Test 6')
    test(len(s), 0, 'Test 7')

    s.push(1)
    s.push(2)
    s.push(3)
    s.push(4)
    test(len(s), 4, 'Test 8')

    test(s.pop(), 4, 'Test 9')
    test(s.pop(), 3, 'Test 10')
    test(s.pop(), 2, 'Test 11')
    test(s.pop(), 1, 'Test 12')
    test(len(s), 0, 'Test 13')


def str_repr_Tests():
    s = stack()
    test(str(s), '[] TOS', 'Test 14a')
    test(s.__repr__(), '[] TOS', 'Test 14b')

    s.push(1)
    test(str(s), '[1] TOS', 'Test 15a')
    test(s.__repr__(), '[1] TOS', 'Test 15b')

    s.push(2)
    test(str(s), '[1, 2] TOS', 'Test 16a')
    test(s.__repr__(), '[1, 2] TOS', 'Test 16b')

    s.push(7)
    test(str(s), '[1, 2, 7] TOS', 'Test 17a')
    test(s.__repr__(), '[1, 2, 7] TOS', 'Test 17b')


def equals_tests():
    s1 = stack()
    s2 = stack()
    test(s1 == s2, True, 'Test 18a')
    test(s1 != s2, False, 'Test 18b')
    test(s2 == s1, True, 'Test 18c')
    test(s2 != s1, False, 'Test 18d')
    test(s1 == s1, True, 'Test 18e')
    test(s1 != s1, False, 'Test 18f')

    s1.push(1)
    s1.push(2)
    s1.push(3)
    test(s1 == s2, False, 'Test 19a')
    test(s1 != s2, True, 'Test 19b')
    test(s2 == s1, False, 'Test 19c')
    test(s2 != s1, True, 'Test 19d')
    test(s1 == s1, True, 'Test 19e')
    test(s1 != s1, False, 'Test 19f')

    s2.push(3)
    s2.push(2)
    s2.push(1)
    test(s1 == s2, False, 'Test 20a')
    test(s1 != s2, True, 'Test 20b')
    test(s2 == s1, False, 'Test 20c')
    test(s2 != s1, True, 'Test 20d')
    test(s1 == s1, True, 'Test 20e')
    test(s1 != s1, False, 'Test 20f')

    s1 = stack()
    s2 = stack()
    for x in range(5):
        s1.push(x)
        s2.push(x)
    test(s1 == s2, True, 'Test 21a')
    test(s1 != s2, False, 'Test 21b')


def isEmpty_tests():
    s = stack()
    test(s.isEmpty(), True, '22')

    s = stack([])
    test(s.isEmpty(), True, '23')

    s.push(1)
    test(s.isEmpty(), False, '24')

    s.pop()
    test(s.isEmpty(), True, '25')


def main():
    initializer_tests()
    push_pop_top_tests()
    str_repr_Tests()
    equals_tests()
    isEmpty_tests()

    
    if all(tests):
        print('No errors found')


main()
