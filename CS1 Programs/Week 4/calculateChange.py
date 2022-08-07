"""
John Elwart
calculateChange.py
CS:1210 Computer Science 1: Fundamentals
Homework 14 - Calculate Change
7/9/22
This program takes two strings formatted in different ways that contain the
amount owed and amount paid for a product and extract the amounts to calculate
how much change, if any, is owed to the customer or if they paid enough.
"""

def extractAmt(sentence):
    # Index stores the location of the decimal point
    index = sentence.find('.')
    
    # Dollars holds the numbers to the left of the decimal representing the 
    #dollar value. The if statement removes the dollar sign if there is one
    dollars = sentence[sentence.rfind(' ', 0, index) + 1 : index]
    if dollars[0] == '$':
        dollars = dollars[1 : len(dollars)]
    
    # Cents holds the 2 numbers after the decimal point
    cents = sentence[index + 1 : index + 3]
    
    # Dollars and cents are concatenated to the total amount without the
    # decimal and returned by the function as an integer
    return(int(dollars + cents))
        
def convertAmt(amount):
    # If the integer passed in is 0, the function returns 0.00 fromatted
    # correctly, otherwise it inserts the decimal place in the appropriate
    # location
    if amount == 0:
        return('0.00')
    else:
        sentence = str(amount)
        return(sentence[0 : -2] + '.' + sentence[-2 : len(sentence)])


def main():
    # Gets the string for amount owed and paid from the user, passes it
    # straight into extractAmt and stores the result in the appropriate
    # variables
    amtOwed = extractAmt(str(input("How much is owed?\n")))
    amtPaid = extractAmt(str(input("How much was paid?\n")))
    
    # Caluclates the change
    change = amtPaid - amtOwed
    
    # Prints the cost and amount paid using the extracted data from the
    # strings
    print(f'\nCost:   ${convertAmt(amtOwed)}')
    print(f'Paid:   ${convertAmt(amtPaid)}')
    
    # Checks if the amount paid is enough to cover the amount owed. If not,
    # then the program tells the user that not enough was paid
    if change < 0:
        print('Not enough was paid.')
    else:
        print(f'Change: ${convertAmt(change)}')
    
main()