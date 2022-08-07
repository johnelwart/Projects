"""
John Elwart
concentricSquares.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 7: Concentric Squares
6/23/22
This program uses a for loop to draw 10 concentric squares that vary in size
and in color
"""

import turtle

def makeNewTurtle():
    # Creates a window that is 400px by 400px
    turtle.setup(400, 400)
    
    # Makes a new screen, sets the background color to white
    # and gives it the title "concentric squares"
    squares = turtle.Screen()
    squares.bgcolor('gray')
    squares.title('Concentric Squares')
    
    # Makes a new turtle object called newTurtle that gets returned to main
    newTurtle = turtle.Turtle()
    return newTurtle
    
    
def drawSquare(tur, length):
    # The turtle moves forward by the length passed into the drawSquare
    # function and then turns left 90 degrees to start the next side. This
    # process repeats 4 times
    for i in range(4):
        tur.forward(length)
        tur.left(90)
    
    
def main():
    # Uses the makeNewTurtle function to create a turtle to pass into
    # drawSquare and hides the turtle from the screen
    squares = makeNewTurtle()
    squares.hideturtle()
    
    # Moves the turtle to its starting position so the squares are centered
    squares.up()
    squares.goto(-100, -100)
    squares.down()
    
    # Initializes the side length to 200 and the red value for the pen color
    # and fill color to be 1
    length = 200
    r = 1
    
    for i in range(10):
        # Sets the pen and fill color for the next square
        squares.color(r, 0, 0)
        
        # Passes the turtle and the length into drawSquare to draw the next
        # square and fill it in after it is done
        squares.begin_fill()
        drawSquare(squares, length)
        squares.end_fill()
        
        # Moves the turtle to the starting position of the next square to be
        # drawn using the xcor and ycor functions
        squares.up()
        squares.goto(squares.xcor() + 10, squares.ycor() + 10)
        squares.down()
        
        # Reduces the side length by 20 and the red value for the pen and fill
        # color by 0.1
        length -= 20
        r -= 0.1
        
    # Stops execution
    turtle.done()
    
main()
    