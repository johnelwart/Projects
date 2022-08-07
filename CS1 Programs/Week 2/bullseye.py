"""
John Elwart
bullseye.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 8: Bullseye
6/25/22
This program uses the python turtle to create a bullseye using concentric
filled circles
"""

import turtle

def drawCircle(radius, penColor, fillColor, turtle):
    # Set the starting position of the turtle for the circle
    turtle.up()
    turtle.goto(0, 0 - radius)
    turtle.down()    
    
    # Set the pen and fill color using the function parameters
    turtle.pencolor(penColor)
    turtle.fillcolor(fillColor)
    
    # Begin drawing the circle specifying the beginning and end of the fill
    turtle.begin_fill()
    turtle.circle(radius)
    turtle.end_fill()
    
def main():
    # Sets the size of the screen, sets the background color to gray, and
    # stes the title as "Bullseye"
    turtle.setup(400, 400)

    screen = turtle.Screen()
    screen.bgcolor('gray')
    screen.title('Bullseye')
    
    # Creates a new turtle object called bullseye
    bullseye = turtle.Turtle()
    
    # Draws the two black circles with a white fill
    drawCircle(150, 'black', 'white', bullseye)
    drawCircle(135, 'black', 'white', bullseye)
    
    # Draws the two white circles with a black fill
    drawCircle(120, 'white', 'black', bullseye)
    drawCircle(105, 'white', 'black', bullseye)
    
    # Draws the two black circles with a cyan fill
    drawCircle(90, 'black', 'cyan', bullseye)
    drawCircle(75, 'black', 'cyan', bullseye)
    
    # Draws the two black circles with a red fill
    drawCircle(60, 'black', 'red', bullseye)
    drawCircle(45, 'black', 'red', bullseye)
    
    # Draws the two black circles with a yellow fill
    drawCircle(30, 'black', 'yellow', bullseye)
    drawCircle(15, 'black', 'yellow', bullseye)
    
    # Draws a gray circle with a yellow fill
    drawCircle(7.5, 'gray', 'yellow', bullseye)
    
    # Draws a small black circle with a black fill creating a dot
    drawCircle(1, 'black', 'black', bullseye)    
    
    # Stops execution
    turtle.done()
    
main()