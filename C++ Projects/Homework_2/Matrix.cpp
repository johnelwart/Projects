// John Elwart
// 2/17/21
// Matrix.cpp
// Implements each method declared in the header file

#include <iostream>
#include <iomanip>
#include "Matrix.h"
using namespace std;

Matrix::Matrix(int e1, int e2, int e3, int e4)
{
    setElement1(e1);
    setElement2(e2);
    setElement3(e3);
    setElement4(e4);
}

double Matrix::getElement1() const {
    return m_element1;
}

void Matrix::setElement1(double elementVal) {
    m_element1 = elementVal;
}

double Matrix::getElement2() const {
    return m_element2;
}

void Matrix::setElement2(double elementVal){
    m_element2 = elementVal;
}

double Matrix::getElement3() const {
    return m_element3;
}

void Matrix::setElement3(double elementVal){
    m_element3 = elementVal;
}

double Matrix::getElement4() const {
    return m_element4;
}

void Matrix::setElement4(double elementVal){
    m_element4 = elementVal;
}

//Description: Prints the matrix to the screen
//Inputs: None
//Outputs: Elements 1 through 4 to the console
void Matrix::printMatrix() const {

    cout << getElement1() << setw(3) << getElement2() << endl;
    cout << getElement3() << setw(3) << getElement4() << endl;
}

//Description: Finds the determinant of a matrix using the formula (a)(d) - (b)(c)
//Inputs: None
//Outputs: Returns the determinant
double Matrix::findDeter() const {

    //Applying the formula for the determinant here
    double determinant = (getElement1() * getElement4()) - (getElement2() * getElement3());

    return (determinant);
}

//Description: Finds the inverse of a matrix by first finding the scalar then
// multiplying that by every element
//Inputs: None
//Outputs: Stores the result of the inverse in each elements respective member and
//prints them out to the screen
void Matrix::findInverse() {
    double oneOverDet;

    if (findDeter() == 0)
    {
        cout << "This matrix is not invertible" << endl;
    }
    else
    {
        //oneOverDet stores the scalar that very element is multiplied by
        oneOverDet = 1.0 / findDeter();

        cout << "1/determinant: " << oneOverDet << endl;

        //Storing element 1s value in a variable so the setter for element 4
        // uses the original value
        double oriElement1 = getElement1();

        setElement1(getElement4() * oneOverDet);
        setElement2(-1 * getElement2() * oneOverDet);
        setElement3(-1 * getElement3() * oneOverDet);
        setElement4(oriElement1 * oneOverDet);

        cout << getElement1() << setw(5) << getElement2() << endl;
        cout << getElement3() << setw(5) << getElement4() << endl;
    }
}

//Description: Finds the transpose of a matrix
//Inputs: None
//Outputs: Stores the result of the transpose in each elements respective member and
//prints them out to the screen
void Matrix::findTranspose(){

    //Using variables instead of the the getter methods to prevent the setter using
    //a new value instead of an old one.
    double oriElement2 = getElement2(),
           oriElement3 = getElement3();

    //Flipping element 2 and 3 since that's the only thing that changes when
    // taking the transpose of a 2x2 matrix
    setElement2(oriElement3);
    setElement3(oriElement2);

    cout << getElement1() << setw(4) << getElement2() << endl;
    cout << getElement3() << setw(4) << getElement4() << endl;
}

//Description: Adds a matrix passed through the function to the original matrix given
//Inputs: 4 elements that form a new matrix
//Outputs: Adds the new elements to the original one and stores them in the
// member variables
void Matrix::addMatrix(double e1, double e2, double e3, double e4){

    //Adding each element in the matrix passed to the function to the respective
    //element in the original matrix
    setElement1(getElement1() + e1);
    setElement2(getElement2() + e2);
    setElement3(getElement3() + e3);
    setElement4(getElement4() + e4);

    cout << getElement1() << setw(4) << getElement2() << endl;
    cout << getElement3() << setw(4) << getElement4() << endl;
}

//Description: multiplies the original matrix by a new matrix made with variables
// that are passed through
//Inputs: 4 elements that form a new matrix
//Outputs: values resulting from multiplying the new matrix by the old one
void Matrix::multiplyMatrix(double e1, double e2, double e3, double e4) {

    //Keeping the values of the original matrix in variables so that the setters
    //use the original values rather than the new values created during the
    //multiplication process
    double oriElement1 = getElement1(),
           oriElement2 = getElement2(),
           oriElement3 = getElement3(),
           oriElement4 = getElement4();

    //Multiplying the original matrix by the matrix passed through the function
    setElement1((oriElement1 * e1) + (oriElement2 * e3));
    setElement2((oriElement1 * e2) + (oriElement2 * e4));
    setElement3((oriElement3 * e1) + (oriElement4 * e3));
    setElement4((oriElement3 * e2) + (oriElement4 * e4));

    cout << getElement1() << setw(4) << getElement2() << endl;
    cout << getElement3() << setw(4) << getElement4() << endl;
}