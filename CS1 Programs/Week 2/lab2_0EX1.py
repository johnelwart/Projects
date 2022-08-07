"""
John Elwart
lab2_0EX1.py
CS:1210:EXA Computer Science 1: Fundamentals
Lab 2 - 0EX1: Circles
6/23/22
This program gets a radius value and the number of circles from the user using
dialog boxes and then uses 2 functions to draw a chain of circles.
"""

import turtle

def drawCircle (x, y, radius, color, fillcolor, tur):
    # The pen is lifted up and moved to where the center of the new circle
    # will be and then placed back down
    tur.up()
    tur.goto(x, y)
    tur.down()
    
    # Changing the pen and fill color to black and green
    tur.pencolor(color)
    tur.fillcolor(fillcolor)
    
    # Begins drawing a circle using the x and y locations as the center and
    # drawing around that point. The beginning and end points for the fill are
    # also marked
    tur.begin_fill()
    tur.circle(radius)
    tur.end_fill()
    
    # Pick the pen back up and returns to the center of the circle
    tur.up()
    tur.goto(x, y)
    tur.down()    
    
def multipleCircles(n, radius, tur):
    # for n amount of times, a new circle is drawn down and to the right of
    # the previous circle using parameters passed in from main
    for i in range(n):
        drawCircle(tur.xcor() + (radius * 1.4), tur.ycor() - (radius * 1.4), 
                   radius, 'black', 'green', tur)
        
    
def main():
    # Sets the size of the screen, gives it a white background, and gives it
    # a title
    turtle.setup(700, 700)

    screen = turtle.Screen()
    screen.bgcolor('white')
    screen.title('Week 2 Lab -Circles')
    
    # Creates a new turtle called circles
    circles = turtle.Turtle()
    
    # Gets the number of circles to be drawn from the user using a dialog box
    numCircles = int(turtle.textinput("Number of Circles", "Enter the " +
                                       "number of circles you would like to" +
                                       " draw"))
    
    # Gets the radius of the circles from the user using a dialog box                 
    cirRadius = int(turtle.textinput("Circle Radius", "Enter the circle" +
                                      " radius"))
    
    # Adjusts the starting posistion to account for the radius of the circle
    circles.up()
    circles.goto(-cirRadius, cirRadius) 
    circles.down()
    
    # Calls multiple circles to begin drawing the circles
    multipleCircles(numCircles, cirRadius, circles)
    
    # Stops execution
    turtle.done()
    
main()