"""
John Elwart
vinValidator.py
CS:1210 Computer Science 1: Fundamentals
Homework 18 - VIN Validator
7/17/22
This program takes in a vin from the function call and first confirms that it
is the correct length then uses a special algorithm to determine if the vin is
valid by multiplying the coded vin and weights based of the position in the
list
"""

def validateVIN(vin):
    # Strings to find the index of a letter/number in a vin and uses that index
    # to determine the cooresponding number
    vinChar = '0123456789ABCDEFGHJKLMNPRSTUVWXYZ'
    coorNumber = '012345678912345678123457923456789'
    
    # Creates a list for the weights based off the index and an empty list for
    # the codedVin and the products
    weight = [8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2]
    codedVin, products = [], []
    
    # Checks the size of the vin to make sure it is 17 characters long
    if len(vin) != 17:
        return False
    
    # Codes the vin using the two strings from above and some string methods
    for i in range(len(vin)):
        codedVin.append(int(coorNumber[vinChar.find(vin[i])]))
    
    # Multiplies the elements in codedVin and weight together and stores it in
    # the products list
    for i in range(len(codedVin)):
        products.append(codedVin[i] * weight[i])
    
    # Creates the check value
    checkVal = sum(products) % 11
    
    # Checks for the special case with the X character in the vin or if the 8th
    # character equals the check value
    if vin[8] == 'X' and checkVal == 10:
        return True
    elif codedVin[8] == checkVal:
        return True    
    else:
        return False 