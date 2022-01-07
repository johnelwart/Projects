// John Elwart
// 2/17/21
// main.cpp
// Goes through all the methods and tests to make sure they are all
// working as they should

#include <iostream>
#include "Matrix.h"
using namespace std;

int main() {
    //Testing the constructor with default values
    cout << "---Matrix A is: " << endl;

    Matrix a;
    a.printMatrix();

    //Testing the print, determinant, inverse, and transpose methods with Matrix A
    cout << endl;
    cout << "---Matrix B is: " << endl;

    Matrix b(4, 3, 6, 5);
    b.printMatrix();

    cout << endl;
    cout << "The determinant of Matrix B is: " << b.findDeter() << endl << endl;

    cout << "The Inverse of B is: " << endl;
    b.findInverse();

    cout << endl;
    cout << "The transpose of matrix B is: " << endl;
    b.findTranspose();

    //Testing the print, addition and transpose methods with Matrix B
    cout << endl;
    cout << "---Matrix C is: " << endl;

    Matrix c(2,5,4,1);
    c.printMatrix();

    cout << endl;
    cout << "Matrix B + C equals: " << endl;
    c.addMatrix(b.getElement1(), b.getElement2(), b.getElement3(), b.getElement4());

    cout << endl;
    cout << "The transpose of matrix C is: " << endl;
    c.findTranspose();


    //Testing the print, inverse, and multiplication methods with Matrix C and D
    cout << endl;
    cout << "---Matrix D is :" << endl;

    Matrix d(2,3,1,4);
    d.printMatrix();

    cout << endl;
    cout << "---Matrix E is :" << endl;

    Matrix e(2,3,1,4);
    e.printMatrix();

    cout << endl;
    cout << "The Inverse of D is: " << endl;
    d.findInverse();

    cout << endl;
    cout << "Matrix D * E equals: " << endl;
    d.multiplyMatrix(e.getElement1(), e.getElement2(), e.getElement3(), e.getElement4());

    return 0;
}
