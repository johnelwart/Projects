# processNameTester.py
from processName import processName

def main():
    errors = False
    
    tests = [('Thomas Alva Edison', 'Thomas', 'Alva', 'Edison')]
    tests += [('Arthur Conan Doyle', 'Arthur', 'Conan', 'Doyle')]
    tests += [('Neil Patrick Harris', 'Neil', 'Patrick', 'Harris')]
    tests += [('Louisa May Alcott', 'Louisa', 'May', 'Alcott')]
    tests += [('Francis Ford Coppola', 'Francis', 'Ford', 'Coppola')]
    tests += [('J. K. Rowling', 'J.', 'K.' ,'Rowling')]
    
    for full, expF, expM, expL in tests:
        gotF, gotM, gotL = processName(full)
        if expF != gotF or expM != gotM or expL != gotL:
            print(f"Error: Expected '{expF}' '{expM}' '{expL}', Got '{gotF}' '{gotM}' '{gotL}'")
            errors = True
    
    if not errors:
        print('No errors found')

main()
