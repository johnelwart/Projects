"""
John Elwart
processName.py
CS:1210 Computer Science 1: Fundamentals
Homework 16 - Process Name
7/9/22
This function takes a string in from a tester file and extracts the first, 
middle, and last names from the string using the slice operator and returns 
them seperatly
"""

def processName(name):
    # Slices the string from the beginning to the first instance of a space
    # representing the first name
    first = name[0 : name.find(' ')]
    
    # Slices the string from one index after the first instance of a space to
    # the last instance of a space representing the middle name
    middle = name[name.find(' ') + 1 : name.rfind(' ')]
    
    # Slices the string from one index after the last instance of a space to
    # the end of the string representing the last name
    last = name[name.rfind(' ') + 1 : len(name)]
    
    # Returns all three strings
    return first, middle, last