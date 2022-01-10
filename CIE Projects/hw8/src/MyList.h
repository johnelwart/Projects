#include "Node.h"

class MyList {
public:
    MyList() {
        currPtr = nullptr;
    }

    void printAscending();

private:
    Node * currPtr;
};