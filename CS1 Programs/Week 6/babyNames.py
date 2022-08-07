"""
John Elwart
babyNames.py
CS:1210 Computer Science 1: Fundamentals
Homework 21 - Baby Names
7/24/22
This program reads starts by giving the user a year range and the current
output file and then 5 options. Option 1 allows the user to change the year
range, Option 2 allows the user to change the output file, Option 3 reads in
the data from each year and determins the most popular boy and girl name and 
prints it to the output file, option 4 reads in the data from the year range
and finds any uique names that were introduced that year and prints them to
the output file, and finally option 5 quits the program
"""

# Helper function to determine the most popular name or names for each year
def buildMostPop(nameList, year, sex, outFile):
    # List to hold the most popular names that will be printed at the end
    popNames = []
    
    # Holds the highest popularity rating as the name list is iterated through
    mostPopular = 0
    
    # String that gets printed out for the most popular boy and girl names for
    # each year
    result = f'Most popular {sex} name for {year}'
    
    # Iterates through the list of names and finds the highest poularity rating
    for entry in nameList:
        if int(entry[entry.rfind(',') + 1 : -1]) > mostPopular:
            mostPopular = int(entry[entry.rfind(',') + 1 : -1])
    
    # Iterates through the name list again to find all the names that have that
    # popularity rating and adds them to the popNames list
    for entry in nameList:
        if int(entry[entry.rfind(',') + 1 : -1]) == mostPopular:
            popNames.append(entry[0 : entry.find(',')])
    
    # If the popNames list is greater than 1, add an s to the end of name
    if len(popNames) > 1:
        index = result.rfind('name') + 4
        result = ''.join((result[ : index], 's ', result[-8 : ]))
    
    # Write the sentence containing sex and current year to the output file
    outFile.write(result)
                
    # Print all the names to the output file
    for entry in popNames:
        outFile.write(f'\n{entry}')
    
    outFile.write('\n\n')

def main():
    # Setting the default value for start year and end year and output file 
    # name
    startYear = 1880
    endYear = 2018
    outFileName = 'output.txt'
    
    run = True
    
    # Program will run until run is set to false
    while(run):
        
        # Print the current year range and output file name
        print(f'\nCurrent range: {startYear} - {endYear}\n' + 
              f'Current output file: {outFileName}')
        
        # Print the choices and wait for the user choice
        choice = int(input('1. Specify the date range\n' +
                           '2. Specify output file name\n' +
                           '3. Most popular names\n' +
                           '4. When names first appear\n' +
                           '5. Quit\n\n' +
                           'Make selection: '))
        
        # If the user chooses 1, Let them change the start and end years
        if choice == 1:
            # Wait for user input for the start year
            print(f'\nDefault value is 1880, Current value is: {startYear}')
            newStartYr = int(input('Enter desired start year: '))
            
            # Wait for user input for the end year
            print(f'\nDefault value is 2018, Current value is: {endYear}')
            newEndYr = int(input('Enter desired end year: '))
                
            # If the user chooses a start year that is out of the default range
            # then set the default value
            if newStartYr < 1880 or newStartYr > 2018:
                startYear = 1880
            
            # IF the user chooses an end year that is out of the default range
            # then set the default value
            elif newEndYr < 1880 or newEndYr > 2018:
                endYear = 2018
            
            # If the user chosen start year is greater than the user chosen end
            # year then flip the two values
            elif newStartYr > newEndYr:
                startYear = newEndYr
                endYear = newStartYr            
             
            # If all the parameters are passed then set the start year and end
            # year with the user chosen ones
            else:
                startYear = newStartYr
                endYear = newEndYr
        
        # If the user chooses 2, then allow them to change the output file name       
        elif choice == 2:
            # Wait for user input containing the name of the file
            print(f'\nDefault name is: output.txt, Current name is: ' +
                  f'{outFileName}')
            
            # assign the user input to the output file name variable
            outFileName = str(input('Enter new file name: '))
        
        # If the user chooses 3, determine the most popular boy and girl
        # names(s) for each year in the year range
        elif choice == 3:
            # currYear starts at the startYear of the year range and is
            # incremented by one each time the loop goes through
            currYear = startYear
            
            # Opens the output file
            outFile = open(outFileName, 'w')
            
            # loops through each year in the year range
            for i in range((endYear - startYear) + 1):
                # List to hold all the data read in from the input file
                names = []
                
                # Individual lists that hold the boy and girl names for each
                # year
                boyNames  = []
                girlNames = []                
                
                # Opens the input file cooresponding to the current year
                inFile = open(str(f'testdata\\yob{currYear}.txt'), 'r')
                
                # Adds each line into the name list
                for line in inFile:
                    names.append(line)
                    
                # closes the input file as it is not needed anymore
                inFile.close()
                
                # Seperates the data in the names list by sex and adds it to
                # the respective list
                for entry in names:
                    if entry[entry.find(',') + 1 : entry.rfind(',')] == 'M':
                        boyNames.append(entry)
                        
                    else:
                        girlNames.append(entry)
                
                # Generates the most popular list for boy and girl names for
                # each year
                buildMostPop(girlNames, currYear, 'girl', outFile)
                
                buildMostPop(boyNames, currYear, 'boy', outFile)
                
                # Increment the year
                currYear += 1
            
            # Close the output file
            outFile.close()   
        
        # If the user chooses 4, Find the unique names for each year in the
        # year range
        elif choice == 4:
            # Current year starts at the start of the year range
            currYear = startYear
            
            # Opens the output file
            outFile = open(outFileName, 'w')
            
            # Creates a list for existing names to check against names for each
            # year
            existingNames = []
            
            # Loops once for each year in the range
            for i in range((endYear - startYear) + 1):
                # Holds all the data for that year
                names = []
                
                # Contains the unique names for that year, resets for each
                # year
                uniqueNames = []
                
                # Opens the input file for each year in the range
                inFile = open(str(f'testdata\\yob{currYear}.txt'), 'r')
                
                # Adds each line into the names list
                for line in inFile:
                    names.append(line)
                
                # Close the input file as it is not needed anyore
                inFile.close()
                
                # Iterates through each element in names and adds the unique
                # ones for that year to the unique names list
                for entry in names:
                    if entry[0 : entry.find(',')] not in existingNames:
                        uniqueNames.append(entry[0 : entry.find(',') + 1])
                
                # If there are names in the list print the year and which names
                # are unique for that year then add them to the existing names
                # list
                if len(uniqueNames) > 0:
                    outFile.write(f'Names that first appeared in {currYear}\n')
                            
                    for entry in uniqueNames:
                        outFile.write(f'{entry[0 : entry.find(",")]}\n')
                        existingNames.append(entry[0 : entry.find(',')])  
                    
                    outFile.write('\n')
                
                # Increment the current year        
                currYear += 1
            
            # Close the output file    
            outFile.close()
        
        # If the user chooses 5, then the run variable gets set to false and
        # the condition in the while loop will cause it to end
        elif choice == 5:
            run = False
    
main()