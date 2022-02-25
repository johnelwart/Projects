// John Elwart
// 3/4/21
// Classroom.h
// Contains the classroom outline of all available seats

#ifndef HOMEWORK_3_CLASSROOM_H
#define HOMEWORK_3_CLASSROOM_H

#include <iostream>
#include <string>
#include <vector>
#include "Student.h"
#include "Chair.h"
#include "Classroom.h"
using namespace std;

class Classroom
{
public:

    explicit Classroom(vector<Chair> chairList = { });

    Chair getChairList();
    void setChairList(Chair chair);

    void printChairList();

    int buildChairs(const string& filename);

    void assignStudents(vector<Student> classList);

private:
    vector<Chair>chairList;

};

#endif //HOMEWORK_3_CLASSROOM_H
