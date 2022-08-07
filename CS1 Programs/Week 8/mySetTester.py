from mySet import mySet

errorcount = 0
testcount = 0

def checkresult(expected, result, message):
    global errorcount, testcount
    testcount += 1
    if result != expected:
        print(f'Error: {message}.  Expected: {expected}, Got: {result}')
        errorcount += 1

def getTestSets():
    empty = mySet()
    s0 = mySet([1,2,3,4,5,6,7,8,9,10])
    s1 = mySet([1,2,3])
    s2 = mySet([4,5,6])
    s3 = mySet([2,3,4,5])
    return empty, s0, s1, s2, s3

# ----------------------------------------------------------------- init and len
def initializer_test(): # also tests len
    empty, s0, s1, s2, s3 = getTestSets()

    s = mySet()
    message = 'init 1'
    result = len(s)
    expected = 0
    checkresult(expected, result, message)

    s = mySet([])
    message = 'init 2'
    result = len(s)
    expected = 0
    checkresult(expected, result, message)

    s = mySet('')
    message = 'init 3'
    result = len(s)
    expected = 0
    checkresult(expected, result, message)
    
    s = mySet(())
    message = 'init 4'
    result = len(s)
    expected = 0
    checkresult(expected, result, message)

    s = mySet({})
    message = 'init 5'
    result = len(s)
    expected = 0
    checkresult(expected, result, message)

    s = mySet(empty)
    message = 'init 6'
    result = len(s)
    expected = 0
    checkresult(expected, result, message)
    
    s = mySet([1,3,2])
    message = 'init 7'
    result = s
    expected = s1
    checkresult(expected, result, message)

    s = mySet([1,1,1,3,2,3,2,1,2,3,1,2,3])
    message = 'init 8'
    result = s
    expected = s1
    checkresult(expected, result, message)

    s = mySet([1,1,1,3,2,3,2,1,2,3,1,2,3])
    message = 'init 9'
    result = len(s)
    expected = 3
    checkresult(expected, result, message)

    s = mySet((1,1,1,3,2,3,2,1,2,3,1,2,3))
    message = 'init 10'
    result = s
    expected = s1
    checkresult(expected, result, message)

    s = mySet((1,1,1,3,2,3,2,1,2,3,1,2,3))
    message = 'init 11'
    result = len(s)
    expected = 3
    checkresult(expected, result, message)


    s = mySet('Mississippi')
    message = 'init 12'
    result = s == mySet('ispM')
    expected = True
    checkresult(expected, result, message)

    s = mySet('Mississippi')
    message = 'init 13'
    result = len(s)
    expected = 4
    checkresult(expected, result, message)

def containment_test():
    # -------------------------------------------------------------- containment
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'containment 1'
    result = 1 in s1
    expected = True
    checkresult(expected, result, message)

    message = 'containment 2'
    result = 1 not in s1
    expected = False
    checkresult(expected, result, message)

    message = 'containment 3'
    result = 4 in s1
    expected = False
    checkresult(expected, result, message)

    message = 'containment 4'
    result = 4 not in s1
    expected = True
    checkresult(expected, result, message)

    message = 'containment 5'
    result = 4 in empty
    expected = False
    checkresult(expected, result, message)

    message = 'containment 6'
    result = 4 not in empty
    expected = True
    checkresult(expected, result, message)

def equality_test():
    # ----------------------------------------------------------------- equality
    empty, s0, s1, s2, s3 = getTestSets()

    empty1 = mySet()
    s101 = mySet([3,2,1])

    message = 'euqals 1'
    result = empty == empty1
    expected = True
    checkresult(expected, result, message)

    message = 'euqals 2'
    result = s1 == s101
    expected = True
    checkresult(expected, result, message)
    
    message = 'euqals 3'
    result = s1 == s2
    expected = False
    checkresult(expected, result, message)
        
    message = 'euqals 4'
    result = empty != empty1
    expected = False
    checkresult(expected, result, message)

    message = 'euqals 5'
    result = s1 != s101
    expected = False
    checkresult(expected, result, message)
    
    message = 'euqals 6'
    result = s1 != s2
    expected = True
    checkresult(expected, result, message)

def isdisjoint_test():
    # --------------------------------------------------------------- isdisjoint
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'isdisjoint 1'
    result = s1.isdisjoint(s2)
    expected = True
    checkresult(expected, result, message)

    message = 'isdisjoint 2'
    result = s2.isdisjoint(s1)
    expected = True
    checkresult(expected, result, message)
    
    message = 'isdisjoint 3'
    result = s1.isdisjoint(s3)
    expected = False
    checkresult(expected, result, message)

    message = 'isdisjoint 4'
    result = s3.isdisjoint(s1)
    expected = False
    checkresult(expected, result, message)    
    
    message = 'isdisjoint 5'
    result = s1.isdisjoint(empty)
    expected = True
    checkresult(expected, result, message)

    message = 'isdisjoint 6'
    result = empty.isdisjoint(s1)
    expected = True
    checkresult(expected, result, message)    
    
def issubset_test():
    # ----------------------------------------------------------------- issubset
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'subset 1'
    result = s1.issubset(s0)
    expected = True
    checkresult(expected, result, message)
    
    message = 'subset 2'
    result = s0.issubset(s1)
    expected = False
    checkresult(expected, result, message)
    
    message = 'subset 3'
    result = empty.issubset(s1)
    expected = True
    checkresult(expected, result, message)
    
    message = 'subset 4'
    result = s1.issubset(empty)
    expected = False
    checkresult(expected, result, message)

    message = 'subset 5'
    result = s1.issubset(s1)
    expected = True
    checkresult(expected, result, message)

    message = 'subset 6'
    result = empty.issubset(empty)
    expected = True
    checkresult(expected, result, message)

    message = 'subset 7'
    result = s1 <= s0
    expected = True
    checkresult(expected, result, message)
    
    message = 'subset 8'
    result = s0 <= s1
    expected = False
    checkresult(expected, result, message)
    
    message = 'subset 9'
    result = empty <= s1
    expected = True
    checkresult(expected, result, message)
    
    message = 'subset 10'
    result = s1 <= empty
    expected = False
    checkresult(expected, result, message)

    message = 'subset 11'
    result = s1 <= s1
    expected = True
    checkresult(expected, result, message)

    message = 'subset 12'
    result = empty <= empty
    expected = True
    checkresult(expected, result, message)

def ispropersubset_test():
    # ----------------------------------------------------------- ispropersubset
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'proper subset 1'
    result = s1.ispropersubset(s0)
    expected = True
    checkresult(expected, result, message)
    
    message = 'proper subset 2'
    result = s0.ispropersubset(s1)
    expected = False
    checkresult(expected, result, message)
    
    message = 'proper subset 3'
    result = empty.ispropersubset(s1)
    expected = True
    checkresult(expected, result, message)
    
    message = 'proper subset 4'
    result = s1.ispropersubset(empty)
    expected = False
    checkresult(expected, result, message)

    message = 'proper subset 5'
    result = s1.ispropersubset(s1)
    expected = False
    checkresult(expected, result, message)

    message = 'proper subset 6'
    result = empty.ispropersubset(empty)
    expected = False
    checkresult(expected, result, message)

    message = 'proper subset 7'
    result = s1 < s0
    expected = True
    checkresult(expected, result, message)
    
    message = 'proper subset 8'
    result = s0 < s1
    expected = False
    checkresult(expected, result, message)
    
    message = 'proper subset 9'
    result = empty < s1
    expected = True
    checkresult(expected, result, message)
    
    message = 'proper subset 10'
    result = s1 < empty
    expected = False
    checkresult(expected, result, message)

    message = 'proper subset 11'
    result = s1 < s1
    expected = False
    checkresult(expected, result, message)

    message = 'proper subset 12'
    result = empty < empty
    expected = False
    checkresult(expected, result, message)

def issuperset_test():
    # --------------------------------------------------------------- issuperset
    empty, s0, s1, s2, s3 = getTestSets()

    message = 'superset 1'
    result = s0.issuperset(s1)
    expected = True
    checkresult(expected, result, message)
    
    message = 'superset 2'
    result = s1.issuperset(s0)
    expected = False
    checkresult(expected, result, message)
    
    message = 'superset 3'
    result = s1.issuperset(empty)
    expected = True
    checkresult(expected, result, message)
    
    message = 'superset 4'
    result = empty.issuperset(s1)
    expected = False
    checkresult(expected, result, message)

    message = 'superset 5'
    result = s1.issuperset(s1)
    expected = True
    checkresult(expected, result, message)

    message = 'superset 6'
    result = empty.issuperset(empty)
    expected = True
    checkresult(expected, result, message)

    message = 'superset 7'
    result = s0 >= s1
    expected = True
    checkresult(expected, result, message)
    
    message = 'superset 8'
    result = s1 >= s0
    expected = False
    checkresult(expected, result, message)
    
    message = 'superset 9'
    result = s1 >= empty
    expected = True
    checkresult(expected, result, message)
    
    message = 'superset 10'
    result = empty >= s1
    expected = False
    checkresult(expected, result, message)

    message = 'superset 11'
    result = s1 >= s1
    expected = True
    checkresult(expected, result, message)

    message = 'superset 12'
    result = empty >= empty
    expected = True
    checkresult(expected, result, message)

def ispropersuperset_test():
    empty, s0, s1, s2, s3 = getTestSets()
    
    # --------------------------------------------------------- ispropersuperset
    message = 'proper superset 1'
    result = s1.ispropersuperset(s0)
    expected = False
    checkresult(expected, result, message)
    
    message = 'proper superset 2'
    result = s0.ispropersuperset(s1)
    expected = True
    checkresult(expected, result, message)
    
    message = 'proper superset 3'
    result = empty.ispropersuperset(s1)
    expected = False
    checkresult(expected, result, message)
    
    message = 'proper superset 4'
    result = s1.ispropersuperset(empty)
    expected = True
    checkresult(expected, result, message)

    message = 'proper superset 5'
    result = s1.ispropersuperset(s1)
    expected = False
    checkresult(expected, result, message)

    message = 'proper superset 6'
    result = empty.ispropersuperset(empty)
    expected = False
    checkresult(expected, result, message)

    message = 'proper superset 7'
    result = s1 > s0
    expected = False
    checkresult(expected, result, message)
    
    message = 'proper superset 8'
    result = s0 > s1
    expected = True
    checkresult(expected, result, message)
    
    message = 'proper superset 9'
    result = empty > s1
    expected = False
    checkresult(expected, result, message)
    
    message = 'proper superset 10'
    result = s1 > empty
    expected = True
    checkresult(expected, result, message)

    message = 'proper superset 11'
    result = s1 > s1
    expected = False
    checkresult(expected, result, message)

    message = 'proper superset 12'
    result = empty > empty
    expected = False
    checkresult(expected, result, message)

def add_test():
    # ---------------------------------------------------------------------- add
    
    message = 'add 1'
    result = mySet()
    result.add(1)
    expected = mySet([1])
    checkresult(expected, result, message)

    message = 'add 2'
    result = mySet([1,2,3])
    result.add(4)
    expected = mySet([4,2,1,3])
    checkresult(expected, result, message)

    message = 'add 3'
    result = mySet([1,2,3])
    result.add(3)
    expected = mySet([2,1,3])
    checkresult(expected, result, message)


def union_test():
    # -------------------------------------------------------------------- union
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'union 1A'
    result = s1.union(s2)
    expected = mySet([1, 2, 3, 4, 5, 6])
    checkresult(expected, result, message)

    message = 'union 2A'
    result = s2.union(s3)
    expected = mySet([2, 3, 4, 5, 6])
    checkresult(expected, result, message)

    message = 'union 3A'
    result = empty.union(s2)
    expected = mySet([4, 5, 6])
    checkresult(expected, result, message)

    message = 'union 4A'
    result = s2.union(empty)
    expected = mySet([4, 5, 6])
    checkresult(expected, result, message)

    message = 'union 1B'
    result = s1 | s2
    expected = mySet([1, 2, 3, 4, 5, 6])
    checkresult(expected, result, message)

    message = 'union 2B'
    result = s2 | s3
    expected = mySet([2, 3, 4, 5, 6])
    checkresult(expected, result, message)

    message = 'union 3B'
    result = empty | s2
    expected = mySet([4, 5, 6])
    checkresult(expected, result, message)

    message = 'union 4B'
    result = s2 | empty
    expected = mySet([4, 5, 6])
    checkresult(expected, result, message)

def update_test():
    # ------------------------------------------------------------------- update
    message = 'update 1A'
    result = mySet([1, 2, 3])
    result.update(mySet([3, 4, 5]))
    expected = mySet([1, 2, 3, 4, 5])
    checkresult(expected, result, message)

    message = 'update 2A'
    result = mySet()
    result.update(mySet([1, 2, 3]))
    expected = mySet([1, 2, 3])
    checkresult(expected, result, message)

    message = 'update 3A'
    result = mySet([1, 2, 3])
    result.update(mySet())
    expected = mySet([1, 2, 3])
    checkresult(expected, result, message)

    message = 'update 4A'
    result = mySet([1, 2, 3])
    result.update(mySet([1, 2, 3]))
    expected = mySet([1, 2, 3])
    checkresult(expected, result, message)
    
    
    message = 'update 1B'
    result = mySet([1, 2, 3])
    result |= mySet([3, 4, 5])
    expected = mySet([1, 2, 3, 4, 5])
    checkresult(expected, result, message)

    message = 'update 2B'
    result = mySet()
    result |= mySet([1, 2, 3])
    expected = mySet([1, 2, 3])
    checkresult(expected, result, message)

    message = 'update 3B'
    result = mySet([1, 2, 3])
    result |= mySet()
    expected = mySet([1, 2, 3])
    checkresult(expected, result, message)

    message = 'update 4B'
    result = mySet([1, 2, 3])
    result |= mySet([1, 2, 3])
    expected = mySet([1, 2, 3])
    checkresult(expected, result, message)


def intersection_test():
    # ------------------------------------------------------------- intersection
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'intersection 1A'
    result = s1.intersection(s2)
    expected = mySet()
    checkresult(expected, result, message)

    message = 'intersection 2A'
    result = s2.intersection(s3)
    expected = mySet([4, 5])
    checkresult(expected, result, message)

    message = 'intersection 3A'
    result = empty.intersection(s2)
    expected = mySet()
    checkresult(expected, result, message)

    message = 'intersection 4A'
    result = s2.intersection(empty)
    expected = mySet()
    checkresult(expected, result, message)

    message = 'intersection 1B'
    result = s1 & s2
    expected = mySet()
    checkresult(expected, result, message)

    message = 'intersection 2B'
    result = s2  & s3
    expected = mySet([4, 5])
    checkresult(expected, result, message)

    message = 'intersection 3B'
    result = empty  & s2
    expected = mySet()
    checkresult(expected, result, message)

    message = 'intersection 4B'
    result = s2 & empty
    expected = mySet()
    checkresult(expected, result, message)

def intersection_update_test():
    # ------------------------------------------------------ intersection_update
    empty, s0, s1, s2, s3 = getTestSets()
    message = 'intersection_update 1A'
    s1.intersection_update(s2) # the result
    expected = mySet()
    checkresult(expected, s1, message)
    
    empty, s0, s1, s2, s3 = getTestSets()
    message = 'intersection_update 2A'
    s2.intersection_update(s3) # the result
    expected = mySet([4, 5])
    checkresult(expected, s2, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'intersection_update 3A'
    s3.intersection_update(s2) # the result
    expected = mySet([4, 5])
    checkresult(expected, s3, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'intersection_update 4A'
    s1.intersection_update(empty) # the result
    expected = mySet()
    checkresult(expected, s1, message)


    empty, s0, s1, s2, s3 = getTestSets()
    message = 'intersection_update 1B'
    s1 &= s2 # the result
    expected = mySet()
    checkresult(expected, s1, message)
    
    empty, s0, s1, s2, s3 = getTestSets()
    message = 'intersection_update 2B'
    s2 &= s3 # the result
    expected = mySet([4, 5])
    checkresult(expected, s2, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'intersection_update 3B'
    s3 &= s2 # the result
    expected = mySet([4, 5])
    checkresult(expected, s3, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'intersection_update 4B'
    s1 &= empty # the result
    expected = mySet()
    checkresult(expected, s1, message)
    
def difference_test():
    # --------------------------------------------------------------- difference
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'difference 1A'
    result = s1.difference(s2)
    expected = mySet([1, 2, 3])
    checkresult(expected, result, message)

    message = 'difference 2A'
    result = s1.difference(s3)
    expected = mySet([1])
    checkresult(expected, result, message)

    message = 'difference 3A'
    result = s3.difference(s1)
    expected = mySet([4, 5])
    checkresult(expected, result, message)

    message = 'difference 4A'
    result = s3.difference(s3)
    expected = mySet()
    checkresult(expected, result, message)
    
    message = 'difference 5A'
    result = empty.difference(s3)
    expected = mySet()
    checkresult(expected, result, message)

    message = 'difference 6A'
    result = s3.difference(empty)
    expected = mySet([2,3,4,5])
    checkresult(expected, result, message)
    
    message = 'difference 7A'
    result = empty.difference(empty)
    expected = mySet()
    checkresult(expected, result, message)
    
    
    message = 'difference 1B'
    result = s1 - s2
    expected = mySet([1, 2, 3])
    checkresult(expected, result, message)

    message = 'difference 2B'
    result = s1 - s3
    expected = mySet([1])
    checkresult(expected, result, message)

    message = 'difference 3B'
    result = s3 - s1
    expected = mySet([4, 5])
    checkresult(expected, result, message)

    message = 'difference 4B'
    result = s3  - s3
    expected = mySet()
    checkresult(expected, result, message)
    
    message = 'difference 5B'
    result = empty - s3
    expected = mySet()
    checkresult(expected, result, message)

    message = 'difference 6B'
    result = s3 - empty
    expected = mySet([2,3,4,5])
    checkresult(expected, result, message)
    
    message = 'difference 7B'
    result = empty - empty
    expected = mySet()
    checkresult(expected, result, message)    
    
def difference_update_test():
    # -------------------------------------------------------- difference_update
    message = 'difference_update 1A'
    empty, s0, s1, s2, s3 = getTestSets()
    s1.difference_update(s2) # the result
    expected = mySet([1, 2, 3])
    checkresult(expected, s1, message)
    
    message = 'difference_update 2A'
    empty, s0, s1, s2, s3 = getTestSets()
    s2.difference_update(s0) # the result
    expected = mySet()
    checkresult(expected, s2, message)    

    message = 'difference_update 3A'
    empty, s0, s1, s2, s3 = getTestSets()
    empty.difference_update(s0) # the result
    expected = mySet()
    checkresult(expected, empty, message)    


    message = 'difference_update 1B'
    empty, s0, s1, s2, s3 = getTestSets()
    s1 -= s2 # the result
    expected = mySet([1, 2, 3])
    checkresult(expected, s1, message)
    
    message = 'difference_update 2B'
    empty, s0, s1, s2, s3 = getTestSets()
    s2 -= s0 # the result
    expected = mySet()
    checkresult(expected, s2, message)    

    message = 'difference_update 3B'
    empty, s0, s1, s2, s3 = getTestSets()
    empty -= s0 # the result
    expected = mySet()
    checkresult(expected, empty, message)   
    
def symmetric_difference_test():
    # ----------------------------------------------------- symmetric_difference
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'symmetric_difference 1A'
    result = s1.symmetric_difference(s2)
    expected = mySet([1, 2, 3, 4, 5, 6])
    checkresult(expected, result, message)

    message = 'symmetric_difference 2A'
    result = s1.symmetric_difference(s3)
    expected = mySet([1, 4, 5])
    checkresult(expected, result, message)

    message = 'symmetric_difference 3A'
    result = s2.symmetric_difference(s3)
    expected = mySet([2, 3, 6])
    checkresult(expected, result, message)

    message = 'symmetric_difference 4A'
    result = s2.symmetric_difference(s2)
    expected = mySet()
    checkresult(expected, result, message)

    message = 'symmetric_difference 5A'
    result = empty.symmetric_difference(s2)
    expected = mySet([4, 5, 6])
    checkresult(expected, result, message)

    message = 'symmetric_difference 6A'
    result = s2.symmetric_difference(empty)
    expected = mySet([4, 5, 6])
    checkresult(expected, result, message)

    message = 'symmetric_difference 7A'
    result = empty.symmetric_difference(empty)
    expected = mySet()
    checkresult(expected, result, message)


    message = 'symmetric_difference 1B'
    result = s1 ^ s2
    expected = mySet([1, 2, 3, 4, 5, 6])
    checkresult(expected, result, message)

    message = 'symmetric_difference 2B'
    result = s1 ^ s3
    expected = mySet([1, 4, 5])
    checkresult(expected, result, message)

    message = 'symmetric_difference 3B'
    result = s2 ^ s3
    expected = mySet([2, 3, 6])
    checkresult(expected, result, message)

    message = 'symmetric_difference 4B'
    result = s2 ^ s2
    expected = mySet()
    checkresult(expected, result, message)

    message = 'symmetric_difference 5B'
    result = empty ^ s2
    expected = mySet([4, 5, 6])
    checkresult(expected, result, message)

    message = 'symmetric_difference 6B'
    result = s2 ^ empty
    expected = mySet([4, 5, 6])
    checkresult(expected, result, message)

    message = 'symmetric_difference 7B'
    result = empty ^ empty
    expected = mySet()
    checkresult(expected, result, message)
    
    
def symmetric_difference_update_test():
    # ----------------------------------------- symmetric_difference_update_test
    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 1A'
    s1.symmetric_difference_update(s2) # result
    expected = mySet([1, 2, 3, 4, 5, 6])
    checkresult(expected, s1, message)
    
    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 2A'
    s1.symmetric_difference_update(s3) # result
    expected = mySet([1, 4, 5])
    checkresult(expected, s1, message)
    
    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 3A'
    s2.symmetric_difference_update(s3) # result
    expected = mySet([2, 3, 6])
    checkresult(expected, s2, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 4A'
    s2.symmetric_difference_update(s2) # result
    expected = mySet()
    checkresult(expected, s2, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 5A'
    empty.symmetric_difference_update(s2)  # result
    expected = mySet([4, 5, 6])
    checkresult(expected, empty, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 6A'
    s2.symmetric_difference_update(empty) # result
    expected = mySet([4, 5, 6])
    checkresult(expected, s2, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 7A'
    empty.symmetric_difference_update(empty) # result
    expected = mySet()
    checkresult(expected, empty, message)    
    
    
    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 1B'
    s1 ^= s2 # result
    expected = mySet([1, 2, 3, 4, 5, 6])
    checkresult(expected, s1, message)
    
    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 2B'
    s1 ^= s3 # result
    expected = mySet([1, 4, 5])
    checkresult(expected, s1, message)
    
    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 3B'
    s2 ^= s3 # result
    expected = mySet([2, 3, 6])
    checkresult(expected, s2, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 4B'
    s2 ^= s2 # result
    expected = mySet()
    checkresult(expected, s2, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 5B'
    empty ^= s2  # result
    expected = mySet([4, 5, 6])
    checkresult(expected, empty, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 6B'
    s2 ^= empty # result
    expected = mySet([4, 5, 6])
    checkresult(expected, s2, message)

    empty, s0, s1, s2, s3 = getTestSets()
    message = 'symmetric_difference_update 7B'
    empty ^= empty # result
    expected = mySet()
    checkresult(expected, empty, message)   
    
def copy_test():
    # --------------------------------------------------------------------- copy
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'copy 1'
    result = s1.copy()
    expected = mySet([1, 2, 3])
    checkresult(expected, result, message)
    
    message = 'copy 2'
    result = empty.copy()
    expected = mySet()
    checkresult(expected, result, message)    

def remove_test():
    # ------------------------------------------------------------------- remove
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'remove 1'
    s1.remove(1)
    expected = mySet([2, 3])
    checkresult(expected, s1, message)
    
    try:
        s1.remove(1)
        message = 'remove 2 - try block'
        expected = 'exception thrown'
        result = 'exception not thrown'
        checkresult(expected, result, message)
    except KeyError:
        message = 'remove 2 - except block'
        expected = 'exception thrown'
        result = 'exception thrown'
        checkresult(expected, result, message)

    try:
        s1.remove(2)
        message = 'remove 3 - try block'
        expected = 'exception not thrown'
        result = 'exception not thrown'
        checkresult(expected, result, message)
    except KeyError:
        message = 'remove 3 - except block'
        expected = 'exception not thrown'
        result = 'exception thrown'
        checkresult(expected, result, message)
        
def discard_test():
    # ------------------------------------------------------------------ discard
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'discard 1'
    s1.discard(1)
    s1 # result
    expected = mySet([2, 3])
    checkresult(expected, s1, message)
    
    message = 'discard 2'
    s1.discard(7) # result
    expected = mySet([3, 2])
    checkresult(expected, s1, message)

    message = 'discard 3'
    empty.discard(7) # result
    expected = mySet()
    checkresult(expected, empty, message)

    
def clear_test():
    # -------------------------------------------------------------------- clear
    empty, s0, s1, s2, s3 = getTestSets()
    
    message = 'clear 1'
    s0.clear() # result
    expected = mySet()
    checkresult(expected, s0, message)

    message = 'clear 2'
    empty.clear() # result
    expected = mySet()
    checkresult(expected, empty, message)
    
def pop_test():
    # ---------------------------------------------------------------------- pop
    L = [1, 2, 3]
    s = mySet(L)
    item = s.pop()

    message = 'pop 1 - part A'
    expected = 'item selected from the set'
    if item in L:
        result = 'item selected from the set'
        checkresult(expected, result, message)
    else:
        result = 'item was not from the set'
        checkresult(expected, result, message)
        
    message = 'pop 1 - part B'
    expected = 'item removed from the set'
    if item not in s:
        result = 'item removed from the set'
        checkresult(expected, result, message)
    else:
        result = 'item was not removed from the set'
        checkresult(expected, result, message)

    message = 'pop 1 - part C'
    expected = 'Length of the set is reduced by 1'
    if len(s) == len(L) - 1:
        result = 'Length of the set is reduced by 1'
        checkresult(expected, result, message)
    else:
        result = 'Length of the set is incorrect'
        checkresult(expected, result, message)

    
    message = 'pop 1 - part D'
    expected = 'other items are still in the set'
    L.remove(item)
    if L[0] in s and L[1] in s:
        result = 'other items are still in the set'
        checkresult(expected, result, message)
    else:
        result = 'not all other items are still in the set'
        checkresult(expected, result, message)
        
    # Check for exception
    try:
        mySet().pop()
        message = 'pop 2 - try block'
        expected = 'exception thrown'
        result = 'exception not thrown'
        checkresult(expected, result, message)
    except KeyError:
        message = 'pop 2 - except block'
        expected = 'exception thrown'
        result = 'exception thrown'
        checkresult(expected, result, message)    
    
def main():
    initializer_test()
    containment_test()

    equality_test()
    isdisjoint_test()
    issubset_test()
    ispropersubset_test()    
    issuperset_test()
    ispropersuperset_test()
    
    add_test()
    update_test()
    
    union_test()
    intersection_test()
    intersection_update_test()
    difference_test()
    difference_update_test()
    symmetric_difference_test()
    symmetric_difference_update_test() 

    copy_test()
    remove_test()
    discard_test()
    clear_test()
    pop_test()
    
    print(f'{testcount} tests run.')
    
    if errorcount == 0:
        print('No errors found')
    else:
        print(f'{errorcount} errors found')
        
main()
