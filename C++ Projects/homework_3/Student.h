// John Elwart
// 2/25/21
// Student.h
// Contains the data members for the first and last name of a student as well as if
// they are right or left handed. It also contains the getters and setter for each
// member

#include <iostream>
#include <string>
#include <vector>
using namespace std;

#ifndef HOMEWORK_3_STUDENT_H
#define HOMEWORK_3_STUDENT_H

class Student{

public:

    explicit Student(string firstName = "noVal", string lastName = "noVal", bool rightHanded = true);

    string getFirstName() const;
    void setFirstName(string firstName);

    string getLastName() const;
    void setLastName(string firstName);

    bool getRightHanded() const;
    void setRightHanded(bool rightHanded);

    void printStudent();


private:
    string S_firstName;
    string S_lastName;
    bool S_rightHanded;

};

#endif //HOMEWORK_3_STUDENT_H
