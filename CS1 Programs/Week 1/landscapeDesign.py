"""
John Elwart
landscapeDesign.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 4: Landscape Design
6/12/22
This program calculates the area of the grass and concrete in a garden. The
garden dimensions and the width of the sidewalk are defined by the user.
"""

def main():
    # Collecting user input for the length and width of the garden (x and y
    # respectively) and the width of the sidewalk (d)
    # Strings are converted into integers for the calculation
    x = int(input("How long is the garden? "))
    y = int(input("How wide is the garden? "))
    d = int(input("How wide are the sidewalks? "))
    
    # Calculating the area of the grass by subtracting the width of the 4 paths
    # along the length of the garden from the total length and the 3 paths 
    # along the width from the total width and multiplying those two values
    grassArea = (x - (4 * d)) * (y - (3 * d))
    
    # Calculating the area of the concrete by subtracting the grass area from
    # the total area
    concreteArea = (x * y) - grassArea
    
    # Printing the final result
    print(f'\nArea of the grass is {grassArea}.\n' +
          f'Area of the concrete is {concreteArea}.')
    
main()