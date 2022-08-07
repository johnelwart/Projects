"""
John Elwart
lab7_0EX1.py
CS:1210 Computer Science 1: Fundamentals
Lab 7 - 0EX1 Flatten a List Recursively
7/28/22
This program flattens a multi dimensional list using recursion
"""

def flatten(obj):
        # Base case. Check if the length of the object is 0, meaning no
        # elements. If it is true return the object
        if len(obj) == 0:
                return obj
        
        # If the element of the list is a list type, flatten the first element
        # of the original list and add the remaining elements that are
        # flattened
        if isinstance(obj[0], list):
                return flatten(obj[0]) + flatten(obj[1:])
        
        # Otherwise, return the first element and flatten anything past that
        return obj[:1] + flatten(obj[1:])    