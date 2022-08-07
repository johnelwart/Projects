"""
John Elwart
cableRevenue.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 2: Cable Installation Revenue
6/12/22
This program calculates the revenue generated from installing an amount of 
cable defined by the user in yards at the number of different locations also 
defined by the user. The result is printed to the screen after calculation.
"""

def main():
    # User input for the amount of cable and number of unique locations
    # Input strings are converted into integers for the purpose of calculation
    totalCable = int(input("Yards of cable: "))
    numLocations = int(input("Number of locations "))
    
    # Calculating the revenue using the given rates
    revenue = (numLocations * 25) + ((totalCable * 3) * 2)
    
    # Printing the final result
    print(f'\nThe revenue generated from {totalCable} yards of cable \n' +
          f'at {numLocations} different locations is ${revenue}.')
    
main()