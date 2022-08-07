"""
John Elwart
lab4_0EX1.py
CS:1210 Computer Science 1: Fundamentals
Lab 4 - 0EX1 Left Justify a String
7/7/22
This program contains a single function that takes in a string and a width and
returns a new string that contains the old one as well as a calculated number
of spaces to fit the desired width as long as the original string is less than
that width
"""

def ljust(string, width):
    # Checks if the length of the passed in string is less than the desired
    # width
    if len(string) < width:
        # Copy the original string to the new one
        newString = string
        
        # calculate how many spaces are needed by subtracting the sring length
        # from the desired width and add the appropriate number of spaces
        for i in range(width - len(string)):
            newString += ' '
            
        # Return the formatted string
        return newString
    else:
        # If the length of the original string is greater than the width,
        # return the original string
        return string