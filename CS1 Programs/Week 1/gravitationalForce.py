"""
John Elwart
gravitationalForce.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 3: Gravitational Force
6/12/22
This program calculates the force between two bodies in dynes using the mass of
each body and the distance between the bodies provided by the user and the
gravitational constant k. The values are plugged into Newtons universal law
of gravitation
"""

def main():
    # Approximate gravitational constant
    kConstant = 6.67e-8
    
    # Collecting user input for force calculation
    # Strings are converted to floats
    massBodyOne = float(input("Mass of body 1: "))
    massBodyTwo = float(input("Mass of body 2: "))
    distance = float(input("Distance between the two bodies: "))
    
    # Force calculation
    force = kConstant * ((massBodyOne * massBodyTwo) / (distance ** 2))
    
    # Printing the result
    print(f'\nForce is {force}')
    
main()