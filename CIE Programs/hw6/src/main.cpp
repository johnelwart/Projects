#include <iostream>
#include <string>
using namespace std;

class Business {
public:

    // Business class default constructor
    explicit Business (string name = "noVal", string address = "noVal", int Occupancy = -1){
        // Sets the name value if the passed parameter is not blank
        if(name != " ") {
            m_name = name;
        }

        // Sets the address value if the passed value is not blank
        if(address != " ") {
            m_address = address;
        }

        // Sets the maxOccupancy value if the passed value is greater than zero
        if(Occupancy > 0) {
            maxOccupancy = new int;
            *maxOccupancy = Occupancy;
        }
    }

    // Business class copy constructor
    Business(const Business &oriBusiness){
        m_name = oriBusiness.m_name;
        m_address = oriBusiness.m_address;
        maxOccupancy = oriBusiness.maxOccupancy;
    }

    // Business class destructor, also deletes allocated memory for maxOccupancy
    ~Business() {
        delete maxOccupancy;
    }

    string getName() const {
        return m_name;
    }
    void SetName(string busName) {
        m_name = busName;
    }

    string getAddress() const {
        return m_address;
    }
    void SetAddress(string busAddress) {
        m_address = busAddress;
    }

    string GetDescription() const {
        return m_name + " -- " + m_address;
    }

    int getMaxOccupancy() const {
        return *maxOccupancy;
    }
    void setMaxOccupancy(int occupancy){
        *maxOccupancy = occupancy;
    }

    // Print function for the business class that prints the member variables
    void printBusiness(){
        cout << "Name: " << m_name << endl;
        cout << "Address: " << m_address << endl;
        cout << "Max Occupancy: " << *maxOccupancy << endl;
    }

protected:
    int *maxOccupancy = nullptr;

private:
    string m_name;
    string m_address;
};

class Restaurant : public Business {
public:

    // Restaurant class default constructor
    Restaurant (string name = "noVal", string address = "noVal", int Occupancy = -1, int rating = 0)
    : Business(name, address, Occupancy)
    {
        // Sets the value of rating if the passed value is between 0 and 10
        if(rating >= 0 && rating <= 10){
            m_rating = rating;
        }
    }

    // Restaurant class copy constructor
    Restaurant(const Restaurant &oriRestaurant)
    : Business(oriRestaurant.getName(), oriRestaurant.getAddress(), oriRestaurant.getMaxOccupancy())
    {
        m_rating = oriRestaurant.m_rating;
    }

    // Restaurant class destructor
    ~Restaurant(){

    }

    void SetRating(int userRating) {
        m_rating = userRating;
    }
    int GetRating() const {
        return m_rating;
    }

    // print function for restaurant that calls the business print function and then the value for rating
    void printRestaurant(){
        printBusiness();
        cout << "Rating: " << m_rating << endl;
    }

private:
    int m_rating;
};

int main() {
    Business someBusiness;
    Restaurant favoritePlace;

    // Sets the name and address of a business object
    someBusiness.SetName("ACME");
    someBusiness.SetAddress("4 Main St");

    // Sets the name, address, and rating of a restaurant object
    favoritePlace.SetName("Friends Cafe");
    favoritePlace.SetAddress("500 W 2nd Ave");
    favoritePlace.SetRating(5);

    // Prints descriptions of the business and restaurant objects created above
    cout << someBusiness.GetDescription() << endl;
    cout << favoritePlace.GetDescription() << endl;
    cout << "  Rating: " << favoritePlace.GetRating() << endl;

    cout << endl;

    cout << "Testing business constructor:" << endl;
    Business b1("Test1", "Test1", 50);
    b1.printBusiness();

    cout << endl;

    cout << "Testing restaurant constructor:" << endl;
    Restaurant r1("Test2", "Test2", 50, 4);
    r1.printRestaurant();

    cout << endl;

    cout << "Testing business copy constructor:" << endl;
    Business b2(b1);
    b2.printBusiness();

    cout << endl;

    cout << "Testing restaurant copy constructor:" << endl;
    Restaurant r2(r1);
    r2.printRestaurant();

    return 0;
}