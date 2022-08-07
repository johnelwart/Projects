"""
John Elwart
nChooseK.py
CS:1210 Computer Science 1: Fundamentals
Homework 19 - n Choose k
7/17/22
This program takes in an n and k value and determines the answer using some
special cases or the formula for a combination used in statistics
"""
import math

def nChooseK(n, k):
    # If the k value is zero or the k value equals the n value the function
    # returns 1
    if k == 0 or k == n:
        return 1
    
    # If the k value is greater than the n value the function returns zero
    elif k > n:
        return 0
    
    # Otherwise the function returns the binary coefficient using the
    # statisitics formula n! / k!(n-k)!
    else:
        return math.factorial(n) / (math.factorial(k) * math.factorial(n-k))