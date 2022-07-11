// John Elwart
// 2/25/21
// Student.cpp
// Implements each method declared in the header file

#include <iostream>
#include <string>
#include "Student.h"
using namespace std;

Student::Student(string firstName, string lastName, bool rightHanded)
{
    setFirstName(firstName);
    setLastName(lastName);
    setRightHanded(rightHanded);
}

string Student::getFirstName() const {
    return S_firstName;
}
void Student::setFirstName(string firstName) {
    S_firstName = firstName;
}

string Student::getLastName() const {
    return S_lastName;
}
void Student::setLastName(string lastName) {
    S_lastName = lastName;
}

bool Student::getRightHanded() const {
    return S_rightHanded;
}
void Student::setRightHanded(bool rightHanded) {
    S_rightHanded = rightHanded;
}

// Description: Prints the private data members to the screen
// Inputs: None
// Outputs: The values of Student class to the console
void Student::printStudent() {
    char temp = ' ';

    if (S_rightHanded == 1)
    {
        temp = 'R';
    }
    else if (S_rightHanded == 0)
    {
        temp = 'L';
    }

    cout << S_firstName << " " << S_lastName << " " << temp << endl;
}