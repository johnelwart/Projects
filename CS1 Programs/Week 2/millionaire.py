"""
John Elwart
millionaire.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 6: How to Become a Millionaire
6/23/22
This program uses the python turtle to get the amount of money saved each
month, the number of years youll save, and the annual interest rate as
a percent from the user and calculates the worth of the investment and
displays it to the screen
"""

import turtle

def main():
    # Creates a window that is 600px wide and 300px high
    turtle.setup(600, 300)
    
    # Makes a turtle object called millionaire, sets the background color to
    # white, and gives it a title
    millionaire = turtle.Screen()
    millionaire.bgcolor('white')
    millionaire.title('How to Be a Millionaire')
    
    # Hides the cursor so at the end only the text is visable
    turtle.hideturtle()
    
    # Gets the amount saved each month from the user in the form of a float 
    # from a dialog box
    amtSaved = float(turtle.textinput("Amount Saved", "Enter the amount of" +
                                " money saved each month"))
    
    # Gets the number of years planned to save from the user using a dialog box
    # and saving it as float
    numYears = float(turtle.textinput("Years Saved", "Enter the number of" +
                                      " years you plan to save"))
    
    # Gets the annual interest rate from the user using a dialog box and saving
    # it as a float
    interestRate = float(turtle.textinput("Annual Interest Rate", "Enter the" +
                                          " annual interest rate as a " +
                                          "percent"))
    
    # Because the formula is long, the numerator is calculated first then
    # the denominator. The final resut is calculated by multiplying the
    # amount saved by the numerator divided by the denominator
    numerator = ((1 + (interestRate / 1200)) ** (12 * numYears)) - 1
    denominator = interestRate / 1200
    
    futureValue = amtSaved * (numerator / denominator)
    
    # Write the final value to the turtle screen in a 16px font
    font = ('Arial', 16)
    turtle.write(f'The future value of your savings is: ' + 
                 f'${"{:.2f}".format(futureValue)}', False, 'center', font)
    
    # Stops execution
    turtle.done()

main()