// John Elwart
// 3/4/21
// Chair.h
// Contains the data members for the row and seat number as well as if each set is right
// or left handed

#ifndef HOMEWORK_3_CHAIR_H
#define HOMEWORK_3_CHAIR_H

#include <iostream>
#include <string>
#include "Student.h"
using namespace std;

class Chair
{
public:

    explicit Chair(string row = " ", int number = 0, bool rightHanded = true, const Student &student = Student());

    string getRow();
    void setRow(string row);

    int getNumber();
    void setNumber(int number);

    bool getRightHanded();
    void setRightHanded(bool rightHanded);

    Student getStudent();
    void setStudent(Student student);

    void printChair();

private:

    string Ch_row;
    int Ch_number;
    bool Ch_rightHanded;
    Student Ch_student;
};


#endif //HOMEWORK_3_CHAIR_H
