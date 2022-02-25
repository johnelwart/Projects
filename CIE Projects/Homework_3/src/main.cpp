// John Elwart
// 2/17/21
// main.cpp
// Declares the classList vector and opens the student.txt file for
// input into the vector

#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include "Student.h"
#include "Chair.h"
#include "Classroom.h"
using namespace std;

int main()
{
    vector<Student>classList;
    string firstName;
    string lastName;
    string rol;
    bool rightHanded;

    ifstream fileIn("student.txt");
    if (fileIn.fail()){
        cout << "Error: could not open student.txt";
        return -1;
    }
    while (!fileIn.eof())
    {
        fileIn >> firstName;
        fileIn >> lastName;
        fileIn >> rol;

        if(rol == "R")
        {
            rightHanded = true;
        }
        else if (rol == "L") {
            rightHanded = false;
        }
        Student student(firstName, lastName, rightHanded);
        classList.push_back(student);
    }

    for (int i = 0; i < classList.size(); i++)
    {
        classList.at(i).printStudent();
    }
    fileIn.close();

    Classroom c1;
    Classroom c2;
    Classroom c3;

    c1.buildChairs("AJB_E105.txt");
    c2.buildChairs("CB_W55.txt");
    c3.buildChairs("VAN_LR2.txt");

    cout << endl;
    cout << "CLASSROOM 1: AJB E105" << endl;
    c1.assignStudents(classList);
    cout << endl;
    cout << "CLASSROOM 2: CB W55" << endl;
    c2.assignStudents(classList);
    cout << endl;
    cout << "CLASSROOM 3: VAN LR2" << endl;
    c3.assignStudents(classList);
}