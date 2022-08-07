"""
John Elwart
lab1_0EX1.py
CS:1210 Computer Science 1: Fundamentals
Lab 1 - 0EX1 Driving Calculation, Speed
9/16/22
This program takes in a distance and time value from the user and calculates
the average speed needed to achieve that using the distance = rate * time
formula
"""

def main():
    # Gathering a distance and time input from the user
    distance = int(input("How far do you want to drive: "))
    drivingTime = int(input("How many hours do you want to drive "))
    
    # Calculating the average speed needed to achieve that goal
    averageSpeed = distance / drivingTime
    
    # Printing the results to the screen
    print(f'\nTo drive {distance} miles in {drivingTime} hours.\n' + 
          f'you need to average at least {averageSpeed} MPH')

main()