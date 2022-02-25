// John Elwart
// 3/4/21
// Chair.cpp
// Implements each method declared in the header file

#include "Chair.h"
#include "Student.h"
#include <iostream>
#include <string>
using namespace std;

Chair::Chair(string row, int number, bool rightHanded, const Student &student)
{
    setRow(row);
    setNumber(number);
    setRightHanded(rightHanded);
    setStudent(student);
}

string Chair::getRow() {
return Ch_row;
}
void Chair::setRow(string row) {
    Ch_row = row;
}

int Chair::getNumber() {
return Ch_number;
}
void Chair::setNumber(int number) {
    Ch_number = number;
}

bool Chair::getRightHanded() {
return Ch_rightHanded;
}
void Chair::setRightHanded(bool rightHanded) {
    Ch_rightHanded = rightHanded;
}

Student Chair::getStudent() {
return Ch_student;
}
void Chair::setStudent(Student student) {
    Ch_student = student;
}

// Description: Prints the private data members to the screen
// Inputs: None
// Outputs: The values of Student class to the console
void Chair::printChair() {
    char temp = ' ';

    if (Ch_rightHanded == 1)
    {
        temp = 'R';
    }
    else if (Ch_rightHanded == 0)
    {
        temp = 'L';
    }

    cout << Ch_row << " " <<  Ch_number << " " << temp << " ";
    Ch_student.printStudent();
}
