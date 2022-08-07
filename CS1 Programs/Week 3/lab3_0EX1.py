"""
John Elwart
lab3_0EX1.py
CS:1210:EXA Computer Science 1: Fundamentals
Lab 3 - 0EX1: Pi Approximation
6/30/22
This program uses a pi approximation formula to estimate the value of pi
after getting a value for number of terms from the user.
"""

import math

def piapprox(n):
    # Initialize the denominator to 1 for the start of the pattern and result
    # to 0
    denominator = 1
    result = 0
    
    for i in range(n):
        # Add the next term to the accumulator (result)
        result += (6 / (denominator ** 2))
        
        # Increment the denominator by 1 for the next term
        denominator += 1
    
    # Return the final result of the function
    return math.sqrt(result)

def main():
    # Get the number of terms from the user of the program
    terms = int(input('Enter the number of terms as an integer: '))
    
    # Print the result after the function executes
    print(f'The pi approximation is: {piapprox(terms)}\n')
    
main()