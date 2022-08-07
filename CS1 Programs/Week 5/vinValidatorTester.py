# vinValidatorTester.py
from vinValidator import *

def main():
    # Nine valid VINS
    tests  = [('1N4AL3AP8JC280698',True,'Valid VIN, Real VIN in use')]
    tests += [('1N4AL3AP5GC278576',True,'Valid VIN, Real VIN in use')]
    tests += [('JTEBU5JR6H5424686',True,'Valid VIN, Real VIN in use')]
    tests += [('JTDL9RFU2K3011679',True,'Valid VIN, Real VIN in use')]
    tests += [('2C4RC1GG7KR605326',True,'Valid VIN, Real VIN in use')]
    tests += [('5FNYF6H53LB062681',True,'Valid VIN, Real VIN in use')]
    tests += [('3GNAXKEV9LL288891',True,'Valid VIN, Real VIN in use')]
    tests += [('KNDJN2A2XG7838457',True,'Valid VIN, Real VIN in use')]
    tests += [('11111111111111111',True,'Valid VIN, Special testing VIN')]
    
    # Nine invalid VINS
    tests += [('11N4AL3AP8JC280698',False,'Invalid VIN, too long')]
    tests += [('N4AL3AP5GC278576',False,'Invalid VIN, too short')]
    tests += [('JTEBU5JR7H5424686',False,'Invalid VIN, check digit should be 6')]
    tests += [('JTDL9RFU2K3O11679',False,'Invalid VIN, contains bad character O')]
    tests += [('2C4RCIGG7KR605326',False,'Invalid VIN, contains bad character I')]
    tests += [('5FNYF6H53LBQ62681',False,'Invalid VIN, contains bad character Q')]
    tests += [('3GNAXKEVXLL288891',False,'Invalid VIN, check digit should be 9')]
    tests += [('KNDJN2A29G7838457',False,'Invalid VIN, check digit should be X')]
    tests += [('KNDJN2A2WG7838457',False,'Invalid VIN, W not allowed for check digit')]
    
    errors = False
    for vin, expected, description in tests:
        result = validateVIN(vin)
        if result != expected:
            print(f'ERROR: {vin}, {description}, function returned {result}')
            errors = True
            
    if not errors:
        print('No errors found')

main()
    
