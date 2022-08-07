"""
John Elwart
tribonacci.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 10 - Tribonacci Sequence
6/30/22
This program calculates the tribonacci number at an index from a test file and
compares it to the expected value to determine if its correct
"""

def tribonacci(i):
    # Starts the value of the next number and previous 3 numbers to 0
    nextNum = 0
    oneBack = 0
    twoBack = 0
    threeBack = 0
    
    for j in range(i):
        # Determine the next number in the sequence by adding the previous
        # 3 numbers in the sequence
        nextNum = threeBack + twoBack + oneBack
        
        # Rotate the numbers
        # threeBack <- twoBack <- oneBack <- nextNum
        threeBack = twoBack
        twoBack = oneBack
            
        if j == 0:
            oneBack = 1
        else:
            oneBack = nextNum
    
    # Returns the number at index i
    return oneBack