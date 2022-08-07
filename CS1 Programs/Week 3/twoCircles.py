"""
John Elwart
twoCircles.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 11 - Two Circles
6/30/22
This program gets the center and radius for 2 circeles and determines if the
smaller circle is inside, outside, or if the two overlap
"""
import math

def main():
    # Gathers circle one information from the user
    print('Circle 1 - Center is (x1, y1), radius is r1')
    x1 = int(input('Enter x1: '))
    y1 = int(input('Enter y1: '))
    r1 = int(input('Enter r1: '))
    
    # Gathers circle two information from the user
    print('\nCircle 2 - Center is (x2, y2), radius is r2')
    x2 = int(input('Enter x2: '))
    y2 = int(input('Enter y2: '))
    r2 = int(input('Enter r2: ')) 
    
    # Calculates the distance between the two centers for comparison
    distance = math.sqrt((x2 - x1) ** 2 + (y2 - y1) ** 2)
    
    # Checks if the sum of the distance and one of the radii is less than the
    # other radii
    if distance + r2 < r1 or distance + r1 < r2:
        print('\nSmall circle is INSIDE big circle\n')
        
    # Checks if the distance is greater than the sum of the two radii meaning
    # the circles are seperated
    elif distance > r1 + r2:
            print('\nSmall circle is OUTSIDE big circle\n')   

    # Checks if the sum of the distance and one of the radii is greater than
    # the other radii meaning they overlap
    elif distance + r2 >= r1 or distance + r1 >= r2:
        print('\nCircles OVERLAP\n')
        
main()