from lab4_0EX1 import ljust

def main():
    errors = False
    tests = [('coffee', 10, 'coffee    '), ('coffee', 6, 'coffee'), ('coffee', 4, 'coffee'),
             ('coffee', 0, 'coffee'), ('coffee', -12, 'coffee'), ('', 10, '          '),
             ('', 0, ''),('',-1,'')]
    
    for string, width, expected in tests:
        result = ljust(string, width)
        if result != expected:
            print(f"ljust('{string}', {width}) returned '{result}'. Expected '{expected}'")
            errors = True
            
    if not errors:
        print('No errors found')
        
main()