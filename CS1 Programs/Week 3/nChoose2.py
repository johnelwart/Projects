"""
John Elwart
nChoose2.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 13 - n Choose 2
6/30/22
This program calculates the binomial coefficient using the number of terms
given by the user
"""

def nChoose2(n):
    # Initializes result to 0
    result = 0
    
    # Outerloop that loops through all the terms
    for i in range(n):
        
        # Inner loop that loops through terms after current one
        for j in range(i + 1, n):
            
            # Adds one to the result
            result += 1
    
    # Returns the final result
    return result
