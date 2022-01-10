// John Elwart
// 3/4/21
// Classroom.cpp
// Implements each method declared in the header file

#include <iostream>
#include <string>
#include <fstream>
#include <ctime>
#include "Student.h"
#include "Chair.h"
#include "Classroom.h"
using namespace std;

Classroom::Classroom(vector<Chair> chairList) {
}

Chair Classroom::getChairList() {

}
void Classroom::setChairList(Chair chair) {
vector<Chair>chairList;
}

void Classroom::printChairList() {

}

// Description: Separates students into left and right handed vectors
// and then assigns them to a desk based on their handedness
// Inputs: Student vector from main.cpp
// Outputs: Prints the class list to the screen
void Classroom::assignStudents(vector<Student> classList)
{
    vector<Student> leftHanded;
    vector<Student> rightHanded;

    srand(time(NULL));
    int sizeOfVector = classList.size();

    for (int k = 0; k < sizeOfVector; k++) {
        int r = k + rand() % (sizeOfVector - k); // careful here!
        swap(classList[k], classList[r]);
    }



    for (int i = 0; i < classList.size(); i++)
    {
        if (classList[i].getRightHanded() == 1)
        {
            rightHanded.push_back(classList[i]);
        }else {
            leftHanded.push_back(classList[i]);
        }
    }

    cout << endl;
    cout << "Assigning students to chairs" << endl;

    int i = 0;
    while (rightHanded.size() != 0 || leftHanded.size() != 0)
    {

        if(chairList.at(i).getRightHanded() == 1)
        {
            if(rightHanded.size() != 0) {
                chairList.at(i).setStudent(rightHanded.back());
                rightHanded.pop_back();
            }
        }
        else if(chairList.at(i).getRightHanded() == 0)
        {
            chairList.at(i).setStudent(leftHanded.back());
            leftHanded.pop_back();
        }

        i++;
    }

    for (int i = 0; i < chairList.size(); i++)
    {
        chairList.at(i).printChair();
    }
}

// Description: Builds a chair list based on a uiowa classroom
// Inputs: Filename from main.cpp
// Outputs: None
int Classroom::buildChairs(const string &filename) {

    string row;
    int minNumber;
    int maxNumber;

    ifstream fileIn(filename);
    if (fileIn.fail()){
        cout << "Error: could not open student.txt";
        return -1;
    }
    while(!fileIn.eof())
    {
        fileIn >> row;
        fileIn >> minNumber;
        fileIn >> maxNumber;

        for(int i = minNumber; i <= maxNumber; i++)
        {
            if(i == maxNumber)
            {
                Chair chair(row, i, false);
                chairList.push_back(chair);
            } else {
                Chair chair(row, i, true);
                chairList.push_back(chair);
            }

        }
    }

    fileIn.close();
}