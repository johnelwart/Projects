"""
John Elwart
mySet.py
CS:1210:EXA Computer Science 1: Fundamentals
Homework 24: mySet Class
8/  /22

"""

class mySet:

    def __init__(self, list, str, tuple, dict):
        self.list = list
        self.str = str
        self.tuple = tuple
        self.dict = dict
    
    def len(self, set):
        return len(set)
    
    def in_set(self, x):
        return x in set
    
    def not_in_set(self, x):
        return x not in set
    
    def set1_eq_set2(self, set1, set2):
        return set1 == set2
    
    def set1_ne_set2(self, set1, set2):
        return set1 != set2
    
    def isdisjoint(self, otherSet):
        return set.isdisjoint(otherSet)
    
    def issubset(self, otherSet):
        return set <= otherSet
    
    def set_lt_otherSet(self, set, otherSet):
        return set < otherSet
    
    def issuperset(self, otherSet):
        return set >= otherSet
    
    def set_gt_otherSet(self, set, otherSet):
        return set > otherSet
    
    def union(self, otherSet):
        return set | otherSet
    
    def intersection(self, other):
        return set & other
    
    def difference(self, otherSet):
        return set - otherSet
    
    def symmetric_difference(self, otherSet):
        return set ^ otherSet
    
    def copy(self):
        return set.copy()
    
    def update(self, otherSet):
        return set |= otherSet
    
    def intersection_update(self, other):
        return set &= other
    
    def difference_update(self, other):
        return set -= other
    
    def symmetric_difference_update(self, other):
        return set ^= other
    
    def add(self, element):
        return set.add(element)
    
    def remove(self, element):
        return set.remove(element)
    
    def discard(self, element):
        return set.discard(element)
    
    def pop(self):
        return set.pop()
    
    def clear(self):
        return set.clear()