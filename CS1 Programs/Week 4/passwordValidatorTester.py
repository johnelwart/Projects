# passwordValidatorTester.py

from passwordValidator import validPassword


def main():
    errors = False

    # ---------------------------------------------------------------
    # Build Test Cases
    testCases = []

    # Invalid Passwords
    # Everything is OK, but an invalid character is used.
    for ch in '|\\/"\'\n\t\r':  # These are invalid
        testCases.append((f'aA1!xxxxxxxxxxxx{ch}', False, 'Invalid Password, Invalid character used'))

    # Missing an uppercase letter
    testCases.append(('AAAaaaXXX&&&---', False, 'Invalid Password, Missing upper case letter'))

    # Missing a lowercase letter
    testCases.append(('AAA&&&XXX111---', False, 'Invalid Password, Missing lower case letter'))

    # Missing a digit
    testCases.append(('AAAaaa&&&XXX---', False, 'Invalid Password, Missing digit'))

    # Missing a special character
    testCases.append(('AAAaaa111XXXxxx', False, 'Invalid Password, Missing special character'))

    # Too short
    testCases.append(('AAAaaa111&&', False, 'Invalid Password, Too short'))

    # Valid Passwords
    for ch in 'ABCDEFGHIJKLMNOPQRSTUVWXYZ':
        testCases.append((f'!a123456789{ch}', True, 'Valid password'))

    for ch in 'abcdefghijklmnopqrstuvwxyz':
        testCases.append((f'!A123456789{ch}', True, 'Valid Password'))

    for ch in '0123456789':
        testCases.append((f'!aABCDEFGHI{ch}', True, 'Valid password'))

    for ch in '~`!@#$%^&*()_-+={}[]<>;:,.?':
        testCases.append((f'aA123456789{ch}', True, 'Valid password'))

    # ---------------------------------------------------------------
    # Call function with each test case

    for pw, expected, message in testCases:
        result = validPassword(pw)
        if result != expected:
            errors = True
            print(f'Failed on password: {pw}. {message}. Function returned {result}')

    # ---------------------------------------------------------------
    # Print summary

    if not errors:
        print('All tests passed')


main()
