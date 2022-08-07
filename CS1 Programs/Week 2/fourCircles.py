"""
John Elwart
fourCircles.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 9: Four Circles
6/25/22
This program uses the python turtle to create a bullseye using concentric
filled circles
"""

import turtle

def drawCircle(x, y, radius, turtle):
    # Adjusts the turtle posistion for the start of a new circle
    turtle.up()
    turtle.goto(x, y)
    turtle.down()
    
    # Sets the pen color to the black
    turtle.pencolor('black')
    
    # Draws a circle using the passed in radius
    turtle.circle(radius)
    
def drawCircles(radius, turtle):
    # This function calls the drawCircle function 4 times adjusting the x and
    # y posistion for each circle but uses the same radius and turtle each time
    drawCircle(-radius, 0, radius, turtle)
    drawCircle(-radius, -2 * radius, radius, turtle)
    drawCircle(radius, 0, radius, turtle)
    drawCircle(radius, -2 *radius, radius, turtle)
    
def main():
    # Sets the size of the screen, sets the background color to light blue, and
    # stes the title as "Four Circles"
    turtle.setup(600, 600)

    screen = turtle.Screen()
    screen.bgcolor('light blue')
    screen.title('Four Circles')
    
    # Creates a new turtle object called fourCircles
    fourCircles = turtle.Turtle()
    
    # Gets the desired circle radius from the user using a dialog box
    radius = int(turtle.textinput("Radius Input", "Enter the desired" +
                                 " radius"))
    
    # Calls drawCircles to begin drawing
    drawCircles(radius, fourCircles)
    
    # Stops execution
    turtle.done()
    
main()
    