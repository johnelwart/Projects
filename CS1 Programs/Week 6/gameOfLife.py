"""
John Elwart
gameOfLife.py
CS:1210 Computer Science 1: Fundamentals
Homework 20 - Game of Life
7/23/22
This program simulates Conways Game of Life using the python turtle. 100
random coordinates are generated at the start of the program and from there the
simulation updates the live cells using the rules of the game
"""

import turtle
import random

# This function checks the surrounding cells of the current one being checked
# it takes the x and y coords of the current one as well as the live cells and
# the whole game board
def checkSurrounding(x, y, live, board):
    # Variable to hold the total number of neighbors that are live
    neighbors = 0
    
    # Surrounding cells in relation to the current one
    offsets = [(-1, -1), (0, -1), (1, -1), (-1, 0),
               (1, 0), (-1, 1), (0, 1), (1, 1)]
    
    # Checks if a neighbor is live and on the board
    for i, j in offsets:
        if (x + i, y + j) in board:
            if (x + i, y + j) in live:
                neighbors += 1
    
    # Returns the number of neighbors that are live
    return neighbors

# Checks all the cells on the board and updates the ones that are live or dead
# in the next generation
def checkStatus(board, live):
    # New list of live cells that will be filled during function executuon
    newLive = []
    
    # Iterates through all the cells on the board and uses checkSurrounding to
    # determine which cells are going to be alive in the next generation
    for i in range(0, 30):
        for j in range(0, 30):
            if (j, i) in live:
                
                # Checks how many of the neighbors are live
                neighbors = checkSurrounding(j, i, live, board)
                
                # Checks if the current cell stays alive or not
                if neighbors == 2 or neighbors == 3:
                    newLive.append((j, i))
            else:
                # Checks how mnay of the neighbors are live
                neighbors = checkSurrounding(j, i, live, board)
                
                # Checks if the current cell become live
                if neighbors == 3:
                    newLive.append((j, i))
    
    # Returns the new list of live cells
    return newLive
    
# This function draws just the live cells each time the for loop runs through
def setupGrid(liveTur, live):
    # Clear the current live squares
    liveTur.clear()
    
    # Lift the pen up and go to the starting position
    liveTur.up()
    liveTur.goto(-300, 300)    
    
    # Iterates through the board
    for i in range(0, 30):
        for j in range(0, 30):
            if (j, i) in live:
                
                # If the current element is live put the pen down and draw a
                # square with red filling
                liveTur.fillcolor('red')
                liveTur.begin_fill()
                
                for k in range(4):
                    liveTur.down()
                    liveTur.forward(20)
                    liveTur.right(90)
                    liveTur.up()
                    
                liveTur.end_fill()
            
            # Move to the next square    
            liveTur.forward(20)
        
        # Move to the beginning of the next row
        liveTur.up()
        liveTur.goto(-300, liveTur.ycor() - 20)

# Main function that contains the startup code and loop that continues the
# program
def main():
    # Creates a new window
    turtle.setup(650, 650)
    
    # Creates a screen to draw on and title it 'Game of Life'
    screen = turtle.Screen()
    screen.title('Game of Life')
    
    # Create 2 new turles, one for the grid and one for drawing the live
    # squares
    grid = turtle.Turtle()
    liveTur = turtle.Turtle()
    
    # Hide both turtles while they are drawing and sets the update interval
    # delay
    liveTur.hideturtle()
    grid.hideturtle()
    screen.tracer(25)    
    
    # Go to the starting location for the grid and put the pen down
    grid.up()
    grid.goto(-300, 300)
    grid.down()
    
    # Draw the 30 by 30 grid
    for i in range(0, 30):
        for j in range(0, 30):  
            for k in range(4):
                grid.forward(20)
                grid.right(90) 
                
            grid.forward(20)
            
        grid.up()
        grid.goto(-300, grid.ycor() - 20)
        grid.down()
    
    # Create a list of x and y coordinates
    xCoords = list(range(0, 30))
    yCoords = list(range(0, 30))
    
    # Create a new list for all the game board coordinates, randomly generated
    # numbers and the cooresponding coordinates
    gameBoard = []
    randNums = []
    randCoords = []
    
    # Creates all coordinates on the game board
    for i in yCoords:
        for j in xCoords:
            gameBoard.append((j, i))
    
    
    # Generates 100 unique random numbers
    while len(randNums) < 100:
        r = random.randint(0, 899)
        if r not in randNums:
            randNums.append(r)
    
    # Appends the cooresponding coordinates using the random numbers as the
    # list index
    for num in randNums:
        randCoords.append(gameBoard[num])
    
    # Draws the initial live cells   
    setupGrid(liveTur, randCoords)
    
    # Continues the simulation for 50 more times updating the live cells and
    # calling setupGrid to update the live cells
    for i in range(50):
        grid.up()
        grid.goto(-300, 300)
        grid.down()        
        
        randCoords = checkStatus(gameBoard, randCoords)
        
        setupGrid(liveTur, randCoords)
    
    # Ends the program
    turtle.done()
    
main()