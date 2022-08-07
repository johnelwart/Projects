"""
John Elwart
arithmeticQuiz.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 12 - Arithmetic Quiz
6/30/22
This program administers a short arithmetic quiz to the user using random
numbers to choose the operands and the operation. If the user gets the problem
wrong it shows the correct answer and displays the number of correct answers
at the end of the quiz
"""

import random

def generateProblem(o1, o2, op):
    # An operation value of 0 indicates addition
    if op == 0:
        # Gets the users answer to the problem
        userAnswer = int(input(f'{o1} + {o2} = '))
        
        # If the user gets it right, the function returns a 1 to be added to
        # the number of correct answers, If the user gets the problme wrong
        # the function prints the correct answer and returns a 0
        if o1 + o2 == userAnswer:
            return 1
        else:
            print(f'   Sorry, {o1} + {o2} = {o1 + o2}')
            return 0
    
    # An operation value of 1 indicates subtraction
    elif op == 1:
        # Gets the users answer to the problem
        userAnswer = int(input(f'{o1} - {o2} = '))
        
        # If the user gets it right, the function returns a 1 to be added to
        # the number of correct answers, If the user gets the problme wrong
        # the function prints the correct answer and returns a 0        
        if o1 - o2 == userAnswer:
            return 1
        else:
            print(f'   Sorry, {o1} - {o2} = {o1 - o2}')
            return 0
    
    # An operation value of 2 indicates multiplication
    elif op == 2:
        # Gets the users answer to the problem
        userAnswer = int(input(f'{o1} * {o2} = '))
        
        # If the user gets it right, the function returns a 1 to be added to
        # the number of correct answers, If the user gets the problme wrong
        # the function prints the correct answer and returns a 0        
        if o1 * o2 == userAnswer:
            return 1
        else:
            print(f'   Sorry, {o1} * {o2} = {o1 * o2}')
            return 0

def main():
    # Initializes the number of correct answers to 0
    correct = 0

    for i in range(5):
        # Generates random numbers for the two operands and operation
        operandOne = random.randint(0,9)
        operandTwo = random.randint(0,9)
        operation = random.randint(0,2)
        
        # Adds up the number correct by adding the value returned by the
        # function
        correct += generateProblem(operandOne, operandTwo, operation)
    
    # Prints the final score for the user      
    print(f'You got {correct} out of 5')
    
main()