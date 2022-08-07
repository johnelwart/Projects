"""
John Elwart
elapsedTime.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 5: Elapsed Time
6/12/22
This program calculates the time between two user defined times. These times
are in 24 hour time and the second one is assumed to be later than the first
"""

def main():
    # Collecting the hours, minutes, and seconds for the start time from
    # the user
    print("Time 1")
    timeOneHr = int(input("    Enter hour: "))
    timeOneMin = int(input("    Enter minute: "))
    timeOneSec = int(input("    Enter second: "))
    
    # Collecting the hours, minutes, and seconds for the end time from the
    # user
    print("\nTime 2")
    timeTwoHr = int(input("    Enter hour: "))
    timeTwoMin = int(input("    Enter minute: "))
    timeTwoSec = int(input("    Enter second: "))
    
    # Because we can assume the end time is later, elapsed hours is the end
    # minus the start
    elapsedHr = timeTwoHr - timeOneHr
    
    # To find the elapsed minutes subtract the end and start, If the minutes
    # value is greater than 60, add one to the hour using integer division and
    # use the remainder as the minutes
    elapsedMin = timeTwoMin - timeOneMin
    elapsedHr += elapsedMin // 60
    elapsedMin = elapsedMin % 60
    
    # To find the elapsed minutes subtract the end and start, If the seconds
    # value is greater than 60, add one to the minutes using integer division 
    # and use the remainder as the seconds  
    elapsedSec = timeTwoSec - timeOneSec
    elapsedMin += elapsedSec // 60
    elapsedSec = elapsedSec % 60
    
    # Print the final elapsed time
    print("\nElapsed time\n" + 
          f'    {elapsedHr} hours\n' + 
          f'    {elapsedMin} minutes\n' +
          f'    {elapsedSec} seconds')
    
main()