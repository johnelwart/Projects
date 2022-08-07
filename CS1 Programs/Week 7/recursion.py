"""
John Elwart
recursion.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 23: Three Recursion Problems
7/28/22
This program contains 3 functions that solve diffeent recursion problems.
removeSubstrings removes all the instances of the parameter substring from the
parameter message

closest finds the 2 numbers that are the closest in distance from each other
out of a given list

strDist finds the longest substring length that begins and ends with the mark
passed into the function
"""

def removeSubstrings(message, substring):
    # If the substring is empty, return the original message
    if substring == '':
        return message
    
    # If the substring is the same as the message, return nothing
    elif message == substring:
        return ''
    
    # Find the first instance of the substring in message
    index = message.find(substring)
    
    # If it is not found, return message
    if index == -1:
        return message
    
    # If the index of first instance of substring is 0, call removeSubstrings
    # again using everything in message after that first instance
    elif index == 0:
        return removeSubstrings(message[len(substring):], substring)
    
    # If the index is not zero, call the function with everything after that
    # instance as the message and add that to everything before that instance
    elif index > 0:
        return (message[:index] 
               + removeSubstrings(message[index + len(substring):], substring))
    
def closest(L):
    # If the length of the list is 2, return the list
    if len(L) == 2:
        return L
    
    # Compare the first two elements of the list and the last two elements of
    # the list. If the distance between the last two is greater, call the
    # function again using all the original elements up to the last one
    if abs(L[1] - L[0]) < abs(L[-1] - L[-2]):
        return closest(L[:-1])
    
    # Otherwise, Call the function again using the original elements after the
    # first one
    else:
        return closest(L[1:])

def strDist(message, mark):
    # If the lenght of the message is less than the length of the mark, return
    # a zero
    if len(message) < len(mark):
        return 0
    
    # If the first len(mark) characters of the message do not equal the mark,
    # then call the function again using everything except the first character
    elif message[:len(mark)] != mark:
        return strDist(message[1:], mark)
    
    # If the last len(mark) characters of the message do not equal the mark,
    # then call the function again using everything up to the last character of
    # message
    elif message[len(message) - len(mark):] != mark:
        return strDist(message[:-1], mark)
    
    # Otherwise return the length of message meaning the first and last 
    # len(mark) characters are equal to the mark
    else:
        return len(message)