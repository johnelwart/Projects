cmake_minimum_required(VERSION 3.17)
project(homework_3)

set(CMAKE_CXX_STANDARD 14)
configure_file(student.txt student.txt COPYONLY)
configure_file(AJB_E105.txt AJB_E105.txt COPYONLY)
configure_file(CB_W55.txt CB_W55.txt COPYONLY)
configure_file(VAN_LR2.txt VAN_LR2.txt COPYONLY)

add_executable(homework_3 main.cpp Student.cpp Student.h Chair.cpp Chair.h Classroom.cpp Classroom.h)