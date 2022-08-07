"""
John Elwart
fibonacciOrder.py
CS:1210 Computer Science 1: Fundamentals
Homework 17 - nth Order Fibonacci Sequence
7/17/22
This program takes in a n value representing the order of the fibonacci
sequence and a k value representing the value in the sequence we are looking
for. The function then calculates the sequence numbers up to the kth value and
stores those in a list
"""

def fibonacciOrder(n, k):
    # Defines a list with the first two numbers of the sequence
    fibonacci = [0, 1]
    
    # Loop that generates the rest of numbers in the sequence up to the kth
    # value
    for i in range(2, k + 1):
        # Accumulator that gets added to the fibonacci list as the next element
        # in the sequence
        fibNumber = 0
        
        # If i is greater than 2 but less than n the number that gets added is
        # the sum of the last i - 1 terms
        if 2 <= i <= n:
            for j in range(0, i):
                fibNumber += fibonacci[j]
            fibonacci.append(fibNumber)
        
        # If i is greater than the order, the number that gets added is the sum
        # of the last n numbers
        if i > n:
            for j in range(i - n, i):
                fibNumber += fibonacci[j]
            fibonacci.append(fibNumber)
            
    # Return the kth value of the sequence
    return fibonacci[k]