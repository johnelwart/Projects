// John Elwart
// 2/17/21
// Matrix.h
// Declares all the methods and variables this project will be using such as
// the getter and setter methods as well as each of the different
// mathematical functions[

#ifndef HOMEWORK_2_MATRIX_H
#define HOMEWORK_2_MATRIX_H

class Matrix
{
    public:
        //Part 2: Matrix class constructor
        explicit Matrix(int e1 = 0, int e2 = 0, int e3 = 0, int e4 = 0);

        //Part 3 #2: getter and setters for each private data members
        double getElement1() const;
        void setElement1(double elementVal);

        double getElement2() const;
        void setElement2(double elementVal);

        double getElement3() const;
        void setElement3(double elementVal);

        double getElement4() const;
        void setElement4(double elementVal);

        //Part 3 #3: Print method
        void printMatrix() const;

        //Part 3 #4: Determinant method
        double findDeter() const;

        //Part 3 #5: Inverse method
        void findInverse();

        //Part 3 #6: Transpose method
        void findTranspose();

        //Part 3 #7: Addition of two matrices method
        void addMatrix(double e1, double e2, double e3, double e4);

        //Part 3 #8: Multiplication of two matrices
        void multiplyMatrix(double e1, double e2, double e3, double e4);

    private:

        //Part 1: Private members
        double m_element1{},
               m_element2{},
               m_element3{},
               m_element4{};
};

#endif //HOMEWORK_2_MATRIX_H
