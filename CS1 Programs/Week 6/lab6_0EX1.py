"""
John Elwart
lab6_0EX1.py
CS:1210 Computer Science 1: Fundamentals
Lab 6 - 0EX1 Building a Book Index
7/21/22
This program reads data from a file and creates a dictionary of terms and page
numbers and then prints out the sorted version to create a book index.
"""

def main():
    # Create a new dictionary and a list for the data read in from the file
    dictionary = {}
    tupleList = []
    
    # Open the input file and output file
    inFile = open("terms.txt", 'r')
    outFile = open("index.txt", 'w')
    
    # Read the data from the file into the lsit, seperating the data at the
    # colon
    for i in inFile.read().split():
        a, b = i.split(':')
        tupleList.append((b, int(a)))
    
    # Populate the dictionary with the data from the tuple list by checking if
    # the term is already in the dictionary and just adding a new value if it
    # is or creating a new key if it is not
    for i in range(len(tupleList)):
        if tupleList[i][0] in dictionary:
            dictionary[tupleList[i][0]].append(tupleList[i][1])
                       
        else:
            dictionary[tupleList[i][0]] = []
            dictionary[tupleList[i][0]].append(tupleList[i][1])
    
    # Sort the dictionary
    dictionary = {key : sorted(dictionary[key]) for key in sorted(dictionary)}
    
    # Convert the sorted dictionary to a list
    index = sorted(dictionary.items())
    
    print(index)
    
    # Print the list to the screen and also to the ouput file
    for key, value in index:
        newLine = (key + ' ' + ', '.join(map(str, value)))
        
        print(newLine)
        outFile.write(newLine + '\n')
        
    inFile.close()
    outFile.close()
    
main()