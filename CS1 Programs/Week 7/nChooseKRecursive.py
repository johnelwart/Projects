"""
John Elwart
nChooseKRecursive.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 22: n Choose k Recursive
7/29/22
This program computes the n choose k value using a recursive function
"""

def nChooseK(n, k):
    # Base cases. If the k value is equal to 0 or the n value, return 1
    if k == 0 or n == k:
        return 1
    
    # Otherwise, return the sum of the n choose k value at n - 1 and k - 1 and
    # the value at n - 1 and k
    else:
        return nChooseK(n - 1, k - 1) + nChooseK(n - 1, k)