#include <iostream>
using namespace std;

class Node {
public:
    explicit Node(int c_value = 0, int * c_nextNode = nullptr, int * c_prevNode = nullptr){
        value = c_value;
        nextNode = c_nextNode;
        prevNode = c_prevNode;
    }

    int getValue(){
        return value;
    }
    void setValue(int p_value){
        value = p_value;
    }

    int * getNextNode(){
        return nextNode;
    }
    void setNextNode(int * p_nextNode){
        nextNode = p_nextNode;
    }

    int * getPrevNode(){
        return prevNode;
    }
    void setPrevNode(int * p_prevNode){
        prevNode = p_prevNode;
    }

    void printValue(){
        cout << "The value of the current node is: " << value << endl;
    }

private:
    int value;
    int * nextNode;
    int * prevNode;
};