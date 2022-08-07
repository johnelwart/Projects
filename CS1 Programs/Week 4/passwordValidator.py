"""
John Elwart
passwordValidator.py
CS:1210 Computer Science 1: Fundamentals
Homework 15 - Password Validator
7/9/22
This function checks passwords from a tester file using a list of predetermined
rules set by the instructor
"""

def validPassword(password):
    # Accumulators to check for at least one uppercase and lowercase letter
    # as well as a number and special character
    upper = 0
    lower = 0
    number = 0
    special = 0
    
    # String of special characters to be used in a for loop
    specialChars = '~`!@#$%^&*()_-+={}[]<>;:,.?'
    
    # Loops over all the characters in password
    for i in range(len(password)):
        # Checks the current character for an uppercase letter and updates the
        # accumulator if there is one
        if 64 < ord(password[i]) < 91:
            upper += 1
        
        # Checks the current character for a lowercase letter and updates the
        # accumulator if there is one            
        if 96 < ord(password[i]) < 123:
            lower += 1
            
        # Checks the current character for a number and updates the
        # accumulator if there is one           
        if 47 < ord(password[i]) < 58:
            number += 1
        
        # Loops over all the characters in the special characters string and
        # updates the accumulator if it finds a match
        for j in specialChars:
            if password[i] == j:
                special += 1
    
    # Adds up all the accumulators to check if there are any invalid characters
    # that wernt counted
    total = upper + lower + number + special
    
    # Returns true if the password is valid or false if the password violates
    # one or more of the password rules
    if (upper == 0 or lower == 0 or number == 0 or special == 0 
        or total != len(password) or len(password) < 12):
        return False
    else:
        return True