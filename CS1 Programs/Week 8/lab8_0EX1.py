"""
John Elwart
lab8_0EX1.py
CS:1210 Computer Science 1: Fundamentals
Lab 8 - 0EX1 Stack Class
8/4/22
This program creates a class called stack and uses a list to keep track of the
elements in the stack. There are methods in the class that allow you to push
and pop items to and from the stack as well as check the top element, check if
it is empty, print the contents, get the length, and check equality.
"""

class stack:
    # __init__ is the constructor of the class. An argument can be passed in
    # that is of type list which will represent the stack and its contents. If
    # one is not passed in, it is initialized to an empty stack
    def __init__(self, L = None):
        if L is None:
            self.L = []
        else:
            self.L = L
    
    # Push adds the element to the end of the list or the top of the stack
    # since it is Last In First Out
    def push(self, element):
        self.L.append(element)
        
    # Pop returns the element at the top of the stack as long as it is not
    # empty
    def pop(self):
        if not self.isEmpty():
            return self.L.pop()
    
    # Top essentially does the same as pop but it does not remove the element
    # from the list meaning it is still present in the stack
    def top(self):
        if not self.isEmpty():
            return self.L[-1]
        
    # isEmpty checks if the list representing the stack is empty by comparing
    # the length of the list to 0. If it is empty, it returns true, otherwise,
    # it returns false
    def isEmpty(self):
        if len(self.L) == 0:
            return True
        else:
            return False
        
    # __str__ returns the contents of the list followed by TOS indicating which
    # element is at the top of the stack.
    def __str__(self):
        return f'{self.L} TOS'
    
    # __repr__ does the exact same as __str__
    def __repr__(self):
        return f'{self.L} TOS'
    
    # __len__ returns the length of the list representing how many elements are
    # currently on the stack
    def __len__(self):
        return len(self.L)
    
    # If the "other" argument is a stack and equal to L then the method will
    # return true. If the other argument is not a stack the method will return
    # nohing and if the other stack is not equal to L then it will return false
    def __eq__(self, other):
        if not isinstance(other, stack):
            return
        
        if self.L == other.L:
            return True
        else:
            return False
    
    # __ne__ does the same as __eq__ however it will return true if the two
    # are not equal and false if they are equal
    def __ne__(self, other):
        if not isinstance(other, stack):
            return
        
        if self.L == other.L:
            return False
        else:
            return True