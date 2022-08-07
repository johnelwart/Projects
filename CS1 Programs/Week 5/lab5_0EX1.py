"""
John Elwart
lab5_0EX1.py
CS:1210 Computer Science 1: Fundamentals
Lab 5 - 0EX1 Sum of All Integers
7/14/22
This function iterates through all the indicies of a list and, if the index is
a int type, adds the integer to an accumulator to keep a running total. At the
end of the interation it returns the total value.
"""

def sumIntegers(L):
    # Initializing the accumulator to 0
    sumOfInts = 0
    
    # Iterates through all the elements of the list
    for i in range(len(L)):
        
        # If the current index is an integer type add the value to the
        # accumulator
        if (type(L[i]) == int):
            sumOfInts += L[i]
    
    # Returns the value of the accumulator       
    return sumOfInts